package com.cantina.main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import com.cantina.jsonpojo.Subview;
import com.cantina.jsonpojo.SubviewAbstract;
import com.cantina.jsonpojo.SystemViewController;
import com.google.gson.Gson;

public class FindMatchingViews {
	//HTTP CLIENT to be used to fetch JSON real time.
	public static final HttpClient client = HttpClientBuilder.create().build();
	
	//CLASS --> LIST<Json String output>
	static HashMap<String, List<String>> outClassMatch = new HashMap<String, List<String>>();
	
	//CLASSNAMES --> LIST<Json String output>
	static HashMap<String, List<String>> outClassNamesMatch = new HashMap<String, List<String>>();
	
	//IDENTIFIER --> LIST<Json String output>
	static HashMap<String, List<String>> outIdentifierMatch = new HashMap<String, List<String>>();
	
	
	//The Final Object (in memory) that contains all the data from the JSON.
	static SystemViewController sysViewCntrl = null;
	
	//First method to traverse All the JSON. The method will call other method to make it recursive and will also extract info. to be saved in mem.
	private static void traverseAll() {
		if (sysViewCntrl.getSubviews().size() > 0) {
			for (Subview s : sysViewCntrl.getSubviews())
				traverseView(s);
		}
	}
	
	//A simple visited map to check if a subview does not get revisited again (probably not required, but who knows we are going for a recursion anyway)..
	static HashMap<Subview, Boolean> _visited = new HashMap<Subview, Boolean>();
	
	//Second method to traverse all JSON. designed in a way that it could be recursively used to extract all subviews or contentViews-Subviews.
	private static void traverseView(Subview s) {
		if (_visited.get(s)==null || _visited.get(s) == Boolean.FALSE) {
			_visited.put(s,  Boolean.TRUE);
			
			SubviewAbstract subOut = new SubviewAbstract(); //The output json serialized object.
			subOut.populate(s);
			String jsonOut = new Gson().toJson(subOut);
			
			//System.out.println(jsonOut); //debug outout!
			
			//PRE-PROCESSING CLASS MATCHES FROM VIEWS
			String class_ = s.getClass_();
			if (outClassMatch.get(class_)==null) {
				ArrayList<String> jsonList = new ArrayList<String>();
				jsonList.add(jsonOut);
				outClassMatch.put(class_, jsonList);
			} else {
				ArrayList<String> jsonList = (ArrayList)outClassMatch.get(class_);
				jsonList.add(jsonOut);
			}
			
			//CHECKING FOR CLASS MATCH IN CONTROL
			if (s.getControl()!=null && s.getControl().getClass_()!=null) {
				String controlClass = s.getControl().getClass_();
				if (outClassMatch.get(controlClass)==null) {
					ArrayList<String> jsonList = new ArrayList<String>();
					jsonList.add(jsonOut);
					outClassMatch.put(controlClass, jsonList);
				} else {
					ArrayList<String> jsonList = (ArrayList)outClassMatch.get(controlClass);
					jsonList.add(jsonOut);
				}
			}
			
			//END PRE-PROCESSING CLASS MATCHES
			
			//PRE PROCESSING CLASSNAME MATCHES
			List<String> classNames = s.getClassNames();
			if (classNames!=null && classNames.size()>0) {
				for (String cname : classNames) {
					if (outClassNamesMatch.get(cname)==null) {
						ArrayList<String> jsonList = new ArrayList<String>();
						jsonList.add(jsonOut);
						outClassNamesMatch.put(cname, jsonList);
					} else {
						ArrayList<String> jsonList = (ArrayList)outClassNamesMatch.get(cname);
						jsonList.add(jsonOut);
					}
				}
			}
			//END PRE PROCESSING CLASSNAME MATCHES
			
			//PRE PROCESSING IDENTIFIER:
			String identifier = s.getIdentifier();
			if (identifier!=null && identifier.length()>0) {
				if (outIdentifierMatch.get(identifier)==null) {
					ArrayList<String> jsonList = new ArrayList<String>();
					jsonList.add(jsonOut);
					outIdentifierMatch.put(identifier, jsonList);
				} else {
					ArrayList<String> jsonList = (ArrayList)outIdentifierMatch.get(identifier);
					jsonList.add(jsonOut);
				}
			}
			//FETCHING IDENTIFIER FROM CONTROL ELEMENT
			if (s.getControl()!=null && s.getControl().getIdentifier()!=null 
					&& s.getControl().getIdentifier().length()>0) {
				String controlIdentifier = s.getControl().getIdentifier();
				if (outIdentifierMatch.get(controlIdentifier)==null) {
					ArrayList<String> jsonList = new ArrayList<String>();
					jsonList.add(jsonOut);
					outIdentifierMatch.put(controlIdentifier, jsonList);
				} else {
					ArrayList<String> jsonList = (ArrayList)outIdentifierMatch.get(controlIdentifier);
					jsonList.add(jsonOut);
				}
			}
			//END PRE PROCESSING IDENTIFIER
			
			
			//READY FOR RECURSION!!!!!!!!
			
			//check for next subviews
			if (s.getSubviews()!=null && s.getSubviews().size() > 0)
				for (Subview nextS : s.getSubviews())
					if (nextS!=null)
						traverseView(nextS);
			//check for content view and subviews.
			if (s.getContentView()!=null && s.getContentView().getSubviews()!=null 
					&& s.getContentView().getSubviews().size()>0) {
				for (Subview nextS : s.getContentView().getSubviews())
					if (nextS!=null)
						traverseView(nextS);
			}
			
		} else {
			//ignore, we have visited the SubView. This could really be a wasted branch!
		}
	}

	//MAIN MAIN MAIN MAIN
	public static void main(String[] args) {
		try {
			//get ready to read user input.
			Scanner reader = new Scanner(System.in);
			
			//loading pojo into mem.
			sysViewCntrl = fetchJSON();
			
			System.out.println("Analyzing JSON Objects...");
			//post process the loaded pojos.
			traverseAll();
			System.out.println("Done");
			
			String userInput = "";
			do {
				System.out.println("Enter Selectable (quit to terminate)");
				userInput = reader.nextLine();
				
				//CLASSNAME SEARCH
				if (userInput.startsWith(".")) {
					List<String> classNameBasedViews = outClassNamesMatch.get(userInput.substring(1));
					if (classNameBasedViews == null || classNameBasedViews.size()==0) {
						System.out.println("Invalid Selectable");
						continue;
					}
					for (String match : classNameBasedViews) {
						System.out.println(match);
					}
				}
				//IDENTIFIER SEARCH
				else if (userInput.startsWith("#")) {
					List<String> idBasedViews = outIdentifierMatch.get(userInput.substring(1));
					if (idBasedViews == null || idBasedViews.size()==0) {
						System.out.println("Invalid Selectable");
						continue;
					}
					for (String match : idBasedViews) {
						System.out.println(match);
					}
				}
				//STANDARD CLASS SEARCH OR COMPOUND SEARCH
				else {
					//standard class based search.
					if (!userInput.contains("#") && !userInput.contains(".")) {
						List<String> classBasedViews = outClassMatch.get(userInput);
						if (classBasedViews == null || classBasedViews.size()==0) {
							System.out.println("Invalid Selectable");
							continue;
						}
						for (String match : classBasedViews) {
							System.out.println(match);
						}
					} else {
						//FORMAT CLASS.CLASSNAME   (no space)
						if (userInput.contains(".")) {
							String[] split = userInput.split("\\.");
							String class_ = split[0];
							String className_ = split[1];
							List<String> matchedClassListViews = outClassMatch.get(class_);
							List<String> matchedClassNameListViews = outClassNamesMatch.get(className_);
							if (matchedClassListViews==null) {
								System.out.println("Invalid Compound Selectable, class not found");
                                                                continue;
                                                        }
							if (matchedClassNameListViews==null) {
								System.out.println("Invalid Compound Selectable, classname not found");
                                                                continue;
						    	}
							
							for (String item : matchedClassListViews) {
								if (matchedClassNameListViews.contains(item))
									System.out.println(item);
							}
						//FORMAT CLASS#IDENTIFIER (no space)
						} else if (userInput.contains("#")) {
							String[] split = userInput.split("#");
							String class_ = split[0];
							String identifier = split[1];
							List<String> matchedClassListViews = outClassMatch.get(class_);
							List<String> matchedIdentifierListViews = outIdentifierMatch.get(identifier);
							if (matchedClassListViews==null) {
								System.out.println("Invalid Compound Selectable, class not found");
								continue;
                                                        }
							if (matchedIdentifierListViews==null) {
								System.out.println("Invalid Compound Selectable, identifier not found");
								continue;
							}
							
							for (String item : matchedClassListViews) {
								if (matchedIdentifierListViews.contains(item))
									System.out.println(item);
							}
						//HA!	
						} else {
							System.out.println("Invalid Selectable");
						}
					}
					
				}
				
			}while (!userInput.equalsIgnoreCase("quit"));
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Sorry! My bad!");
		}
	}

	
	//Method to fetch JSON from URL. The URL is hardcoded and exception is thrown back!
	//ASSUMPTON: JSON processing will be successful.
	static SystemViewController fetchJSON() throws Exception { 
		String responseStr = "";
		//please note: raw.githubusercontent.com
		String url = "https://raw.githubusercontent.com/jdolan/quetoo/master/src/cgame/default/ui/settings/SystemViewController.json";
		HttpGet http_get_obj = new HttpGet(url);
		System.out.println("Fetching JSON from provided URL...");
		HttpResponse response = client.execute(http_get_obj);
		System.out.println("Done");
		responseStr = EntityUtils.toString(response.getEntity());
		//debug
		//System.out.println(responseStr);
		//debug end
		System.out.println("Converting JSON String to Java POJO");
		SystemViewController sysViewCntrl = new Gson().fromJson(responseStr, SystemViewController.class);
		System.out.println("Done");
		return sysViewCntrl;
	}
}

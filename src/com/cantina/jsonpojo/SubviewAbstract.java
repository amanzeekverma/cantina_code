package com.cantina.jsonpojo;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SubviewAbstract {
	@SerializedName("class")
    @Expose
    protected String _class;
	
    @SerializedName("classNames")
    @Expose
    protected List<String> classNames = null;
   
    
    @SerializedName("label")
    @Expose
    protected Label label;
    
    @SerializedName("control")
    @Expose
    protected Control control;
    
    @SerializedName("identifier")
    @Expose
    protected String identifier;
    
    @SerializedName("title")
    @Expose
    protected Title title;
    
    
    
	public void populate(Subview s) {
		this._class = s.getClass_();
		this.label = s.getLabel();
		this.control = s.getControl();
		this.identifier = s.getIdentifier();
		this.title = s.getTitle();
		this.classNames = s.getClassNames();
		
	}
}

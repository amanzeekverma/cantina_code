
package com.cantina.jsonpojo;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang.builder.ToStringBuilder;

public class Subview extends SubviewAbstract {

	 @SerializedName("subviews")
	 @Expose
     private List<Subview> subviews = null;
	 @SerializedName("contentView")
	 @Expose
	 protected ContentView contentView;
    
    public Label getLabel() {
		return label;
	}

	public void setLabel(Label label) {
		this.label = label;
	}

	public ContentView getContentView() {
		return contentView;
	}

	public void setContentView(ContentView contentView) {
		this.contentView = contentView;
	}

	public Control getControl() {
		return control;
	}

	public void setControl(Control control) {
		this.control = control;
	}

	public String getIdentifier() {
		return identifier;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	public Title getTitle() {
		return title;
	}

	public void setTitle(Title title) {
		this.title = title;
	}

	
    
    
    
    public String getClass_() {
        return _class;
    }

    public void setClass_(String _class) {
        this._class = _class;
    }

    public List<String> getClassNames() {
        return classNames;
    }

    public void setClassNames(List<String> classNames) {
        this.classNames = classNames;
    }

    public List<Subview> getSubviews() {
        return subviews;
    }

    public void setSubviews(List<Subview> subviews) {
        this.subviews = subviews;
    }

   /* @Override
    public String toString() {
        return new ToStringBuilder(this).append("_class", _class).append("classNames", classNames).toString();
    }*/

}

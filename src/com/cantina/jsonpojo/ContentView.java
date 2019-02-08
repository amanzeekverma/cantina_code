
package com.cantina.jsonpojo;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang.builder.ToStringBuilder;

public class ContentView {

    @SerializedName("subviews")
    @Expose
    private List<Subview> subviews = null;

    public List<Subview> getSubviews() {
        return subviews;
    }

    public void setSubviews(List<Subview> subviews) {
        this.subviews = subviews;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("subviews", subviews).toString();
    }

}

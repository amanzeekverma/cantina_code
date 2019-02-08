
package com.cantina.jsonpojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang.builder.ToStringBuilder;

public class Label {

    @SerializedName("text")
    @Expose
    private Text text;

    public Text getText() {
        return text;
    }

    public void setText(Text text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("text", text).toString();
    }

}

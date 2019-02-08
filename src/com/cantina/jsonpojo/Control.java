
package com.cantina.jsonpojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang.builder.ToStringBuilder;

public class Control {

    @SerializedName("class")
    @Expose
    private String _class;
    @SerializedName("identifier")
    @Expose
    private String identifier;
    @SerializedName("var")
    @Expose
    private String var;
    @SerializedName("min")
    @Expose
    private Number min;
    @SerializedName("max")
    @Expose
    private Number max;
    @SerializedName("step")
    @Expose
    private Integer step;
    @SerializedName("expectsStringValue")
    @Expose
    private Boolean expectsStringValue;

    public String getClass_() {
        return _class;
    }

    public void setClass_(String _class) {
        this._class = _class;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getVar() {
        return var;
    }

    public void setVar(String var) {
        this.var = var;
    }

    public Number getMin() {
        return min;
    }

    public void setMin(Integer min) {
        this.min = min;
    }

    public Number getMax() {
        return max;
    }

    public void setMax(Integer max) {
        this.max = max;
    }

    public Integer getStep() {
        return step;
    }

    public void setStep(Integer step) {
        this.step = step;
    }

    public Boolean getExpectsStringValue() {
        return expectsStringValue;
    }

    public void setExpectsStringValue(Boolean expectsStringValue) {
        this.expectsStringValue = expectsStringValue;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("_class", _class).append("identifier", identifier).append("var", var).append("min", min).append("max", max).append("step", step).append("expectsStringValue", expectsStringValue).toString();
    }

}

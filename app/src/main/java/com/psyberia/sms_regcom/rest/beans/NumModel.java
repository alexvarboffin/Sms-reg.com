package com.psyberia.sms_regcom.rest.beans;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.psyberia.sms_regcom.rest.badbackend.BaseTypeModel;

/**
 * Created by combo on 21.03.2017.
 */

public class NumModel extends BaseTypeModel {
    //@SerializedName("response")
    //@Expose
    //private String responseu;
    @SerializedName("tzid")
    @Expose
    private String tzid;

    public String getTzid() {
        return tzid;
    }

    public void setTzid(String tzid) {
        this.tzid = tzid;
    }

    @Override
    public String toString() {
        return "ClassPojo [response = " + response + ", tzid = " + tzid + "]";
    }
}

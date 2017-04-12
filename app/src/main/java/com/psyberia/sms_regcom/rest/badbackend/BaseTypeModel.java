package com.psyberia.sms_regcom.rest.badbackend;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by combo on 09.04.2017.
 */

public class BaseTypeModel {
    @SerializedName("response")
    @Expose
    protected String response;

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

}

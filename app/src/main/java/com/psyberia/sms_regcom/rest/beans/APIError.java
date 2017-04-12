package com.psyberia.sms_regcom.rest.beans;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.psyberia.sms_regcom.rest.badbackend.BaseTypeModel;

/**
 * Created by combo on 24.03.2017.
 * <p>
 * {"response":"ERROR","error_msg":"setReady to this TZID not applicable"}
 */

public class APIError extends BaseTypeModel {
    @SerializedName("error_msg")
    @Expose
    private String errorMsg;

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    @Override
    public String toString() {
        return "ClassPojo [response = " + response + ", error_msg = " + errorMsg + "]";
    }
}

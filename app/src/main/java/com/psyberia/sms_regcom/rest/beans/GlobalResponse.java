package com.psyberia.sms_regcom.rest.beans;

import com.psyberia.sms_regcom.rest.badbackend.BaseTypeModel;

/**
 * Created by combo on 24.03.2017.
 * <p>
 * Метод setOperationOver
 */

public class GlobalResponse extends BaseTypeModel {
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

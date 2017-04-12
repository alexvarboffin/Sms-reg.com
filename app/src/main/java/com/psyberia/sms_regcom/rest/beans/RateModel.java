package com.psyberia.sms_regcom.rest.beans;

import com.psyberia.sms_regcom.rest.badbackend.BaseTypeModel;

/**
 * F5F8FF
 * Created by combo on 24.03.2017.
 */

public class RateModel extends BaseTypeModel {

    private String rate;

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    @Override
    public String toString() {
        return "ClassPojo [response = " + response + ", rate = " + rate + "]";
    }
}

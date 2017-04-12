package com.psyberia.sms_regcom.rest.beans;

import com.psyberia.sms_regcom.rest.badbackend.BaseTypeModel;

/**
 * Created by combo on 21.03.2017.
 */

public class Balance extends BaseTypeModel {

    private String balance;

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    @Override
    public String toString() {
        return "ClassPojo [response = " + response + ", balance = " + balance + "]";
    }
}

package com.psyberia.sms_regcom.rest.beans;

import com.psyberia.sms_regcom.rest.badbackend.BaseTypeModel;

/**
 * Created by combo on 24.03.2017.
 */

public class OrderAddModel extends BaseTypeModel {

    private String order_id;

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    @Override
    public String toString() {
        return "ClassPojo [response = " + response + ", order_id = " + order_id + "]";
    }
}


package com.psyberia.sms_regcom.ui.ui;

/**
 * Created by combo on 10.04.2017.
 */

public class SpinnerHelper {
    private String key;
    private String value;

    public SpinnerHelper(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}

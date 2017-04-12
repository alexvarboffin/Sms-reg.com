package com.psyberia.sms_regcom.rest.beans;

/**
 * Created by combo on 24.03.2017.
 */

public class VsimGetModel {
    private String response;
    private String stop;
    private String start;
    private String number;

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public String getStop() {
        return stop;
    }

    public void setStop(String stop) {
        this.stop = stop;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return "ClassPojo [response = " + response + ", stop = " + stop + ", start = " + start
                + ", number = " + number + "]";
    }
}

package com.psyberia.sms_regcom.rest.badbackend;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.psyberia.sms_regcom.rest.beans.APIError;
import com.psyberia.sms_regcom.rest.beans.OperationBean;

import java.lang.reflect.Type;

public class BadBackendResponseDeserializer/*<T>*/ implements JsonDeserializer<BadBackendResponse> {

    // register all adapters you need
    private Gson gson = new GsonBuilder()
            //.registerTypeAdapter(OperationsResponse.class, null)
            .create();

    @Override
    public BadBackendResponse deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        if (json instanceof JsonObject) {
            final JsonObject jobject = json.getAsJsonObject();//new JsonObject(jelement.getAsJsonObject());
            System.out.println("-- " + jobject);
            if (jobject.has("error_msg")) {
                APIError error = gson.fromJson(/*resp*/jobject, APIError.class);
                return new BadBackendResponse(null, error);
            } else {
                return gson.fromJson(/*resp*/json, typeOfT);
            }
        } else if (json instanceof JsonArray) {
            JsonArray jarray = json.getAsJsonArray();
            System.out.println("##" + jarray);

            OperationBean[] data = gson.fromJson(/*resp*/jarray, OperationBean[].class);
            return new BadBackendResponse(data, null);
        }
        System.out.println("-----------------");

        return new BadBackendResponse(null, null);

    }
}

package com.psyberia.sms_regcom.rest.badbackend;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.psyberia.sms_regcom.rest.beans.APIError;

import java.lang.reflect.Type;

/**
 * Created by combo on 09.04.2017.
 */

public class SetReadyModelDeserializer implements JsonDeserializer<BaseTypeModel> {

    GsonBuilder gsonBuilder = new GsonBuilder();
    //gsonBuilder.registerTypeAdapter(MyBaseTypeModel.class, new MyTypeModelDeserializer());
    Gson gson = gsonBuilder.create();

    @Override
    public BaseTypeModel deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {

        //typeOfT == beans.ReadyModel

        BaseTypeModel typeModel = null;

        if (json instanceof JsonObject) {
            final JsonObject jsonObject = json.getAsJsonObject();
            if (jsonObject.has("error_msg")) {
                typeModel = gson.fromJson(json, APIError.class);
            } else {
                typeModel = gson.fromJson(json, typeOfT);
            }
        }

        return typeModel;
    }
}

package com.psyberia.sms_regcom;

import android.app.Application;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.preference.PreferenceManager;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.psyberia.sms_regcom.helper.PreferencesHelper;
import com.psyberia.sms_regcom.rest.APIService;
import com.psyberia.sms_regcom.rest.badbackend.BadBackendResponse;
import com.psyberia.sms_regcom.rest.badbackend.BadBackendResponseDeserializer;
import com.psyberia.sms_regcom.rest.badbackend.SetReadyModelDeserializer;
import com.psyberia.sms_regcom.rest.beans.Balance;
import com.psyberia.sms_regcom.rest.beans.GlobalResponse;
import com.psyberia.sms_regcom.rest.beans.NumModel;
import com.psyberia.sms_regcom.rest.beans.OrderAddModel;
import com.psyberia.sms_regcom.rest.beans.ReadyModel;
import com.psyberia.sms_regcom.sdk.DLog;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by combo on 07.04.2017.
 */

public class MyApplication extends Application {

    public static final boolean DEBUG = false;
    public static SharedPreferences sSharedPreferences;

    //======================================================
    private static APIService api;
    private static Retrofit retrofit;
    private static String sApiKey;
    //======================================================
    //BadBackend
    BadBackendResponseDeserializer deserializer = new BadBackendResponseDeserializer();
    Gson gson = new GsonBuilder()
            //.registerTypeAdapter(BadBackendResponse.class, deserializer)
            //.registerTypeAdapter(OperationBean.class, deserializer)

            .registerTypeAdapter(BadBackendResponse.class, deserializer)
            .registerTypeAdapter(ReadyModel.class, new SetReadyModelDeserializer())//BaseTypeModel
            .registerTypeAdapter(GlobalResponse.class, new SetReadyModelDeserializer())
            .registerTypeAdapter(Balance.class, new SetReadyModelDeserializer())
            .registerTypeAdapter(OrderAddModel.class, new SetReadyModelDeserializer())
            .registerTypeAdapter(NumModel.class, new SetReadyModelDeserializer())
            .create();

    public static SharedPreferences getSharedPreferences() {
        return sSharedPreferences;
    }

    public static void updateKey() {
        sApiKey = PreferencesHelper.getInstance().getApiKey();
    }

    public static APIService getApi() {
        return api;
    }

    public static Retrofit retrofit() {
        return retrofit;
    }

    // Called when the application is starting, before any other application objects have been created.
    // Overriding this method is totally optional!
    @Override
    public void onCreate() {
        super.onCreate();
        sSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        initRetrofit();
    }

    private void initRetrofit() {
        updateKey();
        OkHttpClient client = new OkHttpClient.Builder()
                .readTimeout(10, TimeUnit.SECONDS)
                .connectTimeout(5, TimeUnit.SECONDS)

                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {

                     /*   String uid = "0";
                        long timestamp = (int) (Calendar.getInstance().getTimeInMillis() / 1000);
                        String signature = MD5Util.crypt(timestamp + "" + uid + MD5_SIGN);
                        String base64encode = signature + ":" + timestamp + ":" + uid;
                        base64encode = Base64.encodeToString(base64encode.getBytes(), Base64.NO_WRAP | Base64.URL_SAFE);
*/
                        Request request = chain.request();


                        //System.out.println(String.format("\nrequest:\n%s\nheaders:\n%s",
                        //        request.body().toString(), request.headers()));

                        HttpUrl url = request.url()

                                .newBuilder()
                                .addQueryParameter("apikey", sApiKey)
                                .addQueryParameter("appid", getString(R.string.app_name))
                                //.addQueryParameter("method", "getAliasList")
                                .build();


                        request = request
                                .newBuilder()
                                //.addHeader("Authorization", "zui " + base64encode)
                                .addHeader("from_client", "sms-reg")
                                .url(url)
                                .build();

                        DLog.d("REST: " + url.toString());
                        return chain.proceed(request);
                    }
                }).build();


        retrofit = new Retrofit.Builder()
                .baseUrl("http://api.sms-reg.com")
                .addConverterFactory(GsonConverterFactory.create(

                        gson //BadBackend

                        //new GsonBuilder().create()
                        //Исключает все непомеченное как @Expose
                        //new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create()
                )) //Конвертер, необходимый для преобразова

                .client(client)

                .build();

        api = retrofit.create(APIService.class);
    }

    // Called by the system when the device configuration changes while your component is running.
    // Overriding this method is totally optional!
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    // This is called when the overall system is running low on memory,
    // and would like actively running processes to tighten their belts.
    // Overriding this method is totally optional!
    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

}

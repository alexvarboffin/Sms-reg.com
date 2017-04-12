package com.psyberia.sms_regcom.sdk;

import android.util.Log;

import com.psyberia.sms_regcom.MyApplication;


/**
 * Created by combo on 18.12.2016.
 */

public class DLog {
    private static String TAG = "####";

    // █  ▄| 

    /**
     * Log Level Error
     **/
    public static final void e(String message) {
        if (MyApplication.DEBUG) {
            Log.e(TAG, buildLogMsg(message));
        }
    }

    /**
     * Log Level Warning
     **/
    public static final void w(String message) {
        if (MyApplication.DEBUG) Log.w(TAG, buildLogMsg(message));
    }

    /**
     * Log Level Information
     **/
    public static final void i(String message) {
        if (MyApplication.DEBUG) Log.i(TAG, buildLogMsg(message));
    }

    /**
     * Log Level Debug
     **/
    public static final void d(String message) {
        if (MyApplication.DEBUG) Log.d(TAG, buildLogMsg(message));
    }

    /**
     * Log Level Verbose
     **/
    public static final void v(String message) {
        if (MyApplication.DEBUG) Log.v(TAG, buildLogMsg(message));
    }


    public static String buildLogMsg(String message) {

        StackTraceElement ste = Thread.currentThread().getStackTrace()[4];

        StringBuilder sb = new StringBuilder();


        //Class<? extends StackTraceElement> obj = ste.getClass();
        //if (ste.getClass() instanceof Fragment) {
        //    sb.append("#");
        //}
        sb.append("█ ");
        sb.append(ste.getFileName().replace(".java", ""));
        sb.append("::");
        sb.append(ste.getMethodName());
        sb.append(" ● ");
        sb.append(message);
        sb.append(" █");

        return sb.toString();

    }
}
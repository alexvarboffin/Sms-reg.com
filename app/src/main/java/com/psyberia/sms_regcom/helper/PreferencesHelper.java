package com.psyberia.sms_regcom.helper;

import android.content.SharedPreferences;

import com.psyberia.sms_regcom.MyApplication;

/**
 * Created by combo on 29.03.2017.
 */

public class PreferencesHelper {
    private static PreferencesHelper INSTANCE = null;
    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;

    private PreferencesHelper() {
        this.mSharedPreferences = MyApplication.getSharedPreferences();
        this.mEditor = this.mSharedPreferences.edit();
    }

    public static PreferencesHelper getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new PreferencesHelper();
        }
        return INSTANCE;
    }

    //для примера сохранение данных в SharedPreferences
    public void saveStatusCheckbox(String name, boolean status) {
        mEditor.putBoolean(name, status);
        mEditor.apply();
    }

    public float getRate() {
        return mSharedPreferences.getFloat("rate", 0.0f);
    }

    public void setRate(float v) {
        mEditor.putFloat("rate", v).apply();
    }

    public String getApiKey() {
        return this.mSharedPreferences.getString("ak", "");
    }

    public void setApiKey(String s) {
        mEditor.putString("ak", s).apply();
        MyApplication.updateKey();
    }

    //для примера загрузка из данных из SharedPreferences
    //public int loadStatusCheckbox(String name){
    //    return mSharedPreferences.getBoolean(name, false);
    //}
}

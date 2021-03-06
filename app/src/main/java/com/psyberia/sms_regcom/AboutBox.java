package com.psyberia.sms_regcom;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v7.app.AlertDialog;
import android.text.SpannableString;
import android.text.util.Linkify;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by combo on 14.04.2017.
 */

class AboutBox {
    private static String VersionName(Context context) {
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            return "Unknown";
        }
    }

    static void Show(Activity activity) {
        //Use a Spannable to allow for links highlighting
        SpannableString aboutText = new SpannableString("Version " + VersionName(activity)
                + "\n\n"
                + activity.getString(R.string.about));
        //Generate views to pass to AlertDialog.Builder and to set the text
        View about;
        TextView tvAbout;
        try {
            //Inflate the custom view
            LayoutInflater inflater = activity.getLayoutInflater();
            about = inflater.inflate(R.layout.aboutbox, (ViewGroup) activity.findViewById(R.id.aboutView));
            tvAbout = (TextView) about.findViewById(R.id.aboutText);
        } catch (InflateException e) {
            //Inflater can throw exception, unlikely but default to TextView if it occurs
            about = tvAbout = new TextView(activity);
        }
        //Set the about text
        tvAbout.setText(aboutText);
        // Now Linkify the text
        Linkify.addLinks(tvAbout, Linkify.ALL);
        //Build and show the dialog
        new AlertDialog.Builder(activity)
                .setTitle("About " + activity.getString(R.string.app_name))
                .setCancelable(true)
                .setIcon(R.mipmap.ic_launcher)
                .setPositiveButton(android.R.string.ok, null)
                .setView(about)
                .show();    //Builder method returns allow for method chaining
    }
}
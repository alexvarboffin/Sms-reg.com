package com.psyberia.sms_regcom.ui;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import com.psyberia.sms_regcom.R;


/**
 * Created by combo on 27.03.2017.
 */

public class DialogSelectCountry extends AlertDialog.Builder {

    public DialogSelectCountry(Context context) {
        super(context);
        setTitle("Выбрать страну");
        setIcon(R.mipmap.ic_launcher);
        setItems(R.array.country_names, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
    }
}

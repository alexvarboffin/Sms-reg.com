package com.psyberia.sms_regcom;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.widget.Toast;

/**
 * Created by combo on 29.03.2017.
 */

public class Utils {
/*    public static final String EXTRA_KEY_OUT = "EXTRA_OUT";
    public static final String ACTION_MYINTENTSERVICE = "ru.timgor.alerttest.RESPONSE";
    public static final String TAG = "AlertTest";
    public static final String SUCCESS = "success";
    public static final String AUTOMATIC = "chbAutomatic";
*/

    public static void copyToClipboard(Context context, String msg) {
        ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("label", msg);
        clipboard.setPrimaryClip(clip);
        Toast.makeText(context, "Скопированно в буфер обмена: " + msg, Toast.LENGTH_SHORT).show();
    }
}

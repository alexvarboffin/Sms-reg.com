package com.psyberia.sms_regcom;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

/**
 * Created by combo on 24.12.2016.
 * <p>
 * <p>
 * <p>
 * >adb shell
 * # su
 * # chmod 777 /data/data/com.me.app/databases/data.dao
 */

class AdUtil {
    String banner_ad_unit_id = "ca-app-pub-5111357348858303/9224001474";
    private String application_id = "ca-app-pub-5111357348858303~7747268278";

    View getAdView(Context context) {
        MobileAds.initialize(context, application_id);
        LinearLayout layout = new LinearLayout(context);
        layout.setOrientation(LinearLayout.VERTICAL);
        //layout.setBackgroundColor(context.getResources().getColor(R.color.AQUA));

        //mAdView = (AdView) findViewById(R.id.ad_view);
        // Create a banner ad. The ad size and ad unit ID must be set before calling loadAd.
        AdView mAdView = new AdView(context);
        mAdView.setAdSize(AdSize.SMART_BANNER);//BANNER
        mAdView.setAdUnitId(banner_ad_unit_id);

        //Следуйте руководству по интеграции SDK. При внедрении кода укажите тип объявления,
        //его размер и место размещения.
        //Идентификатор приложения: ca-app-pub-9067624429199412~6821692688
        //Идентификатор рекламного блока: ca-app-pub-9067624429199412/5818977482

        //<string name="banner_ad_unit_id">ca-app-pub-3940256099942544/6300978111</string>

        // Create an ad request. Check logcat output for the hashed device ID to
        // get test ads on a physical device. e.g.
        // "Use AdRequest.Builder.addTestDevice("ABCDEF012345") to get test ads
        // on this device."
        AdRequest.Builder adRequest = new AdRequest.Builder()
                //.addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                //.addTestDevice("4D01657EE12797A512F48F4177D1D7B1")
                //.addTestDevice("0D2A5ED7525433C1F516A9C73E0BC6B8")
                .addTestDevice("7D2FA7A23F13AE4D002EA0B0366F8E43");//wize l3
        //.addTestDevice("1234567");
        layout.addView(mAdView);


        // Start loading the ad in the background.
        mAdView.loadAd(adRequest.build());
        return layout;
    }
}

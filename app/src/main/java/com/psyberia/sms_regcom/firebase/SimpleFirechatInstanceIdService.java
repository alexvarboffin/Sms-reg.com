package com.psyberia.sms_regcom.firebase;

import com.google.firebase.iid.FirebaseInstanceIdService;

public class SimpleFirechatInstanceIdService extends FirebaseInstanceIdService {


    private static final String CHAT_ENGAGE_TOPIC = "chat_engage";

    public void onTokenRefresh() {
        //       String token = FirebaseInstanceId.getInstance().getToken();
//        FirebaseMessaging.getInstance().subscribeToTopic(CHAT_ENGAGE_TOPIC);
    }

}
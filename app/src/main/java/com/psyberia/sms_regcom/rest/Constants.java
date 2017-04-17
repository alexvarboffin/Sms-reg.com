package com.psyberia.sms_regcom.rest;


import java.util.HashMap;

import static com.psyberia.sms_regcom.rest.Constants.State.TZ_INPOOL;
import static com.psyberia.sms_regcom.rest.Constants.State.TZ_NUM_ANSWER;
import static com.psyberia.sms_regcom.rest.Constants.State.TZ_NUM_PREPARE;
import static com.psyberia.sms_regcom.rest.Constants.State.TZ_NUM_WAIT;
import static com.psyberia.sms_regcom.rest.Constants.State.TZ_OVER_EMPTY;
import static com.psyberia.sms_regcom.rest.Constants.State.TZ_OVER_NR;
import static com.psyberia.sms_regcom.rest.Constants.State.TZ_OVER_OK;

/**
 * Created by combo on 27.03.2017.
 */

public class Constants {
    public static HashMap<String, String> hm = new HashMap<>();

    static {
        hm.put(TZ_INPOOL, "Операция ожидает выделения номера");//<1 ---------------------------
        hm.put(TZ_NUM_PREPARE, "Выдан номер, ожидается выполнение метода SetReady");//<2

        //setready


        hm.put(TZ_NUM_WAIT, "Ожидается ответ");//3

        //Регистрируюсь

        hm.put(TZ_NUM_ANSWER, "Поступил ответ");

        //ввожу код

        hm.put("TZ_NUM_WAIT2", "Ожидается уточнение полученного кода");
        hm.put("TZ_NUM_ANSWER2", "Поступил ответ после уточнения");
        hm.put("WARNING_NO_NUMS", "Нету подходящих номеров");

        //Также если время по операции уже истекло то получите следующие значения:";
        hm.put(TZ_OVER_OK, "Операция завершена");
        hm.put("TZ_OVER_GR", "Операция отмечена как ошибочная");

        hm.put(TZ_OVER_NR, "Вы не отправили запрос методом setReady");//<---- 1

        hm.put(TZ_OVER_EMPTY, "Ответ не поступил за отведенное время");//<--- 2

        hm.put("TZ_OVER2_EMPTY", "Уточнение не поступило за отведенное время");
        hm.put("TZ_OVER2_OK", "Операция завершена после уточнения");
        hm.put("TZ_DELETED", "Операция удалена, средства возвращены");
    }

    public class State {
        public static final String TZ_INPOOL = "TZ_INPOOL";
        public static final String TZ_NUM_PREPARE = "TZ_NUM_PREPARE";

        public static final String TZ_NUM_WAIT = "TZ_NUM_WAIT";
        public static final String TZ_NUM_ANSWER = "TZ_NUM_ANSWER";


        //timeout
        public static final String TZ_OVER_NR = "TZ_OVER_NR";
        public static final String TZ_OVER_EMPTY = "TZ_OVER_EMPTY";

        public static final String TZ_OVER_OK = "TZ_OVER_OK";
    }
}

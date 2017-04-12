package com.psyberia.sms_regcom.rest;

import com.psyberia.sms_regcom.rest.badbackend.BadBackendResponse;
import com.psyberia.sms_regcom.rest.beans.Balance;
import com.psyberia.sms_regcom.rest.beans.GetStateModel;
import com.psyberia.sms_regcom.rest.beans.GlobalResponse;
import com.psyberia.sms_regcom.rest.beans.ListOrders;
import com.psyberia.sms_regcom.rest.beans.NumModel;
import com.psyberia.sms_regcom.rest.beans.OperationBean;
import com.psyberia.sms_regcom.rest.beans.OrderAccOkModel;
import com.psyberia.sms_regcom.rest.beans.OrderAccReviseModel;
import com.psyberia.sms_regcom.rest.beans.OrderAddModel;
import com.psyberia.sms_regcom.rest.beans.RateModel;
import com.psyberia.sms_regcom.rest.beans.ReadyModel;
import com.psyberia.sms_regcom.rest.beans.VsimGetModel;
import com.psyberia.sms_regcom.rest.beans.VsimGetSMSModel;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/**
 * Created by combo on 07.04.2017.
 */

public interface APIService {
    String getNumRepeatOffline = "getNumRepeatOffline";

    //Работа с номерами для активаций
    @GET("/getNum.php")
    Call<NumModel> getNum(
            @QueryMap Map<String, String> options
            //@Query("country") String country,//all ru
            //@Query("service") String service

    );//Cоздает операцию на использование виртуального номера.
    //Сообщает что вы отправили смс на номер и готовы получить код поступивший в этом смс .

    @GET("/setReady.php")
    Call<ReadyModel> setReady(
            @Query("tzid") String tzid
    );

    @GET("/getState.php")
    Call<GetStateModel> getState(
            @Query("tzid") String tzid
    );//Позволяет получить информацию о состоянии операции.

    //
    @GET("/getOperations.php")
    Call<BadBackendResponse<OperationBean[]>> getOperations(
            @Query("opstate") String opstate,//active | completed
            @Query("count") int count//100 ... 1000
    );//Возвращает список ваших операций и их состояние.
    //Отправляет уведомление об успешном получении кода.

    @GET("/setOperationOk.php")
    Call<GlobalResponse> setOperationOk(
            @Query("tzid") String tzid
    );

    @GET("/setOperationRevise.php")
    Call<GlobalResponse> setOperationRevise(
            @Query("tzid") String tzid
    );//Создает запрос на уточнение правильности кода.
    //Завершает операцию с уведомлением о неверном коде.

    @GET("/setOperationOver.php")
    Call<GlobalResponse> setOperationOver(
            @Query("tzid") String tzid
    );

    @GET("/getNumRepeat.php")
    Call<GlobalResponse> getNumRepeat(
            @Query("tzid") int tzid
    );//Cоздает операцию на повторное использование ранее использованного номера.
    //Cоздает заказ на оффлайн-повтор по ранее использованному номеру.

    @GET("/setOperationUsed.php")
    Call<GlobalResponse> setOperationUsed(
            @Query("tzid") String tzid
    );
    //Сообщает, что выданный номер уже использован или заблокирован
    // в сервисе для которого запрашивалась активация.

    //==========================================================================================
    //Работа с VirtualSiM
    @GET("/vsimGet.php")
    Call<VsimGetModel> vsimGet(
            @Query("country") String country,
            @Query("period") String period
    );
    //Создает новый заказ на аренду виртуального номера VirtualSiM.

    @GET("/vsimGetSMS.php")
    Call<VsimGetSMSModel> vsimGetSMS(
            @Query("number") String number
    );//Возвращает список полученных SMS на арендуемый номер.


    //===========================================================================================
    //<----------- tab2
    //Работа с заказами
    @POST("/orderAdd.php")
    Call<OrderAddModel> orderAdd(
            @QueryMap Map<String, String> options
            /*
            @Query("count") int count,
            @Query("country") String country,
            @Query("service") String service,
            @Query("options") String on,

            @Query("name") String name,
            @Query("age") int age,
            @Query("gender") String gender,
            @Query("city") String[] city
    */
    );//Создает новый заказ на регистрацию готовых аккаунтов.

    @GET("/orderGetByID.php")
    Call<GlobalResponse> orderGetByID(
            @Query("order_id") int order_id//Идентификатор заказа.
    );//Возвращает список аккаунтов из заказа.


    //  - not
    @GET("/listOrders.php")
    Call<ListOrders> listOrders(
            @Query("count") int count //default 10
    );//Возвращает список заказов и информацию по ним.

    //  - not
    @GET("/setOrderAccOk.php")
    Call<OrderAccOkModel> setOrderAccOk(
            @Query("histid") int histid
    );

    //Отправляет подтверждение о правильности принятого аккаунта.
    //  - not
    @GET("/setOrderAccRevise.php")
    Call<OrderAccReviseModel> setOrderAccRevise(
            @Query("histid") int histid
    );
    //Создает запрос на уточнение данных по аккаунту.


    //=====================================================
    //Аккаунт
    // Возвращает информацию о состоянии баланса.
    @GET("/getBalance.php")
    Call<Balance> getBalance();


    @GET("/setRate.php")
    Call<RateModel> setRate(
            @Query("rate") float rate
    );
    //Устанавливает новое значение персональной ставки.
}
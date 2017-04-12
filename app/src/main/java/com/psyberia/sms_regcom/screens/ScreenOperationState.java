package com.psyberia.sms_regcom.screens;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.psyberia.sms_regcom.MyApplication;
import com.psyberia.sms_regcom.R;
import com.psyberia.sms_regcom.Utils;
import com.psyberia.sms_regcom.rest.APIService;
import com.psyberia.sms_regcom.rest.Constants;
import com.psyberia.sms_regcom.rest.badbackend.BaseTypeModel;
import com.psyberia.sms_regcom.rest.beans.APIError;
import com.psyberia.sms_regcom.rest.beans.GetStateModel;
import com.psyberia.sms_regcom.rest.beans.GlobalResponse;
import com.psyberia.sms_regcom.rest.beans.ReadyModel;
import com.psyberia.sms_regcom.sdk.BaseFragment;
import com.psyberia.sms_regcom.sdk.ChildItemClickListener;
import com.psyberia.sms_regcom.sdk.DLog;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Optional;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.psyberia.sms_regcom.rest.Constants.State.TZ_OVER_OK;
import static com.psyberia.sms_regcom.rest.Constants.hm;

/**
 * Created by combo on 27.03.2017.
 */

public class ScreenOperationState extends BaseFragment
        implements ChildItemClickListener {


    private static String ARG_TZID = "tzid";
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.btn_action_set_ready)
    Button btnSetReady;
    @BindView(R.id.btn_action_set_operation_revise)
    Button btnSteOperRevise;
    @BindView(R.id.btn_action_get_num_repeat)
    Button btnGetNumRepeat;
    @BindView(R.id.btn_action_set_operation_over)
    Button btnSetOperOver;
    @BindView(R.id.btn_action_set_operation_used)
    Button btnOperUsed;
    @BindView(R.id.btn_action_set_operation_ok)
    Button btnOperOk;
    @BindView(R.id.tv_number_title)
    TextView tvNumTitle;
    @BindView(R.id.tv_msg_title)
    TextView tvMsgTitle;
    @BindView(R.id.tv_response)
    TextView tvResponse;
    @BindView(R.id.tv_service)
    TextView tvService;
    @BindView(R.id.tv_number)
    TextView tvNumber;
    @BindView(R.id.tv_msg)
    TextView tvMsg;
    private String tzid;
    private Callback<GetStateModel> operationsCallback = new Callback<GetStateModel>() {
        @Override
        public void onResponse(Call<GetStateModel> call, Response<GetStateModel> response) {
            if (response.isSuccessful()) {
                GetStateModel data = response.body();

                tvResponse.setText("Response: " + hm.get(data.getResponse()));
                tvService.setText("Сервис: " + data.getService());

                if (data.getNumber() == null) {
                    tvNumTitle.setVisibility(View.GONE);
                    tvNumber.setVisibility(View.GONE);
                } else {
                    tvNumTitle.setVisibility(View.VISIBLE);
                    tvNumber.setVisibility(View.VISIBLE);
                    tvNumber.setText(data.getNumber());//"Выделенный номер: "
                }


                if (data.getMsg() == null) {
                    tvMsgTitle.setVisibility(View.GONE);
                    tvMsg.setVisibility(View.GONE);
                } else {
                    tvMsgTitle.setVisibility(View.VISIBLE);
                    tvMsg.setVisibility(View.VISIBLE);
                    tvMsg.setText(data.getMsg());//"Выделенный номер: "
                }
                DLog.d(response.toString());
                switchState(data.getResponse());
            }
        }

        @Override
        public void onFailure(Call<GetStateModel> call, Throwable t) {

        }
    };
    private Callback<GlobalResponse> globalModelCallback0 = new Callback<GlobalResponse>() {
        @Override
        public void onResponse(Call<GlobalResponse> call, Response<GlobalResponse> response) {
            if (response.isSuccessful()) {
                BaseTypeModel bm = response.body();

                if (bm instanceof APIError) {
                    Toast.makeText(getContext(), ((APIError) bm).getErrorMsg(), Toast.LENGTH_SHORT).show();
                } else if (bm instanceof GlobalResponse) {
                    //success
                }
            }
        }

        @Override
        public void onFailure(Call<GlobalResponse> call, Throwable t) {

        }
    };
    private Callback<GlobalResponse> globalModelCallback1 = new Callback<GlobalResponse>() {
        @Override
        public void onResponse(Call<GlobalResponse> call, Response<GlobalResponse> response) {
            if (response.isSuccessful()) {
                BaseTypeModel bm = response.body();

                if (bm instanceof APIError) {
                    /*
    0 — повтор по указанной операции невозможен;
    1 — запрос выполнен успешно;
    2 — номер оффлайн, используйте метод getNumRepeatOffline;
    3 — Этот номер сейчас занят. Попробуйте позже.

NEWTZID = id новой операции операции.*/
                    Toast.makeText(getContext(), ((APIError) bm).getErrorMsg(), Toast.LENGTH_SHORT).show();
                } else if (bm != null) {

                    GlobalResponse bean = (GlobalResponse) bm;
                    String resp = bean.getResponse();

                    if (null != bean.getTzid()) {
                        //работаем с тзид
                    } else {
                        String[] arr = getResources().getStringArray(R.array.err_get_num_repeat);
                        int index = Integer.parseInt(bean.getResponse());
                        Toast.makeText(getContext(), arr[index], Toast.LENGTH_SHORT).show();
                    }

                }
            }
        }

        @Override
        public void onFailure(Call<GlobalResponse> call, Throwable t) {

        }
    };
    //
    private Callback<GlobalResponse> globalModelCallback2 = new Callback<GlobalResponse>() {
        @Override
        public void onResponse(Call<GlobalResponse> call, Response<GlobalResponse> response) {
            if (response.isSuccessful()) {
                BaseTypeModel bm = response.body();

                if (bm instanceof APIError) {
                    Toast.makeText(getContext(), ((APIError) bm).getErrorMsg(), Toast.LENGTH_SHORT).show();
                } else if (bm != null) {

                    GlobalResponse bean = (GlobalResponse) bm;
                    String resp = bean.getResponse();

                    if (null != bean.getTzid()) {
                        //работаем с тзид
                        //response=1

                        Toast.makeText(getContext(), "Запрос выполнен успешно!", Toast.LENGTH_SHORT).show();
                    } else {
                        //Toast.makeText(getContext(), arr[index], Toast.LENGTH_SHORT).show();
                    }

                }
            }
        }

        @Override
        public void onFailure(Call<GlobalResponse> call, Throwable t) {

        }
    };
    private Callback<ReadyModel> setReadyCallback = new Callback<ReadyModel>() {
        @Override
        public void onResponse(Call<ReadyModel> call, Response<ReadyModel> response) {
            if (response.isSuccessful()) {
                BaseTypeModel bm = response.body();

                if (bm instanceof APIError) {
                    Toast.makeText(getContext(), ((APIError) bm).getErrorMsg(), Toast.LENGTH_SHORT).show();
                } else if (bm != null) {
                    //{response: 1 }
                    ReadyModel been = (ReadyModel) bm;
                    if (been.getResponse().equals("1")) {
                        Toast.makeText(getContext(), "Запрос выполнен успешно!", Toast.LENGTH_SHORT).show();
                    }
                }

                /*if("ERROR".equals(readyModel.getResponse())){
                    APIError error = ErrorUtils.parseError(response, MyApplication.retrofit());
                    DLog.d("###" + error.toString());
                }*/
            }
        }

        @Override
        public void onFailure(Call<ReadyModel> call, Throwable t) {

        }
    };
    private APIService api;

    public static ScreenOperationState newInstance(String tzid) {
        ScreenOperationState fragment = new ScreenOperationState();
        Bundle args = new Bundle();
        args.putString(ARG_TZID, tzid);
        fragment.setArguments(args);
        return fragment;
    }

    private void switchState(String state) {
        switch (state) {
            case Constants.State.TZ_INPOOL:
                enableBtn(new Button[]{
                        btnSetReady,
                        btnSteOperRevise,
                        btnGetNumRepeat,
                        btnSetOperOver,
                        btnOperUsed,
                        btnOperOk
                }, false);
                //all false
                break;

            case Constants.State.TZ_NUM_PREPARE:

                enableBtn(new Button[]{
                        //btnSetReady,
                        btnSteOperRevise,
                        btnGetNumRepeat,
                        btnSetOperOver,
                        btnOperUsed,
                        btnOperOk
                }, false);

                enableBtn(new Button[]{
                        btnSetReady
                }, true);
                break;

            case Constants.State.TZ_NUM_WAIT:

                //Сервис ждет смс
                enableBtn(new Button[]{
                        btnSetReady,
                        btnSteOperRevise,
                        btnGetNumRepeat,
                        btnSetOperOver,
                        btnOperUsed,
                        btnOperOk
                }, false);

                //Тут нужно запросить у сайта смс....
                break;

            case Constants.State.TZ_NUM_ANSWER:
                enableBtn(new Button[]{
                        btnSetReady
                }, false);

                enableBtn(new Button[]{
                        //btnSetReady,
                        btnSteOperRevise,
                        btnGetNumRepeat,
                        btnSetOperOver,
                        btnOperUsed,
                        btnOperOk
                }, true);

                //Отвечаем сервису родошел ли код
                break;


            //timeout
            case Constants.State.TZ_OVER_NR:
            case Constants.State.TZ_OVER_EMPTY:


                enableBtn(new Button[]{
                        btnSetReady,
                        btnSteOperRevise,
                        btnGetNumRepeat,
                        btnSetOperOver,
                        btnOperUsed,
                        btnOperOk
                }, false);
                //Отвечаем сервису додошел ли код
                break;


            case TZ_OVER_OK:
                enableBtn(new Button[]{
                        btnSetReady,
                        btnSteOperRevise,
                        //btnGetNumRepeat,
                        btnSetOperOver,
                        btnOperUsed,
                        btnOperOk
                }, false);
                enableBtn(new Button[]{btnGetNumRepeat}, true);
                break;

        }
    }

    private void enableBtn(Button[] buttons, boolean b) {
        for (Button button : buttons) {
            button.setEnabled(b);
            button.setVisibility((b) ? View.VISIBLE : View.GONE);
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            tzid = getArguments().getString(ARG_TZID);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.screen_operation_state, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initInstances();
            }
        });
        if (mListener != null) {
            mListener.setActionBarTitle("№" + tzid);
        }
        initInstances();
    }

    @Optional
    @OnClick({R.id.btn_action_get_num_repeat, R.id.tv_number, R.id.btn_action_set_ready,
            R.id.btn_action_set_operation_over, R.id.btn_action_set_operation_used,
            R.id.btn_action_set_operation_revise,
            R.id.btn_action_set_operation_ok, R.id.tv_msg})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_action_get_num_repeat:
                api.getNumRepeat(Integer.parseInt(tzid)).enqueue(globalModelCallback1);
                break;

            case R.id.tv_number:
                Utils.copyToClipboard(getContext(), tvNumber.getText().toString());
                break;

            case R.id.tv_msg:
                Utils.copyToClipboard(getContext(), tvMsg.getText().toString());
                break;

            case R.id.btn_action_set_ready:
                api = MyApplication.getApi();
                api.setReady(tzid).enqueue(setReadyCallback);
                break;

            case R.id.btn_action_set_operation_over:
                api = MyApplication.getApi();
                api.setOperationOver(tzid).enqueue(globalModelCallback0);
                break;

            case R.id.btn_action_set_operation_used:
                api = MyApplication.getApi();
                api.setOperationUsed(tzid).enqueue(globalModelCallback2);
                break;

            case R.id.btn_action_set_operation_revise:
                api = MyApplication.getApi();
                api.setOperationRevise(tzid).enqueue(globalModelCallback2);
                break;

            case R.id.btn_action_set_operation_ok:
                api = MyApplication.getApi();
                api.setOperationOk(tzid).enqueue(globalModelCallback2);
                break;
        }
    }

    @Override
    protected void initInstances() {
        api = MyApplication.getApi();
        api.getState(tzid).enqueue(operationsCallback);
        // Stop refresh animation
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onClick(View v, int position) {

    }
}

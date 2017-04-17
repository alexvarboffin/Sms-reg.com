package com.psyberia.sms_regcom.screens;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.psyberia.sms_regcom.MyApplication;
import com.psyberia.sms_regcom.R;
import com.psyberia.sms_regcom.helper.PreferencesHelper;
import com.psyberia.sms_regcom.rest.APIService;
import com.psyberia.sms_regcom.rest.ErrorUtils;
import com.psyberia.sms_regcom.rest.badbackend.BaseTypeModel;
import com.psyberia.sms_regcom.rest.beans.APIError;
import com.psyberia.sms_regcom.rest.beans.NumModel;
import com.psyberia.sms_regcom.sdk.BaseFragment;
import com.psyberia.sms_regcom.sdk.ChildItemClickListener;
import com.psyberia.sms_regcom.sdk.DLog;
import com.psyberia.sms_regcom.ui.adapter.CustomSpinnerAdapter;
import com.psyberia.sms_regcom.ui.adapter.NothingSelectedSpinnerAdapter;
import com.psyberia.sms_regcom.ui.adapter.SpinnerItem;
import com.psyberia.sms_regcom.ui.dialog.CountrycodeActivity;
import com.psyberia.sms_regcom.ui.dialog.ServicecodeActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ScreenGetNum extends BaseFragment implements ChildItemClickListener {


    private static final String RESPONSE_SUCCESS = "1";
    @BindView(R.id.tv_response)
    TextView tvResponse;
    @BindView(R.id.tv_tzid)
    TextView tvTzid;
    @BindView(R.id.s_country)
    Spinner sCountry;
    @BindView(R.id.s_service)
    Spinner sService;
    List<SpinnerItem> spinnerItems1, spinnerItems2;
    private boolean isStarted = false;
    private Callback<NumModel> callback = new Callback<NumModel>() {

        @Override
        public void onResponse(Call<NumModel> call, Response<NumModel> response) {
            /*DLog.d("Call request " + call.request().toString());
            DLog.d("Call request header " + call.request().headers().toString());
            DLog.d("Response raw header " + response.headers().toString());
            DLog.d("Response raw" + String.valueOf(response.raw().body()));
            DLog.d("Response code " + String.valueOf(response.code()));*/

            if (response.isSuccessful()) {//Always true Response code 200 hence

                BaseTypeModel bm = response.body();
                if (bm instanceof APIError) {
                    Toast.makeText(getContext(), ((APIError) bm).getErrorMsg(), Toast.LENGTH_SHORT).show();
                } else if (bm != null) {
                    //{response: 1 }
                    NumModel data = (NumModel) bm;
                    if (data.getResponse().equals(RESPONSE_SUCCESS)) {
                        tvResponse.setText("ID вашей операции: ");
                        tvTzid.setVisibility(View.VISIBLE);
                        tvTzid.setText(data.getTzid());

                        DLog.d(data.toString());
                    }
                }

                //[response = null, tzid = 33203680]
                //DLog.d(">>>>>>>>>>" + response.errorBody());


                //APIError error = ErrorUtils.parseError(response);
                //DLog.d(error.getErrorMsg());

            } else {
                //DLog.d("Response errorBody " + String.valueOf(response.body()));
                APIError error = ErrorUtils.parseError(response, MyApplication.retrofit());
                DLog.d(error.getErrorMsg());
            }

            /*if (response.isSuccessful() && response.body() != null) {
                DLog.d("control always here as status code 200 for error condition also");
            } else if (response.errorBody() != null) {
                DLog.d("control never reaches here");
            }*/
        }

        @Override
        public void onFailure(Call<NumModel> call, Throwable t) {
            DLog.d("Pasha onFailure " + t.getMessage());
        }
    };
    private APIService api;
    private Map<String, String> options;
    private PreferencesHelper mpm;
    AdapterView.OnItemSelectedListener spinnerItemSelectListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            if (position <= 0) return;
            String selected = "";

            int viewId = parent.getId();
            switch (viewId) {
                case R.id.s_country:
                    selected = //item.getCode(); //
                            spinnerItems1.get(position - 1).getCode();
                    mpm.putSelected(viewId, position);
                    options.put("country", selected);
                    break;

                case R.id.s_service:
                    selected = spinnerItems2.get(position - 1).getCode();
                    mpm.putSelected(viewId, position);
                    options.put("service", selected);
                    break;
            }

            //if (isStarted) Toast.makeText(getContext(), selected, Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.screen_get_number, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            // Restore last state for checked position.
            String tzid = savedInstanceState.getString("tzid", "#");
            tvTzid.setText(tzid);
        }

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        mpm = PreferencesHelper.getInstance();
        initInstances();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("tzid", tvTzid.getText().toString());
    }

    @Override
    protected void initInstances() {
        options = new HashMap<>();

        if (mListener != null) {
            mListener.setActionBarTitle(getString(R.string.title_get_num));
        }


        String[] countryValues = getResources().getStringArray(R.array.country_names);
        String[] countryCodes = getResources().getStringArray(R.array.country_codes);

        String[] serviceValues = getResources().getStringArray(R.array.service_names);
        String[] serviceCodes = getResources().getStringArray(R.array.service_codes);


        spinnerItems1 = new ArrayList<>();
        spinnerItems2 = new ArrayList<>();

        for (int i = 0; i < countryValues.length; i++) {
            spinnerItems1.add(new SpinnerItem(countryValues[i], countryCodes[i]));
        }

        for (int i = 0; i < serviceValues.length; i++) {
            spinnerItems2.add(new SpinnerItem(serviceValues[i], serviceCodes[i]));
        }


        CustomSpinnerAdapter adapter = new CustomSpinnerAdapter(getActivity(), R.layout.row_activity_countrycode, spinnerItems1);
        sCountry.setAdapter(adapter);

        //adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sCountry.setPrompt("-- Выбрать страну --");

        sCountry.setAdapter(
                new NothingSelectedSpinnerAdapter(adapter, R.layout.spinner_row_nothing_selected,
                        // R.layout.contact_spinner_nothing_selected_dropdown, // Optional
                        getContext(), "-- Выбрать страну --"));


        sCountry.setSelection(mpm.getSelected(R.id.s_country));

        adapter = new CustomSpinnerAdapter(getActivity(), R.layout.row_activity_countrycode, spinnerItems2);
        sService.setAdapter(
                new NothingSelectedSpinnerAdapter(adapter, R.layout.spinner_row_nothing_selected,
                        // R.layout.contact_spinner_nothing_selected_dropdown, // Optional
                        getContext(), "-- Выбрать сервис --"));
        sService.setPrompt("-- Выбрать сервис --");
        sService.setSelection(mpm.getSelected(R.id.s_service));

        sCountry.setOnItemSelectedListener(spinnerItemSelectListener);
        sService.setOnItemSelectedListener(spinnerItemSelectListener);


        isStarted = true;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRetainInstance(true);
    }

    @OnClick({
            //R.id.btn_select_country,
            //R.id.btn_select_service,
            R.id.btn_get_num,
            R.id.tv_tzid
    })
    public void submit(View view) {
        // TODO submit data to server...
        switch (view.getId()) {
            /*case R.id.btn_select_country:
                Intent intent = new Intent(getContext(), CountrycodeActivity.class);
                startActivityForResult(intent, 1);
                break;

            case R.id.btn_select_service:
                intent = new Intent(getContext(), ServicecodeActivity.class);
                startActivityForResult(intent, 2);
                break;*/

            case R.id.btn_get_num:
                //if (options.get("country").isEmpty()) {
                //    Toast.makeText(getContext(), "", Toast.LENGTH_LONG).show();
                //} else
                if (options.get("service") == null) {
                    Toast.makeText(getContext(), "Выберите сервис", Toast.LENGTH_SHORT).show();
                } else {
                    api = MyApplication.getApi();
                    api.getNum(options).enqueue(callback);
                }
                break;

            case R.id.tv_tzid:
                mListener.replaceFragment(ScreenOperationState.newInstance(tvTzid.getText().toString()));
                break;
        }
    }

    @Override
    public void onClick(View v, int position) {
    }

    // Get Result Back
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            String countryCode = data.getStringExtra(CountrycodeActivity.RESULT_CONTRYCODE);
            options.put("country", countryCode);
            Toast.makeText(getContext(), "Номер: " + countryCode, Toast.LENGTH_SHORT).show();
        } else if (requestCode == 2 && resultCode == Activity.RESULT_OK) {
            String service = data.getStringExtra(ServicecodeActivity.RESULT_SERVICECODE);
            options.put("service", service);
            Toast.makeText(getContext(), "Сервис: " + service, Toast.LENGTH_SHORT).show();
        }
    }
}

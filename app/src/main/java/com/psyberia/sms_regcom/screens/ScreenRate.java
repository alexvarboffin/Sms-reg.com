package com.psyberia.sms_regcom.screens;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.psyberia.sms_regcom.MyApplication;
import com.psyberia.sms_regcom.R;
import com.psyberia.sms_regcom.helper.PreferencesHelper;
import com.psyberia.sms_regcom.rest.APIService;
import com.psyberia.sms_regcom.rest.beans.RateModel;
import com.psyberia.sms_regcom.sdk.BaseFragment;
import com.psyberia.sms_regcom.sdk.ChildItemClickListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ScreenRate extends BaseFragment
        implements ChildItemClickListener, View.OnClickListener {


    @BindView(R.id.btn_set_rate)
    Button btnSetRate;
    @BindView(R.id.tv_response)
    TextView tvResponse;
    @BindView(R.id.tv_rate)
    TextView tvRate;
    @BindView(R.id.et_rate)
    EditText etRate;
    private APIService api;
    private PreferencesHelper mpm;
    private Callback<RateModel> callback = new Callback<RateModel>() {
        @Override
        public void onResponse(Call<RateModel> call, Response<RateModel> response) {
            if (response.isSuccessful()) {
                RateModel data = response.body();

                if (data.getResponse().equals("1")) {
                    float rate = (data.getRate() == null) ? 0.0f : Float.parseFloat(data.getRate());
                    mpm.setRate(rate);
                    etRate.setText(Float.toString(rate));
                    Toast.makeText(getContext(), "Установлено значение: "
                            + Float.toString(rate), Toast.LENGTH_SHORT).show();
                }

            }
        }

        @Override
        public void onFailure(Call<RateModel> call, Throwable t) {
            Toast.makeText(getContext(), "Error", Toast.LENGTH_LONG).show();
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.screen_rate, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        if (mListener != null) {
            mListener.setActionBarTitle(getString(R.string.title_set_rate));
        }
        initInstances();
    }

    @Override
    protected void initInstances() {
        btnSetRate.setOnClickListener(ScreenRate.this);
        mpm = PreferencesHelper.getInstance();
        etRate.setText(Float.toString(mpm.getRate()));
    }

    @Override
    public void onClick(View v, int position) {

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_set_rate) {
            api = MyApplication.getApi();
            String rate = etRate.getText().toString().trim();
            if (rate.isEmpty()) {
                Toast.makeText(getContext(), "Установите значение", Toast.LENGTH_LONG).show();
            } else {
                api.setRate(Float.parseFloat(rate)).enqueue(callback);
            }

        }
    }
}
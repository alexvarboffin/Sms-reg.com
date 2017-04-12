package com.psyberia.sms_regcom.screens;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.psyberia.sms_regcom.MyApplication;
import com.psyberia.sms_regcom.R;
import com.psyberia.sms_regcom.rest.APIService;
import com.psyberia.sms_regcom.rest.badbackend.BaseTypeModel;
import com.psyberia.sms_regcom.rest.beans.APIError;
import com.psyberia.sms_regcom.rest.beans.Balance;
import com.psyberia.sms_regcom.sdk.BaseFragment;
import com.psyberia.sms_regcom.sdk.ChildItemClickListener;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ScreenBalance extends BaseFragment
        implements ChildItemClickListener {

    @BindView(R.id.tv_response)
    TextView tvResponse;
    @BindView(R.id.tv_balance)
    TextView tvBalance;
    APIService api;
    private Callback<Balance> callback = new Callback<Balance>() {
        @Override
        public void onResponse(Call<Balance> call, Response<Balance> response) {
            if (response.isSuccessful()) {


                BaseTypeModel bm = response.body();

                if (bm instanceof APIError) {
                    Toast.makeText(getContext(), ((APIError) bm).getErrorMsg(), Toast.LENGTH_SHORT).show();
                } else if (bm != null) {
                    Balance data = response.body();
                    //tvResponse.setText(data.getBalance());

                    String result = String.format(Locale.getDefault(), "%.2f", Float.valueOf(data.getBalance()));
                    tvBalance.setText(result);//1 - success
                }

            }

        }

        @Override
        public void onFailure(Call<Balance> call, Throwable t) {

        }
    };

    public static ScreenBalance newInstance() {
        return new ScreenBalance();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.screen_balance, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        initInstances();
    }

    @Override
    protected void initInstances() {
        if (mListener != null) {
            mListener.setActionBarTitle(getString(R.string.title_balance));
        }
        api = MyApplication.getApi();
        api.getBalance().enqueue(callback);
    }

    @Override
    public void onClick(View v, int position) {

    }
}
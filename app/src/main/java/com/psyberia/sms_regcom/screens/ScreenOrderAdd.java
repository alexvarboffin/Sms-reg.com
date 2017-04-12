package com.psyberia.sms_regcom.screens;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.psyberia.sms_regcom.MyApplication;
import com.psyberia.sms_regcom.R;
import com.psyberia.sms_regcom.rest.APIService;
import com.psyberia.sms_regcom.rest.badbackend.BaseTypeModel;
import com.psyberia.sms_regcom.rest.beans.APIError;
import com.psyberia.sms_regcom.rest.beans.OrderAddModel;
import com.psyberia.sms_regcom.sdk.BaseFragment;
import com.psyberia.sms_regcom.sdk.DLog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by combo on 10.04.2017.
 */

public class ScreenOrderAdd extends BaseFragment implements CompoundButton.OnCheckedChangeListener {

    @BindView(R.id.input_count)
    EditText inputCount;
    @BindView(R.id.s_country)
    Spinner sCountry;
    @BindView(R.id.s_service)
    Spinner sService;
    @BindView(R.id.cb_options)
    CheckBox cbOptions;
    //@BindView(R.id.input_layout_name) TextInputLayout inputLayoutName;
    @BindView(R.id.input_name)
    EditText inputName;
    //@BindView(R.id.input_layout_age) TextInputLayout inputLayoutAge;
    @BindView(R.id.input_age)
    EditText inputAge;
    @BindView(R.id.s_gender)
    Spinner sGender;
    //@BindView(R.id.input_layout_city) TextInputLayout inputLayoutCity;
    @BindView(R.id.input_city)
    EditText inputCity;
    private APIService service;
    private Map<String, String> options;
    private AdapterView.OnItemSelectedListener genderListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            String current = (String) parent.getSelectedItem();
            //DLog.d(current.getKey());
            //Toast.makeText(getContext(), "Country ID: " + current.getKey()
            //        + ",  Country Name : " + current.getValue(), Toast.LENGTH_SHORT).show();
            options.put("gender", current);

        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };
    private Callback<OrderAddModel> addOrderCallback = new Callback<OrderAddModel>() {
        @Override
        public void onResponse(Call<OrderAddModel> call, Response<OrderAddModel> response) {
            if (response.isSuccessful()) {

                //{"response":"ERROR","error_msg":"Wrong count value"}


                BaseTypeModel bm = response.body();

                if (bm instanceof APIError) {
                    Toast.makeText(getContext(), ((APIError) bm).getErrorMsg(), Toast.LENGTH_SHORT).show();
                    DLog.d("Error: " + bm.toString());
                } else if (bm != null) {
                    OrderAddModel bean = (OrderAddModel) bm;
                    DLog.d("Success: " + bean.toString());

                    if (bean.getResponse().equals("1")) {
                        bean.getOrder_id();//id заказа
                    }
                }
            }
        }

        @Override
        public void onFailure(Call<OrderAddModel> call, Throwable t) {

        }
    };

    private AdapterView.OnItemSelectedListener selectedListener;


    private String[] countriesCodes;
    private String[] servicesCodes;

    @Override
    protected void initInstances() {


        if (mListener != null) {
            mListener.setActionBarTitle(getString(R.string.title_order_add));
        }
        options = new HashMap<>();
        selectedListener = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String code = "";
                switch (parent.getId()) {
                    case R.id.s_country:
                        code = countriesCodes[position];
                        options.put("country", code);
                        break;
                    case R.id.s_service:
                        code = servicesCodes[position];
                        options.put("service", code);
                        break;
                }

                Toast.makeText(getContext(), code, Toast.LENGTH_SHORT).show();

      /*  switch (position) {
            case 0:
                // Whatever you want to happen when the first item gets selected
                break;
            case 1:
                // Whatever you want to happen when the second item gets selected
                break;
            case 2:
                // Whatever you want to happen when the thrid item gets selected
                break;

        }*/
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        };

        countriesCodes = getResources().getStringArray(R.array.country_codes);
        servicesCodes = getResources().getStringArray(R.array.service_codes);


        //Country
        String[] countries = getResources().getStringArray(R.array.country_names);


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, countries);
        sCountry.setAdapter(adapter);
        sCountry.setOnItemSelectedListener(selectedListener);

        //Service
        String[] services = getResources().getStringArray(R.array.service_names);

        ArrayAdapter<String> adapterService = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, services);
        sService.setAdapter(adapterService);
        sService.setOnItemSelectedListener(selectedListener);

        //gender
        ArrayList<String> genderList = new ArrayList<>();
        genderList.add("male");
        genderList.add("female");

        ArrayAdapter<String> adp = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_spinner_dropdown_item, genderList);
        sGender.setAdapter(adp);
        //dropdownGender.setSelection(adapter.getPosition("###"));
        sGender.setOnItemSelectedListener(genderListener);

        cbOptions.setOnCheckedChangeListener(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.screen_order_add, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        initInstances();
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        options.put("options", (isChecked) ? "on" : "");
        enableExtarnalOptions(isChecked);
    }

    private void enableExtarnalOptions(boolean isChecked) {
        inputAge.setEnabled(isChecked);
        inputCity.setEnabled(isChecked);
        inputName.setEnabled(isChecked);
        sGender.setEnabled(isChecked);
    }

    @OnClick(R.id.btn_order_add)
    void onBtnOrderAddClick() {
        options.put("count", inputCount.getText().toString());
        options.put("name", inputName.getText().toString());
        options.put("age", inputAge.getText().toString());
        options.put("city", inputCity.getText().toString());
        options.put("count", inputCount.getText().toString());

        DLog.d(options.toString());
        service = MyApplication.getApi();
        service.orderAdd(options).enqueue(addOrderCallback);
    }







    /*private OnItemSelectedListener typeSelectedListener = new OnItemSelectedListener() {

        @Override
        public void onItemSelected(AdapterView<?> parent, View view,
                                   int position, long id) {
            Log.d(TAG, "user selected : "
                    + typeSpinner.getSelectedItem().toString());

        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

    private OnTouchListener typeSpinnerTouchListener = new OnTouchListener() {

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            selected = true;
            ((BaseAdapter) typeSpinnerAdapter).notifyDataSetChanged();
            return false;
        }
    };

*/
}

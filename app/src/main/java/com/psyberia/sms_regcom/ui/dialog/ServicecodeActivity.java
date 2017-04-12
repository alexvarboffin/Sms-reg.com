package com.psyberia.sms_regcom.ui.dialog;

import android.app.ListActivity;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.psyberia.sms_regcom.R;
import com.psyberia.sms_regcom.ui.adapter.ServiceListArrayAdapter;

import java.util.ArrayList;
import java.util.List;

public class ServicecodeActivity extends ListActivity {

    public static String RESULT_SERVICECODE = "servicecode";
    public String[] countrynames, countrycodes;
    private TypedArray imgs;
    private List<Service> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        populateServiceList();

        ArrayAdapter<Service> adapter = new ServiceListArrayAdapter(this, list);

        setListAdapter(adapter);
        getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Service c = list.get(position);
                Intent returnIntent = new Intent();
                returnIntent.putExtra(RESULT_SERVICECODE, c.getCode());
                setResult(RESULT_OK, returnIntent);
                imgs.recycle(); //recycle images
                finish();
            }
        });
    }

    private void populateServiceList() {
        list = new ArrayList<Service>();
        countrynames = getResources().getStringArray(R.array.service_names);
        countrycodes = getResources().getStringArray(R.array.service_codes);
        imgs = getResources().obtainTypedArray(R.array.service_flags);
        for (int i = 0; i < countrycodes.length; i++) {
            list.add(new Service(countrynames[i], countrycodes[i], imgs.getDrawable(i)));
        }
    }

    public class Service {
        private String name;
        private String code;
        private Drawable flag;

        public Service(String name, String code, Drawable flag) {
            this.name = name;
            this.code = code;
            this.flag = flag;
        }

        public String getName() {
            return name;
        }

        public Drawable getFlag() {
            return flag;
        }

        public String getCode() {
            return code;
        }
    }
}

package com.psyberia.sms_regcom.screens;

/**
 * Created by combo on 25.03.2017.
 */


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.psyberia.sms_regcom.MyApplication;
import com.psyberia.sms_regcom.R;
import com.psyberia.sms_regcom.rest.APIService;
import com.psyberia.sms_regcom.rest.badbackend.BadBackendResponse;
import com.psyberia.sms_regcom.rest.beans.APIError;
import com.psyberia.sms_regcom.rest.beans.OperationBean;
import com.psyberia.sms_regcom.sdk.BaseFragment;
import com.psyberia.sms_regcom.sdk.ChildItemClickListener;
import com.psyberia.sms_regcom.sdk.DLog;
import com.psyberia.sms_regcom.ui.adapter.ComplexRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ScreenOperationTab1 extends BaseFragment
        implements View.OnClickListener, ChildItemClickListener {

    private static final String ARG_OPSTATE = "opstate";
    @BindView(R.id.rv_categories_list)
    RecyclerView recyclerView;
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;

    // @BindView(R.id.spinner)
    // Spinner spinner;
    APIService api;

    //@OnClick(R.id.btn_select_country)
    //public void submit() {
    //
    //new DialogSelectCountry(getContext()).show();
    //}
    private String opstate;
    private ArrayList<Object> dataset;
    private Callback<BadBackendResponse<OperationBean[]>> operationsCallback = new Callback<BadBackendResponse<OperationBean[]>>() {
        @Override
        public void onResponse(Call<BadBackendResponse<OperationBean[]>> call,
                               Response<BadBackendResponse<OperationBean[]>> response) {
            /*DLog.d("Call request " + call.request().toString());
            DLog.d("Call request header " + call.request().headers().toString());
            DLog.d("Response raw header " + response.headers().toString());
            DLog.d("Response raw" + String.valueOf(response.raw().body()));
            DLog.d("Response code " + String.valueOf(response.code()));
            DLog.d("Response body> " + String.valueOf(response.body()));
            DLog.d("Response errbody>> " + String.valueOf(response.errorBody()));*/
            dataset = new ArrayList<Object>();
            if (response.isSuccessful()) {

                OperationBean[] data = response.body().getData();
                APIError error = response.body().getError();


                if (null != data) {


                    dataset.addAll(Arrays.asList(data));

                    //data.add(spinner.getSelectedItem());
                    //data = new ArrayList<>();
                    //data.addAll(response.body().getOperationBean());

                    ComplexRecyclerViewAdapter adapter = new ComplexRecyclerViewAdapter(
                            dataset, ScreenOperationTab1.this);
                    adapter.setChildItemClickListener(ScreenOperationTab1.this);
                    recyclerView.setAdapter(adapter);

                    //List<OperationBean> list = response.body();
                    //DLog.d(">>" + list.toString());
                /*for (OperationBean operation : list) {
                    String tzid = operation.getTzid();
                    DLog.d(tzid);

                    //=====================================
                Response<GetStateModel> state = App.getApi().getState(tzid).execute();
                if (state.isSuccessful()) {
                    System.out.println(state.body().toString());

                    System.out.println(state.body().toString());
                }
                    //=====================================

                    String number = operation.getPhone();
                    DLog.d(operation.getService() + " " + number);
                    DLog.d(operation.getAnswer()
                            + " "
                            + hm.get(operation.getStatus()) + "->" + operation.getStatus());
                    DLog.d("------------------------------");

                }*/
                } else if (null != error) {
                    dataset.add(error);
                    ComplexRecyclerViewAdapter adapter = new ComplexRecyclerViewAdapter(
                            dataset, ScreenOperationTab1.this);
                    adapter.setChildItemClickListener(ScreenOperationTab1.this);
                    recyclerView.setAdapter(adapter);
                }


            } else Toast.makeText(getContext(), "fail", Toast.LENGTH_LONG).show();
            //[{"response":"0","error_msg":"No active tranzactions"}]


            DLog.d("##" + ScreenOperationTab1.this.hashCode() + dataset.toString());

        }

        @Override
        public void onFailure(Call<BadBackendResponse<OperationBean[]>> call, Throwable t) {

        }
    };

    public static ScreenOperationTab1 newInstance(String opstate) {
        ScreenOperationTab1 fragment = new ScreenOperationTab1();
        Bundle args = new Bundle();
        args.putString(ARG_OPSTATE, opstate);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (null != getArguments()) {
            opstate = getArguments().getString(ARG_OPSTATE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.screen_operations, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        if (savedInstanceState != null) {
            opstate = getArguments().getString(ARG_OPSTATE);
        } else {
            opstate = getArguments() != null ? getArguments().getString(ARG_OPSTATE) : "null";
        }


        //spinner.setEnabled(false);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initInstances();
            }
        });

        api = MyApplication.getApi();
        initInstances();
    }

    @Override
    protected void initInstances() {

        //onresponse
        //recyclerView.setAdapter(new ComplexRecyclerViewAdapter(null, getContext()));
        /*spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Balance balance = (Balance) parent.getAdapter().getItem(position);


                api.getOperations(
                        opstate//"completed"
                        , 100).enqueue(operationsCallback);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });*/
        api.getOperations(opstate, 100).enqueue(operationsCallback);

        // Stop refresh animation
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //case R.id.btn_select_country:
            //    break;
        }
    }

    @Override
    public void onClick(View v, int position) {
        DLog.d(this.hashCode() + "::Clicked tab#1: " + v.getId() + "|" + position);
        switch (v.getId()) {
            //case R.id.rl_item://R.id.tv_tzid:
            //    break;
            default:
                DLog.d(dataset.toString());

                Object current = dataset.get(position);
                if (current instanceof OperationBean) {
                    mListener.replaceFragment(ScreenOperationState.newInstance(
                            ((OperationBean) current).getTzid()));
                }
                break;
        }
    }
}

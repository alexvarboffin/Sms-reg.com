package com.psyberia.sms_regcom.screens;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.psyberia.sms_regcom.R;
import com.psyberia.sms_regcom.sdk.BaseFragment;
import com.psyberia.sms_regcom.ui.adapter.SectionsPagerAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ScreenOperation extends BaseFragment {

    @BindView(R.id.viewpager)
    ViewPager viewPager;
    @BindView(R.id.tabs)
    TabLayout tabLayout;

    public ScreenOperation() {
        // Required empty public constructor
    }

    //@BindView(R.id.toolbar)
    //Toolbar toolbar;

    // TODO: Rename and change types and number of parameters
    public static ScreenOperation newInstance() {
        return new ScreenOperation();
    }

    @Override
    protected void initInstances() {
        setupViewPager(viewPager);
        if (mListener != null) {
            mListener.setActionBarTitle(getString(R.string.title_operations));
        }
    }

    private void setupViewPager(ViewPager viewPager) {
        SectionsPagerAdapter adapter = new SectionsPagerAdapter(getChildFragmentManager());
        adapter.addFragment(ScreenOperationTab1.newInstance("active"), "active");
        adapter.addFragment(ScreenOperationTab1.newInstance("completed"), "completed");
        //adapter.addFragment(new OneFragment(), "THREE");
        viewPager.setAdapter(adapter);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.tab_layout, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        initInstances();

        //((MainActivity)getActivity()).setSupportActionBar(toolbar);
        //((MainActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        tabLayout.setupWithViewPager(viewPager);
        //tabLayout.setVisibility(View.INVISIBLE);
    }
}

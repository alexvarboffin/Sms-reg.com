package com.psyberia.sms_regcom.sdk;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by ponch on 09.03.17.
 */

public abstract class BaseFragment extends Fragment {
    public IOnFragmentInteractionListener mListener;

    public BaseFragment() {
        // Required empty public constructor
    }

    protected abstract void initInstances();

    //[1]
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        DLog.d("");
        if (context instanceof IOnFragmentInteractionListener) {
            mListener = (IOnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement IOnFragmentInteractionListener");
        }
    }

    //[2]
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setRetainInstance(true);//saved fragment state
        DLog.d("");
    }

    //[3]
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //View v = inflater.inflate(R.layout.screen_categories_, container, false);
        DLog.d("");
        return null;
    }

    //[4]
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //mListener.setBurger(toolbar);//<-- show toolbar
        DLog.d("");
    }

    //[5]
    @Override
    public void onStart() {
        super.onStart();
        DLog.d("");
    }

    //FRAGMENT IS ACTIVE....

    //[6]
    @Override
    public void onResume() {
        super.onResume();
        //deltaTime = System.currentTimeMillis() - time;
        //Log.e(TAG, "[ #### onResume: delta - " + deltaTime + " #### ]");
        DLog.d("");
    }


    //FRAGMENT_IS_ACTIVE
    //FRAGMENT IS ACTIVE....
    //7
    @Override
    public void onPause() {
        super.onPause();
        DLog.d("");
    }

    //8
    @Override
    public void onStop() {
        super.onStop();
        DLog.d("");
    }

    //9
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        DLog.d("");
    }

    //10
    @Override
    public void onDestroy() {
        super.onDestroy();
        DLog.d("");
    }

    //11...
    @Override
    public void onDetach() {
        super.onDetach();
        DLog.d("");
        mListener = null;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            //mListener.onFragmentInteraction(uri);
        }
    }

    public IOnFragmentInteractionListener getListener() {
        return mListener;
    }


}

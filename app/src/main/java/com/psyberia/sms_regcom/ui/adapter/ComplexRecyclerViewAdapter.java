package com.psyberia.sms_regcom.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.psyberia.sms_regcom.R;
import com.psyberia.sms_regcom.rest.beans.APIError;
import com.psyberia.sms_regcom.rest.beans.OperationBean;
import com.psyberia.sms_regcom.sdk.ChildItemClickListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.psyberia.sms_regcom.rest.Constants.hm;


/**
 * Created by combo on 25.03.2017.
 */

public class ComplexRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final int OPERATION = 0, ERROR = 1;
    //private final View.OnClickListener listener;
    private /*FAIL = static */ ChildItemClickListener childItemClickListener;
    // The items to display in your RecyclerView
    private List<Object> items;

    // Provide a suitable constructor (depends on the kind of dataset)
    public ComplexRecyclerViewAdapter(List<Object> items, View.OnClickListener listener) {
        this.items = items;
        //this.listener = listener;
    }

    public void setChildItemClickListener(ChildItemClickListener listener) {
        childItemClickListener = listener;
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return this.items.size();
    }

    //Returns the view type of the item at position for the purposes of view recycling.
    @Override
    public int getItemViewType(int position) {
        if (items.get(position) instanceof OperationBean) {
            return OPERATION;
        } else if (items.get(position) instanceof APIError) {
            return ERROR;
        }
        return -1;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        RecyclerView.ViewHolder viewHolder;
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());

        switch (viewType) {
            case OPERATION:
                View v1 = inflater.inflate(R.layout.row_item_operation, viewGroup, false);
                viewHolder = new ViewHolder1(v1);
                break;
            case ERROR:
                View v2 = inflater.inflate(R.layout.row_item_error, viewGroup, false);
                viewHolder = new ViewHolder2(v2);
                break;
            default:
                View v = inflater.inflate(android.R.layout.simple_list_item_1, viewGroup, false);
                viewHolder = new RecyclerViewSimpleTextViewHolder(v);
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        switch (viewHolder.getItemViewType()) {
            case OPERATION:
                ViewHolder1 vh1 = (ViewHolder1) viewHolder;
                configureViewHolder1(vh1, position);
                break;
            case ERROR:
                ViewHolder2 vh2 = (ViewHolder2) viewHolder;
                configureViewHolder2(vh2, position);
                break;
            default:
                RecyclerViewSimpleTextViewHolder vh = (RecyclerViewSimpleTextViewHolder) viewHolder;
                configureDefaultViewHolder(vh, position);
                break;
        }
    }


    private void configureDefaultViewHolder(RecyclerViewSimpleTextViewHolder vh, int position) {
        //vh.getLabel().setText((CharSequence) items.get(position));
    }

    private void configureViewHolder1(ViewHolder1 holder, int position) {
        OperationBean operation = (OperationBean) items.get(position);
        if (operation != null) {
            holder.tzid.setText(operation.getTzid());
            //--holder.service.setText(operation.getService());
            holder.phone.setText("+" + operation.getPhone());
            holder.msg.setText("Сообщение: " + ((null == operation.getMsg()) ? "" : operation.getMsg()));
            holder.status.setText(hm.get(operation.getStatus()));

        }
    }

    private void configureViewHolder2(ViewHolder2 vh2, int positon) {
        //vh2.getImageView().setImageResource(R.drawable.sample_golden_gate);
        APIError error = (APIError) items.get(positon);
        if (error != null) {
            vh2.error_msg.setText(error.getErrorMsg());
        }
    }

    //========================================================================================
    public class ViewHolder1 extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.tv_tzid)
        TextView tzid;
        //@BindView(R.id.iv_service)
        //AppCompatImageView service;

        @BindView(R.id.tv_service)
        TextView service;

        @BindView(R.id.tv_phone)
        TextView phone;
        @BindView(R.id.tv_msg)
        TextView msg;
        @BindView(R.id.tv_status)
        TextView status;

        public ViewHolder1(View v) {
            super(v);
            ButterKnife.bind(this, v);
            //tzid.setOnClickListener(this);
            v.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {
            childItemClickListener.onClick(v, getAdapterPosition());//clicker...
        }
    }


    class RecyclerViewSimpleTextViewHolder extends RecyclerView.ViewHolder {

        @BindView(android.R.id.text1)
        TextView text1;

        RecyclerViewSimpleTextViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    private class ViewHolder2 extends RecyclerView.ViewHolder {

        TextView response;
        TextView error_msg;

        ViewHolder2(View v2) {
            super(v2);
            response = (TextView) v2.findViewById(R.id.tv_response);
            error_msg = (TextView) v2.findViewById(R.id.tv_error_msg);
        }
    }
}
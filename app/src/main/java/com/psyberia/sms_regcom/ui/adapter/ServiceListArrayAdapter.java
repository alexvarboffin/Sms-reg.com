package com.psyberia.sms_regcom.ui.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.psyberia.sms_regcom.R;
import com.psyberia.sms_regcom.ui.dialog.ServicecodeActivity;

import java.util.List;

/**
 * Created by combo on 27.03.2017.
 */

public class ServiceListArrayAdapter extends ArrayAdapter<ServicecodeActivity.Service> {

    private final List<ServicecodeActivity.Service> list;
    private final Activity context;

    public ServiceListArrayAdapter(Activity context, List<ServicecodeActivity.Service> list) {
        super(context, R.layout.row_activity_countrycode, list);
        this.context = context;
        this.list = list;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = null;

        if (convertView == null) {
            LayoutInflater inflator = context.getLayoutInflater();
            view = inflator.inflate(R.layout.row_activity_countrycode, null);
            final ViewHolder viewHolder = new ViewHolder();
            viewHolder.name = (TextView) view.findViewById(R.id.name);
            viewHolder.flag = (ImageView) view.findViewById(R.id.flag);
            view.setTag(viewHolder);
        } else {
            view = convertView;
        }

        ViewHolder holder = (ViewHolder) view.getTag();
        holder.name.setText(list.get(position).getName());
        holder.flag.setImageDrawable(list.get(position).getFlag());
        return view;
    }

    static class ViewHolder {
        protected TextView name;
        protected ImageView flag;
    }
}
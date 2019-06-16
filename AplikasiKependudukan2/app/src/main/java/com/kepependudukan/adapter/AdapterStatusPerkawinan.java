package com.kepependudukan.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.kepependudukan.R;
import com.kepependudukan.model.DataPerkawinan;

import java.util.List;

public class AdapterStatusPerkawinan extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private List<DataPerkawinan> item;

    public AdapterStatusPerkawinan(Activity activity, List<DataPerkawinan> item) {
        this.activity = activity;
        this.item = item;
    }

    @Override
    public int getCount() {
        return item.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Object getItem(int location) {
        return item.get(location);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (inflater == null)
            inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null)
            convertView = inflater.inflate(R.layout.list_status_perkawinan, null);

        TextView status_perkawinan = (TextView) convertView.findViewById(R.id.status_perkawinan);

        convertView.setFocusable(false);
        convertView.setFocusableInTouchMode(false);

        DataPerkawinan dataPerkawinan;
        dataPerkawinan = item.get(position);
        if (dataPerkawinan != null) {
            status_perkawinan.setText(dataPerkawinan.getStatus_perkawinan());
        } else {

        }
        return convertView;
    }
}

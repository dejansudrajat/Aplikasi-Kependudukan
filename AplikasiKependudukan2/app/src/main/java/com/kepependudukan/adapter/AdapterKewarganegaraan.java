package com.kepependudukan.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.kepependudukan.R;
import com.kepependudukan.model.DataKewarganegaraan;

import java.util.List;

public class AdapterKewarganegaraan extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private List<DataKewarganegaraan> item;

    public AdapterKewarganegaraan(Activity activity, List<DataKewarganegaraan> item) {
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
        if(inflater == null)
            inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if(convertView == null)
            convertView = inflater.inflate(R.layout.list_kewarganegaraan, null);

        TextView kewarganegaraan = (TextView) convertView.findViewById(R.id.kewarganegaraan);

        convertView.setFocusable(false);
        convertView.setFocusableInTouchMode(false);

        DataKewarganegaraan dataKewarganegaraan;
        dataKewarganegaraan = item.get(position);

        if(dataKewarganegaraan != null){
            kewarganegaraan.setText(dataKewarganegaraan.getKewarganegaraan());
        } else {

        }
        return convertView;
    }
}

package com.kepependudukan.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.kepependudukan.R;
import com.kepependudukan.model.DataAgama;

import java.util.List;

public class AdapterAgama extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private List<DataAgama> item;

    public AdapterAgama(Activity activity, List<DataAgama> item) {
        this.activity = activity;
        this.item = item;
    }
    public AdapterAgama(List<DataAgama>item){
        this.item=item;
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
            convertView = inflater.inflate(R.layout.list_agama, null);

        TextView agama = (TextView) convertView.findViewById(R.id.agama);
        convertView.setFocusable(false);
        convertView.setFocusableInTouchMode(false);

        DataAgama dataAgama;
        dataAgama = item.get(position);
        agama.setText(dataAgama.getAgama());

        return convertView;
    }
}

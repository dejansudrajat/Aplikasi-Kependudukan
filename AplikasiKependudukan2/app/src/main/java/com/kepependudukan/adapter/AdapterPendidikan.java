package com.kepependudukan.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;

import com.kepependudukan.R;
import com.kepependudukan.model.DataPendidikan;

import java.util.List;

public class AdapterPendidikan extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private List<DataPendidikan> item;

    public AdapterPendidikan(Activity activity, List<DataPendidikan> item){
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
            convertView = inflater.inflate(R.layout.list_pendidikan, null);

        TextView pendidikan = (TextView) convertView.findViewById(R.id.pendidikan);

        convertView.setFocusable(false);
        convertView.setFocusableInTouchMode(false);

        DataPendidikan dataPendidikan;
        dataPendidikan = item.get(position);
        if(dataPendidikan != null) {
            pendidikan.setText(item.get(position).getPendidikan());
            //pendidikan.setText(dataPendidikan.getPendidikan());
        } else {

        }
        return convertView;
    }
}

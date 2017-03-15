package com.example.dell.banhang.ADAPTER;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.dell.banhang.ClassObject.DebillClass;
import com.example.dell.banhang.R;

import java.util.List;

/**
 * Created by DELL on 11/16/2016.
 */

public class DetailAdapter extends ArrayAdapter<DebillClass> {
    public DetailAdapter(Context context, int textViewResourceID) {
        super(context, textViewResourceID);
    }

    public DetailAdapter(Context context, int resource, List<DebillClass> items) {
        super(context, resource, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.activity_lane_detail, null);
        }
        DebillClass m = getItem(position);
        if (m != null) {
            TextView tt1 = (TextView) v.findViewById(R.id.tvId);
            tt1.setText(String.valueOf(m.getBillID()));
            TextView tt2= (TextView) v.findViewById(R.id.tvMeal);
            tt2.setText(m.getName());
            TextView tt3 = (TextView) v.findViewById(R.id.tvNum);
            tt3.setText(String.valueOf(m.getNum()));
            TextView tt4 = (TextView) v.findViewById(R.id.tvTotal);
            tt4.setText(String.valueOf(m.getTotal()));
        }
        return v;
    }
}
package com.example.dell.banhang.ADAPTER;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.dell.banhang.ClassObject.BillClass;
import com.example.dell.banhang.R;

import java.util.List;

/**
 * Created by DELL on 11/22/2016.
 */

public class BillAdapter extends ArrayAdapter<BillClass> {
    public BillAdapter(Context context, int textViewResourceID) {
        super(context, textViewResourceID);
    }

    public BillAdapter(Context context, int resource, List<BillClass> items) {
        super(context, resource, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.activity_lane_bill, null);
        }
        BillClass m = getItem(position);
        if (m != null) {
            TextView tt1 = (TextView) v.findViewById(R.id.tvHD);
            tt1.setText(String.valueOf(m.getBillID()));
            if(m.getPay()== 0){
                tt1.setText(String.valueOf(m.getBillID()));
            }
        }
        return v;
    }
}
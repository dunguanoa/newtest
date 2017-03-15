package com.example.dell.banhang.ADAPTER;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.dell.banhang.ClassObject.TableClass;
import com.example.dell.banhang.R;

import java.util.List;

/**
 * Created by DELL on 8/9/2016.
 */
public class TableAdapter extends ArrayAdapter<TableClass> { //chỗ nào bên main gọi cái này ra ấy
    public TableAdapter(Context context, int textViewResourceID) {
        super(context, textViewResourceID);
    }

    public TableAdapter(Context context, int resource, List<TableClass> items) {
        super(context, resource, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.activity_lane_table, null);

        }
        TableClass m = getItem(position);
        if (m != null) {
            TextView tt1 = (TextView) v.findViewById(R.id.tvLten);
            tt1.setText(m.getNameT());
            TextView tt2 = (TextView) v.findViewById(R.id.tvEmpty);
            tt2.setText(String.valueOf(m.getEmpty()));
            if(m.getEmpty() == 0){
                tt2.setText("Khách");
                v.setBackgroundColor(Color.YELLOW);
            }else{
                v.setBackgroundColor(Color.WHITE);
                tt2.setText("Trống");
            }
        }
        return v;
    }
}
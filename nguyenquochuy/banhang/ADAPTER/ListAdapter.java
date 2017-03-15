package com.example.dell.banhang.ADAPTER;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dell.banhang.ClassObject.MenuClass;
import com.example.dell.banhang.R;

import java.util.List;

/**
 * Created by DELL on 8/7/2016.
 */
public class ListAdapter extends ArrayAdapter<MenuClass> {
    public ListAdapter (Context context, int textViewResourceID){
        super (context, textViewResourceID);
    }
    public ListAdapter (Context context, int resource, List<MenuClass> items){
        super(context,resource,items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if(v== null){
            LayoutInflater vi ;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.activity_lane_menu,null);
        }
        MenuClass m = getItem(position);
        if (m!=null){
            TextView tt1 = (TextView) v.findViewById(R.id.tvLten);
            tt1.setText(m.Name);
            TextView tt2 = (TextView) v.findViewById(R.id.tvLGia);
            tt2.setText(String.valueOf(m.Price));
            ImageView im = (ImageView) v.findViewById(R.id.imvL);
            Bitmap bitmap = BitmapFactory.decodeByteArray(m.Pic,0,m.Pic.length);
            im.setImageBitmap(bitmap);

            /*
            if(m.getCheck() == 0)
            {
                tt3.setText("Hết hàng");
                v.setBackgroundColor(Color.RED);
            }
            else
            {
                tt3.setText("");
            }*/

        }
        return v;
    }

}
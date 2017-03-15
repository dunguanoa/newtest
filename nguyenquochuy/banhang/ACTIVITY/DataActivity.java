package com.example.dell.banhang.ACTIVITY;

import android.database.Cursor;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.dell.banhang.ADAPTER.DataAdapter;
import com.example.dell.banhang.ClassObject.DebillClass;
import com.example.dell.banhang.DATA.DataDebill;
import com.example.dell.banhang.R;

import java.util.ArrayList;

public class DataActivity extends AppCompatActivity {
    Spinner spDate,spNgay,spThang;
    Button btnXem,btnXem1;
    EditText edtTotal,edtNam;
    ListView lvData;
    public ArrayList<DebillClass>mang1;
    public ArrayList<DebillClass>mang2;
    public ArrayList<DebillClass>mang3;
    public ArrayList<DebillClass>mang4;

    public String[] spngay;
    public String[] spthang;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);
        Anhxa();
        Loadngay();
        spngay = new String[] {
                "","1", "2", "3", "4", "5", "6","7","8","9","10","11","12","13","14","15","16","17","18","19","20","21","22","23","24","25","26","27","28","29","30","31"
        };

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, spngay);
        spNgay.setAdapter(adapter);
        spthang = new String[] {
                "","1", "2", "3", "4", "5", "6","7","8","9","10","11","12"
        };

        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, spthang);
        spThang.setAdapter(adapter1);
        btnXem1.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        Button view = (Button) v;
                        view.getBackground().setColorFilter(0x77000000, PorterDuff.Mode.SRC_ATOP);
                        v.invalidate();
                        break;
                    }
                    case MotionEvent.ACTION_UP:
                        // Your action here on button click
                    {
                        try {
                            Loadthanhtien();
                            Load();

                        } catch (Exception e) {
                            Toast.makeText(getApplicationContext(), "Không có dữ liệu!", Toast.LENGTH_SHORT).show();
                        }
                    }
                    case MotionEvent.ACTION_CANCEL: {
                        Button view = (Button) v;
                        view.getBackground().clearColorFilter();
                        view.invalidate();
                        break;
                    }
                }
                return true;
            }
        });
        btnXem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    if (spNgay.getSelectedItem().toString().length() != 0  && spThang.getSelectedItem().toString().length()!=0 && edtNam.getText().length() != 0) {
                        ngay();
                        Loadthanhtienngay();
                    }
                    if (spNgay.getSelectedItem().toString().length() == 0 && edtNam.getText().length() != 0 && spThang.getSelectedItem().toString().length()!=0) {
                        thang();
                        Loadtienthang();
                    }
                    if (spNgay.getSelectedItem().toString().length() == 0 && spThang.getSelectedItem().toString().length() == 0 && edtNam.getText().length()!=0) {
                        nam();
                        Loadtiennam();
                    }

                }
                catch (Exception ex ){
                    Toast.makeText(getApplicationContext(),"Chưa làm việc ngày nào!",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    public void Anhxa(){
        btnXem = (Button) findViewById(R.id.btnXem);
        edtTotal = (EditText) findViewById(R.id.edtTotal);
        lvData = (ListView) findViewById(R.id.lvData);
        spDate = (Spinner) findViewById(R.id.spDate);
        btnXem1 = (Button)findViewById(R.id.btnXem1);
        spNgay = (Spinner) findViewById(R.id.spNgay);
        spThang = (Spinner) findViewById(R.id.spThang);
        edtNam = (EditText)findViewById(R.id.edtNam);
    }

    public void Load (){
        mang1 = new ArrayList<DebillClass>();
        final DataDebill db = new DataDebill(getApplicationContext(),"VEGAN",null,1);
        Cursor sanpham = db.getData("select 1,name,sum(num),1 from debill d , bill l where d.billid = l.billid and pay = 1 and date = '"+spDate.getSelectedItem().toString()+"' group by(name)");
        while (sanpham.moveToNext()) {
            mang1.add(new DebillClass(sanpham.getInt(0),sanpham.getString(1),sanpham.getInt(2), sanpham.getInt(3)));
        }
        DataAdapter adapter = new DataAdapter(getApplicationContext(),R.layout.activity_lane_data,mang1);
        lvData.setAdapter(adapter);
    }
    public void Loadngay(){

        DataDebill dt = new DataDebill(getApplicationContext(),"VEGAN",null,1);
        ArrayList<String> list = dt.getngay();
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spDate.setAdapter(dataAdapter);
    }
    public void Loadthanhtien() {
        DataDebill dt1 = new DataDebill(getApplicationContext(), "VEGAN", null, 1);
        Cursor cursor1 = dt1.getData("Select sum(TOTAL)from DEBILL D , BILL B where PAY = 1 AND B.BILLID = D.BILLID AND DATE = '"+spDate.getSelectedItem().toString()+"'");
        cursor1.moveToFirst();
        String data1 = "";
        while (cursor1.isAfterLast() == false) {
            data1 += cursor1.getInt(0);
            cursor1.moveToNext();
        }
        edtTotal.setText(data1);
        cursor1.close();
    }
    public void ngay(){
        String ngay1 = spNgay.getSelectedItem().toString() +"/"+ spThang.getSelectedItem().toString() +"/"+ edtNam.getText().toString();
        mang2 = new ArrayList<DebillClass>();
        final DataDebill db = new DataDebill(getApplicationContext(),"VEGAN",null,1);
        Cursor sanpham = db.getData("select 1,name,sum(num),1 from debill d , bill l where d.billid = l.billid and pay = 1 and date = '"+ngay1+"' group by(name)");
        while (sanpham.moveToNext()) {
            mang2.add(new DebillClass(sanpham.getInt(0),sanpham.getString(1),sanpham.getInt(2), sanpham.getInt(3)));
        }
        DataAdapter adapter = new DataAdapter(getApplicationContext(),R.layout.activity_lane_data,mang2);
        lvData.setAdapter(adapter);
    }
    public void Loadthanhtienngay() {
        String ngay1 = spNgay.getSelectedItem().toString() +"/"+ spThang.getSelectedItem().toString() +"/"+ edtNam.getText().toString();
        DataDebill dt1 = new DataDebill(getApplicationContext(), "VEGAN", null, 1);
        Cursor cursor1 = dt1.getData("Select sum(TOTAL)from DEBILL D , BILL B where PAY = 1 AND B.BILLID = D.BILLID AND DATE = '"+ngay1+"'");
        cursor1.moveToFirst();
        String data1 = "";
        while (cursor1.isAfterLast() == false) {
            data1 += cursor1.getInt(0);
            cursor1.moveToNext();
        }
        edtTotal.setText(data1);
        cursor1.close();
    }
    public void thang(){
        mang3 = new ArrayList<DebillClass>();
        final DataDebill db = new DataDebill(getApplicationContext(),"VEGAN",null,1);
        Cursor sanpham = db.getData("select 1,name,sum(num),1 from debill d , bill l where d.billid = l.billid and pay = 1 and date like '%/"+spThang.getSelectedItem().toString()+"/"+edtNam.getText().toString()+"' group by(name)");
        while (sanpham.moveToNext()) {
            mang3.add(new DebillClass(sanpham.getInt(0),sanpham.getString(1),sanpham.getInt(2), sanpham.getInt(3)));
        }
        DataAdapter adapter = new DataAdapter(getApplicationContext(),R.layout.activity_lane_data,mang3);
        lvData.setAdapter(adapter);
    }
    public void Loadtienthang (){
        DataDebill dt1 = new DataDebill(getApplicationContext(), "VEGAN", null, 1);
        Cursor cursor1 = dt1.getData("Select sum(TOTAL)from DEBILL D , BILL B where PAY = 1 AND B.BILLID = D.BILLID AND DATE like '%/"+spThang.getSelectedItem().toString()+"/"+edtNam.getText().toString()+"'");
        cursor1.moveToFirst();
        String data1 = "";
        while (cursor1.isAfterLast() == false) {
            data1 += cursor1.getInt(0);
            cursor1.moveToNext();
        }
        edtTotal.setText(data1);
        cursor1.close();
    }
    public void nam(){
        mang4 = new ArrayList<DebillClass>();
        final DataDebill db = new DataDebill(getApplicationContext(),"VEGAN",null,1);
        Cursor sanpham = db.getData("select 1,name,sum(num),1 from debill d , bill l where d.billid = l.billid and pay = 1 and date like '%/%/"+edtNam.getText().toString()+"' group by(name)");
        while (sanpham.moveToNext()) {
            mang4.add(new DebillClass(sanpham.getInt(0),sanpham.getString(1),sanpham.getInt(2), sanpham.getInt(3)));
        }
        DataAdapter adapter = new DataAdapter(getApplicationContext(),R.layout.activity_lane_data,mang4);
        lvData.setAdapter(adapter);
    }
    public void Loadtiennam (){
        DataDebill dt1 = new DataDebill(getApplicationContext(), "VEGAN", null, 1);
        Cursor cursor1 = dt1.getData("Select sum(TOTAL)from DEBILL D , BILL B where PAY = 1 AND B.BILLID = D.BILLID AND DATE like '%/%/"+edtNam.getText().toString()+"'");
        cursor1.moveToFirst();
        String data1 = "";
        while (cursor1.isAfterLast() == false) {
            data1 += cursor1.getInt(0);
            cursor1.moveToNext();
        }
        edtTotal.setText(data1);
        cursor1.close();
    }


}

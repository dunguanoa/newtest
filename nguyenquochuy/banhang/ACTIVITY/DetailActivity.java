package com.example.dell.banhang.ACTIVITY;

import android.database.Cursor;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.dell.banhang.ADAPTER.DetailAdapter;
import com.example.dell.banhang.ClassObject.BillClass;
import com.example.dell.banhang.ClassObject.DebillClass;
import com.example.dell.banhang.DATA.DataDebill;
import com.example.dell.banhang.R;

import java.util.ArrayList;

public class DetailActivity extends AppCompatActivity {

    Spinner spDate, spBill;
    EditText edtTotal,edtNamee,edtNumm;
    Button btnDelmeal,btnXem;
    ListView lvDetail;
    ArrayList <String> list;
    ArrayList<String> list1;
    public BillClass mBill;
    public DebillClass mDebill;
    ArrayList<DebillClass> mang1;

    AlertDialog b;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Anhxa();
        Loadngay();
        edtNamee.setEnabled(false);
        edtTotal.setEnabled(false);
        edtNumm.setEnabled(false);
        try {
            spDate.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                   Loadbill();
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
        }
        catch (Exception ex){
            Toast.makeText(getApplicationContext(),"Chưa làm việc ngày nào!",Toast.LENGTH_SHORT).show();
        }

        try {
            spBill.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    Loadthanhtien();
                    Load();
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
        }catch (Exception ex){
            Toast.makeText(getApplicationContext(),"Chưa có hóa đơn",Toast.LENGTH_SHORT).show();
        }

        btnDelmeal.setOnTouchListener(new View.OnTouchListener() {
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
                            DataDebill dtt = new DataDebill(getApplicationContext(),"VEGAN",null,1);
                            String a = mDebill.getName().toString();
                            String b = mDebill.getBillID().toString();
                            Integer deletedRows = dtt.Delete_Debill(a.toString(),b.toString());
                            if (deletedRows > 0) {
                                Toast.makeText(getApplicationContext(), "Đã xóa", Toast.LENGTH_SHORT).show();
                                Load();
                                Loadthanhtien();
                                edtNamee.setText("");
                                edtNumm.setText("");
                            }
                        } catch (Exception e) {
                            Toast.makeText(getApplicationContext(), "Chọn món để xóa!", Toast.LENGTH_SHORT).show();
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

        lvDetail.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                mDebill= (DebillClass) adapterView.getAdapter().getItem(i);
                edtNamee.setText(mDebill.getName());
                edtNumm.setText(String.valueOf(mDebill.getNum()));
            }
        });

    }

    public void Anhxa(){
        spDate = (Spinner)findViewById(R.id.spDate);
        spBill = (Spinner)findViewById(R.id.spTime);

        btnDelmeal= (Button)findViewById(R.id.btnDelmeal);
        lvDetail = (ListView)findViewById(R.id.lvDetail);
        btnXem= (Button) findViewById(R.id.btnXem);
        edtTotal = (EditText)findViewById(R.id.edtTotal);
        edtNamee = (EditText)findViewById(R.id.edtNamee);
        edtNumm = (EditText)findViewById(R.id.edtNumm);

    }
    public void Loadngay(){

        DataDebill dt = new DataDebill(getApplicationContext(),"VEGAN",null,1);
        ArrayList<String> list = dt.getngay();
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spDate.setAdapter(dataAdapter);
    }
    public void Loadbill(){
        String s=spDate.getSelectedItem().toString();
        DataDebill dt = new DataDebill(getApplicationContext(),"VEGAN",null,1);
        ArrayList<String> list1 = dt.getBill(s);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,list1);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spBill.setAdapter(dataAdapter);
    }
    public void Loadthanhtien() {
        String i = spBill.getSelectedItem().toString();
        DataDebill dt1 = new DataDebill(getApplicationContext(), "VEGAN", null, 1);
        Cursor cursor1 = dt1.getData("Select sum(TOTAL) FROM DEBILL D, BILL B, BAN N WHERE D.BILLID = B.BILLID AND B.IDTB = N.IDTB AND PAY = 0 AND EMPTY =0 AND NAMET = '"+i+"'");
        cursor1.moveToFirst();
        String data1 = "";
        while (cursor1.isAfterLast() == false) {
            data1 += cursor1.getInt(0);
            cursor1.moveToNext();
        }
        edtTotal.setText(data1);
        cursor1.close();
    }
    public void Load (){
        mang1 = new ArrayList<DebillClass>();
        String s = spBill.getSelectedItem().toString();
        final DataDebill db = new DataDebill(getApplicationContext(),"VEGAN",null,1);
        Cursor sanpham = db.getData("SELECT B.BILLID, NAME, NUM,TOTAL FROM DEBILL D, BILL B, BAN N WHERE D.BILLID = B.BILLID AND B.IDTB = N.IDTB AND PAY = 0 AND EMPTY =0 AND NAMET = '"+s+"'");
        while (sanpham.moveToNext()) {
            mang1.add(new DebillClass(sanpham.getInt(0),sanpham.getString(1),sanpham.getInt(2), sanpham.getInt(3)));
        }
        DetailAdapter adapter = new DetailAdapter(getApplicationContext(),R.layout.activity_lane_detail,mang1);
        lvDetail.setAdapter(adapter);
    }


}

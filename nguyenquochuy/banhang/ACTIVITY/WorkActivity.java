package com.example.dell.banhang.ACTIVITY;

import android.content.DialogInterface;
import android.database.Cursor;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.dell.banhang.ADAPTER.ListAdapter;
import com.example.dell.banhang.ADAPTER.TableAdapter;
import com.example.dell.banhang.ClassObject.BillClass;
import com.example.dell.banhang.ClassObject.MenuClass;
import com.example.dell.banhang.ClassObject.TableClass;
import com.example.dell.banhang.DATA.DataBill;
import com.example.dell.banhang.DATA.DataDebill;
import com.example.dell.banhang.DATA.DataTable;
import com.example.dell.banhang.DATA.Database;
import com.example.dell.banhang.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class WorkActivity extends AppCompatActivity { //rồi làm sao mày hiện cái này lên

    Button btnSave, btnPay, btnAT;
    EditText edtTable, edtBill, edtName, edtPrice, edtNum;
    ListView lvTable, lvMeal;
    ArrayList<TableClass> mangtb;
    public TableClass mTable;
    ArrayList<BillClass> listtb;
    ArrayList<MenuClass> mang;
    public MenuClass mMonan;
    public BillClass mBill;
    EditText edtName1, edtPrice1;
    AlertDialog b;
    ArrayList<String> list1;
    @Override
    protected void onCreate(Bundle savedInstanceState) { // giờ nó chạy được kjhoong
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work);
        Anhxa();
        edtName.setEnabled(false);
        edtPrice.setEnabled(false);
        edtBill.setEnabled(false);
        try {
            Load();
        } catch (Exception ex) {
            Toast.makeText(getApplicationContext(), "Chưa có món", Toast.LENGTH_SHORT).show();
        }
        try {
            LoadTable();// cai nay chay ngoan

        } catch (Exception ex) {
            Toast.makeText(getApplicationContext(), "Chưa có bàn", Toast.LENGTH_SHORT).show();
        }
        final DataTable db = new DataTable(getApplicationContext(), "VEGAN", null, 1);
        db.QueryData("CREATE TABLE IF NOT EXISTS BAN( IDTB INTEGER PRIMARY KEY AUTOINCREMENT ,NAMET VARCHAR,EMPTY INTEGER)");
        btnAT.setOnTouchListener(new View.OnTouchListener() {
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
                            DataTable dt = new DataTable(getApplicationContext(), "VEGAN", null, 1);
                            Cursor cursor = dt.getData("Select IDTB from BAN where NAMET= '"+ edtTable.getText().toString()+"'");
                            cursor.moveToFirst();
                            String data = "";
                            while (cursor.isAfterLast() == false) {
                                data += cursor.getInt(0);
                                cursor.moveToNext();
                            }
                            String code = String.valueOf(data);
                            cursor.close();
                            if (edtTable.getText().length() != 0 && code.length() == 0) {
                                db.INSERT_TABLE(
                                        edtTable.getText().toString(),1);
                                Toast.makeText(getApplicationContext(), "Đã tạo bàn "+edtTable.getText().toString(), Toast.LENGTH_SHORT).show();
                                edtTable.setText("");
                            } else
                                Toast.makeText(getApplicationContext(), "Bàn đã tồn tại hoặc tên bàn trống", Toast.LENGTH_SHORT).show();
                            edtTable.setText("");
                            LoadTable();
                        } catch (Exception e) {
                            Toast.makeText(getApplicationContext(), "Bàn đã tồn tại", Toast.LENGTH_SHORT).show();
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
        lvTable.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                final AlertDialog.Builder b = new AlertDialog.Builder(WorkActivity.this);
                b.setIcon(android.R.drawable.ic_dialog_alert);
                b.setMessage("Xóa bàn "+edtTable.getText().toString());
                b.setPositiveButton("Đồng ý",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int whichButton) {
                                try {
                                    String a = mTable.getIDTB().toString();
                                    DataTable dt = new DataTable(getApplicationContext(), "VEGAN", null, 1);
                                    Cursor cursor = dt.getData("Select EMPTY from BAN where IDTB ="+Integer.parseInt(a));
                                    cursor.moveToFirst();
                                    String data = "";
                                    while (cursor.isAfterLast() == false) {
                                        data += cursor.getInt(0);
                                        cursor.moveToNext();
                                    }
                                    int rs = Integer.parseInt(data);
                                    cursor.close();
                                    if(rs == 1) {
                                        Integer deletedRows = db.Delete_Table(a.toString());
                                        if (deletedRows > 0) {
                                            Toast.makeText(getApplicationContext(), "Đã xóa bàn " + edtTable.getText().toString(), Toast.LENGTH_SHORT).show();
                                            LoadTable();
                                            edtTable.setText("");
                                        }
                                    }
                                    else {
                                        Toast.makeText(getApplicationContext(), "Bàn có khách! Không thể xóa", Toast.LENGTH_SHORT).show();
                                    }
                                }catch(Exception e ){
                                    Toast.makeText(getApplicationContext(), "Vui lòng chọn bàn!", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                b.setNegativeButton("Trở lại",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int whichButton) {
                                dialog.cancel();
                            }
                        });

                b.show();
                return true;
            }
        });
        final DataDebill db2 = new DataDebill(getApplicationContext(), "VEGAN", null, 1);
        final DataBill dt = new DataBill(getApplicationContext(), "VEGAN", null, 1);
        dt.QueryData("CREATE TABLE IF NOT EXISTS BILL( BILLID INTEGER PRIMARY KEY AUTOINCREMENT , IDTB INTEGER, DATE VARCHAR,PAY INTEGER, FOREIGN KEY (IDTB) REFERENCES BAN (IDTB))");
        lvTable.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            final public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                mTable = (TableClass) adapterView.getAdapter().getItem(i);
                final int z = (mTable.getIDTB());
                edtTable.setText(mTable.getNameT());
                try {
                    int empty = (mTable.getEmpty());
                    if(empty==1) {
                        final AlertDialog.Builder b = new AlertDialog.Builder(WorkActivity.this);
                        b.setIcon(android.R.drawable.ic_dialog_alert);
                        b.setMessage("Tạo hóa đơn mới?");
                        b.setPositiveButton("Đồng ý",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int whichButton) {
                                        try {
                                            DataBill dt = new DataBill(getApplicationContext(), "VEGAN", null, 1);
                                            Cursor cursor = dt.getData("Select max(BILLID) from BILL ");
                                            cursor.moveToFirst();
                                            String data = "";
                                            while (cursor.isAfterLast() == false) {
                                                data += cursor.getInt(0);
                                                cursor.moveToNext();
                                            }
                                            String code = String.valueOf(data);
                                            final int c1 = Integer.parseInt(code) + 1;
                                            cursor.close();
                                            Calendar c = GregorianCalendar.getInstance();
                                            int year = c.get(c.YEAR);
                                            int month = c.get(c.MONTH)+1;
                                            int day = c.get(c.DAY_OF_MONTH);
                                            String a = String.valueOf(year);
                                            String b = String.valueOf(month);
                                            String d = String.valueOf(day);
                                            String e = d + "/" + b + "/" + a;
                                            dt.INSERT_BILL(z, e, 0);
                                            db.UPDATE_TABLE(edtTable.getText().toString(), 0);
                                            LoadTable();
                                            edtBill.setText("");
                                            Toast.makeText(getApplicationContext(), "Đã tạo hóa đơn " + c1, Toast.LENGTH_SHORT).show();
                                        } catch (Exception e) {
                                            Toast.makeText(getApplicationContext(), "Không thể tạo", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                        b.setNegativeButton("Trở lại",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,
                                                        int whichButton) {
                                        dialog.cancel();
                                    }
                                });
                        b.show();
                    }
                    else
                    {
                        Cursor cursor = db2.getData("SELECT SUM(TOTAL) FROM DEBILL D , BILL B , BAN N WHERE D.BILLID = B.BILLID AND B.IDTB= N.IDTB AND EMPTY = 0 AND PAY = 0 and N.NAMET ='"+edtTable.getText().toString()+"'");
                        cursor.moveToFirst();
                        String data = "";
                        while (cursor.isAfterLast() == false) {
                            data += cursor.getInt(0);
                            cursor.moveToNext();
                        }
                        String code = String.valueOf(data);
                       // int c = Integer.parseInt(code);
                        edtBill.setText(code);
                        cursor.close();
                    }
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(),"", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btnPay.setOnTouchListener(new View.OnTouchListener() {
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
                            Cursor cursor = db2.getData("SELECT BILLID FROM BILL B , BAN N WHERE B.IDTB= N.IDTB AND EMPTY = 0 AND PAY = 0 and N.NAMET ='"+edtTable.getText().toString()+"'");
                            cursor.moveToFirst();
                            String data = "";
                            while (cursor.isAfterLast() == false) {
                                data += cursor.getInt(0);
                                cursor.moveToNext();
                            }
                            String code = String.valueOf(data);
                            int c = Integer.parseInt(code);
                            cursor.close();
                            db.UPDATE_TABLE(edtTable.getText().toString(),1);
                            dt.UPDATE_BILL(c, 1);
                            LoadTable();
                            edtBill.setText("");
                            edtName.setText("");
                            edtPrice.setText("");
                            Toast.makeText(getApplicationContext(), "Đã thanh toán bàn "+ edtTable.getText().toString(), Toast.LENGTH_SHORT).show();
                        }
                        catch(Exception ex){
                            Toast.makeText(getApplicationContext(), " Vui lòng chọn bàn thanh toán", Toast.LENGTH_SHORT).show();
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
        db2.QueryData("CREATE TABLE IF NOT EXISTS DEBILL( BILLID INTEGER, NAME VARCHAR, NUM INTEGER, TOTAL INTEGER,FOREIGN KEY (BILLID) REFERENCES BILL(BILLID))");
        lvMeal.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                mMonan = (MenuClass) adapterView.getAdapter().getItem(i);
                edtName.setText(mMonan.getName());
                edtPrice.setText(String.valueOf(mMonan.getPrice()));

            }
        });
        btnSave.setOnTouchListener(new View.OnTouchListener() {
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
                            DataBill dt = new DataBill(getApplicationContext(), "VEGAN", null, 1);
                            Cursor cursor = dt.getData("Select BILLID from BILL B, BAN N where B.IDTB = N.IDTB and pay = 0 and EMPTY = 0 and NAMET ='"+edtTable.getText().toString()+"'");
                            cursor.moveToFirst();
                            String data = "";
                            while (cursor.isAfterLast() == false) {
                                data += cursor.getInt(0);
                                cursor.moveToNext();
                            }
                            String code = String.valueOf(data);
                            final int c = Integer.parseInt(code);
                            cursor.close();
                            db2.INSERT_DEBILL(c, edtName.getText().toString(), Integer.parseInt(edtNum.getText().toString()), Integer.parseInt(edtPrice.getText().toString()) * Integer.parseInt(edtNum.getText().toString()));
                            Toast.makeText(getApplicationContext(), "Đã thêm"+edtName.getText().toString(), Toast.LENGTH_SHORT).show();
                            Loadthanhtien();
                            edtName.setText("");
                            edtPrice.setText("");
                            edtNum.setText("");
                        } catch (Exception e) {
                            Toast.makeText(getApplicationContext(), "Vui lòng chọn bàn", Toast.LENGTH_SHORT).show();
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
    }
    public void Anhxa() {
        btnSave = (Button) findViewById(R.id.btnSave);
        btnPay = (Button) findViewById(R.id.btnPay);
        btnAT = (Button) findViewById(R.id.btnAT);
        edtNum = (EditText) findViewById(R.id.edtNum);
        edtTable = (EditText) findViewById(R.id.edtTable);
        lvTable = (ListView) findViewById(R.id.lvTable);
        lvMeal = (ListView) findViewById(R.id.lvMeal);
        edtName = (EditText) findViewById(R.id.edtName);
        edtPrice = (EditText) findViewById(R.id.edtPrice);
        edtBill = (EditText) findViewById(R.id.edtBill);
    }
        //load table ra listview
    public void LoadTable() {

        mangtb = new ArrayList<TableClass>();
        DataTable db = new DataTable(getApplicationContext(), "VEGAN", null, 1);
        Cursor sanpham1 = db.getData("SELECT * FROM BAN");
        while (sanpham1.moveToNext()) {
            mangtb.add(new TableClass(sanpham1.getInt(0), sanpham1.getString(1),sanpham1.getInt(2)));
        }
        TableAdapter adapter = new TableAdapter(getApplicationContext(), R.layout.activity_lane_table, mangtb);
        lvTable.setAdapter(adapter);
    }


    public void Load() {
        mang = new ArrayList<MenuClass>();
        final Database db = new Database(getApplicationContext(), "VEGAN", null, 1);
        Cursor sanpham = db.getData("SELECT * FROM MENU");
        while (sanpham.moveToNext()) {
            mang.add(new MenuClass(sanpham.getInt(0), sanpham.getString(1), sanpham.getInt(2), sanpham.getBlob(3)));
        }
        ListAdapter adapter = new ListAdapter(getApplicationContext(), R.layout.activity_lane_menu, mang);
        lvMeal.setAdapter(adapter);
    }
    public void Loadthanhtien() {

        DataBill dt = new DataBill(getApplicationContext(), "VEGAN", null, 1);
        //Cursor cursor = dt.getData("Select max(BILLID) from BILL ");
        Cursor cursor = dt.getData("Select BILLID from BILL B, BAN N where B.IDTB = N.IDTB AND PAY = 0 AND EMPTY = 0 AND NAMET = '"+edtTable.getText().toString()+"'");
        cursor.moveToFirst();
        String data = "";
        while (cursor.isAfterLast() == false) {
            data += cursor.getInt(0);
            cursor.moveToNext();
        }
        String code = String.valueOf(data);
        int c1 = Integer.parseInt(code);
        cursor.close();
        DataDebill dt1 = new DataDebill(getApplicationContext(), "VEGAN", null, 1);
        Cursor cursor1 = dt1.getData("SELECT SUM(TOTAL) FROM DEBILL D, BILL B, BAN N WHERE D.BILLID= B.BILLID AND EMPTY = 0 AND PAY = 0 AND B.IDTB = N.IDTB AND B.BILLID = "+c1+" AND NAMET = '"+edtTable.getText().toString()+"'");
        cursor1.moveToFirst();
        String data1 = "";
        while (cursor1.isAfterLast() == false) {
            data1 += cursor1.getInt(0);
            cursor1.moveToNext();
        }
        edtBill.setText(data1);
        cursor1.close();
    }
}

package com.example.dell.banhang.ACTIVITY;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.PorterDuff;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.dell.banhang.ADAPTER.ListAdapter;
import com.example.dell.banhang.ClassObject.MenuClass;
import com.example.dell.banhang.DATA.Database;
import com.example.dell.banhang.R;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class MenuActivity extends AppCompatActivity {

    EditText edtName, edtPrice;
    ImageView imvPic;
    ListView lvList;
    Button btnAdd, btnF5,btnDel;
    ArrayList<MenuClass> mang;
    public MenuClass mMonan;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        Anhxa();
        try{
            Load();
        }catch (Exception ex){
            Toast.makeText(getApplicationContext(), "Chưa có món",Toast.LENGTH_SHORT).show();
        }

        final Database db = new Database(getApplicationContext(),"VEGAN", null, 1);
         db.QueryData("CREATE TABLE IF NOT EXISTS MENU(id INTEGER PRIMARY KEY AUTOINCREMENT, TEN VARCHAR, GIA INTEGER, HINH BLOB)");

        lvList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mMonan= (MenuClass) parent.getAdapter().getItem(position);
                edtName.setText(mMonan.getName());
                edtPrice.setText(String.valueOf(mMonan.getPrice()));
            }
        });
        imvPic.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent maychuphinh = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(maychuphinh, 9999);
            }
        });
        btnF5.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent event){
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        Button view = (Button) v;
                        view.getBackground().setColorFilter(0x77000000,PorterDuff.Mode.SRC_ATOP);
                        v.invalidate();
                        break;
                    }
                    case MotionEvent.ACTION_UP:
                        // Your action here on button click
                    {
                        try{
                            String a = mMonan.getID().toString();

                            db.Update_MENU(Integer.parseInt(a.toString()),
                                    edtName.getText().toString(),
                                    Integer.parseInt(edtPrice.getText().toString()),
                                    Image_To_Byte(imvPic)
                            );
                            Toast.makeText(getApplicationContext(), "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                            Load();
                            edtName.setText("");
                            edtPrice.setText("");
                            imvPic.setImageResource(R.drawable.diet);
                        }catch(Exception e){
                            Toast.makeText(getApplicationContext(), "Vui lòng chọn món!", Toast.LENGTH_SHORT).show();
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
        btnAdd.setOnTouchListener(new View.OnTouchListener() {
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
                            db.INSERT_MENU(
                                    edtName.getText().toString(),
                                    Integer.parseInt(edtPrice.getText().toString()),
                                    Image_To_Byte(imvPic)
                            );
                            Toast.makeText(getApplicationContext(), "Đã thêm vào menu",Toast.LENGTH_SHORT).show();
                            edtName.setText("");
                            edtPrice.setText("");
                            imvPic.setImageResource(R.drawable.diet);
                            Load();
                        }catch (Exception e){
                            Toast.makeText(getApplicationContext(),"Chưa nhập tên và giá",Toast.LENGTH_SHORT).show();
                        }
                    }
                    case MotionEvent.ACTION_CANCEL:{
                        Button view = (Button) v;
                        view.getBackground().clearColorFilter();
                        view.invalidate();
                        break;
                    }
                }
                return true;
            }
        });
        btnDel.setOnTouchListener(new View.OnTouchListener() {
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
                            String a = mMonan.getID().toString();
                            Integer deletedRows = db.Delete_MONAN(a.toString());
                            if (deletedRows > 0) {
                                Toast.makeText(getApplicationContext(), "Đã xóa", Toast.LENGTH_SHORT).show();
                                Load();
                                edtName.setText("");
                                edtPrice.setText("");
                                imvPic.setImageResource(R.drawable.diet);
                            }
                        }catch(Exception e ){
                            Toast.makeText(getApplicationContext(), "Chọn món xóa", Toast.LENGTH_SHORT).show();
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
    public void Anhxa(){
        edtName = (EditText)findViewById(R.id.edtName);
        edtPrice = (EditText)findViewById(R.id.edtPrice);
        imvPic = (ImageView)findViewById(R.id.imvPic);
        lvList = (ListView)findViewById(R.id.lvList);
        btnAdd = (Button) findViewById(R.id.btnAdd);
        btnF5 = (Button) findViewById(R.id.btnF5);
        btnDel = (Button) findViewById(R.id.btnDel);

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == 9999 && resultCode == RESULT_OK){
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            imvPic.setImageBitmap(photo);
        }
    }
    public byte [] Image_To_Byte(ImageView h){
        BitmapDrawable drawable = (BitmapDrawable)h.getDrawable();
        Bitmap bmp = drawable.getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG,100,stream);
        byte[]byteArray = stream.toByteArray();
        return byteArray;
    }
    public void Load (){
        mang = new ArrayList<MenuClass>();
        final Database db = new Database(getApplicationContext(),"VEGAN",null,1);
        Cursor sanpham = db.getData("SELECT * FROM MENU");
        while (sanpham.moveToNext()){
            mang.add(new MenuClass(sanpham.getInt(0), sanpham.getString(1), sanpham.getInt(2), sanpham.getBlob(3)));
        }
        ListAdapter adapter = new ListAdapter(getApplicationContext(),R.layout.activity_lane_menu,mang);
        lvList.setAdapter(adapter);
    }
}

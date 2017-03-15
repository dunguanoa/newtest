package com.example.dell.banhang.DATA;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

import com.example.dell.banhang.ClassObject.MenuClass;

import java.util.ArrayList;

/**
 * Created by DELL on 8/7/2016.
 */
public class Database extends SQLiteOpenHelper {

    public Database (Context context, String name, SQLiteDatabase.CursorFactory factory, int version){
        super (context,name, factory,version);
    }
    public void QueryData(String sql){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(sql);
    }
    public Cursor getData (String sql){
        SQLiteDatabase db = getWritableDatabase();
        return db.rawQuery(sql,null);
    }
    public void INSERT_MENU( String tenmon, Integer giamon,byte[] hinh){
        SQLiteDatabase db= getWritableDatabase();
        String sql= "INSERT INTO MENU VALUES(null,?,?,?)";
        SQLiteStatement statement= db.compileStatement(sql);
        statement.clearBindings();
        statement.bindString(1,tenmon);
        statement.bindDouble(2,giamon);
        statement.bindBlob(3,hinh);
        statement.executeInsert();
    }
    public void Update_MENU( int id,String ten, Integer gia, byte[] hinh){
        SQLiteDatabase db= getWritableDatabase();
        String sql= "UPDATE MENU SET  ten= @ten , gia = @gia , hinh = @hinh WHERE id = " +id;
        SQLiteStatement statement= db.compileStatement(sql);
        statement.clearBindings();
        statement.bindString(1,ten);
        statement.bindDouble(2,gia);
        statement.bindBlob(3,hinh);
        statement.executeUpdateDelete();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    public Integer Delete_MONAN( String id){

        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("MENU","id = ?",new String[]{id});
    }
    public ArrayList<MenuClass> getAllitems (){

        ArrayList<MenuClass> list = new ArrayList<MenuClass>();
        SQLiteDatabase db = this.getReadableDatabase();
        db.beginTransaction();
        try{
            String selectSql = "SELECT * FROM MENU";
            Cursor cr = db.rawQuery(selectSql,null);
            if (cr.getCount()>0){
                while (cr.moveToNext()){
                    list.add(new MenuClass(cr.getInt(0), cr.getString(1), cr.getInt(2), cr.getBlob(3)));
                }
            }
            db.setTransactionSuccessful();
        }catch(Exception e){
            e.printStackTrace();
        }
        finally {
            db.endTransaction();
            db.close();
        }
        return list;

    }

}

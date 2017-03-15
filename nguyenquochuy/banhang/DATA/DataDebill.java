package com.example.dell.banhang.DATA;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

import com.example.dell.banhang.ClassObject.DebillClass;

import java.util.ArrayList;

/**
 * Created by DELL on 10/19/2016.
 */

public class DataDebill extends SQLiteOpenHelper {

    public DataDebill(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public void QueryData(String sql) {
        SQLiteDatabase dbtb = getWritableDatabase();
        dbtb.execSQL(sql);
    }

    public Cursor getData(String sql) {
        SQLiteDatabase dbtb = getWritableDatabase();
        return dbtb.rawQuery(sql, null);
    }

    public void INSERT_DEBILL(Integer billid, String name, Integer num , Integer total ) {
        SQLiteDatabase dbtb = getWritableDatabase();
        String sql = "INSERT INTO DEBILL VALUES(?,?,?,?)";
        SQLiteStatement statement = dbtb.compileStatement(sql);
        statement.clearBindings();
        statement.bindDouble(1, billid );
        statement.bindString(2, name);
        statement.bindDouble(3, num);
        statement.bindDouble(4,total);
        statement.executeInsert();
    }
    public void Update_Debill(String name, Integer num, Integer total){
        SQLiteDatabase db= getWritableDatabase();
        String sql= "UPDATE DEBILL SET num = @num ,total = @total where NAME = '"+name+"'";
        SQLiteStatement statement= db.compileStatement(sql);
        statement.clearBindings();
        statement.bindDouble(2,num);
        statement.bindDouble(3,total);
        statement.executeUpdateDelete();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    public ArrayList<DebillClass> getAllitems (){

        ArrayList<DebillClass> list = new ArrayList<DebillClass>();
        SQLiteDatabase db = this.getReadableDatabase();
        db.beginTransaction();
        try{
            String selectSql = "SELECT * FROM DEBILL";
            Cursor cr = db.rawQuery(selectSql,null);
            if (cr.getCount()>0){
                while (cr.moveToNext()){
                    list.add(new DebillClass( cr.getInt(0),cr.getString(1),cr.getInt(2),cr.getInt(3)));
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
    public ArrayList<String> getThongtin () {
        ArrayList<String> list = new ArrayList<String>();
        SQLiteDatabase db = this.getReadableDatabase();
        db.beginTransaction();
        try {
            String sql = "SELECT DISTINCT BILLID FROM BILLID";
            Cursor cursor = db.rawQuery(sql, null);
            if (cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                    String ngay = cursor.getString(cursor.getColumnIndex("BILLID"));
                    list.add(ngay);
                }
            }
            db.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
            db.close();
        }
        return list;
    }
    public ArrayList<String> getngay () {
        ArrayList<String> list = new ArrayList<String>();
        SQLiteDatabase db = this.getReadableDatabase();
        db.beginTransaction();
        try {
            String sql = "SELECT DISTINCT DATE FROM BILL";
            Cursor cursor = db.rawQuery(sql, null);
            if (cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                    String ngay = cursor.getString(cursor.getColumnIndex("DATE"));
                    list.add(ngay);
                }
            }
            db.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
            db.close();
        }
        return list;
    }
    public ArrayList<String> getBill (String date) {
        ArrayList<String> list = new ArrayList<String>();
        SQLiteDatabase db = this.getReadableDatabase();
        db.beginTransaction();
        try {
            String sql = "SELECT NAMET FROM BAN N, BILL B  where N.IDTB=B.IDTB AND PAY = 0 AND DATE ='"+date+"'";
            Cursor cursor = db.rawQuery(sql, null);
            if (cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                    String id = cursor.getString(cursor.getColumnIndex("NAMET"));
                    list.add(id);
                }
            }
            db.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
            db.close();
        }
        return list;
    }
    public Integer Delete_Debill( String name, String id){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("DEBILL","NAME = ? AND BILLID =? ", new String[]{name,id});
    }


}
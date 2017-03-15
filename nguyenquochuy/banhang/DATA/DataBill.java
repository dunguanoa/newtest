package com.example.dell.banhang.DATA;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

import com.example.dell.banhang.ClassObject.BillClass;

import java.util.ArrayList;

/**
 * Created by DELL on 9/27/2016.
 */

public class DataBill extends SQLiteOpenHelper {

    public DataBill(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
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

    public void INSERT_BILL(Integer idtb,String date,Integer pay  ) {
        SQLiteDatabase dbtb = getWritableDatabase();
        String sql = "INSERT INTO BILL VALUES(null,?,?,?)";
        SQLiteStatement statement = dbtb.compileStatement(sql);
        statement.clearBindings();
        statement.bindDouble(1, idtb);
        statement.bindString(2, date);
        statement.bindDouble(3,pay);
        statement.executeInsert();
    }

    public void UPDATE_BILL(Integer billid,Integer pay){
        SQLiteDatabase dbtb = getWritableDatabase();
        String sql = "UPDATE BILL SET pay = @pay WHERE BILLID ="+billid;
        SQLiteStatement statement = dbtb.compileStatement(sql);
        statement.clearBindings();
        statement.bindDouble(1, pay);
        statement.executeUpdateDelete();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    public ArrayList<BillClass> getAllitems (){

        ArrayList<BillClass> list = new ArrayList<BillClass>();
        SQLiteDatabase db = this.getReadableDatabase();
        db.beginTransaction();
        try{
            String selectSql = "SELECT * FROM BILL";
            Cursor cr = db.rawQuery(selectSql,null);
            if (cr.getCount()>0){
                while (cr.moveToNext()){
                    list.add(new BillClass( cr.getInt(0),cr.getInt(2),cr.getString(1),cr.getInt(3)));
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
    public ArrayList<String> getIdbill () {
        ArrayList<String> list = new ArrayList<String>();
        SQLiteDatabase db = this.getReadableDatabase();
        db.beginTransaction();
        try {
            String sql = "SELECT BILLID FROM BILL";
            Cursor cursor = db.rawQuery(sql, null);
            if (cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                    String id = cursor.getString(cursor.getColumnIndex("BILLID"));
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
    public Integer Delete_BILL( String id){

        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("BILL","BILLID = ?",new String[]{id});
    }

}
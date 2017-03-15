package com.example.dell.banhang.DATA;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

import com.example.dell.banhang.ClassObject.TableClass;

import java.util.ArrayList;

/**
 * Created by DELL on 8/8/2016.
 */
public class DataTable extends SQLiteOpenHelper {

    public DataTable(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
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

    public void INSERT_TABLE(String tenban,Integer empty) {
        SQLiteDatabase dbtb = getWritableDatabase();
        String sql = "INSERT INTO BAN VALUES(null,?,?)";
        SQLiteStatement statement = dbtb.compileStatement(sql);
        statement.clearBindings();
        statement.bindString(1, tenban);
        statement.bindDouble(2, empty);
        statement.executeInsert();
    }
    public void UPDATE_TABLE(String nameT,Integer empty) {
        SQLiteDatabase dbtb = getWritableDatabase();
        String sql = "UPDATE BAN SET empty=@empty where NAMET = '"+nameT+"'";
        SQLiteStatement statement = dbtb.compileStatement(sql);
        statement.clearBindings();
        statement.bindDouble(1, empty);
        statement.executeUpdateDelete();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    public ArrayList<TableClass> getAllitems (){

        ArrayList<TableClass> list = new ArrayList<TableClass>();
        SQLiteDatabase db = this.getReadableDatabase();
        db.beginTransaction();
        try{
            String selectSql = "SELECT * FROM BAN";
            Cursor cr = db.rawQuery(selectSql,null);
            if (cr.getCount()>0){
                while (cr.moveToNext()){
                    list.add(new TableClass( cr.getInt(0), cr.getString(1),cr.getInt(2)));
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
    public Integer Delete_Table( String id){

        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("BAN","IDTB = ?",new String[]{id});
    }
    /*
    public List<TableClass> getItems() {
        List<TableClass> itemObjects = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from BAN order by id DESC", null);
        try {
            if (cursor.moveToFirst()) {
                while (cursor.isAfterLast() == false) {//Cách lấy dữ liệu từ curssor
                    int id = cursor.getInt(cursor
                            .getColumnIndex("idtb"));
                    String name = cursor.getString(cursor
                            .getColumnIndex("namet"));
                    itemObjects.add(new TableClass(id,name));
                    cursor.moveToNext();
                }
            }
            return itemObjects;
        } finally {
            db.close();
            cursor.close();
        }

    }
    /*
    public TableClass getItem(int itemId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from BAN WHERE idtb =" + itemId, null);
        try {
            TableClass table = null;
            if (cursor.moveToFirst()) {
                int id = cursor.getInt(cursor
                        .getColumnIndex("idtb"));
                String nt = cursor.getString(cursor
                        .getColumnIndex("NAMET"));
                table = new TableClass(id, nt);
            }
            return table;
        } finally {
            db.close();
            cursor.close();
        }

    }
    public boolean insertItem(TableClass itemObject) {
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            ContentValues itemContentValues = new ContentValues();
//            itemContentValues.put("id", itemObject.getId());

            itemContentValues.put("namet", itemObject.getNameT());
              try {
                db.insert("BAN", null, itemContentValues);
            } catch (Exception ex) {
                return false;
            }
            return true;
        } finally {
            db.close();
        }
    }

    public boolean updateItem(TableClass  itemObject) {//ghi file trc khi call
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            ContentValues itemContentValues = new ContentValues();
//            itemContentValues.put("id", itemObject.getId());

            itemContentValues.put("namet", itemObject.getNameT());
            try {
                db.update("BAN", itemContentValues, "idtb=" + itemObject.getIDTB(), null);
            } catch (Exception ex) {

                return false;
            }

            return true;
        } finally {
            db.close();
        }
    }


    public boolean deleteItem(int id) {
            boolean result = true;
            SQLiteDatabase db = this.getWritableDatabase();
            try {
                db.delete("BAN", "IDTB = " + id, null);
            } catch (Exception ex) {
                result = false;
            } finally {
                db.close();
                return result;
            }
        }*/

}
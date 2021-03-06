package com.zrz.inventory.dao.impl;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.zrz.inventory.bean.Receipts;
import com.zrz.inventory.bean.User;
import com.zrz.inventory.dao.ReceiptsDao;
import com.zrz.inventory.database.DBHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 周瑞忠
 * @description java类作用描述
 * @date 2019/4/1 22:27
 */
public class ReceiptsDaoImpl implements ReceiptsDao {

    private DBHelper dbHelper;
    private SQLiteDatabase database;
    private ContentValues values;

    public ReceiptsDaoImpl(Context context) {
        dbHelper = new DBHelper(context);
    }

    @Override
    public List<Receipts> findAll(Integer currentPage, Integer pageSize) {
        List<Receipts> list = new ArrayList<>(10);
        database = dbHelper.getReadableDatabase();
        //方式一：sql语句操作数据库
//        String sql = "select * from " + DBHelper.TABLENAME + " where " + DBHelper.COLNUMNAME + " = ?";
//        Cursor cursor = database.rawQuery(sql, new String[]{name});
        //方法二：封装的api操作，直接操作方法即可
        Cursor cursor = database.query("receipts", new String[]{"id","number","count","matched"}, null, null, null, null, null, ((currentPage - 1) * pageSize) + "," + (currentPage * pageSize));
        Receipts receipts = null;
        while(cursor.moveToNext()){
            receipts = new Receipts();
            receipts.setCount(cursor.getString(cursor.getColumnIndex("count")));
            receipts.setMatched(cursor.getString(cursor.getColumnIndex("matched")));
            receipts.setNumber(cursor.getString(cursor.getColumnIndex("number")));
            receipts.setId(cursor.getInt(cursor.getColumnIndex("id")));
            list.add(receipts);
        }
        cursor.close();
        return list;
    }

    @Override
    public Receipts findById(Integer id){
        database = dbHelper.getReadableDatabase();
        Cursor cursor = database.query("receipts", new String[]{"id","number","count","matched"}, "id = ?", new String[]{id.toString()}, null, null, null, null);
        Receipts receipts = new Receipts();
        while(cursor.moveToNext()){
            receipts.setCount(cursor.getString(cursor.getColumnIndex("count")));
            receipts.setMatched(cursor.getString(cursor.getColumnIndex("matched")));
            receipts.setNumber(cursor.getString(cursor.getColumnIndex("number")));
            receipts.setId(cursor.getInt(cursor.getColumnIndex("id")));
        }
        return receipts;
    }

    @Override
    public void add(String number) {
        database = dbHelper.getWritableDatabase();
        //方式一：sql语句操作数据库
//        String sql = "insert into users(uName,uPass) values(?,?)";
//        database.execSQL(sql,new Object[]{user.getUserName(),user.getUserPass()});

        //方法二：封装的api操作，直接操作方法即可
        values = new ContentValues();
        values.put("number", number);
        database.insert("receipts", null, values);
    }

    @Override
    public void delete(List<Integer> id) {
        database = dbHelper.getWritableDatabase();
        for (Integer integer: id) {
            database.delete("receipts", "id=?", new String[]{integer.toString()});
        }
    }

    @Override
    public void updateCountById(Integer id, Integer count) {
        database = dbHelper.getWritableDatabase();
        values = new ContentValues();

        Cursor cursor = database.query("receipts", new String[]{"id","number","count","matched"}, "id = ?", new String[]{id.toString()}, null, null, null, null);
        while(cursor.moveToNext()){
            String temp = cursor.getString(cursor.getColumnIndex("count"));
            values.put("count", count + Integer.parseInt(temp));
        }
        database.update("receipts", values,"id = ?", new String[]{id.toString()});
    }

    @Override
    public void updateMatchedById(Integer id, Integer matched) {
        database = dbHelper.getWritableDatabase();
        values = new ContentValues();
        Cursor cursor = database.query("receipts", new String[]{"id","number","count","matched"}, "id = ?", new String[]{id.toString()}, null, null, null, null);
        while(cursor.moveToNext()){
            String temp = cursor.getString(cursor.getColumnIndex("matched"));
            values.put("matched", matched + Integer.parseInt(temp));
        }
        database.update("receipts", values,"id = ?", new String[]{id.toString()});
    }
}

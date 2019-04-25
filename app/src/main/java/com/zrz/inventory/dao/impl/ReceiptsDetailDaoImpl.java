package com.zrz.inventory.dao.impl;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import com.zrz.inventory.bean.Receipts;
import com.zrz.inventory.bean.ReceiptsDetail;
import com.zrz.inventory.dao.ReceiptsDetailDao;
import com.zrz.inventory.database.DBHelper;

import java.util.ArrayList;
import java.util.List;

public class ReceiptsDetailDaoImpl implements ReceiptsDetailDao {

    private DBHelper dbHelper;
    private SQLiteDatabase database;
    private ContentValues values;

    public ReceiptsDetailDaoImpl(Context context) {
        dbHelper = new DBHelper(context);
    }

    @Override
    public void add(ReceiptsDetail receiptsDetail) {
        database = dbHelper.getWritableDatabase();
        //方式一：sql语句操作数据库
//        String sql = "insert into users(uName,uPass) values(?,?)";
//        database.execSQL(sql,new Object[]{user.getUserName(),user.getUserPass()});

        //方法二：封装的api操作，直接操作方法即可
        values = new ContentValues();
        values.put("receipts", receiptsDetail.getReceiptsId());
        values.put("item1", receiptsDetail.getItem1());
        values.put("item2", receiptsDetail.getItem2());
        values.put("item3", receiptsDetail.getItem3());
        values.put("item4", receiptsDetail.getItem4());
        long add = database.insert("receipts_detail", null, values);
        Log.d("receipts_detail_add", add == 1 ? "添加成功" : "添加失败");
    }

    @Override
    public ReceiptsDetail findByRfid(Integer receiptsId, String rfidData) {
        database = dbHelper.getReadableDatabase();
        //方式一：sql语句操作数据库
//        String sql = "select * from " + DBHelper.TABLENAME + " where " + DBHelper.COLNUMNAME + " = ?";
//        Cursor cursor = database.rawQuery(sql, new String[]{name});
        //方法二：封装的api操作，直接操作方法即可
        Cursor cursor = database.query("receipts_detail", new String[]{"id", "receipts","item1","item2","item3", "item4"}, "receipts = ? and item4 = ? ", new String[]{receiptsId.toString(), rfidData}, null, null, null, null);
        ReceiptsDetail detail = null;
        while(cursor.moveToNext()){
            detail = new ReceiptsDetail();
            detail.setReceiptsId(cursor.getInt(cursor.getColumnIndex("receipts")));
            detail.setItem1(cursor.getString(cursor.getColumnIndex("item1")));
            detail.setItem2(cursor.getString(cursor.getColumnIndex("item2")));
            detail.setItem3(cursor.getString(cursor.getColumnIndex("item3")));
            detail.setItem4(cursor.getString(cursor.getColumnIndex("item4")));
            detail.setId(cursor.getInt(cursor.getColumnIndex("id")));
        }
        cursor.close();
        return detail;
    }

    @Override
    public List<ReceiptsDetail> find(Integer receiptsId, Integer currentPage, Integer pageSize) {
        List<ReceiptsDetail> list = new ArrayList<>(10);
        database = dbHelper.getReadableDatabase();
        //方式一：sql语句操作数据库
//        String sql = "select * from " + DBHelper.TABLENAME + " where " + DBHelper.COLNUMNAME + " = ?";
//        Cursor cursor = database.rawQuery(sql, new String[]{name});
        //方法二：封装的api操作，直接操作方法即可
        Cursor cursor = database.query("receipts_detail", new String[]{"id", "receipts","item1","item2","item3", "item4"}, "receipts = ?", new String[]{receiptsId.toString()}, null, null, null, ((currentPage - 1) * pageSize) + "," + (currentPage * pageSize));
        ReceiptsDetail detail = null;
        while(cursor.moveToNext()){
            detail = new ReceiptsDetail();
            detail.setReceiptsId(cursor.getInt(cursor.getColumnIndex("receipts")));
            detail.setItem1(cursor.getString(cursor.getColumnIndex("item1")));
            detail.setItem2(cursor.getString(cursor.getColumnIndex("item2")));
            detail.setItem3(cursor.getString(cursor.getColumnIndex("item3")));
            detail.setItem4(cursor.getString(cursor.getColumnIndex("item4")));
            detail.setId(cursor.getInt(cursor.getColumnIndex("id")));
            list.add(detail);
        }
        cursor.close();
        return list;
    }

    @Override
    public List<ReceiptsDetail> find(Integer receiptsId) {
        List<ReceiptsDetail> list = new ArrayList<>(10);
        database = dbHelper.getReadableDatabase();
        Cursor cursor = database.query("receipts_detail", new String[]{"id", "receipts","item1","item2","item3", "item4"}, "receipts = ?", new String[]{receiptsId.toString()}, null, null, null, null);
        ReceiptsDetail detail = null;
        while(cursor.moveToNext()){
            detail = new ReceiptsDetail();
            detail.setReceiptsId(cursor.getInt(cursor.getColumnIndex("receipts")));
            detail.setItem1(cursor.getString(cursor.getColumnIndex("item1")));
            detail.setItem2(cursor.getString(cursor.getColumnIndex("item2")));
            detail.setItem3(cursor.getString(cursor.getColumnIndex("item3")));
            detail.setItem4(cursor.getString(cursor.getColumnIndex("item4")));
            detail.setId(cursor.getInt(cursor.getColumnIndex("id")));
            list.add(detail);
        }
        cursor.close();
        return list;
    }
}

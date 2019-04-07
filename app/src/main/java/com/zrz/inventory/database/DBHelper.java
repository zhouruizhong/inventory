package com.zrz.inventory.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * @author 周瑞忠
 * 数据库封装类
 * @date 2019/3/30 17:56
 */
public class DBHelper extends SQLiteOpenHelper {

    /**
     * 数据库名
     */
    private static final String DBNAME = "inventory.db";

    /**
     * 版本号
     */
    public static final int VERSION = 2;

    /**
     * 表名
     */
    public static final String TABLENAME = "users";
    /**
     * 列名
     */
    public static final String COLNUMNAME = "uName";
    /**
     * 列名
     */
    public static final String COLNUMPASS = "uPass";

    public DBHelper(Context context) {
        super(context, DBNAME, null, VERSION);
    }

    /**
     * 该方法只执行一次，首次执行创建表
     *
     * @param db
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        String receipts = "create table receipts (id integer primary key autoincrement,number varchar(20) default '' not null, matched integer default 0 not null, count integer default 0 not null)";
        String receipts_detail = "create table receipts_detail (id integer primary key autoincrement, receipts integer not null default 0, item1 varchar(20) default '' not null, item2 varchar(20) default '' not null, item3 varchar(20) default '' not null, item4 varchar(20) default '' not null)";
        db.execSQL(receipts);
        db.execSQL(receipts_detail);
    }

    /**
     * 版本更新调用该方法
     *
     * @param db
     * @param oldVersion
     * @param newVersion
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String receipts = "create table receipts (id integer primary key autoincrement,number varchar(20) default '' not null, matched integer default 0 not null, count integer default 0 not null)";
        String receipts_detail = "create table receipts_detail (id integer primary key autoincrement, receipts integer not null default 0, item1 varchar(20) default '' not null, item2 varchar(20) default '' not null, item3 varchar(20) default '' not null, item4 varchar(20) default '' not null)";
        db.execSQL(receipts);
        db.execSQL(receipts_detail);
    }

    /**
     * 还原旧版本
     *
     * @param db
     * @param oldVersion
     * @param newVersion
     */
    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        super.onDowngrade(db, oldVersion, newVersion);
    }
}

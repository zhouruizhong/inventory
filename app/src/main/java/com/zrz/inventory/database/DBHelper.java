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
    public static final int VERSION = 1;

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
        String sql = "create table " + TABLENAME + "(_id integer primary key autoincrement,uName varchar(20) not null,uPass varchar(20) not null)";
        String sql1 = "create table inventory (id integer primary key autoincrement,number varchar(20) default '' not null, matched integer default 0 not null, count integer default 0 not null)";
        db.execSQL(sql);
        db.execSQL(sql1);
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

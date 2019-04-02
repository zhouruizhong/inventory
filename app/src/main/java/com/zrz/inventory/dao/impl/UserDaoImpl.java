package com.zrz.inventory.dao.impl;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.zrz.inventory.bean.User;
import com.zrz.inventory.dao.UserDao;
import com.zrz.inventory.database.DBHelper;

/**
 * @author 周瑞忠
 * @description java类作用描述
 * @date 2019/3/30 19:55
 */
public class UserDaoImpl implements UserDao {

    private DBHelper dbHelper;
    private SQLiteDatabase database;
    private ContentValues values;

    public UserDaoImpl(Context context) {
        dbHelper = new DBHelper(context);
    }

    /**
     * 添加新用户
     * @param user 注册的用户信息
     */
    @Override
    public void addUser(User user) {
        database = dbHelper.getWritableDatabase();
        //方式一：sql语句操作数据库
//        String sql = "insert into users(uName,uPass) values(?,?)";
//        database.execSQL(sql,new Object[]{user.getUserName(),user.getUserPass()});

        //方法二：封装的api操作，直接操作方法即可
        values = new ContentValues();
        values.put(DBHelper.COLNUMNAME, user.getUserName());
        values.put(DBHelper.COLNUMPASS, user.getUserPass());
        database.insert(DBHelper.TABLENAME, null, values);
        database.close();
    }

    /**
     * 根据名字删除用户
     *
     * @param name 要删除用户的名字
     */
    @Override
    public void delteUserByName(String name) {
        database = dbHelper.getWritableDatabase();
        //方式一：sql语句操作数据库
//        String sql = "delete from " + DBHelper.TABLENAME + " where " + DBHelper.COLNUMNAME + " = ?";
//        database.execSQL(sql,new Object[]{name});
        //方法二：封装的api操作，直接操作方法即可
        database.delete(DBHelper.TABLENAME,DBHelper.COLNUMNAME + " = ?",new String[]{name});
        database.close();
    }

    /**
     * 更新用户密码
     *
     * @param name 用户名
     * @param pass 用户密码
     */
    @Override
    public void updateUserPwd(String name, String pass) {
        database = dbHelper.getWritableDatabase();
        //方式一：sql语句操作数据库
//        String sql = "update " + DBHelper.TABLENAME + " set " + DBHelper.COLNUMPASS + " = ? where " + DBHelper.COLNUMNAME + " = ?";
//        database.execSQL(sql,new Object[]{pass,name});
        //方法二：封装的api操作，直接操作方法即可
        values = new ContentValues();
        values.put(DBHelper.COLNUMPASS,pass);
        database.update(DBHelper.TABLENAME,values,DBHelper.COLNUMNAME + " = ?",new String[]{pass});
        database.close();
    }

    /**
     * 通过用户名查找用户
     *
     * @param name
     */
    @Override
    public User queryUserByName(String name) {
        database = dbHelper.getReadableDatabase();
        //方式一：sql语句操作数据库
//        String sql = "select * from " + DBHelper.TABLENAME + " where " + DBHelper.COLNUMNAME + " = ?";
//        Cursor cursor = database.rawQuery(sql, new String[]{name});
        //方法二：封装的api操作，直接操作方法即可
        Cursor cursor = database.query(DBHelper.TABLENAME, null, DBHelper.COLNUMNAME + " = ?", null, null, null, null);
        User user = null;
        while(cursor.moveToNext()){
            user = new User();
            user.setUserName(cursor.getString(cursor.getColumnIndex(DBHelper.COLNUMNAME)));
            user.setUserPass(cursor.getString(cursor.getColumnIndex(DBHelper.COLNUMPASS)));
        }
        cursor.close();

        return user;
    }

    /**
     * 判断是否存在该用户
     *
     * @param user
     * @return
     */
    @Override
    public boolean isExistsUser(User user) {
        User isExit = queryUserByName(user.getUserName());
        return isExit == null ? false : true;
    }

    /**
     * 判断是否登录成功
     * @param user 登录的用户信息
     * @return
     */
    @Override
    public boolean isLoginSuccess(User user) {
        database = dbHelper.getReadableDatabase();
        //方法一：sql语句操作
//        String sql = "select * from " + DBHelper.TABLENAME + " where " + DBHelper.COLNUMNAME + " = ? and " + DBHelper.COLNUMPASS + " = ?";
//        Cursor cursor = database.rawQuery(sql, new String[]{user.getUserName(), user.getUserPass()});
        /**
         * 方法二：封装的api操作，直接操作方法即可
         */
        Cursor cursor = database.query(DBHelper.TABLENAME, null, DBHelper.COLNUMNAME + " = ? and " + DBHelper.COLNUMPASS + " = ?", new String[]{user.getUserName(), user.getUserPass()}, null, null, null);

        if(cursor.moveToFirst()){
            cursor.close();
            return true;
        }
        cursor.close();
        return false;
    }
}

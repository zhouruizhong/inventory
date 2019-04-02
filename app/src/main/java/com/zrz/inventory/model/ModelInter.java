package com.zrz.inventory.model;

import com.zrz.inventory.bean.LoginResp;
import com.zrz.inventory.bean.Uuid;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * @author 周瑞忠
 * 所有数据处理操作功能都定义在这个接口中，方便扩展
 * @date 2019/3/30 17:58
 */
public interface  ModelInter {

    /**
     * 注册操作
     *
     * @param name     用户名
     * @param pass     用户密码
     * @param listener 回调事件，如果成功调用注册成功方法，失败调用注册失败方法
     */
    void register(String name, String pass, OnRegisterListener listener);

    /**
     * 登录操作
     *
     * @param name     用户名
     * @param pass     用户密码
     * @param listener 回调事件，如果成功调用登录成功方法，失败调用登录失败方法
     */
    void login(String name, String pass, OnLoginListener listener);

}

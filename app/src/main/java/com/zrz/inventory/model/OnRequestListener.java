package com.zrz.inventory.model;

/**
 * @author 周瑞忠
 * @description java类作用描述
 * @date 2019/3/31 2:05
 */
public interface OnRequestListener {

    /**
     *
     */
    String TAG = "登录";

    /**
     * 请求成功
     *
     * @param object
     */
    void success(Object object);

    /**
     * 登录失败，传递了用户信息
     *
     * @param object
     */
    void fail(Object object);
}

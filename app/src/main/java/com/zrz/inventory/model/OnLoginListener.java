package com.zrz.inventory.model;

import com.zrz.inventory.bean.User;

/**
 * @author 周瑞忠
 * 登录成功或失败的接口
 * @date 2019/3/30 20:05
 */
public interface OnLoginListener {

    /**
     *
     */
    String TAG = "登录";

    /**
     * 登录成功，传递了用户信息
     *
     * @param user
     */
    void loginSuccess(User user);

    /**
     * 登录失败，传递了用户信息
     *
     * @param user
     */
    void loginFail(User user);

}

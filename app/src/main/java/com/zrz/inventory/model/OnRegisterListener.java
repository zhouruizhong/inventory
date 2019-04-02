package com.zrz.inventory.model;

import com.zrz.inventory.bean.User;

/**
 * @author 周瑞忠
 * @description java类作用描述
 * @date 2019/3/30 20:04
 */
public interface OnRegisterListener {

    /**
     * 表示注册标签
     */
    String TAG = "注册";
    /**
     * 注册成功，传递了用户信息
     *
     * @param user
     */
    void registerSuccess(User user);

    /**
     * 注册失败，传递了用户信息
     *
     * @param user
     */
    void registerFail(User user);
}

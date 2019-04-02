package com.zrz.inventory.view.viewinter;

import com.zrz.inventory.bean.User;

/**
 * @author 周瑞忠
 * view层所有的功能
 * @date 2019/3/30 18:03
 */
public interface ViewInter {

    /**
     * 获取用户名
     * @return
     */
    String getName();

    /**
     * 获取用户密码
     * @return
     */
    String getPass();

    /**
     * 清除界面中编辑框里的名字
     */
    void clearUserName();

    /**
     * 清除界面中编辑框里的密码
     */
    void clearUserPass();

    /**
     * 显示进度条
     */
    void showLoading();

    /**
     * 隐藏进度条
     */
    void hideLoading();

    /**
     * 提示用户登录或注册成功后的状态
     * @param user 用户信息
     * @param tag 表示登录或注册提示
     */
    void successHint(User user, String tag);

    /**
     * 提示用户登录或注册失败后的状态
     * @param user 用户信息
     * @param tag 表示登录或注册提示
     */
    void failHint(User user, String tag);

}

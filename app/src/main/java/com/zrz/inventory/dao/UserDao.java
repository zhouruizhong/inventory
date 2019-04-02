package com.zrz.inventory.dao;

import com.zrz.inventory.bean.User;

/**
 * @author 周瑞忠
 * @description java类作用描述
 * @date 2019/3/30 19:55
 */
public interface UserDao {

    /**
     * 添加新用户
     * @param user 用户信息
     */
    void addUser(User user);

    /**
     * 删除用户
     * @param name 用户名
     */
    void delteUserByName(String name);

    /**
     * 通过用户名修改密码
     * @param name 用户名
     * @param pass 密码
     */
    void updateUserPwd(String name, String pass);

    /**
     * 通过用户名查找用户
     * @param name 用户名
     * @return User
     */
    User queryUserByName(String name);

    /**
     * 判断是否存在重复用户
     * @param user 用户
     * @return boolean
     */
    boolean isExistsUser(User user);

    /**
     * 判断是否登陆成功
     * @param user 用户信息
     * @return boolean
     */
    boolean isLoginSuccess(User user);
}

package com.zrz.inventory.view.viewinter;

import com.zrz.inventory.bean.Receipts;
import com.zrz.inventory.bean.User;

import java.util.List;
import java.util.Map;

/**
 * @author 周瑞忠
 * @description java类作用描述
 * @date 2019/4/2 0:40
 */
public interface ViewReceipts {

    /**
     * 提示用户登录或注册成功后的状态
     * @param tag 表示登录或注册提示
     */
    void successHint(Map<String, Object> response, String tag);

    /**
     * 提示用户登录或注册失败后的状态
     * @param tag 表示保存
     */
    void failHint(Map<String, Object> response, String tag);

}

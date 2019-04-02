package com.zrz.inventory.model;

import com.zrz.inventory.bean.User;

/**
 * @author 周瑞忠
 * @description java类作用描述
 * @date 2019/3/30 17:59
 */
public interface OnSaveListener {

    /**
     * 表示登录标签
     */
    String TAG = "保存";

    /**
     * 保存成功
     *
     * @param number 编号
     */
    void saveSuccess(String number);

    /**
     * 保存失败
     *
     * @param number 编号
     */
    void saveFail(String number);
}

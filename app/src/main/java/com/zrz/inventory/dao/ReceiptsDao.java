package com.zrz.inventory.dao;

import com.zrz.inventory.bean.Receipts;

import java.util.List;

/**
 * @author 周瑞忠
 * @description java类作用描述
 * @date 2019/4/1 22:25
 */
public interface ReceiptsDao {

    /**
     *
     * @return
     */
    List<Receipts> findAll();

    /**
     * 添加
     * @param number 编号
     */
    void add(String number);
}

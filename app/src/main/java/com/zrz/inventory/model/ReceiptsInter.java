package com.zrz.inventory.model;

import com.zrz.inventory.bean.LoginResp;
import com.zrz.inventory.bean.Receipts;
import com.zrz.inventory.bean.Uuid;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

import java.util.List;

/**
 * @author 周瑞忠
 * @description java类作用描述
 * @date 2019/3/31 1:59
 */
public interface ReceiptsInter {

    /**
     *
     * @param number
     * @param listener
     */
    void add(String number, OnSaveListener listener);

    /**
     *
     * @return
     */
    List<Receipts> findAll();
}

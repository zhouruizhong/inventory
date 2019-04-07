package com.zrz.inventory.model;

import com.zrz.inventory.bean.ReceiptsDetail;

public interface ReceiptsDetailInter {

    /**
     *
     * @param receiptsDetail
     * @param listener
     */
    void add(ReceiptsDetail receiptsDetail, OnRequestListener listener);

    /**
     *
     * @return
     */
    void find(Integer receiptsId, Integer currentPage, Integer pageSize, OnRequestListener listener);
}

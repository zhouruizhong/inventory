package com.zrz.inventory.model;

import com.zrz.inventory.bean.ReceiptsDetail;

import java.util.List;

public interface ReceiptsDetailInter {

    /**
     *
     * @param receiptsDetail
     * @param listener
     */
    void add(ReceiptsDetail receiptsDetail, OnRequestListener listener);

    /**
     *
     * @param receiptsDetailList
     * @param listener
     */
    void batchAdd(Integer receiptsId, List<ReceiptsDetail> receiptsDetailList, OnRequestListener listener);

    /**
     *
     * @return
     */
    void find(Integer receiptsId, Integer currentPage, Integer pageSize, OnRequestListener listener);

    /**
     *
     * @return
     */
    void find(Integer receiptsId, OnRequestListener listener);
}

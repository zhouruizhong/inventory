package com.zrz.inventory.dao;

import com.zrz.inventory.bean.ReceiptsDetail;
import com.zrz.inventory.model.OnRequestListener;
import com.zrz.inventory.model.OnSaveListener;

import java.util.List;

public interface ReceiptsDetailDao {

    /**
     *
     * @param receiptsDetail
     */
    void add(ReceiptsDetail receiptsDetail);

    /**
     *
     * @return
     */
    List<ReceiptsDetail> find(Integer receiptsId, Integer currentPage, Integer pageSize);
}

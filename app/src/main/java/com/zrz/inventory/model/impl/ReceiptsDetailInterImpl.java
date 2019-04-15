package com.zrz.inventory.model.impl;

import android.content.Context;
import com.zrz.inventory.bean.Receipts;
import com.zrz.inventory.bean.ReceiptsDetail;
import com.zrz.inventory.dao.ReceiptsDetailDao;
import com.zrz.inventory.dao.impl.ReceiptsDetailDaoImpl;
import com.zrz.inventory.model.OnRequestListener;
import com.zrz.inventory.model.OnSaveListener;
import com.zrz.inventory.model.ReceiptsDetailInter;

import java.util.ArrayList;
import java.util.List;

public class ReceiptsDetailInterImpl implements ReceiptsDetailInter {

    private ReceiptsDetailDao receiptsDetailDao;

    public ReceiptsDetailInterImpl(Context context){
        receiptsDetailDao = new ReceiptsDetailDaoImpl(context);
    }

    @Override
    public void add(ReceiptsDetail receiptsDetail, OnRequestListener listener) {
        try{
            receiptsDetailDao.add(receiptsDetail);
            listener.success(receiptsDetail);
        }catch (Exception e){
            listener.fail(receiptsDetail);
        }
    }

    public Boolean isExist(Integer receiptsId, String rfidData){
        return null == receiptsDetailDao.findByRfid(receiptsId, rfidData);
    }

    @Override
    public void batchAdd(Integer receiptsId, List<ReceiptsDetail> receiptsDetailList, OnRequestListener listener) {
        try{
            for (ReceiptsDetail receiptsDetail : receiptsDetailList){
                String rfidData = receiptsDetail.getItem4();
                if (isExist(receiptsId, rfidData)){
                    receiptsDetailDao.add(receiptsDetail);
                }
            }
            listener.success(receiptsDetailList);
        }catch (Exception e){
            listener.fail(receiptsDetailList);
        }
    }

    @Override
    public void find(Integer receiptsId, Integer currentPage, Integer pageSize, OnRequestListener listener) {
        try{

            List<ReceiptsDetail> receiptsDetails = receiptsDetailDao.find(receiptsId, currentPage, pageSize);
            listener.success(receiptsDetails);
        }catch (Exception e){
            listener.fail(new ArrayList<ReceiptsDetail>());
        }
    }
}

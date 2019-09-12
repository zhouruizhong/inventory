package com.zrz.inventory.model.impl;

import android.content.Context;
import com.zrz.inventory.bean.LoginResp;
import com.zrz.inventory.bean.Receipts;
import com.zrz.inventory.bean.Uuid;
import com.zrz.inventory.common.DataManager;
import com.zrz.inventory.common.RetrofitHelper;
import com.zrz.inventory.dao.ReceiptsDao;
import com.zrz.inventory.dao.UserDao;
import com.zrz.inventory.dao.impl.ReceiptsDaoImpl;
import com.zrz.inventory.dao.impl.UserDaoImpl;
import com.zrz.inventory.model.OnRequestListener;
import com.zrz.inventory.model.OnSaveListener;
import com.zrz.inventory.model.ReceiptsInter;
import rx.Observable;
import rx.subscriptions.CompositeSubscription;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author 周瑞忠
 * @date 2019/3/31 2:00
 */
public class ReceiptsInterImpl implements ReceiptsInter {

    /**
     * 获取数据库管理类，对数据库进行操作
     */
    private ReceiptsDao receiptsDao;

    public ReceiptsInterImpl(Context context){
        receiptsDao = new ReceiptsDaoImpl(context);
    }


    @Override
    public void add(String number, OnSaveListener listener) {
        try{
            receiptsDao.add(number);
            listener.saveSuccess(number);
        }catch (Exception e){
            listener.saveFail(number);
        }
    }

    @Override
    public void findAll(Integer currentPage, Integer pageSize, OnRequestListener listener) {
        try{

            List<Receipts> receiptsList = receiptsDao.findAll(currentPage, pageSize);
            listener.success(receiptsList);
        }catch (Exception e){
            listener.fail(new ArrayList<Receipts>());
        }
    }

    @Override
    public void delete(List<Integer> id, OnRequestListener listener){
        try{
            receiptsDao.delete(id);
            listener.success(1);
        }catch (Exception e){
            listener.fail(0);
        }
    }

    @Override
    public void updateCountById(Integer id, Integer count, OnRequestListener listener) {
        try{
            receiptsDao.updateCountById(id, count);
            listener.success(1);
        }catch (Exception e){
            listener.fail(0);
        }
    }

    @Override
    public void updateCountById(Integer id, Integer count) {
        try{
            receiptsDao.updateCountById(id, count);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public Receipts find(Integer id) {
        try{
            return receiptsDao.findById(id);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void updateMatchedById(Integer id, Integer matched, OnRequestListener listener) {
        try{
            receiptsDao.updateMatchedById(id, matched);
            listener.success(1);
        }catch (Exception e){
            listener.fail(0);
        }
    }

    @Override
    public void updateMatchedById(Integer id, Integer matched) {
        try{
            receiptsDao.updateMatchedById(id, matched);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}

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
 * @author 周瑞忠
 * @description java类作用描述
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
    public void findAll(OnRequestListener listener) {
        try{

            List<Receipts> receiptsList = receiptsDao.findAll();
            listener.success(receiptsList);
        }catch (Exception e){
            listener.fail(new ArrayList<Receipts>());
        }
    }
}

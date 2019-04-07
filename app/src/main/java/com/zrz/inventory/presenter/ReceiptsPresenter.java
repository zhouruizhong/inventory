package com.zrz.inventory.presenter;

import android.content.Context;
import android.os.Handler;
import com.zrz.inventory.bean.Receipts;
import com.zrz.inventory.model.OnRequestListener;
import com.zrz.inventory.model.OnSaveListener;
import com.zrz.inventory.model.ReceiptsInter;
import com.zrz.inventory.model.impl.ReceiptsInterImpl;
import com.zrz.inventory.view.viewinter.ViewReceipts;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 周瑞忠
 * @description java类作用描述
 * @date 2019/3/31 1:57
 */
public class ReceiptsPresenter {

    /**
     * view层的控件，对view层进行操作
     */
    ViewReceipts viewReceipts;
    /**
     * 模型层的控件,对model层进行操作
     */
    ReceiptsInter modelInter;

    public ReceiptsPresenter(ViewReceipts viewReceipts, Context context) {
        this.viewReceipts = viewReceipts;
        modelInter = new ReceiptsInterImpl(context);
    }

    public void findAll(Integer currentPage, Integer pageSize){
        modelInter.findAll(currentPage, pageSize, new OnRequestListener(){
            @Override
            public void success(final Object object) {
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //返回成功状态信息
                        Map<String, Object> response = new HashMap<>(16);
                        response.put("receiptsList", (List<Receipts>)object);
                        viewReceipts.successHint(response,"findAll");
                    }
                }, 500);
            }

            @Override
            public void fail(final Object object) {
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //返回成功状态信息
                        Map<String, Object> response = new HashMap<>(16);
                        response.put("", (List<Receipts>)object);
                        viewReceipts.successHint(response,"findAll");
                    }
                }, 500);
            }
        });
    }

    public void add(String number){
        modelInter.add(number, new OnSaveListener() {
            @Override
            public void saveSuccess(final String number) {
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Map<String, Object> response = new HashMap<>(16);
                        Receipts receipts = new Receipts();
                        receipts.setNumber(number);

                        response.put("receipts", receipts);
                        //返回成功状态信息
                        viewReceipts.successHint(response,"save");
                    }
                }, 500);
            }

            @Override
            public void saveFail(final String number) {
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Map<String, Object> response = new HashMap<>(16);
                        Receipts receipts = new Receipts();
                        receipts.setNumber(number);

                        response.put("receipts", receipts);
                        //返回失败状态信息
                        viewReceipts.failHint(response,"save");
                    }
                }, 500);
            }
        });
    }

    public void delete(List<Integer> id){
        modelInter.delete(id, new OnRequestListener() {
            @Override
            public void success(Object object) {
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Map<String, Object> response = new HashMap<>(16);
                        response.put("message", "删除成功");
                        //返回成功状态信息
                        viewReceipts.successHint(response,"delete");
                    }
                }, 500);
            }

            @Override
            public void fail(Object object) {
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Map<String, Object> response = new HashMap<>(16);
                        response.put("message", "删除失败");
                        //返回成功状态信息
                        viewReceipts.successHint(response,"delete");
                    }
                }, 500);
            }
        });
    }
}

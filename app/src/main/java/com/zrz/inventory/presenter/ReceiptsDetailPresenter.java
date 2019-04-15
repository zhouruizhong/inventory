package com.zrz.inventory.presenter;

import android.content.Context;
import android.os.Handler;
import com.zrz.inventory.bean.Receipts;
import com.zrz.inventory.bean.ReceiptsDetail;
import com.zrz.inventory.model.OnRequestListener;
import com.zrz.inventory.model.OnSaveListener;
import com.zrz.inventory.model.ReceiptsDetailInter;
import com.zrz.inventory.model.ReceiptsInter;
import com.zrz.inventory.model.impl.ReceiptsDetailInterImpl;
import com.zrz.inventory.model.impl.ReceiptsInterImpl;
import com.zrz.inventory.view.viewinter.ViewReceipts;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReceiptsDetailPresenter {

    /**
     * view层的控件，对view层进行操作
     */
    ViewReceipts viewReceipts;
    /**
     * 模型层的控件,对model层进行操作
     */
    ReceiptsDetailInter modelInter;
    ReceiptsInter receiptsInter;

    public ReceiptsDetailPresenter(ViewReceipts viewReceipts, Context context) {
        this.viewReceipts = viewReceipts;
        modelInter = new ReceiptsDetailInterImpl(context);
        receiptsInter = new ReceiptsInterImpl(context);
    }

    public void find(Integer receiptsId, Integer currentPage, Integer pageSize){
        modelInter.find(receiptsId, currentPage, pageSize, new OnRequestListener(){
            @Override
            public void success(final Object object) {
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //返回成功状态信息
                        Map<String, Object> response = new HashMap<>(16);
                        response.put("receiptsDetailList", (List<ReceiptsDetail>)object);
                        viewReceipts.successHint(response,"find");
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
                        response.put("receiptsDetailList", (List<ReceiptsDetail>)object);
                        viewReceipts.successHint(response,"find");
                    }
                }, 500);
            }
        });
    }

    public void refresh(Integer receiptsId, Integer currentPage, Integer pageSize){
        modelInter.find(receiptsId, currentPage, pageSize, new OnRequestListener(){
            @Override
            public void success(final Object object) {
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //返回成功状态信息
                        Map<String, Object> response = new HashMap<>(16);
                        response.put("receiptsDetailList", (List<ReceiptsDetail>)object);
                        viewReceipts.successHint(response,"refresh");
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
                        response.put("receiptsDetailList", (List<ReceiptsDetail>)object);
                        viewReceipts.successHint(response,"refresh");
                    }
                }, 500);
            }
        });
    }

    public void add(final ReceiptsDetail receiptsDetail){
        modelInter.add(receiptsDetail, new OnRequestListener() {
            @Override
            public void success(final Object object) {
                receiptsInter.updateCountById(receiptsDetail.getReceiptsId(), 1);

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Map<String, Object> response = new HashMap<>(16);
                        response.put("message", "扫描成功");
                        //返回成功状态信息
                        viewReceipts.successHint(response,"scan");
                    }
                }, 500);
            }

            @Override
            public void fail(final Object object) {
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Map<String, Object> response = new HashMap<>(16);
                        response.put("message", "扫描失败");
                        //返回失败状态信息
                        viewReceipts.failHint(response,"scan");
                    }
                }, 500);
            }
        });
    }

    public void batchAdd(final Integer receiptsId, final List<ReceiptsDetail> receiptsDetailList){
        modelInter.batchAdd(receiptsId, receiptsDetailList, new OnRequestListener() {
            @Override
            public void success(final Object object) {
                receiptsInter.updateMatchedById(receiptsId, receiptsDetailList.size());

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Map<String, Object> response = new HashMap<>(16);
                        response.put("message", "匹配成功");
                        //返回成功状态信息
                        viewReceipts.successHint(response,"batch");
                    }
                }, 500);
            }

            @Override
            public void fail(final Object object) {
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Map<String, Object> response = new HashMap<>(16);
                        response.put("message", "匹配失败");
                        //返回失败状态信息
                        viewReceipts.failHint(response,"batch");
                    }
                }, 500);
            }
        });
    }
}

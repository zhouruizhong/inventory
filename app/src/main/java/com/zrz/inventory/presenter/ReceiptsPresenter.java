package com.zrz.inventory.presenter;

import android.content.Context;
import android.os.Handler;
import com.zrz.inventory.bean.Receipts;
import com.zrz.inventory.model.OnSaveListener;
import com.zrz.inventory.model.ReceiptsInter;
import com.zrz.inventory.model.impl.ReceiptsInterImpl;
import com.zrz.inventory.view.viewinter.ViewReceipts;

import java.util.List;

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

    public List<Receipts> findAll(){
        return modelInter.findAll();
    }

    public void add(String number){
        modelInter.add(number, new OnSaveListener() {
            @Override
            public void saveSuccess(final String number) {
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Receipts receipts = new Receipts();
                        receipts.setNumber(number);
                        //返回成功状态信息
                        viewReceipts.successHint(receipts,TAG);
                    }
                }, 3000);
            }

            @Override
            public void saveFail(final String number) {
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Receipts receipts = new Receipts();
                        receipts.setNumber(number);
                        //返回失败状态信息
                        viewReceipts.failHint(receipts,TAG);
                    }
                }, 3000);
            }
        });
    }
}

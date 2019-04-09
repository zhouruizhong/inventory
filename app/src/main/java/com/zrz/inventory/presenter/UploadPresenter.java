package com.zrz.inventory.presenter;

import android.content.Context;
import android.content.Intent;
import com.zrz.inventory.bean.ResponseObject;
import com.zrz.inventory.bean.Upload;
import com.zrz.inventory.common.DataManager;
import com.zrz.inventory.view.viewinter.UploadView;
import com.zrz.inventory.view.viewinter.View;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

public class UploadPresenter {

    private DataManager manager;
    private CompositeSubscription mCompositeSubscription;
    private Context mContext;

    private UploadView mUploadView;
    private ResponseObject mObject;

    private static final String TAG = "Rxjava";

    /**
     * 设置变量 = 模拟轮询服务器次数
     */
    private int i = 0 ;

    public UploadPresenter(UploadView view, Context mContext) {
        this.mUploadView = view;
        this.mContext = mContext;
    }

    public void onCreate() {
        manager = new DataManager(mContext);
        mCompositeSubscription = new CompositeSubscription();
    }

    public void onStart() {

    }

    public void onStop() {
        if (mCompositeSubscription.hasSubscriptions()) {
            mCompositeSubscription.unsubscribe();
        }
    }

    public void pause() {

    }

    public void attachView(View view) {
        mUploadView = (UploadView) view;
    }

    public void attachIncomingIntent(Intent intent) {
    }

    public void rfidAdd(Upload upload) {
        mCompositeSubscription.add(manager.rfidAdd(upload)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseObject>() {
                    @Override
                    public void onCompleted() {
                        if (mObject != null) {
                            mUploadView.onSuccess(mObject);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        mUploadView.onError("请求失败！！");
                    }

                    @Override
                    public void onNext(ResponseObject object) {
                        mObject = object;
                    }
                })
        );
    }
}

package com.zrz.inventory.presenter;

import android.content.Context;
import android.content.Intent;
import com.zrz.inventory.bean.Uuid;
import com.zrz.inventory.common.DataManager;
import com.zrz.inventory.view.viewinter.UuidView;
import com.zrz.inventory.view.viewinter.View;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * @author 周瑞忠
 * @description java类作用描述
 * @date 2019/3/31 13:47
 */
public class IndexPresenter {

    private DataManager manager;
    private CompositeSubscription mCompositeSubscription;
    private Context mContext;

    private UuidView mUuidView;
    private Uuid mUuid;

    private static final String TAG = "Rxjava";

    /**
     * 设置变量 = 模拟轮询服务器次数
     */
    private int i = 0 ;

    public IndexPresenter(UuidView view, Context mContext) {
        this.mUuidView = view;
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
        mUuidView = (UuidView) view;
    }

    public void attachIncomingIntent(Intent intent) {
    }

    public void getUuid() {
        mCompositeSubscription.add(manager.getUuid()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Uuid>() {
                    @Override
                    public void onCompleted() {
                        if (mUuid != null) {
                            mUuidView.onSuccess(mUuid);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        mUuidView.onError("请求失败！！");
                    }

                    @Override
                    public void onNext(Uuid uuid) {
                        mUuid = uuid;
                    }
                })
        );
    }
}

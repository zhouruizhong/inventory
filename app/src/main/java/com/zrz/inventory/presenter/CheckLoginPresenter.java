package com.zrz.inventory.presenter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import com.zrz.inventory.bean.LoginResp;
import com.zrz.inventory.common.DataManager;
import com.zrz.inventory.view.viewinter.LoginRespView;
import com.zrz.inventory.view.viewinter.View;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

import java.util.concurrent.TimeUnit;

/**
 * @author 周瑞忠
 * @description java类作用描述
 * @date 2019/3/31 14:11
 */
public class CheckLoginPresenter {

    private DataManager manager;
    private CompositeSubscription mCompositeSubscription;
    private Context mContext;
    private LoginResp mLoginResp;
    private LoginRespView mLoginRespView;

    private static final String TAG = "Rxjava";

    /**
     * 设置变量 = 模拟轮询服务器次数
     */
    private int i = 0 ;

    public CheckLoginPresenter(Context mContext){
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
        mLoginRespView = (LoginRespView)view;
    }

    public void attachIncomingIntent(Intent intent) {

    }

    public  void validateLogin(String uuid){
        Observable.interval(1, 2, TimeUnit.SECONDS)
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onCompleted() {
                        Log.d(TAG, "开始采用subscribe连接");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "对Error事件作出响应");
                    }

                    @Override
                    public void onNext(Long aLong) {
                        Log.d(TAG, "接收到了事件"+ aLong  );
                    }
                });

    }

    public void checkLogin(String uuid) {
        mCompositeSubscription.add(manager.checkLogin(uuid)
                .repeatWhen(new Func1<Observable<? extends Void>, Observable<?>>() {
                    @Override
                    public Observable<?> call(Observable<? extends Void> observable) {
                        return observable.flatMap(new Func1<Void, Observable<?>>() {
                            @Override
                            public Observable<?> call(Void aVoid) {
                                // 加入判断条件：当轮询次数 = 5次后，就停止轮询
                                if (i > 10) {
                                    // 此处选择发送onError事件以结束轮询，因为可触发下游观察者的onError（）方法回调
                                    return Observable.error(new Throwable("轮询结束"));
                                }
                                // 若轮询次数＜4次，则发送1Next事件以继续轮询
                                // 注：此处加入了delay操作符，作用 = 延迟一段时间发送（此处设置 = 2s），以实现轮询间间隔设置
                                return Observable.just(1).delay(1000, TimeUnit.MILLISECONDS);
                            }
                        });
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<LoginResp>() {
                    @Override
                    public void onCompleted() {
                        String code = "200";
                        if (mLoginResp != null && code.equals(mLoginResp.getCode())) {
                            mLoginRespView.onSuccess(mLoginResp);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        mLoginRespView.onError("请求失败！！");
                    }

                    @Override
                    public void onNext(LoginResp loginResp) {
                        if (loginResp != null && "200".equals(loginResp.getCode())) {
                            mLoginResp = loginResp;
                            mLoginRespView.onSuccess(loginResp);
                            onStop();
                        }
                    }
                })
        );
    }
}

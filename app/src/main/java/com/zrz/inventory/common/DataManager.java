package com.zrz.inventory.common;

import android.content.Context;
import com.zrz.inventory.bean.LoginResp;
import com.zrz.inventory.bean.Uuid;
import rx.Observable;

/**
 * @author zhouruizhong
 */
public class DataManager {

    private static final String TAG = "Rxjava";

    /**
     * 设置变量 = 模拟轮询服务器次数
     */
    private int i = 0 ;

    private RetrofitService mRetrofitService;

    public void init() {

    }

    public DataManager(Context context) {
        this.mRetrofitService = RetrofitHelper.getInstance(context).getServer();
    }

    public Observable<Uuid> getUuid() {
        return mRetrofitService.getUuid();
    }

    public Observable<String> getQrcode(String uuid) {
        return mRetrofitService.generateQrcode(uuid);
    }

    public Observable<LoginResp> checkLogin(String uuid) {
        return mRetrofitService.checkLogin(uuid);
    }
}

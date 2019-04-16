package com.zrz.inventory.common;

import com.zrz.inventory.bean.LoginResp;
import com.zrz.inventory.bean.ResponseObject;
import com.zrz.inventory.bean.Upload;
import com.zrz.inventory.bean.Uuid;
import retrofit2.http.*;
import rx.Observable;

import java.util.Map;

public interface RetrofitService {

    /**
     * 初始化
     * @return UUid
     */
    @GET("external/login/init")
    Observable<Uuid> getUuid();

    /**
     *  生成二维码
     * @param uuid 初始化的uuid
     * @return 二维码图片
     */
    @GET("loginQr/{uuid}")
    Observable<String> generateQrcode(@Path("uuid") String uuid);

    /**
     * 验证是否登录成功
     * @param uuid 初始化服务器返回的uuid
     * @return 登录信息
     */
    @GET("external/login/checkLogin")
    Observable<LoginResp> checkLogin(@Query("uuid") String uuid);

    /**
     * 盘点数据接口，扫描后上传（post）
     * @param token 登陆凭证
     * @param map 参数
     * @return ResponseObject
     */
    @POST("api/stocking/rfidAdd")
    Observable<ResponseObject> rfidAdd(@Header("token") String token,@QueryMap Map<String, Object> map);

}

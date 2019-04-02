package com.zrz.inventory.view.viewinter;

import com.zrz.inventory.bean.LoginResp;

/**
 * @author 周瑞忠
 * @description java类作用描述
 * @date 2019/3/31 14:11
 */
public interface LoginRespView extends View {

    /**
     *
     * @param mLoginResp
     */
    void onSuccess(LoginResp mLoginResp);

    /**
     *
     * @param result
     */
    void onError(String result);
}

package com.zrz.inventory.view.viewinter;

import com.zrz.inventory.bean.Uuid;

/**
 * @author 周瑞忠
 * @description java类作用描述
 * @date 2019/3/31 13:47
 */
public interface UuidView extends View{

    /**
     *
     * @param mUuid
     */
    void onSuccess(Uuid mUuid);

    /**
     *
     * @param result
     */
    void onError(String result);
}

package com.zrz.inventory.view.viewinter;

import com.zrz.inventory.bean.ResponseObject;

public interface UploadView extends View {

    /**
     *
     * @param object
     */
    void onSuccess(ResponseObject object);

    /**
     *
     * @param result
     */
    void onError(String result);
}

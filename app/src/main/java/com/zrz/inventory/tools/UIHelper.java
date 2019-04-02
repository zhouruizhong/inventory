package com.zrz.inventory.tools;

import android.content.Context;
import android.widget.Toast;

/**
 * @author 周瑞忠
 * @description java类作用描述
 * @date 2019/4/1 20:29
 */
public class UIHelper {

    /**
     * 弹出Toast消息
     *
     * @param msg
     */
    public static void toastMessage(Context cont, String msg) {
        Toast.makeText(cont, msg, Toast.LENGTH_SHORT).show();
    }

    public static void toastMessage(Context cont, int msg) {
        Toast.makeText(cont, msg, Toast.LENGTH_SHORT).show();
    }

    public static void toastMessage(Context cont, String msg, int time) {
        Toast.makeText(cont, msg, time).show();
    }
}

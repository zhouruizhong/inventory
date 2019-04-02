package com.zrz.inventory.presenter;

import android.content.Context;
import android.os.Handler;
import com.zrz.inventory.bean.User;
import com.zrz.inventory.model.ModelInter;
import com.zrz.inventory.model.OnLoginListener;
import com.zrz.inventory.model.OnRegisterListener;
import com.zrz.inventory.model.impl.ModelImp;
import com.zrz.inventory.view.viewinter.ViewInter;

/**
 * @author 周瑞忠
 * @description java类作用描述
 * @date 2019/3/30 17:55
 */
public class Presenter {

    /**
     * view层的控件，对view层进行操作
     */
    ViewInter viewInter;
    /**
     * 模型层的控件,对model层进行操作
     */
    ModelInter modelInter;

    public Presenter(ViewInter viewInter, Context context) {
        this.viewInter = viewInter;
        modelInter = new ModelImp(context);
    }

    /**
     * 注册功能
     */
    public void register() {
        //显示进度条
        viewInter.showLoading();
        //控制层开始处理数据，拿到视图层的name,pass之后，进行注册操作
        //OnRegisterListener匿名内部类
        modelInter.register(viewInter.getName(), viewInter.getPass(), new OnRegisterListener() {
            /**
             * 如果注册成功模型层就会调用该方法
             * 其中的handler表示模拟延时3秒响应操作
             * @param user 返回已经注册的用户信息
             */
            @Override
            public void registerSuccess(final User user) {
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //隐藏进度条
                        viewInter.hideLoading();
                        //返回成功状态信息
                        viewInter.successHint(user,TAG);
                    }
                }, 3000);

            }
            /**
             * 如果注册失败模型层就会调用该方法
             * 其中的handler表示模拟延时3秒响应操作
             * @param user 返回未注册的用户信息
             */
            @Override
            public void registerFail(final User user) {
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //隐藏进度条
                        viewInter.hideLoading();
                        //返回失败状态信息
                        viewInter.failHint(user,TAG);
                    }
                }, 3000);

            }
        });
    }

    /**
     * 清除功能
     */
    public void clear() {
        viewInter.clearUserName();
        viewInter.clearUserPass();
    }

    /**
     * 登录功能，其基本实现和注册一样，只是模型层处理的逻辑不一样
     */
    public void login(){
        viewInter.showLoading();
        modelInter.login(viewInter.getName(), viewInter.getPass(), new OnLoginListener() {
            @Override
            public void loginSuccess(final User user) {
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        viewInter.hideLoading();
                        viewInter.successHint(user,TAG);
                    }
                },3000);

            }

            @Override
            public void loginFail(final User user) {
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        viewInter.hideLoading();
                        viewInter.failHint(user,TAG);
                    }
                },3000);

            }
        });
    }
}

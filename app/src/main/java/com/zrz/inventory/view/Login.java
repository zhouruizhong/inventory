package com.zrz.inventory.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.*;
import com.zrz.inventory.R;
import com.zrz.inventory.bean.User;
import com.zrz.inventory.presenter.Presenter;
import com.zrz.inventory.view.viewinter.ViewInter;

/**
 * @author 周瑞忠
 * @description java类作用描述
 * @date 2019/3/30 20:11
 */
public class Login extends AppCompatActivity implements ViewInter {

    /**
     * 用户名控件
     */
    private EditText mUserName;
    /**
     * 用户密码控件
     */
    private EditText mUserPwd;
    /**
     * 登录按钮
     */
    private Button mBtnLogin;
    /**
     * 清除按钮
     */
    private Button mBtnClear;
    /**
     * 进度条
     */
    private ProgressBar mProBar;
    /**
     * 点我注册控件
     */
    private TextView mRegister;
    private Presenter presenter;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        //初始化控件
        initView();
        //初始化事件
        event();
        //建立与presenter层的关系，创建presenter对象
        presenter = new Presenter(this, Login.this);
    }
    private void initView() {
        mUserName = (EditText) findViewById(R.id.user_name);
        mUserPwd = (EditText) findViewById(R.id.user_pwd);
        mBtnLogin = (Button) findViewById(R.id.btn_login);
        mBtnClear = (Button) findViewById(R.id.btn_clear);
        mProBar = (ProgressBar) findViewById(R.id.progressBar);
        mRegister = (TextView) findViewById(R.id.mRegister);
    }

    private void event() {
        /**
         * 登录响应事件
         */
        mBtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //告诉presenter层，我需要登录操作
                presenter.login();
            }
        });
        /**
         * 清除响应事件
         */
        mBtnClear.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //告诉presenter层，我需要清除操作
                presenter.clear();
            }
        });

        /**
         * 点击注册响应事件，跳转到注册界面
         */
        mRegister.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login.this,Register.class);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    public String getName() {
        return mUserName.getText().toString();
    }

    @Override
    public String getPass() {
        return mUserPwd.getText().toString();
    }

    @Override
    public void clearUserName() {
        mUserName.setText("");
    }

    @Override
    public void clearUserPass() {
        mUserPwd.setText("");
    }

    @Override
    public void showLoading() {
        mProBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        mProBar.setVisibility(View.GONE);
    }

    @Override
    public void successHint(User user, String tag) {
        Toast.makeText(this,"用户" + user.getUserName() + tag + "成功",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void failHint(User user,String tag) {
        Toast.makeText(this,"用户" + user.getUserName() + tag + "失败,密码或账号不正确，maybe not this user",Toast.LENGTH_LONG).show();
    }
}

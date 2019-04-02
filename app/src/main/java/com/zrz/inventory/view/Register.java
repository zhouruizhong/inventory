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
 * @date 2019/3/30 20:12
 */
public class Register extends AppCompatActivity implements ViewInter {

    private EditText mUserName;
    private EditText mUserPwd;
    private Button mBtnRegister;
    private Button mBtnClear;
    private ProgressBar mProBar;
    /**
     * 点击注册控件
     */
    private TextView mTvLogin;
    private Presenter presenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        initView();
        event();
        presenter = new Presenter(this, Register.this);
    }


    private void initView() {
        mUserName = findViewById(R.id.user_name);
        mUserPwd =  findViewById(R.id.user_pwd);
        mBtnRegister = findViewById(R.id.btn_register);
        mBtnClear = findViewById(R.id.btn_clear);
        mProBar = findViewById(R.id.progressBar);
        mTvLogin = findViewById(R.id.mTvLogin);
    }

    private void event() {
        /**
         * 注册功能
         */
        mBtnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.register();
            }
        });
        /**
         * 清除功能
         */
        mBtnClear.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                presenter.clear();
            }
        });
        /**
         * 启动登录界面
         */
        mTvLogin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Register.this,Login.class);
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
        Toast.makeText(this, "用户" + user.getUserName() + tag + "成功", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void failHint(User user, String tag) {
        Toast.makeText(this, "用户" + user.getUserName() + tag + "失败,已存在该用户", Toast.LENGTH_SHORT).show();
    }
}

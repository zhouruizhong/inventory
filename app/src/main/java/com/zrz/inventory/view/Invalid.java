package com.zrz.inventory.view;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.zrz.inventory.R;
import com.zrz.inventory.bean.LoginResp;
import com.zrz.inventory.bean.Uuid;
import com.zrz.inventory.presenter.CheckLoginPresenter;
import com.zrz.inventory.presenter.IndexPresenter;
import com.zrz.inventory.view.viewinter.LoginRespView;
import com.zrz.inventory.view.viewinter.UuidView;

import java.io.IOException;
import java.net.URL;

/**
 *
 */
public class Invalid extends Activity implements UuidView {

    private static final String TAG = "Rxjava";
    private Button refresh;
    private IndexPresenter presenter;
    private String uuid;

    private CheckLoginPresenter loginPresenter = new CheckLoginPresenter(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.invalid);

        //初始化控件
        initView();
        //初始化事件
        event();
    }

    private void initView() {
        refresh = findViewById(R.id.invalid_refresh);
    }

    private void event(){
        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DownloadUrlBitmap().execute("https://www.jhwoods.com/loginQr/" + uuid);

                loginPresenter.onCreate();
                loginPresenter.attachView(mLoginRespView);
                loginPresenter.checkLogin(uuid);
            }
        });
    }

    private LoginRespView mLoginRespView = new LoginRespView() {
        @Override
        public void onSuccess(LoginResp mLoginResp) {
            String code = "200";
            if (code.equals(mLoginResp.getCode())){
                Intent intent = new Intent(Invalid.this, Main.class);

                SharedPreferences sharedPreferences = getSharedPreferences("login", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("keyName", mLoginResp.getKeyName());
                editor.putString("secretName", mLoginResp.getSecretName());
                editor.putString("token", mLoginResp.getToken());
                editor.putString("loginUuid", mLoginResp.getLoginUuid());
                editor.commit();

                startActivity(intent);
            }
        }

        @Override
        public void onError(String result) {
            Toast.makeText(Invalid.this, "抱歉,登陆失败或您还没有登陆：" + result, Toast.LENGTH_LONG).show();
        }
    };

    @Override
    public void onSuccess(Uuid mUuid) {

    }

    @Override
    public void onError(String result) {

    }

    private class DownloadUrlBitmap extends AsyncTask<String, Void, Bitmap> {
        @Override
        protected Bitmap doInBackground(String... params) {
            return loadImageFromNetwork(params[0]);
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            //qrCode.setImageBitmap(bitmap);
        }
    }


    private Bitmap loadImageFromNetwork(String url) {
        //得到可用的图片
        Bitmap bitmap = simpleNetworkImage(url);
        if (bitmap == null) {
            Log.i(TAG, "bitmap is null");
        }
        return bitmap;
    }


    public Bitmap simpleNetworkImage(String url) {
        Bitmap pngBM = null;
        try {
            URL picUrl = new URL(url);
            pngBM = BitmapFactory.decodeStream(picUrl.openStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return pngBM;
    }
}

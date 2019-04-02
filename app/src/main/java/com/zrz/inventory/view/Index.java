package com.zrz.inventory.view;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
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

public class Index extends Activity implements UuidView {

    private static final String TAG = "Rxjava";
    private ImageView qrCode;
    private IndexPresenter presenter;
    private String uuid;

    private CheckLoginPresenter loginPresenter = new CheckLoginPresenter(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.index);

        //初始化控件
        initView();
        //初始化事件
        event();

    }

    private void event() {
        /**
         * 二维码点击时间
         */
        qrCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 开启一个子线程，进行网络操作，等待有返回结果，使用handler通知UI
                presenter.getUuid();
            }
        });
    }

    private void initView() {
        // 二维码显示区
        qrCode = findViewById(R.id.qrCode);
        //建立与presenter层的关系，创建presenter对象
        presenter = new IndexPresenter(this, Index.this);
        presenter.onCreate();
        presenter.attachView(this);
        presenter.getUuid();
    }

    @Override
    public void onSuccess(Uuid mUuid) {
        uuid = mUuid.getUuid();
        System.out.println("-----------uuid :" + uuid + ":----------------");
        // 开启一个子线程，进行网络操作，等待有返回结果，使用handler通知UI
        new DownloadUrlBitmap().execute("https://www.jhwoods.com/loginQr/" + uuid);

        loginPresenter.onCreate();
        loginPresenter.attachView(mLoginRespView);
        loginPresenter.checkLogin(uuid);
    }

    @Override
    public void onError(String result) {
        Toast.makeText(this, "获取uuid超时,请检查网络！", Toast.LENGTH_SHORT).show();
    }

    private LoginRespView mLoginRespView = new LoginRespView() {
        @Override
        public void onSuccess(LoginResp mLoginResp) {
            String code = "200";
            if (code.equals(mLoginResp.getCode())){
                Intent intent = new Intent(Index.this, Main.class);

                Bundle bundle = new Bundle();
                bundle.putCharSequence("keyName", mLoginResp.getKeyName());
                bundle.putCharSequence("secretName", mLoginResp.getSecretName());
                bundle.putCharSequence("token", mLoginResp.getToken());
                bundle.putCharSequence("loginUuid", mLoginResp.getLoginUuid());

                intent.putExtras(bundle);
                startActivity(intent);
            }
        }

        @Override
        public void onError(String result) {
            Toast.makeText(Index.this, "抱歉,登陆失败或您还没有登陆：" + result, Toast.LENGTH_LONG).show();
        }
    };

    private class DownloadUrlBitmap extends AsyncTask<String, Void, Bitmap> {
        @Override
        protected Bitmap doInBackground(String... params) {
            return loadImageFromNetwork(params[0]);
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            qrCode.setImageBitmap(bitmap);
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

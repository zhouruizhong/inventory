package com.zrz.inventory.common;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.InputStream;
import java.net.URL;

public class DownImage extends AsyncTask {

    private ImageView imageView;

    public DownImage(ImageView imageView) {
        this.imageView = imageView;
    }

    @Override
    protected Object doInBackground(Object[] objects) {
        String url = objects[0].toString();
        Bitmap bitmap = null;
        try {
            //加载一个网络图片
            InputStream is = new URL(url).openStream();
            bitmap = BitmapFactory.decodeStream(is);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    protected void onPostExecute(Bitmap result) {
        imageView.setImageBitmap(result);
    }

}

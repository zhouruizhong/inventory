package com.zrz.inventory.view;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import com.rscja.deviceapi.RFIDWithUHF;
import com.zrz.inventory.R;

public class Main extends AppCompatActivity {

    public RFIDWithUHF mReader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        ImageView inventory = findViewById(R.id.inventory);
        ImageView cleanCache = findViewById(R.id.clean_cache);
        initUHF();
        inventory.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Main.this, ScanReceipts.class);
                startActivity(intent);
            }
        });

        cleanCache.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Main.this, Clean.class);
                startActivity(intent);
            }
        });
    }

    public void initUHF() {
        try {
            mReader = RFIDWithUHF.getInstance();
        } catch (Exception ex) {
            toastMessage(ex.getMessage());
            return;
        }
        if (mReader != null) {
            new InitTask().execute();
        }
    }

    public void toastMessage(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    public class InitTask extends AsyncTask<String, Integer, Boolean> {

        @Override
        protected Boolean doInBackground(String... params) {
            return mReader.init();
        }

        @Override
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);
            if (!result) {
                Toast.makeText(Main.this, "init fail",
                        Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
    }
}

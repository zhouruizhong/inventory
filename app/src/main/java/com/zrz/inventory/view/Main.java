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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        ImageView inventory = findViewById(R.id.inventory);
        ImageView cleanCache = findViewById(R.id.clean_cache);
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

}

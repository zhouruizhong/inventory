package com.zrz.inventory.view;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.app.Activity;
import android.os.Handler;
import android.support.annotation.ColorInt;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.*;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.zrz.inventory.R;
import com.zrz.inventory.adapter.ViewListAdapter;
import com.zrz.inventory.adapter.ViewPagerAdapter;
import com.zrz.inventory.bean.Receipts;
import com.zrz.inventory.presenter.ReceiptsPresenter;
import com.zrz.inventory.tools.StringUtils;
import com.zrz.inventory.view.viewinter.ViewReceipts;
import com.zrz.inventory.widget.LoadListView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ScanReceipts extends Activity implements ViewReceipts, LoadListView.ILoadListener {

    private ListView mListView;
    /**
     * 返回
     */
    private ImageView back;
    /**
     * 添加
     */
    private ImageView add;
    private PopupWindow popupWindow;
    private ReceiptsPresenter presenter;
    private List<Receipts> receiptsList = new ArrayList<>();
    private ViewListAdapter viewListAdapter;
    private Integer currentPage = 1;
    private Integer pageSize = 5;
    private LoadListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scan_receipts);

        presenter = new ReceiptsPresenter(this, ScanReceipts.this);
        // 查询数据
        presenter.findAll(currentPage, pageSize);

        initView();

        initData();

        event();
    }

    private void initView() {
        back = findViewById(R.id.scan_receipts_back);
        add = findViewById(R.id.add);
        listView = findViewById(R.id.listView);
        listView.setInterface(this);
    }

    /**
     * 初始化数据
     */
    private void initData() {
        //设置ListView的适配器
        viewListAdapter = new ViewListAdapter(this, receiptsList);
        listView.setAdapter(viewListAdapter);
        listView.setSelection(1);
    }

    public class ButtonOnClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            /* @setView 装入自定义View ==> R.layout.dialog_customize
             * 由于dialog_customize.xml只放置了一个EditView，因此和图8一样
             * dialog_customize.xml可自定义更复杂的View
             */
            final AlertDialog.Builder customizeDialog = new AlertDialog.Builder(ScanReceipts.this, R.style.Theme_AppCompat_DayNight_Dialog_Alert);
            final View dialogView = LayoutInflater.from(ScanReceipts.this).inflate(R.layout.dialog, null);
            customizeDialog.setView(dialogView);
            customizeDialog.setCancelable(true);

            final AlertDialog dialog = customizeDialog.create();

            // 获取EditView中的输入内容
            final ImageView close = dialogView.findViewById(R.id.dialog_close);
            final EditText input = dialogView.findViewById(R.id.dialog_input);
            final Button ok = dialogView.findViewById(R.id.dialog_ok);

            ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String result = input.getText().toString().trim();
                    if (StringUtils.isNotEmpty(result)){
                        presenter.add(result);
                        dialog.dismiss();
                    }else{
                        Toast.makeText(ScanReceipts.this, R.string.please_input_number, Toast.LENGTH_SHORT).show();
                    }
                }
            });

            close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
            dialog.show();
        }
    }

    private void event() {
        // 返回
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        add.setOnClickListener(new ButtonOnClickListener());

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //view.setBackgroundColor(getResources().getColor(R.color.blue3));
                //获取选择的项的值
                Receipts receipts = (Receipts) parent.getItemAtPosition(position);
                Bundle bundle = new Bundle();
                bundle.putSerializable("receipts", receipts);

                Intent intent = new Intent(ScanReceipts.this, Scan.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    @Override
    public void successHint(Map<String, Object> response, String tag) {
        if (tag.equals("findAll")) {
            List<Receipts> receipts = (List<Receipts>) response.get("receiptsList");
            if (receipts.size() > 0) {
                receiptsList.addAll(receipts);
                viewListAdapter.notifyDataSetChanged();
            } else {
                if (currentPage > 1){
                    Toast.makeText(this, "我是有底线的!", Toast.LENGTH_SHORT).show();
                }
            }
        }

        if (tag.equals("save")) {
            Toast.makeText(this, "添加成功", Toast.LENGTH_SHORT).show();
            viewListAdapter.notifyDataSetChanged();
            //presenter.findAll(currentPage, pageSize);
        }
    }

    @Override
    public void failHint(Map<String, Object> response, String tag) {
        if (tag.equals("findAll")) {
            Toast.makeText(this, "查询数据异常", Toast.LENGTH_SHORT).show();
        }
        if (tag.equals("save")) {
            Toast.makeText(this, "添加失败", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onLoad() {
        //获取更多数据
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                currentPage ++;
                presenter.findAll(currentPage, pageSize);
                //通知listView显示更新,加载完毕

                /**
                 * 设置默认显示为Listview最后一行
                 */
                //listView.setTranscriptMode(ListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
                //listView.setStackFromBottom(true);
                //通知listView加载完毕，底部布局消失
                listView.loadComplete();
            }
        }, 500);
    }
}

package com.zrz.inventory.view;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.app.Activity;
import android.support.annotation.ColorInt;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.*;
import com.zrz.inventory.R;
import com.zrz.inventory.adapter.ViewListAdapter;
import com.zrz.inventory.adapter.ViewPagerAdapter;
import com.zrz.inventory.bean.Receipts;
import com.zrz.inventory.presenter.ReceiptsPresenter;
import com.zrz.inventory.view.viewinter.ViewReceipts;

import java.util.ArrayList;
import java.util.List;

public class ScanReceipts extends Activity implements ViewReceipts {

    private ViewPager viewPager;
    private ListView listView;
    /**
     * 返回
     */
    private ImageView scanReceiptsBack;
    /**
     * 添加
     */
    private ImageView add;
    /**
     * 全选
     */
    private Button checkAll;
    /**
     * 删除
     */
    private Button delete;
    private PopupWindow popupWindow;
    private ReceiptsPresenter presenter;
    private List<Receipts> receiptsList = new ArrayList<>();
    private ViewListAdapter viewListAdapter;
    private Integer current_index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scan_receipts);

        initView();
        initData();
        event();
        presenter = new ReceiptsPresenter(this, ScanReceipts.this);
    }

    private void initView() {
        listView = findViewById(R.id.listView);
        scanReceiptsBack = findViewById(R.id.scan_receipts_back);
        add = findViewById(R.id.add);
        checkAll = findViewById(R.id.check_all);
        delete = findViewById(R.id.delete);
    }

    /**
     * 初始化数据
     */
    private void initData() {
        //初始化10项数据
        Receipts receipts = null;
        for (int i = 1; i <= 10; i++) {
            receipts = new Receipts();
            receipts.setNumber("100" + i);
            receipts.setMatched(i + "");
            receipts.setCount(i + "");
            receipts.setId(1);
            receiptsList.add(receipts);
        }

        //设置ListView的适配器
        viewListAdapter = new ViewListAdapter(this, receiptsList);
        listView.setAdapter(viewListAdapter);
        listView.setSelection(1);


    }

    public void setViewDisEnabled() {
        checkAll.setVisibility(View.GONE);
        delete.setVisibility(View.GONE);
    }

    private void event() {
        // 返回
        scanReceiptsBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(ScanReceipts.this);
                //    通过LayoutInflater来加载一个xml的布局文件作为一个View对象
                View view = LayoutInflater.from(ScanReceipts.this).inflate(R.layout.dialog, null);
                //    设置我们自己定义的布局文件作为弹出框的Content
                builder.setView(view);

                final ImageView close = view.findViewById(R.id.dialog_close);
                final EditText input = view.findViewById(R.id.dialog_input);
                //final Button confirm = view.findViewById(R.id.dialog_confirm);

                close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(ScanReceipts.this, "关闭弹窗！", Toast.LENGTH_LONG).show();
                        builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
                            @Override
                            public void onDismiss(DialogInterface dialogInterface) {
                                dialogInterface.dismiss();
                            }
                        });
                    }
                });

                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String result = input.getText().toString().trim();
                        //    将输入的用户名和密码打印出来
                        Toast.makeText(ScanReceipts.this, "您输入的内容是： " + result, Toast.LENGTH_SHORT).show();
                        presenter.add(result);
                    }
                });

                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                /**confirm.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                Toast.makeText(ScanReceipts.this, "您输入的内容是：" + input.getText(), Toast.LENGTH_LONG).show();
                }
                });*/
                builder.show();
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                view.setBackgroundColor(getResources().getColor(R.color.blue3));
                //获取选择的项的值
                Receipts receipts = (Receipts) parent.getItemAtPosition(position);
                Bundle bundle = new Bundle();
                bundle.putSerializable("receipts",receipts);

                Intent intent = new Intent(ScanReceipts.this, Scan.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    @Override
    public void successHint(Receipts receipts, String tag) {
        Toast.makeText(this, tag + "成功", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void failHint(Receipts receipts, String tag) {
        Toast.makeText(this, tag + "失败", Toast.LENGTH_SHORT).show();
    }
}

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
import java.util.Map;

public class ScanReceipts extends Activity implements ViewReceipts {

    private ListView listView;
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
    private Integer pageSize = 10;

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
        listView = findViewById(R.id.listView);
        back = findViewById(R.id.scan_receipts_back);
        add = findViewById(R.id.add);
    }

    /**
     * 初始化数据
     */
    private void initData() {
        //初始化10项数据
        /*Receipts receipts = null;
        for (int i = 1; i <= 1; i++) {
            receipts = new Receipts();
            receipts.setNumber("100" + i);
            receipts.setMatched("");
            receipts.setCount("");
            receipts.setId(1);
            receiptsList.add(receipts);
        }*/
        presenter.findAll(currentPage, pageSize);

        //设置ListView的适配器
        viewListAdapter = new ViewListAdapter(this, receiptsList);
        listView.setAdapter(viewListAdapter);
        listView.setSelection(1);

    }

    public void refreshListView(){
        presenter.findAll(currentPage,pageSize);
        viewListAdapter.notifyDataSetChanged();
    }

    private void event() {
        // 返回
        back.setOnClickListener(new View.OnClickListener() {
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
                bundle.putSerializable("receipts", receipts);

                Intent intent = new Intent(ScanReceipts.this, Scan.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    @Override
    public void successHint(Map<String, Object> response, String tag) {
        if (tag.equals("findAll")){
            receiptsList.clear();
            receiptsList.addAll((List<Receipts>) response.get("receiptsList"));
            viewListAdapter.notifyDataSetChanged();
        }

        if (tag.equals("save")){
            Toast.makeText(this, "添加成功", Toast.LENGTH_SHORT).show();
            presenter.findAll(currentPage, pageSize);
        }
    }

    @Override
    public void failHint(Map<String, Object> response, String tag) {
        if (tag.equals("findAll")){
            receiptsList = (List<Receipts>) response.get("receiptsList");
        }
        if (tag.equals("save")){
            Toast.makeText(this, "添加失败", Toast.LENGTH_SHORT).show();
        }
    }
}

package com.zrz.inventory.view;

import android.content.DialogInterface;
import android.os.Bundle;
import android.app.Activity;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.*;
import com.zrz.inventory.R;
import com.zrz.inventory.adapter.ViewListAdapter;
import com.zrz.inventory.bean.Receipts;
import com.zrz.inventory.presenter.ReceiptsPresenter;
import com.zrz.inventory.view.viewinter.ViewReceipts;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Clean extends Activity implements ViewReceipts {

    private ViewPager viewPager;
    private ListView listView;
    /**
     * 返回
     */
    private ImageView back;
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
    private List<Integer> indexs = new ArrayList<>(10);
    private Integer currentPage = 1;
    private Integer pageSize = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.clean);

        presenter = new ReceiptsPresenter(this, Clean.this);

        initView();

        initData();

        event();

    }

    private void initView() {
        listView = findViewById(R.id.clean_listView);
        back = findViewById(R.id.clean_back);
        checkAll = findViewById(R.id.check_all);
        delete = findViewById(R.id.delete);
    }

    private void initData() {
        // 查询数据
        presenter.findAll(currentPage, pageSize);

        //设置ListView的适配器
        viewListAdapter = new ViewListAdapter(this, receiptsList);
        listView.setAdapter(viewListAdapter);
        listView.setSelection(1);
    }

    public void event() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //获取选择的项的值
                Receipts receipts = (Receipts) parent.getItemAtPosition(position);
                if (null != indexs && indexs.contains(receipts.getId())) {
                    indexs.remove(receipts.getId());
                    view.setBackgroundColor(0);
                } else {
                    indexs.add(receipts.getId());
                    view.setBackgroundColor(getResources().getColor(R.color.blue3));
                    //viewListAdapter.notifyDataSetChanged();
                }
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (indexs.size() == 0){
                    Toast.makeText(Clean.this, "请您先选中一行！", Toast.LENGTH_SHORT).show();
                }else{
                    //    通过AlertDialog.Builder这个类来实例化我们的一个AlertDialog的对象
                    AlertDialog.Builder builder = new AlertDialog.Builder(Clean.this);
                    //    设置Content来显示一个信息
                    builder.setMessage("确定删除吗？");
                    //    设置一个PositiveButton
                    builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            presenter.delete(indexs);
                        }
                    });
                    //    设置一个NegativeButton
                    builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    //    显示出该对话框
                    builder.show();
                }
            }
        });

        checkAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                indexs = new ArrayList<>(10);
            }
        });
    }

    @Override
    public void successHint(Map<String, Object> response, String tag) {
        if (tag.equals("findAll")) {
            receiptsList.clear();
            receiptsList.addAll((List<Receipts>) response.get("receiptsList"));
            viewListAdapter.notifyDataSetChanged();
        }
        if (tag.equals("delete")) {
            Toast.makeText(this, (String) response.get("message"), Toast.LENGTH_SHORT).show();
            presenter.findAll(currentPage, pageSize);
        }
    }

    @Override
    public void failHint(Map<String, Object> response, String tag) {
        if (tag.equals("findAll")) {
            receiptsList = (List<Receipts>) response.get("receiptsList");
            viewListAdapter.notifyDataSetChanged();
        }
    }
}

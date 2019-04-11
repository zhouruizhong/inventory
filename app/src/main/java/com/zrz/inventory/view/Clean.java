package com.zrz.inventory.view;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.app.Activity;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.*;
import com.zrz.inventory.R;
import com.zrz.inventory.adapter.ViewListAdapter;
import com.zrz.inventory.bean.Receipts;
import com.zrz.inventory.presenter.ReceiptsPresenter;
import com.zrz.inventory.view.viewinter.ViewReceipts;
import com.zrz.inventory.widget.LoadListView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Clean extends Activity implements ViewReceipts, LoadListView.ILoadListener {

    private ViewPager viewPager;
    private LoadListView listView;
    /**
     * 返回
     */
    private ImageView back;
    /**
     * Load
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
        listView.setInterface(this);
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
                if(viewListAdapter.isItemSelected(position)){
                    viewListAdapter.removeselected();
                    view.setBackgroundColor(0);
                }else{
                    viewListAdapter.addSelected(position);
                    view.setBackgroundColor(getResources().getColor(R.color.blue3));
                }
                viewListAdapter.notifyDataSetInvalidated();
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
                final List<Integer> selectList = viewListAdapter.getSelectList();
                if (selectList.size() == 0) {
                    Toast.makeText(Clean.this, "请您先选中一行！", Toast.LENGTH_SHORT).show();
                } else {
                    //    通过AlertDialog.Builder这个类来实例化我们的一个AlertDialog的对象
                    AlertDialog.Builder builder = new AlertDialog.Builder(Clean.this);
                    //    设置Content来显示一个信息
                    builder.setMessage("确定删除吗？");
                    //    设置一个PositiveButton
                    builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            presenter.delete(selectList);
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

        /**
         * 全选
         */
        checkAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewListAdapter.removeselected();
                for (Receipts receipts: receiptsList){
                    viewListAdapter.addSelected(receipts.getId());
                }
                viewListAdapter.notifyDataSetInvalidated();
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
                if (currentPage > 1) {
                    Toast.makeText(this, "没有更多了!", Toast.LENGTH_SHORT).show();
                    viewListAdapter.notifyDataSetChanged();
                }
            }
        }
        if (tag.equals("delete")) {
            Toast.makeText(this, (String) response.get("message"), Toast.LENGTH_SHORT).show();
            presenter.findAll(currentPage, pageSize);
        }
    }

    @Override
    public void failHint(Map<String, Object> response, String tag) {
        if (tag.equals("findAll")) {
            Toast.makeText(this, "没有更多了!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onLoad() {
        //获取更多数据
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // 下一页
                currentPage++;
                presenter.findAll(currentPage, pageSize);

                listView.setTranscriptMode(ListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
                listView.setStackFromBottom(true);
                //通知listView加载完毕，底部布局消失
                listView.loadComplete();
            }

        }, 500);
    }
}

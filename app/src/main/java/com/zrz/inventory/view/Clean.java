package com.zrz.inventory.view;

import android.os.Bundle;
import android.app.Activity;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.*;
import com.zrz.inventory.R;
import com.zrz.inventory.adapter.ViewListAdapter;
import com.zrz.inventory.bean.Receipts;
import com.zrz.inventory.presenter.ReceiptsPresenter;

import java.util.ArrayList;
import java.util.List;

public class Clean extends Activity {

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
    private Integer current_index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.clean);

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
        //初始化10项数据
        Receipts receipts = null;
        for (int i = 1; i <= 20; i++) {
            receipts = new Receipts();
            receipts.setNumber("100"+i);
            receipts.setMatched(i + "");
            receipts.setCount(i + "");
            receiptsList.add(receipts);
        }

        //设置ListView的适配器
        viewListAdapter = new ViewListAdapter(this, receiptsList);
        viewListAdapter.notifyDataSetChanged();
        listView.setAdapter(viewListAdapter);
        listView.setSelection(1);
    }

    public void event(){
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //获取选择的项的值
                Receipts receipts = (Receipts) parent.getItemAtPosition(position);
                if (null != current_index && position == current_index){
                    view.setBackgroundColor(getResources().getColor(R.color.white));
                }else{
                    view.setBackgroundColor(getResources().getColor(R.color.blue3));
                    current_index = position;
                    viewListAdapter.notifyDataSetChanged();
                }
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void setEnabled(){
        checkAll.setVisibility(View.VISIBLE);
        delete.setVisibility(View.VISIBLE);
    }

    public void setDisEnable(){
        checkAll.setVisibility(View.INVISIBLE);
        delete.setVisibility(View.INVISIBLE);
    }
}

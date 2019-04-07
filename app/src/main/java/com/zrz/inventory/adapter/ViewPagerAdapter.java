package com.zrz.inventory.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.zrz.inventory.R;
import com.zrz.inventory.bean.BarCode;
import com.zrz.inventory.bean.Receipts;
import com.zrz.inventory.bean.ReceiptsDetail;
import com.zrz.inventory.fragment.KeyDwonFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 周瑞忠
 * @date 2019/3/31 17:05
 */
public class ViewPagerAdapter extends BaseAdapter {

    /**
     * 上下文对象
     */
    private Context context;
    /**
     * ListView显示的数据
     */
    private List<ReceiptsDetail> dataList;

    /**
     * 构造器
     *
     * @param context  上下文对象
     * @param dataList 数据
     */
    public ViewPagerAdapter(Context context, List<ReceiptsDetail> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    @Override
    public int getCount() {
        return dataList == null ? 0 : dataList.size();
    }

    @Override
    public Object getItem(int position) {
        return dataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewPagerAdapter.ViewHolder viewHolder;
        //判断是否有缓存
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.data, null);
            viewHolder = new ViewPagerAdapter.ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            //得到缓存的布局
            viewHolder = (ViewPagerAdapter.ViewHolder) convertView.getTag();
        }
        ReceiptsDetail receiptsDetail = dataList.get(position);
        //设置内容
        viewHolder.item1.setText(receiptsDetail.getItem1());
        viewHolder.item2.setText(receiptsDetail.getItem2());
        viewHolder.item3.setText(receiptsDetail.getItem3());
        viewHolder.item4.setText(receiptsDetail.getItem4());
        return convertView;
    }

    /**
     * ViewHolder类
     */
    private final class ViewHolder {

        TextView item1;
        TextView item2;
        TextView item3;
        TextView item4;

        /**
         * 构造器
         *
         * @param view 视图组件（ListView的子项视图）
         */
        ViewHolder(View view) {
            item1 = view.findViewById(R.id.item1);
            item2 = view.findViewById(R.id.item2);
            item3 = view.findViewById(R.id.item3);
            item4 = view.findViewById(R.id.item4);
        }
    }
}

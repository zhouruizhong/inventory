package com.zrz.inventory.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import com.zrz.inventory.R;
import com.zrz.inventory.bean.Receipts;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 周瑞忠
 * @description java类作用描述
 * @date 2019/4/2 15:00
 */
public class ViewListAdapter extends BaseAdapter {
    /**
     * 上下文对象
     */
    private Context context;
    /**
     * ListView显示的数据
     */
    private List<Receipts> dataList;
    /**
     * 选中的
     */
    private List<Integer> selectList;

    /**
     * 构造器
     *
     * @param context  上下文对象
     * @param dataList 数据
     */
    public ViewListAdapter(Context context, List<Receipts> dataList) {
        super();
        this.context = context;
        this.dataList = dataList;
        selectList = new ArrayList<Integer>();
    }

    @Override
    public int getCount() {
        return dataList == null ? 0 : dataList.size();
    }

    public List<Integer> getSelectList(){
        return selectList;
    }

    @Override
    public Object getItem(int position) {
        return dataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * 加入到选中集合
     * @param position
     */
    public void addSelected(int position) {
        selectList.add(position);
    }

    /**
     * 取消选中
     * @param position
     */
    public void removeSelected(Integer position) {
        if (selectList.contains(position)) {
            selectList.remove(position);
        }
    }

    /**
     * 清空选中集合
     */
    public void removeselected(){
        selectList.clear();
    }

    /**
     * 判读是否包含当前的条目
     */
    public boolean isItemSelected(int position) {
        return selectList.contains(position) ? true : false;
    }

    /**
     * 获取listView中item的布局
     * @param pos 位置
     * @param listView listView
     * @return
     */
    public View getViewByPosition(int pos, ListView listView) {
        final int firstListItemPosition = listView.getFirstVisiblePosition();
        final int lastListItemPosition = firstListItemPosition + listView.getChildCount() - 1;

        if (pos < firstListItemPosition || pos > lastListItemPosition) {
            return listView.getAdapter().getView(pos, null, listView);
        } else {
            final int childIndex = pos - firstListItemPosition;
            return listView.getChildAt(childIndex);
        }
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        //判断是否有缓存
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.receipts, null);
            viewHolder = new ViewHolder(convertView);
            //设置内容
            convertView.setTag(viewHolder);
        } else {
            //得到缓存的布局
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Receipts receipts = dataList.get(position);
        //设置内容
        viewHolder.number.setText(receipts.getNumber());
        viewHolder.matched.setText(receipts.getMatched());
        viewHolder.count.setText(receipts.getCount());
        return convertView;
    }

    /**
     * ViewHolder类
     */
    private final class ViewHolder {

        /**
         * 编号
         */
        TextView number;
        /**
         * 已匹配
         */
        TextView matched;
        /**
         * 总数量
         */
        TextView count;

        /**
         * 构造器
         *
         * @param view 视图组件（ListView的子项视图）
         */
        ViewHolder(View view) {
            number = view.findViewById(R.id.receipts_code_value);
            matched = view.findViewById(R.id.receipts_matched_value);
            count = view.findViewById(R.id.receipts_count_value);
        }
    }
}

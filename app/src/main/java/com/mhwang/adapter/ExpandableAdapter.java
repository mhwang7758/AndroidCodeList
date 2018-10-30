package com.mhwang.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;


import com.mhwang.demolist.R;

import java.util.ArrayList;

/**
 * Description : 扩展列表适配器
 * Author :mhwang
 * Date : 2018/10/29
 * Version : V1.0
 */

public class ExpandableAdapter extends BaseExpandableListAdapter {
    private String[] mGroupItems;
    private ArrayList<String[]> mChildItems;
    private LayoutInflater mLayoutInflater;
    /**
     *  每个组下面的资源ID,需要和mGroupItems中的字符对应,大小一般情况下与mGropuItems相同
     */
    private int[] mChildItemIds = {R.array.expandable_child_ui,R.array.expandable_child_screen,
            R.array.expandable_child_tool};

    public ExpandableAdapter(Context context){
        mLayoutInflater = LayoutInflater.from(context);
        initData(context);
    }

    /**
     *  初始化数据
     */
    private void initData(Context context){
        mGroupItems = context.getResources().getStringArray(R.array.expandable_group);
        mChildItems = new ArrayList<>();
        int length = mGroupItems.length;
        // 为每个组添加资源
        for(int i = 0; i < length; i++){
            mChildItems.add(context.getResources().getStringArray(mChildItemIds[i]));
        }
    }

    @Override
    public int getGroupCount() {
        return mGroupItems.length;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return mChildItems.get(groupPosition).length;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return mGroupItems[groupPosition];
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return mChildItems.get(groupPosition)[childPosition];
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        convertView = mLayoutInflater.inflate(R.layout.item_expandable_group,null);
        TextView tv_group = convertView
                .findViewById(R.id.tv_group);
        tv_group.setText(mGroupItems[groupPosition]);
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        convertView = mLayoutInflater.inflate(R.layout.item_expandable_child, null);
        TextView tv_child = convertView
                .findViewById(R.id.tv_child);
        tv_child.setText(mChildItems.get(groupPosition)[childPosition]);
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}

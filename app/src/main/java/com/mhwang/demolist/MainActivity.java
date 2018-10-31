package com.mhwang.demolist;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ExpandableListView;

import com.mhwang.adapter.ExpandableAdapter;
import com.mhwang.dialog.ButtonsDialog;
import com.mhwang.dialog.ScreenDialog;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends Activity {

    public static final int GROUP_UI = 0;
    public static final int SCREEN = 1;
    @BindView(R.id.elv_itemType)
    ExpandableListView elv_itemType;

    private ExpandableAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mAdapter = new ExpandableAdapter(this);
        elv_itemType.setAdapter(mAdapter);

        elv_itemType.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                // 判断是哪组按钮点击
                switch (groupPosition){
                    case GROUP_UI:
                        showUIDialog(childPosition);
                        break;
                    case SCREEN:
                        showScreenParamDialog(childPosition);
                        break;
                }
                return true;
            }
        });
    }

    /** 显示UI对话框
     * @param position 点击的按钮下标
     */
    private void showUIDialog(int position){
        Intent intent = null;
        switch (position){
            case 0:
                intent = new Intent(this, ButtonsDialog.class);
                break;
        }
        if (intent != null){
            startActivity(intent);
        }
    }

    /** 显示屏幕相关参数对话框
     * @param position 点击的按钮下标
     */
    private void showScreenParamDialog(int position){
        Intent intent = new Intent(this, ScreenDialog.class);
        intent.putExtra(ScreenDialog.OPTION_TYPE, position);
        startActivity(intent);
    }
}

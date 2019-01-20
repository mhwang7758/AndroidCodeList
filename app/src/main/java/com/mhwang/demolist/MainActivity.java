package com.mhwang.demolist;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.view.Window;
import android.widget.ExpandableListView;

import com.mhwang.adapter.ExpandableAdapter;
import com.mhwang.dialog.ButtonsDialog;
import com.mhwang.dialog.OneInputDialog;
import com.mhwang.dialog.ScreenDialog;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends Activity {

    public static final int GROUP_UI = 0;
    public static final int GROUP_SCREEN = 1;
    public static final int GROUP_BYTE_UTIL = 2;
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
                    case GROUP_SCREEN:
                        showScreenParamDialog(childPosition);
                        break;
                    case GROUP_BYTE_UTIL:
                        showByteUtilDialog(childPosition);
                        break;
                }
                return true;
            }
        });
    }

    /** 显示字节操作对话框
     * @param position
     */
    private void showByteUtilDialog(int position){
        // 为防止对话框里面识别错乱，规定在每个组的顺序*100再加下标
        position = 200 + position;
        Intent intent = null;
        switch (position){
            case OneInputDialog.OPERATION_GET_EACH_BYTE:
                intent = getOneInputDlgIntent(OneInputDialog.OPERATION_GET_EACH_BYTE, R.string.title_each_byte,
                        R.string.hint_each_byte, OneInputDialog.DEFAULT_INPUT_TYPE);
                break;
            case OneInputDialog.OPERATION_GET_INT_BYTES_HL:
            case OneInputDialog.OPERATION_GET_INT_BYTES_LH:
                int opoType;
                if (position == OneInputDialog.OPERATION_GET_INT_BYTES_HL) {
                    opoType = OneInputDialog.OPERATION_GET_INT_BYTES_HL;
                }else{
                    opoType = OneInputDialog.OPERATION_GET_INT_BYTES_LH;
                }
                intent = getOneInputDlgIntent(opoType, R.string.title_int_bytes,
                        R.string.hint_int_value, InputType.TYPE_CLASS_NUMBER);
            case OneInputDialog.OPERATION_CRC16:
            case OneInputDialog.OPERATION_XOR:
                break;
            case OneInputDialog.OPERATION_DECIMAL_FORMAT:
                intent = getOneInputDlgIntent(OneInputDialog.OPERATION_DECIMAL_FORMAT, R.string.title_decimal_format,
                        R.string.hint_decimal, InputType.TYPE_NUMBER_FLAG_DECIMAL);
                break;
            case OneInputDialog.OPERATION_IS_NUMBER:
                intent = getOneInputDlgIntent(OneInputDialog.OPERATION_IS_NUMBER, R.string.title_is_number,
                        R.string.hint_is_number, OneInputDialog.DEFAULT_INPUT_TYPE);
                break;
        }

        if (intent != null){
            startActivity(intent);
        }
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
        // 由于多个功能可能重用一个对话框，为防止对话框里面识别错乱，规定在每个组的顺序*100再加下标
        position = 100 + position;
        Intent intent = new Intent(this, ScreenDialog.class);
        intent.putExtra(ScreenDialog.KEY_OPTION_TYPE, position);
        startActivity(intent);
    }

    /** 获取1个输入框对话框的intent
     * @param opoType          操作类型(由于对话框是共用，需要根据不同的操作类型作不同的动作)
     * @param resTitle         对话框标题
     * @param resHint          对话框中的输入框提示
     * @param inputType        对话框中的输入框输入限制
     * @return Intent
     */
    private Intent getOneInputDlgIntent(int opoType, int resTitle, int resHint, int inputType){
        String title = getString(resTitle);
        String hint = getString(resHint);
        Intent intent = new Intent(this, OneInputDialog.class);
        intent.putExtra(OneInputDialog.KEY_OPERATION_TYPE, opoType);
        intent.putExtra(OneInputDialog.KEY_TITLE, title);
        intent.putExtra(OneInputDialog.KEY_EDIT_HINT, hint);
        intent.putExtra(OneInputDialog.KEY_EDIT_INPUT_TYPE, inputType);
        return intent;

    }
}

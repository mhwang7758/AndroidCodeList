package com.mhwang.dialog;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.view.Window;
import android.widget.TextView;

import com.mhwang.demolist.R;
import com.mhwang.utils.ScreenUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Author : mhwang
 * Date : $Date$
 * Version : V1.0
 */
public class ScreenDialog extends Activity {

    public static final String KEY_OPTION_TYPE = "type";
    public static final int GET_WIDTH_HEIGHT = 100;             // 操作类型的下标，与array里的expandable_child_screen对应
    public static final int IS_LAND = 101;

    @BindView(R.id.tv_screenParam)
    TextView tv_screenParam;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_screen);
        ButterKnife.bind(this);
        int type = getIntent().getIntExtra(KEY_OPTION_TYPE, -1);
        showParam(type);
    }

    /** 显示参数
     * @param type 操作类型
     */
    private void showParam(int type){
        String message = "找不到相关的参数";
        switch (type){
            case GET_WIDTH_HEIGHT:
                DisplayMetrics metrics = ScreenUtil.getScreenMetrics(this);
                message = "宽："+ metrics.widthPixels + " 高："+metrics.heightPixels;
                break;
            case IS_LAND:
                message = ScreenUtil.isLand(this) ? "处于横屏" : "处于竖屏";
                break;
                default:
                    break;
        }
        tv_screenParam.setText(message);
    }
}

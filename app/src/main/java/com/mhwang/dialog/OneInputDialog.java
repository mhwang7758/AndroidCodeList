package com.mhwang.dialog;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;

import com.mhwang.demolist.R;
import com.mhwang.utils.BytesUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Author : mhwang
 * Date : 2018/12/12
 * Version : V1.0
 */
public class OneInputDialog extends Activity {
    public static final String KEY_TITLE = "title";
    public static final String KEY_EDIT_HINT = "hint";
    public static final String KEY_OPERATION_TYPE = "type";


    /**
     *  获取16进制字符串每位值
     */
    public static final int OPERATION_GET_EACH_BYTE = 200;



    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.et_input)
    EditText et_input;
    @BindView(R.id.tv_result)
    TextView tv_result;

    private int mOpoType;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_one_input);
        ButterKnife.bind(this);
        // 获取各项参数
        Intent intent = getIntent();
        String title = intent.getStringExtra(KEY_TITLE);
        if (!TextUtils.isEmpty(title)){
            tv_title.setText(title);
        }

        String hint = intent.getStringExtra(KEY_EDIT_HINT);
        if (!TextUtils.isEmpty(hint)){
            et_input.setHint(hint);
        }

        mOpoType = intent.getIntExtra(KEY_OPERATION_TYPE, 0);
    }

    @OnClick(R.id.btn_sure)
    void clickSure(){
        switch (mOpoType){
            case OPERATION_GET_EACH_BYTE:
                showEachByteResult();
                break;
        }
    }

    /**
     *  显示每个字节位结果
     */
    private void showEachByteResult(){
        String input = et_input.getText().toString().trim();
        if (TextUtils.isEmpty(input)){
            return;
        }

        byte[] result = BytesUtil.getEachByteValues(input);

        if (result == null) return;

        StringBuilder builder = new StringBuilder();
        for (byte b : result){
            builder.append(b);
        }
        tv_result.setText(builder.toString());
    }
}

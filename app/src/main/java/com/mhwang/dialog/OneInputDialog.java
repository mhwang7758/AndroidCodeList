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
import com.mhwang.utils.NumberFormat;

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
    public static final String KEY_EDIT_INPUT_TYPE = "inputType";

    public static final int DEFAULT_INPUT_TYPE = -1;

    /**
     *  获取16进制字符串每位值
     */
    public static final int OPERATION_GET_EACH_BYTE = 200;
    /**
     *  获取int类型的数字bytes数组(高位在前，低位在后)
     */
    public static final int OPERATION_GET_INT_BYTES_HL = 201;
    public static final int OPERATION_GET_INT_BYTES_LH = 202;
    /**
     *  crc16校验和异或校验
     */
    public static final int OPERATION_CRC16 = 203;
    public static final int OPERATION_XOR = 204;

    /**
     *  格式化小数
     */
    public static final int OPERATION_DECIMAL_FORMAT = 205;
    /**
     *  判断字符串是否数字
     */
    public static final int OPERATION_IS_NUMBER = 206;

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

        int inputType = getIntent().getIntExtra(KEY_EDIT_INPUT_TYPE, DEFAULT_INPUT_TYPE);
        if (inputType != DEFAULT_INPUT_TYPE){
            et_input.setInputType(inputType);
        }

        mOpoType = intent.getIntExtra(KEY_OPERATION_TYPE, 0);
    }

    @OnClick(R.id.btn_sure)
    void clickSure(){
        switch (mOpoType){
            case OPERATION_GET_EACH_BYTE:
                showEachByteResult();
                break;
            case OPERATION_GET_INT_BYTES_HL:
            case OPERATION_GET_INT_BYTES_LH:
                showGetIntBytesResult();
                break;
            case OPERATION_DECIMAL_FORMAT:
                showDecimalFormatResult();
                break;
            case OPERATION_IS_NUMBER:
                showIsNumberResult();
                break;
        }
    }

    /**
     *  显示判断字符串数字结果
     */
    private void showIsNumberResult() {
        String input = et_input.getText().toString().trim();
        if (TextUtils.isEmpty(input)){
            return;
        }

        boolean numeric = NumberFormat.isNumber(input);
        tv_result.setText(numeric? "该字符串为数字" : "该字符串不是数字");
    }

    /**
     *  显示格式化小数结果
     */
    private void showDecimalFormatResult() {
        String input = et_input.getText().toString().trim();
        if (TextUtils.isEmpty(input)){
            return;
        }
        String result = NumberFormat.getPriceFormat(Double.parseDouble(input));
        tv_result.setText(result);
    }

    /**
     *  显示获取int数字的byte数组结果
     */
    private void showGetIntBytesResult() {
        String input = et_input.getText().toString().trim();
        if (TextUtils.isEmpty(input)){
            return;
        }
        byte[] result;
        if (mOpoType == OPERATION_GET_INT_BYTES_HL) {
            result = BytesUtil.int2bytesHFirst(Integer.parseInt(input));
        }else{
            result = BytesUtil.int2bytesLFirst(Integer.parseInt(input));
        }
        if (result == null) return;

        StringBuilder builder = new StringBuilder();
        builder.append("[");
        for (byte b : result){
            builder.append(b)
                    .append(",");
        }
        builder.deleteCharAt(builder.length()-1)
                .append("]");

        tv_result.setText(builder.toString());
    }

    /**
     *  显示每个字节位结果
     */
    private void showEachByteResult(){
        String input = et_input.getText().toString().trim();
        if (TextUtils.isEmpty(input)){
            return;
        }

        if (input.toUpperCase().startsWith("0X")){
            input = input.substring(2, input.length());
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

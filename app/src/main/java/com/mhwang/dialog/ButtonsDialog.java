package com.mhwang.dialog;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Window;

import com.mhwang.demolist.R;

/**
 * Author : mhwang
 * Date : $Date$
 * Version : V1.0
 */
public class ButtonsDialog extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_buttons);
    }
}

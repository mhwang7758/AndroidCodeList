package com.mhwang.demolist;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.widget.ExpandableListView;

import com.mhwang.adapter.ExpandableAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

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
    }
}

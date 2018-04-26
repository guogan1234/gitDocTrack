package com.avantport.avp.njpb.ui.activity;

import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.avantport.avp.njpb.R;
import com.avantport.avp.njpb.bean.PutInbean;

import java.util.ArrayList;
import java.util.List;

public class TakeOutActivity extends BaseActivity implements View.OnClickListener {

    private TextView mHead_title;
    private ImageView mHead_goback;
    private ImageView mHead_code;

    private List<PutInbean> work = new ArrayList<>();
    private ListView mList_takeout;

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_put_in_work;
    }

    protected void initView() {
        mHead_title = (TextView) findViewById(R.id.head_title);
        mHead_goback = (ImageView) findViewById(R.id.head_goback);
        mHead_code = (ImageView) findViewById(R.id.head_qrcode);
        mHead_title.setText("申请领料");
        mHead_goback.setOnClickListener(this);
        mHead_code.setVisibility(View.INVISIBLE);


    }

    @Override
    protected void initDatas() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.head_goback:
             checkDialog();
                break;

        }

    }
}

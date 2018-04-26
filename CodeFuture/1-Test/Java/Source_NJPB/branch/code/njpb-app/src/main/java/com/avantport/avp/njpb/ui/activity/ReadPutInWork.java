package com.avantport.avp.njpb.ui.activity;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.avantport.avp.njpb.R;

public class ReadPutInWork extends BaseActivity implements View.OnClickListener {

    private TextView mHead_title;
    private ImageView mHead_goback;
    private ImageView mHead_read;
    private TextView mCompany_type;
    private TextView mBike_count;
    private ListView mList_read;
    private Button mFinish;

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_read_put_in_work;
    }

    protected void initView() {
        mHead_title = (TextView) findViewById(R.id.head_title);
        mHead_goback = (ImageView) findViewById(R.id.head_goback);
        mHead_read = (ImageView) findViewById(R.id.head_qrcode);
        mHead_title.setText("入库作业");
        mHead_read.setImageResource(R.mipmap.read_carnumber);
        mHead_read.setOnClickListener(this);
        mHead_goback.setOnClickListener(this);
        mHead_read.setOnClickListener(this);

        //公司
        mCompany_type = (TextView) findViewById(R.id.company_type);
        mBike_count = (TextView) findViewById(R.id.bike_count);
        mList_read = (ListView) findViewById(R.id.list_read);
        mFinish = (Button) findViewById(R.id.finish);
        //// TODO: 2017/8/7 未完待续 
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
            case R.id.head_qrcode:
                break;
        }

    }
}

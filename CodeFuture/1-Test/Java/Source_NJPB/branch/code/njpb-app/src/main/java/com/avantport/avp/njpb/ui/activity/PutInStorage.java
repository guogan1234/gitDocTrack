package com.avantport.avp.njpb.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.avantport.avp.njpb.R;

public class PutInStorage extends AppCompatActivity implements View.OnClickListener {

    private ImageView mHead_goback;
    private TextView mHead_title;
    private ImageView mHead_qrcode;
    private LinearLayout mPutin_code;
    private LinearLayout mPutin_read;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_put_in_storage);

        mHead_goback = (ImageView) findViewById(R.id.head_goback);//返回键
        mHead_title = (TextView) findViewById(R.id.head_title);//头布局的标题
        mHead_qrcode = (ImageView) findViewById(R.id.head_qrcode);//二维码
        mPutin_code = (LinearLayout) findViewById(R.id.putin_code);
        mPutin_read = (LinearLayout) findViewById(R.id.putin_read);
        mHead_qrcode.setVisibility(View.INVISIBLE);

        mHead_goback.setOnClickListener(this);
        mPutin_code.setOnClickListener(this);
        mPutin_read.setOnClickListener(this);
        mHead_title.setText("入库方式的选择");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.putin_code:
                //扫二维码
                startActivity(new Intent(this,PutInWorkActivity.class));
                break;
            case R.id.putin_read:
                //读取车牌号
                startActivity(new Intent(this,ReadPutInWork.class));
                break;
            case R.id.head_goback:
                finish();
                break;
        }

    }
}

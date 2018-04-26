package com.avantport.avp.njpb.view;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.TextView;

import com.avantport.avp.njpb.R;

/**
 * Created by len on 2017/8/15.
 * 自定义加载dialog
 */

public class CustomProDialog extends ProgressDialog {


    private TextView mTv_message;
    private String message;


    public CustomProDialog(Context context,String msg) {
        this(context,0);
        message = msg;
    }

    public CustomProDialog(Context context, int theme) {
        super(context,R.style.Custom_Progress);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setCanceledOnTouchOutside(false);
        setContentView(R.layout.layout_progress_dialog);
        getWindow().getAttributes().gravity = Gravity.CENTER;
        setCanceledOnTouchOutside(false);
        mTv_message = (TextView) findViewById(R.id.tv_message);
        mTv_message.setText(message);

    }


  }



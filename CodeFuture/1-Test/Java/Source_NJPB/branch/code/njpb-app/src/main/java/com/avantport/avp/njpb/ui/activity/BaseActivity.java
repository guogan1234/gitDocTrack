package com.avantport.avp.njpb.ui.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.avantport.avp.njpb.view.CustomProDialog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by len on 2017/8/3.
 */

public abstract class BaseActivity extends AppCompatActivity {

    public List<String> dataSelect = new ArrayList<>() ;
    private CustomProDialog mCustomProDialog;
     public Map<String, Object> params = new HashMap<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutRes());
        initView();
        initDatas();
    }
    protected abstract int getLayoutRes();
    protected abstract void initView();
    protected abstract void initDatas();

    //返回之前弹出对话框，友好提示
    public void checkDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("是否离开当前界面？");
        builder.setNegativeButton("否", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.setPositiveButton("是", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();//先销毁dialog再销毁avtivity
                finish();
            }
        });
        builder.show();

    }

    @Override
    public void onBackPressed() {
        checkDialog();//防止失误返回，
    }

    public void showProDialog(String msg) {//进行上传的提示
        mCustomProDialog = new CustomProDialog(this,msg);
        mCustomProDialog.show();

    }

    public void hideDialiog(){
        if (mCustomProDialog!=null) {
            mCustomProDialog.dismiss();
        }

    }

}

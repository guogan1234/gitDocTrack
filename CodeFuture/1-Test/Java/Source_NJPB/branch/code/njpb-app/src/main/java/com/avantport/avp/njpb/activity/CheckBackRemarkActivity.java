package com.avantport.avp.njpb.activity;

import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.avantport.avp.njpb.R;
import com.avantport.avp.njpb.constant.Constant;
import com.avantport.avp.njpb.okhttp.OkHttpBaseCallback;
import com.avantport.avp.njpb.okhttp.OkHttpUtils;
import com.avantport.avp.njpb.ui.activity.BaseActivity;
import com.avantport.avp.njpb.uitls.ToastUtil;

import java.util.HashMap;
import java.util.Map;

import okhttp3.Response;

public class CheckBackRemarkActivity extends BaseActivity implements View.OnClickListener, TextWatcher {


    private static final int CHECKBACK_CODE = 300;
    private String mDetailsId;
    private EditText mContent;
    private Button mMakeSure;
    private ImageView mHeadGoback;
    private Map<String, Object> params = new HashMap<>();

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_check_back_remark;
    }

    @Override
    protected void initView() {
        Intent intent = getIntent();
        mDetailsId = intent.getStringExtra("DetailsId"); //获得条目详情的id

        TextView headTitle = (TextView) findViewById(R.id.head_title);
        ImageView headQrcode = (ImageView) findViewById(R.id.head_qrcode);
        headTitle.setText(R.string.check_take_back);
        headQrcode.setVisibility(View.INVISIBLE);
        mHeadGoback = (ImageView) findViewById(R.id.head_goback);
        mContent = (EditText) findViewById(R.id.content);
        mMakeSure = (Button) findViewById(R.id.make_sure);


    }

    @Override
    protected void initDatas() {
        mHeadGoback.setOnClickListener(this);//返回
        mMakeSure.setOnClickListener(this);//确认退回
        mContent.addTextChangedListener(this);//监听编辑框的文字变化
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.head_goback:
                finish();
                break;
            case R.id.make_sure:
                //填写备注，然后确认退回申请
                returnedApply();
                break;
        }
    }

    private void returnedApply() {//确认退回,显示对话框，
        // 进行网络请求，
        // 失败隐藏对话框，进行提示，
        // 成功的话，隐藏对话框，回到列表界面，更新界面（删除界面上的目）
        //点击显示对话框
        showProDialog("退回中...");
        params.clear();
        params.put("stockWorkOrderId", mDetailsId);
        params.put("rejectRemark ", mContent.getText().toString());
        getNetDatas(Constant.ORDER_CHECK_REFUSE, params);


    }

    private void getNetDatas(String url, Map<String, Object> params) {
        showProDialog("退回中...");
        //进行put请求
        OkHttpUtils.getInstance().put(url, params, null, new OkHttpBaseCallback() {
            @Override
            public void onSuccess(Response response, Object o) {
                hideDialiog();
                setResult(CHECKBACK_CODE);//请求驳回成功，返回成功信息
                finish();
            }

            @Override
            public void onError(Response response, int code, Exception e) {
                hideDialiog();
                ToastUtil.show(CheckBackRemarkActivity.this,"退回失败！");
            }
        });
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        if (mContent.getText().length() == 0) {
            mMakeSure.setEnabled(false);
        } else {
            mMakeSure.setEnabled(true);
        }
    }
}

package com.avantport.avp.njpb.ui.activity;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.acker.simplezxing.activity.CaptureActivity;
import com.avantport.avp.njpb.R;
import com.avantport.avp.njpb.bean.Companybean;
import com.avantport.avp.njpb.bean.DeviceTypebean;
import com.avantport.avp.njpb.bean.Devicebean;
import com.avantport.avp.njpb.constant.Constant;
import com.avantport.avp.njpb.okhttp.OkHttpBaseCallback;
import com.avantport.avp.njpb.okhttp.OkHttpUtils;
import com.avantport.avp.njpb.uitls.ToastUtil;
import com.avantport.avp.njpb.view.CustomDialog;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Response;

public class ChangeCodeActivity extends BaseActivity implements View.OnClickListener {


    private TextView mHeadTitle;
    private ImageView mHeadGoback;
    private ImageView mHeadQrcode;
    private TextView mCompanyType;
    private TextView mStationType;
    private TextView mBreakType;
    private TextView mMachineType;
    private TextView mOldCode;
    private TextView mNewCode;
    private Button mSubmit;
    private List<Companybean.ResultsBean> mCompanyResults;
    private List<String> showDatas = new ArrayList<>();
    private int mCorpId;//公司的id
    private List<DeviceTypebean.ResultsBean> mBreakResults;
    private Integer mBreakTypeId;//设备类型id
    private List<Devicebean.ResultsBean> mMachineResults;
    private Integer mMachineId;//所选设备的id
    private String mStationId;//所选站点的id；


    @Override
    protected int getLayoutRes() {
        return R.layout.activity_change_code;
    }

    protected void initView() {//初始化view
        mHeadTitle = (TextView) findViewById(R.id.head_title);
        mHeadGoback = (ImageView) findViewById(R.id.head_goback);
        mHeadQrcode = (ImageView) findViewById(R.id.head_qrcode);
        mHeadTitle.setText(R.string.change_codes);


        mCompanyType = (TextView) findViewById(R.id.company_type);
        mStationType = (TextView) findViewById(R.id.station_type);
        mBreakType = (TextView) findViewById(R.id.break_type);
        mMachineType = (TextView) findViewById(R.id.machine_type);
        // 旧条码
        mOldCode = (TextView) findViewById(R.id.old_code);
        mNewCode = (TextView) findViewById(R.id.new_code);
        mSubmit = (Button) findViewById(R.id.submit);
    }

    @Override
    protected void initDatas() {
        initSetting();

    }

    private void initSetting() {
        mHeadGoback.setOnClickListener(this);
        mHeadQrcode.setOnClickListener(this);
        mCompanyType.setOnClickListener(this);
        mStationType.setOnClickListener(this);
        mBreakType.setOnClickListener(this);
        mMachineType.setOnClickListener(this);
        mSubmit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.head_goback:
                checkDialog();
                break;
            case R.id.head_qrcode:
                startActivityForResult(new Intent(this, CaptureActivity.class), CaptureActivity.REQ_CODE);
                break;
            case R.id.company_type:
                getCompanyData();
                break;
            case R.id.station_type:
                goToSerachActivity();
                break;
            case R.id.break_type:
                showBreakType();
                break;
            case R.id.machine_type:
                showMachineType();
                break;
            case R.id.submit:
                //进行提交
                // TODO: 2017/9/10
                break;
        }
    }

    private void showMachineType() {//故障设备
        if (mBreakType.getText().length() == 0) {
            ToastUtil.show(this, "请先选择设备类型");
            return;
        }
        if (mCompanyType.getText().length() == 0) {
            ToastUtil.show(this, "请选择所属公司");
        }
        //获取设备信息
        params.clear();
        params.put("estateTypeId", mBreakTypeId);
        params.put("corpId", mCorpId);
        OkHttpUtils.getInstance().get(Constant.SEARCH_DEVICE_NOLIMIT, params, null, new OkHttpBaseCallback<Devicebean>() {
            @Override
            public void onSuccess(Response response, Devicebean devicebean) {
                mMachineResults = devicebean.getResults();
                if (mMachineResults != null && mMachineResults.size() != 0) {
                    showDatas.clear();
                    for (int i = 0; i < mMachineResults.size(); i++) {
                        showDatas.add(mMachineResults.get(i).getEstateName());
                    }
                    showListDialog(showDatas, mMachineType, 3);
                } else {
                    ToastUtil.show(ChangeCodeActivity.this, "数据为空");
                }
            }

            @Override
            public void onError(Response response, int code, Exception e) {
                ToastUtil.show(ChangeCodeActivity.this, "获取数据失败");
            }
        });


    }

    private void showBreakType() {//设备类型
        mMachineType.setText("");
        //网络获取设备类型
        params.clear();
        params.put("category", 1);
        params.put("partsType", 1);
        OkHttpUtils.getInstance().get(Constant.DEVICE_TYPE, params, null, new OkHttpBaseCallback<DeviceTypebean>() {
            @Override
            public void onSuccess(Response response, DeviceTypebean deviceTypebean) {
                //获取到数据
                mBreakResults = deviceTypebean.getResults();
                if (mBreakResults != null && mBreakResults.size() != 0) {
                    //进行数据展示//先清理集合，然后添加数据
                    showDatas.clear();
                    for (int i = 0; i < mBreakResults.size(); i++) {
                        showDatas.add(mBreakResults.get(i).getName());
                    }
                    showListDialog(showDatas, mBreakType, 2);
                } else {
                    ToastUtil.show(ChangeCodeActivity.this, "数据为空");
                }
            }

            @Override
            public void onError(Response response, int code, Exception e) {
                ToastUtil.show(ChangeCodeActivity.this, "获取数据失败");
            }
        });
    }

    private void goToSerachActivity() {

        if (mCompanyType.getText().length() == 0) {
            ToastUtil.show(this, "请先选择公司");
        } else {
            Intent intent = new Intent(this, SearchActivity.class);
            intent.putExtra("CorpId", mCorpId);
            startActivityForResult(intent, 100);
        }
    }

    private void getCompanyData() {//获取公司的信息
        mStationType.setText("");
        showUserCompany();
    }

    private void showUserCompany() {
        OkHttpUtils.getInstance().get(Constant.COMPANY_INFO, null, null, new OkHttpBaseCallback<Companybean>() {
            @Override
            public void onSuccess(Response response, Companybean companybean) {
                mCompanyResults = companybean.getResults();
                if (mCompanyResults != null && mCompanyResults != null) {
                    showDatas.clear();
                    for (int i = 0; i < mCompanyResults.size(); i++) {
                        showDatas.add(mCompanyResults.get(i).getCorpName());
                    }
                    showListDialog(showDatas, mCompanyType, 1);
                }
            }

            @Override
            public void onError(Response response, int code, Exception e) {

            }
        });
    }


    private void showListDialog(List<String> showDatas, final TextView mText, final int type) {

        final CustomDialog customDialog = new CustomDialog(this, showDatas);
        customDialog.setOnDialogItemClick(new CustomDialog.onDialogItemClick() {
            @Override
            public void getDialogItem(String areaName, int position) {
                mText.setText(areaName);
                switch (type) {
                    case 1:
                        Companybean.ResultsBean resultsBean1 = mCompanyResults.get(position);
                        mCorpId = resultsBean1.getId();//公司
                        break;
                    case 2:
                        DeviceTypebean.ResultsBean resultsBean2 = mBreakResults.get(position);
                        mBreakTypeId = resultsBean2.getId();
                        break;
                    case 3:
                        Devicebean.ResultsBean resultsBean3 = mMachineResults.get(position);
                        mMachineId = resultsBean3.getId();
                        break;
                }
                customDialog.dismiss();
            }
        });
        customDialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CaptureActivity.REQ_CODE && resultCode == RESULT_OK) {
            String stringExtra = data.getStringExtra(CaptureActivity.EXTRA_SCAN_RESULT);//获取二维码的的扫描结果
        }
        //获取站点搜索的结果
        if (resultCode == 200 && requestCode == 100) {
            String stationName = data.getStringExtra("StationName");
            mStationId = data.getStringExtra("StationId");
            mStationType.setText(stationName);
        }
    }
}

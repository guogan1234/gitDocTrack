package com.avantport.avp.njpb.ui.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.acker.simplezxing.activity.CaptureActivity;
import com.avantport.avp.njpb.R;
import com.avantport.avp.njpb.adapter.FinalListAdapter;
import com.avantport.avp.njpb.adapter.GirdAdapter;
import com.avantport.avp.njpb.bean.Companybean;
import com.avantport.avp.njpb.bean.DeviceTypebean;
import com.avantport.avp.njpb.bean.Devicebean;
import com.avantport.avp.njpb.bean.UserInfobean;
import com.avantport.avp.njpb.constant.Constant;
import com.avantport.avp.njpb.okhttp.OkHttpBaseCallback;
import com.avantport.avp.njpb.okhttp.OkHttpUtils;
import com.avantport.avp.njpb.uitls.SpUtil;
import com.avantport.avp.njpb.uitls.ToastUtil;
import com.avantport.avp.njpb.view.CircleImageView;
import com.avantport.avp.njpb.view.CustomDialog;
import com.avantport.avp.njpb.view.CustomListview;
import com.avantport.avp.njpb.view.SelectGirdView;
import com.yongchun.library.view.ImageSelectorActivity;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Response;

/**
 * 站点自修保养
 */
public class KeepStationActivity extends BaseActivity implements View.OnClickListener, AdapterView.OnItemClickListener {


    private TextView mStationType;
    private LinearLayout mLastserving;
    private boolean isOpen;
    private GirdAdapter mGirdAdapter;
    private ImageView mHeadGoback;
    private TextView mHeadTitle;
    private ImageView mHeadQrcode;
    private TextView mCompanyType;
    private TextView mBreakType;
    private TextView mMachineType;
    private ImageView mLastservingShowmore;
    private LinearLayout mLastServingMsg;
    private CircleImageView mHeadView;
    private TextView mHeadName;
    private TextView mLastservingTime;
    private TextView mLastservingMeasure;
    private ListView mLastservingMore;
    private ImageView mAddMoreserving;
    private CustomListview mListServing;
    private SelectGirdView mGridPic;
    private EditText mInputMsg;
    private Integer mCorpId;
    private String mStationId;//站点id
    private List<DeviceTypebean.ResultsBean> mResults;
    private List<String> showDatas = new ArrayList<>();
    private Integer mType;//设备类型
    private List<Devicebean.ResultsBean> mResults1;
    private Integer mMachine;//设备
    private String mMachineEstateSn;
    private List<Companybean.ResultsBean> mCompanyResults;
    private List<DeviceTypebean.ResultsBean> mSelectParts;
    private FinalListAdapter<DeviceTypebean.ResultsBean> mResultsBeanFinalListAdapter;

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_keep_by_myself;
    }

    protected void initView() {
        //获取公司的信息

        mHeadGoback = (ImageView) findViewById(R.id.head_goback);
        mHeadTitle = (TextView) findViewById(R.id.head_title);
        mHeadQrcode = (ImageView) findViewById(R.id.head_qrcode);
        mHeadGoback.setOnClickListener(this);
        mHeadQrcode.setOnClickListener(this);
        mHeadTitle.setText("自修保养");

        mCompanyType = (TextView) findViewById(R.id.company_type);
        mStationType = (TextView) findViewById(R.id.station_type);
        mBreakType = (TextView) findViewById(R.id.break_type);
        mMachineType = (TextView) findViewById(R.id.machine_type);
        //上次回修
        mLastserving = (LinearLayout) findViewById(R.id.lastserving);
        mLastservingShowmore = (ImageView) findViewById(R.id.lastserving_showmore);
        //上次维修列表
        mLastServingMsg = (LinearLayout) findViewById(R.id.last_serving_msg);
        mHeadView = (CircleImageView) findViewById(R.id.head_view);
        mHeadName = (TextView) findViewById(R.id.head_name);
        mLastservingTime = (TextView) findViewById(R.id.lastserving_time);
        mLastservingMeasure = (TextView) findViewById(R.id.lastserving_measure);
        mLastservingMore = (ListView) findViewById(R.id.lastserving_more);
        //添加维修项目
        mAddMoreserving = (ImageView) findViewById(R.id.add_moreserving);
        mListServing = (CustomListview) findViewById(R.id.list_serving);
        //选择图片
        mGridPic = (SelectGirdView) findViewById(R.id.grid_pic);
        mInputMsg = (EditText) findViewById(R.id.input_msg);


    }

    @Override
    protected void initDatas() {
        initSetting();
        getUserCompany();
        showUserCompany(1);
    }

    private void getUserCompany() {
        //获得公司的id
        UserInfobean userInfobean = SpUtil.getObjFromSp("userInfobean");
        mCorpId = userInfobean.getPrincipal().getCorpId();
        ToastUtil.show(this, mCorpId + "");
    }

    private void showUserCompany(final int type) {
        OkHttpUtils.getInstance().get(Constant.COMPANY_INFO, null, null, new OkHttpBaseCallback<Companybean>() {

            @Override
            public void onSuccess(Response response, Companybean companybean) {
                mCompanyResults = companybean.getResults();
                if (mCompanyResults != null && mCompanyResults != null) {
                    switch (type) {
                        case 1:
                            for (int i = 0; i < mCompanyResults.size(); i++) {
                                Companybean.ResultsBean resultsBean = mCompanyResults.get(i);
                                if (resultsBean.getId() == mCorpId) {
                                    mCompanyType.setText(resultsBean.getCorpName());
                                }
                            }
                            break;

                        case 2:
                            showDatas.clear();
                            for (int i = 0; i < mCompanyResults.size(); i++) {
                                showDatas.add(mCompanyResults.get(i).getCorpName());
                            }
                            showListDialog(showDatas, mCompanyType, 3);
                            break;
                    }
                }

            }

            @Override
            public void onError(Response response, int code, Exception e) {

            }
        });
    }


    private void initSetting() {
        mGirdAdapter = new GirdAdapter(dataSelect, this);
        mGridPic.setAdapter(mGirdAdapter);
        mLastserving.setOnClickListener(this);
        mAddMoreserving.setOnClickListener(this);
        mStationType.setOnClickListener(this);
        mBreakType.setOnClickListener(this);
        mMachineType.setOnClickListener(this);
        mCompanyType.setOnClickListener(this);
        mListServing.setOnItemClickListener(this);
    }

    //// TODO: 2017/8/9
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.head_goback:
                checkDialog();
                break;
            case R.id.head_qrcode://集成二维码
                startActivityForResult(new Intent(this, CaptureActivity.class), CaptureActivity.REQ_CODE);
                break;
            case R.id.lastserving://隐藏和展开信息
                getLastServeData();//有数据展开，没有数据隐藏
                showAndHideLastMsg();
                break;
            case R.id.add_moreserving://选择零部件
                goToChangePartsActivity();

                break;
            case R.id.station_type://跳转站点选择界面
                goToSearchActivity();
                break;
            case R.id.break_type://设备类型
                showBreakType();
                break;
            case R.id.machine_type://故障设备
                showMachineType();
                break;

            case R.id.company_type://获取公司信息
                showCompanyType();
                break;
        }
    }

    private void showCompanyType() {//展示公司信息
        mStationType.setText("");
        showUserCompany(2);

    }

    private void goToChangePartsActivity() {
        Intent intent = new Intent(this, ChangePartsActivity.class);
        intent.putExtra("category", 2 + "");
        intent.putExtra("partsType", 1 + "");
        startActivityForResult(intent, 200);
    }

    private void getLastServeData() {//获得上次维修的记录
        if (mMachineType.getText().length() == 0) {//没有选择设备
            ToastUtil.show(this, "请先选择设备");
            return;
        }
        //getlastData();
        // TODO: 2017/9/7  
    }

    private void getlastData() {
        params.clear();
        params.put("estateId", mMachine);
        OkHttpUtils.getInstance().get(Constant.LAST_SERVING_INFO, params, null, new OkHttpBaseCallback() {
            @Override
            public void onSuccess(Response response, Object o) {

            }

            @Override
            public void onError(Response response, int code, Exception e) {
                if (code == 500) {
                    //没有上次维修记录
                }
            }
        });
    }


    private void showBreakType() {
        mMachineType.setText("");
        //网络获取设备类型
        params.clear();
        params.put("category", 1);
        params.put("partsType", 1);
        OkHttpUtils.getInstance().get(Constant.DEVICE_TYPE, params, null, new OkHttpBaseCallback<DeviceTypebean>() {
            @Override
            public void onSuccess(Response response, DeviceTypebean deviceTypebean) {
                //获取到数据
                mResults = deviceTypebean.getResults();
                if (mResults != null && mResults.size() != 0) {
                    //进行数据展示//先清理集合，然后添加数据
                    showDatas.clear();
                    for (int i = 0; i < mResults.size(); i++) {
                        showDatas.add(mResults.get(i).getName());
                    }
                    showListDialog(showDatas, mBreakType, 1);
                } else {
                    ToastUtil.show(KeepStationActivity.this, "数据为空");
                }
            }

            @Override
            public void onError(Response response, int code, Exception e) {
                ToastUtil.show(KeepStationActivity.this, "获取数据失败");
            }
        });
    }

    private void showMachineType() {//展示选择故障设备
        //没有选择设备类型的，不能获得设备信息
        if (mBreakType.getText().toString().trim().length() == 0) {
            ToastUtil.show(this, "请先选择设备类型");
            return;
        }
        //获取设备信息
        params.clear();
        params.put("estateTypeId", mType);
        params.put("corpId", mCorpId);
        OkHttpUtils.getInstance().get(Constant.SEARCH_DEVICE_NOLIMIT, params, null, new OkHttpBaseCallback<Devicebean>() {
            @Override
            public void onSuccess(Response response, Devicebean devicebean) {
                mResults1 = devicebean.getResults();
                if (mResults1 != null && mResults1.size() != 0) {
                    showDatas.clear();
                    for (int i = 0; i < mResults1.size(); i++) {
                        showDatas.add(mResults1.get(i).getEstateName());
                    }
                    showListDialog(showDatas, mMachineType, 2);
                } else {
                    ToastUtil.show(KeepStationActivity.this, "数据为空");
                }
            }

            @Override
            public void onError(Response response, int code, Exception e) {
                ToastUtil.show(KeepStationActivity.this, "获取数据失败");
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
                        DeviceTypebean.ResultsBean resultsBean = mResults.get(position);
                        mType = resultsBean.getId(); //设备类型
                        break;
                    case 2:
                        Devicebean.ResultsBean resultsBean1 = mResults1.get(position);
                        mMachine = resultsBean1.getId();//设备
                        ToastUtil.show(KeepStationActivity.this, mMachine + "");
                        break;
                    case 3:
                        Companybean.ResultsBean resultsBean2 = mCompanyResults.get(position);
                        mCorpId = resultsBean2.getId();//公司
                        break;

                }
                customDialog.dismiss();
            }
        });
        customDialog.show();
    }

    private void goToSearchActivity() {
        if (mCompanyType.getText().length() == 0) {
            ToastUtil.show(this,"请先选择公司");
        }else{
            Intent intent = new Intent(this, SearchActivity.class);
            intent.putExtra("CorpId", mCorpId);
            startActivityForResult(intent, 100);
        }

    }


    private void showAndHideLastMsg() {//隐藏和展示上次信息
        Animation animation;
        if (isOpen) {
            animation = AnimationUtils.loadAnimation(this, R.anim.anim_lastmsg_close);
        } else {
            animation = AnimationUtils.loadAnimation(this, R.anim.anim_lastmsg_open);
        }
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                isOpen = !isOpen;
                if (isOpen) {
                    mLastServingMsg.setVisibility(View.VISIBLE);
                } else {
                    mLastServingMsg.setVisibility(View.GONE);
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
        mLastservingShowmore.startAnimation(animation);
    }

    //返回上一个Activity的结果
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == ImageSelectorActivity.REQUEST_IMAGE) {
            ArrayList<String> images = (ArrayList<String>) data.getSerializableExtra(ImageSelectorActivity.REQUEST_OUTPUT);
            dataSelect.clear();
            dataSelect.addAll(images);
            mGirdAdapter.notifyDataSetChanged();
        }

        if (resultCode == 200 && requestCode == 100) {
            String stationName = data.getStringExtra("StationName");
            mStationId = data.getStringExtra("StationId");
            mStationType.setText(stationName);
        }

        if (resultCode == 300 && requestCode == 200) {//获得返回的结果
            mSelectParts = (List<DeviceTypebean.ResultsBean>) data.getSerializableExtra("selectParts");
            //进行展示
            showSelectParts(mSelectParts);
        }

        if (requestCode == CaptureActivity.REQ_CODE && resultCode == RESULT_OK) {
            String stringExtra = data.getStringExtra(CaptureActivity.EXTRA_SCAN_RESULT);
            ToastUtil.show(this,stringExtra);
            // TODO: 2017/9/7 二维码
        }
    }

    private void showSelectParts(List<DeviceTypebean.ResultsBean> selectParts) {
        mResultsBeanFinalListAdapter = new FinalListAdapter<>(selectParts, R.layout.item_mine_stock, new FinalListAdapter.AdapterListener<DeviceTypebean.ResultsBean>() {
            @Override
            public void bindView(FinalListAdapter.FinalViewHolder finalViewHolder, DeviceTypebean.ResultsBean resultsBean) {
                TextView partsName = (TextView) finalViewHolder.getViewById(R.id.parts_name);
                TextView partsCount = (TextView) finalViewHolder.getViewById(R.id.parts_count);
                partsName.setText(resultsBean.getName());
                partsCount.setText(resultsBean.getCount() + "");
            }
        });
        //展示部件信息
        mListServing.setAdapter(mResultsBeanFinalListAdapter);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
        //部件列表点击删除
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("确定移除此配件吗？");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mSelectParts.remove(position);
                mResultsBeanFinalListAdapter.notifyDataSetChanged();
            }
        });
        builder.setNegativeButton("取消",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                    builder.create().dismiss();
            }
        });
        builder.create().show();
    }
}

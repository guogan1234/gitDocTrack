package com.avantport.avp.njpb.ui.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.AudioManager;
import android.media.SoundPool;
import android.reader.ReaderAndroid;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.avantport.avp.njpb.R;
import com.avantport.avp.njpb.adapter.FinalListAdapter;
import com.avantport.avp.njpb.adapter.GirdAdapter;
import com.avantport.avp.njpb.bean.BikeReadCardbean;
import com.avantport.avp.njpb.bean.BikeSupplierbean;
import com.avantport.avp.njpb.bean.CompanyInfobean;
import com.avantport.avp.njpb.bean.DeviceTypebean;
import com.avantport.avp.njpb.constant.Constant;
import com.avantport.avp.njpb.okhttp.OkHttpBaseCallback;
import com.avantport.avp.njpb.okhttp.OkHttpUtils;
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
 * 车辆自修保养
 * */
public class KeepBikeActivity extends BaseActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

    private TextView mHeadTitle;
    private ImageView mHeadGoback;
    private ImageView mHeadQrcode;
    private TextView mBikeCompany;
    private TextView mBikeNumber;
    private TextView mBikeReadcard;
    private TextView mBikeStationCompany;
    private TextView mBikeStation;
    private LinearLayout mLastserving;
    private ImageView mLastservingShowmore;
    private LinearLayout mLastServingMsg;
    private CircleImageView mHeadView;
    private TextView mHeadName;
    private TextView mLastservingTime;
    private TextView mLastservingMeasure;
    private ListView mLastservingMore;
    private ImageView mAddMoreserving;
    private CustomListview mListServing;
    public List<String> dataSelect = new ArrayList<>() ;
    private GirdAdapter mGirdAdapter;
    private SelectGirdView mGridPic;
    private EditText mInputMsg;
    private List<BikeSupplierbean.ResultsBean> mCompanyResults;
    private List<String> showDatas = new ArrayList<>();
    private Integer mCompanyId;//自行车生产商id
    private SoundPool mSoundPool;
    private NJPBApplication mApplication;
    private ReaderAndroid mSerialPort;
    private int loadId;
    private int handle;
    protected int address = 0x00;
    private byte[] ver = new byte[20];
    protected byte mode = 0x00, halt = 0x00;
    protected byte[] card_flag = new byte[2];
    private byte[] retlen = new byte[1];
    private Integer mBikeCodeId;//自行车车架号id
    private List<CompanyInfobean.ResultsBean> mPeopleCompanyResults;
    private Integer mPeopleCompanyId;//站点所属公司的id
    private String mStationId;//站点的id
    private boolean isOpen;
    private List<DeviceTypebean.ResultsBean> mSelectParts;
    private FinalListAdapter<DeviceTypebean.ResultsBean> mResultsBeanFinalListAdapter;

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_keep_bike;
    }

    @Override
    protected void initView() {
        mHeadTitle = (TextView) findViewById(R.id.head_title);
        mHeadGoback = (ImageView) findViewById(R.id.head_goback);
        mHeadQrcode = (ImageView) findViewById(R.id.head_qrcode);
        mHeadTitle.setText("自修保养");
        mHeadGoback.setOnClickListener(this);
        mHeadQrcode.setVisibility(View.INVISIBLE);

        mBikeCompany = (TextView) findViewById(R.id.bike_company);
        mBikeNumber = (TextView) findViewById(R.id.bike_number);
        mBikeReadcard = (TextView) findViewById(R.id.bike_readcard);
        mBikeStationCompany = (TextView) findViewById(R.id.bike_station_company);
        mBikeStation = (TextView) findViewById(R.id.bike_station);

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
        mInputMsg = (EditText) findViewById(R.id.break_describe);


        mBikeReadcard.setOnClickListener(this);
        mBikeCompany.setOnClickListener(this);
        mBikeStationCompany.setOnClickListener(this);
        mBikeStation.setOnClickListener(this);
        mLastserving.setOnClickListener(this);
        mAddMoreserving.setOnClickListener(this);
        mListServing.setOnItemClickListener(this);
    }

    @Override
    protected void initDatas() {
        initSetting();
        setReadInfo();//初始化读卡
    }
    private void setReadInfo() {
        mSoundPool = new SoundPool(3, AudioManager.STREAM_MUSIC, 100);
        loadId = mSoundPool.load(KeepBikeActivity.this, R.raw.card_mp3, 1);
        mApplication = (NJPBApplication) getApplication();
        try {
            mSerialPort = mApplication.getSerialPort();
            Log.i("ANDROID", "getSerialPort end");
            handle = mSerialPort.getHandle();
            Log.i("ANDROID", "mSerialPort.getHandle()" + handle);
        } catch (Exception e) {
        }
    }
    private void initSetting() {
        mGirdAdapter = new GirdAdapter(dataSelect, this);
        mGridPic.setAdapter(mGirdAdapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.head_goback:
                checkDialog();
                break;
            case R.id.bike_company:
                getBikeCompany();
                break;
            case R.id.bike_readcard://读卡
                readCardDatas();
                break;
            case R.id.bike_station_company://站点公司
                mBikeStation.setText("");
                getStationCompany();
                break;
            case R.id.bike_station://获取站点
                getStation();
                break;
            case R.id.lastserving://隐藏和展开信息
               // getLastServeData();//有数据展开，没有数据隐藏
                showAndHideLastMsg();
                break;
            case R.id.add_moreserving://选择零部件
                goToChangePartsActivity();

                break;
        }
    }
    private void goToChangePartsActivity() {
        Intent intent = new Intent(this, ChangePartsActivity.class);
        intent.putExtra("category", 2 + "");
        intent.putExtra("partsType", 2 + "");
        startActivityForResult(intent, 200);
    }

   /* private void getLastServeData() {//获得上次维修的记录
        if (mMachineType.getText().length() == 0) {//没有选择设备
            ToastUtil.show(this, "请先选择设备");
            return;
        }
        //getlastData();
        // TODO: 2017/9/7
    }*/

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

    private void getStationCompany() {//获取站点公司
        OkHttpUtils.getInstance().get(Constant.COMPANY_INFO, null, null, new OkHttpBaseCallback<CompanyInfobean>() {
            @Override
            public void onSuccess(Response response, CompanyInfobean companyInfobean) {
                mPeopleCompanyResults = companyInfobean.getResults();
                if (mPeopleCompanyResults != null && mPeopleCompanyResults.size() != 0) {//展示公司信息
                    showDatas.clear();
                    for (int i = 0; i < mPeopleCompanyResults.size(); i++) {
                        showDatas.add(mPeopleCompanyResults.get(i).getCorpName());
                    }
                    showListDialog(showDatas, mBikeStationCompany, 2);
                } else {
                    ToastUtil.show(KeepBikeActivity.this, "数据为空");
                }
            }

            @Override
            public void onError(Response response, int code, Exception e) {
                ToastUtil.show(KeepBikeActivity.this, "获取数据失败");
            }
        });

    }

    private void getStation() {//获得站点
        //校验公司
        if (mBikeStationCompany.getText().length() == 0) {
            ToastUtil.show(this, "请先选择公司");
            return;
        }
        Intent intent = new Intent(this, SearchActivity.class);
       intent.putExtra("CorpId", mPeopleCompanyId);
        startActivityForResult(intent, 100);

    }
    private void readCardDatas() {//读卡获取信息
        if (0 == mSerialPort.MFGetVersion(handle, address, ver, retlen)) {
            ToastUtil.show(this, "连接成功");//连接成功，还要读卡
            //连接成功，还要读卡
            mode = (byte) (1 & 0xff);
            halt = (byte) (1 & 0xff);
            int ret = mSerialPort.getCardSN(handle, address, mode, halt, card_flag, ver);
            if (0 == ret) {
                //读卡成功
                ToastUtil.show(KeepBikeActivity.this, "read Card SN:" + ByteToStr(card_flag[1], ver));
                mSoundPool.play(loadId, 15, 15, 1, 0, 1);
                String bikeSn = ByteToStr(card_flag[1], ver);
                getBikeCode(bikeSn);
                // showRemindDialog();
            } else if (ret == 4) {
                ToastUtil.show(KeepBikeActivity.this, "读卡失败");
                //弹出对话框 //读卡失败，进行站点报修
                showRemindDialog();
            }
        } else {
            ToastUtil.show(this, "连接失败,请重试");
            mSoundPool.play(loadId, 15, 15, 1, 0, 1);
        }
    }

    private void getBikeCode(final String bikeSn) {//网络获取车架号TODO: 2017/9/6
        params.clear();
        params.put("cardId", "BE DE SE A3");// TODO: 2017/9/6
        OkHttpUtils.getInstance().get(Constant.FIND_BIKE_CARD, params, null, new OkHttpBaseCallback<BikeReadCardbean>() {
            @Override
            public void onSuccess(Response response, BikeReadCardbean bikeReadCardbean) {
                BikeReadCardbean.ResultBean result = bikeReadCardbean.getResult();
                if (result != null) {
                    mBikeNumber.setText(result.getEstateSn());
                    mBikeCodeId = result.getId();//自行车id
                }
            }

            @Override
            public void onError(Response response, int code, Exception e) {
                //获取车架号失败
                ToastUtil.show(KeepBikeActivity.this, "获取失败" + code);
            }
        });

    }

    private void showRemindDialog() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setMessage("读卡失败,请进行站点报修");
        dialog.show();
    }
    //读卡的类
    public String ByteToStr(int byteSize, byte[] in) {
        String ret = new String("");
        if (in.length < byteSize)
            return ret;
        for (int i = 0; i < byteSize; i++) {
            ret = ret.concat(String.format("%1$02X ", in[i]));
        }
        return ret;
    }

    private void getBikeCompany() {//获取自行车公司

        OkHttpUtils.getInstance().get(Constant.BIKE_SUPPLIER, null, null, new OkHttpBaseCallback<BikeSupplierbean>() {

            @Override
            public void onSuccess(Response response, BikeSupplierbean bikeSupplierbean) {
                mCompanyResults = bikeSupplierbean.getResults();
                if (mCompanyResults != null && mCompanyResults.size() != 0) {
                    showDatas.clear();
                    for (int i = 0; i < mCompanyResults.size(); i++) {
                        showDatas.add(mCompanyResults.get(i).getSupplierName());
                    }
                    showListDialog(showDatas, mBikeCompany, 1);

                } else {
                    ToastUtil.show(KeepBikeActivity.this, "数据为空");
                }
            }

            @Override
            public void onError(Response response, int code, Exception e) {
                ToastUtil.show(KeepBikeActivity.this, "获取数据失败");
            }
        });
    }

    private void showListDialog(List<String> showDatas, final TextView mtext, final int type) {//条目展示
        final CustomDialog customDialog = new CustomDialog(KeepBikeActivity.this, showDatas);
        customDialog.setOnDialogItemClick(new CustomDialog.onDialogItemClick() {
            @Override
            public void getDialogItem(String areaName, int position) {
                mtext.setText(areaName);
                switch (type) {
                    case 1:
                        BikeSupplierbean.ResultsBean resultsBean = mCompanyResults.get(position);
                        mCompanyId = resultsBean.getId();//车辆生产商id
                        break;
                    case 2:
                        CompanyInfobean.ResultsBean resultsBean1 = mPeopleCompanyResults.get(position);
                        mPeopleCompanyId = resultsBean1.getId();//站点所属公司
                        break;
                }
                customDialog.dismiss();//隐藏dialog
            }
        });

        customDialog.show();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //图片选择
        if (resultCode == RESULT_OK && requestCode == ImageSelectorActivity.REQUEST_IMAGE) {
            ArrayList<String> images = (ArrayList<String>) data.getSerializableExtra(ImageSelectorActivity.REQUEST_OUTPUT);
            dataSelect.clear();
            dataSelect.addAll(images);
            mGirdAdapter.notifyDataSetChanged();
        }
        if (resultCode == 300 && requestCode == 200) {//获得返回的结果
            mSelectParts = (List<DeviceTypebean.ResultsBean>) data.getSerializableExtra("selectParts");
            //进行展示
            showSelectParts(mSelectParts);
        }

        if (resultCode == 200 && requestCode == 100) {
            String stationName = data.getStringExtra("StationName");
            mStationId = data.getStringExtra("StationId");
            mBikeStation.setText(stationName);
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

    //配件点击删除
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

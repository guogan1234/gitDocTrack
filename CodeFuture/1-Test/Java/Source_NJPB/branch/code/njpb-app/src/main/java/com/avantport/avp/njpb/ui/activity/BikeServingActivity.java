package com.avantport.avp.njpb.ui.activity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.media.AudioManager;
import android.media.SoundPool;
import android.reader.ReaderAndroid;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.avantport.avp.njpb.R;
import com.avantport.avp.njpb.adapter.GirdAdapter;
import com.avantport.avp.njpb.adapter.GirgCheckAdapter;
import com.avantport.avp.njpb.bean.BikeReadCardbean;
import com.avantport.avp.njpb.bean.BikeSupplierbean;
import com.avantport.avp.njpb.bean.CompanyInfobean;
import com.avantport.avp.njpb.bean.Partsbean;
import com.avantport.avp.njpb.bean.Rolebean;
import com.avantport.avp.njpb.constant.Constant;
import com.avantport.avp.njpb.okhttp.OkHttpBaseCallback;
import com.avantport.avp.njpb.okhttp.OkHttpUtils;
import com.avantport.avp.njpb.uitls.ToastUtil;
import com.avantport.avp.njpb.view.CustomDialog;
import com.avantport.avp.njpb.view.SelectGirdView;
import com.yongchun.library.view.ImageSelectorActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Response;

/**
 * 车辆报修
 */
@SuppressLint({"NewApi", "NewApi", "NewApi"})
public class BikeServingActivity extends BaseActivity implements View.OnClickListener, AdapterView.OnItemClickListener {


    private GirdAdapter mGirdAdapter;
    private SelectGirdView mBikeParts;
    private TextView mBikeCompany;
    private TextView mBikeNumber;
    private TextView mBikeReadcard;
    private TextView mBikeStationCompany;
    private TextView mBikeStation;
    private EditText mBreakDescribe;
    private SelectGirdView mGridPic;
    private TextView mHeadTitle;
    private ImageView mHeadGoback;
    private ImageView mHeadQrcode;
    private TextView mSendPeople;
    private GirgCheckAdapter mGirgCheckAdapter;
    private Map<String, Object> params = new HashMap<>();
    private List<String> showDatas = new ArrayList<>();
    private List<BikeSupplierbean.ResultsBean> mCompanyResults;
    private List<CompanyInfobean.ResultsBean> mPeopleCompanyResults;
    private List<Rolebean.ResultsBean> mResults2;
    private List<String> checkString = new ArrayList<>();
    private List<Partsbean.ResultsBean> mCheckboxResults;
    private Integer mPeopleCompanyId;
    private Integer mCompanyId;//公司生产商id
    private String mStationId;//站点id
    private Integer mBikeCodeId;//自行车车架号id
    private Button mFinish;
    private NJPBApplication mApplication;
    private ReaderAndroid mSerialPort;
    private int handle;
    protected int address = 0x00;
    private byte[] ver = new byte[20];
    protected byte mode = 0x00, halt = 0x00;
    protected byte[] card_flag = new byte[2];
    private byte[] retlen = new byte[1];
    private SoundPool mSoundPool;
    private int loadId;

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_bike_serving;
    }

    @Override
    protected void initView() {
        mHeadTitle = (TextView) findViewById(R.id.head_title);
        mHeadGoback = (ImageView) findViewById(R.id.head_goback);
        mHeadQrcode = (ImageView) findViewById(R.id.head_qrcode);
        mHeadTitle.setText("车辆报修");
        mHeadGoback.setOnClickListener(this);
        mHeadQrcode.setVisibility(View.INVISIBLE);

        mBikeCompany = (TextView) findViewById(R.id.bike_company);
        mBikeNumber = (TextView) findViewById(R.id.bike_number);
        mBikeReadcard = (TextView) findViewById(R.id.bike_readcard);
        mSendPeople = (TextView) findViewById(R.id.send_people);
        mBikeStationCompany = (TextView) findViewById(R.id.bike_station_company);
        mBikeStation = (TextView) findViewById(R.id.bike_station);
        mBreakDescribe = (EditText) findViewById(R.id.break_describe);
        mGridPic = (SelectGirdView) findViewById(R.id.grid_pic);
        mBikeParts = (SelectGirdView) findViewById(R.id.bike_parts);
        mFinish = (Button) findViewById(R.id.finish);

        mBikeReadcard.setOnClickListener(this);
        mBikeCompany.setOnClickListener(this);
        mBikeStationCompany.setOnClickListener(this);
        mBikeStation.setOnClickListener(this);
        mSendPeople.setOnClickListener(this);
        mFinish.setOnClickListener(this);
        mBikeParts.setOnItemClickListener(this);

    }

    @Override
    protected void initDatas() {
        mGirdAdapter = new GirdAdapter(dataSelect, this);
        mGridPic.setAdapter(mGirdAdapter);
        //获取车辆部件的数据
        getAllPartsType();
        setReadInfo();//初始化读卡
    }

    private void setReadInfo() {
        mSoundPool = new SoundPool(3, AudioManager.STREAM_MUSIC, 100);
        loadId = mSoundPool.load(BikeServingActivity.this, R.raw.card_mp3, 1);
        mApplication = (NJPBApplication) getApplication();
        try {
            mSerialPort = mApplication.getSerialPort();
            Log.i("ANDROID", "getSerialPort end");
            handle = mSerialPort.getHandle();
            Log.i("ANDROID", "mSerialPort.getHandle()" + handle);
        } catch (Exception e) {
        }
    }

    private void uploadDatas() {
        if (mGirgCheckAdapter != null) {
            checkString.clear();
            for (int i = 0; i < mGirgCheckAdapter.getDatas().size(); i++) {
                if (mGirgCheckAdapter.getDatas().get(i).isSelected()) {
                    checkString.add(mGirgCheckAdapter.getDatas().get(i).getName());
                }
            }
        }

        ToastUtil.show(this, checkString.toString());
    }

    private void getAllPartsType() {//获取车辆部件的数据
        params.clear();
        params.put("partsType", "2");
        OkHttpUtils.getInstance().get(Constant.COMPONENTS_TYPE, params, null, new OkHttpBaseCallback<Partsbean>() {
            @Override
            public void onSuccess(Response response, Partsbean partsbean) {
                mCheckboxResults = partsbean.getResults();
                //获得部件的数量
                mGirgCheckAdapter = new GirgCheckAdapter(mCheckboxResults, BikeServingActivity.this);
                mBikeParts.setAdapter(mGirgCheckAdapter);
            }

            @Override
            public void onError(Response response, int code, Exception e) {

            }
        });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.head_goback:
                checkDialog();
                break;
            case R.id.bike_company://获取自行车生产公司
                getBikeCompany();
                break;
            case R.id.bike_station_company://站点公司

                mBikeStation.setText("");
                getStationCompany();
                break;
            case R.id.bike_station://获取站点
                getStation();
                break;
            case R.id.send_people:
                getSendPeople();
                break;
            case R.id.finish:
                uploadDatas();//上传数据
                break;
            case R.id.bike_readcard://读卡
                readCardDatas();
                break;

        }
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
                ToastUtil.show(BikeServingActivity.this, "read Card SN:" + ByteToStr(card_flag[1], ver));
                mSoundPool.play(loadId, 15, 15, 1, 0, 1);
                String bikeSn = ByteToStr(card_flag[1], ver);
                getBikeCode(bikeSn);
                // showRemindDialog();
            } else if (ret == 4) {
                ToastUtil.show(BikeServingActivity.this, "读卡失败");
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
                ToastUtil.show(BikeServingActivity.this, "获取失败" + code);
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

    private void getSendPeople() {//获得派单人员  //获取派单人员的信息

        params.clear();
        params.put("roleId", "3");
        OkHttpUtils.getInstance().get(Constant.FIND_USER_ROLE, params, null, new OkHttpBaseCallback<Rolebean>() {

            @Override
            public void onSuccess(Response response, Rolebean rolebean) {
                mResults2 = rolebean.getResults();
                if (mResults2 != null && mResults2.size() != 0) {
                    showDatas.clear();
                    for (int i = 0; i < mResults2.size(); i++) {
                        Rolebean.ResultsBean resultsBean = mResults2.get(i);
                        showDatas.add(resultsBean.getUserName());
                    }
                    showListDialog(showDatas, mSendPeople, 3);
                } else {
                    ToastUtil.show(BikeServingActivity.this, "数据为空");
                }
            }

            @Override
            public void onError(Response response, int code, Exception e) {
                ToastUtil.show(BikeServingActivity.this, "获取数据失败");
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
                    ToastUtil.show(BikeServingActivity.this, "数据为空");
                }
            }

            @Override
            public void onError(Response response, int code, Exception e) {
                ToastUtil.show(BikeServingActivity.this, "获取数据失败");
            }
        });

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
                    ToastUtil.show(BikeServingActivity.this, "数据为空");
                }
            }

            @Override
            public void onError(Response response, int code, Exception e) {
                ToastUtil.show(BikeServingActivity.this, "获取数据失败");
            }
        });
    }

    private void showListDialog(List<String> showDatas, final TextView mtext, final int type) {//条目展示
        final CustomDialog customDialog = new CustomDialog(BikeServingActivity.this, showDatas);
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
    public boolean onKeyDown(int keyCode, KeyEvent event) {//点击大黄键，进行读卡
        if (keyCode == KeyEvent.KEYCODE_DPAD_CENTER) {
            //Toast.makeText(this, "你点了黄色按键，KEYCODE_DPAD_CENTER 123", Toast.LENGTH_SHORT).show();
            readCardDatas();
        }
        return super.onKeyDown(keyCode, event);
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


        if (resultCode == 200 && requestCode == 100) {
            String stationName = data.getStringExtra("StationName");
            mStationId = data.getStringExtra("StationId");
            mBikeStation.setText(stationName);
        }
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //checkbox的点击事件
        Partsbean.ResultsBean resultsBean = mCheckboxResults.get(position);
        if (resultsBean.isSelected()) {
            resultsBean.setSelected(false);
        } else {
            resultsBean.setSelected(true);
        }
        mGirgCheckAdapter.notifyDataSetChanged();

    }

    @Override
    protected void onDestroy() {
        mApplication.closeSerialPort();
        mSerialPort = null;
        super.onDestroy();
    }
}

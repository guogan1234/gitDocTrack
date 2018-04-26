package com.avantport.avp.njpb.ui.activity;

import android.content.Intent;
import android.media.AudioManager;
import android.media.SoundPool;
import android.reader.ReaderAndroid;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.acker.simplezxing.activity.CaptureActivity;
import com.avantport.avp.njpb.R;
import com.avantport.avp.njpb.bean.BikeReadCardbean;
import com.avantport.avp.njpb.bean.Companybean;
import com.avantport.avp.njpb.bean.PutRequestbean;
import com.avantport.avp.njpb.bean.UserInfobean;
import com.avantport.avp.njpb.constant.Constant;
import com.avantport.avp.njpb.okhttp.OkHttpBaseCallback;
import com.avantport.avp.njpb.okhttp.OkHttpUtils;
import com.avantport.avp.njpb.uitls.SpUtil;
import com.avantport.avp.njpb.uitls.ToastUtil;
import com.avantport.avp.njpb.view.CustomListview;

import java.util.List;

import okhttp3.Response;

/**
 * 车辆盘点
 */
public class BikeCheckActivity extends BaseActivity implements View.OnClickListener {


    private ImageView mHeadGoback;
    private TextView mHeadTitle;
    private ImageView mHeadRead;
    private TextView mCompanyType;
    private TextView mBikeCount;
    private CustomListview mListRead;
    private ImageView mCheckQrcode;
    private TextView mCompanyStation;
    private Button mCommit;
    private Integer mCorpId;//用户的公司id
    private List<Companybean.ResultsBean> mCompanyResults;
    private String mStationId;//站点的id
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
        return R.layout.activity_bike_check;
    }

    @Override
    protected void initView() {
        mHeadGoback = (ImageView) findViewById(R.id.head_goback);
        mHeadTitle = (TextView) findViewById(R.id.head_title);
        mHeadRead = (ImageView) findViewById(R.id.head_qrcode);
        mHeadTitle.setText(R.string.bike_check);
        mHeadRead.setImageResource(R.mipmap.read_carnumber);
        mCompanyType = (TextView) findViewById(R.id.company_type);
        mBikeCount = (TextView) findViewById(R.id.bike_count);
        mListRead = (CustomListview) findViewById(R.id.list_read);
        mCheckQrcode = (ImageView) findViewById(R.id.check_qrcode);
        mCompanyStation = (TextView) findViewById(R.id.company_station);
        mCommit = (Button) findViewById(R.id.commit);
        mHeadRead.setOnClickListener(this);
        mHeadGoback.setOnClickListener(this);
        mCheckQrcode.setOnClickListener(this);
        mCompanyStation.setOnClickListener(this);
    }

    @Override
    protected void initDatas() {
        getCheckTime();
        getUserId();
        showUserCompany();
        setReadInfo();//初始化读卡
    }

    private void getCheckTime() {//获得是否是盘点时间
        OkHttpUtils.getInstance().get(Constant.CHECK_TIME, null, null, new OkHttpBaseCallback<PutRequestbean>() {
            @Override
            public void onSuccess(Response response, PutRequestbean putRequestbean) {
                // TODO: 2017/9/10 取是否是可以盘点的时间
                /**
                 * 1.是盘点时间，进行加载数据
                 * 2，不是盘点时间，弹出对话框提示
                 * 3.网络状态不好进行提示
                 *
                 * */
            }

            @Override
            public void onError(Response response, int code, Exception e) {

            }
        });

    }

    private void setReadInfo() {//初始化读卡信息
        mSoundPool = new SoundPool(3, AudioManager.STREAM_MUSIC, 100);
        loadId = mSoundPool.load(BikeCheckActivity.this, R.raw.card_mp3, 1);
        mApplication = (NJPBApplication) getApplication();
        try {
            mSerialPort = mApplication.getSerialPort();
            Log.i("ANDROID", "getSerialPort end");
            handle = mSerialPort.getHandle();
            Log.i("ANDROID", "mSerialPort.getHandle()" + handle);
        } catch (Exception e) {
        }
    }

    private void getUserId() {
        //获得公司的id
        UserInfobean userInfobean = SpUtil.getObjFromSp("userInfobean");
        mCorpId = userInfobean.getPrincipal().getCorpId();
        ToastUtil.show(this, mCorpId + "");
    }

    private void showUserCompany() {
        OkHttpUtils.getInstance().get(Constant.COMPANY_INFO, null, null, new OkHttpBaseCallback<Companybean>() {
            @Override
            public void onSuccess(Response response, Companybean companybean) {
                mCompanyResults = companybean.getResults();
                if (mCompanyResults != null && mCompanyResults != null) {
                    for (int i = 0; i < mCompanyResults.size(); i++) {
                        Companybean.ResultsBean resultsBean = mCompanyResults.get(i);
                        if (resultsBean.getId() == mCorpId) {
                            mCompanyType.setText(resultsBean.getCorpName());
                        }
                    }
                }
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
            case R.id.head_qrcode:
                //芯片感应，读取数据
                readCardDatas();
                break;
            case R.id.check_qrcode:
                //扫描二维码
                startActivityForResult(new Intent(this, CaptureActivity.class), CaptureActivity.REQ_CODE);
                break;
            case R.id.company_station://站点
                getStation();
                break;
            case R.id.commit:
                //进行数据的提交
                // TODO: 2017/9/10
                break;
        }
    }

    private void readCardDatas() {//读卡读出信息
        if (0 == mSerialPort.MFGetVersion(handle, address, ver, retlen)) {
            ToastUtil.show(this, "连接成功");//连接成功，还要读卡
            //连接成功，还要读卡
            mode = (byte) (1 & 0xff);
            halt = (byte) (1 & 0xff);
            int ret = mSerialPort.getCardSN(handle, address, mode, halt, card_flag, ver);
            if (0 == ret) {
            //读卡成功
                ToastUtil.show(BikeCheckActivity.this, "read Card SN:" + ByteToStr(card_flag[1], ver));
                mSoundPool.play(loadId, 15, 15, 1, 0, 1);
                String bikeSn = ByteToStr(card_flag[1], ver);
                getBikeCode(bikeSn);
                // showRemindDialog();
            } else if (ret == 4) {
                ToastUtil.show(BikeCheckActivity.this, "读卡失败");
                //弹出对话框 //读卡失败，进行站点报修
                //showRemindDialog();
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
               /* if (result != null) {
                    mBikeNumber.setText(result.getEstateSn());
                    mBikeCodeId = result.getId();//自行车id
               }*/
                // TODO: 2017/9/10 //读卡读出信息后添加到列表进行展示，去除重复的

            }

            @Override
            public void onError(Response response, int code, Exception e) {
                //获取车架号失败
                ToastUtil.show(BikeCheckActivity.this, "获取信息失败" + code);
            }
        });

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

    private void getStation() {
        Intent intent = new Intent(this, SearchActivity.class);
        intent.putExtra("CorpId", mCorpId);
        startActivityForResult(intent, 100);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //扫描二维码
        if (requestCode == CaptureActivity.REQ_CODE&&resultCode == RESULT_OK) {
            String stringExtra = data.getStringExtra(CaptureActivity.EXTRA_SCAN_RESULT);//获取二维码的的扫描结果
        }

        // TODO: 2017/9/10 二维码
        //获取站点搜索的结果
        if (resultCode == 200 && requestCode == 100) {
            String stationName = data.getStringExtra("StationName");
            mStationId = data.getStringExtra("StationId");
            mCompanyStation.setText(stationName);
        }
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
    protected void onDestroy() {
        mApplication.closeSerialPort();
        mSerialPort = null;
        super.onDestroy();
    }
}

package com.avantport.avp.njpb.ui.activity;

import android.content.Intent;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.acker.simplezxing.activity.CaptureActivity;
import com.avantport.avp.njpb.R;
import com.avantport.avp.njpb.adapter.GirdAdapter;
import com.avantport.avp.njpb.bean.Companybean;
import com.avantport.avp.njpb.bean.DeviceTypebean;
import com.avantport.avp.njpb.bean.Devicebean;
import com.avantport.avp.njpb.bean.Rolebean;
import com.avantport.avp.njpb.bean.UploadImage;
import com.avantport.avp.njpb.bean.UserInfobean;
import com.avantport.avp.njpb.constant.Constant;
import com.avantport.avp.njpb.okhttp.OkHttpBaseCallback;
import com.avantport.avp.njpb.okhttp.OkHttpUtils;
import com.avantport.avp.njpb.uitls.SpUtil;
import com.avantport.avp.njpb.uitls.ToastUtil;
import com.avantport.avp.njpb.view.CustomDialog;
import com.avantport.avp.njpb.view.SelectGirdView;
import com.yongchun.library.view.ImageSelectorActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Response;

public class StationServingActivity extends BaseActivity implements View.OnClickListener {

    private Button mFinish;
    private GirdAdapter mGirdAdapter;
    private TextView sendPeople;
    private TextView mHeadTitle;
    private ImageView mHeadGoback;
    private ImageView mHeadQrcode;
    private TextView mCompanyType;
    private TextView mStationType;
    private TextView mBreakType;
    private TextView mMachineType;
    private EditText mBreakDescribe;
    private SelectGirdView mGridPic;
    private Integer mCorpId;
    private Map<String, Object> params = new HashMap<>();
    private List<String> showDatas = new ArrayList<>();
    private List<DeviceTypebean.ResultsBean> mResults;
    private Integer mId;//设备类型id
    private String mStationId;//站点id
    private List<Devicebean.ResultsBean> mResults1;
    private Integer mId1;//设备id
    private List<Rolebean.ResultsBean> mResults2;
    private Integer mId2;//派单人id


    private  List<String> imgDatas = new ArrayList<>();
    private ImageView mViewById;


    @Override
    protected int getLayoutRes() {
        return R.layout.activity_station_serving;
    }

    @Override
    protected void initView() {
        mHeadTitle = (TextView) findViewById(R.id.head_title);
        mHeadGoback = (ImageView) findViewById(R.id.head_goback);
        mHeadQrcode = (ImageView) findViewById(R.id.head_qrcode);
        mCompanyType = (TextView) findViewById(R.id.company_type);

        mStationType = (TextView) findViewById(R.id.station_type);
        mBreakType = (TextView) findViewById(R.id.break_type);
        mMachineType = (TextView) findViewById(R.id.machine_type);
        sendPeople = (TextView) findViewById(R.id.send_people);
        mBreakDescribe = (EditText) findViewById(R.id.break_describe);
        mGridPic = (SelectGirdView) findViewById(R.id.grid_pic);//图片选择，展示图片
        mFinish = (Button) findViewById(R.id.finish);

        mViewById = (ImageView) findViewById(R.id.pic);

    }

    @Override
    protected void initDatas() {
        //获得用户的信息
        //将用户的公司信息进行回填
        getUserCompany();
        showUserCompany();//获得用户的公司信息
        initWidget();
    }

    private void showUserCompany() {
        OkHttpUtils.getInstance().get(Constant.COMPANY_INFO, null, null, new OkHttpBaseCallback<Companybean>() {

            @Override
            public void onSuccess(Response response, Companybean companybean) {
                List<Companybean.ResultsBean> results = companybean.getResults();
                for (int i = 0; i < results.size(); i++) {
                    Companybean.ResultsBean resultsBean = results.get(i);
                    if (resultsBean.getId() == mCorpId) {
                        mCompanyType.setText(resultsBean.getCorpName());
                    }
                }
            }

            @Override
            public void onError(Response response, int code, Exception e) {

            }
        });
    }

    private void initWidget() {
        mHeadTitle.setText("站点报修");
        mHeadGoback.setOnClickListener(this);
        mHeadQrcode.setOnClickListener(this);
        sendPeople.setOnClickListener(this);
        mBreakType.setOnClickListener(this);
        mMachineType.setOnClickListener(this);

        //设置图片选择
        mGirdAdapter = new GirdAdapter(dataSelect, this);
        mGridPic.setAdapter(mGirdAdapter);
        mFinish.setOnClickListener(this);//提交
        mStationType.setOnClickListener(this);
    }

    private void getUserCompany() {
        //获得公司的id
        UserInfobean userInfobean = SpUtil.getObjFromSp("userInfobean");
        mCorpId = userInfobean.getPrincipal().getCorpId();
        ToastUtil.show(this, mCorpId + "");
    }

    @Override
    public void onClick(View v) {//处理点击事件
        switch (v.getId()) {
            case R.id.head_goback:
                //返回，并添加弹出框进行提示
                checkDialog();
                break;
            case R.id.head_qrcode://二维码
                //集成的二维码扫描，简单的
                startActivityForResult(new Intent(this, CaptureActivity.class), CaptureActivity.REQ_CODE);
                break;
            case R.id.finish://进行上传数据
                uploadDatas();
                break;
            case R.id.break_type://故障类型,选择类型的时候要将设备置空
                showBreakType();
                break;
            case R.id.machine_type://故障设备
                showMachineType();
                break;
            case R.id.send_people://派单人员
                showSendPeople();
                break;
            case R.id.station_type:
                goToSearchActivity();
                break;

        }

    }

    private void uploadDatas() {//将数据进行上传
        //校验要上传的数据
        if (mStationType.getText().length() == 0) {//站点
            ToastUtil.show(this, "请填写站点信息");
            return;
        }
        if (sendPeople.getText().length() == 0) {
            ToastUtil.show(this, "请填写派单人员");
            return;
        }
        //没有故障类型和故障设备，填写故障描述
        if (mBreakType.getText().length() == 0 || mMachineType.getText().length() == 0) {
            if (mBreakDescribe.getText().length() == 0) {
                ToastUtil.show(this, "请填写故障描述信息");
                return;
            }
        }

        //校验完成
        getUploadDatas();

    }

    private void getUploadDatas() {
        JSONObject object = new JSONObject();
        try {
            object.put("stationId", mStationId);
            object.put("typeId", "1");
            object.put("reportWay", "1");
            object.put("assignEmployee", mId2);
            object.put("estateId", mId1);
            object.put("faultDescription",mBreakDescribe.getText().toString());
            showProDialog("上传中...");
           /* uploadOnlyDatas(object.toString());//只上传数据*/



        } catch (JSONException e) {
            e.printStackTrace();
            ToastUtil.show(StationServingActivity.this,"操作失败，请重试");
            return;
        }


    }

   // private void textDatas() {
       /* String s = dataSelect.get(0);
        try {
            String s1 = encodeBase64File(s);
            String replace = s1.replace("+", "%2B");
            params.clear();
            params.put("uploadFiles",replace);

            OkHttpUtils.getInstance().post("http://192.168.1.116:4071/uploadFiles",params,null,new OkHttpBaseCallback<CreateOeder>() {

                @Override
                public void onSuccess(Response response, CreateOeder createOeder) {
                    hideDialiog();
                    ToastUtil.show(StationServingActivity.this,"成功");
                    //上传成功清空数据
                    clearnOrderDatas();
                }

                @Override
                public void onError(Response response, int code, Exception e) {
                    hideDialiog();
                    ToastUtil.show(StationServingActivity.this,""+code);
                    try {
                        System.out.println(response.body().string());
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
            } );
        } catch (Exception e) {
            e.printStackTrace();
        }

    }*/

    //图片文件
 /*   public  String encodeBase64File(String path) throws Exception {
        File file = new File(path);
        FileInputStream inputFile = new FileInputStream(file);
        byte[] buffer = new byte[(int)file.length()];
        inputFile.read(buffer);
        inputFile.close();
        return Base64.encodeToString(buffer,Base64.DEFAULT);
    }*/

   /* private void uploadOnlyDatas(String content)  {//上传图片
       *//* String url = Constant.ORDER_REPAIRS;//拼接url

        if (dataSelect.size()!= 0) {
            ToastUtil.show(StationServingActivity.this,"多部分请求");
            String s = dataSelect.get(0);
            String s1 = null;
            try {
                s1 = encodeBase64File(s);
                String replace = s1.replace("+", "%2B");
                url = changeUrl(Constant.ORDER_REPAIRS,replace);
            } catch (Exception e) {
                e.printStackTrace();
            }
*//*
        }

        ToastUtil.show(this,url);
        System.out.println(url);
            OkHttpUtils.getInstance().sendStringByPostMethod(url, content, new OkHttpBaseCallback<CreateOeder>() {

                @Override
                public void onSuccess(Response response, CreateOeder createOeder) {
                    hideDialiog();
                    ToastUtil.show(StationServingActivity.this,"成功");
                    //上传成功清空数据
                    clearnOrderDatas();
                }

                @Override
                public void onError(Response response, int code, Exception e) {
                    hideDialiog();
                    ToastUtil.show(StationServingActivity.this,""+code);
                    try {
                        System.out.println(response.body().string());
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
            } );

        //通过json传递数据
        System.out.println(content);

    }
*/
    private String changeUrl(String repairs, String orderRepairs) {
        StringBuffer buffer = new StringBuffer();
        buffer.append(repairs+"?uploadFiles=");

            buffer.append(orderRepairs);

       return  buffer.toString();
    }

    private void clearnOrderDatas() {
        //上传成功
        mStationType.setText("");
        mBreakType.setText("");
        mMachineType.setText("");
        sendPeople.setText("");
        mBreakDescribe.setText("");
        dataSelect.clear();
    }

    private void showSendPeople() {//展示派单人员
        //获取派单人员的信息
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
                    showListDialog(showDatas, sendPeople, 3);
                } else {
                    ToastUtil.show(StationServingActivity.this, "数据为空");
                }
            }

            @Override
            public void onError(Response response, int code, Exception e) {
                ToastUtil.show(StationServingActivity.this, "获取数据失败");
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
        params.put("estateTypeId", mId);
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
                    ToastUtil.show(StationServingActivity.this, "数据为空");
                }
            }

            @Override
            public void onError(Response response, int code, Exception e) {
                ToastUtil.show(StationServingActivity.this, "获取数据失败");
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
                    showDatas.clear();
                    for (int i = 0; i < mResults.size(); i++) {
                        showDatas.add(mResults.get(i).getName());
                    }
                    showListDialog(showDatas, mBreakType, 1);
                } else {
                    ToastUtil.show(StationServingActivity.this, "数据为空");
                }
            }

            @Override
            public void onError(Response response, int code, Exception e) {
                ToastUtil.show(StationServingActivity.this, "获取数据失败");
            }
        });
    }

    private void  showListDialog(List<String> showDatas, final TextView mText, final int type) {

        final CustomDialog customDialog = new CustomDialog(StationServingActivity.this, showDatas);
        customDialog.setOnDialogItemClick(new CustomDialog.onDialogItemClick() {
            @Override
            public void getDialogItem(String areaName, int position) {
                mText.setText(areaName);
                switch (type) {
                    case 1:
                        DeviceTypebean.ResultsBean resultsBean = mResults.get(position);
                        mId = resultsBean.getId(); //设备类型
                        break;
                    case 2:
                        Devicebean.ResultsBean resultsBean1 = mResults1.get(position);
                        mId1 = resultsBean1.getId();//设备
                        break;
                    case 3:
                        Rolebean.ResultsBean resultsBean2 = mResults2.get(position);
                        mId2 = resultsBean2.getId();//派单人
                        break;
                }
                customDialog.dismiss();
            }
        });
        customDialog.show();
    }


    private void goToSearchActivity() {
        Intent intent = new Intent(this, SearchActivity.class);
        intent.putExtra("CorpId", mCorpId);
        startActivityForResult(intent, 100);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == ImageSelectorActivity.REQUEST_IMAGE) {
            ArrayList<String> images = (ArrayList<String>) data.getSerializableExtra(ImageSelectorActivity.REQUEST_OUTPUT);
            dataSelect.clear();
            dataSelect.addAll(images);
            mGirdAdapter.notifyDataSetChanged();
            //获取图片并压缩转化为字符串
            getImageFile(images);
        }
        if (resultCode == 200 && requestCode == 100) {
            String stationName = data.getStringExtra("StationName");
            mStationId = data.getStringExtra("StationId");
            mStationType.setText(stationName);
        }


    }

    //上传图片
    private void getImageFile(ArrayList<String> images) {

        OkHttpUtils.getInstance().postImg("http://192.168.1.116:4071/files/uploadImage", images, new OkHttpBaseCallback<UploadImage>() {


            @Override
            public void onSuccess(Response response, UploadImage uploadImage) {
                ToastUtil.show(StationServingActivity.this,"成功");
            }

            @Override
            public void onError(Response response, int code, Exception e) {
                ToastUtil.show(StationServingActivity.this,"失败");
            }
        });


    }


    //手持机中大黄键，点击按钮启动扫描二维码
    //// TODO: 2017/9/2 扫二维获得二维码的数据，进行二维码的数据的显示
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_DPAD_CENTER) {
            Toast.makeText(this, "你点了黄色按键，KEYCODE_DPAD_CENTER 123", Toast.LENGTH_SHORT).show();
            startActivityForResult(new Intent(this, CaptureActivity.class), CaptureActivity.REQ_CODE);
        }
        return super.onKeyDown(keyCode, event);
    }


}

package com.avantport.avp.njpb.ui.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;

import com.avantport.avp.njpb.R;
import com.avantport.avp.njpb.adapter.FinalListAdapter;
import com.avantport.avp.njpb.bean.Companybean;
import com.avantport.avp.njpb.bean.DeviceTypebean;
import com.avantport.avp.njpb.bean.PartsApplyAndReturn;
import com.avantport.avp.njpb.bean.UserInfobean;
import com.avantport.avp.njpb.constant.Constant;
import com.avantport.avp.njpb.okhttp.OkHttpBaseCallback;
import com.avantport.avp.njpb.okhttp.OkHttpUtils;
import com.avantport.avp.njpb.uitls.SpUtil;
import com.avantport.avp.njpb.uitls.ToastUtil;
import com.avantport.avp.njpb.view.CustomDialog;
import com.avantport.avp.njpb.view.CustomListview;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Response;

/**
 * 物料归还和物料申请
 */
public class PutInWorkActivity extends BaseActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

    private TextView mPartType;
    private ListView mTypeLv;
    private ArrayAdapter<String> mTestDataAdapter;
    private ArrayList<String> mTestData;
    private PopupWindow typeSelectPopup;
    private TextView mHeadTitle;
    private ImageView mHeadGoback;
    private ImageView mHeadQrcode;
    private TextView mCompanyType;
    private EditText mPartCount;
    private Spinner mPart;
    private Integer mCorpId;//用户公司id
    private List<Companybean.ResultsBean> mCompanyResults;
    private String mSelectedItem;
    private int partsType = -1;
    private List<DeviceTypebean.ResultsBean> mResults;
    private List<String> showDatas = new ArrayList<>();
    private Integer mType;//配件类型
    private TextView mAddParts;
    private List<PartsApplyAndReturn> returnParts = new ArrayList<>();
    private CustomListview mListShow;
    private FinalListAdapter mFinalListAdapter;
    private Button mApply;
    private String mTypeActivity;//stockWorkOrderTypeId网络请求
    // TODO: 2017/9/7  
    

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_put_in_work;
    }

    protected void initView() {
        getType();
        mHeadTitle = (TextView) findViewById(R.id.head_title);
        mHeadGoback = (ImageView) findViewById(R.id.head_goback);
        mHeadQrcode = (ImageView) findViewById(R.id.head_qrcode);
       setActivityTitle();
        mHeadGoback.setOnClickListener(this);
        mHeadQrcode.setVisibility(View.INVISIBLE);
        mPart = (Spinner) findViewById(R.id.part_type);
        mCompanyType = (TextView) findViewById(R.id.company_type);
        mPartType = (TextView) findViewById(R.id.part_type_more);
        mPartCount = (EditText) findViewById(R.id.part_count);//设备数量
        mAddParts = (TextView) findViewById(R.id.add_parts);
        mListShow = (CustomListview) findViewById(R.id.list_show);
        mApply = (Button) findViewById(R.id.apply);//进行申请

    }

    private void setActivityTitle() {
        if (TextUtils.equals(mTypeActivity,"1")) {
            mHeadTitle.setText("物料申请");
        }

        if (TextUtils.equals(mTypeActivity,"2")) {
            mHeadTitle.setText("物料归还");
        }

    }

    @Override
    protected void initDatas() {
        initSetting();
        getUserId();
        showUserCompany();
        initSpinner();

    }

    private void getType() {//获得类型
        Intent intent = getIntent();
        mTypeActivity = intent.getStringExtra("type");//物料归还和物料申请
    }

    private void initSetting() {
        mCompanyType.setOnClickListener(this);
        mPartType.setOnClickListener(this);
        mAddParts.setOnClickListener(this);
        mListShow.setOnItemClickListener(this);//条目点击删除
        mApply.setOnClickListener(this);
    }

    private void initSpinner() {
        List<String> listString = new ArrayList<>();
        listString.add("请选择配件类别");
        listString.add("车辆");
        listString.add("站点");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, listString);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mPart.setAdapter(adapter);
        mPart.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mSelectedItem = (String) mPart.getSelectedItem();
                mPartType.setText("");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
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

    private void getUserId() {//获得用户信息
        //获得公司的id
        UserInfobean userInfobean = SpUtil.getObjFromSp("userInfobean");
        mCorpId = userInfobean.getPrincipal().getCorpId();
        ToastUtil.show(this, mCorpId + "");
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.head_goback:
                checkDialog();
                break;
            case R.id.part_type_more:
                getPartType();
                break;
            case R.id.add_parts://添加到列表展示
                addPartsToList();
                break;
            case R.id.apply://进行提交申请
                uploadDatas();
                break;
        }
    }

    private void uploadDatas() {//进行数据上传
        //校验一下填写信息
        if (returnParts.size() == 0) {
            ToastUtil.show(this,"请填写归还信息");
            return;
        }
        // TODO: 2017/9/7 再次进行数据的上传
        //进行上传数据的整合
        ToastUtil.show(this,mTypeActivity);

        
    }

    private void addPartsToList() {
        if (mPartType.getText().length() == 0) {
            ToastUtil.show(this, "请填写配件类型");
            return;
        }
        if (mPartCount.getText().length() == 0) {
            ToastUtil.show(this, "请填写配件数量");
            return;
        }
        showPartsData();//添加到list中进行展示
    }

    private void showPartsData() {
        String count = mPartCount.getText().toString().trim();
        String partName = mPartType.getText().toString();

        if (returnParts.size()!=0) {
            for (int i = 0; i < returnParts.size(); i++) {
                PartsApplyAndReturn partsApplyAndReturn = returnParts.get(i);
                if (TextUtils.equals( partsApplyAndReturn.getName(),partName)) {
                    ToastUtil.show(this,"配件已添加");
                    return;
                }
            }
        }
        returnParts.add(new PartsApplyAndReturn(partName,mType,count));
        if (mFinalListAdapter ==null) {
            mFinalListAdapter = new FinalListAdapter(returnParts, R.layout.item_mine_stock, new FinalListAdapter.AdapterListener<PartsApplyAndReturn>() {
                @Override
                public void bindView(FinalListAdapter.FinalViewHolder finalViewHolder, PartsApplyAndReturn partsApplyAndReturn) {
                    TextView partName = (TextView) finalViewHolder.getViewById(R.id.parts_name);
                    TextView partCount = (TextView) finalViewHolder.getViewById(R.id.parts_count);
                    partName.setText(partsApplyAndReturn.getName());
                    partCount.setText(partsApplyAndReturn.getCount());
                }

            });
            mListShow.setAdapter(mFinalListAdapter);
        }else{
            mFinalListAdapter.notifyDataSetChanged();
        }
        //添加成功后，清空配件类型和配件数量
        mPartCount.setText("");
        mPartType.setText("");
    }


    private void getPartType() {//获取配件类型
        if (TextUtils.equals(mSelectedItem, "请选择配件类别")) {
            ToastUtil.show(this, "请先选择配件类别");
            return;
        }

        if (TextUtils.equals(mSelectedItem, "车辆")) {
            partsType = 2;
        }
        if (TextUtils.equals(mSelectedItem, "站点")) {
            partsType = 1;
        }
        params.clear();
        params.put("partsType", partsType);
        OkHttpUtils.getInstance().get(Constant.PART_TYPE, params, null, new OkHttpBaseCallback<DeviceTypebean>() {

            @Override
            public void onSuccess(Response response, DeviceTypebean deviceTypebean) {
                mResults = deviceTypebean.getResults();
                if (mResults != null && mResults.size() != 0) {
                    showDatas.clear();
                    for (int i = 0; i < mResults.size(); i++) {
                        showDatas.add(mResults.get(i).getName());
                    }
                    showListDialog(showDatas, mPartType);
                }
            }

            @Override
            public void onError(Response response, int code, Exception e) {
                ToastUtil.show(PutInWorkActivity.this, "获取部件信息失败");

            }
        });
    }

    private void showListDialog(List<String> showDatas, final TextView mText) {

        final CustomDialog customDialog = new CustomDialog(this, showDatas);
        customDialog.setOnDialogItemClick(new CustomDialog.onDialogItemClick() {
            @Override
            public void getDialogItem(String areaName, int position) {
                mText.setText(areaName);
                DeviceTypebean.ResultsBean resultsBean = mResults.get(position);
                mType = resultsBean.getId();
                customDialog.dismiss();
            }
        });
        customDialog.show();
    }

    /**
     * 模拟假数据
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
        //部件列表点击删除
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("确定移除此配件吗？");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                returnParts.remove(position);
                mFinalListAdapter.notifyDataSetChanged();
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

package com.avantport.avp.njpb.ui.activity;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.avantport.avp.njpb.R;
import com.avantport.avp.njpb.adapter.ChangePartAdapter;
import com.avantport.avp.njpb.bean.DeviceTypebean;
import com.avantport.avp.njpb.constant.Constant;
import com.avantport.avp.njpb.okhttp.OkHttpBaseCallback;
import com.avantport.avp.njpb.okhttp.OkHttpUtils;
import com.avantport.avp.njpb.uitls.ToastUtil;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Response;

/**
 * 自修保养，选择部件
 */
public class ChangePartsActivity extends BaseActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

    private TextView mHeadTitle;
    private ImageView mHeadQrcode;
    private ImageView mHeadGoback;
    private ListView mPartsList;
    private Button mMakeSure;
    private List<DeviceTypebean.ResultsBean> mResults;
    private List<DeviceTypebean.ResultsBean> setDatas = new ArrayList<>();
    private ChangePartAdapter mChangePartAdapter;

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_change_parts;
    }

    @Override
    protected void initView() {
        mHeadTitle = (TextView) findViewById(R.id.head_title);
        mHeadQrcode = (ImageView) findViewById(R.id.head_qrcode);
        mHeadGoback = (ImageView) findViewById(R.id.head_goback);
        mPartsList = (ListView) findViewById(R.id.parts_list);
        mMakeSure = (Button) findViewById(R.id.make_sure);
        mHeadTitle.setText("更换配件");
        mHeadGoback.setOnClickListener(this);
        mMakeSure.setOnClickListener(this);
        mHeadQrcode.setVisibility(View.INVISIBLE);
        mPartsList.setOnItemClickListener(this);
    }

    @Override
    protected void initDatas() {
        Intent intent = getIntent();
        String category = intent.getStringExtra("category");
        String partsType = intent.getStringExtra("partsType");
        params.clear();
        params.put("category", category);
        params.put("partsType", partsType);//
        OkHttpUtils.getInstance().get(Constant.DEVICE_TYPE, params, null, new OkHttpBaseCallback<DeviceTypebean>() {

            @Override
            public void onSuccess(Response response, DeviceTypebean deviceTypebean) {
                mResults = deviceTypebean.getResults();
                if (mResults != null && mResults.size() != 0) {
                    //进行数据展示
                    showItemMessage();
                }
            }

            @Override
            public void onError(Response response, int code, Exception e) {
                ToastUtil.show(ChangePartsActivity.this,"获取部件信息失败");
                ToastUtil.show(ChangePartsActivity.this,code+"");
            }
        });
    }

    private void showItemMessage() {
        mChangePartAdapter = new ChangePartAdapter(mResults,this);
        mPartsList.setAdapter(mChangePartAdapter);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.head_goback:
                checkDialog();
                break;
            case R.id.make_sure://返回数据
                checkSelectData();
                break;
        }

    }

    private void checkSelectData() {//檢查是选中部件
        if (mResults != null && mResults.size()!=0) {//检查是否有选中的
            for (int i = 0; i < mResults.size(); i++) {
                if (mResults.get(i).isSelected()) {
                    setDatas.add(mResults.get(i));
                }
            }

            if (setDatas.size() == 0) {
                ToastUtil.show(this,"请选择部件");
            }else{
                Intent intent = new Intent();
                intent.putExtra("selectParts", (Serializable) setDatas);
                setResult(300,intent);
                finish();
            }

        }else{
            finish();//直接销毁
        }

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        DeviceTypebean.ResultsBean resultsBean = mResults.get(position);
        if (resultsBean.isSelected()) {
            resultsBean.setSelected(false);
        }else{
            resultsBean.setSelected(true);
        }
        mChangePartAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        setDatas.clear();
    }
}

package com.avantport.avp.njpb.ui.activity;

import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.avantport.avp.njpb.R;
import com.avantport.avp.njpb.adapter.FinalListAdapter;
import com.avantport.avp.njpb.bean.StationInfobean;
import com.avantport.avp.njpb.constant.Constant;
import com.avantport.avp.njpb.okhttp.OkHttpBaseCallback;
import com.avantport.avp.njpb.okhttp.OkHttpUtils;
import com.avantport.avp.njpb.uitls.ToastUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Response;

/**
 * 站点搜索
 *
 * */
public class SearchActivity extends BaseActivity implements View.OnClickListener, AdapterView.OnItemClickListener, TextWatcher {

    private TextView mHeadTitle;
    private ImageView mHeadGoback;
    private EditText mPutin;
    private TextView mSerach;
    private ListView mList;
    private Button mMakeSure;
    private int mCorpId;
    private Map<String, Object> params = new HashMap<>();
    private String mSerachName;
    private TextView mNullMsg;
    private FinalListAdapter mFinalListAdapter;
    private List<StationInfobean.ResultsBean> mResults;

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_search;
    }

    @Override
    protected void initView() {
        mHeadTitle = (TextView) findViewById(R.id.head_title);
        mHeadGoback = (ImageView) findViewById(R.id.head_goback);
        mPutin = (EditText) findViewById(R.id.putin);
        mSerach = (TextView) findViewById(R.id.serach);
        mList = (ListView) findViewById(R.id.list);
        mMakeSure = (Button) findViewById(R.id.make_sure);
        mNullMsg = (TextView) findViewById(R.id.null_message);
    }

    @Override
    protected void initDatas() {
        getCorpId();
        mHeadTitle.setText("站点搜索");
        mHeadGoback.setOnClickListener(this);
        mSerach.setOnClickListener(this);
        mMakeSure.setOnClickListener(this);
        mList.setOnItemClickListener(this);
        mPutin.addTextChangedListener(this);
    }

    private void getCorpId() {
        Intent intent = getIntent();
        mCorpId = intent.getIntExtra("CorpId",-1);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.head_goback:
                finish();
                break;
            case R.id.serach://点击进行网络请求
                getStationDatas();
                break;
            case R.id.make_sure:
                //获得选中的条目
                getStationItem();
                break;
        }
    }

    private void getStationItem() {
        //获得选中的条目信息
        if (mResults != null&&mResults.size()!=0) {
            for (int i = 0; i < mResults.size(); i++) {
                if (mResults.get(i).isSelected()) {
                    String stationNoName = mResults.get(i).getStationNoName();
                    Integer id = mResults.get(i).getId();
                    Intent data = new Intent();
                    data.putExtra("StationId",id+"");
                    data.putExtra("StationName",stationNoName);
                    setResult(200,data);
                    finish();
                    return;
                }
            }

            finish();//返回上一个activity

        }else{//没有搜索到地址
            finish();
        }
    }

    private void getStationDatas() {
        String mSerachName = mPutin.getText().toString().trim();
        if (mSerachName.length()==0) {
            ToastUtil.show(this,"请输入关键字");
            return;
        }
        getNetDatas(mSerachName);


    }

    private void getNetDatas(String mSerachName) {
        //进行网络请求
        params.clear();
        params.put("stationNoName",mSerachName);
        params.put("corpId",mCorpId);// TODO: 2017/9/2
        OkHttpUtils.getInstance().get(Constant.STATION_INFO, params, null, new OkHttpBaseCallback<StationInfobean>() {

            @Override
            public void onSuccess(Response response, StationInfobean stationInfobean) {
                mResults = stationInfobean.getResults();
                if (mResults != null&& mResults.size() == 0) {
                    mNullMsg.setVisibility(View.VISIBLE);
                }else{
                    mNullMsg.setVisibility(View.GONE);
                    setNetDatas(mResults);
                }
            }

            @Override
            public void onError(Response response, int code, Exception e) {
                //搜索错误的时候进行提示
                mNullMsg.setVisibility(View.VISIBLE);
            }
        });

    }

    private void setNetDatas(final List<StationInfobean.ResultsBean> results) {
        //展示数据
        mFinalListAdapter = new FinalListAdapter(results, R.layout.item_station_search, new FinalListAdapter.AdapterListener<StationInfobean.ResultsBean>() {
            @Override
            public void bindView(FinalListAdapter.FinalViewHolder finalViewHolder, StationInfobean.ResultsBean resultsBean) {
                TextView station_name = (TextView) finalViewHolder.getViewById(R.id.Station_name);
                ImageView station_check = (ImageView) finalViewHolder.getViewById(R.id.station_select);
                station_name.setText(resultsBean.getStationNoName());
                if (resultsBean.isSelected()) {
                    station_check.setSelected(true);
                }else{
                    station_check.setSelected(false);
                }
            }

        });
        mList.setAdapter(mFinalListAdapter);
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //条目的点击事件
        for (int i = 0; i < mResults.size(); i++) {
            if (i == position) {
                mResults.get(position).setSelected(true);
            }else{
                mResults.get(i).setSelected(false);
            }
        }
        mFinalListAdapter.notifyDataSetChanged();
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        if (mPutin.getText().length() == 0) {
            mNullMsg.setVisibility(View.GONE);
        }
    }
}

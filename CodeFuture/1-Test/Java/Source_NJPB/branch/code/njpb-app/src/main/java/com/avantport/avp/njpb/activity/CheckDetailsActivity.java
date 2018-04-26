package com.avantport.avp.njpb.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.avantport.avp.njpb.R;
import com.avantport.avp.njpb.adapter.FinalListAdapter;
import com.avantport.avp.njpb.bean.CheckDetailsbean;
import com.avantport.avp.njpb.bean.MineTakeOutbean;
import com.avantport.avp.njpb.bean.PutRequestbean;
import com.avantport.avp.njpb.constant.Constant;
import com.avantport.avp.njpb.okhttp.OkHttpBaseCallback;
import com.avantport.avp.njpb.okhttp.OkHttpUtils;
import com.avantport.avp.njpb.ui.activity.BaseActivity;
import com.avantport.avp.njpb.uitls.DateUtil;
import com.avantport.avp.njpb.uitls.ToastUtil;
import com.avantport.avp.njpb.view.CustomListview;
import com.avantport.avp.njpb.view.HintDialog;
import com.avantport.avp.njpb.view.StateLayout;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Response;

public class CheckDetailsActivity extends BaseActivity implements View.OnClickListener {

    private static final int CHECKPASS_CODE = 200;
    private StateLayout mLayoutState;
    private TextView mOrderCode;
    private TextView mApplyName;
    private TextView mConmpany;
    private TextView mApplyTime;
    private CustomListview mListParts;
    private Button mCheckBack;
    private Button mCheckPass;

    private Map<String, Object> params = new HashMap<>();
    private MineTakeOutbean.ResultsBean mIntentDatas;
    private FinalListAdapter mFinalListAdapter;

    private int REQUESTCODE_CANCEL = 1;
    private int mPosition;


    @Override
    protected int getLayoutRes() {
        return R.layout.activity_check_details;
    }

    @Override
    protected void initView() {
        Bundle bundle = getIntent().getExtras();
        mPosition = bundle.getInt("position");//条目的索引
        mIntentDatas = (MineTakeOutbean.ResultsBean) bundle.getSerializable("resultsBean");
        TextView headTitle = (TextView) findViewById(R.id.head_title);
        ImageView headQrcode = (ImageView) findViewById(R.id.head_qrcode);
        ImageView headGoback = (ImageView) findViewById(R.id.head_goback);
        headQrcode.setVisibility(View.INVISIBLE);
        headTitle.setText(bundle.getCharSequence("title"));//获取传递过来的数据
        headGoback.setOnClickListener(this);

        //初始化加载成功的界面
        getSuccessView();
        //显示数据
        setDetailDatas();

    }

    private void setDetailDatas() {
        mOrderCode.setText(mIntentDatas.getSerialNo());
        mApplyName.setText(mIntentDatas.getProcessUserName());
        mConmpany.setText(mIntentDatas.getCorpName());
        mApplyTime.setText(DateUtil.formatDate(mIntentDatas.getApplyTime()));
    }

    private void getSuccessView() {//初始化成功的界面
        mLayoutState = (StateLayout) findViewById(R.id.layout_state);
        View view = View.inflate(this, R.layout.layout_check_detail, null);
        TextView character = (TextView) view.findViewById(R.id.character);//角色
        character.setText(R.string.character);
        mOrderCode = (TextView) view.findViewById(R.id.order_code);
        mApplyName = (TextView) view.findViewById(R.id.apply_name);  //名字
        mConmpany = (TextView) view.findViewById(R.id.conmpany);
        mApplyTime = (TextView) view.findViewById(R.id.apple_time);
        //部件展示列表
        mListParts = (CustomListview) view.findViewById(R.id.list_parts);
        mCheckBack = (Button) view.findViewById(R.id.check_back);
        mCheckPass = (Button) view.findViewById(R.id.check_pass);
        mLayoutState.bindSuccessView(view);
        //button的点击事件
        mCheckBack.setOnClickListener(this);//退回
        mCheckPass.setOnClickListener(this);//通过
    }

    @Override
    protected void initDatas() {
        //获取网络数据
        params.clear();
        params.put("stockWorkOrderId", mIntentDatas.getId());
        mLayoutState.showLoadingView();
        getNetDatas(Constant.ORDER_DETAILS, params);

    }

    private void getNetDatas(String url, Map<String, Object> params) {
        OkHttpUtils.getInstance().get(url, params, null, new OkHttpBaseCallback<CheckDetailsbean>() {

            @Override
            public void onSuccess(Response response, CheckDetailsbean checkDetailsbean) {
                mLayoutState.showSuccessView();//解析数据
                List<CheckDetailsbean.ResultsBean> results = checkDetailsbean.getResults();
                //listview的数据展示
                setNetDatas(results);
            }

            @Override
            public void onError(Response response, int code, Exception e) {
                mLayoutState.showErrorView();//加载失败
            }
        });

    }

    private void setNetDatas(final List<CheckDetailsbean.ResultsBean> results) {//展示列表数据

        mFinalListAdapter = new FinalListAdapter(results, R.layout.item_mine_stock, new FinalListAdapter.AdapterListener<CheckDetailsbean.ResultsBean>() {
            @Override
            public void bindView(FinalListAdapter.FinalViewHolder finalViewHolder, CheckDetailsbean.ResultsBean resultsBean) {
                TextView partsName = (TextView) finalViewHolder.getViewById(R.id.parts_name);
                TextView partsCount = (TextView) finalViewHolder.getViewById(R.id.parts_count);
                partsName.setText(resultsBean.getEstateName());
                partsCount.setText(resultsBean.getCount() + "");
            }
        });
        mListParts.setAdapter(mFinalListAdapter);
    }

    @Override
    public void onClick(View v) {//点击事件
        switch (v.getId()) {
            case R.id.head_goback:
                finish();
                break;
            case R.id.check_back:
                //弹出对话框进行提示
                showBackDialog();

                break;
            case R.id.check_pass:
                //进行通过请求
                showPassDialog();
                break;
        }
    }

    private void showPassDialog() {//显示通过dialog
        final HintDialog hintDialog = new HintDialog(this);
        hintDialog.setOnClickListaner(new HintDialog.onClickListaner() {
            @Override
            public void clickCancel() {
                hintDialog.dismiss();
            }

            @Override
            public void clickSure() {
                //进行网络请求，通过工单审核
                hintDialog.dismiss();

                NetCheckPass();//进行网络请求，通过审核
            }
        });
        hintDialog.show();
    }

    private void NetCheckPass() {//进行网络请求，通过审核
        showProDialog("确认中...");
        params.clear();
        params.put("stockWorkOrderId", mIntentDatas.getId()+"");
        OkHttpUtils.getInstance().put(Constant.ORDER_CHECK_PASS, params, null, new OkHttpBaseCallback<PutRequestbean>() {

            @Override
            public void onSuccess(Response response, PutRequestbean putRequestbean) {
                hideDialiog();//隐藏dialog 审核成功
                //返回到列表界面并删除该信息
                Intent intent = new Intent();
                intent.putExtra("position",mPosition);
                setResult(CHECKPASS_CODE,intent);
                finish();
            }

            @Override
            public void onError(Response response, int code, Exception e) {
                hideDialiog();//隐藏dialog
                ToastUtil.show(CheckDetailsActivity.this, "审核失败！");
            }
        });

    }

    private void showBackDialog() {//退回的dialog
        final HintDialog hintDialog = new HintDialog(this);
        hintDialog.setOnClickListaner(new HintDialog.onClickListaner() {
            @Override
            public void clickCancel() {
                hintDialog.dismiss();
            }

            @Override
            public void clickSure() {
                hintDialog.dismiss();
                Intent intent = new Intent(CheckDetailsActivity.this, CheckBackRemarkActivity.class);
                intent.putExtra("DetailsId", mIntentDatas.getId());
                startActivityForResult(intent, REQUESTCODE_CANCEL);
            }
        });
        hintDialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode ==REQUESTCODE_CANCEL) {
            switch (resultCode) {
                case 300:
                    //返回到列表界面并删除该信息
                    Intent intent = new Intent();
                    intent.putExtra("position",mPosition);
                    setResult(CHECKPASS_CODE,intent);
                    finish();
                    break;
            }
        }

    }

    @Override
    public void onBackPressed() {
        finish();
    }
}

package com.avantport.avp.njpb.activity;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.avantport.avp.njpb.R;
import com.avantport.avp.njpb.adapter.FinalListAdapter;
import com.avantport.avp.njpb.adapter.GirgCheckAdapter;
import com.avantport.avp.njpb.bean.AllPicbean;
import com.avantport.avp.njpb.bean.NextStepbean;
import com.avantport.avp.njpb.bean.OrderDetailsbean;
import com.avantport.avp.njpb.bean.OrderMessagebean;
import com.avantport.avp.njpb.bean.Partsbean;
import com.avantport.avp.njpb.bean.ReplacePartbean;
import com.avantport.avp.njpb.constant.Constant;
import com.avantport.avp.njpb.okhttp.OkHttpBaseCallback;
import com.avantport.avp.njpb.okhttp.OkHttpUtils;
import com.avantport.avp.njpb.ui.activity.BaseActivity;
import com.avantport.avp.njpb.uitls.DateUtil;
import com.avantport.avp.njpb.view.CustomListview;
import com.avantport.avp.njpb.view.SelectGirdView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Response;

public class OrderBikeDetailsActivity extends BaseActivity implements View.OnClickListener {

    private OrderMessagebean.ResultsBean mResultsBean;
    private ImageView mHeadQrcode;
    private ImageView mHeadGoback;
    private TextView mHeadTitle;
    private TextView mOrderCode;
    private TextView mCharacter;
    private TextView mName;
    private TextView mBikeCode;
    private TextView mBreakSee;
    private TextView mUpdataTime;

    private LinearLayout mLinearPic;
    private GridLayout mLayoutPic;
    private CustomListview mListRecord;
    private RelativeLayout mNextButton;
    private Button mSendBack;
    private Button mSendPass;

    private LinearLayout mFactory;
    private TextView mFactoryName;
    private SelectGirdView mCheckParts;
    private Map<String, Object> params = new HashMap<>();
    private GirgCheckAdapter mGirgCheckAdapter;
    private LinearLayout mLinearSendMsg;
    private TextView mSendName;
    private TextView mSendtime;
    private TextView mSendRemark;
    private TextView mRepairName;
    private TextView mRepairTime;
    private TextView mRepairWay;
    private LinearLayout mLinearFinishPic;
    private GridLayout mLayoutFinishPic;
    private LinearLayout mLinearPartlist;
    private CustomListview mListPartCount;
    private LinearLayout mLinearDoubleSend;
    private Button mSendOrderAgain;
    private Button mOrderCheck;
    private Button mOrderSign;
    private Button mOrderSure;
    private LinearLayout mLinearDoubleFinish;
    private Button mOrderLeave;
    private Button mOrderFinish;
    private LinearLayout mLinearDoubleAgain;
    private Button mOrderCheckover;
    private Button mOrderCheckagain;
    private TextView mChangeTime;
    private LinearLayout mSendoRderPeople;
    private LinearLayout mSendOrderTime;
    private LinearLayout mSendOrderRemark;
    private LinearLayout mRepairOrderName;
    private LinearLayout mRepairOrderTime;
    private LinearLayout mRepairOrderWay;

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_order_details;
    }

    @Override
    protected void initView() {
        getIntentData();
        mHeadQrcode = (ImageView) findViewById(R.id.head_qrcode);
        mHeadGoback = (ImageView) findViewById(R.id.head_goback);
        mHeadTitle = (TextView) findViewById(R.id.head_title);
        mChangeTime = (TextView) findViewById(R.id.change_time);
        mOrderCode = (TextView) findViewById(R.id.order_code);
        mCharacter = (TextView) findViewById(R.id.character);
        mName = (TextView) findViewById(R.id.name);
        mBikeCode = (TextView) findViewById(R.id.bike_code);
        mBreakSee = (TextView) findViewById(R.id.break_see);
        mUpdataTime = (TextView) findViewById(R.id.updata_time);
        mFactoryName = (TextView) findViewById(R.id.factory_name);
        mFactory = (LinearLayout) findViewById(R.id.factory);

        mLinearSendMsg = (LinearLayout) findViewById(R.id.linear_send_message);//派单信息
        mSendoRderPeople = (LinearLayout) findViewById(R.id.sendo_rder_people);
        mSendName = (TextView) findViewById(R.id.send_name);//派单人
        mSendOrderTime = (LinearLayout) findViewById(R.id.send_order_time);
        mSendtime = (TextView) findViewById(R.id.send_time);//派单时间
        mSendOrderRemark = (LinearLayout) findViewById(R.id.send_order_remark);
        mSendRemark = (TextView) findViewById(R.id.send_remark);//派单备注
        mRepairOrderName = (LinearLayout) findViewById(R.id.repair_order_name);
        mRepairName = (TextView) findViewById(R.id.repair_name);//维修人
        mRepairOrderTime = (LinearLayout) findViewById(R.id.repair_order_time);
        mRepairTime = (TextView) findViewById(R.id.repair_time);//维修时间
        mRepairOrderWay = (LinearLayout) findViewById(R.id.repair_order_way);
        mRepairWay = (TextView) findViewById(R.id.repair_way);//维修措施


        //报修图片
        mLinearPic = (LinearLayout) findViewById(R.id.linear_pic);
        mLayoutPic = (GridLayout) findViewById(R.id.layout_pic);
        //完成图片
        mLinearFinishPic = (LinearLayout) findViewById(R.id.linear_finish_pic);
        mLayoutFinishPic = (GridLayout) findViewById(R.id.layout_finish_pic);
        //checkbox复选框
        mCheckParts = (SelectGirdView) findViewById(R.id.check_parts);
        //更换配件
        mLinearPartlist = (LinearLayout) findViewById(R.id.linear_partlist);
        mListPartCount = (CustomListview) findViewById(R.id.list_part_count);
        //工作流
        mListRecord = (CustomListview) findViewById(R.id.list_record);
        //下一步操作的按钮
        //派单
        mLinearDoubleSend = (LinearLayout) findViewById(R.id.linear_double_send);
        mSendBack = (Button) findViewById(R.id.check_back);//退回
        mSendPass = (Button) findViewById(R.id.check_pass);//完成
        //再次派单
        mSendOrderAgain = (Button) findViewById(R.id.send_order_again);
        //工单审核
        mOrderCheck = (Button) findViewById(R.id.order_check);
        //工单签到
        mOrderSign = (Button) findViewById(R.id.order_sign);
        //工单确认
        mOrderSure = (Button) findViewById(R.id.order_sure);
        //遗留和完成
        mLinearDoubleFinish = (LinearLayout) findViewById(R.id.linear_double_finish);
        mOrderLeave = (Button) findViewById(R.id.order_leave);
        mOrderFinish = (Button) findViewById(R.id.order_finish);
        //报修结束
        mLinearDoubleAgain = (LinearLayout) findViewById(R.id.linear_double_again);
        mOrderCheckover = (Button) findViewById(R.id.order_checkover);
        mOrderCheckagain = (Button) findViewById(R.id.order_checkagain);
        //设置数据
        mHeadTitle.setText("工单详情");
        mCharacter.setText("报修人");
        mChangeTime.setText("报修时间");
        mHeadQrcode.setVisibility(View.INVISIBLE);
        mFactory.setVisibility(View.VISIBLE);

        mHeadGoback.setOnClickListener(this);
        mSendBack.setOnClickListener(this);
        mSendPass.setOnClickListener(this);
        mSendOrderAgain.setOnClickListener(this);
        mOrderCheck.setOnClickListener(this);
        mOrderSign.setOnClickListener(this);
        mOrderSure.setOnClickListener(this);
        mOrderLeave.setOnClickListener(this);
        mOrderFinish.setOnClickListener(this);
        mOrderCheckover.setOnClickListener(this);
        mOrderCheckagain.setOnClickListener(this);
    }

    private void getIntentData() {
        Intent intent = getIntent();
        mResultsBean = (OrderMessagebean.ResultsBean) intent.getSerializableExtra("orderBike");
    }

    @Override
    protected void initDatas() {
        getNextStep();//获得是否有下一步操作
        getAllPartsType();//获取部件类型
        getBadParts();//获得更换部件
        getRepairsPic();//获得维修的图片
        getOrderRecord();//获得工单历史记录
        getItemMessage();//加载的条目信息
    }

    private void getItemMessage() {//获得条目的信息
        params.clear();
        params.put("workOrderId", "10");
        OkHttpUtils.getInstance().get(Constant.ORDER_MEASSAGE_DETAILS, params, null, new OkHttpBaseCallback<OrderDetailsbean>() {

            @Override
            public void onSuccess(Response response, OrderDetailsbean orderDetailsbean) {
                OrderDetailsbean.ResultBean result = orderDetailsbean.getResult();
                //显示数据
                showOrderMsgDatas(result);

            }

            @Override
            public void onError(Response response, int code, Exception e) {

            }
        });
    }

    private void showOrderMsgDatas(OrderDetailsbean.ResultBean result) {
        mOrderCode.setText(result.getSerialNo());
        mName.setText(result.getReportEmployeeUserName());
        mFactoryName.setText(result.getSupplierName());
        mUpdataTime.setText(DateUtil.formatDate(result.getReportTime()));
        mBikeCode.setText(result.getEstateSn());
        mBreakSee.setText(result.getFaultDescription());

        if (result.getAssignEmployeeUserName() != null) {
            mSendName.setText(result.getAssignEmployeeUserName());
        } else {
            mSendoRderPeople.setVisibility(View.GONE);
        }
        if (result.getAssignTime() != null) {
            mSendtime.setText(DateUtil.formatDate(result.getAssignTime()));
        } else {
            mSendOrderTime.setVisibility(View.GONE);
        }

        if (result.getRepairEndTime() != null) {
            mRepairTime.setText(DateUtil.formatDate(result.getRepairEndTime()));
        } else {
            mRepairOrderTime.setVisibility(View.GONE);
        }

        if (result.getAssignRemark() != null) {
            mSendRemark.setText(result.getAssignRemark());
        } else {
            mSendOrderRemark.setVisibility(View.GONE);
        }
        if (result.getRepairEmployeeUserName() != null) {
            mRepairName.setText(result.getRepairEmployeeUserName());

        } else {
            mRepairOrderName.setVisibility(View.GONE);
        }
        if (result.getRepairRemark() != null) {
            mRepairWay.setText(result.getRepairRemark());
        } else {
            mRepairOrderWay.setVisibility(View.GONE);
        }
    }

    private void getNextStep() {
        params.clear();
        params.put("workOrderId", mResultsBean.getId());
        OkHttpUtils.getInstance().get(Constant.NEXT_STEP, params, null, new OkHttpBaseCallback<NextStepbean>() {

            @Override
            public void onSuccess(Response response, NextStepbean nextStepbean) {
                List<String> results = nextStepbean.getResults();
                if (results != null && results.size() != 0) {//请求成功，有数据
                    resultSwitch(results);
                }
            }

            @Override
            public void onError(Response response, int code, Exception e) {

            }
        });

    }

    private void resultSwitch(List<String> results) {
        //下一步的指示
        if (results.contains("flow_report_agree")) {
            mLinearDoubleSend.setVisibility(View.VISIBLE);
        } else if (results.contains("flow_reReport")) {
            mLinearDoubleAgain.setVisibility(View.VISIBLE);
        } else if (results.contains("flow_reAssign")) {
            mSendOrderAgain.setVisibility(View.VISIBLE);
        } else if (results.contains("flow_repair_complete")) {
            mLinearDoubleFinish.setVisibility(View.VISIBLE);
        } else if (results.contains("flow_confirm_wo")) {
            mOrderSure.setVisibility(View.VISIBLE);
        } else if (results.contains("flow_sign_in")) {
            mOrderSign.setVisibility(View.VISIBLE);
        } else if (results.contains("flow_repair_check")) {
            mOrderCheck.setVisibility(View.VISIBLE);
        }

    }

    private void getOrderRecord() {//获得流转记录
        params.clear();
        params.put("workOrderId", mResultsBean.getId());
       /* OkHttpUtils.getInstance().get(Constant.FLOW_MEASSAGE, params, null, new OkHttpBaseCallback() {
            @Override
            public void onSuccess(Response response, Object o) {

            }

            @Override
            public void onError(Response response, int code, Exception e) {

            }
        });*/
    }

    private void getRepairsPic() {
        //获得图片资源
        params.clear();
        params.put("workOrderId", mResultsBean.getId());
        OkHttpUtils.getInstance().get(Constant.ORDER_PICTURE, params, null, new OkHttpBaseCallback<AllPicbean>() {

            @Override
            public void onSuccess(Response response, AllPicbean allPicbean) {
                //获得图片资源，进行图片类的操作，进行图片的加载
                List<AllPicbean.ResultsBean> results = allPicbean.getResults();
                //获得图片数据
                for (int i = 0; i < results.size(); i++) {
                    AllPicbean.ResultsBean resultsBean = results.get(i);
                   //图片的类型，有几种类型2？
                }
            }

            @Override
            public void onError(Response response, int code, Exception e) {

            }
        });
    }

    private void getBadParts() {
        params.clear();
        params.put("workOrderId", mResultsBean.getId());
        OkHttpUtils.getInstance().get(Constant.BADCOMPONENTS, params, null, new OkHttpBaseCallback<ReplacePartbean>() {

            @Override
            public void onSuccess(Response response, ReplacePartbean replacePartbean) {
                List<ReplacePartbean.ResultsBean> results = replacePartbean.getResults();
                if (results != null && results.size() != 0) {
                    if (mGirgCheckAdapter != null) {
                        for (int i = 0; i < mGirgCheckAdapter.getCount(); i++) {
                            if (results.contains(mGirgCheckAdapter.getItem(i).getName())) {
                                mGirgCheckAdapter.getItem(i).setSelected(true);
                            }
                        }
                        mGirgCheckAdapter.notifyDataSetChanged();
                    }
                    //将部件的数量和类型也要展示出来
                    showPartsTypeCount(results);
                    // TODO: 2017/8/30 车辆和站点的是否要区别 
                }
            }

            @Override
            public void onError(Response response, int code, Exception e) {

            }
        });
    }

    private void showPartsTypeCount(final List<ReplacePartbean.ResultsBean> results) {
        //将部件的类型和数量展示出来，没有的话藏改条目
        mLinearPartlist.setVisibility(View.VISIBLE);
        mListPartCount.setAdapter(new FinalListAdapter<>(results, R.layout.item_mine_stock, new FinalListAdapter.AdapterListener<ReplacePartbean.ResultsBean>() {
            @Override
            public void bindView(FinalListAdapter.FinalViewHolder finalViewHolder, ReplacePartbean.ResultsBean resultsBean) {
                TextView partsName = (TextView) finalViewHolder.getViewById(R.id.parts_name);
                TextView partsCount = (TextView) finalViewHolder.getViewById(R.id.parts_count);
                partsName.setText(resultsBean.getName());//// TODO: 2017/8/30
                if (resultsBean.getReplaceCount() != null) {
                    partsCount.setText(resultsBean.getReplaceCount() + "");//有数据显示数据
                } else {
                    partsCount.setText("");
                }
            }
        }));
    }

    private void getAllPartsType() {
        params.clear();
        params.put("partsType", "2");
        OkHttpUtils.getInstance().get(Constant.COMPONENTS_TYPE, params, null, new OkHttpBaseCallback<Partsbean>() {
            @Override
            public void onSuccess(Response response, Partsbean partsbean) {
                List<Partsbean.ResultsBean> results = partsbean.getResults();
                //获得部件的数量
                mGirgCheckAdapter = new GirgCheckAdapter(results, OrderBikeDetailsActivity.this);
                mCheckParts.setAdapter(mGirgCheckAdapter);
            }

            @Override
            public void onError(Response response, int code, Exception e) {

            }
        });
    }


    @Override
    public void onClick(View v) {//按钮的点击事件
        switch (v.getId()) {
            case R.id.head_goback:
                finish();
                break;
            case R.id.check_back://退回
                break;
            case R.id.check_pass://派单
                break;
            case R.id.send_order_again://再次派单
                break;
            case R.id.order_check://工单审核
                break;
            case R.id.order_sign://工单签到
                break;
            case R.id.order_sure://工单确认
                break;
            case R.id.order_leave://工单遗留
                break;
            case R.id.order_finish://工单完成
                break;
            case R.id.order_checkover://报修结束
                break;
            case R.id.order_checkagain://再次报修
                break;

        }

    }
}

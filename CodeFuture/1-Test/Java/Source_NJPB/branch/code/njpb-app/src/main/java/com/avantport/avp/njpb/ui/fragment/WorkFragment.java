package com.avantport.avp.njpb.ui.fragment;

import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.avantport.avp.njpb.R;
import com.avantport.avp.njpb.bean.WorkLimitbean;
import com.avantport.avp.njpb.ui.work.BikeCheckActivity;
import com.avantport.avp.njpb.ui.work.BreakServingActivity;
import com.avantport.avp.njpb.ui.work.ChangeCodeActivity;
import com.avantport.avp.njpb.ui.work.CheckApplyActivity;
import com.avantport.avp.njpb.ui.work.KeepByMyselfActivity;
import com.avantport.avp.njpb.ui.work.PutInWorkActivity;
import com.avantport.avp.njpb.uitls.SpUtil;

import java.util.List;


/**
 * Created by avp on 2017-08-02.
 * 工作 fragment
 */

public class WorkFragment extends BaseFragment implements View.OnClickListener {

    private LinearLayout mWork_break;
    private LinearLayout mWork_check;
    private LinearLayout mWork_putin;
    private LinearLayout mWork_pickout;
    private LinearLayout mWork_changeCode;
    private LinearLayout mWork_bicycleCount;
    private LinearLayout mWork_checkapply;
    private WorkLimitbean mUserWorkLimit;
    private List<WorkLimitbean.ResultsBean> mResults;
    private LinearLayout mWorkBreakdown;
    private LinearLayout mBreakDownCheck;


    @Override
    protected View getFragmentView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.fragment_work_view, container, false);
    }

    @Override
    protected void initDatas() {
        getWorkLimit();
        showWorkLimit();
    }

    private void showWorkLimit() {
        for (int i = 0; i < mResults.size(); i++) {
            String resourceCode = mResults.get(i).getResourceCode();
            if (TextUtils.equals(resourceCode, "NJPB-APP-WORK-FAULTREPORT")) {
                mWork_break.setVisibility(View.VISIBLE);
            } else if (TextUtils.equals(resourceCode, "NJPB-APP-WORK-MAINTENANCE")) {
                mWork_check.setVisibility(View.VISIBLE);
            } else if (TextUtils.equals(resourceCode, "NJPB-APP-WORK-APPLYPICKING")) {
                mWork_pickout.setVisibility(View.VISIBLE);
            } else if (TextUtils.equals(resourceCode, "NJPB-APP-WORK-BACK")) {
                mWork_putin.setVisibility(View.VISIBLE);
            } else if (TextUtils.equals(resourceCode, "NJPB-APP-WORK-REPLACEBARCODE")) {
                mWork_changeCode.setVisibility(View.VISIBLE);
            } else if (TextUtils.equals(resourceCode, "NJPB-APP-WORK-BIKECHECK")) {
                mWork_bicycleCount.setVisibility(View.VISIBLE);
            } else if (TextUtils.equals(resourceCode, "NJPB-APP-WORK-CHECKAPPLY")) {
                mWork_checkapply.setVisibility(View.VISIBLE);
            } else if (TextUtils.equals(resourceCode,"NJPB-APP-WORK-SCRAP")){
               mWorkBreakdown.setVisibility(View.VISIBLE);
            }
        }
    }

    private void getWorkLimit() {
        mUserWorkLimit = SpUtil.getObjFromSp("userWorkLimit");
        mResults = mUserWorkLimit.getResults();
    }

    @Override
    protected void getFragmentData(View view) {
        mWork_break = (LinearLayout) view.findViewById(R.id.work_break);
        mWork_check = (LinearLayout) view.findViewById(R.id.work_check);
        mWork_putin = (LinearLayout) view.findViewById(R.id.work_putin);
        mWork_pickout = (LinearLayout) view.findViewById(R.id.work_pickout);
        mWork_changeCode = (LinearLayout) view.findViewById(R.id.work_changeCode);
        mWork_bicycleCount = (LinearLayout) view.findViewById(R.id.work_bicycleCount);
        mWork_checkapply = (LinearLayout) view.findViewById(R.id.work_checkapply);
        mWorkBreakdown = (LinearLayout) view.findViewById(R.id.work_breakdown);
        mBreakDownCheck = (LinearLayout) view.findViewById(R.id.breakdown_check);

        mWork_break.setOnClickListener(this);
        mWork_check.setOnClickListener(this);
        mWork_putin.setOnClickListener(this);
        mWork_pickout.setOnClickListener(this);
        mWork_changeCode.setOnClickListener(this);
        mWork_bicycleCount.setOnClickListener(this);
        mWork_checkapply.setOnClickListener(this);
        mWorkBreakdown.setOnClickListener(this);
        mBreakDownCheck.setOnClickListener(this);
    }

    //条目设置点击事件，跳转界面
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.work_break://故障报修
                /*startActivity(new Intent(mContext, BreakServingActivity.class));
                activityJumpAnim();*/
                startActivity(BreakServingActivity.class);
                break;
            case R.id.work_check://自修保养
               /* startActivity(new Intent(mContext, KeepByMyselfActivity.class));
                activityJumpAnim();*/
                startActivity(KeepByMyselfActivity.class);
                break;
            case R.id.work_putin://物料归还
                Intent intent1 = new Intent(mContext, PutInWorkActivity.class);
                intent1.putExtra("type", "2");
                startActivity(intent1);
                activityJumpAnim();
                break;
            case R.id.work_pickout://领料作业//
                Intent intent2 = new Intent(mContext, PutInWorkActivity.class);
                intent2.putExtra("type", "1");
                startActivity(intent2);
                activityJumpAnim();
                break;
            case R.id.work_changeCode://更换条码
                /*startActivity(new Intent(mContext, ChangeCodeActivity.class));
                activityJumpAnim();*/
                startActivity(ChangeCodeActivity.class);
                break;
            case R.id.work_bicycleCount:
//                ToastUtil.show(mContext,"没有读卡设备！");
               /* startActivity(new Intent(mContext, BikeCheckActivity.class));
                activityJumpAnim();*/
                startActivity(BikeCheckActivity.class);
                break;
            case R.id.work_checkapply:
               /* startActivity(new Intent(mContext, CheckApplyActivity.class));
                activityJumpAnim();*/
                startActivity(CheckApplyActivity.class);
                break;
            case R.id.work_breakdown:
                //进行物料报废
                Intent intent3 = new Intent(mContext, PutInWorkActivity.class);
                intent3.putExtra("type", "4");
                startActivity(intent3);
                activityJumpAnim();
                break;
            // TODO: 2017/11/8 报废
//            case R.id.breakdown_check:
//                //进行报废的审核
//                break;
        }

    }

}

package com.avantport.avp.njpb.ui.fragment;

import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.avantport.avp.njpb.R;
import com.avantport.avp.njpb.activity.MineCountCheckActivity;
import com.avantport.avp.njpb.activity.MineHaveActivity;
import com.avantport.avp.njpb.activity.MineKeepActivity;
import com.avantport.avp.njpb.activity.MineNewsActivity;
import com.avantport.avp.njpb.activity.MineNumCountActivity;
import com.avantport.avp.njpb.activity.MineRepairsActivity;
import com.avantport.avp.njpb.activity.MineReturnActivity;
import com.avantport.avp.njpb.activity.MineSendActivity;
import com.avantport.avp.njpb.activity.MineServiceActivity;
import com.avantport.avp.njpb.activity.MineTakeOutActivity;
import com.avantport.avp.njpb.bean.AllWorkScorebean;
import com.avantport.avp.njpb.bean.UserInfobean;
import com.avantport.avp.njpb.bean.WorkLimitbean;
import com.avantport.avp.njpb.bean.WorkMonthScorebean;
import com.avantport.avp.njpb.constant.Constant;
import com.avantport.avp.njpb.okhttp.OkHttpBaseCallback;
import com.avantport.avp.njpb.okhttp.OkHttpUtils;
import com.avantport.avp.njpb.ui.base.LoginActivity;
import com.avantport.avp.njpb.ui.base.MainActivity;
import com.avantport.avp.njpb.ui.mine.AboutUsActivity;
import com.avantport.avp.njpb.ui.mine.MineWorkScoreActivity;
import com.avantport.avp.njpb.ui.mine.UserInfoActivity;
import com.avantport.avp.njpb.ui.mine.WorkScoreDetailsActivity;
import com.avantport.avp.njpb.uitls.DateUtil;
import com.avantport.avp.njpb.uitls.SpUtil;
import com.avantport.avp.njpb.uitls.ToastUtil;
import com.avantport.avp.njpb.view.CircleImageView;
import com.avantport.avp.njpb.view.HintDialog;

import java.util.List;

import okhttp3.Response;

import static com.avantport.avp.njpb.R.menu.mine;


/**
 * Created by avp on 2017-08-02.
 * 我的界面
 */

public class MineFragment extends BaseFragment implements View.OnClickListener {

    private ImageView mMine_menu;
    private CircleImageView mMine_headpic;
    private TextView mMine_name;
    private TextView mMine_company;
    private TextView mMine_job;
    private LinearLayout mMine_click_all;
    private TextView mMine_allgf;
    private LinearLayout mMine_click_every;
    private TextView mMine_everygf;
    private LinearLayout mMine_msg;
    private LinearLayout mMine_repairs;
    private LinearLayout mMine_service;
    private LinearLayout mMine_keep;
    private LinearLayout mMine_takein;
    private LinearLayout mMine_takeout;
    private LinearLayout mMine_numcount;
    private LinearLayout mMine_countcheck;
    private LinearLayout mMine_hava;
    private LinearLayout mMine_sendorder;
    private RelativeLayout mUserInfo;
    private WorkLimitbean mUserWorkLimit;
    private List<WorkLimitbean.ResultsBean> mResults;
    private LinearLayout mMineBreakDown;

    @Override
    protected View getFragmentView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.fragment_mine_view, container, false);
    }

    @Override
    protected void initDatas() {
        getUserInfo();
        getWorkLimit();
        showWorkLimit();
       /* getAllWorkScore();//获得总共分*/
        getMonthWorkScore();
    }

    private void getAllWorkScore() {//获得总的工分
        OkHttpUtils.getInstance().get(Constant.WORK_SCORE, null, null, new OkHttpBaseCallback<AllWorkScorebean>() {
            @Override
            public void onSuccess(Response response, AllWorkScorebean allWorkScorebean) {
                Double result = allWorkScorebean.getResult();//获得总共分
                if (result != null) {
                    mMine_allgf.setText(result + "");
                }
            }

            @Override
            public void onError(Response response, int code, Exception e) {

            }
        });

    }

    private void getMonthWorkScore() {//获得当月工分
        OkHttpUtils.getInstance().get(Constant.WORK_MONTH_SCORE, null, null, new OkHttpBaseCallback<WorkMonthScorebean>() {
            @Override
            public void onSuccess(Response response, WorkMonthScorebean workMonthScorebean) {
                List<WorkMonthScorebean.ResultsBean> results = workMonthScorebean.getResults();
                if (results != null && results.size() != 0) {
                    //获得每个月的工分
                    showCurrentScore(results);
                    showAllScore(results);
                }
            }

            @Override
            public void onError(Response response, int code, Exception e) {

            }
        });
    }

    private void showAllScore(List<WorkMonthScorebean.ResultsBean> results) {
        double allScore = 0;
        for (int i = 0; i < results.size(); i++) {
            WorkMonthScorebean.ResultsBean resultsBean = results.get(i);
            Double workOrderScoreCount = resultsBean.getWorkOrderScoreCount();
            if (workOrderScoreCount!=null) {
                allScore +=workOrderScoreCount;
            }
        }
        mMine_allgf.setText((int) allScore+"");
    }

    private void showCurrentScore(List<WorkMonthScorebean.ResultsBean> results) {//展示当前月的工分
        WorkMonthScorebean.ResultsBean resultsBean = results.get(results.size() - 1);
        String scoreMonth = resultsBean.getScoreMonth();
        String currentMonth = DateUtil.getCurrentMonth();
        if (TextUtils.equals(scoreMonth, currentMonth)) {
            if (resultsBean.getWorkOrderScoreCount()!=0) {
                mMine_everygf.setText((int) resultsBean.getWorkOrderScoreCount() + "");
            }else{
                mMine_everygf.setText("0");
            }
        } else {
            mMine_everygf.setText("0");
        }

    }

    private void showWorkLimit() {
        for (int i = 0; i < mResults.size(); i++) {
            String resourceCode = mResults.get(i).getResourceCode();
            if (TextUtils.equals(resourceCode, "NJPB-APP-WORK-MINE-MESSAGE")) {
                mMine_msg.setVisibility(View.VISIBLE);
            } else if (TextUtils.equals(resourceCode, "NJPB-APP-WORK-MINE-REPORT")) {
                mMine_repairs.setVisibility(View.VISIBLE);
            } else if (TextUtils.equals(resourceCode, "NJPB-APP-WORK-MINE-ASSIGN")) {
                mMine_sendorder.setVisibility(View.VISIBLE);
            } else if (TextUtils.equals(resourceCode, "NJPB-APP-WORK-MINE-REPAIR")) {
                mMine_service.setVisibility(View.VISIBLE);
            } else if (TextUtils.equals(resourceCode, "NJPB-APP-WORK-MINE-MAINTENANCE")) {
                mMine_keep.setVisibility(View.VISIBLE);
            } else if (TextUtils.equals(resourceCode, "NJPB-APP-WORK-MINE-APPLYPICKING")) {
                mMine_takeout.setVisibility(View.VISIBLE);
            } else if (TextUtils.equals(resourceCode, "NJPB-APP-WORK-MINE-BACK")) {
                mMine_takein.setVisibility(View.VISIBLE);
            } else if (TextUtils.equals(resourceCode, "NJPB-APP-WORK-MINE-STOCK")) {
                mMine_hava.setVisibility(View.VISIBLE);
            } else if (TextUtils.equals(resourceCode, "NJPB-APP-WORK-MINE-APPLYCHECK")) {
                mMine_countcheck.setVisibility(View.VISIBLE);
            } else if (TextUtils.equals(resourceCode, "NJPB-APP-WORK-MINE-STOCKCHECK")) {
                mMine_numcount.setVisibility(View.VISIBLE);
            }
        }
    }

    private void getWorkLimit() {//工作界面的限制
        mUserWorkLimit = SpUtil.getObjFromSp("userWorkLimit");
        mResults = mUserWorkLimit.getResults();
    }

    private void getUserInfo() {
        UserInfobean userInfobean = SpUtil.getObjFromSp("userInfobean");
        mMine_name.setText(userInfobean.getResult().getUserName());
        mMine_company.setText(userInfobean.getResult().getCorpName());
        mMine_job.setText(userInfobean.getResult().getRoleNames());
    }

    @Override
    protected void getFragmentData(View view) {
        mMine_menu = (ImageView) view.findViewById(R.id.mine_menu);
        mMine_headpic = (CircleImageView) view.findViewById(R.id.mine_headpic);//头像
        mMine_name = (TextView) view.findViewById(R.id.mine_name);//名字
        mMine_company = (TextView) view.findViewById(R.id.mine_company);//所属公司
        mMine_job = (TextView) view.findViewById(R.id.mine_job);//角色

        mMine_click_all = (LinearLayout) view.findViewById(R.id.mine_click_all);//总共分
        mMine_allgf = (TextView) view.findViewById(R.id.mine_allgf);//总共分
        mMine_click_every = (LinearLayout) view.findViewById(R.id.mine_click_every);//当月工分
        mMine_everygf = (TextView) view.findViewById(R.id.mine_everygf);//月工分

        mMine_msg = (LinearLayout) view.findViewById(R.id.mine_msg);
        mMine_repairs = (LinearLayout) view.findViewById(R.id.mine_repairs);
        mMine_service = (LinearLayout) view.findViewById(R.id.mine_service);
        mMine_keep = (LinearLayout) view.findViewById(R.id.mine_keep);
        mMine_takein = (LinearLayout) view.findViewById(R.id.mine_takein);
        mMine_takeout = (LinearLayout) view.findViewById(R.id.mine_takeout);
        mMine_numcount = (LinearLayout) view.findViewById(R.id.mine_numcount);
        mMine_countcheck = (LinearLayout) view.findViewById(R.id.mine_countcheck);
        mMine_hava = (LinearLayout) view.findViewById(R.id.mine_have);
        mMine_sendorder = (LinearLayout) view.findViewById(R.id.mine_sendorder);
        mUserInfo = (RelativeLayout) view.findViewById(R.id.user_info);//用户信息
        mMineBreakDown = (LinearLayout) view.findViewById(R.id.mine_breakDown);
        //设置点击事件
        mMine_menu.setOnClickListener(this);
        mMine_click_all.setOnClickListener(this);
        mMine_click_every.setOnClickListener(this);
        mMine_msg.setOnClickListener(this);
        mMine_repairs.setOnClickListener(this);
        mMine_service.setOnClickListener(this);
        mMine_keep.setOnClickListener(this);
        mMine_takein.setOnClickListener(this);
        mMine_takeout.setOnClickListener(this);
        mMine_numcount.setOnClickListener(this);
        mMine_countcheck.setOnClickListener(this);
        mMine_hava.setOnClickListener(this);
        mMine_sendorder.setOnClickListener(this);
        mUserInfo.setOnClickListener(this);
        mMineBreakDown.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.mine_menu:
                //右上角菜单，点击弹popuMenu
                showPopupMenu(mMine_menu);
                break;
            case R.id.mine_click_all://总共分
                getAllWorkoScore();
                break;
            case R.id.mine_click_every://当月工分
                getCurrentWorkScore();
                break;
            case R.id.mine_msg:
                /*startActivity(new Intent(mContext, MineNewsActivity.class));
                activityJumpAnim();*/
                startActivity(MineNewsActivity.class);
                break;
            case R.id.mine_repairs:
                /*startActivity(new Intent(mContext, MineRepairsActivity.class));
                activityJumpAnim();*/
                startActivity(MineRepairsActivity.class);
                break;
            case R.id.mine_sendorder:
                //我的派单
              /*  startActivity(new Intent(mContext, MineSendActivity.class));
                activityJumpAnim();*/
                startActivity(MineSendActivity.class);
                break;
            case R.id.mine_service:
              /*  startActivity(new Intent(mContext, MineServiceActivity.class));
                activityJumpAnim();*/
                startActivity(MineServiceActivity.class);
                break;
            case R.id.mine_keep:
              /*  startActivity(new Intent(mContext, MineKeepActivity.class));
                activityJumpAnim();*/
                startActivity(MineKeepActivity.class);
                break;
            case R.id.mine_takein:
               /* startActivity(new Intent(mContext, MineReturnActivity.class));
                activityJumpAnim();*/
                startActivity(MineReturnActivity.class);
                break;
            case R.id.mine_takeout:
                /*startActivity(new Intent(mContext, MineTakeOutActivity.class));
                activityJumpAnim();*/
                startActivity(MineTakeOutActivity.class);
                break;
            case R.id.mine_numcount:
               /* startActivity(new Intent(mContext, MineNumCountActivity.class));
                activityJumpAnim();*/
                startActivity(MineNumCountActivity.class);
                break;
            case R.id.mine_countcheck:
               /* startActivity(new Intent(mContext, MineCountCheckActivity.class));
                activityJumpAnim();*/
                startActivity(MineCountCheckActivity.class);
                break;
            case R.id.mine_have:
                //我的库存
               /* startActivity(new Intent(mContext, MineHaveActivity.class));
                activityJumpAnim();*/
                startActivity(MineHaveActivity.class);
                break;
            case R.id.user_info://跳转用户信息详情
                /*startActivity(new Intent(mContext, UserInfoActivity.class));
                activityJumpAnim();*/
                startActivity(UserInfoActivity.class);
                break;
            // TODO: 2017/11/8 报废
//            case R.id.mine_breakDown:
//                //跳转我的报废
//                break;
        }
    }

    private void getCurrentWorkScore() {
       // startActivity(new Intent(mContext, WorkScoreDetailsActivity.class));
        String s = mMine_everygf.getText().toString();
        if (TextUtils.equals(s, "0")) {
            ToastUtil.show(mContext, "您当月工分为零");
            return;
        } else {
            Intent intent = new Intent(mContext, WorkScoreDetailsActivity.class);
            intent.putExtra("CurrentMonth",DateUtil.getCurrentMonth());
            intent.putExtra("WorkScore",s);
            startActivity(intent);
            activityJumpAnim();
        }
    }

    private void getAllWorkoScore() {//获得总工分
        String s = mMine_allgf.getText().toString();
        if (TextUtils.equals(s, "0")) {
            ToastUtil.show(mContext, "您当前总工分为零");
            return;
        } else {
            Intent intent = new Intent(mContext, MineWorkScoreActivity.class);
            intent.putExtra("WorkScore",s);
            startActivity(intent);
            activityJumpAnim();
        }
        //startActivity(new Intent(mContext, MineWorkScoreActivity.class));
    }

    //弹出popumenu
    private void showPopupMenu(ImageView mine_menu) {
        PopupMenu popupMenu = new PopupMenu(mContext, mine_menu);
        //设置弹出菜单的布局
        popupMenu.getMenuInflater().inflate(mine, popupMenu.getMenu());

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.aboutme:
                       /* startActivity(new Intent(mContext, AboutUsActivity.class));
                        activityJumpAnim();*/
                       startActivity(AboutUsActivity.class);
                        break;
                    case R.id.exitus:
                        exitApp();
                        break;
                }
                return true;
            }
        });
        popupMenu.show();

    }

    private void exitApp() {
        //退出app;
        final HintDialog hintDialog = new HintDialog(mContext, "退出当前用户吗？");
        hintDialog.setOnClickListaner(new HintDialog.onClickListaner() {
            @Override
            public void clickCancel() {
                hintDialog.dismiss();
            }

            @Override
            public void clickSure() {
                startActivity(new Intent(mContext, LoginActivity.class));
                hintDialog.dismiss();
                ((MainActivity) getActivity()).finish();

            }
        });
        hintDialog.show();

    }


}

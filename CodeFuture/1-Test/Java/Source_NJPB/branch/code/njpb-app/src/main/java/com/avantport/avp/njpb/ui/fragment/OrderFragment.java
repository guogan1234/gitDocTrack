package com.avantport.avp.njpb.ui.fragment;

import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.avantport.avp.njpb.R;
import com.avantport.avp.njpb.bean.WorkLimitbean;
import com.avantport.avp.njpb.ui.order.OrderLeftFragment;
import com.avantport.avp.njpb.ui.order.OrderRightFragment;
import com.avantport.avp.njpb.ui.work.BikeServingActivity;
import com.avantport.avp.njpb.ui.work.StationServingActivity;
import com.avantport.avp.njpb.uitls.SpUtil;

import java.util.List;


/**
 * Created by avp on 2017-08-02.
 * 工单界面
 */

public class OrderFragment extends BaseFragment implements View.OnClickListener {


    private TextView mBtnLeft;
    private TextView mBtnRight;
    private ImageView mHeadQrcode;
    private OrderLeftFragment mOrderLeftFragment;
    private OrderRightFragment mOrderRightFragment;
    private WorkLimitbean mUserWorkLimit;
    private List<WorkLimitbean.ResultsBean> mResults;

    @Override
    protected View getFragmentView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.fragment_order_view, container, false);
    }

    @Override
    protected void initDatas() {
        mHeadQrcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopuMenu(mHeadQrcode);
            }
        });
        mBtnLeft.setOnClickListener(this);
        mBtnRight.setOnClickListener(this);
        getWorkLimit();
        firstSelect();
    }

    private void getWorkLimit() {
        mUserWorkLimit = SpUtil.getObjFromSp("userWorkLimit");
        mResults = mUserWorkLimit.getResults();
        for (int i = 0; i < mResults.size(); i++) {
            if (mResults.get(i).getResourceCode().equals("NJPB-APP-WORK-FAULTREPORT")) {
                mHeadQrcode.setVisibility(View.VISIBLE);
                return;
            }
        }
    }

    private void firstSelect() {
        mBtnLeft.setSelected(true);
        mOrderRightFragment = new OrderRightFragment();
        mOrderLeftFragment = new OrderLeftFragment();
        getChildFragmentManager().beginTransaction().add(R.id.fragment, mOrderRightFragment)
                .add(R.id.fragment, mOrderLeftFragment)
                .hide(mOrderRightFragment).commitAllowingStateLoss();
    }


    @Override
    protected void getFragmentData(View view) {
        mHeadQrcode = (ImageView) view.findViewById(R.id.head_qrcode);
        mHeadQrcode.setImageResource(R.mipmap.order_more);
        mHeadQrcode.setVisibility(View.INVISIBLE);
        ImageView headDivide = (ImageView) view.findViewById(R.id.head_goback);
        headDivide.setImageResource(R.mipmap.bicycle);
        TextView headTitle = (TextView) view.findViewById(R.id.head_title);
        headTitle.setText("工单");
        mBtnLeft = (TextView) view.findViewById(R.id.btn_left);
        mBtnRight = (TextView) view.findViewById(R.id.btn_right);
    }

    @Override
    public void onClick(View v) {
        selectAll();
        android.support.v4.app.FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
        hideAllFragment(fragmentTransaction);
        switch (v.getId()) {
            case R.id.btn_left://待处理
                mBtnLeft.setSelected(true);
                mOrderLeftFragment.refreshOrderFragment();
                fragmentTransaction.show(mOrderLeftFragment);
                break;
            case R.id.btn_right: //处理中
                mBtnRight.setSelected(true);
                mOrderRightFragment.refreshOrderFragment();
                fragmentTransaction.show(mOrderRightFragment);
                break;
        }
        fragmentTransaction.commit();
    }

    private void hideAllFragment(FragmentTransaction transaction) {
        if (mOrderRightFragment != null) {
            transaction.hide(mOrderRightFragment);
        }
        if (mOrderLeftFragment != null) {
            transaction.hide(mOrderLeftFragment);
        }
    }

    //全部设置没有选中
    public void selectAll() {
        mBtnLeft.setSelected(false);
        mBtnRight.setSelected(false);
    }

    private void showPopuMenu(ImageView order_menu) {
        //弹出菜单提示框
        PopupMenu popupMenu = new PopupMenu(mContext, order_menu);
        //设置弹出菜单的布局
        popupMenu.getMenuInflater().inflate(R.menu.order, popupMenu.getMenu());

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.station_repairs:
                       /* startActivity(new Intent(mContext, StationServingActivity.class));
                        activityJumpAnim();*/
                       startActivity(StationServingActivity.class);
                        break;
                    case R.id.blke_repairs:
                      /* startActivity(new Intent(mContext, BikeServingActivity.class));
                        activityJumpAnim();*/
//                        ToastUtil.show(mContext,"没有读卡设备！");
                        startActivity(BikeServingActivity.class);
                        break;
                }
                return true;
            }
        });
        popupMenu.show();
    }
}

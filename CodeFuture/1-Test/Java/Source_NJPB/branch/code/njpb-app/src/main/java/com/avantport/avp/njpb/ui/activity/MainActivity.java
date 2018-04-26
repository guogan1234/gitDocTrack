package com.avantport.avp.njpb.ui.activity;

import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.avantport.avp.njpb.R;
import com.avantport.avp.njpb.ui.fragment.MineFragment;
import com.avantport.avp.njpb.ui.fragment.OrderFragment;
import com.avantport.avp.njpb.ui.fragment.WorkFragment;

public class MainActivity extends BaseActivity implements View.OnClickListener {
    private FrameLayout mFragment;
    private LinearLayout mButton_order;
    private LinearLayout mButton_work;
    private LinearLayout mButton_mine;
    private TextView mOrder_count;
    private OrderFragment mOrderFragment;
    private WorkFragment mWorkFragment;
    private MineFragment mMineFragment;
    @Override
    protected int getLayoutRes() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        mFragment = (FrameLayout) findViewById(R.id.fragment);
        mButton_order = (LinearLayout) findViewById(R.id.button_order);
        mButton_work = (LinearLayout) findViewById(R.id.button_work);
        mButton_mine = (LinearLayout) findViewById(R.id.button_mine);
        mOrder_count = (TextView) findViewById(R.id.order_count);
    }

    @Override
    protected void initDatas() {
        mButton_mine.setOnClickListener(this);
        mButton_order.setOnClickListener(this);
        mButton_work.setOnClickListener(this);

        firstSelect();
    }

    //进入首页默认加载第一页
    public void firstSelect(){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        mButton_order.setSelected(true);
        if (mOrderFragment==null) {
            mOrderFragment = new OrderFragment();
            transaction.add(R.id.fragment, mOrderFragment);
        }
        transaction.commit();
    }

    //全部设置没有选中
    public void selectAll(){
        mButton_order.setSelected(false);
        mButton_work.setSelected(false);
        mButton_mine.setSelected(false);
    }

    //隐藏所有fragment
    public void hideAllFragment(FragmentTransaction transaction){
        if (mOrderFragment!=null) {
            transaction.hide(mOrderFragment);
        }
        if (mWorkFragment!=null) {
            transaction.hide(mWorkFragment);
        }
        if (mMineFragment!=null) {
            transaction.hide(mMineFragment);
        }

    }

    @Override
    public void onClick(View v) {
        selectAll();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        hideAllFragment(transaction);
        switch (v.getId()) {
            case R.id.button_order:
                mButton_order.setSelected(true);
                if (mOrderFragment==null) {
                    mOrderFragment = new OrderFragment();
                    transaction.add(R.id.fragment,mOrderFragment);
                }else{
                    transaction.show(mOrderFragment);
                }

                break;
            case R.id.button_work:
                mButton_work.setSelected(true);
                if (mWorkFragment==null) {
                    mWorkFragment = new WorkFragment();
                    transaction.add(R.id.fragment,mWorkFragment);
                }else{
                    transaction.show(mWorkFragment);
                }
                break;
            case R.id.button_mine:
                mButton_mine.setSelected(true);
                if (mMineFragment==null) {
                    mMineFragment = new MineFragment();
                    transaction.add(R.id.fragment,mMineFragment);
                }else{
                    transaction.show(mMineFragment);
                }
                break;
        }
        transaction.commit();
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}

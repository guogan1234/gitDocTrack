package com.avantport.avp.njpb.activity;

import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.avantport.avp.njpb.R;
import com.avantport.avp.njpb.ui.base.BaseActivity;
import com.avantport.avp.njpb.ui.fragment.BaseFragment;

/**
 * Created by len on 2017/8/17.
 */

public abstract class MineBaseActivity extends BaseActivity implements View.OnClickListener {
    public TextView mHead_title;
    public ImageView mHead_goback;
    public ImageView mHead_qrcode;
    public TextView mBtn_left;
    public TextView mBtn_right;
    public FrameLayout mFramelayout;
    private BaseFragment mLeftFragment;
    private BaseFragment mRightFragment;


    @Override
    protected int getLayoutRes() {
        return R.layout.activity_mine_take_out;
    }

    @Override
    protected void initView() {
        mHead_title = (TextView) findViewById(R.id.head_title);
        mHead_goback = (ImageView) findViewById(R.id.head_goback);
        mHead_qrcode = (ImageView) findViewById(R.id.head_qrcode);
        mHead_title.setText(getTitleString());
        mHead_qrcode.setImageResource(getTitleImg());
        mBtn_left = (TextView) findViewById(R.id.btn_left);
        mBtn_right = (TextView) findViewById(R.id.btn_right);
        mFramelayout = (FrameLayout) findViewById(R.id.framelayout);
        mBtn_left.setText(getBtnLeft());
        mBtn_right.setText(getBtnRight());
    }

    protected abstract String getTitleString();

    protected abstract int getTitleImg();

    protected abstract String getBtnRight();

    protected abstract String getBtnLeft();

    @Override
    protected void initDatas() {
        mHead_goback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mHead_qrcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selsectTitleImg();
            }
        });
        mBtn_left.setOnClickListener(this);
        mBtn_right.setOnClickListener(this);
        mLeftFragment = getLeftFragment();
        mRightFragment = getRightFragment();

        firstSelect();

    }

    public void firstSelect() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        mBtn_left.setSelected(true);
        if (mLeftFragment != null) {//进行隐藏和显示
            transaction.add(R.id.framelayout, mLeftFragment)
                      .add(R.id.framelayout, mRightFragment)
                    .hide(mRightFragment);
        }
        transaction.commit();
    }

    protected abstract BaseFragment getRightFragment();

    protected abstract BaseFragment getLeftFragment();

    @Override
    public void onClick(View v) {
        mBtn_left.setSelected(false);
        mBtn_right.setSelected(false);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
       hideAllFragment(fragmentTransaction);

        switch (v.getId()) {
            case R.id.btn_left:
                mBtn_left.setSelected(true);
                if (mLeftFragment!=null) {
                    fragmentTransaction.show(mLeftFragment);
                }
                break;
            case R.id.btn_right:
                mBtn_right.setSelected(true);
               if (mRightFragment!=null){
                    fragmentTransaction.show(mRightFragment);
                }
                break;
        }

        fragmentTransaction.commit();
    }

    protected abstract void selsectTitleImg();

    @Override
    public void onBackPressed() {
        finish();
    }

    //隐藏所有fragment
    public void hideAllFragment(FragmentTransaction transaction){
        if (mLeftFragment!=null) {
            transaction.hide(mLeftFragment);
        }
        if (mRightFragment!=null) {
            transaction.hide(mRightFragment);
        }

    }
}

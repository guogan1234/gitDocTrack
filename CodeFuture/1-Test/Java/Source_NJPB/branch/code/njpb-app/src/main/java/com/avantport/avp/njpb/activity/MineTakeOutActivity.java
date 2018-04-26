package com.avantport.avp.njpb.activity;

import android.view.View;

import com.avantport.avp.njpb.R;
import com.avantport.avp.njpb.fragment.TakeOutApplyFragment;
import com.avantport.avp.njpb.fragment.TakeOutFinishFragment;
import com.avantport.avp.njpb.ui.fragment.BaseFragment;

/**
 * 我的领料 2017/8/16
 *
 * */
public class MineTakeOutActivity extends MineBaseActivity {
    @Override
    protected String getBtnRight() {
        return "已完成";
    }

    @Override
    protected String getBtnLeft() {
        mHead_qrcode.setVisibility(View.INVISIBLE);
        return "申请中";
    }

    @Override
    protected BaseFragment getRightFragment() {
        return new TakeOutFinishFragment();
    }

    @Override
    protected BaseFragment getLeftFragment() {
        return new TakeOutApplyFragment();
    }

    @Override
    protected int getTitleImg() {
        return R.mipmap.order_more;
    }

    @Override
    protected String getTitleString() {
        return "我的领料";
    }

    @Override
    protected void selsectTitleImg() {
        //startActivity(new Intent(this, TakeOutActivity.class));
    }
}

package com.avantport.avp.njpb.ui.activity;

import android.view.View;

import com.avantport.avp.njpb.R;
import com.avantport.avp.njpb.activity.MineBaseActivity;
import com.avantport.avp.njpb.ui.fragment.BaseFragment;
import com.avantport.avp.njpb.ui.fragment.WorkReturnCheckFragment;
import com.avantport.avp.njpb.ui.fragment.WorkTakeCheckFragment;

public class CheckApplyActivity extends MineBaseActivity {

    @Override
    protected String getTitleString() {
        return "物料审核";
    }

    @Override
    protected int getTitleImg() {
        return R.mipmap.order_more;
    }

    @Override
    protected String getBtnRight() {
        mHead_qrcode.setVisibility(View.INVISIBLE);
        return "归还审核";
    }

    @Override
    protected String getBtnLeft() {
        return "领料审核";
    }

    @Override
    protected BaseFragment getRightFragment() {
        return new WorkReturnCheckFragment();
    }

    @Override
    protected BaseFragment getLeftFragment() {
        return new WorkTakeCheckFragment();
    }

    @Override
    protected void selsectTitleImg() {

    }
}

package com.avantport.avp.njpb.activity;

import android.view.View;

import com.avantport.avp.njpb.R;
import com.avantport.avp.njpb.fragment.ReturnApplyFragment;
import com.avantport.avp.njpb.fragment.ReturnFinishFragment;
import com.avantport.avp.njpb.ui.fragment.BaseFragment;

public class MineReturnActivity extends MineBaseActivity {


    @Override
    protected String getTitleString() {
        return getString(R.string.mine_return);
    }

    @Override
    protected int getTitleImg() {
        return R.mipmap.order_more;
    }

    @Override
    protected String getBtnRight() {
        mHead_qrcode.setVisibility(View.INVISIBLE);
        return getString(R.string.finished);
    }

    @Override
    protected String getBtnLeft() {
        return getString(R.string.applying);
    }

    @Override
    protected BaseFragment getRightFragment() {
        return new ReturnFinishFragment();
    }

    @Override
    protected BaseFragment getLeftFragment() {
        return new ReturnApplyFragment();
    }

    @Override
    protected void selsectTitleImg() {

    }
}

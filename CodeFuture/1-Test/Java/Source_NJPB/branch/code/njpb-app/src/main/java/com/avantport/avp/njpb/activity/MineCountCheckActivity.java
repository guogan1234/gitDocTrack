package com.avantport.avp.njpb.activity;

import android.view.View;

import com.avantport.avp.njpb.R;
import com.avantport.avp.njpb.fragment.CheckReturnFragment;
import com.avantport.avp.njpb.fragment.CheckTakeFragment;
import com.avantport.avp.njpb.ui.fragment.BaseFragment;

public class MineCountCheckActivity extends MineBaseActivity {


    @Override
    protected String getTitleString() {
        return getString(R.string.mine_check);
    }

    @Override
    protected int getTitleImg() {
        return R.mipmap.order_more;
    }

    @Override
    protected String getBtnRight() {
        mHead_qrcode.setVisibility(View.INVISIBLE);
        return getString(R.string.returned);
    }

    @Override
    protected String getBtnLeft() {
        return getString(R.string.takeout);
    }

    @Override
    protected BaseFragment getRightFragment() {
        return new CheckReturnFragment();
    }

    @Override
    protected BaseFragment getLeftFragment() {
        return new CheckTakeFragment();
    }

    @Override
    protected void selsectTitleImg() {

    }
}

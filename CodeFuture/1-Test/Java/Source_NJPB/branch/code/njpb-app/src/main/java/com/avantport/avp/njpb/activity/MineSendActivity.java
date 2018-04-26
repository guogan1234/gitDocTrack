package com.avantport.avp.njpb.activity;

import android.view.View;

import com.avantport.avp.njpb.R;
import com.avantport.avp.njpb.fragment.SendBikeFragment;
import com.avantport.avp.njpb.fragment.SendStationFragment;
import com.avantport.avp.njpb.ui.fragment.BaseFragment;
/**
 * 我的派单
 * */
public class MineSendActivity extends MineBaseActivity {


    @Override
    protected String getTitleString() {
        return getString(R.string.mine_send);
    }

    @Override
    protected int getTitleImg() {
        return R.mipmap.order_more;
    }

    @Override
    protected String getBtnRight() {
        mHead_qrcode.setVisibility(View.INVISIBLE);
        return getString(R.string.bike_service);
    }

    @Override
    protected String getBtnLeft() {
        return getString(R.string.station_service);
    }

    @Override
    protected BaseFragment getRightFragment() {
        return new SendBikeFragment();
    }

    @Override
    protected BaseFragment getLeftFragment() {
        return new SendStationFragment();
    }

    @Override
    protected void selsectTitleImg() {

    }
}

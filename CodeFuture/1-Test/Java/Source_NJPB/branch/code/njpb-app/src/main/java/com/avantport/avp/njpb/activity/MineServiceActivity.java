package com.avantport.avp.njpb.activity;

import android.view.View;

import com.avantport.avp.njpb.R;
import com.avantport.avp.njpb.fragment.ServiceBikeFragment;
import com.avantport.avp.njpb.fragment.ServiceStationFragment;
import com.avantport.avp.njpb.ui.fragment.BaseFragment;
/**
 * 我的维修
 * */
public class MineServiceActivity extends MineBaseActivity {


    @Override
    protected String getTitleString() {
        return getString(R.string.mine_service);
    }

    @Override
    protected int getTitleImg() {
        return R.mipmap.order_more;
    }

    @Override
    protected String getBtnRight() {
        mHead_qrcode.setVisibility(View.INVISIBLE);
        return getString(R.string.bike_repairs);
    }

    @Override
    protected String getBtnLeft() {
        return getString(R.string.station_repairs);
    }

    @Override
    protected BaseFragment getRightFragment() {
        return new ServiceBikeFragment();
    }

    @Override
    protected BaseFragment getLeftFragment() {
        return new ServiceStationFragment();
    }

    @Override
    protected void selsectTitleImg() {

    }
}

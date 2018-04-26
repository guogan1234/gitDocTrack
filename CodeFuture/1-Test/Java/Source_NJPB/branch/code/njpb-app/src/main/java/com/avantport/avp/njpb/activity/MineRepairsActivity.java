package com.avantport.avp.njpb.activity;

import android.view.View;

import com.avantport.avp.njpb.R;
import com.avantport.avp.njpb.fragment.RepairsBikeFragment;
import com.avantport.avp.njpb.fragment.RepairsStationFragment;
import com.avantport.avp.njpb.ui.fragment.BaseFragment;

public class MineRepairsActivity extends MineBaseActivity {


    @Override
    protected String getTitleString() {
        return getString(R.string.mine_repairs);
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
        return new RepairsBikeFragment();
    }

    @Override
    protected BaseFragment getLeftFragment() {
        return new RepairsStationFragment();
    }

    @Override
    protected void selsectTitleImg() {

    }
}

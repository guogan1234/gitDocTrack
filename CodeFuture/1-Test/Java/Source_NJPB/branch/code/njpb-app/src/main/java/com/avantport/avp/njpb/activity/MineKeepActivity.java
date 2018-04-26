package com.avantport.avp.njpb.activity;

import android.view.View;

import com.avantport.avp.njpb.R;
import com.avantport.avp.njpb.fragment.KeepBikeFragment;
import com.avantport.avp.njpb.fragment.KeepStationFragment;
import com.avantport.avp.njpb.ui.fragment.BaseFragment;
import com.avantport.avp.njpb.ui.work.KeepStationActivity;

public class MineKeepActivity extends MineBaseActivity {


    @Override
    protected String getTitleString() {
        return getString(R.string.mine_keep);
    }

    @Override
    protected int getTitleImg() {
        mHead_qrcode.setVisibility(View.INVISIBLE);
        return R.mipmap.order_more;
    }

    @Override
    protected String getBtnRight() {
        mHead_qrcode.setVisibility(View.INVISIBLE);
        return getString(R.string.bike);
    }

    @Override
    protected String getBtnLeft() {
        return getString(R.string.station);
    }

    @Override
    protected BaseFragment getRightFragment() {
        return new KeepBikeFragment();
    }

    @Override
    protected BaseFragment getLeftFragment() {
        return new KeepStationFragment();
    }

    @Override
    protected void selsectTitleImg() {
//            startActivity(new Intent(this, KeepStationActivity.class));
        startActivity(KeepStationActivity.class);
    }
}

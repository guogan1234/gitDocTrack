package com.avantport.avp.njpb.activity;

import android.content.Intent;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;

import com.avantport.avp.njpb.R;
import com.avantport.avp.njpb.fragment.HavaBikeFragment;
import com.avantport.avp.njpb.fragment.HavePartsFragment;
import com.avantport.avp.njpb.ui.fragment.BaseFragment;
import com.avantport.avp.njpb.ui.work.PutInWorkActivity;

/**
 * 我的库存
 *
 * */
public class MineHaveActivity extends MineBaseActivity {


    @Override
    protected String getTitleString() {
        return getString(R.string.mine_have);
    }

    @Override
    protected int getTitleImg() {
        return R.mipmap.order_more;
    }

    @Override
    protected String getBtnRight() {
        mHead_qrcode.setVisibility(View.INVISIBLE);
        return getString(R.string.bike);
    }

    @Override
    protected String getBtnLeft() {
        return getString(R.string.parts);
    }

    @Override
    protected BaseFragment getRightFragment() {
        return new HavaBikeFragment();
    }

    @Override
    protected BaseFragment getLeftFragment() {
        return new HavePartsFragment();
    }

    @Override
    protected void selsectTitleImg() {
        showPopuMenu(mHead_qrcode);
    }



    private void showPopuMenu(ImageView order_menu) {
        //弹出菜单提示框
        PopupMenu popupMenu = new PopupMenu(this, order_menu);
        //设置弹出菜单的布局
        popupMenu.getMenuInflater().inflate(R.menu.have, popupMenu.getMenu());

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.apply://// TODO: 2017/8/18  
                        //startActivity(new Intent(MineHaveActivity.this, PutInWorkActivity.class));
                        break;
                    case R.id.back:// TODO: 2017/8/18  
                        startActivity(new Intent(MineHaveActivity.this, PutInWorkActivity.class));
                        break;
                }
                return true;
            }
        });
        popupMenu.show();
    }
}

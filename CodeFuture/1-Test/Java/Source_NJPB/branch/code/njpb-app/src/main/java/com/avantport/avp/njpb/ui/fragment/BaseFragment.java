package com.avantport.avp.njpb.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.avantport.avp.njpb.R;
import com.avantport.avp.njpb.view.CustomProDialog;

/**
 * Created by len on 2017/8/3.
 */

public abstract class BaseFragment extends Fragment {

    public Context mContext;
    private CustomProDialog mCustomProDialog;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getContext();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return getFragmentView(inflater, container);
    }

    protected abstract View getFragmentView(LayoutInflater inflater, ViewGroup container);

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getFragmentData(view);

        initDatas();
    }


    protected abstract void getFragmentData(View view);

    protected abstract void initDatas();

    public void showProDialog(String msg) {
        mCustomProDialog = new CustomProDialog(mContext, msg);
        mCustomProDialog.show();

    }

    public void hideDialiog() {
        if (mCustomProDialog != null) {
            mCustomProDialog.dismiss();
        }

    }
//多层嵌套fragment使用，解决onActivityForResult回调问题
    private BaseFragment forResultChildFragment;

    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        Fragment parentFragment = getParentFragment();
        if (parentFragment != null && parentFragment instanceof BaseFragment) {
            ((BaseFragment) parentFragment).startActivityForResultFromChildFragment(intent, requestCode, this);
        } else {
            forResultChildFragment = null;
            super.startActivityForResult(intent, requestCode);
        }


    }

    private void startActivityForResultFromChildFragment(Intent intent, int requestCode, BaseFragment childFragment) {
        forResultChildFragment = childFragment;
        Fragment parentFragment = getParentFragment();
        if (parentFragment != null && parentFragment instanceof BaseFragment) {
            ((BaseFragment) parentFragment).startActivityForResultFromChildFragment(intent, requestCode, this);
        } else {
            super.startActivityForResult(intent, requestCode);
        }
    }

    @Override
    public final void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (forResultChildFragment != null) {
            forResultChildFragment.onActivityResult(requestCode, resultCode, data);
            forResultChildFragment = null;
        } else {
            onActivityResultNestedCompat(requestCode, resultCode, data);
        }
    }

    public void onActivityResultNestedCompat(int requestCode, int resultCode, Intent data) {
    }



    public void refreshOrderFragment(){}


    public void activityJumpAnim() {
        getActivity().overridePendingTransition(R.anim.base_slide_right_in, R.anim.base_slide_remain);
    }


    public void startActivity(Class<?> cls){
        Intent intent = new Intent (mContext,cls);
        startActivity(intent);
        activityJumpAnim();
    }
}

package com.avantport.avp.njpb.fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.avantport.avp.njpb.R;
import com.avantport.avp.njpb.adapter.FinalListAdapter;
import com.avantport.avp.njpb.bean.MineStockbean;
import com.avantport.avp.njpb.constant.Constant;
import com.avantport.avp.njpb.constant.UrlParam;
import com.avantport.avp.njpb.okhttp.OkHttpBaseCallback;
import com.avantport.avp.njpb.okhttp.OkHttpUtils;
import com.avantport.avp.njpb.ui.fragment.BaseFragment;
import com.avantport.avp.njpb.view.StateLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Response;

/**
 * Created by len on 2017/8/18.
 * 我的库存 车辆模块
 */

public class HavaBikeFragment extends BaseFragment implements StateLayout.OnReloadListener {
    private StateLayout mStateLayout;
    private ListView mList;
    private Map<String, Object> params = new HashMap<>();
    private List<MineStockbean.ResultsBean> datas = new ArrayList<>();//储存请求的数据
    private FinalListAdapter<MineStockbean.ResultsBean> mFinalListAdapter;

    @Override
    protected View getFragmentView(LayoutInflater inflater, ViewGroup container) {
        if (mStateLayout == null) {
            mStateLayout = new StateLayout(mContext);
        }
        return mStateLayout;
    }

    @Override
    protected void getFragmentData(View view) {
        View inflate = View.inflate(mContext, R.layout.only_list, null);
        mList = (ListView) inflate.findViewById(R.id.list);
        mStateLayout.bindSuccessView(inflate);
        mStateLayout.setOnReloadListener(this);
    }

    @Override
    protected void initDatas() {
        //获取网络数据
        params.clear();
        params.put(UrlParam.HAVE_PAPAM, UrlParam.PARAM_TYPE_TWO);
        mStateLayout.showLoadingView();
        getInDatas(Constant.MINE_STOCK, params);
    }

    private void getInDatas(String url, Map<String, Object> params) {
        OkHttpUtils.getInstance().get(url, params, null, new OkHttpBaseCallback<MineStockbean>() {
            @Override
            public void onSuccess(Response response, MineStockbean mineStockbean) {
                List<MineStockbean.ResultsBean> results = mineStockbean.getResults();
                if (results != null & results.size() == 0) {
                    mStateLayout.showEmptyView();
                }else{
                    mStateLayout.showSuccessView();
                    datas.addAll(results);
                    setIntDatas();
                }
            }

            @Override
            public void onError(Response response, int code, Exception e) {
                mStateLayout.showErrorView();
            }
        });

    }
    private void setIntDatas() {

        mFinalListAdapter = new FinalListAdapter<>(datas, R.layout.item_mine_stock, new FinalListAdapter.AdapterListener<MineStockbean.ResultsBean>() {
            @Override
            public void bindView(FinalListAdapter.FinalViewHolder finalViewHolder, MineStockbean.ResultsBean resultsBean) {
                TextView parts_name = (TextView) finalViewHolder.getViewById(R.id.parts_name);
                TextView parts_count = (TextView) finalViewHolder.getViewById(R.id.parts_count);
                parts_name.setText(resultsBean.getEstateTypeName());
                parts_count.setText(resultsBean.getCount()+"");
            }

        });
        mList.setAdapter(mFinalListAdapter);

    }

    @Override
    public void onReload() {
        params.clear();
        params.put(UrlParam.HAVE_PAPAM, UrlParam.PARAM_TYPE_TWO);
        getInDatas(Constant.MINE_STOCK, params);
    }


}

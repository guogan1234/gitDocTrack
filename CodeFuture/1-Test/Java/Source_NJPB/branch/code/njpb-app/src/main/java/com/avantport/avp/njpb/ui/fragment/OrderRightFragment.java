package com.avantport.avp.njpb.ui.fragment;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.avantport.avp.njpb.R;
import com.avantport.avp.njpb.activity.OrderBikeDetailsActivity;
import com.avantport.avp.njpb.activity.OrderStationDetailsActivity;
import com.avantport.avp.njpb.adapter.OrderMsgAdapter;
import com.avantport.avp.njpb.bean.OrderMessagebean;
import com.avantport.avp.njpb.constant.Constant;
import com.avantport.avp.njpb.okhttp.OkHttpBaseCallback;
import com.avantport.avp.njpb.okhttp.OkHttpUtils;
import com.avantport.avp.njpb.uitls.DateUtil;
import com.avantport.avp.njpb.uitls.ToastUtil;
import com.avantport.avp.njpb.view.StateLayout;
import com.avantport.avp.njpb.view.SwipeRefreshView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Response;

/**
 * Created by len on 2017/8/28.
 *工单处理中
 */

public class OrderRightFragment extends BaseFragment implements SwipeRefreshView.OnLoadMoreListener, SwipeRefreshLayout.OnRefreshListener, StateLayout.OnReloadListener, AdapterView.OnItemClickListener {
    private StateLayout mStateLayout;
    private SwipeRefreshView mRefresh;
    private ListView mList;
    private Map<String, Object> params = new HashMap<>();
    private List<OrderMessagebean.ResultsBean> datas = new ArrayList<>();
    private boolean isRefresh;
    private boolean isLoad;
    private OrderMsgAdapter mMsgAdapter;

    @Override
    protected View getFragmentView(LayoutInflater inflater, ViewGroup container) {
        if (mStateLayout == null) {
            mStateLayout = new StateLayout(mContext);
        }
        return mStateLayout;
    }

    @Override
    protected void getFragmentData(View view) {
        View inflate = View.inflate(mContext, R.layout.refresh_list, null);
        mRefresh = (SwipeRefreshView) inflate.findViewById(R.id.refresh);
        mList = (ListView) inflate.findViewById(R.id.list);
        mStateLayout.bindSuccessView(inflate);
        mRefresh.setOnLoadMoreListener(this);
        mRefresh.setOnRefreshListener(this);
        mStateLayout.setOnReloadListener(this);
        mList.setOnItemClickListener(this);
    }

    @Override
    protected void initDatas() {
        params.clear();
        params.put("operationTime", DateUtil.getTodayDate());
        mStateLayout.showLoadingView();
        getNetData(Constant.ORDER_MESSAGE_HANDLE_LOADMORE, params);
    }

    private void getNetData(String url, Map<String, Object> params) {
        OkHttpUtils.getInstance().get(url, params, null, new OkHttpBaseCallback<OrderMessagebean>() {

            @Override
            public void onSuccess(Response response, OrderMessagebean orderMessagebean) {
                List<OrderMessagebean.ResultsBean> results = orderMessagebean.getResults();
                if (results != null & results.size() == 0) {
                    if (isRefresh) {
                        ToastUtil.show(mContext, "没有更新的数据了");
                        isRefresh = false;
                        mRefresh.setRefreshing(false);
                    } else if (isLoad) {
                        ToastUtil.show(mContext, "没有更多的数据了");
                        isLoad = false;
                        mRefresh.setLoading(false);
                    } else {
                        mStateLayout.showEmptyView();
                    }
                } else {
                    mStateLayout.showSuccessView();
                    if (isRefresh) {
                        datas.addAll(0, results);
                        mRefresh.setRefreshing(false);
                        isRefresh = false;
                    } else if (isLoad) {
                        datas.addAll(results);
                        mRefresh.setLoading(false);
                        isLoad = false;
                    } else {
                        datas.addAll(results);
                    }
                    setIntDatas();
                    mMsgAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onError(Response response, int code, Exception e) {
                //还要根据code值进行细化提示操作
                if (isRefresh) {
                    ToastUtil.show(mContext, "网络异常");
                    mRefresh.setRefreshing(false);
                    isRefresh = false;
                } else if (isLoad) {
                    ToastUtil.show(mContext, "网络异常");
                    mRefresh.setLoading(false);
                    isLoad = false;
                } else {
                    mStateLayout.showErrorView();
                }
            }
        });
    }

    private void setIntDatas() {
        mMsgAdapter = new OrderMsgAdapter(datas,mContext);
        mList.setAdapter(mMsgAdapter);
    }

    @Override
    public void onLoadMore() {
        if (datas != null & datas.size() > 0) {
            long createTime = datas.get(datas.size() - 1).getLastUpdateTime();
            params.clear();
            params.put("operationTime", DateUtil.formatDate(createTime));
            isLoad = true;
            getNetData(Constant.ORDER_MESSAGE_HANDLE_LOADMORE, params);
        } else {
            return;
        }
    }

    @Override
    public void onRefresh() {
        //获取列表的第一条信息的时间
        if (datas != null & datas.size() > 0) {
            long createTime = datas.get(0).getLastUpdateTime();
            params.clear();
            params.put("operationTime", DateUtil.formatDate(createTime));
            isRefresh = true;
            getNetData(Constant.ORDER_MESSAGE_HANDLE_REFRESH, params);
        } else {
            return;
        }
    }

    @Override
    public void onReload() {
        params.clear();
        params.put("operationTime", DateUtil.getTodayDate());
        getNetData(Constant.ORDER_MESSAGE_HANDLE_LOADMORE, params);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        OrderMessagebean.ResultsBean resultsBean = datas.get(position);
        switch (resultsBean.getReportWay()) {
            case 1://站点
                Intent intentBike = new Intent(mContext, OrderStationDetailsActivity.class);
                intentBike.putExtra("orderStation",resultsBean);
                startActivity(intentBike);
                break;
            case 2://车辆
                Intent intentStation = new Intent(mContext, OrderBikeDetailsActivity.class);
                intentStation.putExtra("orderBike",resultsBean);
                startActivity(intentStation);
                break;
        }
    }
}

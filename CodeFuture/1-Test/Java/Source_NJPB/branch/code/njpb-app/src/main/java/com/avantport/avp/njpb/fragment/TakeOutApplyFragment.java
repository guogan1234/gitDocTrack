package com.avantport.avp.njpb.fragment;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.avantport.avp.njpb.R;
import com.avantport.avp.njpb.adapter.FinalListAdapter;
import com.avantport.avp.njpb.bean.MineTakeOutbean;
import com.avantport.avp.njpb.constant.Constant;
import com.avantport.avp.njpb.okhttp.OkHttpBaseCallback;
import com.avantport.avp.njpb.okhttp.OkHttpUtils;
import com.avantport.avp.njpb.ui.fragment.BaseFragment;
import com.avantport.avp.njpb.ui.mine.DetailsActivity;
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
 * Created by len on 2017/8/17.
 * 我的领料申请
 */

public class TakeOutApplyFragment extends BaseFragment implements SwipeRefreshView.OnLoadMoreListener, SwipeRefreshLayout.OnRefreshListener, StateLayout.OnReloadListener, AdapterView.OnItemClickListener {


    private StateLayout mStateLayout;
    private SwipeRefreshView mRefresh;
    private ListView mList;

    private Map<String, Object> params = new HashMap<>();
    private List<MineTakeOutbean.ResultsBean> datas = new ArrayList<>();//储存请求的数据
    private FinalListAdapter<MineTakeOutbean.ResultsBean> mFinalListAdapter;
    private boolean isRefresh;
    private boolean isLoad;

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

        //获取网络数据
        params.clear();
        params.put("stockWorkOrderTypeId", "1");
        params.put("firstOperationTime", DateUtil.getTodayDate());
        params.put("operationResult", "0");
        params.put("sort", "lastUpdateTime,desc");
        mStateLayout.showLoadingView();
        getInDatas(Constant.MINE_TAKEOUT_LOADMORE, params);

    }

    public void getInDatas(String url, Map<String, Object> params) {
        OkHttpUtils.getInstance().get(url, params, null, new OkHttpBaseCallback<MineTakeOutbean>() {

            @Override
            public void onSuccess(Response response, MineTakeOutbean mineTakeOutbean) {
                List<MineTakeOutbean.ResultsBean> results = mineTakeOutbean.getResults();
                if (results != null & results.size() == 0) {
                    if (isRefresh) {
                        ToastUtil.show(mContext, "没有更新的数据了");
                        mRefresh.setRefreshing(false);
                        isRefresh = false;
                    } else if (isLoad) {
                        ToastUtil.show(mContext, "没有更多的数据了");
                        mRefresh.setLoading(false);
                        isLoad = false;
                    } else {
                        mStateLayout.showEmptyView();
                    }
                } else {
                    mStateLayout.showSuccessView();
                    if (isRefresh) {
                        datas.addAll(0, results);
                        mRefresh.setRefreshing(false);
                        mFinalListAdapter.notifyDataSetChanged();
                        isRefresh = false;
                    } else if (isLoad) {
                        datas.addAll(results);
                        mRefresh.setLoading(false);
                        mFinalListAdapter.notifyDataSetChanged();
                        isLoad = false;
                    } else {
                        datas.addAll(results);
                        setIntDatas();
                    }
                }
            }


            @Override
            public void onError(Response response, int code, Exception e) {
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
        mFinalListAdapter = new FinalListAdapter<>(datas, R.layout.item_mine_takeout_apply, new FinalListAdapter.AdapterListener<MineTakeOutbean.ResultsBean>() {
            @Override
            public void bindView(FinalListAdapter.FinalViewHolder finalViewHolder, MineTakeOutbean.ResultsBean resultsBean) {
                TextView order_code = (TextView) finalViewHolder.getViewById(R.id.order_code);
                TextView apply_name = (TextView) finalViewHolder.getViewById(R.id.apply_name);
                TextView apply_company = (TextView) finalViewHolder.getViewById(R.id.conmpany);
                TextView apply_time = (TextView) finalViewHolder.getViewById(R.id.apple_time);
                order_code.setText(resultsBean.getSerialNo());
                apply_name.setText(resultsBean.getProcessUserName());
                apply_company.setText(resultsBean.getCorpName());
                apply_time.setText(DateUtil.formatDate(resultsBean.getApplyTime()));
            }
        });

        mList.setAdapter(mFinalListAdapter);
    }

    @Override
    public void onReload() {
        params.clear();
        params.put("stockWorkOrderTypeId", "1");
        params.put("firstOperationTime", DateUtil.getTodayDate());
        params.put("operationResult", "0");
        params.put("sort", "lastUpdateTime,desc");
        getInDatas(Constant.MINE_TAKEOUT_LOADMORE, params);
    }


    @Override
    public void onLoadMore() {//上拉加载更多

        if (datas != null & datas.size() > 0) {
            long createTime = datas.get(datas.size() - 1).getLastUpdateTime();
            params.clear();
            params.put("stockWorkOrderTypeId", "1");
            params.put("firstOperationTime", DateUtil.formatLastDate(createTime));
            params.put("operationResult", "0");
            params.put("sort", "lastUpdateTime,desc");
            isLoad = true;
            getInDatas(Constant.MINE_TAKEOUT_LOADMORE, params);
        } else {
            return;
        }
    }

    @Override
    public void onRefresh() {//下拉刷新
        //获取列表的第一条信息的时间
        if (datas != null & datas.size() > 0) {
            long createTime = datas.get(0).getLastUpdateTime();
            params.clear();
            params.put("stockWorkOrderTypeId", "1");
            params.put("firstOperationTime", DateUtil.formatDate(createTime));
            params.put("operationResult", "0");
            //            params.put("sort", "lastUpdateTime,desc");
            isRefresh = true;
            getInDatas(Constant.MINE_TAKEOUT_REFRESH, params);
        } else {
            return;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //条目点击事件
        MineTakeOutbean.ResultsBean resultsBean = datas.get(position);
        Intent intent = new Intent(mContext, DetailsActivity.class);
        intent.putExtra("titleName", "领料详情");
        intent.putExtra("resultsBean", resultsBean);
        startActivity(intent);
        activityJumpAnim();
    }
}

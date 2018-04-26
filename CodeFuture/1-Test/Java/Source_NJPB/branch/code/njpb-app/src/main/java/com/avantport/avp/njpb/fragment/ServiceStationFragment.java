package com.avantport.avp.njpb.fragment;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.avantport.avp.njpb.R;
import com.avantport.avp.njpb.adapter.FinalListAdapter;
import com.avantport.avp.njpb.bean.MineRepairsbean;
import com.avantport.avp.njpb.constant.Constant;
import com.avantport.avp.njpb.okhttp.OkHttpBaseCallback;
import com.avantport.avp.njpb.okhttp.OkHttpUtils;
import com.avantport.avp.njpb.ui.fragment.BaseFragment;
import com.avantport.avp.njpb.ui.mine.MineStationDetailsActivity;
import com.avantport.avp.njpb.uitls.DateUtil;
import com.avantport.avp.njpb.uitls.ToastUtil;
import com.avantport.avp.njpb.view.StateLayout;
import com.avantport.avp.njpb.view.SwipeRefreshView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Response;

import static com.avantport.avp.njpb.R.id.break_see;


/**
 * Created by len on 2017/8/21.
 * 我的维修  车站维修
 */

public class ServiceStationFragment extends BaseFragment implements SwipeRefreshView.OnLoadMoreListener, SwipeRefreshLayout.OnRefreshListener, StateLayout.OnReloadListener, AdapterView.OnItemClickListener {

    private StateLayout mStateLayout;
    private SwipeRefreshView mRefresh;
    private ListView mList;

    private Map<String, Object> params = new HashMap<>();
    private List<MineRepairsbean.ResultsBean> datas = new ArrayList<>();//储存请求的数据
    private FinalListAdapter<MineRepairsbean.ResultsBean> mFinalListAdapter;
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
    protected void getFragmentData(View view) {//初始化加载成功的界面
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
        //打开进行数据加载和显示

        //获取网络数据
        params.clear();
        params.put("reportWay", "1");
        params.put("sort", "lastUpdateTime,desc");
        params.put("firstOperationTime", DateUtil.getTodayDate());
        mStateLayout.showLoadingView();
        getInDatas(Constant.MINE_SERVICE_LOADMORE, params);
    }

    private void getInDatas(String url, Map<String, Object> params) {
        OkHttpUtils.getInstance().get(url, params, null, new OkHttpBaseCallback<MineRepairsbean>() {

            @Override
            public void onSuccess(Response response, MineRepairsbean mineRepairsbean) {
                List<MineRepairsbean.ResultsBean> results = mineRepairsbean.getResults();
                if (results != null & results.size() == 0) {
                    if (isRefresh) {
                        ToastUtil.show(mContext, "没有更新的数据了");
                        mRefresh.setRefreshing(false);
                        isRefresh = false;
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
                        isRefresh = false;
                        mFinalListAdapter.notifyDataSetChanged();
                        mRefresh.setRefreshing(false);
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
                    isLoad = false;
                    mRefresh.setLoading(false);
                } else {
                    mStateLayout.showErrorView();
                }
            }
        });

    }

    private void setIntDatas() {//进行数据填充，数据展示
        mFinalListAdapter = new FinalListAdapter<>(datas, R.layout.item_mine_repairs_station, new FinalListAdapter.AdapterListener<MineRepairsbean.ResultsBean>() {
            @Override
            public void bindView(FinalListAdapter.FinalViewHolder finalViewHolder, MineRepairsbean.ResultsBean resultsBean) {
                TextView orderCode = (TextView) finalViewHolder.getViewById(R.id.order_code);
                TextView name = (TextView) finalViewHolder.getViewById(R.id.name);
                LinearLayout repairPeople = (LinearLayout) finalViewHolder.getViewById(R.id.repair_people);
                TextView company = (TextView) finalViewHolder.getViewById(R.id.company);
                TextView orderState = (TextView) finalViewHolder.getViewById(R.id.order_state);
                TextView station = (TextView) finalViewHolder.getViewById(R.id.station);
                TextView breakMachine = (TextView) finalViewHolder.getViewById(R.id.break_machine);
                TextView breakSee = (TextView) finalViewHolder.getViewById(break_see);
                TextView updataTime = (TextView) finalViewHolder.getViewById(R.id.updata_time);
                ImageView orderGrade = (ImageView) finalViewHolder.getViewById(R.id.grade);
                orderCode.setText(resultsBean.getSerialNo());
                orderState.setText(resultsBean.getWorkOrderStatusNameCn());
                company.setText(resultsBean.getCorpName());
                station.setText(resultsBean.getStationName());
                breakMachine.setText(resultsBean.getEstateName());
                updataTime.setText(DateUtil.formatDate(resultsBean.getLastUpdateTime()));
                if (resultsBean.getFaultDescription() != null && resultsBean.getFaultDescription().length() != 0) {
                    breakSee.setText(resultsBean.getFaultDescription());
                } else {
                    breakSee.setText("未填写");
                }

                if (resultsBean.getLevel() != null) {
                    orderGrade.setVisibility(View.VISIBLE);
                    if (resultsBean.getLevel() == 1) {
                        orderGrade.setImageResource(R.mipmap.one_grade);
                    } else if (resultsBean.getLevel() == 2) {
                        orderGrade.setImageResource(R.mipmap.two_grade);
                    } else if (resultsBean.getLevel() == 3) {
                        orderGrade.setImageResource(R.mipmap.three_grade);
                    } else if (resultsBean.getLevel() == 99) {
                        orderGrade.setImageResource(R.mipmap.danger);
                    }
                } else {
                    orderGrade.setVisibility(View.GONE);
                }

                if (resultsBean.getRepairEmployeeUserName() != null) {
                    repairPeople.setVisibility(View.VISIBLE);
                    name.setText(resultsBean.getRepairEmployeeUserName());
                } else {
                    repairPeople.setVisibility(View.GONE);
                }
            }

        });

        mList.setAdapter(mFinalListAdapter);
    }

    @Override
    public void onLoadMore() {
        if (datas != null & datas.size() > 0) {
            long createTime = datas.get(datas.size() - 1).getLastUpdateTime();
            params.clear();
            params.put("reportWay", "1");
            params.put("sort", "lastUpdateTime,desc");
            params.put("firstOperationTime", DateUtil.formatLastDate(createTime));
            isLoad = true;
            getInDatas(Constant.MINE_SERVICE_LOADMORE, params);
        }
        mRefresh.setLoading(false);//去除底部的布局
    }

    @Override
    public void onRefresh() {
        //获取列表的第一条信息的时间
        if (datas != null & datas.size() > 0) {
            long createTime = datas.get(0).getLastUpdateTime();
            params.clear();
            params.put("reportWay", "2");
            //            params.put("sort", "lastUpdateTime,desc");
            params.put("firstOperationTime", DateUtil.formatDate(createTime));
            isRefresh = true;
            getInDatas(Constant.MINE_SERVICE_REFRESH, params);
        } else {
            return;
        }
    }

    @Override
    public void onReload() {
        params.clear();
        params.put("reportWay", "1");
        params.put("sort", "lastUpdateTime,desc");
        params.put("firstOperationTime", DateUtil.getTodayDate());
        getInDatas(Constant.MINE_SERVICE_LOADMORE, params);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //点击条目，进入详情界面
        MineRepairsbean.ResultsBean resultsBean = datas.get(position);
        Intent intent = new Intent(mContext, MineStationDetailsActivity.class);
        intent.putExtra("orderId", resultsBean.getId() + "");
        intent.putExtra("resourceType", 3 + "");
        startActivity(intent);
        activityJumpAnim();
    }
}

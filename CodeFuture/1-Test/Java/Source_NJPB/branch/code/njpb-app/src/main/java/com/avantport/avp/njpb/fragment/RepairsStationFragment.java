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

/**
 * Created by len on 2017/8/21.
 * 我的报修 站点报修
 */

public class RepairsStationFragment extends BaseFragment implements SwipeRefreshView.OnLoadMoreListener, SwipeRefreshLayout.OnRefreshListener, StateLayout.OnReloadListener, AdapterView.OnItemClickListener {
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
        params.put("reportWay", "1");
        params.put("sort", "lastUpdateTime,desc");
        params.put("firstOperationTime", DateUtil.getTodayDate());
        mStateLayout.showLoadingView();
        getInDatas(Constant.MINE_REPAIES_LOADMORE, params);

    }

    //数据bean也需要进行修改
    private void getInDatas(String url, Map<String, Object> params) {

        OkHttpUtils.getInstance().get(url, params, null, new OkHttpBaseCallback<MineRepairsbean>() {
            @Override
            public void onSuccess(Response response, MineRepairsbean mineRepairsbean) {
                List<MineRepairsbean.ResultsBean> results = mineRepairsbean.getResults();
                if (results != null && results.size() == 0) {
                    if (isRefresh) {
                        isRefresh = false;
                        mRefresh.setRefreshing(false);
                        ToastUtil.show(mContext, "没有更新的数据了");
                    } else if (isLoad) {
                        isLoad = false;
                        mRefresh.setLoading(false);
                        ToastUtil.show(mContext, "没有更多的数据了");
                    } else {
                        mStateLayout.showEmptyView();
                    }
                } else {
                    mStateLayout.showSuccessView();
                    if (isRefresh) {
                        datas.addAll(0, results);
                        isRefresh = false;
                        mRefresh.setRefreshing(false);
                        mFinalListAdapter.notifyDataSetChanged();
                    } else if (isLoad) {
                        datas.addAll(results);
                        isLoad = false;
                        mRefresh.setLoading(false);
                        mFinalListAdapter.notifyDataSetChanged();
                    } else {
                        datas.addAll(results);
                        setIntDatas();
                    }
                }
            }

            @Override
            public void onError(Response response, int code, Exception e) {
                if (isRefresh) {
                    isRefresh = false;
                    mRefresh.setRefreshing(false);
                    ToastUtil.show(mContext, "网络异常");
                } else if (isLoad) {
                    isLoad = false;
                    mRefresh.setLoading(false);
                    ToastUtil.show(mContext, "网络异常");
                } else {
                    mStateLayout.showErrorView();
                }

            }
        });
    }

    private void setIntDatas() {//这里需要做修改，
        mFinalListAdapter = new FinalListAdapter<>(datas, R.layout.item_mine_repairs_station, new FinalListAdapter.AdapterListener<MineRepairsbean.ResultsBean>() {
            @Override
            public void bindView(FinalListAdapter.FinalViewHolder finalViewHolder, MineRepairsbean.ResultsBean resultsBean) {
                TextView orderCode = (TextView) finalViewHolder.getViewById(R.id.order_code);
                LinearLayout repairPeople = (LinearLayout) finalViewHolder.getViewById(R.id.repair_people);
                TextView name = (TextView) finalViewHolder.getViewById(R.id.name);
                TextView orderState = (TextView) finalViewHolder.getViewById(R.id.order_state);
                TextView company = (TextView) finalViewHolder.getViewById(R.id.company);
                TextView station = (TextView) finalViewHolder.getViewById(R.id.station);
                TextView breakMachine = (TextView) finalViewHolder.getViewById(R.id.break_machine);
                TextView breakSee = (TextView) finalViewHolder.getViewById(R.id.break_see);
                TextView updataTime = (TextView) finalViewHolder.getViewById(R.id.updata_time);
                ImageView orderGrade = (ImageView) finalViewHolder.getViewById(R.id.grade);
                orderCode.setText(resultsBean.getSerialNo());
                orderState.setText(resultsBean.getWorkOrderStatusNameCn());
                company.setText(resultsBean.getCorpName());
                station.setText(resultsBean.getStationName());
                breakMachine.setText(resultsBean.getEstateName());
                //                breakSee.setText(resultsBean.getFaultDescription());
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
            getInDatas(Constant.MINE_REPAIES_LOADMORE, params);
        } else {
            return;
        }
    }

    @Override
    public void onRefresh() {
        //获取列表的第一条信息的时间
        // TODO: 2017/9/12 上拉加载更多有问题
        if (datas != null & datas.size() > 0) {
            long createTime = datas.get(0).getLastUpdateTime();
            params.clear();
            params.put("reportWay", "1");
            params.put("firstOperationTime", DateUtil.formatDate(createTime));
            isRefresh = true;
            getInDatas(Constant.MINE_REPAIES_REFRESH, params);
        } else {
            return;
        }
    }

    @Override
    public void onReload() {
        //获取网络数据
        params.clear();
        params.put("reportWay", "1");
        params.put("sort", "lastUpdateTime,desc");
        params.put("firstOperationTime", DateUtil.getTodayDate());
        getInDatas(Constant.MINE_REPAIES_LOADMORE, params);
    }

    @Override
    public void onDestroy() {
        datas.clear();
        super.onDestroy();
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

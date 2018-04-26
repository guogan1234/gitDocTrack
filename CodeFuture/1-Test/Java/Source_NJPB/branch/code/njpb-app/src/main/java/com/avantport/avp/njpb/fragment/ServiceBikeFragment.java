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
import com.avantport.avp.njpb.ui.mine.MineBikeDetailsActivity;
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
 * 我的维修  车辆维修
 */

public class ServiceBikeFragment extends BaseFragment implements SwipeRefreshView.OnLoadMoreListener, SwipeRefreshLayout.OnRefreshListener, StateLayout.OnReloadListener, AdapterView.OnItemClickListener {

    private StateLayout mStateLayout;
    private SwipeRefreshView mRefresh;
    private ListView mList;
    private Map<String, Object> params = new HashMap<>();
    private List<MineRepairsbean.ResultsBean> datas = new ArrayList<>();//储存请求的数据
    private FinalListAdapter<MineRepairsbean.ResultsBean> mFinalListAdapter;
    private boolean isRefresh ;
    private boolean isLoad;
    protected View getFragmentView(LayoutInflater inflater, ViewGroup container) {
        if (mStateLayout ==null) {
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
        params.put("reportWay", "2");
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
                if (results!= null & results.size()==0) {
                    if (isRefresh) {//上拉加载更多走到了refresh中了
                        ToastUtil.show(mContext,"没有更新的数据了");
                        mRefresh.setRefreshing(false);
                        isRefresh = false;
                    }else if (isLoad){
                        ToastUtil.show(mContext,"没有更多的数据了");
                        mRefresh.setLoading(false);
                        isLoad = false;
                    }else{
                        mStateLayout.showEmptyView();
                    }
                }else{
                    mStateLayout.showSuccessView();
                    if (isRefresh) {
                        datas.addAll(0,results);
                        mRefresh.setRefreshing(false);
                        mFinalListAdapter.notifyDataSetChanged();
                        isRefresh = false;
                    }else if (isLoad){
                        datas.addAll(results);
                        mRefresh.setLoading(false);
                        mFinalListAdapter.notifyDataSetChanged();
                        isLoad = false;
                    }else{
                        datas.addAll(results);
                        setIntDatas();
                    }
                }
            }

            @Override
            public void onError(Response response, int code, Exception e) {
                if (isRefresh) {
                    ToastUtil.show(mContext,"网络异常");
                    mRefresh.setRefreshing(false);
                    isRefresh = false;
                }else if (isLoad){
                    ToastUtil.show(mContext,"网络异常");
                    mRefresh.setLoading(false);
                    isLoad = false;
                }else{
                    mStateLayout.showErrorView();
                }
            }
        });
    }

    private void setIntDatas() {
        mFinalListAdapter = new FinalListAdapter<>(datas, R.layout.item_mine_repairs_bike, new FinalListAdapter.AdapterListener<MineRepairsbean.ResultsBean>() {
            @Override
            public void bindView(FinalListAdapter.FinalViewHolder finalViewHolder, MineRepairsbean.ResultsBean resultsBean) {
                TextView order_code = (TextView) finalViewHolder.getViewById(R.id.order_code);
                LinearLayout repair_people = (LinearLayout) finalViewHolder.getViewById(R.id.repair_people);
                TextView name = (TextView) finalViewHolder.getViewById(R.id.name);
                TextView break_see = (TextView) finalViewHolder.getViewById(R.id.break_see);
                TextView updata_time = (TextView) finalViewHolder.getViewById(R.id.updata_time);
                TextView bike_code = (TextView) finalViewHolder.getViewById(R.id.bike_code);
                TextView orderState = (TextView) finalViewHolder.getViewById(R.id.order_state);
                ImageView orderGrade = (ImageView) finalViewHolder.getViewById(R.id.grade);
                orderState.setText(resultsBean.getWorkOrderStatusNameCn());
                order_code.setText(resultsBean.getSerialNo());
                bike_code.setText(resultsBean.getBikeFrameNo()+"");
                if (resultsBean.getFaultDescription()!=null &&resultsBean.getFaultDescription().length()!=0) {
                    break_see.setText(resultsBean.getFaultDescription());
                }else{
                    break_see.setText("未填写");
                }
                updata_time.setText(DateUtil.formatDate(resultsBean.getLastUpdateTime()));
                if (resultsBean.getRepairEmployeeUserName() !=null) {
                    repair_people.setVisibility(View.VISIBLE);
                    name.setText(resultsBean.getRepairEmployeeUserName());
                }else{
                    repair_people.setVisibility(View.GONE);
                }

                if (resultsBean.getLevel() != null) {
                    orderGrade.setVisibility(View.VISIBLE);
                    if (resultsBean.getLevel() == 1) {
                        orderGrade.setImageResource(R.mipmap.one_grade);
                    }else if (resultsBean.getLevel() == 2){
                        orderGrade.setImageResource(R.mipmap.two_grade);
                    }else if (resultsBean.getLevel() == 3){
                        orderGrade.setImageResource(R.mipmap.three_grade);
                    }else if (resultsBean.getLevel() == 99){
                        orderGrade.setImageResource(R.mipmap.danger);
                    }
                }else{
                    orderGrade.setVisibility(View.GONE);
                }
            }
        });
        mList.setAdapter(mFinalListAdapter);
    }

    @Override
    public void onLoadMore() {
        if (datas!=null&datas.size()>0) {
            long createTime = datas.get(datas.size()-1).getLastUpdateTime();
            params.clear();
            params.put("reportWay", "2");
            params.put("sort", "lastUpdateTime,desc");
            params.put("firstOperationTime", DateUtil.formatLastDate(createTime));
            isLoad = true;
            getInDatas(Constant.MINE_SERVICE_LOADMORE, params);
        }
    }

    @Override
    public void onRefresh() {
        //获取列表的第一条信息的时间
        if (datas!=null&datas.size()>0) {
            long createTime  = datas.get(0).getLastUpdateTime();
            params.clear();
            params.put("reportWay", "2");
//            params.put("sort", "lastUpdateTime,desc");
            params.put("firstOperationTime", DateUtil.formatDate(createTime));
            isRefresh = true;
            getInDatas(Constant.MINE_SERVICE_REFRESH, params);
        }else{
            return;
        }
    }

    @Override
    public void onReload() {
        params.clear();
        params.put("reportWay", "2");
        params.put("sort", "lastUpdateTime,desc");
        params.put("firstOperationTime", DateUtil.getTodayDate());
        getInDatas(Constant.MINE_SERVICE_LOADMORE, params);
    }

    @Override
    public void onDestroy() {
        datas.clear();
        super.onDestroy();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //条目的点击事件
        //点击条目，进入详情界面
        MineRepairsbean.ResultsBean resultsBean = datas.get(position);
        Intent intent = new Intent(mContext, MineBikeDetailsActivity.class);
        intent.putExtra("orderId",resultsBean.getId()+"");
        intent.putExtra("resourceType",3+"");
        startActivity(intent);
        activityJumpAnim();
    }
}

package com.avantport.avp.njpb.activity;


import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.avantport.avp.njpb.R;
import com.avantport.avp.njpb.adapter.FinalListAdapter;
import com.avantport.avp.njpb.bean.CheckCountbean;
import com.avantport.avp.njpb.bean.MineBikeCheckbean;
import com.avantport.avp.njpb.constant.Constant;
import com.avantport.avp.njpb.okhttp.OkHttpBaseCallback;
import com.avantport.avp.njpb.okhttp.OkHttpUtils;
import com.avantport.avp.njpb.ui.base.BaseActivity;
import com.avantport.avp.njpb.ui.work.BikeCheckActivity;
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
 * 我的盘点 2017/8/16
 */
public class MineNumCountActivity extends BaseActivity implements SwipeRefreshView.OnLoadMoreListener, SwipeRefreshLayout.OnRefreshListener, View.OnClickListener, StateLayout.OnReloadListener {


    private TextView mHead_title;
    private ImageView mHead_goback;
    private ImageView mHead_qrcode;

    private TextView mCurrentCheck;
    private TextView mCheckLastTime;

    private List<MineBikeCheckbean.ResultsBean> minecheck = new ArrayList<>();//我的盘点返回的数据
    private Map<String, Object> params = new HashMap<>();

    private boolean isRefresh;
    private boolean isLoad;
    private FinalListAdapter<MineBikeCheckbean.ResultsBean> mFinalListAdapter;
    private StateLayout mLayoutState;
    private SwipeRefreshView mRefresh;
    private ListView mList;


    @Override
    protected int getLayoutRes() {
        return R.layout.mine_countcheck;
    }

    @Override
    protected void initView() {
        mHead_title = (TextView) findViewById(R.id.head_title);
        mHead_goback = (ImageView) findViewById(R.id.head_goback);
        mHead_qrcode = (ImageView) findViewById(R.id.head_qrcode);
        mHead_title.setText("我的盘点");
        mHead_qrcode.setImageResource(R.mipmap.work_bicycle);
        // TODO: 2017/9/30 暂时封锁该功能
        mHead_qrcode.setVisibility(View.VISIBLE);

        mCurrentCheck = (TextView) findViewById(R.id.check_all);//本次盘点数
        mCheckLastTime = (TextView) findViewById(R.id.check_lasttime);//上次的盘点数
        mLayoutState = (StateLayout) findViewById(R.id.layout_state);
        mHead_goback.setOnClickListener(this);
        mHead_qrcode.setOnClickListener(this);
    }

    @Override
    protected void initDatas() {
        initSuccessView();//初始化成功的view
        getCheckCountData();//获取盘点总数和上次盘点数
        getDatas();//获取网络数据
    }

    private void getDatas() {
        params.clear();//进行数据请求，获取数据
        params.put("firstOperationTime", DateUtil.getTodayDate());
        params.put("sort", "lastUpdateTime,desc");
        mLayoutState.showLoadingView();
        getIntDatas(Constant.MINE_CHECK_LOADMORE, params);
    }

    private void initSuccessView() {
        View inflate = View.inflate(this, R.layout.refresh_list, null);
        mRefresh = (SwipeRefreshView) inflate.findViewById(R.id.refresh);
        mList = (ListView) inflate.findViewById(R.id.list);
        mLayoutState.bindSuccessView(inflate);
        mRefresh.setOnLoadMoreListener(this);
        mRefresh.setOnRefreshListener(this);
        mLayoutState.setOnReloadListener(this);
    }

    @Override
    public void onLoadMore() {//加载更多

        if (minecheck != null && minecheck.size() > 0) {
            long createTime = minecheck.get(minecheck.size() - 1).getLastUpdateTime();
            params.clear();
            params.put("firstOperationTime", DateUtil.formatLastDate(createTime));
            params.put("sort", "lastUpdateTime,desc");
            isLoad = true;
            getIntDatas(Constant.MINE_CHECK_LOADMORE, params);
        } else {
            return;
        }

    }

    @Override
    public void onRefresh() {//下拉刷新
        //获取列表的第一条信息的时间

        if (minecheck != null && minecheck.size() > 0) {
            long createTime = minecheck.get(0).getLastUpdateTime();
            params.clear();
            params.put("firstOperationTime", DateUtil.formatDate(createTime));
//            params.put("sort", "lastUpdateTime,desc");
            isRefresh = true;
            getIntDatas(Constant.MINE_CHECK_REFRESH, params);
        } else {
            return;
        }
    }

    @Override
    public void onReload() {//重新加载
        params.clear();//进行数据请求，获取数据
        params.put("firstOperationTime", DateUtil.getTodayDate());
        params.put("sort", "lastUpdateTime,desc");
        mLayoutState.showLoadingView();

        getIntDatas(Constant.MINE_CHECK_LOADMORE, params);
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.head_goback:
                finish();
                break;
            case R.id.head_qrcode:
                //跳转车辆盘点
//                ToastUtil.show(MineNumCountActivity.this,"没有读卡设备！");
//                startActivity(new Intent(this, BikeCheckActivity.class));
////              //  activityJumpAnim();
//                ActivityJumpAnim();
                startActivity(BikeCheckActivity.class);
                break;
        }
    }

    public void getIntDatas(String url, Map<String, Object> params) {

        OkHttpUtils.getInstance().get(url, params, null, new OkHttpBaseCallback<MineBikeCheckbean>() {

            @Override
            public void onSuccess(Response response, MineBikeCheckbean mineBikeCheckbean) {

                List<MineBikeCheckbean.ResultsBean> results = mineBikeCheckbean.getResults();

                if (results != null & results.size() == 0) {
                    //没有数据
                    if (isRefresh) {//刷新界面
                        ToastUtil.show(MineNumCountActivity.this, getString(R.string.no_refresh_news));
                        isRefresh = false;
                        mRefresh.setRefreshing(false);
                    } else if (isLoad) {
                        ToastUtil.show(MineNumCountActivity.this, getString(R.string.no_more_news));
                        isLoad = false;
                        mRefresh.setLoading(false);
                    } else {
                        mLayoutState.showEmptyView();
                    }
                } else {//有数据，进行数据判断
                    mLayoutState.showSuccessView();
                    if (isRefresh) {
                        minecheck.addAll(0, results);
                        isRefresh = false;
                        mRefresh.setRefreshing(false);
                        mFinalListAdapter.notifyDataSetChanged();
                    }
                    if (isLoad) {
                        minecheck.addAll(results);
                        mRefresh.setLoading(false);
                        isLoad = false;
                        mFinalListAdapter.notifyDataSetChanged();
                    } else {
                        minecheck.addAll(results);
                        setIntDatas();
                    }
                }
            }

            @Override
            public void onError(Response response, int code, Exception e) {
                if (isRefresh) {
                    ToastUtil.show(MineNumCountActivity.this, getString(R.string.no_refresh_news));
                    mRefresh.setRefreshing(false);
                    isRefresh = false;
                } else if (isLoad) {
                    ToastUtil.show(MineNumCountActivity.this, getString(R.string.no_refresh_news));
                    mRefresh.setLoading(false);
                    isLoad = false;
                } else {
                    mLayoutState.showErrorView();
                }

            }
        });

    }

    private void setIntDatas() {//展示数据，将网络的数据显示出来
        mFinalListAdapter = new FinalListAdapter<>(minecheck, R.layout.item_mine_bikecheck, new FinalListAdapter.AdapterListener<MineBikeCheckbean.ResultsBean>() {
            @Override
            public void bindView(FinalListAdapter.FinalViewHolder finalViewHolder, MineBikeCheckbean.ResultsBean resultsBean) {
                TextView checkNum = (TextView) finalViewHolder.getViewById(R.id.item_checknum);
                TextView checkAdress = (TextView) finalViewHolder.getViewById(R.id.item_checkadress);
                TextView checkTime = (TextView) finalViewHolder.getViewById(R.id.item_checktime);
                //对int和long类型的数据进行判断
                if (resultsBean.getCount() == null) {
                    checkNum.setText("未知");
                } else {
                    checkNum.setText(resultsBean.getCount() + "");
                }
                if (resultsBean.getStationNoName() == null) {
                    checkAdress.setText("");
                } else {
                    checkAdress.setText(resultsBean.getStationNoName());
                }
                if (resultsBean.getLastUpdateTime() == null) {
                    checkTime.setText("时间流失了");
                } else {
                    checkTime.setText(DateUtil.formatDate(resultsBean.getLastUpdateTime()));
                }
            }
        });
        mList.setAdapter(mFinalListAdapter);
    }

    public void getCheckCountData() {
        OkHttpUtils.getInstance().get(Constant.MINE_CHECK_COUNT, null, null, new OkHttpBaseCallback<CheckCountbean>() {
            @Override
            public void onSuccess(Response response, CheckCountbean checkCountbean) {
                CheckCountbean.ResultBean result = checkCountbean.getResult();
                setCountDatas(result);
            }

            @Override
            public void onError(Response response, int code, Exception e) {
                mCurrentCheck.setHint(R.string.get_data_fail);
                mCheckLastTime.setHint(R.string.get_data_fail);
            }
        });
    }

    private void setCountDatas(CheckCountbean.ResultBean result) {
        //获取数据后，判断数据bean是否有数据
        //数据会返回null，要做null的判断
        if (result.getPersonCheckNow() == null) {
            mCurrentCheck.setText(R.string.defult_num);
        } else {
            mCurrentCheck.setText(result.getPersonCheckNow() + "");
        }

        if (result.getPersonCheckLast() == null) {
            mCheckLastTime.setText(R.string.defult_num);
        } else {
            mCheckLastTime.setText(result.getPersonCheckLast() + "");
        }
    }
}

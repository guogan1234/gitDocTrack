package com.avantport.avp.njpb.activity;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.avantport.avp.njpb.R;
import com.avantport.avp.njpb.adapter.FinalListAdapter;
import com.avantport.avp.njpb.bean.MineNewsbean;
import com.avantport.avp.njpb.constant.Constant;
import com.avantport.avp.njpb.constant.UrlParam;
import com.avantport.avp.njpb.okhttp.OkHttpBaseCallback;
import com.avantport.avp.njpb.okhttp.OkHttpUtils;
import com.avantport.avp.njpb.ui.base.BaseActivity;
import com.avantport.avp.njpb.ui.mine.NewsDetailsActivity;
import com.avantport.avp.njpb.uitls.DateUtil;
import com.avantport.avp.njpb.uitls.ToastUtil;
import com.avantport.avp.njpb.view.StateLayout;
import com.avantport.avp.njpb.view.SwipeRefreshView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Response;

public class MineNewsActivity extends BaseActivity implements View.OnClickListener, SwipeRefreshView.OnLoadMoreListener, SwipeRefreshLayout.OnRefreshListener, StateLayout.OnReloadListener, AdapterView.OnItemClickListener {

    private TextView mHeadTitle;
    private ImageView mHeadQrcode;
    private ImageView mHeadGoback;
    private StateLayout mStateLayout;
    private SwipeRefreshView mRefresh;
    private ListView mList;
    private Map<String, Object> params = new HashMap<>();
    private List<MineNewsbean.ResultsBean> datas = new ArrayList<>();//储存请求的数据
    private FinalListAdapter<MineNewsbean.ResultsBean> mFinalListAdapter;
    private boolean isRefresh;
    private boolean isLoad;

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_mine_news;
    }

    @Override
    protected void initView() {
        mHeadTitle = (TextView) findViewById(R.id.head_title);
        mHeadQrcode = (ImageView) findViewById(R.id.head_qrcode);
        mHeadGoback = (ImageView) findViewById(R.id.head_goback);
        mHeadQrcode.setVisibility(View.INVISIBLE);
        mHeadTitle.setText(R.string.mine_new_title);
        mHeadGoback.setOnClickListener(this);
        mStateLayout = (StateLayout) findViewById(R.id.layout_state);
    }

    @Override
    protected void initDatas() {
        View inflate = View.inflate(this, R.layout.refresh_list, null);
        mRefresh = (SwipeRefreshView) inflate.findViewById(R.id.refresh);
        mList = (ListView) inflate.findViewById(R.id.list);
        mStateLayout.bindSuccessView(inflate);
        mRefresh.setOnLoadMoreListener(this);
        mRefresh.setOnRefreshListener(this);
        mStateLayout.setOnReloadListener(this);
        mList.setOnItemClickListener(this);
        mList.setDivider(null);
        getIntDatas();
    }

    private void getIntDatas() {
        //进行网络请求，获取数据
        params.clear();
        params.put("sort", "lastUpdateTime,desc");
        params.put(UrlParam.PARAM_LOADMORE, DateUtil.getTodayDate());
        mStateLayout.showLoadingView();
        getInDatas(Constant.MINE_NEWS_LOADMORE, params);
    }

    private void getInDatas(String url, Map<String, Object> params) {
        OkHttpUtils.getInstance().get(url, params, null, new OkHttpBaseCallback<MineNewsbean>() {

            @Override
            public void onSuccess(Response response, MineNewsbean mineNewsbean) {
                List<MineNewsbean.ResultsBean> results = mineNewsbean.getResults();
                if (results != null & results.size() == 0) {
                    if (isRefresh) {//上拉加载更多走到了refresh中了,解决了
                        mRefresh.setRefreshing(false);
                        ToastUtil.show(MineNewsActivity.this, getString(R.string.no_refresh_news));
                        isRefresh = false;
                    } else if (isLoad) {
                        mRefresh.setLoading(false);
                        ToastUtil.show(MineNewsActivity.this, getString(R.string.no_more_news));
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
                    ToastUtil.show(MineNewsActivity.this, getString(R.string.net_error));
                    mRefresh.setRefreshing(false);
                    isRefresh = false;
                } else if (isLoad) {
                    ToastUtil.show(MineNewsActivity.this, getString(R.string.net_error));
                    mRefresh.setLoading(false);
                    isLoad = false;
                } else {
                    mStateLayout.showErrorView();
                }
            }
        });
    }
    private void setIntDatas() {
        mFinalListAdapter = new FinalListAdapter<>(datas, R.layout.item_mine_news, new FinalListAdapter.AdapterListener<MineNewsbean.ResultsBean>() {
            @Override
            public void bindView(FinalListAdapter.FinalViewHolder finalViewHolder, MineNewsbean.ResultsBean resultsBean) {
                TextView title = (TextView) finalViewHolder.getViewById(R.id.title);
                TextView time = (TextView) finalViewHolder.getViewById(R.id.time);
                title.setText(resultsBean.getMessageTitle());
                time.setText(DateUtil.formatDate(resultsBean.getLastUpdateTime()));
            }
        });
        mList.setAdapter(mFinalListAdapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.head_goback:
                finish();
                break;
        }
    }

    @Override
    public void onLoadMore() {
        if (datas != null & datas.size() > 0) {
            long createTime = datas.get(datas.size() - 1).getLastUpdateTime();
            params.clear();
            params.put("sort", "lastUpdateTime,desc");
            params.put(UrlParam.PARAM_LOADMORE, DateUtil.formatLastDate(createTime));
            isLoad = true;
            getInDatas(Constant.MINE_NEWS_LOADMORE, params);
        }
    }

    @Override
    public void onRefresh() {
        //获取列表的第一条信息的时间
        if (datas != null & datas.size() > 0) {
            long createTime = datas.get(0).getLastUpdateTime();
            params.clear();
            params.put(UrlParam.PARAM_LOADMORE, DateUtil.formatDate(createTime));
            isRefresh = true;
            getInDatas(Constant.MINE_NEWS_REFRESH, params);
        } else {
            return;
        }
    }

    @Override
    public void onReload() {
        params.clear();
        params.put("sort", "lastUpdateTime,desc");
        params.put(UrlParam.PARAM_LOADMORE, DateUtil.getTodayDate());
        getInDatas(Constant.MINE_NEWS_LOADMORE, params);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //点击条目，跳转详情界面
        MineNewsbean.ResultsBean resultsBean = datas.get(position);
        Intent intent = new Intent(this, NewsDetailsActivity.class);
        intent.putExtra("newsInfos",resultsBean);
        startActivity(intent);
        ActivityJumpAnim();
    }
}

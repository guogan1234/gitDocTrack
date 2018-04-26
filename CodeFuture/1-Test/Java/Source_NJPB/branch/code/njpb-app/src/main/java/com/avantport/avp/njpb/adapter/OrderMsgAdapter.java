package com.avantport.avp.njpb.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.avantport.avp.njpb.R;
import com.avantport.avp.njpb.bean.OrderMessagebean;
import com.avantport.avp.njpb.uitls.DateUtil;

import java.util.List;

/**
 * Created by len on 2017/8/28.
 * 工单列表
 */

public class OrderMsgAdapter extends BaseAdapter {

    private static final int TYPECOUNT = 2;
    private static final int TYPE_STATION = 0;
    private static final int TYPE_BIKE = 1;
    private List<OrderMessagebean.ResultsBean> datas;
    private Context mContext;

    public OrderMsgAdapter(List<OrderMessagebean.ResultsBean> datas, Context context) {
        this.datas = datas;
        mContext = context;
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public OrderMessagebean.ResultsBean getItem(int position) {
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        OrderMessagebean.ResultsBean resultsBean = datas.get(position);
        if (resultsBean.getReportWay() == 1) {
            return TYPE_STATION;
        } else if (resultsBean.getReportWay() == 2) {

            return TYPE_BIKE;
        }
        return super.getItemViewType(position);
    }

    @Override
    public int getViewTypeCount() {
        return TYPECOUNT;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        OrderMessagebean.ResultsBean item = getItem(position);
        viewHolder holder;
        if (convertView == null) {
            holder = new viewHolder();
            switch (getItemViewType(position)) {
                case TYPE_STATION:
                    convertView = View.inflate(mContext, R.layout.item_order_station, null);
                    holder.breakMachine = (TextView) convertView.findViewById(R.id.break_machine);
                    holder.company = (TextView) convertView.findViewById(R.id.company);
                    holder.station = (TextView) convertView.findViewById(R.id.station);
                    break;
                case TYPE_BIKE:
                    convertView = View.inflate(mContext, R.layout.item_order_bike, null);
                    holder.bikeCode = (TextView) convertView.findViewById(R.id.bike_code);
                    break;
            }
            holder.orderCode = (TextView) convertView.findViewById(R.id.order_code);
            holder.name = (TextView) convertView.findViewById(R.id.name);
            holder.breakSee = (TextView) convertView.findViewById(R.id.break_see);
            holder.updataTime = (TextView) convertView.findViewById(R.id.updata_time);
            holder.character = (TextView) convertView.findViewById(R.id.character);
            holder.orderState = (TextView) convertView.findViewById(R.id.order_state);
            holder.orderLevel = (ImageView) convertView.findViewById(R.id.grade);

            convertView.setTag(holder);
        } else {
            holder = (viewHolder) convertView.getTag();
        }

        if (item.getFaultDescription().length() !=0) {
            holder.breakSee.setText(item.getFaultDescription());
        }else{
            holder.breakSee.setText("未填写");
        }
        holder.character.setText("报修人");
        holder.orderCode.setText(item.getSerialNo());
        holder.name.setText(item.getReportEmployeeUserName());
        holder.updataTime.setText(DateUtil.formatDate(item.getLastUpdateTime()));
        holder.orderState.setText(item.getWorkOrderStatusNameCn());
        //设置等级的图片
        if (item.getLevel() != null) {
            holder.orderLevel.setVisibility(View.VISIBLE);
            if (item.getLevel() == 1) {
                holder.orderLevel.setImageResource(R.mipmap.one_grade);
            } else if (item.getLevel() == 2) {
                holder.orderLevel.setImageResource(R.mipmap.two_grade);
            } else if (item.getLevel() == 3) {
                holder.orderLevel.setImageResource(R.mipmap.three_grade);
            } else if (item.getLevel() == 99) {
                holder.orderLevel.setImageResource(R.mipmap.danger);
            }
        } else {
            holder.orderLevel.setVisibility(View.INVISIBLE);
        }
        switch (getItemViewType(position)) {
            case TYPE_STATION:
                holder.company.setText(item.getCorpName());
                holder.breakMachine.setText(item.getEstateName());
                holder.station.setText(item.getStationName());
                break;
            case TYPE_BIKE:
                holder.bikeCode.setText(item.getBikeFrameNo()+"");
                break;
        }
        return convertView;
    }

    static class viewHolder {
        TextView orderCode;
        TextView name;
        TextView company;
        TextView station;
        TextView breakMachine;
        TextView breakSee;
        TextView updataTime;
        TextView bikeCode;
        TextView character;
        // TODO: 2017/8/28
        //还有textview和imageview
        TextView orderState;//工单状态
        ImageView orderLevel;//工单等级

    }
}

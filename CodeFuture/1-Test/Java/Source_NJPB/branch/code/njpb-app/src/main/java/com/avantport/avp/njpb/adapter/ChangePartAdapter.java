package com.avantport.avp.njpb.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.avantport.avp.njpb.R;
import com.avantport.avp.njpb.bean.DeviceTypebean;

import java.util.List;

/**
 * Created by len on 2017/9/6.
 * 部件选择展示列表
 */

public class ChangePartAdapter extends BaseAdapter {

    private List<DeviceTypebean.ResultsBean> data;
    private Context mContext;

    public ChangePartAdapter(List<DeviceTypebean.ResultsBean> data, Context context) {
        this.data = data;
        mContext = context;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public DeviceTypebean.ResultsBean getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final DeviceTypebean.ResultsBean resultsBean = data.get(position);
        final viewHolder holder ;
        if (convertView ==null) {
            convertView = View.inflate(mContext, R.layout.item_change_part, null);
            holder = new viewHolder();
            holder.changed = (ImageView) convertView.findViewById(R.id.changed);
            holder.station = (TextView) convertView.findViewById(R.id.station);
            holder.add = (ImageView) convertView.findViewById(R.id.add);
            holder.reduce = (ImageView) convertView.findViewById(R.id.reduce);
            holder.count = (TextView) convertView.findViewById(R.id.count);
            convertView.setTag(holder);

        }else{
            holder = (viewHolder) convertView.getTag();
        }

        holder.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    int count = resultsBean.getCount();
                    count +=1;
                    resultsBean.setCount(count);
                    holder.count.setText(count+"");

            }
        });
        holder.reduce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (resultsBean.getCount()>1) {
                    int count = resultsBean.getCount();
                    count -=1;
                    resultsBean.setCount(count);
                    holder.count.setText(count+"");
                }
            }
        });
        holder.changed.setSelected(resultsBean.isSelected());
        holder.station.setText(resultsBean.getName());
        holder.count.setText(resultsBean.getCount()+"");
        return convertView;
    }

    static class viewHolder{
        ImageView changed;
        TextView station;
        ImageView add;
        ImageView reduce;
        TextView count;
    }
}

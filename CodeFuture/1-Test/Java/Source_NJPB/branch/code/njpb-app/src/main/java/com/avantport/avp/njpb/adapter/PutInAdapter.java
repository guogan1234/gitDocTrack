package com.avantport.avp.njpb.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.avantport.avp.njpb.R;
import com.avantport.avp.njpb.bean.PutInbean;

import java.util.List;

/**
 * Created by len on 2017/8/11.
 */

public class PutInAdapter extends BaseAdapter {

    private List<PutInbean> datas;
    private Context mContext;

    public PutInAdapter(List<PutInbean> datas,Context context) {
        this.datas = datas;
        mContext = context;
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public Object getItem(int position) {
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = View.inflate(mContext, R.layout.work_item, null);
        TextView name = (TextView) view.findViewById(R.id.name);
        TextView count = (TextView) view.findViewById(R.id.count);
        PutInbean putInbean = datas.get(position);
        name.setText(putInbean.name);
        count.setText(putInbean.count+"");


        return view;
    }
}

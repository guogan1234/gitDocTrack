package com.avantport.avp.njpb.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;

import com.avantport.avp.njpb.R;
import com.avantport.avp.njpb.bean.Partsbean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by len on 2017/8/29.
 * 部件的复选框
 */

public class GirgCheckAdapter extends BaseAdapter {

    private List<Partsbean.ResultsBean> datas = new ArrayList<>();
    private Context mContext;


    public GirgCheckAdapter(List<Partsbean.ResultsBean> datas, Context context) {
        this.datas = datas;
        mContext = context;
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    public List<Partsbean.ResultsBean> getDatas() {
        return datas;
    }

    @Override
    public Partsbean.ResultsBean getItem(int position) {
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Partsbean.ResultsBean item = getItem(position);
        View inflate = View.inflate(mContext, R.layout.item_checkbox, null);
        CheckBox checkbox = (CheckBox) inflate.findViewById(R.id.item_checkbox);
        checkbox.setChecked(item.isSelected());
        checkbox.setText(item.getName());
        return inflate;
    }


}

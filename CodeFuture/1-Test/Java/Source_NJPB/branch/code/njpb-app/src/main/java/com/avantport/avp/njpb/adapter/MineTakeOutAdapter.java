package com.avantport.avp.njpb.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.avantport.avp.njpb.R;

/**
 * Created by len on 2017/8/11.
 */

public class MineTakeOutAdapter extends BaseAdapter {
    private Context mContext;

    public MineTakeOutAdapter(Context context) {
        mContext = context;
    }

    @Override
    public int getCount() {
        return 5;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

      View.inflate(mContext, R.layout.item_mine_parts,null);
        return  View.inflate(mContext, R.layout.item_mine_parts,null);
    }
}

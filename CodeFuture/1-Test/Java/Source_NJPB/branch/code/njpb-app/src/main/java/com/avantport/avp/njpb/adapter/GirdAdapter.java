package com.avantport.avp.njpb.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.avantport.avp.njpb.R;
import com.bumptech.glide.Glide;
import com.yongchun.library.view.ImageSelectorActivity;

import java.util.List;

/**
 * Created by len on 2017/8/5.
 * 图片选择显示的GridView
 */

public class GirdAdapter extends BaseAdapter {

    private List<String> datas;
    private Context mContext;

    private final int ITEM_TYPE_MORE = 1;
    private final int ITEM_TYPE_ONE = 0;
    private ImageView mGird_item_pic;
    private ImageView mGird_remove;

    public GirdAdapter(List<String> datas, Context context) {
        this.datas = datas;
        mContext = context;
    }

    @Override
    public int getCount() {
        return datas.size()+1;
    }

    @Override
    public String getItem(int position) {

        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {

        if (position == datas.size()) {
            return ITEM_TYPE_ONE;
        }else {
            return ITEM_TYPE_MORE;
        }

    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        int itemViewType = getItemViewType(position);

        switch (itemViewType) {
            case ITEM_TYPE_MORE:
                if (convertView ==null) {
                    convertView = View.inflate(mContext, R.layout.gird_item_view,null);
                }
                mGird_item_pic = (ImageView) convertView.findViewById(R.id.gird_item_pic);
                mGird_remove = (ImageView) convertView.findViewById(R.id.gird_remove);

                Glide.with(mContext).load(datas.get(position)).into(mGird_item_pic);

                mGird_remove.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        datas.remove(position);
                        notifyDataSetChanged();

                    }
                });
                break;
            case ITEM_TYPE_ONE:
               convertView = View.inflate(mContext,R.layout.select_btn_view,null);
                ImageView imageView = (ImageView) convertView.findViewById(R.id.select_pic);
                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ImageSelectorActivity.start((Activity) mContext, 5, ImageSelectorActivity.MODE_MULTIPLE, true,true,true);
                    }
                });
                break;
        }
        return convertView;
    }

}

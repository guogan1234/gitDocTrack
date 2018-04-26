package com.avantport.avp.njpb.adapter;

import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 *
 **/

public class FinalListAdapter<T> extends BaseAdapter {

    private List<T> mShowItems = new ArrayList<>();

    private int mLayout;

    //定义接收的监听
    private AdapterListener mAdapterListener;

    public FinalListAdapter(List<T> showItems, int layout, AdapterListener<T> adapterListener) {
        mShowItems = showItems;
        this.mLayout = layout;
        mAdapterListener = adapterListener;
    }

    @Override
    public int getCount() {
        return mShowItems.size();
    }

    @Override
    public T getItem(int position) {
        return mShowItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup group) {
        FinalViewHolder finalViewHolder;
        if (convertView == null) {
            convertView = View.inflate(group.getContext(), mLayout, null);
            finalViewHolder = new FinalViewHolder(convertView);
            convertView.setTag(finalViewHolder);
        } else {
            finalViewHolder = (FinalViewHolder) convertView.getTag();
        }
        mAdapterListener.bindView(finalViewHolder, mShowItems.get(position));
        return convertView;
    }

    //viewholder主要功能是,找控件
    public static class FinalViewHolder {
        private View mView;

        public FinalViewHolder(View view) {
            this.mView = view;
        }

        private SparseArray<View> mSparseArray = new SparseArray<>();

        //根据id自动查找控件
        public View getViewById(int id) {
            //集合中查找,如果第一次没有就去findviewbyid,
            //如果第二次,直接得到view
            View view = mSparseArray.get(id);
            if (view == null) {
                view = mView.findViewById(id);
                mSparseArray.put(id, view);
            }
            return view;
        }

    }

    public interface AdapterListener<T> {
        //控件,viewholder
        //数据
        void bindView(FinalViewHolder finalViewHolder, T t);
    }
}

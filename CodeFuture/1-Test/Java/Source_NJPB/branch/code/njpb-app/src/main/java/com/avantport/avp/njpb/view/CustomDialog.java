package com.avantport.avp.njpb.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.avantport.avp.njpb.R;

import java.util.List;

/**
 * Created by len on 2017/8/5.
 * 自定义dialog
 */

public class CustomDialog extends Dialog implements AdapterView.OnItemClickListener {

    private  Context mContext;
    private ListView mListView;
    private List<String> datas;


    public CustomDialog(@NonNull Context context, List<String> arealist) {
        this(context,0);
        this.mContext = context;
        datas = arealist;
    }

    private CustomDialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context, R.style.customdialog);

    }

    protected CustomDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        this(context, 0);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window = getWindow();
        window.setGravity(Gravity.CENTER);
       setContentView(R.layout.dialog_view);
        mListView = (ListView) findViewById(R.id.listview);
        setCanceledOnTouchOutside(true);
        mListView.setAdapter(new ArrayAdapter<String>(mContext,android.R.layout.simple_list_item_1,datas));
        mListView.setOnItemClickListener(this);
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (mOnDialogItemClick!=null) {
            mOnDialogItemClick.getDialogItem(datas.get(position),position);
        }
    }

    //点击的借口回调
    public interface onDialogItemClick{
      void  getDialogItem(String areaName,int position);
    }

    private onDialogItemClick mOnDialogItemClick;

    public void setOnDialogItemClick(onDialogItemClick onDialogItemClick) {
        mOnDialogItemClick = onDialogItemClick;
    }
}

package com.avantport.avp.njpb.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * Created by len on 2017/8/7.
 * 解决ScrollView嵌套GridView
 */

public class SelectGirdView extends GridView {
    public SelectGirdView(Context context) {
        super(context);
    }

    public SelectGirdView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SelectGirdView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(
                Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}

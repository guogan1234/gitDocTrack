package com.avantport.avp.njpb.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.view.View;
import android.widget.TextView;

import com.avantport.avp.njpb.R;

/**
 * Created by len on 2017/8/24.
 */

public class HintDialog extends Dialog implements View.OnClickListener {

    private  String message ;

    public HintDialog(@NonNull Context context,String msg) {
        this(context,0);
        message = msg;
    }

    public HintDialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context, R.style.hintDialog);
    }

    protected HintDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        this(context, 0);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_hint_dialog);
        TextView cancel = (TextView) findViewById(R.id.cancel);
        TextView textMsg = (TextView) findViewById(R.id.text_msg);
        TextView makeSure = (TextView) findViewById(R.id.make_sure);
        textMsg.setText(message);
        cancel.setOnClickListener(this);
        makeSure.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cancel:
                if (mOnClickListaner !=null) {
                    mOnClickListaner.clickCancel();
                }
                break;
            case R.id.make_sure:
                if (mOnClickListaner!=null) {
                    mOnClickListaner.clickSure();
                }
                break;
        }
    }

    public interface  onClickListaner{
        void clickCancel();
        void clickSure();
    }

    public void setOnClickListaner(onClickListaner onClickListaner) {
        mOnClickListaner = onClickListaner;
    }

    private  onClickListaner mOnClickListaner;
}

package com.avantport.avp.njpb.view;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.avantport.avp.njpb.R;
import com.avantport.avp.njpb.ui.activity.TakeOutActivity;

/**
 * Created by len on 2017/8/8.
 */

public class VerifyDialog extends AlertDialog implements View.OnClickListener {

    private Context mContext;
    private ImageView mDialog_delect;
    private EditText mDialog_psw;
    private Button mDialog_btn;

    public VerifyDialog(@NonNull Context context) {
        this(context,0);
        mContext = context;
    }

    public VerifyDialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context, R.style.verifydialog);

    }

    protected VerifyDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        this(context, 0);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_verify_view);
        mDialog_delect = (ImageView) findViewById(R.id.dialog_delect);
        mDialog_psw = (EditText) findViewById(R.id.dialog_psw);
        mDialog_btn = (Button) findViewById(R.id.dialog_btn);

        mDialog_delect.setOnClickListener(this);
        mDialog_btn.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.dialog_delect:
                dismiss();
                break;
            case R.id.dialog_btn:
                mContext.startActivity(new Intent(mContext, TakeOutActivity.class));
              //// TODO: 2017/8/8 要进行验证密码的操作

               dismiss();
                break;
        }
    }
}

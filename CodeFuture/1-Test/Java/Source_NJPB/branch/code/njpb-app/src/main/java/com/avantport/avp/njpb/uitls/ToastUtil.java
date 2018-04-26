package com.avantport.avp.njpb.uitls;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by avp on 2017-08-02.
 * Toast 工具类
 */

public class ToastUtil {

    private ToastUtil(){}
    private static Toast toast;

    public static void show(Context context,String msg){
        if (toast == null) {
            toast = Toast.makeText(context.getApplicationContext(),msg,Toast.LENGTH_SHORT);
        }

        toast.setText(msg);
        toast.show();
    }
}

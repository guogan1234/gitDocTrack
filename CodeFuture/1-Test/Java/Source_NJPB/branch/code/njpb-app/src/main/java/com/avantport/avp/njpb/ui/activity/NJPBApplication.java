package com.avantport.avp.njpb.ui.activity;

import android.app.Application;
import android.content.Context;
import android.reader.ReaderAndroid;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.InvalidParameterException;

/**
 * Created by len on 2017/8/7.
 */

public class NJPBApplication extends Application {

    public static  Context context;
    public ReaderAndroid mSerialPort = null;
    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }

    //手持机读卡
    public void switchSerialPin(String whichPin) {
        Log.e("serialport", "write uart switch");
        File f = new File("/sys/devices/soc.0/78b0000.serial/uart_switch");
        Log.e("serialport45", "/sys/devices/soc.0/78b0000.serial/uart_switch Read:" + f.canRead() + " Write:" + f.canWrite());

        try {
            FileOutputStream e = new FileOutputStream(f);
            e.write(whichPin.getBytes());
            e.close();
        } catch (IOException var4) {
            var4.printStackTrace();
            Log.e("chou", "switchSerialPin fail ");
        }
    }


    public ReaderAndroid getSerialPort() throws SecurityException, IOException, InvalidParameterException {
        if (mSerialPort == null) {
            String path = "/dev/ttyHSL0";
            int baudrate = 9600;

			/* Open the serial port */
            this.switchSerialPin(new String("uart3"));

            mSerialPort = new ReaderAndroid(new File(path), baudrate);
        }
        return mSerialPort;
    }

    public void closeSerialPort() {
        if (mSerialPort != null) {
            mSerialPort.close(mSerialPort.getHandle());
            mSerialPort = null;
        }
    }
}

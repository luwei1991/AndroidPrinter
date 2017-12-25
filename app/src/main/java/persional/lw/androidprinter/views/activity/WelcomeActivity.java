package persional.lw.androidprinter.views.activity;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Iterator;

import persional.lw.androidprinter.MainApplication;
import persional.lw.androidprinter.R;
import persional.lw.androidprinter.util.Constant;



/**
 * Created by 陆伟 on 2017/12/5.
 */

public class WelcomeActivity extends Activity{
    /**1s后跳转到主页面*/
    private static final int sleepTime = 1000;
    private UsbManager usbManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);



    }



    @Override
    protected void onStart() {
        super.onStart();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(sleepTime);
                    startActivity(new Intent(WelcomeActivity.this, HomeActivity.class));
                    finish();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();


    }

}

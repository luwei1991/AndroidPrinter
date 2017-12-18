package persional.lw.androidprinter.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import org.greenrobot.eventbus.EventBus;

import persional.lw.androidprinter.MainApplication;
import persional.lw.androidprinter.model.event.DataReceiveEvent;
import persional.lw.androidprinter.util.StringUtil;

/**
 * 启动读取数据服务
 * Created by 陆伟 on 2017/11/29.
 */

public class ReadDataService extends Service{

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        startReadThread();
        return super.onStartCommand(intent, flags, startId);
    }


    /**
     * 开启服务读取数据
     */
    private void startReadThread(){
        Thread readThread = new Thread(){
            @Override
            public void run() {
                byte[] buffer = new byte[4096];
                int i = 0;
                while (true) {
                    int length = MainApplication.driver.ReadData(buffer, 4096);
                    if (length > 0) {
                        String recv = StringUtil.toHexString(buffer, length);
                        //通过EventBus传递数据
                        EventBus.getDefault().post(new DataReceiveEvent(recv));
                        Log.d("=====",recv);
                    }
                    //测试代码
//                    if(i%2==0){
//                        EventBus.getDefault().post(new DataReceiveEvent("ff 01 00 0a fd"));
//                    }else{
//                        EventBus.getDefault().post(new DataReceiveEvent("ff 00 00 0a fd"));
//                    }

                    i++;
                    try {
                        Thread.sleep(10000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };

        readThread.start();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();


    }

}

package persional.lw.androidprinter.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;


import persional.lw.androidprinter.MainApplication;
import persional.lw.androidprinter.util.Constant;

/**
 * 启动读取数据服务
 * Created by 陆伟 on 2017/11/29.
 */

public class SendDataService extends Service{

    @Override
    public void onCreate() {
        super.onCreate();
        startSendThread();

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }


    /**
     * 开启服务读取数据
     */
    private void startSendThread(){
        Thread readThread = new Thread(){
            @Override
            public void run() {
                while (true) {
                    MainApplication.driver.WriteData(Constant.PrinterStatus.OK,Constant.PrinterStatus.OK.length);
                    try {
                        Thread.sleep(500);
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
        stopSelf();
    }

}

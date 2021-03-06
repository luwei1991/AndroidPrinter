package persional.lw.androidprinter.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

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
                    }
                }
            }
        };

        readThread.start();
    }

//    private String getCurTime(){
//        Date date = new Date();
//        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd :hh:mm:ss");
//        return dateFormat.format(date);
//
//    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopSelf();
        EventBus.getDefault().unregister(this);
    }

}

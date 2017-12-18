package persional.lw.androidprinter.service;

import android.app.PendingIntent;
import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;

import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.RemoteViews;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import persional.lw.androidprinter.R;
import persional.lw.androidprinter.model.PrinterModel;
import persional.lw.androidprinter.model.event.DataReceiveEvent;
import persional.lw.androidprinter.util.PrinterStatusUtil;
import persional.lw.androidprinter.views.activity.PrintWidget;

/**
 * Created by 陆伟 on 2017/12/15.
 */

public class UpdateWidgetService extends Service {
    private PrinterModel printerModel;
    public static String TAG = "UpdateWidgetService";

    public UpdateWidgetService(){

    }

    @Override
    public void onCreate() {
        super.onCreate();
        EventBus.getDefault().register(this);
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(DataReceiveEvent event) {
        String msg = event.getMessage();
        printerModel = PrinterStatusUtil.getPrintStatus(msg);
        Log.d("===LW",printerModel+"");
        ComponentName componentName = new ComponentName(UpdateWidgetService.this, PrintWidget.class);
        RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.print_widget);
        if(printerModel.getConnection() == R.string.printer_connection_c){
            remoteViews.setImageViewResource(R.id.iv_connection,R.mipmap.float_connection);
        }else{
            remoteViews.setImageViewResource(R.id.iv_connection,R.mipmap.float_disconnection);
        }
        //设置装纸监听
        Intent intent = new Intent(PrintWidget.ACTION_PAPER);
        intent.putExtra(PrintWidget.NUM_INTENT,printerModel.getConnection());
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this,-1,intent, PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setOnClickPendingIntent(R.id.iv_in_or_out,pendingIntent);
        //设置联机、脱机监听
        Intent conIntent = new Intent(PrintWidget.ACTION_CONNETION);
        conIntent.putExtra(PrintWidget.NUM_INTENT,printerModel.getConnection());
        PendingIntent conPendIntent = PendingIntent.getBroadcast(this,-1,conIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setOnClickPendingIntent(R.id.iv_connection,conPendIntent);

        AppWidgetManager.getInstance(this).updateAppWidget(componentName,remoteViews);




    }
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        stopSelf();
    }
}

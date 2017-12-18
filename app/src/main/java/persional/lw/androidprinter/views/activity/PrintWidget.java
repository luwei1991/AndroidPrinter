package persional.lw.androidprinter.views.activity;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;

import persional.lw.androidprinter.MainApplication;
import persional.lw.androidprinter.R;
import persional.lw.androidprinter.util.Constant;

/**
 * 桌面组件
 * Implementation of App Widget functionality.
 */
public class PrintWidget extends AppWidgetProvider {
    private static String TAG = "PrintWidget";
    public static String NUM_INTENT = "neft.yyjcb";
    public static String ACTION_CONNETION = "lw.persional.connection";
    public static String ACTION_PAPER = "lw.persional.paper";
    private final Intent EXAMPLE_SERVICE_INTENT =
            new Intent("lw.persional.updataservice");


    private RemoteViews mRemoteViews;
    private ComponentName mComponentName;


    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        CharSequence widgetText = context.getString(R.string.appwidget_text);
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.print_widget);

        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        mRemoteViews = new RemoteViews(context.getPackageName(), R.layout.print_widget);
        Intent skipIntent = new Intent(context, HomeActivity.class);
        PendingIntent pi = PendingIntent.getActivity(context, 200, skipIntent, PendingIntent.FLAG_CANCEL_CURRENT);
        mRemoteViews.setOnClickPendingIntent(R.id.iv_logo, pi);
        mComponentName = new ComponentName(context, PrintWidget.class);
        AppWidgetManager.getInstance(context).updateAppWidget(mComponentName,mRemoteViews);


    }

    @Override
    public void onEnabled(Context context) {
        context.startService(EXAMPLE_SERVICE_INTENT);
    }

    @Override
    public void onDisabled(Context context) {
        context.stopService(EXAMPLE_SERVICE_INTENT);
    }

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        super.onDeleted(context, appWidgetIds);
        context.stopService(EXAMPLE_SERVICE_INTENT);
    }


    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("===Lw",intent.getAction());
         if(intent.getAction().equals(ACTION_CONNETION)){
            int uuu = intent.getIntExtra(NUM_INTENT,0);
            if(uuu == R.string.printer_connection_c){
                sendByte(Constant.PrinterCode.DISCONNECTION);
            }else{
                sendByte(Constant.PrinterCode.CONNECTION);
            }
        } else if(intent.getAction().equals(ACTION_PAPER)){
             int uuu = intent.getIntExtra(NUM_INTENT,0);
             if(uuu == R.string.printer_connection_c){
                 sendByte(Constant.PrinterCode.PAPER_CONNECTION);
             }else{
                 sendByte(Constant.PrinterCode.PAGE_DISCONNECTION);
             }
         }

        super.onReceive(context, intent);
    }






    /**
     * 发送数据
     */
    private void sendByte(byte[] bytes){
        MainApplication.driver.WriteData(bytes,bytes.length);
    }
}


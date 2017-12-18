package persional.lw.androidprinter.presenter;

import android.app.Dialog;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import persional.lw.androidprinter.MainApplication;
import persional.lw.androidprinter.R;
import persional.lw.androidprinter.contract.HomeContract;
import persional.lw.androidprinter.model.event.DataReceiveEvent;
import persional.lw.androidprinter.model.PrinterModel;
import persional.lw.androidprinter.util.Constant;
import persional.lw.androidprinter.util.DialogUtil;
import persional.lw.androidprinter.util.PrinterStatusUtil;
import persional.lw.androidprinter.views.activity.HomeActivity;

/**
 * Created by 陆伟 on 2017/12/12.
 */

public class HomePresenter implements HomeContract.Presenter{
    private static String TAG = "HomePresenter";
    private HomeContract.View view;
    private PrinterModel printerModel;

    public HomePresenter(HomeContract.View view){
        this.view = view;
        view.setPresenter(this);
    }

    @Override
    public void start() {
        EventBus.getDefault().register(this);
        loadPrinterInfo();

    }

    @Override
    public void loadPrinterInfo() {
        byte[] code = view.loadCode();
        sendByte(code);
    }




    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(DataReceiveEvent event) {
        String msg = event.getMessage();
        printerModel = PrinterStatusUtil.getPrintStatus(msg);
        view.showPrinterInfo(printerModel);
    }
    /**发送重启指令*/
    public void sendStartCode(){
        sendByte(Constant.PrinterCode.RESTART);
    }

    /**
     * 联机命令
     */
    @Override
    public void connection() {
        sendByte(Constant.PrinterCode.CONNECTION);

    }

    /**
     * 脱机命令
     */
    @Override
    public void disconnection() {
        sendByte(Constant.PrinterCode.DISCONNECTION);
    }

    /**
     * 处理联机脱机指令
     */
    @Override
    public void dealConnection() {
        if(null == printerModel){
            Log.d(TAG,"printerModel is null");
            return;
        }
        if(printerModel.getConnection() == R.string.printer_connection_c){
           disconnection();
        }else {
            connection();
        }

    }


    /**
     * 发送数据
     */
    private void sendByte(byte[] bytes){
        MainApplication.driver.WriteData(bytes,bytes.length);
    }


}

package persional.lw.androidprinter.presenter;

import android.util.Log;
import android.widget.Toast;

import org.apache.commons.lang3.SystemUtils;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.SimpleDateFormat;
import java.util.Date;

import persional.lw.androidprinter.MainApplication;
import persional.lw.androidprinter.R;
import persional.lw.androidprinter.contract.ConnectionContract;
import persional.lw.androidprinter.model.PrinterModel;
import persional.lw.androidprinter.model.event.DataReceiveEvent;
import persional.lw.androidprinter.util.Constant;
import persional.lw.androidprinter.util.LogUtils;
import persional.lw.androidprinter.util.PrinterStatusUtil;

/**
 * Created by 陆伟 on 2017/12/12.
 */

public class ConnectionFragmentPresenter implements ConnectionContract.Presenter {
    private static String TAG = "ConnectionFragmentPresenter";
    private ConnectionContract.View view;
    private PrinterModel printerModel;

    public ConnectionFragmentPresenter(ConnectionContract.View view){
        this.view = view;
        view.setPresenter(this);
    }

    @Override
    public void start() {
        EventBus.getDefault().register(this);
    }

    /**
     * 拷贝能力切换
     */
    @Override
    public void changeCopy() {
        if(null == printerModel){
            Log.d(TAG,"printerModel is null");
            return;
        }

        if(printerModel.getConnection() == R.string.printer_connection_c){
            sendByte(Constant.PrinterCode.COPY_CONNECTION);
        }else {
            Toast.makeText(MainApplication.instance,"拷贝切换只能在联机下进行",Toast.LENGTH_LONG).show();
        }



    }


    private String getCurTime(){
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd :hh:mm:ss");
        return dateFormat.format(date);

    }

    /**
     * 速度切换
     */
    @Override
    public void changeSpeed() {
        Log.d("====lw   111",getCurTime());
        if(null == printerModel){
            Log.d(TAG,"printerModel is null");
            return;
        }
        if(printerModel.getConnection() == R.string.printer_connection_c){
            sendByte(Constant.PrinterCode.SPEED_CONNECTION);
        }else {
            sendByte(Constant.PrinterCode.SPEED_DISCONNECTION);
        }

    }

    /**
     * 压缩能力切换
     */
    @Override
    public void changeCompress() {
        if(null == printerModel){
            Log.d(TAG,"printerModel is null");
            return;
        }
        if(printerModel.getConnection() == R.string.printer_connection_c){
            sendByte(Constant.PrinterCode.COMPRESS_CONNECTION);
        }else {
            sendByte(Constant.PrinterCode.COMPRESS_DISCONNECTION);
        }

    }

    /**
     * 单连页切换
     */
    @Override
    public void changePage() {
        if(null == printerModel){
            Log.d(TAG,"printerModel is null");
            return;
        }
        if(printerModel.getConnection() == R.string.printer_connection_c){
            sendByte(Constant.PrinterCode.PAGE_CONNECTION);
        }else {
            sendByte(Constant.PrinterCode.PAGE_DISCONNECTION);
        }

    }

    /**
     * 装纸
     */
    @Override
    public void changePaper() {
        if(null == printerModel){
            Log.d(TAG,"printerModel is null");
            return;
        }

        if(printerModel.getConnection() == R.string.printer_connection_c){
            //联机下
            sendByte(Constant.PrinterCode.PAPER_CONNECTION);
        }else {
            sendByte(Constant.PrinterCode.PAPER_DISCONNECTION);
        }
    }

    /**
     * 设置页长命令
     */
    @Override
    public void setPageLong(byte b) {
        LogUtils.debug(b+"");
        byte [] pageLongByte = new byte[]{0x0c,0x06,b,0x03};
        sendByte(pageLongByte);
    }

    @Override
    public int getConnection() {
        if(null != printerModel){
            return printerModel.getConnection();
        }else {
            return R.string.printer_connection_d;
        }

    }



    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(DataReceiveEvent event) {
        printerModel = PrinterStatusUtil.getPrintStatus(event.getMessage());
        view.showPrinterInfo(printerModel);
    }

    /**
     * 发送数据
     */
    private void sendByte(byte[] bytes){
        MainApplication.driver.WriteData(bytes,bytes.length);
    }





}

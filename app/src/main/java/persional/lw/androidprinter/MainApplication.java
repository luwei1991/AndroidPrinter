package persional.lw.androidprinter;

import android.app.Application;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbManager;

import android.widget.Toast;



import cn.wch.ch34xuartdriver.CH34xUARTDriver;
import persional.lw.androidprinter.service.ReadDataService;
import persional.lw.androidprinter.service.SendDataService;
import persional.lw.androidprinter.util.Constant;
import persional.lw.androidprinter.util.Usbdriver;


/**
 * 程序入口
 * Created by 陆伟 on 2017/11/14.
 */

public class MainApplication extends Application {

    public static MainApplication instance;

    public static CH34xUARTDriver driver;

    @Override
    public void onCreate() {
        super.onCreate();
        getInstance();
        //初始化驱动
        initDriverData();
        //初始化服务
        initService();
        initUsbDriver();



    }

    public MainApplication getInstance(){
        return instance = this;
    }

    /**
     * 初始化参数
     */
    private void initDriverData(){

        //初始化串口驱动
        driver =  new CH34xUARTDriver((UsbManager) getSystemService(Context.USB_SERVICE), this, Constant.ACTION_USB_PERMISSION);
        if(driver.isConnected()){
            Toast.makeText(this,"设备已经连接！",Toast.LENGTH_SHORT).show();
        }
        //判断设备是否支持USB_HOST
        //打开设备
        // 返回0则打开设备成功，否则失败
        if(!(MainApplication.driver.ResumeUsbList() == Constant.ENUM_SUCESS)){
            Toast.makeText(this,R.string.enum_fail,Toast.LENGTH_SHORT).show();
            return;
        }
        //串口初始化
        if(MainApplication.driver.UartInit()){
            //设置串口参数
            try{
                if( MainApplication.driver.SetConfig(Constant.SerialPort.BAUDRATE
                        ,Constant.SerialPort.DATA_BIT
                        ,Constant.SerialPort.STOP_BIT
                        ,Constant.SerialPort.PARITY
                        ,Constant.SerialPort.FLOW_CONTROLLER)){
                    Toast.makeText(this,R.string.port_suc,Toast.LENGTH_SHORT).show();
                }
            }catch (NullPointerException e){
                Toast.makeText(this,R.string.port_fail,Toast.LENGTH_SHORT).show();
            }


        }else {
            Toast.makeText(this,R.string.permission_usb,Toast.LENGTH_SHORT).show();
            System.exit(0);
        }
    }

    /**
     * 初始化usb驱动
     */
    private void initUsbDriver()
        {
            Usbdriver usbdriver = new Usbdriver();
    }


    /**
     * 初始化相应的服务
     */
    private void initService(){
        Intent intent = new Intent(this, ReadDataService.class);
        this.startService(intent);
        Intent intentSend = new Intent(this, SendDataService.class);
        this.startService(intentSend);
    }


    /**
     * 获取屏幕宽 高
     * @return
     */
    public  int getScreenWidth(){
        return  getInstance().getResources().getDisplayMetrics().widthPixels;
    }

    public  int getScreenHeight(){
        return  getInstance().getResources().getDisplayMetrics().heightPixels;
    }

    /**
     * 获取屏幕密度
     * @return
     */
    public float getDensity(){
        return getInstance().getResources().getDisplayMetrics().density;
    }


}

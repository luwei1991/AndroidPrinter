package persional.lw.androidprinter;

import android.app.Application;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.hardware.usb.UsbManager;
import android.support.v7.app.AlertDialog;
import android.view.WindowManager;
import android.widget.Toast;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

import cn.wch.ch34xuartdriver.CH34xUARTDriver;
import persional.lw.androidprinter.service.ReadDataService;
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
        //判断设备是否支持USB_HOST
        checkDevice();
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

    }


    /**
     * 检查设置是否支持USB_HOST
     */
    private void checkDevice(){
        if (!MainApplication.driver.UsbFeatureSupported()){// 判断系统是否支持USB HOST
            Toast.makeText(this,R.string.not_support_usb,Toast.LENGTH_SHORT).show();
            System.exit(0);
        }
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

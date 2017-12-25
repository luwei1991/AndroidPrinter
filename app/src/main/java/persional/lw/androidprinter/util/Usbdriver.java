package persional.lw.androidprinter.util;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbDeviceConnection;
import android.hardware.usb.UsbEndpoint;
import android.hardware.usb.UsbInterface;
import android.hardware.usb.UsbManager;
import android.hardware.usb.UsbRequest;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Iterator;

import persional.lw.androidprinter.MainApplication;
import persional.lw.androidprinter.R;
import persional.lw.androidprinter.model.UsbErrorerModel;


/**
 *  USB连接驱动
 * Created by 陆伟 on 2017/11/30.
 */

public class Usbdriver {
    private static String TAG = "Usbdriver";
    private static UsbManager usbManager;

    private static UsbEndpoint mUsbEndpointIn;
    private static UsbEndpoint mUsbEndpointOut;

    private static UsbInterface mUsbInterface;

    private static UsbDeviceConnection connection;


    private  static Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 0x566:
                    if(null != msg.obj){
                        Toast.makeText(MainApplication.instance,MainApplication.instance.getString((int)msg.obj),Toast.LENGTH_LONG).show();
                    }
                    break;
            }
        }
    };

    static {

        open();
    }

    public Usbdriver(){

    }

    /**
     * 打来USB通信
     * @return
     */
    public static boolean open(){
        UsbDevice mDevice = enumDevice();
        if(null != mDevice ){
             UsbErrorerModel usbErrorerModel =  findAndSetPort(mDevice);
            if(null != usbErrorerModel && usbErrorerModel.isSuccess()){
                toastMessage(usbErrorerModel.getCode());
                return true;
            }else{
                toastMessage(usbErrorerModel.getCode());
                return false;
            }
        }else {
            toastMessage(R.string.usb_device_no);
            return false;
        }
    }

    /**
     * 关闭连接
     * @param
     */
    public void close(){
        if(connection != null){
            connection.close();
        }
    }

    /**
     * 写入数据
     * @param bytes
     * @return
     */
    public boolean writeData(byte[] bytes){
            try {
                int recv =  connection.bulkTransfer(mUsbEndpointOut,bytes,bytes.length,1500);
                if(recv < 0){
                    Log.d(TAG,recv+"");
                    return false;
                }else {
                    Log.d(TAG,recv+"");
                    return true;
                }
            }catch (Exception e){
                toastMessage(R.string.usb_conncetion_err);
                close();
                return  false;
            }
        }

    /**
     * 读取数据
     */
    public String readData() {
        if (open()) {
//        int outMax = mUsbEndpointOut.getMaxPacketSize();
            int inMax = mUsbEndpointIn.getMaxPacketSize();
            ByteBuffer byteBuffer = ByteBuffer.allocate(inMax);
            UsbRequest usbRequest = new UsbRequest();
            usbRequest.initialize(connection, mUsbEndpointIn);
            usbRequest.queue(byteBuffer, inMax);
            if (connection.requestWait() == usbRequest) {
                byte[] retData = byteBuffer.array();
                return StringUtil.toHexString(retData, retData.length);
            }
        }
        return "";
    }

    /**
     * 枚举设备信息
     * @return
     */
    private static UsbDevice enumDevice(){
        usbManager = (UsbManager) MainApplication.instance.getSystemService(Context.USB_SERVICE);
        HashMap<String,UsbDevice> usbDeviceHashMap = usbManager.getDeviceList();
        Iterator<UsbDevice> deviceIterator = usbDeviceHashMap.values().iterator();
        UsbDevice mDevice = null;
        while (deviceIterator.hasNext()){
            UsbDevice usbDevice = deviceIterator.next();
            if(usbDevice.getProductId() == Constant.UsbDevice.PID && usbDevice.getVendorId() == Constant.UsbDevice.VID){
                Log.d(TAG,"PID："+usbDevice.getProductId());
                mDevice = usbDevice;
            }
        }
        return  mDevice;
    }

    /**
     * Toast
     * @param id
     */
    private static  void toastMessage(int id){
        Message message = Message.obtain();
        message.what= 0x566;
        message.obj = id;
        handler.sendMessage(message);
    }



    /**
     * 建立通信
     * @param device
     */
    private static UsbErrorerModel findAndSetPort(UsbDevice device){
        UsbErrorerModel usbErrorerModel = new UsbErrorerModel();
        //获取设备接口一般只有一个接口 一个接口上2个端点
        for(int i = 0; i < device.getInterfaceCount();){
            mUsbInterface = device.getInterface(i);
            break;
        }

        if(!usbManager.hasPermission(device)){
            PendingIntent pendingIntent = PendingIntent.getBroadcast(MainApplication.instance,0,new Intent(Constant.ACTION_USB_PERMISSION),0);
            IntentFilter filter = new IntentFilter(Constant.ACTION_USB_PERMISSION);
            MainApplication.instance.registerReceiver(mUsbReceiver,filter);
            usbManager.requestPermission(device,pendingIntent);
            usbErrorerModel.setSuccess(false);
            usbErrorerModel.setCode(R.string.usb_device_permission_no);
            return usbErrorerModel;
        }

        UsbDeviceConnection conn = usbManager.openDevice(device);
        if(conn == null){
            usbErrorerModel.setSuccess(false);
            usbErrorerModel.setCode(R.string.usb_conncetion_err);
            return usbErrorerModel;
        }

        if(conn.claimInterface(mUsbInterface,true)){
            connection = conn;
            mUsbEndpointOut = mUsbInterface.getEndpoint(1);
            mUsbEndpointIn = mUsbInterface.getEndpoint(0);
        }

        if(connection !=null && mUsbEndpointOut !=null && mUsbEndpointIn != null){
            usbErrorerModel.setSuccess(true);
            usbErrorerModel.setCode(R.string.usb_conncetion_suc);
            return usbErrorerModel;
        }else{
            usbErrorerModel.setSuccess(false);
            usbErrorerModel.setCode(R.string.usb_device_set_err);
            return usbErrorerModel;
        }
    }




    public static final BroadcastReceiver mUsbReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (Constant.ACTION_USB_PERMISSION.equals(action)) {
                synchronized (this) {
                    UsbDevice device = intent.getParcelableExtra(UsbManager.EXTRA_DEVICE);
                    if (intent.getBooleanExtra(UsbManager.EXTRA_PERMISSION_GRANTED, false)) {
                        if(device != null){
                            Toast.makeText(context,"权限已经获取到，请重启APP",Toast.LENGTH_SHORT).show();
                        }
                    }

                }
            }
        }
    };


}

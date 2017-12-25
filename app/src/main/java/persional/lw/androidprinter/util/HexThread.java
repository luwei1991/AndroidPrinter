package persional.lw.androidprinter.util;

/**
 * Created by 陆伟 on 2017/12/6.
 */

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import org.apache.commons.io.output.ByteArrayOutputStream;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.Arrays;

import persional.lw.androidprinter.MainApplication;

/**
 * 升级线程
 */
class HexThread extends Thread{
    private String path;
    private Handler handler;

    public HexThread(String path,Handler handler){
        this.path = path;
        this.handler = handler;
    }

    @Override
    public void run() {
        Log.d("====lw","开始固件升级！");
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        InputStream is = null;
        Usbdriver usbdriver = new Usbdriver();
        byte[]  buff = new byte[1024 * 3];
        try {
            is = new FileInputStream(path);
            int len = -1;
            //先读取数据
            while ((len = is.read(buff)) != -1){
                outStream.write(buff,0,len);
            }
            byte[] data = outStream.toByteArray();
            int j = Constant.UsbDevice.BUFFER_SIZE;
            float curData = 0;
            if((data.length) % j == 0){
                //如果能被整除
                for(int i = 0;i < (data.length/j);i++){
                    byte[] hex01 = Arrays.copyOfRange(data,j*i,j * (i+1));
                    usbdriver.writeData(hex01);
                    curData  = curData +j;
                    Message message = Message.obtain();
                    DecimalFormat df = new DecimalFormat("0.00");//格式化小数
                    String num = df.format((float)curData/(data.length));//返回的是String类型
                    message.what = 0x56;
                    message.obj = curData/(data.length);
                    handler.sendMessage(message);
                }
            }else{
                //不能被整除
                for(int i = 0;i < (data.length/j)+1;i++){
                    if(i == (data.length/j)){
                        byte[] hex02 =  Arrays.copyOfRange(data,j*i,data.length);
                        usbdriver.writeData(hex02);
                    }else{
                        byte[] hex01 = Arrays.copyOfRange(data,j*i,j * (i+1));
                        usbdriver.writeData(hex01);
                    }
                    curData  = curData +j;
                    Message message = Message.obtain();
                    DecimalFormat df = new DecimalFormat("0.00");//格式化小数
                    String num = df.format((float)curData/(data.length));//返回的是String类型
                    message.what = 0x56;
                    Log.d("====lw",Float.parseFloat(num)+"");
                    message.obj = Float.parseFloat(num);
                    handler.sendMessage(message);
                }
            }
            //执行完毕
            handler.sendEmptyMessage(0x57);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                outStream.close();
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}

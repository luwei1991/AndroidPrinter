package persional.lw.androidprinter.util;

import java.util.HashMap;

import persional.lw.androidprinter.R;
import persional.lw.androidprinter.model.PrinterModel;

/**
 * 打印机状态
 * Created by 陆伟 on 2017/11/28.
 */

public class PrinterStatusUtil {

    /**返回打印机信息*/
    public static PrinterModel getPrintStatus(String str){
        byte bitOne = (byte) Integer.parseInt(str.split(" ")[1],16);
        byte bitSec = (byte) Integer.parseInt(str.split(" ")[2],16);
        HashMap<Integer,Integer>  map = explainStatus(bitOne,bitSec);
        PrinterModel printerModel = new PrinterModel();
        printerModel.setPage(map.get(Constant.PrinterStatus.PAGE));
        printerModel.setCopy(map.get(Constant.PrinterStatus.COPY));
        printerModel.setSpeed(map.get(Constant.PrinterStatus.SPEED));
        printerModel.setCompress(map.get(Constant.PrinterStatus.COMPRESS));
        printerModel.setConnection(map.get(Constant.PrinterStatus.CONNECTION));
        printerModel.setPaper(map.get(Constant.PrinterStatus.PAPER));
        printerModel.setStatus(map.get(Constant.PrinterStatus.STATUS));
        return printerModel;
    }




    /**
     * 解析状态一字段的信息
     * @return
     */
    private  static HashMap<Integer,Integer>  explainStatus(byte oneBit,byte secBit){
        HashMap<Integer,Integer> statusMap = new HashMap<Integer,Integer>();
        /*********解析状态字一**********************/
        byte [] oneStatus =  getBooleanArray(oneBit);//返回8位二进制数据[0,1,0,0,0,0,0,0]高位------->低位
        /**第一位表示单页连页的状态 1：联页  0：单页*/
        if(oneStatus[0] == 0){
            statusMap.put(Constant.PrinterStatus.PAGE, R.string.printer_page_one );
        }else{
            statusMap.put(Constant.PrinterStatus.PAGE,R.string.printer_page_more);
        }

        /**第二、三位表示状态为拷贝能力   00: 低拷贝  10:高拷贝  01: 中拷贝*/
        if(oneStatus[1] == 0 && oneStatus[2] == 0){
            statusMap.put(Constant.PrinterStatus.COPY,R.string.printer_copy_low);
        }

        if(oneStatus[1] == 1 && oneStatus[2] == 0){
            statusMap.put(Constant.PrinterStatus.COPY,R.string.printer_copy_high);
        }

        if(oneStatus[1] == 0 && oneStatus[2] == 1){
            statusMap.put(Constant.PrinterStatus.COPY,R.string.printer_copy_middle);
        }

        /**第四、五位表示状态为打印速度   00: 标准速度  10:超高速  01: 高速*/
        if(oneStatus[3] == 0 && oneStatus[4] == 0){
            statusMap.put(Constant.PrinterStatus.SPEED,R.string.printer_speed_low);
        }

        if(oneStatus[3] == 1 && oneStatus[4] == 0){
            statusMap.put(Constant.PrinterStatus.SPEED,R.string.printer_speed_high);
        }

        if(oneStatus[3] == 0 && oneStatus[4] == 1){
            statusMap.put(Constant.PrinterStatus.SPEED,R.string.printer_speed_middle);
        }

        /**第六、七位表示状态为压缩状态   00: 低压缩  10:高压缩  01: 中压缩*/
        if(oneStatus[5] == 0 && oneStatus[6] == 0){
            statusMap.put(Constant.PrinterStatus.COMPRESS,R.string.printer_compress_low);
        }

        if(oneStatus[5] == 1 && oneStatus[6] == 0){
            statusMap.put(Constant.PrinterStatus.COMPRESS,R.string.printer_compress_high);
        }

        if(oneStatus[5] == 0 && oneStatus[6] == 1){
            statusMap.put(Constant.PrinterStatus.COMPRESS,R.string.printer_compress_middle);
        }
        /**第八位表示状态为脱机和联机状态 0：脱机 1：联机*/
        if(oneStatus[7] == 0){
            statusMap.put(Constant.PrinterStatus.CONNECTION,R.string.printer_connection_d);
        }else{
            statusMap.put(Constant.PrinterStatus.CONNECTION,R.string.printer_connection_c);
        }

        /*********解析状态字二信息***********/
        byte[] secStatus = getBooleanArray(secBit);//返回8位二进制数据[0,1,0,0,0,0,0,0]高位------->低位
        /**第一位为 报警\正常  0:正常 1:报警*/
        if(secStatus[0] == 0){
            statusMap.put(Constant.PrinterStatus.STATUS,R.string.printer_status_normal);
        }else{
            statusMap.put(Constant.PrinterStatus.STATUS,R.string.printer_status_alarm);
        }
        /**第四、五、六位为特殊模式*/
        if(secStatus[6] == 0 && secStatus[5]== 0 && secStatus[4] == 0){//0表示NORMAL
            statusMap.put(Constant.PrinterStatus.SPECMODE,R.string.printer_status_special_normal);
        }else if(secStatus[6] == 1 && secStatus[5]== 0 && secStatus[4] == 0){//1自检
            statusMap.put(Constant.PrinterStatus.SPECMODE,R.string.printer_status_special_self);
        }else if(secStatus[6] == 0 && secStatus[5]== 1 && secStatus[4] == 0){//2竖线调整
            statusMap.put(Constant.PrinterStatus.SPECMODE,R.string.printer_status_special_verticl);
        }else if(secStatus[6] == 1 && secStatus[5]== 1 && secStatus[4] == 0){//3首行调整
            statusMap.put(Constant.PrinterStatus.SPECMODE,R.string.printer_status_special_first_line);
        }else if(secStatus[6] == 0 && secStatus[5]== 0 && secStatus[4] == 1){//4清单打印
            statusMap.put(Constant.PrinterStatus.SPECMODE,R.string.printer_status_special_bill);
        }else if(secStatus[6] == 1 && secStatus[5]== 0 && secStatus[4] == 1){//e2prom
            statusMap.put(Constant.PrinterStatus.SPECMODE,R.string.printer_status_special_init);
        }


        /**第八位是表示 有纸\无纸 0：有纸 1：无纸*/
        if(secStatus[7] == 0){
            statusMap.put(Constant.PrinterStatus.PAPER,R.string.printer_paper_yes);
        }else {
            statusMap.put(Constant.PrinterStatus.PAPER,R.string.printer_paper_no);
        }

        return  statusMap;
    }






    /**
     * 将byte 转换成byte[]
     * @param b
     * @return
     */
    public  static  byte[] getBooleanArray(byte b) {
        byte[] array = new byte[8];
        for (int i = 7; i >= 0; i--) {
            array[i] = (byte)(b & 1);
            b = (byte) (b >> 1);
        }
        return array;
    }




}

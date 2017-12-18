package persional.lw.androidprinter.util;

import android.os.Environment;

import java.util.HashMap;

/**
 * 定义一些常量
 * Created by 陆伟 on 2017/11/22.
 */

public  class Constant {

    public static final String  FILE_PATH = Environment.getExternalStorageDirectory().getPath() + "/FUJITSU";


    /**权限常量*/
    public static final String ACTION_USB_PERMISSION = "persional.lw.androidprinters.USB_PERMISSION";
    /**设备枚举返回成功标识*/
    public static final int ENUM_SUCESS = 0;
    /**进入工厂模式的密码*/
    public static final String PASSWORD = "123456";


    /**链接串口参数*/
    public static  class SerialPort{
        /**波特率*/
        public static int BAUDRATE = 9600;
        /**停止位 //  0：表示1个停止位, 1：表示2个停止位，默认为1个停止位 */
        public static byte STOP_BIT = 0;
        /**数据位*/
        public static byte DATA_BIT = 8;
        /**PARITY   //  0:none ,1:add ,2:even,3:mark,4:space*/
        public static byte PARITY = 0;
        /**flowController //0:none,1:cts/rts*/
        public static byte FLOW_CONTROLLER = 0;
    }
    /**打印机状态常量*/
    public static class PrinterStatus{
        /**用于检测打印机状态*/
        public static byte[] OK = {0x4f,0x4b};
        public static byte[] WORD = {0x1b,0x4a};
        /**单页/联页*/
        public static int PAGE =  0x01;
        /**拷贝能力*/
        public static int COPY =  0x02;
        /**打印机速度*/
        public static int SPEED = 0x03;
        /**压缩能力*/
        public static int COMPRESS = 0x04;
        /**联机/脱机*/
        public static int CONNECTION = 0x05;
        /**报警/正常*/
        public static int STATUS = 0x06;
        /**特殊模式*/
        /**NORMAL/自检/竖线调整/首行调整/清单d打印/e2prom初始化*/
        public static int SPECMODE = 0x07;
        /**有纸/无纸*/
        public static int PAPER = 0x08;
    }

    /**
     * 广播常量
     */
    public static class BroadCast{
        /**Action*/
       public static String  READ_RECEIVER_ACTION = "readAction";
        /**intent常量*/
       public static String  INTENT = "intent";

    }

    /**
     * 设备pid vid
     */
    public static class UsbDevice{

        /**PT80 VID PID*/
        public static int PID = 4207;
        public static int VID = 1221;
        /**DPK200 VID PID*/
//        public static int PID = 513;
//        public static int VID = 10633;
        public static int BUFFER_SIZE = 1024;




    }

    /**
     * 打印机发送码
     */
    public static class PrinterCode {
        /**打印机重启命令*/
        public static byte[] RESTART = {(byte) 0xc0,0x20,0,0x03};
        /**联机下压缩能力切换命令*/
        public static byte[] COMPRESS_CONNECTION = {(byte) 0xf0, (byte) 0x80,0x00,0x03};
        /**脱机下压缩能力切换命令*/
        public static byte[] COMPRESS_DISCONNECTION = {0x0f, (byte) 0x10,0x00,0x03};
        /**单页、连页切换命令联机*/
        public static byte[] PAGE_CONNECTION = {(byte) 0xf0,0x40,0x00,0x03};
        /**单页、连页切换命令脱机*/
        public static byte[] PAGE_DISCONNECTION = {0x0f,0x40,0x00,0x03};
        /**速度切换 联机*/
        public static byte[] SPEED_CONNECTION = {(byte) 0xf0,0x20,0x00,0x03};
        /**速度切换 脱机*/
        public static byte[] SPEED_DISCONNECTION = {0x0f,0x20,0x00,0x03};
        /**拷贝能力*/
        public static byte[] COPY_CONNECTION = {(byte) 0xf0,0x04,0x00,0x03};
        /**装/卸纸命令 联机*/
        public static byte[] PAPER_CONNECTION = {(byte) 0xf0,0x08,0x00,0x03};
        /**装/卸纸命令 脱机*/
        public static byte[] PAPER_DISCONNECTION = {0x0f,0x04,0x00,0x03};
        /**脱机*/
        public static byte[] DISCONNECTION = {(byte) 0xf0,0x01,0x00,0x03};
        /**联机*/
        public static byte[] CONNECTION = {0x0f,0x01,0x00,0x03};
        /**清单打印*/
        public static byte[] BILL_PRINT = {(byte) 0xc0, (byte) 0x80,0x00,0x03};
        /**菜单初始化*/
        public static byte[] OPTION_INIT= {(byte) 0xc0,0x08,0x00,0x03};
        /**十六进制转储*/
        public static byte[] SITEEN= {(byte) 0xc0,0x10,0x00,0x03};
        /**EEPROM*/
        public static byte[] EEPROM_INIT= {(byte) 0xc0, (byte) 0xa0,0x00,0x03};
        /**PIT1*/
        public static byte[] PIT1= {(byte) 0xc0, (byte) 0xc0,0x00,0x03};


        /**自检模式*/
        public static byte[] SELF_PRINT= {(byte) 0xc0,0x01,0x00,0x03};
        /**自检模式 打印下一个编码区*/
        public static byte[] SELF_PRINT_NEXT_CODE= {(byte) 0xc0,0x01, (byte) 0x80,0x03};
        /**自检模式 速度切换*/
        public static byte[] SELF_PRINT_SPEED_SWITCH= {(byte) 0xc0,0x01,0x40,0x03};
        /**自检模式 暂停/继续*/
        public static byte[] SELF_PRINT_START_STOP= {(byte) 0xc0,0x01,0x20,0x03};
        /**自检模式 退出*/
        public static byte[] SELF_PRINT_EXIT= {(byte) 0xc0,0x01,0x10,0x03};
        /**自检模式 进纸*/
        public static byte[] SELF_PRINT_IN_PAPER= {(byte) 0xc0,0x01,0x08,0x03};


        /**竖线调整模式*/
        public static byte[] VERTICAL_CHANGE= {(byte) 0xc0,0x02,0x00,0x03};
        /**竖线调整模式 进纸*/
        public static byte[] VERTICAL_IN_OUT_PAPER = {(byte) 0xc0,0x02, (byte) 0x80,0x03};
        /**竖线调整模式 向左调整*/
        public static byte[] VERTICAL_LEFT= {(byte) 0xc0,0x02,0x40,0x03};
        /**竖线调整模式 向右调整*/
        public static byte[] VERTICAL_RIGHT= {(byte) 0xc0,0x02,0x20,0x03};
        /**退出保存*/
        public static byte[] VERTICAL_EXIT_SAVE= {(byte) 0xc0,0x02,0x10,0x03};
        /**速度切换*/
        public static byte[] VERTICAL_SPEED_SWITCH= {(byte) 0xc0,0x02,0x08,0x03};
        /**退出不保存*/
        public static byte[] VERTICAL_EXIT_NOT_SAVE= {(byte) 0xc0,0x02,0x04,0x03};


        /**首行调整模式*/
        public static byte[] FIRST_LINE = {(byte) 0xc0,0x04,0x00,0x03};
        public static byte[] FIRST_LINE_TO_LITTLE = {(byte) 0xc0,0x04, (byte) 0x80,0x03};
        public static byte[] FIRST_LINE_BACK_LITTLE = {(byte) 0xc0,0x04,0x40,0x03};
        public static byte[] FIRST_LINE_EXIT_SAVE= {(byte) 0xc0,0x04,0x20,0x03};
        public static byte[] FIRST_LINE_IN_OUT_PAPER = {(byte) 0xc0,0x04,0x10,0x03};
        public static byte[] FIRST_LINE_BASE_POSTION = {(byte) 0xc0,0x04,0x08,0x03};
        public static byte[] FIRST_LINE_EXIT_NOT_SAVE = {(byte) 0xc0,0x04,0x04,0x03};













        /**页长对应指令*/
        public static byte[] PAGE_LONG = {0x18,0x19,0x1a,0x1b,0x1c,0x1e,0x1f,0x20,0x24,0x00};
        /**页长命令无效、有效*/
        public static byte[] PAGE_LONG_CODE_YES = {0x0c,0x30,0x01,0x03};
        public static byte[] PAGE_LONG_CODE_NO = {0x0c,0x30,0x00,0x03};

        /**首行单页,连页上端余白*/
        public static byte[] TOP_OUT = {0x00,0x01,0x02,0x03,0x04,0x05,0x06,0x07,0x08,0x09};
        /**首行单页，连页微调*/
        public static byte[] PAGE_CHANGE = {0x00,0x01,0x02,0x03,0x04,0x05,0x06,0x07,0x08,0x09,0x0a,0x0b,0x0c,0x0d,0x0e,0x0f,0x10,
        0x11,0x12,0x13,0x14,0x15,0x16,0x17,0x18,0x19,0x1a,0x1b,0x1c,0x1d};


















    }


}

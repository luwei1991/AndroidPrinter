package persional.lw.androidprinter.presenter;

import android.os.Handler;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import persional.lw.androidprinter.MainApplication;
import persional.lw.androidprinter.R;
import persional.lw.androidprinter.contract.FcatoryFragementContract;
import persional.lw.androidprinter.model.PrinterModel;
import persional.lw.androidprinter.model.event.DataReceiveEvent;
import persional.lw.androidprinter.util.Constant;
import persional.lw.androidprinter.util.HexUpdate;
import persional.lw.androidprinter.util.PrinterStatusUtil;
import persional.lw.androidprinter.util.Usbdriver;

/**
 * Created by 陆伟 on 2017/12/15.
 */

public class FactoryPresenter implements FcatoryFragementContract.Presenter{
    private static String TAG = "FactoryPresenter";
    private FcatoryFragementContract.View view;

    private PrinterModel printerModel;

    public FactoryPresenter(FcatoryFragementContract.View view){
        this.view = view;
        view.setPresenter(this);
    }

    @Override
    public void start() {
        EventBus.getDefault().register(this);
    }

    @Override
    public void hexUpdate(String path, Handler handler) {
            Usbdriver usbdriver = new Usbdriver();
            if(usbdriver.open()){//如果打开成功
                usbdriver.writeData(new byte[]{0x1B,0x7C,0x7F,0x7F,(byte) 0xFF});
                HexUpdate hexUpdate = new HexUpdate(path,handler);
                hexUpdate.startUpdate();
            }else{
                Toast.makeText(MainApplication.instance,"USB没有连接无法升级！",Toast.LENGTH_LONG).show();
            }
    }

    @Override
    public void billPrint() {
        sendByte(Constant.PrinterCode.BILL_PRINT);
    }

    /**
     * 进入自检模式
     */
    @Override
    public void inSelfPrint() {
        sendByte(Constant.PrinterCode.SELF_PRINT);
    }

    /**
     * 打印下一个编码区
     */
    @Override
    public void selfNextCode() {
        sendByte(Constant.PrinterCode.SELF_PRINT_NEXT_CODE);
    }

    /**
     * 速度切换
     */
    @Override
    public void selfSpeedSwitch() {
        sendByte(Constant.PrinterCode.SELF_PRINT_SPEED_SWITCH);
    }

    /**
     * 暂停/继续
     */
    @Override
    public void selfStartOrOn() {
        sendByte(Constant.PrinterCode.SELF_PRINT_START_STOP);
    }

    /**
     * 自检模式下进纸
     */
    @Override
    public void selfInPaper() {
        sendByte(Constant.PrinterCode.SELF_PRINT_IN_PAPER);
    }

    /**
     * 退出自检模式
     */
    @Override
    public void selfExit() {
        sendByte(Constant.PrinterCode.SELF_PRINT_EXIT);
    }

    /**
     * 进入竖线调整模式
     */
    @Override
    public void inVertical() {
        sendByte(Constant.PrinterCode.VERTICAL_CHANGE);
    }

    /**
     * 竖线模式下进纸
     */
    @Override
    public void verticalInOutPaper() {
        sendByte(Constant.PrinterCode.VERTICAL_IN_OUT_PAPER);
    }

    /**
     * 竖线下的向左调整
     */
    @Override
    public void verticalLeft() {
        sendByte(Constant.PrinterCode.VERTICAL_LEFT);
    }

    /**
     * 竖线向右调整
     */
    @Override
    public void verticalRight() {
        sendByte(Constant.PrinterCode.VERTICAL_RIGHT);
    }

    /**
     * 退出保存
     */
    @Override
    public void vreticalExitSave() {
        sendByte(Constant.PrinterCode.VERTICAL_EXIT_SAVE);
    }

    /**
     * 竖线模式下速度切换
     */
    @Override
    public void verticalSpeed() {
        sendByte(Constant.PrinterCode.VERTICAL_SPEED_SWITCH);
    }
    /**
     * 退出不保存
     */
    @Override
    public void verticalExitNotSave() {
        sendByte(Constant.PrinterCode.VERTICAL_EXIT_NOT_SAVE);
    }

    /**
     * 进入首行设置模式
     */
    @Override
    public void inFirstLine() {
        sendByte(Constant.PrinterCode.FIRST_LINE);

    }

    @Override
    public void firsLineBacklittle() {
        sendByte(Constant.PrinterCode.FIRST_LINE_BACK_LITTLE);


    }

    @Override
    public void firstLineToLittle() {
        sendByte(Constant.PrinterCode.FIRST_LINE_TO_LITTLE);


    }

    @Override
    public void firstLineExitSave() {
        sendByte(Constant.PrinterCode.FIRST_LINE_EXIT_SAVE);


    }

    @Override
    public void firstLineInOutPaper() {
        sendByte(Constant.PrinterCode.FIRST_LINE_IN_OUT_PAPER);


    }

    @Override
    public void firsLineBasePostion() {
        sendByte(Constant.PrinterCode.FIRST_LINE_BASE_POSTION);


    }

    @Override
    public void firstLineExitNotSave() {
        sendByte(Constant.PrinterCode.FIRST_LINE_EXIT_NOT_SAVE);
    }




    @Override
    public void sixteenPrint() {
        sendByte(Constant.PrinterCode.SITEEN);
    }

    @Override
    public void optioninti() {
        sendByte(Constant.PrinterCode.OPTION_INIT);

    }

    @Override
    public void eepromInit() {
        sendByte(Constant.PrinterCode.EEPROM_INIT);
    }

    @Override
    public void evenPageSetting() {
        Toast.makeText(MainApplication.instance,"暂定！",Toast.LENGTH_LONG).show();
    }

    @Override
    public void piT1Mode() {

        sendByte(Constant.PrinterCode.PIT1);
    }

    @Override
    public void savePrint() {
        sendByte(Constant.PrinterCode.SITEEN);
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(DataReceiveEvent event) {
        printerModel = PrinterStatusUtil.getPrintStatus(event.getMessage());
    }

    @Override
    public boolean getPaper(){
        if(null != printerModel && printerModel.getPaper() == R.string.printer_paper_yes) {
            return  true;
        }else {
            return false;
        }
    }


    /**
     * 发送数据
     */
    private void sendByte(byte[] bytes){
        MainApplication.driver.WriteData(bytes,bytes.length);
    }

}

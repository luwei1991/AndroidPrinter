package persional.lw.androidprinter.presenter;

import persional.lw.androidprinter.MainApplication;
import persional.lw.androidprinter.contract.FirstLineContract;

/**
 * Created by 陆伟 on 2017/12/13.
 */

public class FirstLinePresenter implements FirstLineContract.Presenter {
    private static String TAG = "FirstLinePresenter";

    private FirstLineContract.View view;

    public FirstLinePresenter(FirstLineContract.View view){
        this.view = view;
        view.setPresenter(this);

    }


    @Override
    public void start() {

    }


    /**
     * 选择单页余白
     */
    @Override
    public void singleBlank(byte b) {
        byte []  bytes = new byte[]{0x0c,0x08,b,0x03};
        sendByte(bytes);
    }

    /**
     * 单页微调
     */
    @Override
    public void singleTrim(byte b) {
        byte []  bytes = new byte[]{0x0c,0x09,b,0x03};
        sendByte(bytes);
    }

    /**
     * 连页余白
     */
    @Override
    public void evenBlank(byte b) {
        byte []  bytes = new byte[]{0x0c,0x0a,b,0x03};
        sendByte(bytes);

    }

    /**
     * 连页微调
     */
    @Override
    public void evenTrim(byte b) {
        byte []  bytes = new byte[]{0x0c,0x0b,b,0x03};
        sendByte(bytes);


    }

    /**
     * 发送配置好的代码
     */
    @Override
    public void sendFirstLineCode() {

    }


    /**
     * 发送数据
     */
    private void sendByte(byte[] bytes){
        MainApplication.driver.WriteData(bytes,bytes.length);
    }


}

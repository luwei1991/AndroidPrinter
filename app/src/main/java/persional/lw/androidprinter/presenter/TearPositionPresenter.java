package persional.lw.androidprinter.presenter;

import persional.lw.androidprinter.MainApplication;
import persional.lw.androidprinter.contract.TearPostionContract;
import persional.lw.androidprinter.util.Constant;

/**
 * Created by 陆伟 on 2017/12/25.
 */

public class TearPositionPresenter implements TearPostionContract.Presenter {
    private static String TAG = "TearPositionPresenter";
    private TearPostionContract.View view;


    public TearPositionPresenter(TearPostionContract.View view){
        this.view = view;
        view.setPresenter(this);

    }

    @Override
    public void start() {

    }

    @Override
    public void save() {
        sendByte(Constant.PrinterCode.EVEN_TEAR_EXIT_SAVE);
    }

    @Override
    public void exit() {
        sendByte(Constant.PrinterCode.EVEN_TEAR_EXIT_NOT_SAVE);
    }

    @Override
    public void backWard() {
        sendByte(Constant.PrinterCode.EVEN_TEAR_BACK);
    }

    @Override
    public void forWard() {
        sendByte(Constant.PrinterCode.EVEN_TEAR_FORWARD);
    }

    @Override
    public void inTear() {
        sendByte(Constant.PrinterCode.EVEN_TEAR);
    }

    /**
     * 发送数据
     */
    private void sendByte(byte[] bytes){
        MainApplication.driver.WriteData(bytes,bytes.length);
    }

}

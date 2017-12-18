package persional.lw.androidprinter.presenter;

import persional.lw.androidprinter.MainApplication;
import persional.lw.androidprinter.contract.EvenPageSettingContract;

/**
 * Created by 陆伟 on 2017/12/14.
 */

public class EvenPageSettingPresenter implements EvenPageSettingContract.Presenter {
    private static String TAG = "EvenPageSettingPresenter";
    private EvenPageSettingContract.View view;

    public EvenPageSettingPresenter(EvenPageSettingContract.View view){
        this.view = view;
        view.setPresenter(this);

    }

    @Override
    public void start() {

    }

    @Override
    public void openCode() {

    }

    @Override
    public void closeCode() {

    }

    /**
     * 发送数据
     */
    private void sendByte(byte[] bytes){
        MainApplication.driver.WriteData(bytes,bytes.length);
    }
}

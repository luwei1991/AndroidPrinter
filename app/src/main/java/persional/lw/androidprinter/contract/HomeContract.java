package persional.lw.androidprinter.contract;

import persional.lw.androidprinter.model.PrinterModel;
import persional.lw.androidprinter.presenter.BasePresenter;

/**
 * Created by 陆伟 on 2017/12/12.
 */

public interface HomeContract {
    interface View extends BaseView<Presenter>{
        void showPrinterInfo(PrinterModel printerModel);//将打印机返回信息回调
        byte[] loadCode();//接口需要code
        void reStartPrinter();
    }

    interface Presenter extends BasePresenter{
        void loadPrinterInfo();
        void sendStartCode();
        void connection();
        void disconnection();
        void dealConnection();
    }
}

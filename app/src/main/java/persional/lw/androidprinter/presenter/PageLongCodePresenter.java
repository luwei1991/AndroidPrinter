package persional.lw.androidprinter.presenter;

import android.content.Context;
import android.content.Intent;

import persional.lw.androidprinter.MainApplication;
import persional.lw.androidprinter.contract.PageLongCodeContract;
import persional.lw.androidprinter.util.Constant;

/**
 * Created by 陆伟 on 2017/12/14.
 */

public class PageLongCodePresenter implements PageLongCodeContract.Presenter {
    private static String TAG = "PageLongCodePresenter";
    private PageLongCodeContract.View view;

    public PageLongCodePresenter(PageLongCodeContract.View view){
        this.view = view;
        view.setPresenter(this);

    }


    @Override
    public void start() {

    }

    @Override
    public void openCode() {
        sendByte(Constant.PrinterCode.PAGE_LONG_CODE_YES);
    }

    @Override
    public void closeCode() {
        sendByte(Constant.PrinterCode.PAGE_LONG_CODE_NO);
    }



    /**
     * 发送数据
     */
    private void sendByte(byte[] bytes){
        MainApplication.driver.WriteData(bytes,bytes.length);
    }


}

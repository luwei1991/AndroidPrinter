package persional.lw.androidprinter.contract;

import android.content.Context;

import persional.lw.androidprinter.presenter.BasePresenter;

/**
 * Created by 陆伟 on 2017/12/12.
 */

public interface PageLongCodeContract {
    interface View extends BaseView<Presenter>{

    }

    interface Presenter extends BasePresenter{
        //打开页长命令
        void openCode();
        //关闭页长命令
        void closeCode();
    }
}

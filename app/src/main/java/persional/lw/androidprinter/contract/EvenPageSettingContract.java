package persional.lw.androidprinter.contract;

import persional.lw.androidprinter.presenter.BasePresenter;

/**
 * Created by 陆伟 on 2017/12/14.
 */

public interface EvenPageSettingContract {
    interface View extends BaseView<Presenter>{

    }
    interface Presenter extends BasePresenter{
        //打开连页命令
        void openCode();
        //关闭连页命令
        void closeCode();

    }
}

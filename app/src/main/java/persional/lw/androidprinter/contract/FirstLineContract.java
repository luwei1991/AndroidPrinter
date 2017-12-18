package persional.lw.androidprinter.contract;

import persional.lw.androidprinter.presenter.BasePresenter;

/**
 * Created by 陆伟 on 2017/12/13.
 */

public interface FirstLineContract {

    interface View extends BaseView<Presenter>{

    }

    interface Presenter extends BasePresenter{
        void sendFirstLineCode();
        void singleBlank(byte b);
        void singleTrim(byte b);
        void evenBlank(byte b);
        void evenTrim(byte b);


    }

}

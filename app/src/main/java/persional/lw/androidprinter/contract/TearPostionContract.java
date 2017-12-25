package persional.lw.androidprinter.contract;

import persional.lw.androidprinter.presenter.BasePresenter;

/**
 * Created by 陆伟 on 2017/12/12.
 */

public interface TearPostionContract {
    interface View extends BaseView<Presenter>{

    }

    interface Presenter extends BasePresenter{
        void save();
        void exit();
        void backWard();
        void forWard();
        void inTear();
    }
}

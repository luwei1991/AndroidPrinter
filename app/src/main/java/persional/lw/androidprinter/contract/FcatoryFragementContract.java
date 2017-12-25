package persional.lw.androidprinter.contract;

import android.os.Handler;

import persional.lw.androidprinter.presenter.BasePresenter;

/**
 * Created by 陆伟 on 2017/12/15.
 */

public interface FcatoryFragementContract {

    interface View extends BaseView<Presenter>{
        void showFilePiccker();
        void showProgresBarPopView(String path);
        void showSelfPopView();
        void showVerticalView();
        void showFirstLineView();
        void showTestPrint();


    }
    interface Presenter extends BasePresenter{
        void hexUpdate(String path, Handler handler);
        void billPrint();

        void inSelfPrint();
        void selfNextCode();
        void selfSpeedSwitch();
        void selfStartOrOn();
        void selfInPaper();
        void selfExit();

        void inVertical();
        void verticalInOutPaper();
        void verticalLeft();
        void verticalRight();
        void vreticalExitSave();
        void verticalSpeed();
        void verticalExitNotSave();

        void inFirstLine();
        void firsLineBacklittle();
        void firstLineToLittle();
        void firstLineExitSave();
        void firstLineInOutPaper();
        void firsLineBasePostion();
        void firstLineExitNotSave();


        void sixteenPrint();
        void optioninti();
        void eepromInit();
        void evenPageSetting();
        void piT1Mode();
        void savePrint();
        boolean getPaper();

    }
}

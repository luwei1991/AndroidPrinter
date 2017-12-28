package persional.lw.androidprinter.views.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.support.v4.app.FragmentTransaction;
import android.widget.Toast;


import persional.lw.androidprinter.R;
import persional.lw.androidprinter.contract.FcatoryFragementContract;
import persional.lw.androidprinter.presenter.FactoryPresenter;
import persional.lw.androidprinter.views.activity.TestPrintActivity;
import persional.lw.androidprinter.views.widget.CircleProgressBar;
import persional.lw.androidprinter.views.widget.wheelview.picker.FilePicker;

/**
 * 工厂页面
 * Created by 陆伟 on 2017/11/29.
 */

public class FactoryFragment extends BaseFragment implements FcatoryFragementContract.View,View.OnClickListener{
    public static String TAG = "FactoryFragment";
    private FcatoryFragementContract.Presenter presenter;
    private RelativeLayout rlHex,rlBill,rlSelf,rlVertical,rlFirstLine,rlPrintTest,
            rlSixten,rlOptionInit,rlEepromInit,rlEvenpage,rlPIT1Mode,rlSavePrint;

    private Button btnBack;
    private View rootView;
    private PopupWindow popupWindow;
    private CircleProgressBar circleProgressBar;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 0x56:
                    Log.d("===",msg.obj+"");
                    circleProgressBar.setProgress((float) msg.obj);
                    break;
                case 0x57:
                    if(null != popupWindow){
                        popupWindow.dismiss();
                    }
                    Toast.makeText(getActivity(),"固件升级完毕!，请在打印机三灯全灭后重启打印机设备！",Toast.LENGTH_LONG).show();
                    break;
            }

        }
    };


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new FactoryPresenter(this);
        presenter.start();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_factory,null);
        initView(view);
        return view;
    }

    /**
     * 初始化布局
     */
    private void initView(View rootView){
        this.rootView = rootView;
        rlHex = rootView.findViewById(R.id.rl_hex);
        rlHex.setOnClickListener(this);
        rlBill = rootView.findViewById(R.id.rl_bill);
        rlBill.setOnClickListener(this);
        rlSelf = rootView.findViewById(R.id.rl_self);
        rlSelf.setOnClickListener(this);
        rlVertical = rootView.findViewById(R.id.rl_vertical);
        rlVertical.setOnClickListener(this);
        rlFirstLine = rootView.findViewById(R.id.rl_first_line);
        rlFirstLine.setOnClickListener(this);
        rlSixten = rootView.findViewById(R.id.rl_sixteen);
        rlSixten.setOnClickListener(this);
        rlOptionInit = rootView.findViewById(R.id.rl_option_init);
        rlOptionInit.setOnClickListener(this);
        rlEepromInit = rootView.findViewById(R.id.rl_eeprom);
        rlEepromInit.setOnClickListener(this);
        rlEvenpage = rootView.findViewById(R.id.rl_even_page);
        rlEvenpage.setOnClickListener(this);
        rlPIT1Mode = rootView.findViewById(R.id.rl_pit1);
        rlPIT1Mode.setOnClickListener(this);
        rlSavePrint = rootView.findViewById(R.id.rl_save_print);
        rlSavePrint.setOnClickListener(this);
        rlPrintTest = rootView.findViewById(R.id.rl_test_print);
        rlPrintTest.setOnClickListener(this);
        btnBack = rootView.findViewById(R.id.bt_back);
        btnBack.setOnClickListener(this);


    }


    @Override
    public void setPresenter(FcatoryFragementContract.Presenter presenter) {
        this.presenter = presenter;

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rl_hex:
                showFilePiccker();
                break;
            case R.id.rl_bill:
                presenter.billPrint();
                break;
            case R.id.rl_self:
                showSelfPopView();
                break;
            case R.id.rl_vertical:
                showVerticalView();
                break;
            case R.id.rl_first_line:
                showFirstLineView();
                break;
            case R.id.rl_sixteen:
                presenter.sixteenPrint();
                break;
            case R.id.rl_option_init:
                presenter.optioninti();
                break;
            case R.id.rl_eeprom:
                presenter.eepromInit();
                break;
            case R.id.rl_even_page:
                presenter.evenPageSetting();
                break;
            case R.id.rl_pit1:
                presenter.piT1Mode();
                break;
            case R.id.rl_save_print:
                presenter.savePrint();
                break;
            case R.id.rl_test_print:
                showTestPrint();
                break;
            case R.id.bt_back:
                FragmentTransaction fra = getActivity().getSupportFragmentManager().beginTransaction();
                fra.replace(R.id.fl_content,new ConnectionFragment());
                fra.commit();
                break;
        }

    }

    @Override
    public void showFilePiccker() {
        if(presenter.getPaper()){
            FilePicker picker = new FilePicker(getActivity(), FilePicker.FILE);
            picker.setShowHideDir(false);
            //picker.setAllowExtensions(new String[]{".apk"});
            picker.setFileIcon(getResources().getDrawable(android.R.drawable.ic_menu_agenda));
            picker.setFolderIcon(getResources().getDrawable(android.R.drawable.ic_menu_upload_you_tube));
            //picker.setArrowIcon(getResources().getDrawable(android.R.drawable.arrow_down_float));
            picker.setOnFilePickListener(new FilePicker.OnFilePickListener() {
                @Override
                public void onFilePicked(String currentPath) {
                    //弹出popupview显示进度条
                    showProgresBarPopView(currentPath);
                }
            });
            picker.show();
        }else{
            Toast.makeText(getActivity(),"固件升级请确保打印机为有纸状态！",Toast.LENGTH_LONG).show();
        }


    }

    /**
     * 固件升级
     * @param currentPath
     */
    @Override
    public void showProgresBarPopView(String currentPath) {
        View barPopView = LayoutInflater.from(getActivity()).inflate(R.layout.popupview_progress,null);
        circleProgressBar =  barPopView.findViewById(R.id.circle_bar);
        popupWindow = new PopupWindow(getActivity());
        popupWindow.setContentView(barPopView);
        popupWindow.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.showAtLocation(rootView, Gravity.CENTER,0,0);
        popupWindow.setOutsideTouchable(false);
        presenter.hexUpdate(currentPath,handler);
    }

    /**
     * 自检模式
     */
    @Override
    public void showSelfPopView() {
        //进入自检模式
        presenter.inSelfPrint();
        View selfPopView = LayoutInflater.from(getActivity()).inflate(R.layout.popupview_self,null);
        final PopupWindow selfWindow = new PopupWindow(getActivity());
        selfWindow.setContentView(selfPopView);
        selfWindow.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        selfWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        selfWindow.showAtLocation(rootView, Gravity.CENTER,0,0);
        selfWindow.setOutsideTouchable(false);

        Button btnNextCode,speedSwitch,startOrStop,inPaper,exit;

        btnNextCode = selfPopView.findViewById(R.id.bt_print_next_code);
        btnNextCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.selfNextCode();
            }
        });
        speedSwitch = selfPopView.findViewById(R.id.bt_speed_switch);
        speedSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.selfSpeedSwitch();
            }
        });
        startOrStop = selfPopView.findViewById(R.id.bt_start_stop);
        startOrStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.selfStartOrOn();
            }
        });
        inPaper = selfPopView.findViewById(R.id.bt_in_paper);
        inPaper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.selfInPaper();

            }
        });
        exit = selfPopView.findViewById(R.id.bt_exit);
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.selfExit();
                selfWindow.dismiss();

            }
        });
    }



    @Override
    public void showVerticalView() {
        presenter.inVertical();
        View verticalPopView = LayoutInflater.from(getActivity()).inflate(R.layout.popupview_vertical,null);
        final PopupWindow verticalWindow = new PopupWindow(getActivity());
        verticalWindow.setContentView(verticalPopView);
        verticalWindow.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        verticalWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        verticalWindow.showAtLocation(rootView, Gravity.CENTER,0,0);
        verticalWindow.setOutsideTouchable(false);

        Button btnInOutPaper,btnSpeedSwitch,btnLeft,btnRight,btnExitSave,btnExitNotSave;

        btnInOutPaper = verticalPopView.findViewById(R.id.bt_in_out_paper);
        btnInOutPaper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.verticalInOutPaper();

            }
        });
        btnSpeedSwitch = verticalPopView.findViewById(R.id.bt_speed_switch);
        btnSpeedSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.verticalSpeed();
            }
        });

        btnLeft = verticalPopView.findViewById(R.id.bt_left);
        btnLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.verticalLeft();

            }
        });

        btnRight = verticalPopView.findViewById(R.id.bt_right);
        btnRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.verticalRight();


            }
        });
        btnExitSave = verticalPopView.findViewById(R.id.bt_exit_save);
        btnExitSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.vreticalExitSave();
                verticalWindow.dismiss();
            }
        });

        btnExitNotSave = verticalPopView.findViewById(R.id.bt_exit_not_save);
        btnExitNotSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.verticalExitNotSave();
                verticalWindow.dismiss();

            }
        });
    }

    @Override
    public void showFirstLineView() {
        presenter.inFirstLine();
        View firstPopView = LayoutInflater.from(getActivity()).inflate(R.layout.popupview_firstline,null);
        final PopupWindow firstWindow = new PopupWindow(getActivity());
        firstWindow.setContentView(firstPopView);
        firstWindow.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        firstWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        firstWindow.showAtLocation(rootView, Gravity.CENTER,0,0);
        firstWindow.setOutsideTouchable(false);

        Button btnToLittle,btnBacklittle,btnInOutPaper,btnBase,btnExitSave,btnExitNotSave;

        btnToLittle = firstPopView.findViewById(R.id.bt_to_little);
        btnToLittle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.firstLineToLittle();

            }
        });

        btnBacklittle = firstPopView.findViewById(R.id.bt_back_little);
        btnBacklittle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.firsLineBacklittle();
            }
        });

        btnInOutPaper = firstPopView.findViewById(R.id.bt_in_out_paper);
        btnInOutPaper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.firstLineInOutPaper();

            }
        });

        btnBase = firstPopView.findViewById(R.id.bt_base_postion);
        btnBase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.firsLineBasePostion();


            }
        });

        btnExitSave = firstPopView.findViewById(R.id.bt_exit_save);
        btnExitSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.firstLineExitSave();
                firstWindow.dismiss();
            }
        });

        btnExitNotSave = firstPopView.findViewById(R.id.bt_exit_not_save);
        btnExitNotSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.firstLineExitNotSave();
                firstWindow.dismiss();

            }
        });

    }

    /**
     * 打印字符串测试
     */
    @Override
    public void showTestPrint() {
        Intent intent = new Intent(getActivity(), TestPrintActivity.class);
        startActivity(intent);
        }

    }

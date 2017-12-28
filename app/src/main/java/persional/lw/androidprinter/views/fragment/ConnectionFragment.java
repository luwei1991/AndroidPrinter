package persional.lw.androidprinter.views.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import persional.lw.androidprinter.R;
import persional.lw.androidprinter.contract.ConnectionContract;
import persional.lw.androidprinter.model.PrinterModel;
import persional.lw.androidprinter.presenter.ConnectionFragmentPresenter;
import persional.lw.androidprinter.util.Constant;
import persional.lw.androidprinter.views.activity.EvenPageSettingActivity;
import persional.lw.androidprinter.views.activity.EvenPageTearActivity;
import persional.lw.androidprinter.views.activity.FirstLinePostionActivity;
import persional.lw.androidprinter.views.activity.PageLongCodeActivity;
import persional.lw.androidprinter.views.widget.wheelview.WheelView;
import persional.lw.androidprinter.views.widget.wheelview.picker.OptionPicker;

/**
 *
 * 联机页面
 * Created by 陆伟 on 2017/11/29.
 */

public class ConnectionFragment extends BaseFragment implements ConnectionContract.View,View.OnClickListener {
    private static String TAG = "ConnectionFragment";

    private ConnectionContract.Presenter presenter;
    /**拷贝能力，速度,压缩能力,单页/连页，标题，装纸/卸纸,隐藏、显示*/
    private TextView tvCopy,tvSpeed,tvCompres,tvPage,tvTitle,tvInOutPaper,tvHideOrOut;
    /**拷贝能力，速度,压缩能力,单页/连页*/
    private Button btnCopy,btnSpeed,btnCompress,btnPage,btniFrstLine,btnPageLongCode,btnPageLong,btnEvenPageSetting,btnEvenPageTear;
    /**装/卸,隐藏其他功能*/
    private LinearLayout llInOutPaper,llHide,llButtons,llExit;
    private ImageView ivHideOrOut;

    private boolean isShowOther = false;





    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new ConnectionFragmentPresenter(this);
        presenter.start();
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_connection,null);
        initView(rootView);
        return rootView;
    }

    /**
     * 初始化布局
     * @param rootView
     */
    private void initView(View rootView){
        tvCopy = rootView.findViewById(R.id.tv_copy);
        tvSpeed = rootView.findViewById(R.id.tv_speed);
        tvCompres = rootView.findViewById(R.id.tv_compres);
        tvPage = rootView.findViewById(R.id.tv_page);
        tvTitle = rootView.findViewById(R.id.tv_tilte);
        tvInOutPaper = rootView.findViewById(R.id.tv_in_out_paper);
        tvHideOrOut = rootView.findViewById(R.id.tv_hide_or_out);

        btnCopy = rootView.findViewById(R.id.bt_copy);
        btnCopy.setOnClickListener(this);
        btnCompress = rootView.findViewById(R.id.bt_compress);
        btnCompress.setOnClickListener(this);
        btnSpeed = rootView.findViewById(R.id.bt_speed);
        btnSpeed.setOnClickListener(this);
        btnPage = rootView.findViewById(R.id.bt_page);
        btnPage.setOnClickListener(this);
        btniFrstLine = rootView.findViewById(R.id.bt_first_line);
        btniFrstLine.setOnClickListener(this);
        btnPageLongCode = rootView.findViewById(R.id.bt_page_long_code);
        btnPageLongCode.setOnClickListener(this);
        btnPageLong = rootView.findViewById(R.id.bt_page_long);
        btnPageLong.setOnClickListener(this);
        btnEvenPageSetting = rootView.findViewById(R.id.bt_even_page_setting);
        btnEvenPageSetting.setOnClickListener(this);
        btnEvenPageTear = rootView.findViewById(R.id.bt_even_page_tear);
        btnEvenPageTear.setOnClickListener(this);

        llInOutPaper = rootView.findViewById(R.id.ll_in_out_paper);
        llInOutPaper.setOnClickListener(this);
        llHide = rootView.findViewById(R.id.ll_hide);
        llHide.setOnClickListener(this);
        llButtons = rootView.findViewById(R.id.ll_buttons);
        ivHideOrOut = rootView.findViewById(R.id.iv_hide_or_out);

        llExit = rootView.findViewById(R.id.ll_exit);
        llExit.setOnClickListener(this);




    }


    @Override
    public void setPresenter(ConnectionContract.Presenter presenter) {
        this.presenter = presenter;

    }

    @Override
    public void showPrinterInfo(PrinterModel printerModel) {
        if(null != printerModel){
            Log.d(TAG,printerModel+"");
            tvCopy.setText(printerModel.getCopy());
            tvPage.setText(printerModel.getPage());
            tvCompres.setText(printerModel.getCompress());
            tvSpeed.setText(printerModel.getSpeed());
            if(printerModel.getConnection() == R.string.printer_connection_d){
                tvTitle.setVisibility(View.VISIBLE);
            }else{
                tvTitle.setVisibility(View.GONE);
            }

            if(printerModel.getPaper() == R.string.printer_paper_yes){
                tvInOutPaper.setText(R.string.out_paper);
            }else {
                tvInOutPaper.setText(R.string.in_paper);

            }

        }

    }

    /**
     * 显示或者隐藏
     * 默认下不显示其他功能
     */
    private void dealHideOrOut(){
        isShowOther = !isShowOther;
        if(isShowOther){
            tvHideOrOut.setText(R.string.connection_hide);
            ivHideOrOut.setBackground(getResources().getDrawable(R.mipmap.hide));
            llButtons.setVisibility(View.VISIBLE);
        }else {
            tvHideOrOut.setText(R.string.connection_spread);
            ivHideOrOut.setBackground(getResources().getDrawable(R.mipmap.out));
            llButtons.setVisibility(View.GONE);

        }

    }

    /**
     * 跳转到首行调整页面
     */
    private void toFirtlineActivity(){
        Intent  intent = new Intent(getActivity(), FirstLinePostionActivity.class);
        startActivity(intent);

    }

    /**
     * 跳转到页长
     */
    private void toPageLongCodeActivity(){
        Intent  intent = new Intent(getActivity(), PageLongCodeActivity.class);
        startActivity(intent);

    }

    /**
     * 弹出选择器
     */
    private void showWheelView(){
        OptionPicker optionPicker = new OptionPicker(getActivity(),new String[]{"2.75","3.0","3.5","11/3","4.0","5.0","5.5","6.0","8.0","11.0"});
        optionPicker.setCanceledOnTouchOutside(false);
        optionPicker.setDividerRatio(WheelView.DividerConfig.FILL);
        optionPicker.setShadowColor(Color.RED, 40);
        optionPicker.setSelectedIndex(1);
        optionPicker.setCycleDisable(true);
        optionPicker.setTextSize(11);
        optionPicker.setOnOptionPickListener(new OptionPicker.OnOptionPickListener() {
            @Override
            public void onOptionPicked(int index, String item) {
                presenter.setPageLong(Constant.PrinterCode.PAGE_LONG[index]);
//                Toast.makeText(getActivity(),"Code=" + Constant.PrinterCode.PAGE_LONG[index] + ", item=" + item,Toast.LENGTH_LONG).show();
            }
        });
        optionPicker.setLabel("IN");

        optionPicker.show();

    }

    /**
     * 跳转连页设置
     */
    private void toEvenPageChangeActivity(){
        Intent  intent = new Intent(getActivity(), EvenPageSettingActivity.class);
        startActivity(intent);

    }

    /**
     * 跳转到连页撕纸
     */
    private void toEvenPageTearActivity(){
        Intent  intent = new Intent(getActivity(), EvenPageTearActivity.class);
        startActivity(intent);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_copy:
                presenter.changeCopy();
                break;
            case R.id.bt_compress:
                presenter.changeCompress();
                break;
            case R.id.bt_speed:
                presenter.changeSpeed();
                break;
            case R.id.bt_page:
                presenter.changePage();
                break;
            case R.id.ll_in_out_paper:
                presenter.changePaper();
                break;
            case R.id.ll_hide:
                dealHideOrOut();
                break;
            case R.id.bt_first_line:
                toFirtlineActivity();
                break;
            case R.id.bt_page_long_code:
                toPageLongCodeActivity();
                break;
            case R.id.bt_page_long:
                showWheelView();
                break;
            case R.id.bt_even_page_setting:
                toEvenPageChangeActivity();
                break;
            case R.id.bt_even_page_tear:
                toEvenPageTearActivity();
                break;
            case R.id.ll_exit:
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                break;
        }

    }


}

package persional.lw.androidprinter.views.activity;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import persional.lw.androidprinter.R;
import persional.lw.androidprinter.contract.FirstLineContract;
import persional.lw.androidprinter.presenter.FirstLinePresenter;
import persional.lw.androidprinter.util.Constant;
import persional.lw.androidprinter.views.widget.wheelview.WheelView;
import persional.lw.androidprinter.views.widget.wheelview.picker.OptionPicker;

/**
 * Created by 陆伟 on 2017/12/13.
 */

public class FirstLinePostionActivity extends Activity implements FirstLineContract.View,View.OnClickListener {
    private static String TAG = "FirstLinePostionActivity";
    private FirstLineContract.Presenter presenter;
    private Button btnBack;

    private RelativeLayout rlSingleTop,rlSingleChange,rlEvenTop,rlEvenChange;

    private TextView tvSingleTop,tvSingleChange,tvEvenTop,tvEvenChange;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_line_postion);
        initView();
        new FirstLinePresenter(this);
        presenter.start();
    }

    /**
     * 初始化布局
     */
    private void initView(){
        btnBack = findViewById(R.id.bt_back);
        btnBack.setOnClickListener(this);
        rlSingleTop = findViewById(R.id.rl_single_top);
        rlSingleTop.setOnClickListener(this);
        rlSingleChange = findViewById(R.id.rl_single_change);
        rlSingleChange.setOnClickListener(this);
        rlEvenTop = findViewById(R.id.rl_even_top);
        rlEvenTop.setOnClickListener(this);
        rlEvenChange = findViewById(R.id.rl_even_change);
        rlEvenChange.setOnClickListener(this);

        tvSingleTop = findViewById(R.id.tv_single_top);
        tvSingleChange = findViewById(R.id.tv_single_change);
        tvEvenTop = findViewById(R.id.tv_even_top);
        tvEvenChange = findViewById(R.id.tv_even_change);

    }

    @Override
    public void setPresenter(FirstLineContract.Presenter presenter) {
        this.presenter = presenter;

    }

    private void showSingleTop(final TextView textView, final int type){
        OptionPicker optionPicker = new OptionPicker(this,new String[]{"1/6","2/6","3/6","4/6","5/6","6/6","7/6","8/6","9/6","10/6"});
        optionPicker.setCanceledOnTouchOutside(false);
        optionPicker.setLabel("IN");
        optionPicker.setDividerRatio(WheelView.DividerConfig.FILL);
        optionPicker.setShadowColor(Color.RED, 40);
        optionPicker.setSelectedIndex(1);
        optionPicker.setCycleDisable(true);
        optionPicker.setTextSize(11);
        optionPicker.setOnOptionPickListener(new OptionPicker.OnOptionPickListener() {
            @Override
            public void onOptionPicked(int index, String item) {
                if(0 ==type){
                    presenter.singleBlank(Constant.PrinterCode.TOP_OUT[index]);
                }else{
                    presenter.evenBlank(Constant.PrinterCode.TOP_OUT[index]);
                }

                textView.setText(item);
                Toast.makeText(FirstLinePostionActivity.this,"Code=" + Constant.PrinterCode.PAGE_LONG[index] + ", item=" + item,Toast.LENGTH_LONG).show();
            }
        });
        optionPicker.show();
    }

    private void showSinglePageChange(final TextView textView, final int type){
        OptionPicker optionPicker = new OptionPicker(this,new String[]{
                "0/180","1/180","2/180","3/180","4/180"
                ,"5/180","6/180","7/180","8/180","9/180"
                ,"10/180","11/180","12/180","13/180","14/180"
                ,"15/180","16/180","17/180","18/180","19/180"
                ,"20/180","21/180","22/180","23/180","24/180"
                ,"25/180","26/180","27/180","28/180","29/180"
                });
        optionPicker.setCanceledOnTouchOutside(false);
        optionPicker.setLabel("IN");
        optionPicker.setDividerRatio(WheelView.DividerConfig.FILL);
        optionPicker.setShadowColor(Color.RED, 40);
        optionPicker.setSelectedIndex(1);
        optionPicker.setCycleDisable(true);
        optionPicker.setTextSize(11);
        optionPicker.setOnOptionPickListener(new OptionPicker.OnOptionPickListener() {
            @Override
            public void onOptionPicked(int index, String item) {
                if(type == 0){
                    presenter.singleTrim(Constant.PrinterCode.PAGE_CHANGE[index]);
                }else{
                    presenter.evenTrim(Constant.PrinterCode.PAGE_CHANGE[index]);
                }
                textView.setText(item);
                Toast.makeText(FirstLinePostionActivity.this,"Code=" + Constant.PrinterCode.PAGE_LONG[index] + ", item=" + item,Toast.LENGTH_LONG).show();
            }
        });
        optionPicker.show();
    }





    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_back:
                finish();
                break;
            case R.id.rl_single_top:
                showSingleTop(tvSingleTop,0);
                break;
            case R.id.rl_single_change:
                showSinglePageChange(tvSingleChange,0);
                break;
            case R.id.rl_even_top:
                showSingleTop(tvEvenTop,1);
                break;
            case R.id.rl_even_change:
                showSinglePageChange(tvEvenChange,1);
                break;
        }

    }
}

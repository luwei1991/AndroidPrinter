package persional.lw.androidprinter.views.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;

import persional.lw.androidprinter.R;
import persional.lw.androidprinter.contract.EvenPageSettingContract;
import persional.lw.androidprinter.presenter.EvenPageSettingPresenter;
import persional.lw.androidprinter.util.Constant;

/**
 * 连页设置
 * Created by 陆伟 on 2017/12/14.
 */

public class EvenPageSettingActivity extends Activity implements EvenPageSettingContract.View,View.OnClickListener{
    private EvenPageSettingContract.Presenter presenter;
    private Switch btnSwitch;
    private Button btnBack;
    private ImageView printStatus;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_even_page);
        initView();
        new EvenPageSettingPresenter(this);
        presenter.start();

    }

    private void initView(){
        btnSwitch = findViewById(R.id.bt_switch);
        btnSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    presenter.openCode();
                }else{
                    presenter.closeCode();
                }
            }
        });


        btnBack = findViewById(R.id.bt_back);
        btnBack.setOnClickListener(this);

        printStatus = findViewById(R.id.iv_print_status);
        int con = getIntent().getIntExtra(Constant.BroadCast.INTENT,0);
        if(con == R.string.printer_connection_c){
            setImage(printStatus,R.mipmap.connect);
        }else{
            setImage(printStatus,R.mipmap.disconnect);
        }

    }

    @Override
    public void setPresenter(EvenPageSettingContract.Presenter presenter) {
        this.presenter =presenter;

    }

    /**
     * 根据初始化数据设置正确图片
     * @param integer
     */
    private void setImage(View view, Integer integer){
        view.setBackground(ContextCompat.getDrawable(this,integer));

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_back:
                finish();
                break;

        }
    }
}

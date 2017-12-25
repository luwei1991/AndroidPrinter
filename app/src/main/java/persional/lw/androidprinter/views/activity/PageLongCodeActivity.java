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
import persional.lw.androidprinter.contract.PageLongCodeContract;
import persional.lw.androidprinter.presenter.PageLongCodePresenter;
import persional.lw.androidprinter.util.Constant;

/**
 * Created by 陆伟 on 2017/12/13.
 */

public class PageLongCodeActivity extends Activity implements PageLongCodeContract.View,View.OnClickListener{
    private PageLongCodeContract.Presenter presenter;

    private Switch btnSwitch;
    private Button btnBack;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_long_code);
        new PageLongCodePresenter(this);
        presenter.start();
        initView();
    }

    private void initView(){

        btnSwitch = findViewById(R.id.bt_switch);
        btnSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){//选中时
                    presenter.openCode();
                }else{//非选中
                    presenter.closeCode();
                }
            }
        });

        btnBack = findViewById(R.id.bt_back);
        btnBack.setOnClickListener(this);


    }

    @Override
    public void setPresenter(PageLongCodeContract.Presenter presenter) {
        this.presenter = presenter;

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

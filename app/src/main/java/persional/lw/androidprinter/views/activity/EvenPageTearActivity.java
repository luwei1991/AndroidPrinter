package persional.lw.androidprinter.views.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import persional.lw.androidprinter.R;
import persional.lw.androidprinter.contract.TearPostionContract;
import persional.lw.androidprinter.presenter.TearPositionPresenter;

/**
 * 连页撕纸
 * Created by 陆伟 on 2017/12/14.
 */

public class EvenPageTearActivity extends Activity implements TearPostionContract.View,View.OnClickListener{
    private RelativeLayout rlTearPostion;
    private TearPostionContract.Presenter presenter;
    private View rootView;
    private Button btnBack;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_even_page_tear);
        initView();
        new TearPositionPresenter(this);
        presenter.start();

    }

    private void initView(){
        rootView = findViewById(R.id.rootview);
        rlTearPostion = findViewById(R.id.rl_tear_postion);
        rlTearPostion.setOnClickListener(this);
        btnBack = findViewById(R.id.bt_back);
        btnBack.setOnClickListener(this);
    }

    private void showPopView(){
        presenter.inTear();
        View tearPopView = LayoutInflater.from(this).inflate(R.layout.popupview_tear,null);
        final PopupWindow tearWindow = new PopupWindow(this);
        tearWindow.setContentView(tearPopView);
        tearWindow.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        tearWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        tearWindow.showAtLocation(rootView,Gravity.CENTER,0,0);
        tearWindow.setOutsideTouchable(false);
        Button btnExitSave,btnExitNotSave,btnForward,btnBackForward;
        btnForward = tearPopView.findViewById(R.id.bt_forward);
        btnForward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.forWard();
            }
        });
        btnBackForward = tearPopView.findViewById(R.id.bt_back_forward);
        btnBackForward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.backWard();
            }
        });

        btnExitSave = tearPopView.findViewById(R.id.bt_exit_save);
        btnExitSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.save();
                tearWindow.dismiss();
            }
        });

        btnExitNotSave = tearPopView.findViewById(R.id.bt_exit_not_save);
        btnExitNotSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.exit();
                tearWindow.dismiss();

            }
        });


    }

    @Override
    public void setPresenter(TearPostionContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rl_tear_postion:
                showPopView();
                break;
            case R.id.bt_back:
                finish();
                break;
        }
    }
}

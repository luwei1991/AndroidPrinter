package persional.lw.androidprinter.views.activity;

import android.app.Dialog;



import android.os.Bundle;

import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;


import persional.lw.androidprinter.R;
import persional.lw.androidprinter.contract.HomeContract;
import persional.lw.androidprinter.model.PrinterModel;
import persional.lw.androidprinter.presenter.HomePresenter;
import persional.lw.androidprinter.util.Constant;
import persional.lw.androidprinter.util.DialogUtil;
import persional.lw.androidprinter.views.fragment.ConnectionFragment;
import persional.lw.androidprinter.views.fragment.FactoryFragment;
import persional.lw.androidprinter.views.widget.ImageViewSolider;

/**
 * 主页面
 * Created by 陆伟 on 2017/11/27.
 */

public class HomeActivity extends FragmentActivity implements View.OnClickListener,HomeContract.View{
    private static String TAG = "HomeActivity";
    private HomeContract.Presenter presenter;

    private FrameLayout frameLayout;
    private ImageView ivPrintConnectStatus;
    private ImageViewSolider ivConnect;
    private LinearLayout reStart,factory;
    private FactoryFragment factoryFragment;

    private long exitTime = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initView();
        new HomePresenter(this);
        presenter.start();
        //启动服务




    }


    /**
     * 初始化布局参数
     */
    private void initView(){
        //主页设置默认页面
        frameLayout = findViewById(R.id.fl_content);
        setDefaultFragment();

        ivPrintConnectStatus = findViewById(R.id.iv_print_status);
        ivConnect = findViewById(R.id.iv_connect);
        ivConnect.setOnClickListener(this);
        reStart = findViewById(R.id.ll_restart);
        reStart.setOnClickListener(this);
        factory = findViewById(R.id.ll_factory);
        factory.setOnClickListener(this);





    }



    /**
     * 根据初始化数据设置正确图片
     * @param integer
     */
    private void setImage(View view,Integer integer){
        view.setBackground(ContextCompat.getDrawable(this,integer));

    }

    private void setDefaultFragment(){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fl_content,new ConnectionFragment());
        fragmentTransaction.commit();


    }
    @Override
    public void onClick(View v) {

        switch (v.getId()){
            //联机、脱机键
            case R.id.iv_connect:
                //处理联机、脱机指令
                presenter.dealConnection();
                break;
            case R.id.ll_factory:
                toFactory();
                break;
            //重启打印机按钮
            case R.id.ll_restart:
                reStartPrinter();
                break;
        }


    }


    /**
     * 重启打印机
     */
    @Override
    public void reStartPrinter(){
        final Dialog dialog = DialogUtil.initTwoButtonDialog(this, R.layout.dialog_two_button);
        TextView title = dialog.findViewById(R.id.dialog_title);
        title.setText("即将重启打印机");
        Button sureButton = dialog.findViewById(R.id.sure);
        sureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG,"restart");
                presenter.sendStartCode();
                dialog.dismiss();
            }
        });

    }


    /**
     * 显示工厂模式
     */
    private void toFactory(){
        if (null ==factoryFragment) {
            factoryFragment = new FactoryFragment();
        }

        if(!getVisibleFragment()){
            final Dialog dialog = DialogUtil.initTwoButtonDialog(this, R.layout.dialog_factory_button);
            TextView title = dialog.findViewById(R.id.dialog_title);
            final EditText editText = dialog.findViewById(R.id.et_password);
            title.setText("请输入工厂模式密码");
            Button sureButton = dialog.findViewById(R.id.sure);
            sureButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d(TAG, "factory");
                    String password = editText.getText().toString();
                    Log.d("====lw", password);
                    if (password.equals(Constant.PASSWORD)) {
                        FragmentManager fragmentManager = getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.fl_content, factoryFragment);
                        fragmentTransaction.commit();
                    } else {
                        Toast.makeText(HomeActivity.this, "您输入的密码不对！", Toast.LENGTH_LONG).show();
                    }
                    dialog.dismiss();
                }

            });
        }
    }

    /**
     * 判断当前fragment是否显示
     * @return
     */
    public boolean  getVisibleFragment(){
       return factoryFragment.isVisible();
    }


    @Override
    public void setPresenter(HomeContract.Presenter presenter) {
        this.presenter = presenter;

    }

    @Override
    public void showPrinterInfo(PrinterModel printerModel) {
        Log.d(TAG,printerModel+"");
        if(null != printerModel){
            //设置状态小图标,//设置联机、脱机图片(三种状态，联机、脱机、不能点击)
            if(printerModel.getConnection() == R.string.printer_connection_c){
                setImage(ivPrintConnectStatus,R.mipmap.connect);
                setImage(ivConnect,R.mipmap.ib_connect);
            }else {
                setImage(ivPrintConnectStatus,R.mipmap.disconnect);
                setImage(ivConnect,R.mipmap.ib_disconnect);
            }
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exit();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    public void exit() {
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            Toast.makeText(getApplicationContext(), "再按一次退出程序",
                    Toast.LENGTH_SHORT).show();
            exitTime = System.currentTimeMillis();
        } else {
            finish();
            System.exit(0);
        }
    }

}

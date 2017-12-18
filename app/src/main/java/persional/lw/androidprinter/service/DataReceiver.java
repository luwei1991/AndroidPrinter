package persional.lw.androidprinter.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Message;
import android.widget.Toast;

import java.util.HashMap;

import persional.lw.androidprinter.util.Constant;

/**
 * Created by 陆伟 on 2017/11/29.
 */

public class DataReceiver extends BroadcastReceiver{
    private  Infomation infomation;


    @Override
    public void onReceive(Context context, Intent intent) {
        String str = intent.getStringExtra(Constant.BroadCast.INTENT);
        infomation.getInfo(str);
    }

    public interface  Infomation{
         void getInfo(String str);

    }

    public void setInfomation(Infomation infomation){

        this.infomation = infomation;
    }
}

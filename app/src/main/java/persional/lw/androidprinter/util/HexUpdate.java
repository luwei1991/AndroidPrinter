package persional.lw.androidprinter.util;

import android.os.Handler;
import android.text.TextUtils;
import java.io.File;


/**
 * USB固件升级
 * Created by 陆伟 on 2017/12/1.
 */

public class HexUpdate {
    private String path;
    private Handler handler;

    public HexUpdate (String path,Handler handler){
        this.path = path;
        this.handler = handler;
    }


    /**
     * 启动线程发送数据
     */
    public void startUpdate(String path){
        if(!TextUtils.isEmpty(path)){
            HexThread hexThread = new HexThread(path,handler);
            hexThread.start();
        }
    }

}


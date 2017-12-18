package persional.lw.androidprinter.model;

import java.io.Serializable;

import persional.lw.androidprinter.MainApplication;

/**
 * 对象基本类
 * Created by 陆伟 on 2017/12/7.
 */

public class BaserModel implements Serializable {

    protected String getString(Integer integer){
        if(null == integer){
            return "null";
        }
        return MainApplication.instance.getString(integer);
    }

}

package persional.lw.androidprinter.model.event;

/**
 * Created by 陆伟 on 2017/12/12.
 */

public class DataReceiveEvent {
    private String message;
    public DataReceiveEvent(String msg){
        message = msg;
    }

    public String getMessage(){
        return  message;
    }
}

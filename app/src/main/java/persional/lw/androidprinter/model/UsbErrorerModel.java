package persional.lw.androidprinter.model;


/**
 * USB连接说明对象
 * Created by 陆伟 on 2017/12/7.
 */

public class UsbErrorerModel extends BaserModel {

    private boolean isSuccess;

    private Integer code;

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "成功："+isSuccess+" 错误信息："+getString(code);
    }
}

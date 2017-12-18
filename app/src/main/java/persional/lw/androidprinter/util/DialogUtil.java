package persional.lw.androidprinter.util;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import persional.lw.androidprinter.MainApplication;
import persional.lw.androidprinter.R;

/**
 * Dialog 工具类
 * Created by 陆伟 on 2017/12/8.
 */

public class DialogUtil {

    /**
     * 初始化一个两个按钮的Dialog
     * @return
     */
    public static Dialog initTwoButtonDialog(Context context,int layoutId){
        final Dialog dialog =iniDialog(context,layoutId);

        Button quitButton = dialog.findViewById(R.id.quit);
        quitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
        return dialog;
    }

    public static Dialog iniDialog(Context context, int layoutID){
        Dialog dialog = new Dialog(context,R.style.Dialog);
        dialog.setContentView(layoutID);
        Window window = dialog.getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        int width = MainApplication.instance.getScreenWidth();
        float margin = 250 * MainApplication.instance.getDensity()*2;
        params.width = Math.round(width - margin);
        window.setAttributes(params);
        dialog.setCanceledOnTouchOutside(false);
        return dialog;

    }

}

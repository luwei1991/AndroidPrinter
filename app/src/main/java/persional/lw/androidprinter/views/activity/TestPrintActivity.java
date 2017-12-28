package persional.lw.androidprinter.views.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.commons.lang3.ArrayUtils;

import java.io.UnsupportedEncodingException;

import persional.lw.androidprinter.MainApplication;
import persional.lw.androidprinter.R;
import persional.lw.androidprinter.util.Constant;
import persional.lw.androidprinter.util.Usbdriver;

/**
 * Created by 陆伟 on 2017/12/21.
 */

public class TestPrintActivity  extends Activity{

    private EditText editText;
    private Button btnSend,doWhileSend,stopSend,btnOutPaper,btnBack;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_print);

         editText = findViewById(R.id.et_string);
         btnSend = findViewById(R.id.bt_send);
         doWhileSend = findViewById(R.id.bt_while_send);
         stopSend = findViewById(R.id.bt_stop_send);
         btnOutPaper = findViewById(R.id.bt_out_paper);
         btnBack = findViewById(R.id.bt_back);
         btnBack.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 finish();
             }
         });
         btnOutPaper.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 MainApplication.driver.WriteData(Constant.PrinterCode.PAPER_CONNECTION,Constant.PrinterCode.PAGE_CONNECTION.length);
             }
         });

        doWhileSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isExit = false;
                new Thread(writeRun).start();
            }
        });

        stopSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isExit = true;
            }
        });

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String etInput = editText.getText().toString().trim();
                if(!TextUtils.isEmpty(etInput)){
                    Usbdriver usbdriver = new Usbdriver();
                    try {
                        byte[] sendBytes = etInput.getBytes("gbk");
                        sendBytes = ArrayUtils.add(sendBytes, (byte) 0xa);
                        usbdriver.writeData(sendBytes);
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }else {
                    Toast.makeText(TestPrintActivity.this,"输入的内容不能为空！",Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    boolean isExit = false;

    Runnable  writeRun = new Runnable() {
        @Override
        public void run() {
            Usbdriver usbdriver = new Usbdriver();
            String testStr = "HHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHH";

            while (!isExit){
                try {
                    usbdriver.writeData(ArrayUtils.add(testStr.getBytes("gbk"), (byte) 0xa));
                    Thread.sleep(400);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    };
}




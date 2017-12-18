package persional.lw.androidprinter.views.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import persional.lw.androidprinter.R;

/**
 * 连页撕纸
 * Created by 陆伟 on 2017/12/14.
 */

public class EvenPageTearActivity extends Activity{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_even_page_tear);
    }
}

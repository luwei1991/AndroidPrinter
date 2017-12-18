package persional.lw.androidprinter.util;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;

/**
 * Created by 陆伟 on 2017/12/16.
 */

public abstract class StateBaseDrawable extends StateListDrawable {

    protected void addState(Drawable pressed) {
        addState(new ColorDrawable(Color.TRANSPARENT), pressed);
    }

    protected void addState(Drawable normal, Drawable pressed) {
        addState(new int[]{android.R.attr.state_pressed, android.R.attr.state_enabled}, pressed);
        addState(new int[]{android.R.attr.state_enabled, android.R.attr.state_focused}, pressed);
        addState(new int[]{android.R.attr.state_enabled}, normal);
        addState(new int[]{android.R.attr.state_focused}, pressed);
        addState(new int[]{android.R.attr.state_window_focused}, normal);
        addState(new int[]{}, normal);
    }

}
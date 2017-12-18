package persional.lw.androidprinter.util;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.ColorInt;

/**
 * Created by 陆伟 on 2017/12/16.
 */

public class StateColorDrawable extends StateBaseDrawable {

    public StateColorDrawable(@ColorInt int pressedColor) {
        this(Color.TRANSPARENT, pressedColor);
    }

    public StateColorDrawable(@ColorInt int normalColor, @ColorInt int pressedColor) {
        addState(new ColorDrawable(normalColor), new ColorDrawable(pressedColor));
    }

}

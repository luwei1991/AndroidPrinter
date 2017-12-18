package persional.lw.androidprinter.views.widget;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;

/**
 * 带边框的Imageview
 * Created by 陆伟 on 2017/12/7.
 */

public class ImageViewSolider extends android.support.v7.widget.AppCompatImageView {
    public ImageViewSolider(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public ImageViewSolider(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ImageViewSolider(Context context) {
        super(context);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //获取控件需要重新绘制的区域
        Rect rect=canvas.getClipBounds();
        rect.bottom--;
        rect.right--;
        rect.top++;
        rect.left++;
        Paint paint=new Paint();
        paint.setColor(Color.parseColor("#6C7697"));
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(2);
        canvas.drawRect(rect, paint);
    }

}

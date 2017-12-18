package persional.lw.androidprinter.views.widget;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

import persional.lw.androidprinter.R;

/**
 *  CircleProgressBar
 * Created by 陆伟 on 2017/12/16.
 */

public class CircleProgressBar extends View {
    /**绘制圆周背景画笔，绘制圆周动态画笔*/
    private Paint backgroudPaint,foregroundPaint,textPaint;
    /**矩阵*/
    private RectF rectF;

    private float strokeWidth = 4;
    private float progress = 0;
    private int min = 0;
    private int max = 100;
    private int startAngle = -90;

    private int color = Color.DKGRAY;

    float textWidh;
    int textHeight;
    //字体大小
    private float textSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 50, getResources().getDisplayMetrics());






    public CircleProgressBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initPaint(context,attrs);
    }


    /**
     * 初始化画笔
     */
    private void initPaint(Context context, @Nullable AttributeSet attrs){
        rectF = new RectF();
        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.CircleProgressBar,0,0);
        //从XML获取值
        try {
            strokeWidth = typedArray.getDimension(R.styleable.CircleProgressBar_progressbarThickness,strokeWidth);
            progress = typedArray.getFloat(R.styleable.CircleProgressBar_progres,progress);
            color = typedArray.getInt(R.styleable.CircleProgressBar_progressbarColor,color);
            min = typedArray.getIndex(R.styleable.CircleProgressBar_min);
            max = typedArray.getIndex(R.styleable.CircleProgressBar_max);
        }finally {
            typedArray.recycle();
        }
        //底色圈
        backgroudPaint = new Paint(Paint.ANTI_ALIAS_FLAG);//消除锯齿
        backgroudPaint.setColor(adjustAlpha(color, 0.3f));
        backgroudPaint.setStyle(Paint.Style.STROKE);//只绘制轮廓
        backgroudPaint.setStrokeWidth(strokeWidth);
        //动态圈
        foregroundPaint = new Paint(Paint.ANTI_ALIAS_FLAG);//消除锯齿
        foregroundPaint.setColor(adjustAlpha(color, 0.3f));
        foregroundPaint.setStyle(Paint.Style.STROKE);//只绘制轮廓
        foregroundPaint.setStrokeWidth(strokeWidth);
        //文字画笔
        textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);//消除锯齿
        textPaint.setColor(adjustAlpha(Color.BLUE, 0.3f));
        textPaint.setTextSize(textSize);
        textWidh = textPaint.measureText(progress + "%");
        textHeight = (int) Math.ceil((textPaint.getFontMetrics().descent - textPaint.getFontMetrics().ascent +2)) ;


    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        final int height = getDefaultSize(getSuggestedMinimumHeight(),heightMeasureSpec);
        final int widh = getDefaultSize(getSuggestedMinimumWidth(),widthMeasureSpec);
        final int min = Math.min(widh,height);
        setMeasuredDimension(min,min);
        rectF.set(0 + strokeWidth / 2, 0 + strokeWidth / 2, min - strokeWidth / 2, min - strokeWidth / 2);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawOval(rectF,backgroudPaint);
        float angle = 360 * progress / max;
        canvas.drawArc(rectF, startAngle, angle, false, foregroundPaint);
        canvas.drawText(progress + "%", getMeasuredWidth()/2 - textWidh / 2, getMeasuredWidth()/2 + textHeight / 4, textPaint);

    }

    /**
     * 设置进度
     */
    public void setProgress(float progress){
        this.progress = progress;
        invalidate();//提示不停的画
    }

    public void setProgressWithAnimation(float progress) {
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(this, "progress", progress);//反射到setProgress()方法
        objectAnimator.setDuration(1500);
        objectAnimator.setInterpolator(new DecelerateInterpolator());
        objectAnimator.start();
    }

    /**
     * 增加alpha的值
     * @param color
     * @param factor
     * @return
     */
    private int adjustAlpha(int color, float factor) {
        int alpha = Math.round(Color.alpha(color) * factor);
        int red = Color.red(color);
        int green = Color.green(color);
        int blue = Color.blue(color);
        return Color.argb(alpha, red, green, blue);
    }


}

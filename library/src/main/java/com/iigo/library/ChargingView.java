package com.iigo.library;

import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

/**
 * @author SamLeung
 * @Emial 729717222@qq.com
 * @date 2018/7/3 0003 15:44
 */
public class ChargingView extends View {
    private int currentProgress; //当前进度
    private int bgColor; //背景颜色
    private int chargingColor; //充电中颜色
    private int textColor;
    private float textSize;

    private Paint chargingPaint; //充电中效果画笔
    private Paint backgroundPaint; //充电背景画笔
    private Paint textPaint; //百分比画笔

    private RectF chargingRectF = new RectF(); //充电矩形
    private RectF headRectF = new RectF(); //充电头头矩形

    private Path wavePath;//波浪轨迹
    private Path clipPath; //要裁剪的轨迹

    private float waveHeight; //波形高度，即峰值

    private ValueAnimator waveAnimator; //用于执行波浪移动动画
    private float waveAnimatorRatio = 0; //该值为三个波形的顺序三个波形分别为(-2 * width -> width -> 2 * width) width为chargingRectF的宽

    public ChargingView(Context context) {
        super(context);

        init();
    }

    public ChargingView(Context context, AttributeSet attrs) {
        super(context, attrs);

        parseAttr(attrs);
        init();
    }

    public ChargingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        parseAttr(attrs);
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public ChargingView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);

        parseAttr(attrs);
        init();
    }

    private void parseAttr(AttributeSet attrs){
        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.ChargingView);

        bgColor = a.getColor(R.styleable.ChargingView_bg_color, Color.GRAY);
        chargingColor = a.getColor(R.styleable.ChargingView_chargingColor, Color.GREEN);
        currentProgress = a.getInteger(R.styleable.ChargingView_progress, 0);
        textSize = a.getDimension(R.styleable.ChargingView_progressTextSize, 20);
        textColor = a.getColor(R.styleable.ChargingView_progressTextColor, Color.WHITE);

        if (currentProgress > 100){
            currentProgress = 100;
        }

        a.recycle();
    }

    private void init(){
        chargingPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        chargingPaint.setColor(chargingColor);
        chargingPaint.setStyle(Paint.Style.FILL);

        backgroundPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        backgroundPaint.setStyle(Paint.Style.FILL);
        backgroundPaint.setColor(bgColor);

        textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setColor(textColor);
        textPaint.setTextSize(textSize);

        wavePath = new Path();
        clipPath = new Path();

        waveAnimator = ValueAnimator.ofFloat(-2, 2);
        waveAnimator.setDuration(4000);
        waveAnimator.setRepeatCount(ValueAnimator.INFINITE);
        waveAnimator.setInterpolator(new LinearInterpolator());
        waveAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                waveAnimatorRatio = (float) animation.getAnimatedValue();
                invalidate();
            }
        });
        waveAnimator.start();
    }

    /**
     * 初始化相关属性
     * */
    private void initAttr(){

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        initRects(w, h);
    }

    /**
     * 初始化充电矩形和头矩形
     * */
    private void initRects(int width, int height){
        float headWidth = width / 3f;
        float headHeight = height / 15f;

        float chargingWidth = width;
        float chargingHeight = height - headHeight;

        headRectF.set((chargingWidth - headWidth) / 2, 0, chargingWidth - (chargingWidth - headWidth) / 2, headHeight);
        chargingRectF.set(0, 0, chargingWidth, chargingHeight);

        clipPath.moveTo(0, headHeight);
        clipPath.lineTo(width / 2 - headWidth / 2, headHeight);
        clipPath.lineTo(width / 2 - headWidth / 2, 0);
        clipPath.lineTo(width / 2 + headWidth / 2, 0);
        clipPath.lineTo(width / 2 + headWidth / 2, headHeight);
        clipPath.lineTo(width, headHeight);

        clipPath.lineTo(width, height);
        clipPath.lineTo(0, height);
        clipPath.close();

        waveHeight = headHeight * 2; //这里设置波形峰值即为充电头的高
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //裁剪画笔
        canvas.clipPath(clipPath);

        //先画充电头矩形
        canvas.drawRect(headRectF, backgroundPaint);

        //画充电主体部分
        canvas.save();
        canvas.translate(0, headRectF.height());
        canvas.drawRect(chargingRectF, backgroundPaint);

        wavePath.reset();
        float centerX = chargingRectF.width() / 2; //获取中心点X坐标
        float centerY = chargingRectF.height() / 2; //获取中心点Y坐标

        //根据当前progress得到的水位线高
        float progressHeight = chargingRectF.height() - currentProgress / 100f * chargingRectF.height(); //根据百分比获取当前的水位线

        //先移动至波形起点
        wavePath.moveTo(-2 * chargingRectF.width() + waveAnimatorRatio * chargingRectF.width(), progressHeight);

        //画波形
        for (int i = (int) (-2 * chargingRectF.width()); i < 2 * chargingRectF.width(); i += chargingRectF.width()) {
            wavePath.rQuadTo(chargingRectF.width() / 2, -waveHeight, chargingRectF.width(),0);
            wavePath.rQuadTo(chargingRectF.width() / 2, waveHeight, chargingRectF.width(),0);
        }

        wavePath.lineTo(chargingRectF.width(), chargingRectF.height());
        wavePath.lineTo(0, chargingRectF.height());
        wavePath.close();

        canvas.drawPath(wavePath, chargingPaint);

        drawProgress(canvas, centerX, centerY);
        canvas.restore();
    }

    /**
     * 绘制进度
     *
     * @param canvas 画布
     * @param centerX 中心点X
     * @param centerY 中心点Y
     * */
    private void drawProgress(Canvas canvas, float centerX, float centerY){
        //绘制百分比
        String text = currentProgress + "%";
        Rect textBound = new Rect();
        textPaint.getTextBounds(text, 0, text.length(), textBound);
        canvas.drawText(text, centerX - textBound.width() / 2, centerY + textBound.height() / 2, textPaint);
    }

    /**
     * 设置当前进度
     *
     * 0 - 100
     * */
    public void setProgress(int progress){
        this.currentProgress = progress;
        if (currentProgress > 100){
            currentProgress = 100;
        }
        invalidate();
    }

    /**
     * 获取当前进度
     * */
    public int getProgress() {
        return currentProgress;
    }

    /**
     * 设置充电矩形背景颜色
     * */
    public void setBgColor(int bgColor) {
        this.bgColor = bgColor;
        backgroundPaint.setColor(bgColor);
        invalidate();
    }

    /**
     * 获取充电矩形背景颜色
     * */
    public int getBgColor() {
        return bgColor;
    }

    /**
     * 获取进度text大小
     * */
    public float getTextSize() {
        return textSize;
    }

    /**
     * 设置进度text大小
     * */
    public void setTextSize(float textSize) {
        this.textSize = textSize;
        textPaint.setTextSize(textSize);
        invalidate();
    }

    /**
     * 获取进度text颜色
     * */
    public int getTextColor() {
        return textColor;
    }

    /**
     * 设置进度text颜色
     * */
    public void setTextColor(int textColor) {
        this.textColor = textColor;
        textPaint.setColor(textColor);
        invalidate();
    }

    /**
     * 获取充电矩形已充颜色
     * */
    public int getChargingColor() {
        return chargingColor;
    }

    /**
     * 设置充电矩形已充颜色
     * */
    public void setChargingColor(int chargingColor) {
        this.chargingColor = chargingColor;
        chargingPaint.setColor(chargingColor);
        invalidate();
    }
}

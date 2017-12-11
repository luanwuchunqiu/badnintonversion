package com.myweather.app.badmintonversion.view.nine;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;


import com.myweather.app.badmintonversion.R;
import com.stkent.polygondrawingutil.PolygonDrawingUtil;

/**
 * Created by zyt on 2017/9/15.
 */

public class GraphView extends View{


    private final PolygonDrawingUtil polygonDrawingUtil = new PolygonDrawingUtil();
    private final Paint polygonFillPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint polygonStrokePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    /*private final Paint polygonStrokePaint1 = new Paint(Paint.ANTI_ALIAS_FLAG);*/
    private final float strokeWidth = getResources().getDimension(R.dimen.stroke_width);
    private float rippleStrokeWidth = getResources().getDimension(R.dimen.ripple_stroke_width);
    private float myripple;


    private int numberOfSides = 6;
    private float cornerRadius = 180;
    private float rotation = 90;
    private float scale = 1.0f;
    private float shadow = 0f;


    private void setFlickerAnimation(View view) {
        final Animation animation = new AlphaAnimation(1,0.5f);
        animation.setDuration(1500);//闂儊鏃堕棿闂撮殧
        animation.setInterpolator(new AccelerateDecelerateInterpolator());


        animation.setRepeatCount(Animation.INFINITE);


        animation.setRepeatMode(Animation.RELATIVE_TO_PARENT);
        view.setAnimation(animation);
    }


    public GraphView(final Context context, final AttributeSet attrs) {
        super(context, attrs);

        polygonFillPaint.setColor(Color.parseColor("#224f54"));
        polygonFillPaint.setStyle(Paint.Style.FILL);
        polygonFillPaint.setShadowLayer(shadow,0f,0f,Color.RED);

        polygonStrokePaint.setColor(Color.parseColor("#addeff"));
        polygonStrokePaint.setStrokeWidth(strokeWidth);

        polygonStrokePaint.setStyle(Paint.Style.STROKE);


     /*   polygonStrokePaint1.setColor(Color.parseColor("#2a7883"));

        polygonStrokePaint1.setStyle(Paint.Style.STROKE);
        polygonStrokePaint1.setStrokeWidth(0);*/
        ripple();

    }

    @Override
    protected void onDraw(final Canvas canvas) {
        super.onDraw(canvas);

        final float centerX = getWidth() / 2;
        final float centerY = getHeight() / 2;
        final float radius = scale * (getWidth() / 2 - rippleStrokeWidth);

        polygonDrawingUtil.drawPolygon(
                canvas,
                numberOfSides,
                centerX,
                centerY,
                radius,
                cornerRadius,
                rotation,
                polygonFillPaint);

        polygonDrawingUtil.drawPolygon(
                canvas,
                numberOfSides,
                centerX,
                centerY,
                radius,
                cornerRadius,
                rotation,
                polygonStrokePaint);
    /*    polygonDrawingUtil.drawPolygon(
                canvas,
                numberOfSides,
                centerX,
                centerY,
                scale * (getWidth() / 2)-strokeWidth,
                cornerRadius,
                rotation,
                polygonStrokePaint1
        );*/
    }
public  void ripple(){
    ValueAnimator va = ValueAnimator.ofInt(0,(int)20f);
    va.setDuration(1000);
    va.setEvaluator(new ArgbEvaluator());
    va.setRepeatCount(ValueAnimator.INFINITE);
    va.setRepeatMode(ValueAnimator.REVERSE);
    va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
        @Override
        public void onAnimationUpdate(ValueAnimator animation) {
            int value= (int) animation.getAnimatedValue();
            shadow = (float) value;
            polygonStrokePaint.setShadowLayer(shadow,0f,0f,Color.WHITE);

            invalidate();
        }
    });
    va.start();
}



    public int getNumberOfSides() {
        return numberOfSides;
    }

    public void setNumberOfSides(final int numberOfSides) {
        this.numberOfSides = numberOfSides;
        invalidate();
    }

    public void setCornerRadius(final float cornerRadius) {
        this.cornerRadius = cornerRadius;
        invalidate();
    }

    public void setPolygonRotation(final float rotation) {
        this.rotation = rotation;
        invalidate();
    }

    public void setScale(final float scale) {
        this.scale = scale;
        invalidate();
    }
}

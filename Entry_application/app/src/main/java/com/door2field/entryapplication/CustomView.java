package com.door2field.entryapplication;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.widget.LinearLayout;

/**
 * Created by 智晴 on 2015/12/05.
 */
public class CustomView extends LinearLayout{
    private Paint basePaint, framePaint;

    public CustomView(Context context, AttributeSet attrs){
        super(context, attrs);
        init();
    }

    private void init(){
        basePaint = new Paint();
        basePaint.setARGB(200,75,75,155);

        framePaint = new Paint();
        framePaint.setARGB(200,55,55,135);
        framePaint.setAntiAlias(true);
        framePaint.setStyle(Paint.Style.STROKE);
        framePaint.setStrokeWidth(0);

    }

    @Override
    protected void dispatchDraw(Canvas canvas){
        RectF drawRect = new RectF();
        setPadding(8,8,8,8);
        drawRect.set(0, 0, getMeasuredWidth(), getMeasuredHeight());
        canvas.drawRoundRect(drawRect,15,15,basePaint);
        canvas.drawRoundRect(drawRect,15,15,framePaint);
        super.dispatchDraw(canvas);
    }
}

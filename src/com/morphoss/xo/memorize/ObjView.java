package com.morphoss.xo.memorize;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

class ObjView extends View {
    float mDpi;

    int mMode = MemoryObj.MEMORY_OBJ_MODE_HIDEN;
    
    private MemoryObj mObj;

    public ObjView(Context context) {
        this(context, null);
        
    }

    public ObjView(Context context, AttributeSet attrs) 
    {
        this(context, attrs, 0);
    }

    public ObjView(Context context, AttributeSet attrs, int defStyle) 
    {
        super(context, attrs, defStyle);
        mDpi = (float)context.getResources().getDisplayMetrics().densityDpi/ 160f;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) 
    {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        
    }
    
    public void setObj(MemoryObj obj) {
        mObj = obj;
    }
    
    public void setMode(int mode) {
        mMode = mode;
    }

    @Override
    protected void onDraw(Canvas canvas) 
    {
        super.onDraw(canvas);
        
        switch (mMode) {
        case MemoryObj.MEMORY_OBJ_MODE_SHOWN:
            drawBackground(canvas, Color.GRAY);
            mObj.draw(canvas);
            break;
        case MemoryObj.MEMORY_OBJ_MODE_MATCHED:
            drawBackground(canvas, Color.GREEN);
            mObj.draw(canvas);
            break;
        case MemoryObj.MEMORY_OBJ_MODE_HIDEN:
        default:
            drawBackground(canvas, Color.GRAY);
            break;
        }
    }

    private void drawBackground(Canvas canvas, int color) {
        Paint p = new Paint();
        p.setStyle(Paint.Style.FILL);
        p.setColor(color);

        RectF rf = new RectF(0, 0, this.getMeasuredWidth(), this.getMeasuredHeight());
        canvas.drawRoundRect(rf, 10 * mDpi, 10 * mDpi, p);
    }
    
}
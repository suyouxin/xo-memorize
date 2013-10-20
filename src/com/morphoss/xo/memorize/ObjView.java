package com.morphoss.xo.memorize;

import com.morphoss.xo.memorize.obj.MemoryObj;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Paint.Align;
import android.util.AttributeSet;
import android.view.View;

public class ObjView extends View {
    float mDpi;

    final static public int BORDER_COLOR_BROWN = 0xffffa522;
    final static int BORDER_COLOR_GREEN = 0xff0ea869;

    final static public int CORNER_SIZE_DP = 10;
    final static public int BORDER_SIZE_DP = 5;

    /*
    final int cornerSizePx = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, (float) cornerDips,
            context.getResources().getDisplayMetrics());*/

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

    @Override
    protected void onDraw(Canvas canvas) 
    {
    	if (mObj == null)
    		return;
        super.onDraw(canvas);
        switch (mObj.getMode()) {
        case MemoryObj.MEMORY_OBJ_MODE_SHOWN:
            drawBackground(canvas, this.getContext().getResources().getColor(R.color.olpc_dark_grey), 
                    false, true, Color.WHITE);
            mObj.draw(canvas);
            break;
        case MemoryObj.MEMORY_OBJ_MODE_MATCHED:
            drawBackground(canvas, BORDER_COLOR_GREEN, false, true, BORDER_COLOR_BROWN);
            mObj.draw(canvas);
            break;
        case MemoryObj.MEMORY_OBJ_MODE_HIDEN:
        default:
            drawBackground(canvas, Color.GRAY, true, false, 0);
            break;
        }
    }

    private void drawBackground(Canvas canvas, int color, boolean withGroup, boolean withBorder, int borderColor) {
        int cornerSizePx = (int) (CORNER_SIZE_DP * mDpi);
        int borderSizePx = (int) (BORDER_SIZE_DP * mDpi);

        Paint p = new Paint();
        p.setAntiAlias(true);
        p.setStyle(Paint.Style.FILL);
        
        RectF rf = new RectF(0, 0, canvas.getWidth(), canvas.getHeight());
        RectF rfSmall = new RectF(borderSizePx, borderSizePx, canvas.getWidth() - borderSizePx, canvas.getHeight() - borderSizePx);
        
        if (withBorder) {
            p.setColor(borderColor);
            p.setStyle(Paint.Style.FILL);
            canvas.drawRoundRect(rf, cornerSizePx, cornerSizePx, p);
            p.setColor(color);
            canvas.drawRoundRect(rfSmall, cornerSizePx, cornerSizePx, p);
        }
        else {
            p.setColor(color);
            canvas.drawRoundRect(rf, cornerSizePx, cornerSizePx, p);
        }
        if (withGroup) {
            // draw text 1 / 2
            Paint textPaint = new Paint();
            
            textPaint.setColor(this.getContext().getResources().getColor(R.color.light_grey));
            textPaint.setTextAlign(Align.CENTER);
            textPaint.setTextSize(55);
            
            int xPos = (canvas.getWidth() / 2);
            int yPos = (int) ((canvas.getHeight() / 2) - ((textPaint.descent() + textPaint.ascent()) / 2)) ; 
            
            canvas.drawText(String.valueOf(mObj.getGroupId()), xPos, yPos, textPaint);
        }
    }
}
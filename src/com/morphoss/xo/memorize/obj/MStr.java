package com.morphoss.xo.memorize.obj;

import java.security.NoSuchAlgorithmException;

import com.morphoss.xo.memorize.Util;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;

public class MStr extends Paired {
    private String mUid; 
    private String mStr;
    
    public MStr(String str) {
        mStr = str;
        try {
            mUid = Util.makeSHA1Hash(str);
        }
        catch (NoSuchAlgorithmException e) {
            mUid = mStr;
        }
    }

    @Override
    public String getUid() {
        // TODO Auto-generated method stub
        return mUid;
    }

    @Override
    public MemoryObj copyMe() {
        MStr me = new MStr(mStr);
        return me;
    }

    @Override
    public void draw(Canvas canvas) 
    {
        Paint textPaint = new Paint();
        
        textPaint.setColor(Color.WHITE);
        textPaint.setTextAlign(Align.CENTER);
        textPaint.setTextSize(35);
        
        int xPos = (canvas.getWidth() / 2);
        int yPos = (int) ((canvas.getHeight() / 2) - ((textPaint.descent() + textPaint.ascent()) / 2)) ; 
        
        canvas.drawText(mStr, xPos, yPos, textPaint);
    }
}
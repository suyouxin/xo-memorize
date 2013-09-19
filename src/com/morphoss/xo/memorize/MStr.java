package com.morphoss.xo.memorize;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;

class MStr extends Paired {
    private String mUid; 
    private String mStr;
    
    MStr(String str) {
        mStr = str;
        try {
            mUid = makeSHA1Hash(str);
        }
        catch (NoSuchAlgorithmException e) {
            mUid = mStr;
        }
    }
    
    @Override
    public boolean doesItMatch(MemoryObj obj) {
        // TODO Auto-generated method stub
        return obj.getUid().equals(this.getUid());
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
    
    public String makeSHA1Hash(String input)
            throws NoSuchAlgorithmException
    {
        MessageDigest md = MessageDigest.getInstance("SHA1");
        md.reset();
        byte[] buffer = input.getBytes();
        md.update(buffer);
        byte[] digest = md.digest();

        String hexStr = "";
        for (int i = 0; i < digest.length; i++) {
            hexStr +=  Integer.toString( ( digest[i] & 0xff ) + 0x100, 16).substring( 1 );
        }
        return hexStr;
    }
}
package com.morphoss.xo.memorize.obj;


import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.media.MediaPlayer;
import android.util.Log;

import com.morphoss.xo.memorize.GameEngine;
import com.morphoss.xo.memorize.ObjView;
import com.morphoss.xo.memorize.Util;

public class MBitmap extends Paired {
    private String mUid; 
    private Bitmap bmp;
    private String mSndPath;
    
    public MBitmap(Bitmap imgCamera, String sndPath) {
        bmp = imgCamera;
        mSndPath = sndPath;
        
        StringBuilder strbuilder = new StringBuilder();
        if (imgCamera != null)
            strbuilder.append(imgCamera);
        if (sndPath != null)
            strbuilder.append(sndPath);

        try {
            mUid = Util.makeSHA1Hash(strbuilder.toString());
        }
        catch (NoSuchAlgorithmException e) {
            mUid = strbuilder.toString();
        }
    }

    @Override
    public String getUid() {
        return mUid;
    }

    @Override
    public MemoryObj copyMe() {
        MBitmap me = new MBitmap(bmp, mSndPath);
        return me;
    }

    @Override
    public void draw(Canvas canvas) 
    {
        if (bmp != null) {
            int reduce = ObjView.BORDER_SIZE_DP;
            Rect rc = new Rect(reduce, reduce, 
                    canvas.getWidth() - reduce, canvas.getHeight() - reduce);
            Paint p = new Paint();
            canvas.drawBitmap(bmp, null, rc, p);
        }
    }

    private void play() {
        GameEngine ge = GameEngine.getInstance();
        MediaPlayer mp = ge.getMediaPlayer();
        play(mp);
    }
    
    private void stop() {
        GameEngine ge = GameEngine.getInstance();
        MediaPlayer mp = ge.getMediaPlayer();
        if (mp.isPlaying()) {
            mp.stop();
        }
    }

    private void play(MediaPlayer player) {
        if (mSndPath != null) {
            if (player.isPlaying()) {
                player.stop();
            }
            player.reset();
            try {
                player.setDataSource(mSndPath);
                player.prepare();
                player.start();
                Log.d("MBitmap", "playing: " + mSndPath);
            } catch (IllegalArgumentException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (SecurityException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IllegalStateException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
    
    @Override
    public void show() {
        super.show();
        play();
    }

    @Override
    public void hide() {
        super.hide();
        stop();
    }
    
    @Override
    public void match() {
        super.match();
        play();
    }
}
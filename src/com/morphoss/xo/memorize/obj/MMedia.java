package com.morphoss.xo.memorize.obj;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import com.morphoss.xo.memorize.GameEngine;
import com.morphoss.xo.memorize.ObjView;
import com.morphoss.xo.memorize.Util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.media.MediaPlayer;
import android.util.Log;

public class MMedia extends Paired {
    private String mUid; 
    private String mImgPath;
    private String mSndPath;
    
    public MMedia(String imgPath, String sndPath) {
        mImgPath = imgPath;
        mSndPath = sndPath;
        
        StringBuilder strbuilder = new StringBuilder();
        if (imgPath != null)
            strbuilder.append(imgPath);
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

    public String getSoundPath(){
    	return mSndPath;
    }
    public String getImagePath(){
    	return mImgPath;
    }
    @Override
    public MemoryObj copyMe() {
        MMedia me = new MMedia(mImgPath, mSndPath);
        return me;
    }

    @Override
    public void draw(Canvas canvas) 
    {
        if (mImgPath != null) {
            int reduce = ObjView.BORDER_SIZE_DP;
            Bitmap b = BitmapFactory.decodeFile(mImgPath);
            Rect rc = new Rect(reduce, reduce, 
                    canvas.getWidth() - reduce, canvas.getHeight() - reduce);
            Paint p = new Paint();
            canvas.drawBitmap(b, null, rc, p);
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
                Log.d("MMedia", "playing: " + mSndPath);
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
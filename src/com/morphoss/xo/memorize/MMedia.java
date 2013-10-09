package com.morphoss.xo.memorize;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.media.MediaPlayer;
import android.util.Log;

class MMedia extends Paired {
    private String mUid; 
    private String mImgPath;
    private String mSndPath;
    
    MMedia(String imgPath, String sndPath) {
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
        // TODO Auto-generated method stub
        return mUid;
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
            Bitmap b = BitmapFactory.decodeFile(mImgPath);
            Rect rc = new Rect(ObjView.BORDER_SIZE_DP, ObjView.BORDER_SIZE_DP, 
                    canvas.getWidth() - ObjView.BORDER_SIZE_DP, canvas.getHeight() - ObjView.BORDER_SIZE_DP);
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
package com.morphoss.xo.memorize;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Message;

public class GameEngine {
    final static String TAG = "GameEngine"; 
    final static int SHOW_TIME_OUT = 2 * 1000; 

    ArrayList<MemoryObj> mIdle = new ArrayList<MemoryObj>();
    ArrayList<MemoryObj> mPicked = new ArrayList<MemoryObj>();
    ArrayList<MemoryObj> mBody = new ArrayList<MemoryObj>();
    
    static private Random randomGenerator = new Random();
    
    int mTotal;
    
    static GameEngine gGameEngine = null;
    static GameCanvas mGc = null;
    
    static Timer mTimer = new Timer();
    
    boolean mDivided = false;
    
    MemoryObj firstShownObj = null;
    MemoryObj secondShownObj = null;
    MemoryObjAdapter mAdapter;
    
    MediaPlayer mPlayer = new MediaPlayer();

    static public GameEngine getInstance() {
        if (gGameEngine == null) {
            gGameEngine = new GameEngine();
            gGameEngine.mPlayer = new MediaPlayer();
        }
        return gGameEngine;
    }

    public boolean start(GameInfo gameInfo, int rows, int cols, 
            Context ctx, GameCanvas gc) {
        mIdle.clear();
        mIdle.addAll(gameInfo.objs);
        mTotal = rows * cols;
        
        mAdapter = new MemoryObjAdapter(ctx);
        mGc = gc;
        mGc.setRows(rows, cols);
        mGc.setAdapter(mAdapter);
        mDivided = gameInfo.divided;
        
        mPicked.clear();
        mBody.clear();
        return restart(ctx);
    }
    
    public boolean restart(int rows, int cols, Context ctx) {
        mTotal = rows * cols;
        mGc.setRows(rows, cols);
        mGc.sizeHasChanged();
        return restart(ctx);
    }
    
    public boolean restart(GameInfo gameInfo, Context ctx) {
        mIdle.clear();
        mIdle.addAll(gameInfo.objs);
        mDivided = gameInfo.divided;
        
        mPicked.clear();
        mBody.clear();
        return restart(ctx);
    }

    public boolean restart(Context ctx) {
        stopTimer();

        mIdle.addAll(mPicked);
        for (MemoryObj obj : mIdle) { 
            obj.hide();
            if (obj.getPairedObj() != null)
                obj.getPairedObj().hide();
        }
        mPicked.clear();
        mBody.clear();
        
        for (int i = 0; i < mTotal / 2; i++) {
            MemoryObj cur = getRandomObj();
            if (cur != null) {
                mIdle.remove(cur);
                cur.setGroupId(1);
                mPicked.add(cur);
            }
        }
        
        if (mDivided) {
            for (MemoryObj cur : mPicked) {
                MemoryObj paired = cur.getPairedObj();
                paired.setGroupId(2);
                mBody.add(paired);
            }
        }
        else {
            for (MemoryObj cur : mPicked) {
                mBody.add(cur.copyMe());
            }
        }
        long seed = System.nanoTime();
        Collections.shuffle(mBody, new Random(seed));

        ArrayList<MemoryObj> ret = new ArrayList<MemoryObj>(mPicked);
        ret.addAll(mBody);
        
        mAdapter.clear();
        mAdapter.addAll(ret);
        mAdapter.notifyDataSetChanged();
        return true;
    }

    public MemoryObj getRandomObj()
    {
        int size = mIdle.size();
        if (size > 1)
            return mIdle.get(randomGenerator.nextInt(mIdle.size()  - 1));
        else if (size == 1)
            return mIdle.get(0);
        return null;
    }
    
    public MediaPlayer getMediaPlayer() {
        return mPlayer;
    }

    public void onClickObj(MemoryObj obj) {
        if (obj.isMatched())
            return;
        
     // clear all the show off obj
        clearShownObj();

        if (obj.isHiden()) {
            if (firstShownObj == null) {
                if ((this.mDivided && obj.getGroupId() == 1) 
                        || !this.mDivided) {
                    firstShownObj = obj;
                    obj.show();
                }
            }
            else {
                if (firstShownObj.doesItMatch(obj)){
                    firstShownObj.match();
                    obj.match();
                    firstShownObj = null;
                }
                else {
                    if ((this.mDivided && obj.getGroupId() == 2) 
                            || !this.mDivided) {
                        obj.show();
                        secondShownObj = obj;
                        startTimer();
                    }
                }
            }
        }
        refresh();
    }

    void clearShownObj() {
        if (secondShownObj != null && firstShownObj != null) {
            firstShownObj.hide();
            secondShownObj.hide();
            firstShownObj = null;
            secondShownObj = null;
            stopTimer();
        }
    }

    public void startTimer() {
        if (mTimer == null) {
            mTimer = new Timer();
        }
        else {
            mTimer.cancel();
            mTimer.purge();
            mTimer = new Timer();
        }
        mTimer.schedule(new ClearShownObj(), SHOW_TIME_OUT);
    }

    public void stopTimer() {
        if (mTimer != null) {
            mTimer.cancel();
            mTimer.purge();
        }
    }

    void refresh() {
        mAdapter.notifyDataSetInvalidated();
    }

    private static Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            GameEngine.getInstance().refresh();
        }
    };

    class ClearShownObj extends TimerTask {

        @Override
        public void run() {
            clearShownObj();
            mHandler.sendEmptyMessage(0);
        }
    };
}
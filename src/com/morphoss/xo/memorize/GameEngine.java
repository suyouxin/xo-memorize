package com.morphoss.xo.memorize;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

public class GameEngine {
    final static int SHOW_TIME_OUT = 3 * 1000; 
    
    ArrayList<MemoryObj> mIdle = new ArrayList<MemoryObj>();
    ArrayList<MemoryObj> mPicked = new ArrayList<MemoryObj>();
    ArrayList<MemoryObj> mBody = new ArrayList<MemoryObj>();
    
    static private Random randomGenerator = new Random();
    
    int mTotal;
    
    static GameEngine gGameEngine = null;
    
    static Timer mTimer = new Timer();
    
    boolean mPaired = false;
    
    MemoryObj firstShownObj = null;
    MemoryObj secondShownObj = null;
    
    MemoryObjAdapter mAdapter;

    static GameEngine getInstance() {
        if (gGameEngine == null) {
            gGameEngine = new GameEngine();
        }
        return gGameEngine;
    }
    
    ArrayList<MemoryObj> restart(ArrayList<MemoryObj> objs, int rows, int cols, boolean paired) {
        mIdle.addAll(objs);
        mPicked.clear();
        mBody.clear();

        mTotal = rows * cols;
        
        for (int i = 0; i < mTotal / 2; i++) {
            MemoryObj cur = getRandomObj();
            mIdle.remove(cur);
            mPicked.add(cur);
        }
        
        mPaired = paired;
        if (mPaired) {
            
        }
        else {
            for (MemoryObj cur : mPicked) {
                mBody.add(cur.copyMe());
            }
            long seed = System.nanoTime();
            Collections.shuffle(mBody, new Random(seed));
        }

        ArrayList<MemoryObj> ret = new ArrayList<MemoryObj>(mPicked);
        ret.addAll(mBody);
        return ret;
    }

    public MemoryObj getRandomObj()
    {
        return mIdle.get(randomGenerator.nextInt(mIdle.size()  - 1));
    }
    
    public void onClickObj(MemoryObj obj) {
        if (obj.isMatched())
            return;
        
     // clear all the show off obj
        clearShownObj();

        if (obj.isHiden()) {
            obj.show();
            if (firstShownObj == null) {
                firstShownObj = obj;
            }
            else {
                if (firstShownObj.doesItMatch(obj)){
                    firstShownObj.match();
                    obj.match();
                    firstShownObj = null;
                }
                else {
                    secondShownObj = obj;
                    startTimer();
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
    
    void setAdapter(MemoryObjAdapter adapter) {
        mAdapter = adapter;
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
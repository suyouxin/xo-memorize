package com.morphoss.xo.memorize;

import android.content.Context;
import android.graphics.Canvas;
import android.view.View;
import android.view.ViewGroup;

interface MemoryObj {
    final static public int MEMORY_OBJ_MODE_HIDEN = 0;
    final static public int MEMORY_OBJ_MODE_SHOWN = 1;
    final static public int MEMORY_OBJ_MODE_MATCHED = 2;
    
    public String getUid();
    public MemoryObj copyMe();

    public boolean doesItMatch(MemoryObj obj);
    
    public int getMode();
    public void show();
    public void hide();
    public void match();
    
    boolean isShown();
    boolean isHiden();
    boolean isMatched();
    
    public View getView(Context context, View convertView, ViewGroup parent, int size);
    
    public void draw(Canvas canvas);
}
package com.morphoss.xo.memorize;

import android.content.Context;
import android.graphics.Canvas;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

class Paired implements MemoryObj {

    int mMode = MemoryObj.MEMORY_OBJ_MODE_HIDEN;

    @Override
    public int getMode() {
        return mMode;
    }
    
    @Override
    public void show() {
        mMode = MemoryObj.MEMORY_OBJ_MODE_SHOWN;
    }

    @Override
    public void hide() {
        mMode = MemoryObj.MEMORY_OBJ_MODE_HIDEN;
    }
    
    @Override
    public void match() {
        mMode = MemoryObj.MEMORY_OBJ_MODE_MATCHED;
    }

    @Override
    public View getView(Context context, View convertView, ViewGroup parent, int size) {
        if (convertView == null) {
            final LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.object, parent, false);
            convertView = new ObjView(context); 
            convertView.setLayoutParams(new ListView.LayoutParams(size, size));
        }
        ObjView v = (ObjView)convertView;
        v.setObj(this);
        v.setMode(mMode);
        return convertView;
    }

    @Override
    public String getUid() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean doesItMatch(MemoryObj obj) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void draw(Canvas canvas) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public MemoryObj copyMe() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean isShown() {
        return mMode == MemoryObj.MEMORY_OBJ_MODE_SHOWN;
    }

    @Override
    public boolean isHiden() {
        return mMode == MemoryObj.MEMORY_OBJ_MODE_HIDEN;
    }

    @Override
    public boolean isMatched() {
        return mMode == MemoryObj.MEMORY_OBJ_MODE_MATCHED;
    }
    
}
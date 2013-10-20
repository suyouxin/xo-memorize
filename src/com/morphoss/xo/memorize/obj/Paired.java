package com.morphoss.xo.memorize.obj;

import com.morphoss.xo.memorize.ObjView;
import com.morphoss.xo.memorize.R;

import android.content.Context;
import android.graphics.Canvas;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

class Paired implements MemoryObj {
    MemoryObj mPairedObj = null;

    int mMode = MemoryObj.MEMORY_OBJ_MODE_HIDEN;
    int mGroupID = 1;

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
    public boolean doesItMatch(MemoryObj obj) {
        if (mPairedObj != null) {
            if (obj.getUid().equals(this.mPairedObj.getUid()))
                return true;
        }
        return obj.getUid().equals(this.getUid());
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
        return convertView;
    }

    @Override
    public String getUid() {
        // TODO Auto-generated method stub
        return null;
    }
    
    @Override
    public int getGroupId() {
        return mGroupID;
    }
    
    @Override
    public void setGroupId(int id) {
        mGroupID = id;
    }

    @Override
    public void setPairedObj(MemoryObj obj) {
        mPairedObj = obj;
    }
    
    @Override
    public MemoryObj getPairedObj() {
        return mPairedObj;
    }
    
    @Override
    public void draw(Canvas canvas) {

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
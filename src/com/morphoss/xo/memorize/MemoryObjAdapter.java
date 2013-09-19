package com.morphoss.xo.memorize;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;


class MemoryObjAdapter extends ArrayAdapter<MemoryObj> {
    int mSize;
    
    public MemoryObjAdapter(Context context, List<MemoryObj> allObj) {
        super(context, 0);

        this.addAll(allObj);
    }
    
    public void setSize(int size) {
        mSize = size;
    }
    
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MemoryObj obj = this.getItem(position);
        
        View v = obj.getView(getContext(), convertView, parent, mSize);
        v.setTag(obj);
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                MemoryObj obj = (MemoryObj)arg0.getTag();
                GameEngine ge = GameEngine.getInstance();
                ge.onClickObj(obj);
                MemoryObjAdapter.this.notifyDataSetInvalidated();
            }
        });
        return v;
    }
}
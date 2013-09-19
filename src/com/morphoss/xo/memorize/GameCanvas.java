package com.morphoss.xo.memorize;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.GridView;
import android.widget.LinearLayout;

public class GameCanvas extends LinearLayout {

    LinearLayout mLeftMenu;
    GridView mGameContent;
    MemoryObjAdapter mAdapter;
    
    float mDpi;
    
    public GameCanvas(Context context) {
        this(context, null);
    }
    
    public GameCanvas(Context context, AttributeSet attrs) 
    {
        this(context, attrs, 0);
    }

    public GameCanvas(Context context, AttributeSet attrs, int defStyle) 
    {
        super(context, attrs, defStyle);
        this.setOrientation(LinearLayout.HORIZONTAL);
        
        LinearLayout.LayoutParams params =
                new LinearLayout.LayoutParams(0, LayoutParams.MATCH_PARENT);
        
        mLeftMenu = new LinearLayout(context);
        mLeftMenu.setOrientation(LinearLayout.VERTICAL);
        mLeftMenu.setLayoutParams(params);
        mLeftMenu.setBackgroundColor(context.getResources().getColor(R.color.olpc_dark_grey));
        this.addView(mLeftMenu);
        
        mGameContent = new GridView(context);
        this.addView(mGameContent);
        
        mDpi = (float)context.getResources().getDisplayMetrics().densityDpi/ 160f;
    }

    void setAdapter(MemoryObjAdapter adapter) {
        mAdapter = adapter;
        mGameContent.setAdapter(mAdapter);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
      int h = bottom - top;
      int w = right - left;
      
      int rows = 4;
      
      int each = h / rows;
      int offset = w - each * (rows + 1);
      // TODO: density
      mGameContent.layout(offset, top, right, bottom);
      
      mGameContent.setColumnWidth(each - convertDpi(2));
      mGameContent.setGravity(Gravity.CENTER);
      mGameContent.setNumColumns(rows + 1);
      
      mGameContent.setVerticalSpacing(convertDpi(4));
      mGameContent.setHorizontalSpacing(0);
      mGameContent.setStretchMode(GridView.NO_STRETCH);
      
      mAdapter.setSize(each - convertDpi(6));
      
      mLeftMenu.layout(0, top, offset, bottom);
    }

    private int convertDpi(int dp) {
        return (int)(mDpi * dp);
    }
}
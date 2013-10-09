package com.morphoss.xo.memorize;

import java.util.ArrayList;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.Spinner;

public class GameCanvas extends LinearLayout {
    LinearLayout mLeftMenu;
    GridView mGameContent;
    MemoryObjAdapter mAdapter;
    
    ArrayList<GameInfo> mGameInfos;

    Button mRestart;
    Spinner mSizeSpinner;
    Spinner mGamesSpinner;
    int mRows;

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
        
        LayoutParams wrapParams =
                new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);

        mLeftMenu = new LinearLayout(context);
        mLeftMenu.setOrientation(LinearLayout.VERTICAL);
        mLeftMenu.setLayoutParams(params);
        mLeftMenu.setBackgroundColor(context.getResources().getColor(R.color.olpc_dark_grey));

        mRestart = new Button(context);
        mRestart.setGravity(Gravity.CENTER);
        mRestart.setText("restart");
        mRestart.setTextSize(26);
        mRestart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                GameEngine ge = GameEngine.getInstance();
                ge.restart(GameCanvas.this.getContext());
            }
        });
        mLeftMenu.addView(mRestart, wrapParams);

        mSizeSpinner = new Spinner(context, Spinner.MODE_DROPDOWN);
        ArrayAdapter<String> sizeAdapter = new ArrayAdapter<String>(
                this.getContext(), android.R.layout.simple_spinner_item, new String[] {"5 x 4", "6 x 5", "7 x 6"});
        sizeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSizeSpinner.setAdapter(sizeAdapter);
        mSizeSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, 
                    int pos, long id) {
                GameEngine ge = GameEngine.getInstance();
                ge.restart(4 + pos, 5 + pos, GameCanvas.this.getContext());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        mLeftMenu.addView(mSizeSpinner, wrapParams);

        mGamesSpinner = new Spinner(context, Spinner.MODE_DROPDOWN);
        mLeftMenu.addView(mGamesSpinner, wrapParams);
        mGamesSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, 
                    int pos, long id) {
                GameEngine ge = GameEngine.getInstance();
                ge.restart(mGameInfos.get(pos), GameCanvas.this.getContext());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        
        this.addView(mLeftMenu, wrapParams);

        mGameContent = new GridView(context);
        mGameContent.setStretchMode(GridView.NO_STRETCH);
        mGameContent.setGravity(Gravity.CENTER);
        this.addView(mGameContent, wrapParams);

        mDpi = (float)context.getResources().getDisplayMetrics().densityDpi/ 160f;
    }

    void setGameInfo(ArrayList<GameInfo> gameInfos) {
        mGameInfos = gameInfos;
        String[] names = new String[gameInfos.size()];
        int i = 0;
        for (GameInfo info : gameInfos) {
            names[i++] = info.name;
        }
        ArrayAdapter<String> gameNameAdapter = new ArrayAdapter<String>(
                this.getContext(), android.R.layout.simple_spinner_item, names);
        gameNameAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mGamesSpinner.setAdapter(gameNameAdapter);
    }

    void setAdapter(MemoryObjAdapter adapter) {
        mAdapter = adapter;
        mGameContent.setAdapter(mAdapter);
    }

    public void setRows(int rows, int cols) {
        mRows = rows;
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
      int h = bottom - top;
      int w = right - left;

      int each = h / mRows;
      int offset = w - each * (mRows + 1);

      mGameContent.layout(offset, top, right, bottom);
      mGameContent.setColumnWidth(each - convertDpi(2));
      mGameContent.setNumColumns(mRows + 1);
      mGameContent.setVerticalSpacing(convertDpi(4));
      mGameContent.setHorizontalSpacing(0);
      
      mAdapter.setSize(each - convertDpi(6));
      
      mLeftMenu.layout(0, top, offset, bottom);
      mRestart.layout(0,  0, 300, 50);
      mSizeSpinner.layout(0,  50, 300, 100);
      mGamesSpinner.layout(0,  100, 300, 150);
    }

    private int convertDpi(int dp) {
        return (int)(mDpi * dp);
    }
}
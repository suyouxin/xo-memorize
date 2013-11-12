package com.morphoss.xo.memorize;

import java.util.ArrayList;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;

import com.morphoss.xo.memorize.res.GameInfo;

public class GameCanvas extends LinearLayout {
    final static String TAG = "GameCanvas"; 
    
    RelativeLayout mLeftMenu;
    GridView mGameContent;
    MemoryObjAdapter mAdapter;
    public static int theme;
    ArrayList<GameInfo> mGameInfos;

    Button mRestart;
    Spinner mSizeSpinner;
    Spinner mThemeSpinner;
    
    int mHeight;
    int mWidth;
    
    final static MenuAdapter.MenuInfo sizeMenus[] = { 
        new MenuAdapter.MenuInfo("4 x 5", R.drawable.size4),
        new MenuAdapter.MenuInfo("5 x 6", R.drawable.size5),
        new MenuAdapter.MenuInfo("6 x 7", R.drawable.size6),
        };
    
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

    public GameCanvas(final Context context, AttributeSet attrs, int defStyle) 
    {
        super(context, attrs, defStyle);
        this.setOrientation(LinearLayout.HORIZONTAL);

        LayoutParams wrapParams =
                new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        LayoutParams matchParams =
                new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);

        mLeftMenu = new RelativeLayout(context);
        final LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mLeftMenu = (RelativeLayout) inflater.inflate(R.layout.leftmenu, this, false);

        mRestart = (Button)mLeftMenu.findViewById(R.id.restart);
        mRestart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                GameEngine ge = GameEngine.getInstance();
                ge.restart(GameCanvas.this.getContext());
            }
        });

        mSizeSpinner = (Spinner)mLeftMenu.findViewById(R.id.change_size); 
        MenuAdapter sizeAdapter = new MenuAdapter(
                this.getContext(), R.layout.menu_text_img, sizeMenus);
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

        mGamesSpinner = (Spinner)mLeftMenu.findViewById(R.id.change_games); 
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
        mThemeSpinner= (Spinner) mLeftMenu.findViewById(R.id.change_theme);
        mThemeSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
        	@Override
            public void onItemSelected(AdapterView<?> parent, View view, 
                    int pos, long id) {
                //change theme
        		Log.d("listener","value on spinner : "+parent.getItemAtPosition(pos).toString());
        		if(parent.getItemAtPosition(pos).toString().equals(context.getString(R.string.defaultTheme))){
        			theme= 1;
        		}
        		if(parent.getItemAtPosition(pos).toString().equals(context.getString(R.string.mandala))){
        			theme= 2;
        		}
        		if(parent.getItemAtPosition(pos).toString().equals(context.getString(R.string.golden))){
        			theme= 3;
        		}
        		if(parent.getItemAtPosition(pos).toString().equals(context.getString(R.string.digital))){
        			theme= 4;
        		}
        		if(parent.getItemAtPosition(pos).toString().equals(context.getString(R.string.geometrical))){
        			theme= 5;
        		}
        		Log.d("listener","value of theme after spinner : "+theme);
        		GameEngine ge = GameEngine.getInstance();
        		ge.restart(GameCanvas.this.getContext());
        		
        		
        		
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
		});
        this.addView(mLeftMenu, wrapParams);

        LinearLayout linear = new LinearLayout(context);
        mGameContent = new GridView(context);
        mGameContent.setStretchMode(GridView.NO_STRETCH);
        linear.setGravity(Gravity.CENTER_HORIZONTAL);
        linear.addView(mGameContent, wrapParams);
        this.addView(linear, matchParams);

        mDpi = (float)context.getResources().getDisplayMetrics().densityDpi/ 160f;
    }

    void setGameInfo(ArrayList<GameInfo> gameInfos) {
        mGameInfos = gameInfos;
        MenuAdapter.MenuInfo[] gameMenus = new MenuAdapter.MenuInfo[gameInfos.size()];
        int i = 0;
        for (GameInfo info : gameInfos) {
            MenuAdapter.MenuInfo menuInfo = new MenuAdapter.MenuInfo();
            menuInfo.text = info.name;
            if (info.type.equals("1")) {
                menuInfo.imgResID = R.drawable.addition;    
            }
            else if (info.type.equals("2")) {
                menuInfo.imgResID = R.drawable.sounds;
            }
            else if (info.type.equals("3")) {
                menuInfo.imgResID = R.drawable.letters;
            }
            else {
                menuInfo.imgResID = 0;
            }
            gameMenus[i++] = menuInfo;
        }

        MenuAdapter gameAdapter = new MenuAdapter(
                this.getContext(), R.layout.menu_text_img, gameMenus);
        mGamesSpinner.setAdapter(gameAdapter);
    }

    void setAdapter(MemoryObjAdapter adapter) {
        mAdapter = adapter;
        mGameContent.setAdapter(mAdapter);
    }

    public void setRows(int rows, int cols) {
        mRows = rows;
    }

    public void sizeHasChanged() {
        int each = mHeight / mRows;
        mGameContent.setColumnWidth(each - convertDpi(2));
        mGameContent.setNumColumns(mRows + 1);
        mGameContent.setVerticalSpacing(convertDpi(4));
        mGameContent.setHorizontalSpacing(2);
        mAdapter.setSize(each - convertDpi(4));
    }
    
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        
        mHeight = this.getMeasuredHeight();
        mWidth = this.getMeasuredWidth();

        int each = mHeight / mRows;
        mGameContent.setColumnWidth(each - convertDpi(2));
        mGameContent.setNumColumns(mRows + 1);
        mGameContent.setVerticalSpacing(convertDpi(4));
        mGameContent.setHorizontalSpacing(2);
        mAdapter.setSize(each - convertDpi(4));
    }
    private int convertDpi(int dp) {
        return (int)(mDpi * dp);
    }
}
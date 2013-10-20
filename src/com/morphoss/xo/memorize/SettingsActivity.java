package com.morphoss.xo.memorize;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;

import com.morphoss.xo.memorize.obj.MStr;
import com.morphoss.xo.memorize.obj.MemoryObj;

public class SettingsActivity extends Activity {
	private EditText autoText1, autoText2;
	private ImageButton pairEquals, pairNonEquals, playGame;
	private FrameLayout mLayout;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.settings_layout);
		
		autoText1 = (EditText) findViewById(R.id.autoText1);
		autoText2 = (EditText) findViewById(R.id.autoText2);
		mLayout = (FrameLayout) findViewById(R.id.left2);
		
		pairEquals = (ImageButton) findViewById(R.id.pairEquals);
		pairEquals.setOnClickListener(new OnClickListener() {
			 
			@Override
			public void onClick(View v) {
 
				mLayout.setVisibility(View.INVISIBLE);
 
			}
 
		});
		pairNonEquals = (ImageButton) findViewById(R.id.pairNonEquals);
		pairNonEquals.setOnClickListener(new OnClickListener() {
			 
			@Override
			public void onClick(View v) {
 
				mLayout.setVisibility(View.VISIBLE);
 
			}
 
		});
		
		playGame = (ImageButton) findViewById(R.id.playGame);
		playGame.setOnClickListener(new OnClickListener() {
			 
			@Override
			public void onClick(View v) {

			Intent intent = new Intent(v.getContext(), Memorize.class);
       		startActivity(intent);
       		finish();

			}

		});
		//remove focus at startup
		this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		
		ObjView view1 = (ObjView) findViewById(R.id.obj_view_1);
		MemoryObj obj1 = new MStr(autoText1.getText().toString());
		obj1.show();
		view1.setObj(obj1);
		ObjView view2 = (ObjView) findViewById(R.id.obj_view_2);
		MemoryObj obj2 = new MStr(autoText2.getText().toString());
		obj2.show();
		view2.setObj(obj2);
		ObjView view3 = (ObjView) findViewById(R.id.obj_view_3);
		MemoryObj obj3 = new MStr("2+3");
		obj3.show();
		view3.setObj(obj3);
		ObjView view4 = (ObjView) findViewById(R.id.obj_view_4);
		MemoryObj obj4 = new MStr("5");
		obj4.show();
		view4.setObj(obj4);
		
		autoText1.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            	ObjView view1 = (ObjView) findViewById(R.id.obj_view_1);
            	MemoryObj obj1 = new MStr(autoText1.getText().toString());
        		obj1.show();
        		view1.setObj(obj1);
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                
            }
        });
		autoText2.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            	ObjView view2 = (ObjView) findViewById(R.id.obj_view_2);
        		MemoryObj obj2 = new MStr(autoText2.getText().toString());
        		obj2.show();
        		view2.setObj(obj2);
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            	
            }

            @Override
            public void afterTextChanged(Editable s) {
                
            }
        });

	}
	  
}

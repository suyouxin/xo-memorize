package com.morphoss.xo.memorize;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.morphoss.xo.memorize.obj.MStr;
import com.morphoss.xo.memorize.obj.MemoryObj;

public class SettingsActivity extends Activity {
	
	private static final String TAG = "SettingsActivity";
	
	private EditText autoText1, autoText2;
	private ImageButton pairEquals, pairNonEquals, playGame, editClear, addPicture;
	private FrameLayout mLayout;
	private LinearLayout mGallery;
	private MemoryObjAdapter moa;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.settings_layout);
		
		autoText1 = (EditText) findViewById(R.id.autoText1);
		autoText2 = (EditText) findViewById(R.id.autoText2);
		mLayout = (FrameLayout) findViewById(R.id.left2);
		addPicture = (ImageButton) findViewById(R.id.addPicture);
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
		editClear = (ImageButton) findViewById(R.id.editClear);
		editClear.setOnClickListener(new OnClickListener() {
			 
			@Override
			public void onClick(View v) {

			mLayout.removeAllViews();

			}

		});
		
		//remove focus at startup
		this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		
		mGallery = (LinearLayout) findViewById(R.id.newItems);
		moa = new MemoryObjAdapter(this);
		for(int i=0; i<moa.getCount();i++){
			mGallery.addView(moa.getView(i, null, mGallery));
		}
		ObjView view1 = (ObjView) findViewById(R.id.obj_view_1);
		MemoryObj obj1 = new MStr(autoText1.getText().toString());
		obj1.show();
		view1.setObj(obj1);
		ObjView view2 = (ObjView) findViewById(R.id.obj_view_2);
		MemoryObj obj2 = new MStr(autoText2.getText().toString());
		obj2.show();
		view2.setObj(obj2);
		
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
		addPicture.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				AlertDialog.Builder builder = new AlertDialog.Builder(SettingsActivity.this);
				builder.setTitle(R.string.addPicture);
				builder.setIcon(R.drawable.import_picture);
				builder.setItems(R.array.addPictureArray, new DialogInterface.OnClickListener() {
		            public void onClick(DialogInterface dialog, int item) {
		                // Do something with the selection
		            	if(item == 0){
		            		Log.d(TAG, "select picture from gallery");
		            	}
		            	if(item == 1){
		            		Log.d(TAG, "select picture from the camera");
		            	}
		            }
				});
				AlertDialog alert = builder.create();
		        alert.show();
				
			}
		});
		
		

	}

}

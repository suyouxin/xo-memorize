package com.morphoss.xo.memorize.settings;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.morphoss.xo.memorize.ObjView;
import com.morphoss.xo.memorize.R;
import com.morphoss.xo.memorize.obj.MStr;
import com.morphoss.xo.memorize.obj.MemoryObj;

public class AdditionActivity extends Activity {

	private static final String TAG = "AdditionActivity";

	private ObjView view1, view2;
	private EditText editTextAddition, editTextAddition2;
	private ImageButton addItem, saveGame, clearGame;
	private GalleryObjectAdapter goa;
	private LinearLayout mGallery;
	private Spinner spinner;
	private Context context = this;
	private static ArrayList<MemoryObj> listNewObjsAddition = new ArrayList<MemoryObj>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.addition_layout);

		editTextAddition = (EditText) findViewById(R.id.editTxtAddition);
		editTextAddition2 = (EditText) findViewById(R.id.editTxtAddition2);
		addItem = (ImageButton) findViewById(R.id.addItems);
		saveGame = (ImageButton) findViewById(R.id.saveGame);
		clearGame = (ImageButton) findViewById(R.id.clearGame);
		mGallery = (LinearLayout) findViewById(R.id.newItems);
		addListenerOnSpinner();

		mGallery.removeAllViews();
		listNewObjsAddition.clear();
		goa = new GalleryObjectAdapter(AdditionActivity.this, listNewObjsAddition);
		for (int i = 0; i < goa.getCount(); i++) {
			mGallery.addView(goa.getView(i, null, mGallery));

		}

		clearGame.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				listNewObjsAddition.clear();
				mGallery.removeAllViews();

			}
		});
		saveGame.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
			if(listNewObjsAddition.size() <=4){
				//not a usable game, display an alert dialog
				AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
				// set dialog message
				alertDialogBuilder
					.setMessage(R.string.needMorePairs)
					.setCancelable(false)
					.setPositiveButton(R.string.gotIt,new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog,int id) {
							// if this button is clicked, close dialog
							dialog.dismiss();
							
						}
					  });
	 
					// create alert dialog
					AlertDialog alertDialog = alertDialogBuilder.create();
	 
					// show it
					alertDialog.show();
			}else{
				AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
				// set dialog message
				alertDialogBuilder
					.setTitle(R.string.titleSave)
					.setMessage(R.string.areYouSure)
					.setCancelable(false)
					.setPositiveButton(R.string.yes,new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog,int id) {
							// if this button is clicked, close dialog
							savingGame();
						}
						})
					.setNegativeButton(R.string.no,new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog,int id) {
							// if this button is clicked, close dialog
							savingGame();
							
						}
					  	});
	 
					// create alert dialog
					AlertDialog alertDialog = alertDialogBuilder.create();
	 
					// show it
					alertDialog.show();
				
			}
				
			}
		});

		addItem.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (!editTextAddition.getText().toString().equals("")
						&& !editTextAddition2.getText().toString().equals("")) {
					view1.getObj().setPairedObj(view2.getObj());
					listNewObjsAddition.add(view1.getObj());
					mGallery.addView(goa.getView(goa.getCount() - 1, null,
							mGallery));

					for (int i = 0; i < listNewObjsAddition.size(); i++) {
						Log.d(TAG, "list : " + listNewObjsAddition.get(i));
						Log.d(TAG, "count in goa : " + goa.getCount());
					}

				}
			}
		});
		// remove auto focus from edit text
		this.getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

		view1 = (ObjView) findViewById(R.id.obj_view_pair1);
		MemoryObj obj1 = new MStr(editTextAddition.getText().toString());
		obj1.show();
		view1.setObj(obj1);

		view2 = (ObjView) findViewById(R.id.obj_view_pair2);
		MemoryObj obj2 = new MStr(editTextAddition.getText().toString());
		obj2.show();
		view2.setObj(obj2);

		view1.getObj().setPairedObj(obj2);
		view2.getObj().setPairedObj(obj1);
		editTextAddition.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				setViews(OnAdditionSelectedListener.additionSelected);

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}

			@Override
			public void afterTextChanged(Editable s) {

			}
		});
		editTextAddition2.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				setViews(OnAdditionSelectedListener.additionSelected);
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {

			}

			@Override
			public void afterTextChanged(Editable s) {

			}
		});

	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		Intent intent = new Intent(context, SettingsActivity.class);
		startActivity(intent);
		finish();
	}
	public void addListenerOnSpinner() {
		spinner = (Spinner) findViewById(R.id.spinner);
		// Create an ArrayAdapter using the string array and a default spinner
		// layout
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
				this, R.array.addition_array, R.layout.spinner_item);
		// Specify the layout to use when the list of choices appears
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// Apply the adapter to the spinner
		spinner.setAdapter(adapter);
		spinner.setOnItemSelectedListener(new OnAdditionSelectedListener(this));
	}

	public void setViews(String addition) {

		if (addition == null) {
			addition = "+";
		}

		int value1 = 0;
		int value2 = 0;
		int total = 0;

		if (editTextAddition.getText().toString().equals("")) {
			value1 = 0;
			addition = "";
		} else {
			value1 = Integer.parseInt(editTextAddition.getText().toString());
		}
		if (editTextAddition2.getText().toString().equals("")) {
			value2 = 0;
			addition = "";
		} else {
			value2 = Integer.parseInt(editTextAddition2.getText().toString());
		}
		if (addition.equals("+")) {
			total = value1 + value2;
		} else if (addition.equals("-")) {
			total = value1 - value2;
		} else if (addition.equals("*")) {
			total = value1 * value2;
		} else {
			total = 0;
		}

		String totalString = "";
		if (editTextAddition.getText().toString().equals("")
				|| editTextAddition2.getText().toString().equals("")) {
			totalString = "";
		} else {
			totalString = String.valueOf(total);
		}

		String sequence = editTextAddition.getText().toString() + addition
				+ editTextAddition2.getText().toString();
		MemoryObj obj1 = new MStr(sequence);
		obj1.show();
		view1.setObj(obj1);

		MemoryObj obj2 = new MStr(totalString);
		obj2.show();
		view2.setObj(obj2);
	}
	
	private void savingGame(){
		
	}
}

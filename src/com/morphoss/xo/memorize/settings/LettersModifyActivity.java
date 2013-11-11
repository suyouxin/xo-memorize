package com.morphoss.xo.memorize.settings;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import org.xmlpull.v1.XmlPullParserException;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.morphoss.xo.memorize.Memorize;
import com.morphoss.xo.memorize.ObjView;
import com.morphoss.xo.memorize.R;
import com.morphoss.xo.memorize.obj.MStr;
import com.morphoss.xo.memorize.obj.MemoryObj;
import com.morphoss.xo.memorize.res.loadGameXmlParser;

public class LettersModifyActivity extends Activity {

	private static final String TAG = "LettersActivity";

	private ObjView view1, view2;
	private TextView numberPairs;
	private EditText editTextLetter;
	private ImageButton addItem, saveGame, clearGame;
	private GalleryObjectAdapter goa;
	private LinearLayout mGallery;
	private String name, type;
	private Context context = this;
	private static ArrayList<MemoryObj> listNewObjsLetters = new ArrayList<MemoryObj>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.letters_layout);

		Bundle extras = getIntent().getExtras();
		name = extras.getString("name");
		type = extras.getString("type");

		numberPairs = (TextView) findViewById(R.id.numberPairsCreated);
		editTextLetter = (EditText) findViewById(R.id.editTxtLetter);
		addItem = (ImageButton) findViewById(R.id.addItems);
		saveGame = (ImageButton) findViewById(R.id.saveGame);
		clearGame = (ImageButton) findViewById(R.id.clearGame);
		mGallery = (LinearLayout) findViewById(R.id.newItems);

		mGallery.removeAllViews();
		listNewObjsLetters.clear();
		int size = readGame();
		Log.d(TAG, "size: " + size);
		numberPairs.setText(String.valueOf(size));

		goa = new GalleryObjectAdapter(LettersModifyActivity.this,
				listNewObjsLetters, 6);
		for (int i = 0; i < goa.getCount(); i++) {
			mGallery.addView(goa.getView(i, null, mGallery));

		}

		clearGame.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				listNewObjsLetters.clear();
				mGallery.removeAllViews();
				numberPairs.setText("0");

			}
		});
		saveGame.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (listNewObjsLetters.size() <= 9) {
					// not a usable game, display an alert dialog
					AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
							context);
					// set dialog message
					alertDialogBuilder
							.setMessage(R.string.needMorePairs)
							.setCancelable(false)
							.setPositiveButton(R.string.gotIt,
									new DialogInterface.OnClickListener() {
										public void onClick(
												DialogInterface dialog, int id) {
											// if this button is clicked, close
											// dialog
											dialog.dismiss();

										}
									});

					// create alert dialog
					AlertDialog alertDialog = alertDialogBuilder.create();

					// show it
					alertDialog.show();
				} else {
					AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
							context);
					// set dialog message
					alertDialogBuilder
							.setTitle(R.string.titleSave)
							.setMessage(R.string.areYouSure)
							.setCancelable(false)
							.setPositiveButton(R.string.yes,
									new DialogInterface.OnClickListener() {
										public void onClick(
												DialogInterface dialog, int id) {
											// if this button is clicked, save
											// the game
											savingGame();
											Intent intent = new Intent(context,
													Memorize.class);
											startActivity(intent);
											finish();
										}
									})
							.setNegativeButton(R.string.no,
									new DialogInterface.OnClickListener() {
										public void onClick(
												DialogInterface dialog, int id) {
											// if this button is clicked, close
											// dialog
											dialog.dismiss();

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
				if (!editTextLetter.getText().toString().equals("")) {
					view1.getObj().setPairedObj(view2.getObj());
					listNewObjsLetters.add(view1.getObj());
					mGallery.addView(goa.getView(goa.getCount() - 1, null,
							mGallery));
					numberPairs.setText(String.valueOf(goa.getCount()));
					for (int i = 0; i < listNewObjsLetters.size(); i++) {
						Log.d(TAG, "list : " + listNewObjsLetters.get(i));
						Log.d(TAG, "count in goa : " + goa.getCount());
					}

				}
			}
		});
		// remove auto focus from edit text
		this.getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

		view1 = (ObjView) findViewById(R.id.obj_view_pair1);
		MemoryObj obj1 = new MStr(editTextLetter.getText().toString());
		obj1.show();
		view1.setObj(obj1);

		view2 = (ObjView) findViewById(R.id.obj_view_pair2);
		MemoryObj obj2 = new MStr(editTextLetter.getText().toString());
		obj2.show();
		view2.setObj(obj2);

		view1.getObj().setPairedObj(obj2);
		view2.getObj().setPairedObj(obj1);
		editTextLetter.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				setViews();

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

	public void setViews() {

		MemoryObj obj1 = new MStr(editTextLetter.getText().toString());
		obj1.show();
		view1.setObj(obj1);

		MemoryObj obj2 = new MStr(editTextLetter.getText().toString()
				.toUpperCase());
		obj2.show();
		view2.setObj(obj2);
		view1.invalidate();
		view2.invalidate();
	}

	private void savingGame() {
		// Create folder
		File folder = new File(Environment.getExternalStorageDirectory()
				.toString()
				+ "/Android/data/com.morphoss.xo.memorize/files/games/" + name);
		folder.mkdirs();
		// Save the path as a string value
		String extStorageDirectory = folder.toString();
		try {
			BufferedWriter buf = new BufferedWriter(new FileWriter(
					extStorageDirectory + "/game.xml"));
			StringBuilder strBuilder = new StringBuilder(
					"<?xml version=\"1.0\"?>\n");
			strBuilder.append("\n");
			strBuilder
					.append("<memorize name=\""
							+ name
							+ "\" type=\"3\"  scoresnd=\"score.wav\" winsnd=\"win.wav\" divided=\"1\" align=\"1\" face1=\"1\" face2=\"2\" >\n");
			for (MemoryObj obj : listNewObjsLetters) {

				strBuilder.append("<pair achar=\"" + ((MStr) obj).getString()
						+ "\" bchar=\""
						+ ((MStr) obj.getPairedObj()).getString() + "\"/>\n");

			}
			strBuilder.append("\n");
			strBuilder.append("</memorize>");
			buf.write(strBuilder.toString());
			buf.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private int readGame() {
		File file = new File(Environment.getExternalStorageDirectory()
				.toString()
				+ "/Android/data/com.morphoss.xo.memorize/files/games/"
				+ name
				+ "/game.xml");
		if (file != null && file.exists()) {
			loadGameXmlParser parser = new loadGameXmlParser();
			try {
				listNewObjsLetters = parser.parse(new FileInputStream(file),
						file);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (XmlPullParserException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return listNewObjsLetters.size();
	}
}
package com.morphoss.xo.memorize.settings;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import org.xmlpull.v1.XmlPullParserException;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.View.OnLongClickListener;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.morphoss.xo.memorize.Memorize;
import com.morphoss.xo.memorize.R;
import com.morphoss.xo.memorize.res.GameInfo;
import com.morphoss.xo.memorize.res.GameResource;
import com.morphoss.xo.memorize.res.GameXmlParser;

public class SettingsActivity extends Activity {

	private Context context = this;
	private EditText editText;
	private RadioGroup radioTypeGroup;
	private RadioButton radioTypeButton;
	private Button btnNext;
	public static SharedPreferences prefs;
	private ArrayList<GameInfo> list = new ArrayList<GameInfo>();
	private ListView listview;
	private final static String DIRECTORY_GAMES = "games";
	private final ArrayList<String> listGames = new ArrayList<String>();
	private static final String TAG = "SettingsActivity";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.settings_layout);
		listview = (ListView) findViewById(R.id.listview);
		editText = (EditText) findViewById(R.id.editTxt);
		// remove auto focus from edit text
		this.getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		addLists();
		listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(final AdapterView<?> parent,
					final View view, final int position, long id) {
				AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
						context);
				// set dialog message
				alertDialogBuilder
						.setTitle(R.string.titleModify)
						.setMessage(R.string.modifyOrDelete)
						.setCancelable(false)
						.setPositiveButton(R.string.modify,
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int id) {
										// if this button is clicked, save
										// the game
										modifyGame(parent, position);
									}
								})
						.setNeutralButton(R.string.delete,
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int id) {
										// if this button is clicked, save
										// the game
										deleteGame(parent, position);
									}
								})
						.setNegativeButton(R.string.cancel,
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int id) {
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
		});

		addListenerOnButton();
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		Intent intent = new Intent(context, Memorize.class);
		startActivity(intent);
		finish();
	}

	public void addListenerOnButton() {
		radioTypeGroup = (RadioGroup) findViewById(R.id.radioType);
		btnNext = (Button) findViewById(R.id.nextBtn);
		editText.setOnKeyListener(new OnKeyListener() {

			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				keyCode = event.getKeyCode();
				Log.d(TAG, "Key pressed on " + v.getClass().toString());
				if (event.getAction() == KeyEvent.ACTION_DOWN) {
					Log.d(TAG, "key event action down");
					switch (keyCode) {
					case KeyEvent.KEYCODE_DPAD_CENTER:
					case KeyEvent.KEYCODE_ENTER:
						// dismiss keyboard when click on enter
						InputMethodManager inputManager = (InputMethodManager) context
								.getSystemService(Context.INPUT_METHOD_SERVICE);
						inputManager.toggleSoftInput(0, 0);
						return true;
					default:
						break;
					}
				}
				return false;
			}
		});
		editText.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}

			@Override
			public void afterTextChanged(Editable s) {
				if (s.toString().length() < 3) {
					btnNext.setEnabled(false);
				} else {
					btnNext.setEnabled(true);
					btnNext.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							boolean gameAlreadyExist = false;
							for (int i = 0; i < listGames.size(); i++) {
								if (list.get(i).name.equals(editText.getText().toString())) {
									// a game with this name already exists
									gameAlreadyExist = true;
								}
							}
							
							if (!gameAlreadyExist) {
								prefs = context.getSharedPreferences(
										"com.morphoss.xo.memorize.settings",
										Context.MODE_PRIVATE);
								String nameGame = editText.getText().toString();
								String nameGameKey = "com.morphoss.xo.memorize.settings";
								prefs.edit().putString(nameGameKey, nameGame)
										.commit();
								int selectedId = radioTypeGroup
										.getCheckedRadioButtonId();
								radioTypeButton = (RadioButton) findViewById(selectedId);

								if (radioTypeButton
										.getText()
										.toString()
										.equals(context
												.getString(R.string.radio_addition))) {
									Intent intent = new Intent(context,
											AdditionActivity.class);
									startActivity(intent);
									finish();
								}
								if (radioTypeButton
										.getText()
										.toString()
										.equals(context
												.getString(R.string.radio_sounds))) {
									Intent intent = new Intent(context,
											SoundsActivity.class);
									startActivity(intent);
									finish();
								}
								if (radioTypeButton
										.getText()
										.toString()
										.equals(context
												.getString(R.string.radio_letters))) {
									Intent intent = new Intent(context,
											LettersActivity.class);
									startActivity(intent);
									finish();
								}
							}else{
								//show a alert dialog
								AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
										context);
								// set dialog message
								alertDialogBuilder
										.setTitle(R.string.alreadyExist)
										.setMessage(R.string.sameName)
										.setCancelable(false)
										.setNeutralButton(R.string.gotIt,
												new DialogInterface.OnClickListener() {
													public void onClick(
															DialogInterface dialog, int id) {
														// if this button is clicked, dismiss the window
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

				}

			}
		});

	}

	private String findGameType(String nameGame) {
		File path = context.getExternalFilesDir(DIRECTORY_GAMES);
		GameInfo gameInfo = null;
		String gameType = null;
		File file = new File(path + "/" + nameGame, "game.xml");
		if (file != null && file.exists()) {
			GameXmlParser parser = new GameXmlParser();

			try {
				gameInfo = parser.parse(new FileInputStream(file), file);
				gameType = gameInfo.type;
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (XmlPullParserException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return gameType;
	}

	private void modifyGame(AdapterView<?> parent, int position) {
		// modify the selected game
		final String item = (String) parent.getItemAtPosition(position);
		Log.d(TAG, "you have selected : " + item);
		// get the type of the game from game.xml
		String type = findGameType(item);
		Log.d(TAG, "game type : " + type);
		if (type.equals("1")) {
			// type: addition
			Intent intent = new Intent(context, AdditionModifyActivity.class);
			intent.putExtra("name", item);
			intent.putExtra("type", type);
			startActivity(intent);
			finish();

		}
		if (type.equals("2")) {
			// type: sounds
			Intent intent = new Intent(context, SoundsModifyActivity.class);
			intent.putExtra("name", item);
			intent.putExtra("type", type);
			startActivity(intent);
			finish();
		}
		if (type.equals("3")) {
			// type: letters
			Intent intent = new Intent(context, LettersModifyActivity.class);
			intent.putExtra("name", item);
			intent.putExtra("type", type);
			startActivity(intent);
			finish();
		}
	}

	private void addLists() {
		GameResource res = new GameResource(context);
		res.initAssetGame();
		list.clear();
		listGames.clear();
		list = res.getAllGames();
		Log.d(TAG, "list of all games :");
		for (int i = 0; i < list.size(); i++) {
			Log.d(TAG, "name : " + list.get(i).name);
		}
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).name.equals("addition")
					|| list.get(i).name.equals("sounds")
					|| list.get(i).name.equals("letters")) {
				// don't allow to modify the basics games
			} else {
				listGames.add(list.get(i).name);
			}
		}
		final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				R.layout.listlayout, R.id.listTextView, listGames);
		listview.setAdapter(adapter);
	}

	private void deleteGame(AdapterView<?> parent, int position) {
		// delete the selected game
		final String item = (String) parent.getItemAtPosition(position);
		Log.d(TAG, "you have selected : " + item);
		// get the type of the game from game.xml
		String type = findGameType(item);
		Log.d(TAG, "game type : " + type);
		listGames.remove(item);
		File dir = new File(Environment.getExternalStorageDirectory()
				.toString()
				+ "/Android/data/com.morphoss.xo.memorize/files/games/" + item);
		if (dir.isDirectory()) {
			String[] children = dir.list();
			for (int i = 0; i < children.length; i++) {
				new File(dir, children[i]).delete();
			}
			dir.delete();
		}
		addLists();
	}
}

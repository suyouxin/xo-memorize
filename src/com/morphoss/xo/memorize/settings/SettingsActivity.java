package com.morphoss.xo.memorize.settings;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.morphoss.xo.memorize.Memorize;
import com.morphoss.xo.memorize.R;
import com.morphoss.xo.memorize.res.GameInfo;
import com.morphoss.xo.memorize.res.GameResource;

public class SettingsActivity extends Activity {

	private Context context = this;
	private EditText editText;
	private RadioGroup radioTypeGroup;
	private RadioButton radioTypeButton;
	private Button btnNext;
	public static SharedPreferences prefs;
	private ArrayList<GameInfo> list = new ArrayList<GameInfo>();
	private ListView listview;
	private final ArrayList<String> listGames = new ArrayList<String>();
	private static final String TAG = "SettingsActivity";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.settings_layout);
		listview = (ListView) findViewById(R.id.listview);
		// remove auto focus from edit text
		this.getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		GameResource res = new GameResource(context);
		res.initAssetGame();
		list.clear();
		list = res.getAllGames();
		Log.d(TAG, "list of all games :");
		for (int i = 0; i < list.size(); i++) {
			Log.d(TAG, "name : " + list.get(i).name);
		}
		for (int i = 0; i < list.size(); i++) {
			listGames.add(list.get(i).name);
		}
		final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				R.layout.listlayout, R.id.listTextView, listGames);
		listview.setAdapter(adapter);
	    listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

	        @Override
	        public void onItemClick(AdapterView<?> parent, final View view,
	            int position, long id) {
	          final String item = (String) parent.getItemAtPosition(position);
	          //modify item selected
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
		editText = (EditText) findViewById(R.id.editTxt);
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
						}
					});
				}

			}
		});

	}
}

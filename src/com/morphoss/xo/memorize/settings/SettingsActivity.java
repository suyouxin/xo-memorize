package com.morphoss.xo.memorize.settings;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.morphoss.xo.memorize.R;

public class SettingsActivity extends Activity {

	private Context context = this;
	private EditText editText;
	private RadioGroup radioTypeGroup;
	private RadioButton radioTypeButton;
	private Button btnNext;
	public static SharedPreferences prefs;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.settings_layout);

		// remove auto focus from edit text
		this.getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

		addListenerOnButton();
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

package com.morphoss.xo.memorize.settings;

import com.morphoss.xo.memorize.R;

import android.app.Activity;
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
import android.widget.Toast;

public class SettingsActivity extends Activity{
	
	private EditText editText;
	private RadioGroup radioTypeGroup;
	private RadioButton radioTypeButton;
	private Button btnNext;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.settings_layout);
		
		//remove auto focus from edit text
		this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		
		
		radioTypeGroup = (RadioGroup) findViewById(R.id.radioType);
		btnNext = (Button) findViewById(R.id.nextBtn);
		editText = (EditText) findViewById(R.id.editTxt);
		
		addListenerOnButton();
		
		editText.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				if(s.toString().length() < 3){
					btnNext.setEnabled(false);
				}else{
					btnNext.setEnabled(true);
				}
				
			}
		});
	}

	public void addListenerOnButton() {
		
		btnNext.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				int selectedId = radioTypeGroup.getCheckedRadioButtonId();
				radioTypeButton = (RadioButton) findViewById(selectedId);
				//get the type of game selected
				Toast.makeText(SettingsActivity.this,
						radioTypeButton.getText(), Toast.LENGTH_SHORT).show();
				
			}
		});
		
	}

}

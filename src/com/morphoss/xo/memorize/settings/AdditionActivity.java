package com.morphoss.xo.memorize.settings;

import com.morphoss.xo.memorize.ObjView;
import com.morphoss.xo.memorize.R;
import com.morphoss.xo.memorize.obj.MStr;
import com.morphoss.xo.memorize.obj.MemoryObj;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.WindowManager;
import android.widget.EditText;

public class AdditionActivity extends Activity{
	
	private ObjView view1, view2;
	private EditText editTextAddition;
	
@Override
protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.addition_layout);
	
	editTextAddition = (EditText) findViewById(R.id.editTxtAddition);
	
	//remove auto focus from edit text
	this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
	
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
		public void onTextChanged(CharSequence s, int start, int before, int count) {
			ObjView view1 = (ObjView) findViewById(R.id.obj_view_pair1);
			MemoryObj obj1 = new MStr(editTextAddition.getText().toString());
			obj1.show();
			view1.setObj(obj1);
			
			ObjView view2 = (ObjView) findViewById(R.id.obj_view_pair2);
			MemoryObj obj2 = new MStr(editTextAddition.getText().toString());
			obj2.show();
			view2.setObj(obj2);
			
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
}

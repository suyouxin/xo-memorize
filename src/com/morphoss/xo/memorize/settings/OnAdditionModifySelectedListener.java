package com.morphoss.xo.memorize.settings;

import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;

import com.morphoss.xo.memorize.R;

public class OnAdditionModifySelectedListener implements OnItemSelectedListener {
	
	private AdditionModifyActivity mActivity;
	
	public  OnAdditionModifySelectedListener(AdditionModifyActivity activity) {
		mActivity = activity;
	}

	public static String additionSelected;
	@Override
	public void onItemSelected(AdapterView parent, View view, int pos, long id) {
		 if(parent.getItemAtPosition(pos).toString().equals(mActivity.getString(R.string.plus))){
			 additionSelected = "+";
		 }
		 if(parent.getItemAtPosition(pos).toString().equals(mActivity.getString(R.string.minus))){
			 additionSelected = "-";
		 }
		 if(parent.getItemAtPosition(pos).toString().equals(mActivity.getString(R.string.mult))){
			 additionSelected = "*";
		 }
		 mActivity.setViews(additionSelected);
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		
	}
}

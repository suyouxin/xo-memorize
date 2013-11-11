package com.morphoss.xo.memorize.settings;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.AdapterView.OnItemSelectedListener;

import com.morphoss.xo.memorize.ObjView;
import com.morphoss.xo.memorize.R;
import com.morphoss.xo.memorize.obj.MStr;
import com.morphoss.xo.memorize.obj.MemoryObj;

public class OnAdditionSelectedListener implements OnItemSelectedListener {

	private static final String TAG = "OnAdditionSelectedListener";

	private AdditionActivity mActivity;

	public OnAdditionSelectedListener(AdditionActivity activity) {
		mActivity = activity;
	}

	public static String additionSelected;

	@Override
	public void onItemSelected(AdapterView parent, View view, int pos, long id) {
		if (parent.getItemAtPosition(pos).toString()
				.equals(mActivity.getString(R.string.plus))) {
			additionSelected = "+";
		}
		if (parent.getItemAtPosition(pos).toString()
				.equals(mActivity.getString(R.string.minus))) {
			additionSelected = "-";
		}
		if (parent.getItemAtPosition(pos).toString()
				.equals(mActivity.getString(R.string.mult))) {
			additionSelected = "*";
		}
		Log.d(TAG, "addition has changed with value : " +additionSelected);
		mActivity.setViews(additionSelected);
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {

	}
}

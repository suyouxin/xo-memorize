package com.morphoss.xo.memorize;

import java.util.ArrayList;
import java.util.Collection;

import com.morphoss.xo.memorize.obj.MemoryObj;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


public class GalleryObjectAdapter {
	/**
	 * This class is an adapter for the gallery in SettingsActivity
	 */
	private Context context;

	private static final String TAG = "GalleryObjectAdapter";
	private static ObjView galleryImage;
	private static MemoryObj object;

	public GalleryObjectAdapter(Context context) {
		super();
		this.context = context;

	}

	/**
	 * This method get the memoryobj view from the gridview
	 */
	public View getView(int position, View convertView, ViewGroup parent) {

		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		object = SettingsActivity.listNewObjs.get(position);
		View galleryLayout = inflater.inflate(R.layout.settings_gallery, null);
		galleryImage = (ObjView) galleryLayout.findViewById(R.id.obj_view);
		object.show();
		galleryImage.setObj(object);

		return galleryLayout;
	}

	public void setMemoryObj(Collection<MemoryObj> newMemoryObj) {

		SettingsActivity.listNewObjs = new ArrayList<MemoryObj>(
				newMemoryObj.size());
		SettingsActivity.listNewObjs.addAll(newMemoryObj);

	}

	public int getCount() {
		if (SettingsActivity.listNewObjs == null)
			return 0;
		return SettingsActivity.listNewObjs.size();
	}

	public Object getItem(int position) {
		return SettingsActivity.listNewObjs.get(position);
	}

	public void setLayout(View v) {
		ViewGroup.LayoutParams lp = v.getLayoutParams();
		v.setLayoutParams(lp);
	}

	/**
	 * this method gets the current category
	 * 
	 * @param position
	 * @return the category selected
	 */
	public static MemoryObj getMemoryObj(int position) {
		return SettingsActivity.listNewObjs.get(position);
	}
}
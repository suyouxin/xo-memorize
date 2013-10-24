package com.morphoss.xo.memorize;

import java.util.ArrayList;
import java.util.Collection;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.morphoss.xo.memorize.obj.MemoryObj;


public class GalleryObjectAdapter {
	/**
	 * This class is an adapter for the gallery in SettingsActivity
	 */
	private Context context;

	private static final String TAG = "GalleryObjectAdapter";
	private static ObjView galleryImageLeft, galleryImageRight;
	private static MemoryObj object, paired;

	public GalleryObjectAdapter(Context context) {
		super();
		this.context = context;

	}

	/**
	 * This method get the memoryobj view 
	 */
	public View getView(int position, View convertView, ViewGroup parent) {

		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		object = SettingsActivity.listNewObjs.get(position);
		paired = object.getPairedObj();
		View galleryLayout = inflater.inflate(R.layout.settings_gallery, null);
		if (paired == null) {
			//we should not be here
		}
		else {
			galleryImageLeft = (ObjView) galleryLayout.findViewById(R.id.obj_viewLeft);
			galleryImageRight = (ObjView) galleryLayout.findViewById(R.id.obj_viewRight);
			object.show();
			paired.show();
			galleryImageLeft.setObj(object);
			galleryImageRight.setObj(paired);
		}

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

	public void removePair(MemoryObj obj){
		SettingsActivity.listNewObjs.remove(obj);
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
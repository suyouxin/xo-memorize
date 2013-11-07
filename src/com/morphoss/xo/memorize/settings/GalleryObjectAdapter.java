package com.morphoss.xo.memorize.settings;

import java.util.ArrayList;
import java.util.Collection;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.morphoss.xo.memorize.ObjView;
import com.morphoss.xo.memorize.R;
import com.morphoss.xo.memorize.obj.MemoryObj;


public class GalleryObjectAdapter extends BaseAdapter{
	/**
	 * This class is an adapter for the gallery in SettingsActivity
	 */
	private Context context;

	private static final String TAG = "GalleryObjectAdapter";
	private static ObjView galleryImageLeft, galleryImageRight;
	private static MemoryObj object, paired;
	private ImageButton deletepair;
	private static ArrayList<MemoryObj> mlist = new ArrayList<MemoryObj>();

	public GalleryObjectAdapter(Context context, ArrayList<MemoryObj> list) {
		super();
		this.context = context;
		this.mlist = list;

	}

	/**
	 * This method get the memoryobj view 
	 */
	public View getView(int position, View convertView, final ViewGroup parent) {

		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		object = mlist.get(position);
		paired = object.getPairedObj();
		final View galleryLayout = inflater.inflate(R.layout.settings_gallery, null);
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
		deletepair = (ImageButton) galleryLayout.findViewById(R.id.deletePair);
		deletepair.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Log.d(TAG, "try to remove pair");
				mlist.remove(object);
				parent.removeView(galleryLayout);
				notifyDataSetChanged();
			}
		});

		return galleryLayout;
	}

	public void setMemoryObj(Collection<MemoryObj> newMemoryObj) {

		mlist = new ArrayList<MemoryObj>(
				newMemoryObj.size());
		mlist.addAll(newMemoryObj);

	}

	public int getCount() {
		if (mlist == null)
			return 0;
		return mlist.size();
	}

	public Object getItem(int position) {
		return mlist.get(position);
	}

	public void setLayout(View v) {
		ViewGroup.LayoutParams lp = v.getLayoutParams();
		v.setLayoutParams(lp);
	}

	public void removePair(MemoryObj obj){
		mlist.remove(obj);
	}
	/**
	 * this method gets the current category
	 * 
	 * @param position
	 * @return the category selected
	 */
	public static MemoryObj getMemoryObj(int position) {
		return mlist.get(position);
	}

	@Override
	public long getItemId(int arg0) {
		return 0;
	}
}
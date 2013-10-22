package com.morphoss.xo.memorize;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.morphoss.xo.memorize.obj.MBitmap;
import com.morphoss.xo.memorize.obj.MMedia;
import com.morphoss.xo.memorize.obj.MStr;
import com.morphoss.xo.memorize.obj.MemoryObj;

public class SettingsActivity extends Activity {

	private static final String TAG = "SettingsActivity";

	private EditText autoText1, autoText2;
	private ImageButton pairEquals, pairNonEquals, playGame, addItem,
			editClear, addPictureLeft, addPictureRight;
	private ObjView objLeft, objRight;
	private FrameLayout mLayout;
	private LinearLayout mGallery;
	private ObjView view1, view2;
	private String soundPath = null;
	private GalleryObjectAdapter goa;
	private boolean samePair = true;
	public static ArrayList<MemoryObj> listNewObjs = new ArrayList<MemoryObj>();
	private final static int RESULT_LOAD_IMAGE_VIEW1 = 1;
	private final static int RESULT_LOAD_IMAGE_VIEW2 = 2;
	private final static int RESULT_LOAD_CAMERA_VIEW1 = 3;
	private final static int RESULT_LOAD_CAMERA_VIEW2 = 4;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.settings_layout);

		mGallery = (LinearLayout) findViewById(R.id.newItems);
		autoText1 = (EditText) findViewById(R.id.autoText1);
		autoText2 = (EditText) findViewById(R.id.autoText2);
		mLayout = (FrameLayout) findViewById(R.id.left2);
		mLayout.setVisibility(View.INVISIBLE);
		Toast.makeText(getApplicationContext(), R.string.pairEquals,
				Toast.LENGTH_LONG).show();
		addItem = (ImageButton) findViewById(R.id.addItems);
		addPictureLeft = (ImageButton) findViewById(R.id.addPictureLeft);
		addPictureRight = (ImageButton) findViewById(R.id.addPictureRight);
		pairEquals = (ImageButton) findViewById(R.id.pairEquals);
		objLeft = (ObjView) findViewById(R.id.obj_view_1);
		objRight = (ObjView) findViewById(R.id.obj_view_2);
		mGallery.removeAllViews();
		listNewObjs.clear();
		goa = new GalleryObjectAdapter(SettingsActivity.this);
		for (int i = 0; i < goa.getCount(); i++) {
			mGallery.addView(goa.getView(i, null, mGallery));

		}
		pairEquals.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				mLayout.setVisibility(View.INVISIBLE);
				samePair = true;
				Toast.makeText(getApplicationContext(), R.string.pairEquals,
						Toast.LENGTH_LONG).show();
			}

		});
		pairNonEquals = (ImageButton) findViewById(R.id.pairNonEquals);
		pairNonEquals.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				mLayout.setVisibility(View.VISIBLE);
				samePair = false;
				Toast.makeText(getApplicationContext(), R.string.pairNonEquals,
						Toast.LENGTH_LONG).show();

			}

		});

		playGame = (ImageButton) findViewById(R.id.playGame);
		playGame.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				Intent intent = new Intent(v.getContext(), Memorize.class);
				startActivity(intent);
				finish();

			}

		});
		editClear = (ImageButton) findViewById(R.id.editClear);
		editClear.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				mGallery.removeAllViews();
				listNewObjs.clear();

			}

		});

		addItem.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// add pairs to the gallery

				Log.d(TAG, "adding new pair in gallery");
				if (!samePair) {
					Log.d(TAG, "pair are non equals");
					view1.getObj().setPairedObj(view2.getObj());
					listNewObjs.add(view1.getObj());
					// listNewObjs.add(view2.getObj());
				}else{
					Log.d(TAG, "pair are equals");
					view1.getObj().setPairedObj(view1.getObj());
					listNewObjs.add(view1.getObj());
					// listNewObjs.add(view1.getObj());
				}
					mGallery.addView(goa.getView(goa.getCount()-1, null, mGallery));

				for(int i=0; i<listNewObjs.size();i++){
					Log.d(TAG, "list : "+listNewObjs.get(i));
					Log.d(TAG, "count in goa : "+goa.getCount());
				}
			}
		});
		
		// remove focus at startup
		this.getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

		view1 = (ObjView) findViewById(R.id.obj_view_1);
		MemoryObj obj1 = new MStr(autoText1.getText().toString());
		obj1.show();
		view1.setObj(obj1);
		view2 = (ObjView) findViewById(R.id.obj_view_2);
		MemoryObj obj2 = new MStr(autoText2.getText().toString());
		obj2.show();
		view2.setObj(obj2);
		objLeft.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				AlertDialog.Builder builder = new AlertDialog.Builder(
						SettingsActivity.this);
				builder.setTitle(R.string.addPicture);
				builder.setIcon(R.drawable.import_picture);
				builder.setItems(R.array.addPictureArray,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int item) {
								// Do something with the selection
								if (item == 0) {
									Log.d(TAG, "select picture from gallery");
									Intent galleryIntent = new Intent(
											Intent.ACTION_PICK,
											android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

									startActivityForResult(galleryIntent,
											RESULT_LOAD_IMAGE_VIEW1);
								}
								if (item == 1) {
									Log.d(TAG, "select picture from the camera");
									Intent cameraIntent = new Intent(
											android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
									startActivityForResult(cameraIntent,
											RESULT_LOAD_CAMERA_VIEW1);
								}
							}
						});
				AlertDialog alert = builder.create();
				alert.show();

			}
		});
		objRight.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				AlertDialog.Builder builder = new AlertDialog.Builder(
						SettingsActivity.this);
				builder.setTitle(R.string.addPicture);
				builder.setIcon(R.drawable.import_picture);
				builder.setItems(R.array.addPictureArray,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int item) {
								// Do something with the selection
								if (item == 0) {
									Log.d(TAG, "select picture from gallery");
									Intent galleryIntent = new Intent(
											Intent.ACTION_PICK,
											android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

									startActivityForResult(galleryIntent,
											RESULT_LOAD_IMAGE_VIEW2);
								}
								if (item == 1) {
									Log.d(TAG, "select picture from the camera");
									Intent cameraIntent = new Intent(
											android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
									startActivityForResult(cameraIntent,
											RESULT_LOAD_CAMERA_VIEW2);
								}
							}
						});
				AlertDialog alert = builder.create();
				alert.show();

			}
		});
		autoText1.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				ObjView view1 = (ObjView) findViewById(R.id.obj_view_1);
				MemoryObj obj1 = new MStr(autoText1.getText().toString());
				obj1.show();
				view1.setObj(obj1);
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {

			}

			@Override
			public void afterTextChanged(Editable s) {

			}
		});
		autoText2.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				ObjView view2 = (ObjView) findViewById(R.id.obj_view_2);
				MemoryObj obj2 = new MStr(autoText2.getText().toString());
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

		
		addPictureLeft.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				AlertDialog.Builder builder = new AlertDialog.Builder(
						SettingsActivity.this);
				builder.setTitle(R.string.addPicture);
				builder.setIcon(R.drawable.import_picture);
				builder.setItems(R.array.addPictureArray,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int item) {
								// Do something with the selection
								if (item == 0) {
									Log.d(TAG, "select picture from gallery");
									Intent galleryIntent = new Intent(
											Intent.ACTION_PICK,
											android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

									startActivityForResult(galleryIntent,
											RESULT_LOAD_IMAGE_VIEW1);
								}
								if (item == 1) {
									Log.d(TAG, "select picture from the camera");
									Intent cameraIntent = new Intent(
											android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
									startActivityForResult(cameraIntent,
											RESULT_LOAD_CAMERA_VIEW1);
								}
							}
						});
				AlertDialog alert = builder.create();
				alert.show();

			}
		});
		addPictureRight.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				AlertDialog.Builder builder = new AlertDialog.Builder(
						SettingsActivity.this);
				builder.setTitle(R.string.addPicture);
				builder.setIcon(R.drawable.import_picture);
				builder.setItems(R.array.addPictureArray,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int item) {
								// Do something with the selection
								if (item == 0) {
									Log.d(TAG, "select picture from gallery");
									Intent galleryIntent = new Intent(
											Intent.ACTION_PICK,
											android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

									startActivityForResult(galleryIntent,
											RESULT_LOAD_IMAGE_VIEW2);
								}
								if (item == 1) {
									Log.d(TAG, "select picture from the camera");
									Intent cameraIntent = new Intent(
											android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
									startActivityForResult(cameraIntent,
											RESULT_LOAD_CAMERA_VIEW2);
								}
							}
						});
				AlertDialog alert = builder.create();
				alert.show();

			}
		});

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
		case RESULT_LOAD_IMAGE_VIEW1:
			if (resultCode == RESULT_OK && null != data) {
				Uri selectedImage = data.getData();
				String[] filePathColumn = { MediaStore.Images.Media.DATA };

				Cursor cursor = getContentResolver().query(selectedImage,
						filePathColumn, null, null, null);
				cursor.moveToFirst();

				int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
				String picturePath = cursor.getString(columnIndex);
				cursor.close();

				MemoryObj objPicture = new MMedia(picturePath, soundPath);
				objPicture.show();
				view1.setObj(objPicture);
				view1.invalidate();
			}
			break;
		case RESULT_LOAD_IMAGE_VIEW2:
			if (resultCode == RESULT_OK && null != data) {
				Uri selectedImage = data.getData();
				String[] filePathColumn = { MediaStore.Images.Media.DATA };

				Cursor cursor = getContentResolver().query(selectedImage,
						filePathColumn, null, null, null);
				cursor.moveToFirst();

				int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
				String picturePath = cursor.getString(columnIndex);
				cursor.close();

				MemoryObj objPicture = new MMedia(picturePath, soundPath);
				objPicture.show();
				view2.setObj(objPicture);
				view2.invalidate();
			}
			break;
		case RESULT_LOAD_CAMERA_VIEW1:
			if (resultCode == RESULT_OK && null != data) {

				Bitmap photo = (Bitmap) data.getExtras().get("data");

				MemoryObj objPicture = new MBitmap(photo, soundPath);
				objPicture.show();
				view1.setObj(objPicture);
				view1.invalidate();
			}
			break;
		case RESULT_LOAD_CAMERA_VIEW2:
			if (resultCode == RESULT_OK && null != data) {

				Bitmap photo = (Bitmap) data.getExtras().get("data");

				MemoryObj objPicture = new MBitmap(photo, soundPath);
				objPicture.show();
				view2.setObj(objPicture);
				view2.invalidate();
			}
			break;
		}

	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		Intent intent = new Intent(this, Memorize.class);
		startActivity(intent);
		finish();
	}

}

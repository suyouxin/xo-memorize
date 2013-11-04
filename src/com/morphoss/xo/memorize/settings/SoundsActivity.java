package com.morphoss.xo.memorize.settings;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.morphoss.xo.memorize.ObjView;
import com.morphoss.xo.memorize.R;
import com.morphoss.xo.memorize.obj.MBitmap;
import com.morphoss.xo.memorize.obj.MMedia;
import com.morphoss.xo.memorize.obj.MStr;
import com.morphoss.xo.memorize.obj.MemoryObj;

public class SoundsActivity extends Activity {

	private static final String TAG = "SoundsActivity";

	private ObjView view1, view2;
	private TextView numberPairs;
	private ImageButton addItem, saveGame, clearGame, addPicture, addSound;
	private GalleryObjectAdapter goa;
	private LinearLayout mGallery;
	private Context context = this;
	private String soundPath = null;
	private String picturePath = null;
	private String curFileName;
	private static ArrayList<MemoryObj> listNewObjsSounds = new ArrayList<MemoryObj>();
	private final static int RESULT_LOAD_IMAGE_VIEW = 1;
	private final static int RESULT_LOAD_CAMERA_VIEW = 2;
	private final static int RESULT_LOAD_MUSIC_SDCARD = 3;
	private final static int RESULT_LOAD_MUSIC_SYSTEM = 4;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sounds_layout);

		numberPairs = (TextView) findViewById(R.id.numberPairsCreated);
		numberPairs.setText("0");
		view1 = (ObjView) findViewById(R.id.obj_view_pair1);
		view2 = (ObjView) findViewById(R.id.obj_view_pair2);
		addItem = (ImageButton) findViewById(R.id.addItems);
		addPicture = (ImageButton) findViewById(R.id.addPicture);
		addSound = (ImageButton) findViewById(R.id.addSound);
		saveGame = (ImageButton) findViewById(R.id.saveGame);
		clearGame = (ImageButton) findViewById(R.id.clearGame);
		mGallery = (LinearLayout) findViewById(R.id.newItems);

		mGallery.removeAllViews();
		listNewObjsSounds.clear();
		goa = new GalleryObjectAdapter(SoundsActivity.this, listNewObjsSounds);
		for (int i = 0; i < goa.getCount(); i++) {
			mGallery.addView(goa.getView(i, null, mGallery));

		}
		view1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				importPictures();

			}
		});
		view2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				importPictures();

			}
		});
		clearGame.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				listNewObjsSounds.clear();
				mGallery.removeAllViews();
				numberPairs.setText("0");

			}
		});
		saveGame.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (listNewObjsSounds.size() <= 4) {
					// not a usable game, display an alert dialog
					AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
							context);
					// set dialog message
					alertDialogBuilder
							.setMessage(R.string.needMorePairs)
							.setCancelable(false)
							.setPositiveButton(R.string.gotIt,
									new DialogInterface.OnClickListener() {
										public void onClick(
												DialogInterface dialog, int id) {
											// if this button is clicked, close
											// dialog
											dialog.dismiss();

										}
									});

					// create alert dialog
					AlertDialog alertDialog = alertDialogBuilder.create();

					// show it
					alertDialog.show();
				} else {
					AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
							context);
					// set dialog message
					alertDialogBuilder
							.setTitle(R.string.titleSave)
							.setMessage(R.string.areYouSure)
							.setCancelable(false)
							.setPositiveButton(R.string.yes,
									new DialogInterface.OnClickListener() {
										public void onClick(
												DialogInterface dialog, int id) {
											// if this button is clicked, save the game
											savingGame();
										}
									})
							.setNegativeButton(R.string.no,
									new DialogInterface.OnClickListener() {
										public void onClick(
												DialogInterface dialog, int id) {
											// if this button is clicked, close
											// dialog
											dialog.dismiss();

										}
									});

					// create alert dialog
					AlertDialog alertDialog = alertDialogBuilder.create();

					// show it
					alertDialog.show();

				}

			}
		});

		addItem.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				if (picturePath != null && soundPath != null) {
					view1.getObj().setPairedObj(view2.getObj());
					listNewObjsSounds.add(view1.getObj());
					mGallery.addView(goa.getView(goa.getCount() - 1, null,
							mGallery));

				}
				numberPairs.setText(String.valueOf(goa.getCount()));
				for (int i = 0; i < listNewObjsSounds.size(); i++) {
					Log.d(TAG, "list : " + listNewObjsSounds.get(i));
					Log.d(TAG, "count in goa : " + goa.getCount());
				}

			}
		});
		addPicture.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				importPictures();
			}
		});
		addSound.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				importSounds();

			}
		});
		// remove auto focus from edit text
		this.getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

		MemoryObj obj1 = new MStr("");
		obj1.show();
		view1.setObj(obj1);

		MemoryObj obj2 = new MStr("");
		obj2.show();
		view2.setObj(obj2);

		view1.getObj().setPairedObj(obj2);
		view2.getObj().setPairedObj(obj1);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
		case RESULT_LOAD_IMAGE_VIEW:
			if (resultCode == RESULT_OK && null != data) {
				Uri selectedImage = data.getData();
				String[] filePathColumn = { MediaStore.Images.Media.DATA };

				Cursor cursor = getContentResolver().query(selectedImage,
						filePathColumn, null, null, null);
				cursor.moveToFirst();

				int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
				picturePath = cursor.getString(columnIndex);
				cursor.close();

				MemoryObj objPicture = new MMedia(picturePath, soundPath);
				objPicture.show();
				view1.setObj(objPicture);
				view1.invalidate();
				view2.setObj(objPicture);
				view2.invalidate();
			}
			break;

		case RESULT_LOAD_CAMERA_VIEW:
			if (resultCode == RESULT_OK && null != data) {

				Bitmap photo = (Bitmap) data.getExtras().get("data");

				MemoryObj objPicture = new MBitmap(photo, soundPath);
				objPicture.show();
				view1.setObj(objPicture);
				view1.invalidate();
				view2.setObj(objPicture);
				view2.invalidate();
			}
			break;
		case RESULT_LOAD_MUSIC_SDCARD:
			if (resultCode == RESULT_OK && null != data) {
				if(data.hasExtra("returnKey1")){
					soundPath = data.getExtras().getString("returnKey1");
					Log.d(TAG, "sound path : " + soundPath);
					 MemoryObj objPicture = new MMedia(picturePath, soundPath);
					 objPicture.show(); view1.setObj(objPicture);
					 view1.invalidate(); view2.setObj(objPicture);
					 view2.invalidate();
				}

			}
			break;
		case RESULT_LOAD_MUSIC_SYSTEM:
			Uri selectedSound = data.getData();
			soundPath = selectedSound.getPath();
			MemoryObj objPicture = new MMedia(picturePath, soundPath);
			objPicture.show();
			view1.setObj(objPicture);
			view1.invalidate();
			view2.setObj(objPicture);
			view2.invalidate();
			break;
		}

	}

	@Override
	public void onBackPressed() {

		super.onBackPressed();
		Intent intent = new Intent(context, SettingsActivity.class);
		startActivity(intent);
		finish();
	}

	private void importPictures() {
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
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
									RESULT_LOAD_IMAGE_VIEW);
						}
						if (item == 1) {
							Log.d(TAG, "select picture from the camera");
							Intent cameraIntent = new Intent(
									android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
							startActivityForResult(cameraIntent,
									RESULT_LOAD_CAMERA_VIEW);
						}
					}
				});
		AlertDialog alert = builder.create();
		alert.show();
	}

	private void importSounds() {
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setTitle(R.string.addSound);
		builder.setIcon(R.drawable.import_sound);
		builder.setItems(R.array.addSoundArray,
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int item) {
						// Do something with the selection
						if (item == 0) {
							// import mp3 from sdcard
							Intent i = new Intent(context, FileChooser.class);
							i.setAction(android.content.Intent.ACTION_PICK);
					        Uri data = Uri.parse("file:///sdcard/Music/");
					        String type = "audio/mp3";
					        i.setDataAndType(data, type);
							startActivityForResult(i, RESULT_LOAD_MUSIC_SDCARD);
						}
						if (item == 1) {
							// import ogg from system/media/audio
						}
					}
				});
		AlertDialog alert = builder.create();
		alert.show();
	}

	private void savingGame() {
		String nameGameKey = "com.morphoss.xo.memorize.settings";
		String nameGame = SettingsActivity.prefs.getString(nameGameKey,
				new String());
		Log.d(TAG, "the name of the game is: " + nameGame);
		// Create folder
		File folder = new File(Environment.getExternalStorageDirectory()
				.toString()
				+ "/Android/data/com.morphoss.xo.memorize/files/games/"
				+ nameGame);
		folder.mkdirs();
		// Save the path as a string value
		String extStorageDirectory = folder.toString();
		try {
			BufferedWriter buf = new BufferedWriter(new FileWriter(
					extStorageDirectory + "/game.xml"));
			StringBuilder strBuilder = new StringBuilder(
					"<?xml version=\"1.0\"?>\n");
			strBuilder.append("\n");
			strBuilder
					.append("<memorize name=\""
							+ nameGame
							+ "\" scoresnd=\"score.wav\" winsnd=\"win.wav\" divided=\"0\" >\n");
			for (MemoryObj obj : listNewObjsSounds) {

				strBuilder.append("<pair aimg=\"" + ((MMedia) obj).getImagePath()
						+ "\" asnd=\"" + ((MMedia) obj).getSoundPath()
						+ "\" bimg=\"" + ((MMedia) obj.getPairedObj()).getImagePath()
						+ "\" bsnd=\"" + ((MMedia) obj.getPairedObj()).getSoundPath()
						+ "\"/>\n");
			}
			strBuilder.append("\n");
			strBuilder.append("</memorize>");
			buf.write(strBuilder.toString());
			buf.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
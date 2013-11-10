package com.morphoss.xo.memorize.settings;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Random;

import com.morphoss.xo.memorize.Memorize;
import com.morphoss.xo.memorize.ObjView;
import com.morphoss.xo.memorize.R;
import com.morphoss.xo.memorize.obj.MMedia;
import com.morphoss.xo.memorize.obj.MStr;
import com.morphoss.xo.memorize.obj.MemoryObj;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SoundsModifyActivity extends Activity{

	private static final String TAG = "SoundsModifyActivity";

	private ObjView view1, view2;
	private TextView numberPairs;
	private ImageButton addItem, saveGame, clearGame, addPicture, addSound;
	private GalleryObjectAdapter goa;
	private LinearLayout mGallery;
	private Context context = this;
	private String soundPath = null;
	private String picturePath = null;
	private String soundName = null;
	private String pictureName = null;
	private String name, type;
	private static ArrayList<MemoryObj> listNewObjsSounds = new ArrayList<MemoryObj>();
	private final static int RESULT_LOAD_IMAGE_VIEW = 1;
	private final static int RESULT_LOAD_CAMERA_VIEW = 2;
	private final static int RESULT_LOAD_MUSIC = 3;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sounds_layout);

		numberPairs = (TextView) findViewById(R.id.numberPairsCreated);
		numberPairs.setText("0");
		Bundle extras = getIntent().getExtras();
        name = extras.getString("name");
        type = extras.getString("type");
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
		goa = new GalleryObjectAdapter(SoundsModifyActivity.this, listNewObjsSounds, 4);
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
				if (listNewObjsSounds.size() <= 9) {
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
											// if this button is clicked, save
											// the game
											savingGame();
											Intent intent = new Intent(context,
													Memorize.class);
											startActivity(intent);
											finish();
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

				if (picturePath != null && soundPath != null
						&& pictureName != null && soundName != null) {
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

				/*Uri selectedImage = data.getData();
				String[] filePathColumn = { MediaStore.Images.Media.DATA };

				Cursor cursor = getContentResolver().query(selectedImage,
						filePathColumn, null, null, null);
				cursor.moveToFirst();

				int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
				picturePath = cursor.getString(columnIndex);
				cursor.close();*/
				//get the file name and file path from the file chooser class
				picturePath = data.getExtras().getString("returnKey1");
				pictureName = data.getExtras().getString("returnKey2");
				Log.d(TAG, "picture path : " + picturePath);
				Log.d(TAG, "picture name : " + pictureName);
				
				//save the file on the sdcard
				String nameGameKey = "com.morphoss.xo.memorize.settings";
				String nameGame = SettingsActivity.prefs.getString(nameGameKey,
						new String());
				String pathDir = Environment.getExternalStorageDirectory()
						+ "/Android/data/com.morphoss.xo.memorize/files/games/"
						+ nameGame + "/images";
				File myDir = new File(pathDir);
				myDir.mkdirs();
				File file = new File(myDir, pictureName);
				if (file.exists())
					file.delete();
				try{
					File src = new File(picturePath);
					File dst = new File(pathDir+"/"+pictureName);
					InputStream in = new FileInputStream(src);
					OutputStream out = new FileOutputStream(dst);
					byte[] buf = new byte[1024];
					int len;
					while((len = in.read(buf)) > 0){
						out.write(buf, 0, len);
					}
					in.close();
					out.close();
				}catch(Exception e){
					e.printStackTrace();
				}
				MemoryObj objPicture = new MMedia(picturePath, soundPath,
						pictureName, soundName);
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

				// save the bitmap on the sd card
				String nameGameKey = "com.morphoss.xo.memorize.settings";
				String nameGame = SettingsActivity.prefs.getString(nameGameKey,
						new String());

				File myDir = new File(Environment.getExternalStorageDirectory()
						+ "/Android/data/com.morphoss.xo.memorize/files/games/"
						+ nameGame + "/images");
				myDir.mkdirs();
				Random generator = new Random();
				int n = 10000;
				n = generator.nextInt(n);
				pictureName = "image_" + n + ".jpg";
				picturePath = Environment.getExternalStorageDirectory()
						+ "/Android/data/com.morphoss.xo.memorize/files/games/"
						+ nameGame + "/images/" + pictureName;
				File file = new File(myDir, pictureName);
				if (file.exists())
					file.delete();
				try {
					FileOutputStream out = new FileOutputStream(file);
					photo.compress(Bitmap.CompressFormat.JPEG, 90, out);
					out.flush();
					out.close();

				} catch (Exception e) {
					e.printStackTrace();
				}
				Log.d(TAG, "picture path : " + picturePath);
				Log.d(TAG, "picture name : " + pictureName);
				MemoryObj objPicture = new MMedia(picturePath, soundPath,
						pictureName, soundName);
				objPicture.show();
				view1.setObj(objPicture);
				view1.invalidate();
				view2.setObj(objPicture);
				view2.invalidate();
			}
			break;
		case RESULT_LOAD_MUSIC:
			if (resultCode == RESULT_OK && null != data) {
				if (data.hasExtra("returnKey1") && data.hasExtra("returnKey2")) {
					//get the file name and file path from the file chooser class
					soundPath = data.getExtras().getString("returnKey1");
					soundName = data.getExtras().getString("returnKey2");
					Log.d(TAG, "sound path : " + soundPath);
					Log.d(TAG, "sound name : " + soundName);
					
					//save the file on the sdcard
					String nameGameKey = "com.morphoss.xo.memorize.settings";
					String nameGame = SettingsActivity.prefs.getString(nameGameKey,
							new String());
					String pathDir = Environment.getExternalStorageDirectory()
							+ "/Android/data/com.morphoss.xo.memorize/files/games/"
							+ nameGame + "/sounds";
					File myDir = new File(pathDir);
					myDir.mkdirs();
					File file = new File(myDir, soundName);
					if (file.exists())
						file.delete();
					try{
						File src = new File(soundPath);
						File dst = new File(pathDir+"/"+soundName);
						InputStream in = new FileInputStream(src);
						OutputStream out = new FileOutputStream(dst);
						byte[] buf = new byte[1024];
						int len;
						while((len = in.read(buf)) > 0){
							out.write(buf, 0, len);
						}
						in.close();
						out.close();
					}catch(Exception e){
						e.printStackTrace();
					}

					//create the object
					MemoryObj objSound = new MMedia(picturePath, soundPath,
							pictureName, soundName);
					objSound.show();
					view1.setObj(objSound);
					view1.invalidate();
					view2.setObj(objSound);
					view2.invalidate();
				}

			}
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

							/*Intent galleryIntent = new Intent(
									Intent.ACTION_PICK,
									android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);*/
							Intent i = new Intent(context, FileChooser.class);
							i.putExtra("pathDirectory", Environment
									.getExternalStorageDirectory().getPath()
									+ "/Pictures/");
							startActivityForResult(i,
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
							i.putExtra("pathDirectory", Environment
									.getExternalStorageDirectory().getPath()
									+ "/Music/");
							startActivityForResult(i, RESULT_LOAD_MUSIC);
						}
						if (item == 1) {
							// import ogg from system/media/audio
							Intent i = new Intent(context, FileChooser.class);
							i.putExtra("pathDirectory", "/system/media/audio/");
							startActivityForResult(i, RESULT_LOAD_MUSIC);
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
							+ "\" type=\"2\"  scoresnd=\"score.wav\" winsnd=\"win.wav\" divided=\"0\" >\n");
			for (MemoryObj obj : listNewObjsSounds) {

				strBuilder.append("<pair aimg=\""
						+ ((MMedia) obj).getImageName() + "\" asnd=\""
						+ ((MMedia) obj).getSoundName() + "\" bimg=\""
						+ ((MMedia) obj.getPairedObj()).getImageName()
						+ "\" bsnd=\""
						+ ((MMedia) obj.getPairedObj()).getSoundName()
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
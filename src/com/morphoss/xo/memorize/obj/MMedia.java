package com.morphoss.xo.memorize.obj;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.media.MediaPlayer;
import android.os.Handler;
import android.util.Log;

import com.morphoss.xo.memorize.GameEngine;
import com.morphoss.xo.memorize.ObjView;
import com.morphoss.xo.memorize.Util;

public class MMedia extends Paired {
	private String mUid;
	private String mImgPath;
	private String mSndPath;
	private String nameImg;
	private String nameSnd;
	private Handler h = new Handler();

	public MMedia(String imgPath, String sndPath) {
		mImgPath = imgPath;
		mSndPath = sndPath;

		StringBuilder strbuilder = new StringBuilder();
		if (imgPath != null)
			strbuilder.append(imgPath);
		if (sndPath != null)
			strbuilder.append(sndPath);

		try {
			mUid = Util.makeSHA1Hash(strbuilder.toString());
		} catch (NoSuchAlgorithmException e) {
			mUid = strbuilder.toString();
		}
	}

	public MMedia(String imgPath, String sndPath, String namePict,
			String nameMus) {
		mImgPath = imgPath;
		mSndPath = sndPath;
		nameImg = namePict;
		nameSnd = nameMus;

		StringBuilder strbuilder = new StringBuilder();
		if (imgPath != null)
			strbuilder.append(imgPath);
		if (sndPath != null)
			strbuilder.append(sndPath);

		try {
			mUid = Util.makeSHA1Hash(strbuilder.toString());
		} catch (NoSuchAlgorithmException e) {
			mUid = strbuilder.toString();
		}
	}

	@Override
	public String getUid() {
		return mUid;
	}

	public String getSoundPath() {
		return mSndPath;
	}

	public String getImagePath() {
		return mImgPath;
	}

	public void setImageName(String name) {
		nameImg = name;
	}

	public String getImageName() {
		return nameImg;
	}

	public void setSoundName(String name) {
		nameSnd = name;
	}

	public String getSoundName() {
		return nameSnd;
	}

	@Override
	public MemoryObj copyMe() {
		MMedia me = new MMedia(mImgPath, mSndPath);
		return me;
	}

	@Override
	public void draw(Canvas canvas) {
		if (mImgPath != null) {
			Bitmap bmp = BitmapFactory.decodeFile(mImgPath);
			int reduce = ObjView.BORDER_SIZE_DP;

			Rect rc = new Rect(reduce, reduce, canvas.getWidth() - reduce,
					canvas.getHeight() - reduce);
			Paint p = new Paint();
			canvas.drawBitmap(bmp, null, rc, p);
		}
	}

	private void play() {
		GameEngine ge = GameEngine.getInstance();
		MediaPlayer mp = ge.getMediaPlayer();
		play(mp);
	}

	public void stop() {
		GameEngine ge = GameEngine.getInstance();
		MediaPlayer mp = ge.getMediaPlayer();
		if (mp.isPlaying()) {
			mp.stop();
		}
	}

	private void play(final MediaPlayer player) {
		if (mSndPath != null) {
			if (player.isPlaying()) {
				player.stop();
			}
			player.reset();
			try {
				player.setDataSource(mSndPath);
				player.prepare();

				player.start();
				Log.d("MMedia", "playing: " + mSndPath);
				h.postDelayed(new Runnable() {
					public void run() {
						player.stop();
					}
				}, 5000);

			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (IllegalStateException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void show() {
		super.show();
		play();
	}
	public void showView() {
		super.show();
	}

	@Override
	public void hide() {
		super.hide();
		stop();
	}

	@Override
	public void match() {
		super.match();
		play();
	}
}
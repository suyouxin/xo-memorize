package com.morphoss.xo.memorize;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

public class WinningActivity extends Activity {
	private Button btnPlay, btnQuit;
	private Context context = this;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.winning_layout);
		startAnimation(R.id.background);
		btnPlay = (Button) findViewById(R.id.playAgainBtn);
		btnQuit = (Button) findViewById(R.id.quitBtn);
		btnPlay.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(context, Memorize.class);
				startActivity(intent);
				finish();
				
			}
		});
		btnQuit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
			finish();
				
			}
		});
	}
	private void startAnimation(int imageId) {
		ImageView bgImage = (ImageView) findViewById(imageId);
		AnimationDrawable bgAnimation = (AnimationDrawable) bgImage
				.getDrawable();
		bgAnimation.start();
	}
	@Override
	public void onBackPressed() {
		super.onBackPressed();
		Intent intent = new Intent(context, Memorize.class);
		startActivity(intent);
		finish();
	}
}

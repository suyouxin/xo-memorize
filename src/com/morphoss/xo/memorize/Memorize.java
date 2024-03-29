package com.morphoss.xo.memorize;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.morphoss.xo.memorize.res.GameInfo;
import com.morphoss.xo.memorize.res.GameResource;
import com.morphoss.xo.memorize.settings.SettingsActivity;
import com.morphoss.xo.memorize.settings.WebViewActivity;

public class Memorize extends Activity implements GameEngine.GameMonitor {
	
	private Button settings, mHelp;
	private Context context = this;
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		GameEngine ge = GameEngine.getInstance();
		ge.setGameMonitor(this);
		GameCanvas gc = (GameCanvas) findViewById(R.id.game_canvas);
		settings = (Button) findViewById(R.id.settingsActivity);

		/*
		 * ArrayList<MemoryObj> objs = new ArrayList<MemoryObj>();
		 * 
		 * for (int i = 0; i < 50; i++) objs.add(new MStr(Integer.toString(i)));
		 */
		GameResource gs = new GameResource(this);
		gs.initAssetGame();
		GameInfo game = gs.getAllGames().get(0);
		gc.setGameInfo(gs.getAllGames());

		ge.start(game, 4, 5, this, gc);
		settings.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(context, SettingsActivity.class);
				startActivity(intent);
				finish();
			}
		});
		mHelp = (Button) findViewById(R.id.helpActivity);
		mHelp.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(context, WebViewActivity.class);
				startActivity(intent);
			}
		});
	}
	@Override
	public void notificationGameOver() {
		Intent intent = new Intent(context, WinningActivity.class);
		startActivity(intent);
		finish();
		Log.d("..............", "game solved");
	}
}

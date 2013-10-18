package com.morphoss.xo.memorize;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

import com.morphoss.xo.memorize.res.GameInfo;
import com.morphoss.xo.memorize.res.GameResource;

public class Memorize extends Activity {
	
	private ImageButton settings;
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		GameEngine ge = GameEngine.getInstance();
		GameCanvas gc = (GameCanvas) findViewById(R.id.game_canvas);
		settings = (ImageButton) findViewById(R.id.settingsActivity);
		settings.setOnClickListener(new OnClickListener() {
			 
			@Override
			public void onClick(View v) {
 
				Intent intent = new Intent(v.getContext(), SettingsActivity.class);
        		startActivity(intent);
 
			}
 
		});

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
	}
}

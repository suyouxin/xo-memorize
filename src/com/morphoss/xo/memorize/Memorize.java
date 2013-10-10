package com.morphoss.xo.memorize;

import com.morphoss.xo.memorize.res.GameInfo;
import com.morphoss.xo.memorize.res.GameResource;

import android.app.Activity;
import android.os.Bundle;

public class Memorize extends Activity
{
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        GameEngine ge = GameEngine.getInstance();
        GameCanvas gc = (GameCanvas)findViewById(R.id.game_canvas);
        
        /*
        ArrayList<MemoryObj> objs = new ArrayList<MemoryObj>();
        
        for (int i = 0; i < 50; i++)
            objs.add(new MStr(Integer.toString(i)));
        */
        GameResource gs = new GameResource(this);
        gs.initAssetGame();
        GameInfo game = gs.getAllGames().get(0);
        gc.setGameInfo(gs.getAllGames());

        ge.start(game, 4, 5, this, gc);
    }
}

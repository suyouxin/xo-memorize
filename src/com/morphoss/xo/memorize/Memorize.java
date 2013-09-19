package com.morphoss.xo.memorize;

import java.util.ArrayList;

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
        
        ArrayList<MemoryObj> objs = new ArrayList<MemoryObj>();
        
        for (int i = 0; i < 50; i++)
            objs.add(new MStr(Integer.toString(i)));

        MemoryObjAdapter adp = new MemoryObjAdapter(this, ge.restart(objs, 4, 5, false));
        gc.setAdapter(adp);
        ge.setAdapter(adp);
    }
}

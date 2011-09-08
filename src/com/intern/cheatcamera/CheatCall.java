package com.intern.cheatcamera;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
//
public class CheatCall extends Activity {
	
	private MediaPlayer mp;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cheatcall);
        Log.d("onCreate","onCreate");
        mp = MediaPlayer.create(this,R.raw.call);
//        mp.setLooping(true);
        mp.start();
    }

}

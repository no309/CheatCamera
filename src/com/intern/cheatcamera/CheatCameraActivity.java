package com.intern.cheatcamera;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * @author furyu
 *
 */
public class CheatCameraActivity extends Activity {
    /** Called when the activity is first created. */
	// commit test by ¼’J@Nq
	// commit test by mtbr
	private Button startBtn;// ‹U’…M‰æ–Ê‚Ö
	private Button settingBtn;// İ’è‰æ–Ê‚Ö
	private Button playBtn;// —V‚Ñ•û‰æ–Ê‚Ö
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        startBtn = (Button)findViewById(R.id.button1);
        settingBtn = (Button)findViewById(R.id.button2);
        playBtn = (Button)findViewById(R.id.button3);
    }
    // İ’è‰æ–Ê‚Ö
    public void onClickSetting(View v){
    	 //Ÿ‚Ì‰æ–Ê‚É‘JˆÚ‚³‚¹‚é
        Intent intent = new Intent();
        intent.setClassName(
                "com.intern.cheatcamera",
                "com.intern.cheatcamera.MenuList");
        startActivity(intent);
    }
    
}
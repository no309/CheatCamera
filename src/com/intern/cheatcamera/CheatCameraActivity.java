package com.intern.cheatcamera;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

/**
 * @author furyu
 *
 */
public class CheatCameraActivity extends Activity {
    /** Called when the activity is first created. */
	// commit test by 松谷　康子
	// commit test by mtbr
	private Button startBtn;// 偽着信画面へ
	private Button settingBtn;// 設定画面へ
	private Button playBtn;// 遊び方画面へ
	
	float    mLaptime = 0.0f;
	Timer   mTimer   = null;
	Handler mHandler = new Handler();
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        startBtn = (Button)findViewById(R.id.button1);
        settingBtn = (Button)findViewById(R.id.button2);
        playBtn = (Button)findViewById(R.id.button3);

        
    }

	
	@Override
	protected void onResume() {
		super.onResume();
		// layotをmainに変更する
		super.setContentView(R.layout.main);
	}

	
    // 設定画面へ
    public void onClickSetting(View v){
    	 //次の画面に遷移させる
        Intent intent = new Intent();
        intent.setClassName(
                "com.intern.cheatcamera",
                "com.intern.cheatcamera.MenuList");
        startActivity(intent);
    }
    // 偽画面へ
    public void onClickCheat(View v){        
        //タイマーの初期化処理
        mLaptime = 0.0f;
        mTimer = new Timer(true);
        
        // ブラックスクリーンを表示
        View view = this.getLayoutInflater().inflate(R.layout.bluck, null);
        addContentView(view,new LinearLayout.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT));
        
//      // データの呼び出し
      SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
      // key="time" 初期値　0
      String timeStr = sharedPreferences.getString("time", "0");
      
      // String→int
      int time = Integer.parseInt(timeStr);
      // time秒をミリ秒にする
      time = time * 1000;
      
        // タイマー設定　○秒後に画面遷移する
        mTimer.schedule(new TimerTask() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				Log.d("timerTask","test");

		    	 //次の画面に遷移させる
		        Intent intent = new Intent();
		        intent.setClassName(
		                "com.intern.cheatcamera",
		                "com.intern.cheatcamera.CheatCall");
		        startActivity(intent);
		       
			}
		}, time);
    }
    
}
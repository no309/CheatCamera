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
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * @author furyu
 *
 */
public class CheatCameraActivity extends Activity {
    /** Called when the activity is first created. */
	// commit test by 松谷　康子
	// commit test by mtbr
	private ImageButton startBtn;// 偽着信画面へ
	private ImageButton settingBtn;// 設定画面へ
	private ImageButton playBtn;// 遊び方画面へ
	
	float    mLaptime = 0.0f;
	Timer   mTimer   = null;
	Handler mHandler = new Handler();
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.main);
        startBtn = (ImageButton)findViewById(R.id.start_button);
        settingBtn = (ImageButton)findViewById(R.id.setting_button);
        playBtn = (ImageButton)findViewById(R.id.play_button);

        
    }

	
	@Override
	protected void onResume() {
		super.onResume();
		// layoutをmainに変更する
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
    public void onClickHowTo(View v){
   	 //次の画面に遷移させる
       Intent intent = new Intent();
       intent.setClassName(
               "com.intern.cheatcamera",
               "com.intern.cheatcamera.HowTo");
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
      
      TextView tv = new TextView(this);
      tv.setText("person");
      
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
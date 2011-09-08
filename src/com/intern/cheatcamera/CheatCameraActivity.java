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

/**
 * @author furyu
 *
 */
public class CheatCameraActivity extends Activity {
    /** Called when the activity is first created. */
	// commit test by ���J�@�N�q
	// commit test by mtbr
	private ImageButton startBtn;// �U���M��ʂ�
	private ImageButton settingBtn;// �ݒ��ʂ�
	private ImageButton playBtn;// �V�ѕ���ʂ�
	
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
		// layot��main�ɕύX����
		super.setContentView(R.layout.main);
	}

	
    // �ݒ��ʂ�
    public void onClickSetting(View v){
    	 //���̉�ʂɑJ�ڂ�����
        Intent intent = new Intent();
        intent.setClassName(
                "com.intern.cheatcamera",
                "com.intern.cheatcamera.MenuList");
        startActivity(intent);
    }
    // �U��ʂ�
    public void onClickCheat(View v){        
        //�^�C�}�[�̏���������
        mLaptime = 0.0f;
        mTimer = new Timer(true);
        
        // �u���b�N�X�N���[����\��
        View view = this.getLayoutInflater().inflate(R.layout.bluck, null);
        addContentView(view,new LinearLayout.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT));
        
//      // �f�[�^�̌Ăяo��
      SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
      // key="time" �����l�@0
      String timeStr = sharedPreferences.getString("time", "0");
      
      // String��int
      int time = Integer.parseInt(timeStr);
      // time�b���~���b�ɂ���
      time = time * 1000;
      
        // �^�C�}�[�ݒ�@���b��ɉ�ʑJ�ڂ���
        mTimer.schedule(new TimerTask() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				Log.d("timerTask","test");

		    	 //���̉�ʂɑJ�ڂ�����
		        Intent intent = new Intent();
		        intent.setClassName(
		                "com.intern.cheatcamera",
		                "com.intern.cheatcamera.CheatCall");
		        startActivity(intent);
		       
			}
		}, time);
    }
    
}
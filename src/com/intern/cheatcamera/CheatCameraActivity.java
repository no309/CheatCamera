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
	// commit test by ���J�@�N�q
	// commit test by mtbr
	private Button startBtn;// �U���M��ʂ�
	private Button settingBtn;// �ݒ��ʂ�
	private Button playBtn;// �V�ѕ���ʂ�
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        startBtn = (Button)findViewById(R.id.button1);
        settingBtn = (Button)findViewById(R.id.button2);
        playBtn = (Button)findViewById(R.id.button3);
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
    
}
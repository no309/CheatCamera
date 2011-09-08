package com.intern.cheatcamera;


import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;


public class Return extends Activity {
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		setContentView(R.layout.res);
		Button returnbutton = (Button)findViewById(R.id.returnbutton);

		Intent intent = getIntent();
        String data = intent.getStringExtra("path");
	}
	
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		
		//ImageView img =(ImageView)findViewById(R.id.);
		
		Intent intent = getIntent();
        String data = intent.getStringExtra("path");
		FileInputStream file  =null;
		BufferedInputStream buf = null;
		
		try{
			String path = Environment.getExternalStorageDirectory ().getPath ();
			
			file = new FileInputStream(data);
			buf = new BufferedInputStream(file);
			
			Bitmap bitmap = BitmapFactory.decodeStream(buf);
			
			//img.setImageBitmap(bitmap);
			
			file.close();
			
			buf.close();
		}catch(FileNotFoundException e){
			e.printStackTrace();
		}catch(IOException e){
			e.printStackTrace();			
		}
		
		
	}
	
	 public void onClickMain(View v){
    	 //���̉�ʂɑJ�ڂ�����
        Intent intent = new Intent();
        intent.setClassName(
                "com.intern.cheatcamera",
                "com.intern.cheatcamera.CheatCameraActivity");
        startActivity(intent);
    }
}

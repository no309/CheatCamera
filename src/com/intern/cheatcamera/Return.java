package com.intern.cheatcamera;


import java.io.BufferedInputStream;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;


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
		
		ImageView img =(ImageView)findViewById(R.id.image);
		
		Intent intent = getIntent();
        String data = intent.getStringExtra("path");
		FileInputStream file  =null;
		BufferedInputStream buf = null;
		
		try{
			String path = Environment.getExternalStorageDirectory ().getPath ();
			
			file = new FileInputStream(data);
			buf = new BufferedInputStream(file);
			
			Bitmap bitmap = BitmapFactory.decodeStream(buf);
			
			img.setImageBitmap(bitmap);
			
			file.close();
			
			buf.close();
		}catch(FileNotFoundException e){
			e.printStackTrace();
		}catch(IOException e){
			e.printStackTrace();			
		}
	Toast.makeText(this, "•Û‘¶‚µ‚Ü‚µ‚½", Toast.LENGTH_LONG).show();
		
	}
	
	 public void onClickMain(View v){
    	 //ŽŸ‚Ì‰æ–Ê‚É‘JˆÚ‚³‚¹‚é
        Intent intent = new Intent();
        intent.setClassName(
                "com.intern.cheatcamera",
                "com.intern.cheatcamera.CheatCameraActivity");
        startActivity(intent);
        
       //finish();
    }
}

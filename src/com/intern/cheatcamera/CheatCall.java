package com.intern.cheatcamera;


import java.io.File;

import java.io.FileOutputStream;
import java.io.IOException;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.PixelFormat;
import android.graphics.Bitmap.CompressFormat;
import android.hardware.Camera;
import android.hardware.Camera.CameraInfo;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.provider.MediaStore.Images;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

/**
 * カメラの情報取得テストアクティビティ
 * 
 * @author t3
 */
public class CheatCall extends Activity implements
		SurfaceHolder.Callback {

	/** 画面に紐づいているカメラデバイス **/
	Camera camera;

	/** カメラの表示領域 **/
	SurfaceView surfaceView;

	/** カメラの表示領域ホルダ */
	SurfaceHolder surfaceHolder;
	
	private MediaPlayer mp;
	
	String file;
	
	private final static String SAVE_FOLDER_NAME = "/ct_camera/";
	
	private TextView textView;

	@Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.cheatcall);
		surfaceView = (SurfaceView) findViewById(R.id.surface);
		surfaceHolder = surfaceView.getHolder();
		surfaceHolder.addCallback(this);
		surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
		
		textView = (TextView)findViewById(R.id.person_text);
		
		Log.d("onCreate","onCreate");
        
//      // データの呼び出し
		SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
	      // key="person" 初期値　ななしのごんべい
	    String personStr = sharedPreferences.getString("person", "ななしのごんべい");
	      
	    textView.setText(personStr);
		
		//Button button = (Button) findViewById(R.id.button);
		//Button button1 = (Button) findViewById(R.id.button1);
		ImageButton imgbutton1 = (ImageButton)findViewById(R.id.imageButton1);
		ImageButton imgbutton2 = (ImageButton)findViewById(R.id.imageButton2);
		
		
		//imgbutton1.setImageResource(R.drawable.catch);
		
		imgbutton1.setOnClickListener(shutterButtonListener);
		imgbutton2.setOnClickListener(shutterButtonListener);
	}
	
	@Override
	public void onStart(){
		super.onStart();
        mp = MediaPlayer.create(this,R.raw.call);
        mp.setLooping(true);
		mp.start();
		Log.d("onStart","mp.start");
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		Log.d("onStart","");
	}
	
	@Override
	public void onPause(){
		//closeCamera();
		super.onPause();
		Log.d("onPause","");
	}
	
	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		mp.stop();
		if(camera != null){
		camera.stopPreview();
		camera.release();
		camera = null;
		}
		Log.d("onStop","");
	}
	
	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();
		//camera.startPreview();
		//camera = Camera.open(1);
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		mp.stop();
		if(camera != null){
		//camera.stopPreview();
		camera.release();
		}
		Log.d("onDestroy","");
	}
	
	public void surfaceCreated(SurfaceHolder holder) {
	}

	public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {
		if (holder.getSurface() == null) {
			return;
		}
		surfaceHolder = holder;
		cameraPreviewStart();
	}

	public void surfaceDestroyed(SurfaceHolder holder) {
		//stopPreview();
		surfaceHolder = null;
	}

	/**
	 * カメラの情報を取得して表示
	 */
	private void cameraPreviewStart() {

		// 端末に搭載されているカメラの数を取得　
		int numberOfCameras = Camera.getNumberOfCameras();
		Log.d("MultiCameraTest",
				"number of camera = " + Integer.toString(numberOfCameras));

		// 各カメラの情報を取得
		for (int i = 0; i < numberOfCameras; i++) {
			CameraInfo caminfo = new CameraInfo();
			Camera.getCameraInfo(i, caminfo);

			// カメラの向きを取得
			int facing = caminfo.facing;

			if (facing == CameraInfo.CAMERA_FACING_BACK) {
				// 後部についているカメラの場合
				Log.d("MultiCameraTest", "cameraId=" + Integer.toString(i)
						+ ", this is a back-facing camera");
			} else if (facing == CameraInfo.CAMERA_FACING_FRONT) {
				// フロントカメラの場合
				Log.d("MultiCameraTest", "cameraId=" + Integer.toString(i)
						+ ", this is a front-facing camera");
			} else {
				Log.d("MultiCameraTest", "cameraId=" + Integer.toString(i)
						+ ", unknown camera?");
			}

			// カメラのOrientation(角度) を取得
			int orient = caminfo.orientation;
			Log.d("MultiCameraTest", "cameraId=" + Integer.toString(i)
					+ ", orientation=" + Integer.toString(orient));
		}

		if (camera == null) {
			// id=0 のカメラを起動
			camera = Camera.open(1);
			camera.setDisplayOrientation(90);
		}
		SurfaceHolder holder = surfaceHolder;
		Camera.Parameters p = camera.getParameters();
		p.setPictureFormat(PixelFormat.JPEG);

		try {
			camera.setParameters(p);
		} catch (Exception e) {
			Log.d("MultiCameraTest", "failed to set param");
			e.printStackTrace();
		}

		try {
			camera.setPreviewDisplay(holder);
		} catch (IOException e) {
			Log.d("MultiCameraTest", "failed to set display");
			e.printStackTrace();
			//closeCamera();
			if (!isFinishing())
				finish();
		}

		try {
			camera.startPreview();
		} catch (Exception e) {
			Log.d("MultiCameraTest", "startPreview failed (exit)");
			//closeCamera();
			e.printStackTrace();
		}
	}

	/*private void stopPreview() {
		if (camera != null) {
			camera.stopPreview();
		}
	}*/

	/*private void closeCamera() {
		if (camera != null) {
			camera.release();
			camera = null;
		}
	}*/
	
	private OnClickListener shutterButtonListener = new OnClickListener(){
		public void onClick(View v){
			//写真の撮影
			camera.takePicture(null, null, new Camera.PictureCallback(){
				public void onPictureTaken(byte[] data, Camera camera){
					
					mp.stop();
					
					
					
		
				/*	//SDカードへの保存処理呼び出し
					try{
						saveSD(data);
						//プレビューの再開
						camera.startPreview();
					}catch(Exception e){
						e.printStackTrace();
						//camera.startPreview();
					}*/

			        camera.stopPreview ();

			        if (data == null)
			        {
			          Log.d ("TAG", "データが取得できませんでした");
			          camera.startPreview ();
			          return;
			        }

			        if (!sdcard_be_written ())
			        {
			          Log.d ("TAG", "SDカードが認識できません");
			          camera.startPreview ();
			          return;
			        }

			        // SDカードへバイトデータを書込み
			        FileOutputStream out = null;
			        try
			        {
			          String path = Environment.getExternalStorageDirectory ().getPath ();
			          Log.d ("TAG", path);

			          File dir = new File (path + SAVE_FOLDER_NAME);

			          //フォルダが存在しなかった場合にフォルダを作成します。
			          if (!dir.exists ())
			            dir.mkdir ();

			          long date = System.currentTimeMillis ();

			          // 他のとかぶらない名前の設定
			          file = path + SAVE_FOLDER_NAME + date +".jpg";
			          Log.d ("TAG", file);

			          // 普通に保存すると、previewだと縦なのに横向きで保存されてしまうので、向きを変えて保存する
			          Bitmap tmp_bitmap = BitmapFactory.decodeByteArray (data, 0, data.length);
			          int width = tmp_bitmap.getWidth ();
			          int height = tmp_bitmap.getHeight ();

			          Matrix matrix = new Matrix ();
			          matrix.postRotate (90);

			          Bitmap bitmap = Bitmap.createBitmap (tmp_bitmap, 0, 0, width, height, matrix, true);
			          out = new FileOutputStream (file);
			          //out.write (data);
			          bitmap.compress (CompressFormat.JPEG, 100, out);
			          out.close ();

			          try
			          {
			            //コンテンツ登録（androidギャラリーへの登録）
			            ContentValues values = new ContentValues ();
			            ContentResolver contentResolver = getApplicationContext ().getContentResolver ();
			            values.put (Images.Media.MIME_TYPE, "image/jpeg");
			            values.put (Images.Media.DATA, file);
			            values.put (Images.Media.SIZE, new File (file).length ());
			            values.put (Images.Media.DATE_ADDED, date);
			            values.put (Images.Media.DATE_TAKEN, date);
			            values.put (Images.Media.DATE_MODIFIED, date);

			            contentResolver.insert (MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
			          }
			          catch (Exception e)
			          {
			            Log.d ("TAG", "再起動後に画像が認識されます");
			            e.printStackTrace ();
			          }
			          finally
			          {
			            if (out != null)
			              out.close ();
			          }
			          Log.d ("TAG", "SDカードに保存しました");
			        }
			        catch (Exception e)
			        {
			          camera.release ();
			          Log.d ("TAG", "SDカードに保存に失敗しました");
			        }

			        camera.setDisplayOrientation (90);
			        camera.startPreview ();
			       // camera.release(); //これがあると、強制終了される

			        
			     // 戻る画面へ
			        	 //次の画面に遷移させる
			        Intent intent = new Intent(CheatCall.this, Return.class);
			        intent.setClassName(
			                 "com.intern.cheatcamera",
			                 	"com.intern.cheatcamera.Return");
			        intent.putExtra("path", file);
			        startActivity(intent);
				}
			});
		}
	};
	 //書き込みができるかどうかを判別する関数
	  private boolean sdcard_be_written ()
	  {
	    String state = Environment.getExternalStorageState ();
	    return (Environment.MEDIA_MOUNTED.equals (state));
	  }

	  private boolean is_portrait ()
	  {
	    return (getResources ().getConfiguration ().orientation == Configuration.ORIENTATION_PORTRAIT);
	  }
}

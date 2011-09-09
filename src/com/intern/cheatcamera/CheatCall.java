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
 * �J�����̏��擾�e�X�g�A�N�e�B�r�e�B
 * 
 * @author t3
 */
public class CheatCall extends Activity implements
		SurfaceHolder.Callback {

	/** ��ʂɕR�Â��Ă���J�����f�o�C�X **/
	Camera camera;

	/** �J�����̕\���̈� **/
	SurfaceView surfaceView;

	/** �J�����̕\���̈�z���_ */
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
        
//      // �f�[�^�̌Ăяo��
		SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
	      // key="person" �����l�@�ȂȂ��̂���ׂ�
	    String personStr = sharedPreferences.getString("person", "�ȂȂ��̂���ׂ�");
	      
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
	 * �J�����̏����擾���ĕ\��
	 */
	private void cameraPreviewStart() {

		// �[���ɓ��ڂ���Ă���J�����̐����擾�@
		int numberOfCameras = Camera.getNumberOfCameras();
		Log.d("MultiCameraTest",
				"number of camera = " + Integer.toString(numberOfCameras));

		// �e�J�����̏����擾
		for (int i = 0; i < numberOfCameras; i++) {
			CameraInfo caminfo = new CameraInfo();
			Camera.getCameraInfo(i, caminfo);

			// �J�����̌������擾
			int facing = caminfo.facing;

			if (facing == CameraInfo.CAMERA_FACING_BACK) {
				// �㕔�ɂ��Ă���J�����̏ꍇ
				Log.d("MultiCameraTest", "cameraId=" + Integer.toString(i)
						+ ", this is a back-facing camera");
			} else if (facing == CameraInfo.CAMERA_FACING_FRONT) {
				// �t�����g�J�����̏ꍇ
				Log.d("MultiCameraTest", "cameraId=" + Integer.toString(i)
						+ ", this is a front-facing camera");
			} else {
				Log.d("MultiCameraTest", "cameraId=" + Integer.toString(i)
						+ ", unknown camera?");
			}

			// �J������Orientation(�p�x) ���擾
			int orient = caminfo.orientation;
			Log.d("MultiCameraTest", "cameraId=" + Integer.toString(i)
					+ ", orientation=" + Integer.toString(orient));
		}

		if (camera == null) {
			// id=0 �̃J�������N��
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
			//�ʐ^�̎B�e
			camera.takePicture(null, null, new Camera.PictureCallback(){
				public void onPictureTaken(byte[] data, Camera camera){
					
					mp.stop();
					
					
					
		
				/*	//SD�J�[�h�ւ̕ۑ������Ăяo��
					try{
						saveSD(data);
						//�v���r���[�̍ĊJ
						camera.startPreview();
					}catch(Exception e){
						e.printStackTrace();
						//camera.startPreview();
					}*/

			        camera.stopPreview ();

			        if (data == null)
			        {
			          Log.d ("TAG", "�f�[�^���擾�ł��܂���ł���");
			          camera.startPreview ();
			          return;
			        }

			        if (!sdcard_be_written ())
			        {
			          Log.d ("TAG", "SD�J�[�h���F���ł��܂���");
			          camera.startPreview ();
			          return;
			        }

			        // SD�J�[�h�փo�C�g�f�[�^��������
			        FileOutputStream out = null;
			        try
			        {
			          String path = Environment.getExternalStorageDirectory ().getPath ();
			          Log.d ("TAG", path);

			          File dir = new File (path + SAVE_FOLDER_NAME);

			          //�t�H���_�����݂��Ȃ������ꍇ�Ƀt�H���_���쐬���܂��B
			          if (!dir.exists ())
			            dir.mkdir ();

			          long date = System.currentTimeMillis ();

			          // ���̂Ƃ��Ԃ�Ȃ����O�̐ݒ�
			          file = path + SAVE_FOLDER_NAME + date +".jpg";
			          Log.d ("TAG", file);

			          // ���ʂɕۑ�����ƁApreview���Əc�Ȃ̂ɉ������ŕۑ�����Ă��܂��̂ŁA������ς��ĕۑ�����
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
			            //�R���e���c�o�^�iandroid�M�������[�ւ̓o�^�j
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
			            Log.d ("TAG", "�ċN����ɉ摜���F������܂�");
			            e.printStackTrace ();
			          }
			          finally
			          {
			            if (out != null)
			              out.close ();
			          }
			          Log.d ("TAG", "SD�J�[�h�ɕۑ����܂���");
			        }
			        catch (Exception e)
			        {
			          camera.release ();
			          Log.d ("TAG", "SD�J�[�h�ɕۑ��Ɏ��s���܂���");
			        }

			        camera.setDisplayOrientation (90);
			        camera.startPreview ();
			       // camera.release(); //���ꂪ����ƁA�����I�������

			        
			     // �߂��ʂ�
			        	 //���̉�ʂɑJ�ڂ�����
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
	 //�������݂��ł��邩�ǂ����𔻕ʂ���֐�
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

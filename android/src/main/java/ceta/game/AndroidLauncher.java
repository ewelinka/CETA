package ceta.game;

import java.io.File;
import java.io.IOException;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.android.Utils;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.highgui.Highgui;
import org.opencv.imgproc.Imgproc;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.ImageFormat;
import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.media.MediaScannerConnection;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Display;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.WindowManager;
import android.widget.Toast;
import ceta.game.utils.CameraUtils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;

import edu.ceta.vision.android.topcode.TopCodeDetectorAndroid;
import edu.ceta.vision.core.topcode.TopCodeDetector;

public class AndroidLauncher extends AndroidApplication implements SurfaceTexture.OnFrameAvailableListener{
	public static final String TAG = AndroidLauncher.class.getName();

	private Camera mCamera;
	private int mCameraPreviewThousandFps;
	private int VIDEO_WIDTH,VIDEO_HEIGHT,DESIRED_PREVIEW_FPS;
	private SurfaceTexture mCameraTexture;
	private byte[] buffer;
	private CustomPreviewCallback cameracallback;

	private boolean openCvInit = false;
	private CetaGame cetaGame;
	private BaseLoaderCallback mLoaderCallback = new BaseLoaderCallback(this) {
		@Override
		public void onManagerConnected(int status) {
			switch (status) {
				case LoaderCallbackInterface.SUCCESS:
				{
					openCvInit = true;
					Log.i(TAG, "OpenCV loaded successfully");
					initCameraListener();

				} break;
				default:
				{
					super.onManagerConnected(status);
				} break;
			}
		}
	};

	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		//config.hideStatusBar=false;
		//TopCodeDetector detec;
		TopCodeDetector de = new TopCodeDetectorAndroid(50,false,70,5,true,false,false, true);
		cetaGame = new CetaGame();
		initialize(cetaGame, config);
	}

	@Override
	protected void onResume() {
		super.onResume();
		OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_2_4_3, this, mLoaderCallback);

		// Ideally, the frames from the camera are at the same resolution as the input to
		// the video encoder so we don't have to scale.
		if(this.mCamera==null && openCvInit)
			openCamera(VIDEO_WIDTH, VIDEO_HEIGHT, DESIRED_PREVIEW_FPS);
	}

	@Override
	protected void onPause() {
		super.onPause();

		releaseCamera();
	}

	private void initCameraListener(){
		this.VIDEO_WIDTH = 640;
		this.VIDEO_HEIGHT = 480;
		this.DESIRED_PREVIEW_FPS = 10;
		this.buffer = new byte[this.VIDEO_HEIGHT*this.VIDEO_WIDTH];
		this.cameracallback = new CustomPreviewCallback();

		openCamera(VIDEO_WIDTH,VIDEO_HEIGHT,DESIRED_PREVIEW_FPS);
		surfaceCreated(null);
	}



	/**
	 * Stops camera preview, and releases the camera to the system.
	 */
	private void releaseCamera() {
		if (mCamera != null) {
			mCamera.stopPreview();
			mCamera.setPreviewCallback(null);
			mCamera.release();
			mCamera = null;
			Log.d(TAG, "releaseCamera -- done");
		}
	}

	private void openCamera(int desiredWidth, int desiredHeight, int desiredFps) {
		if (mCamera != null) {
			throw new RuntimeException("camera already initialized");
		}

		Camera.CameraInfo info = new Camera.CameraInfo();

		// Try to find a front-facing camera (e.g. for videoconferencing).
		int numCameras = Camera.getNumberOfCameras();
		for (int i = 0; i < numCameras; i++) {
			Camera.getCameraInfo(i, info);
			if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
				mCamera = Camera.open(i);
				break;
			}
		}
		if (mCamera == null) {
			Log.d(TAG, "No front-facing camera found; opening default");
			mCamera = Camera.open();    // opens first back-facing camera
		}
		if (mCamera == null) {
			throw new RuntimeException("Unable to open camera");
		}

		Camera.Parameters parms = mCamera.getParameters();

		CameraUtils.choosePreviewSize(parms, desiredWidth, desiredHeight);
		CameraUtils.choosePreviewFormat(parms, ImageFormat.NV21);
		// Try to set the frame rate to a constant value.
		mCameraPreviewThousandFps = CameraUtils.chooseFixedPreviewFps(parms, desiredFps * 1000);

		// Give the camera a hint that we're recording video.  This can have a big
		// impact on frame rate.
//		parms.setRecordingHint(true);
		
		mCamera.setParameters(parms);

		Camera.Size cameraPreviewSize = parms.getPreviewSize();
		String previewFacts = cameraPreviewSize.width + "x" + cameraPreviewSize.height +
				" @" + (mCameraPreviewThousandFps / 1000.0f) + "fps";
		Log.i(TAG, "Camera config: " + previewFacts);
	}




	//@Override   // SurfaceHolder.Callback
	public void surfaceCreated(SurfaceHolder holder) {
		Log.d(TAG, "surfaceCreated, holder=" + holder);

		mCameraTexture = new SurfaceTexture(0);
		mCameraTexture.setOnFrameAvailableListener(this);

		Log.d(TAG, "starting camera preview");
		try {
			mCamera.setPreviewTexture(mCameraTexture);

			mCamera.addCallbackBuffer(buffer);
			mCamera.setPreviewCallback(this.cameracallback);
			mCamera.setPreviewDisplay(holder);
		} catch (IOException ioe) {
			throw new RuntimeException(ioe);
		}
		mCamera.startPreview();
	}

	public class CustomPreviewCallback implements Camera.PreviewCallback{

		@Override
		public void onPreviewFrame(byte[] bytes, Camera camera) {
			Gdx.app.log(TAG,"frame available");
			//
			int bitsPerPixel = ImageFormat.getBitsPerPixel(ImageFormat.NV21);

			Camera.Size cameraPreviewSize = camera.getParameters().getPreviewSize();


			Mat mYuv = new Mat( cameraPreviewSize.height + cameraPreviewSize.height/2, cameraPreviewSize.width, CvType.CV_8UC1 );
			mYuv.put( 0, 0, bytes);
			Mat mRgba = new Mat();

//
//		    YuvImage yuvImage = new YuvImage(bytes, ImageFormat.NV21, cameraPreviewSize.width, cameraPreviewSize.height, null);
//		    ByteArrayOutputStream os = new ByteArrayOutputStream();
//		    yuvImage.compressToJpeg(new Rect(0, 0, cameraPreviewSize.width, cameraPreviewSize.height), 100, os);
//		    byte[] jpegByteArray = os.toByteArray();
//		    Bitmap bitmap = BitmapFactory.decodeByteArray(jpegByteArray, 0, jpegByteArray.length);
//
			Imgproc.cvtColor( mYuv, mRgba, Imgproc.COLOR_YUV2RGBA_NV21, 4 );
			// Imgproc.cvtColor( mYuv, mRgba, Imgproc.COLOR_YCrCb2RGB, 4 );

			Bitmap map = Bitmap.createBitmap( cameraPreviewSize.width, cameraPreviewSize.height, Bitmap.Config.ARGB_8888 );
			Utils.matToBitmap( mRgba, map );
//			customView.setContent(map);
//
////		    customView.setContent(bitmap);
//			//customView.setContent(redBmp);
//			customView.invalidate();
//    		Context	context	=	getApplicationContext();
//	    	File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
//			saveImage(mRgba);
			cetaGame.setLastFrame(mRgba);
			mCamera.addCallbackBuffer(buffer);
		}
	}


	@Override   // SurfaceTexture.OnFrameAvailableListener; runs on arbitrary thread
	public void onFrameAvailable(SurfaceTexture surfaceTexture) {
		//Log.d(TAG, "frame available");
		//mHandler.sendEmptyMessage(MainHandler.MSG_FRAME_AVAILABLE);
	}
	 
}

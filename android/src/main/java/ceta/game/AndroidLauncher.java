package ceta.game;

import android.graphics.ImageFormat;
import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.text.format.Formatter;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SurfaceHolder;
import ceta.game.util.osc.OSCReceiver;
import ceta.game.utils.CameraUtils;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.badlogic.gdx.utils.Logger;
import edu.ceta.vision.core.topcode.TopCodeDetector;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

public class AndroidLauncher extends AndroidApplication implements SurfaceTexture.OnFrameAvailableListener{
	public static final String TAG = AndroidLauncher.class.getName();

	private Camera mCamera;
	private int mCameraPreviewThousandFps;
	private int VIDEO_WIDTH,VIDEO_HEIGHT,DESIRED_PREVIEW_FPS;
	private SurfaceTexture mCameraTexture;
	private byte[] buffer;
	private CustomPreviewCallback cameracallback;

	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		//config.hideStatusBar=false;
		//TopCodeDetector detec;
		TopCodeDetector de;
		initCameraListener();
		initialize(new CetaGame(), config);
	}

	@Override
	protected void onResume() {
		super.onResume();

		// Ideally, the frames from the camera are at the same resolution as the input to
		// the video encoder so we don't have to scale.
		if(this.mCamera==null)
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
		CameraUtils.choosePreviewFormat(parms, ImageFormat.YUY2);
		// Try to set the frame rate to a constant value.
		mCameraPreviewThousandFps = CameraUtils.chooseFixedPreviewFps(parms, desiredFps * 1000);

		// Give the camera a hint that we're recording video.  This can have a big
		// impact on frame rate.
		parms.setRecordingHint(true);

		mCamera.setParameters(parms);

		Camera.Size cameraPreviewSize = parms.getPreviewSize();
		String previewFacts = cameraPreviewSize.width + "x" + cameraPreviewSize.height +
				" @" + (mCameraPreviewThousandFps / 1000.0f) + "fps";
		Log.i(TAG, "Camera config: " + previewFacts);
//		List<Integer> formats = parms.getSupportedPreviewFormats();
//		for(Iterator<Integer> iter = formats.iterator();iter.hasNext();){
//			Gdx.app.log(TAG,"format: " + iter.next());
//
//		}
		//public static final int YV12 = 842094169;
		//public static final int NV21 = 17;

		// Set the preview aspect ratio.
//		AspectFrameLayout layout = (AspectFrameLayout) findViewById(R.id.continuousCapture_afl);
//		layout.setAspectRatio((double) cameraPreviewSize.width / cameraPreviewSize.height);
	}




	//@Override   // SurfaceHolder.Callback
	public void surfaceCreated(SurfaceHolder holder) {
		Log.d(TAG, "surfaceCreated holder=" + holder);

		// Set up everything that requires an EGL context.
		//
		// We had to wait until we had a surface because you can't make an EGL context current
		// without one, and creating a temporary 1x1 pbuffer is a waste of time.
		//
		// The display surface that we use for the SurfaceView, and the encoder surface we
		// use for video, use the same EGL context.

		/*mEglCore = new EglCore(null, EglCore.FLAG_RECORDABLE);
		mDisplaySurface = new WindowSurface(mEglCore, holder.getSurface(), false);
		mDisplaySurface.makeCurrent();

		mFullFrameBlit = new FullFrameRect(
				new Texture2dProgram(Texture2dProgram.ProgramType.TEXTURE_EXT));
		mTextureId = mFullFrameBlit.createTextureObject();
		*/
		mCameraTexture = new SurfaceTexture(0);
		mCameraTexture.setOnFrameAvailableListener(this);

		Log.d(TAG, "starting camera preview");
		try {
			mCamera.setPreviewTexture(mCameraTexture);


			mCamera.addCallbackBuffer(buffer);
			mCamera.setPreviewCallback(this.cameracallback);

		} catch (IOException ioe) {
			throw new RuntimeException(ioe);
		}
		mCamera.startPreview();

	/*	// TODO: adjust bit rate based on frame rate?
		// TODO: adjust video width/height based on what we're getting from the camera preview?
		//       (can we guarantee that camera preview size is compatible with AVC video encoder?)
		try {
			mCircEncoder = new CircularEncoder(VIDEO_WIDTH, VIDEO_HEIGHT, 6000000,
					mCameraPreviewThousandFps / 1000, 7, mHandler);
		} catch (IOException ioe) {
			throw new RuntimeException(ioe);
		}
		mEncoderSurface = new WindowSurface(mEglCore, mCircEncoder.getInputSurface(), true);

		updateControls();*/

	}

	public class CustomPreviewCallback implements Camera.PreviewCallback{

		@Override
		public void onPreviewFrame(byte[] bytes, Camera camera) {
			Gdx.app.log(TAG,"frame available");
			mCamera.addCallbackBuffer(buffer);
		}
	}


	@Override   // SurfaceTexture.OnFrameAvailableListener; runs on arbitrary thread
	public void onFrameAvailable(SurfaceTexture surfaceTexture) {
		//Log.d(TAG, "frame available");
		//mHandler.sendEmptyMessage(MainHandler.MSG_FRAME_AVAILABLE);
	}
}

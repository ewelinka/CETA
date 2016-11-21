package ceta.game;

import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.text.format.Formatter;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import ceta.game.util.osc.OSCReceiver;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import edu.ceta.vision.android.topcode.TopCodeDetectorAndroid;


public class AndroidLauncher extends AndroidApplication {
	public static final String TAG = AndroidLauncher.class.getName();
	public TopCodeDetectorAndroid topCodeDetector;

	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		//config.hideStatusBar=false;
		topCodeDetector = new TopCodeDetectorAndroid(40,true,70,5,true,false);
		initialize(new CetaGame(), config);

	}
	
}

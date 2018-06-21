package ceta.game;


import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;



public class AndroidLauncher extends AndroidApplication{
	public static final String TAG = AndroidLauncher.class.getName();


	private CetaGame cetaGame;


	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		config.useImmersiveMode = true;
		cetaGame = new CetaGame();
		initialize(cetaGame, config);
	}




	 
}

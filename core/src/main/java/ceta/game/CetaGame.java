package ceta.game;

import ceta.game.game.Assets;
import ceta.game.screens.DirectedGame;
import ceta.game.screens.Level1HorizontalScreen;
import ceta.game.screens.MenuScreen;
import ceta.game.screens.TestScreen;
import ceta.game.transitions.ScreenTransition;
import ceta.game.transitions.ScreenTransitionFade;
import ceta.game.util.AudioManager;
import ceta.game.util.Constants;
import ceta.game.util.GamePreferences;
import ceta.game.managers.LevelsManager;
import ceta.game.util.osc.OSCReceiver;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.illposed.osc.OSCListener;

import edu.ceta.vision.core.utils.Logger;

import org.opencv.core.Mat;


public class CetaGame extends DirectedGame {

	private boolean frameBlocked, hasNewFrame;
	private Mat lastFrame;//, previousFrame;
	private Object syncObject = new Object();
	@Override
	public void create () {
		Constants c = new Constants(); // to set the constants (tablet or cv)
		this.frameBlocked = false;
		// Set Libgdx log level
		Gdx.app.setLogLevel(Application.LOG_DEBUG);
		// Load assets
		Assets.instance.init(new AssetManager());
		// load preferences
		GamePreferences.instance.load();
		levelsManager = new LevelsManager(this);

		// load music
		//AudioManager.instance.play(Assets.instance.music.song01);
		ScreenTransition transition = ScreenTransitionFade.init(1);
		//setScreen(new MenuScreen(this),transition);
		//setScreen(new TestScreen(this),transition);

		//setScreen(new MenuScreen(this),transition);
		setScreen(new TestScreen(this,1),transition);
		//setScreen(level1Screen,transition);
		//setScreen(new CongratulationsScreen(this),transition);

	}

	public void setLastFrame(Mat frame){
		Gdx.app.log(TAG,"Setting last frame setLastFrame!");
		synchronized (syncObject) {
			if(!this.frameBlocked) {
				Gdx.app.log(TAG,"Setting new frame!");
				this.lastFrame = frame.clone();
				this.hasNewFrame = true;
			}else{
				Gdx.app.log(TAG,"blocked frame!");
			}
		}
	}

	public Mat getAndBlockLastFrame(){
		synchronized (syncObject) {
			Gdx.app.log(TAG,"blocking frame!");
			this.frameBlocked = true;
			this.hasNewFrame = false;
			return this.lastFrame;
		}

	}

	public void releaseFrame(){

		synchronized (syncObject) {
			Gdx.app.log(TAG,"Frame released!");
			if(this.lastFrame!=null){
				//this.lastFrame.release();
				this.frameBlocked = false;
			}
		}
	}

	public boolean hasNewFrame(){
		return hasNewFrame;
	}


//	public void initReceiver(OSCListener listener){
//		this.receiver =  new OSCReceiver("/wizardOfOz", 12345, listener);
//		//TODO aqui hay que pasarle el level1Screen como OSCListener
//	}
//
//	public void startOSCReceiver(){
//		this.receiver.start();
//	}
//
//	public void stopOSCReceiver(){
//		this.receiver.stop();
//	}
//	public String getLocalIp() {
//		return localIp;
//	}
//	public void setLocalIp(String localIp) {
//		this.localIp = localIp;
//	}
//
//
//	public Level1HorizontalScreen getLevel1HorizontalScreen(){
//		return level1Screen;
//	}
	

	
}

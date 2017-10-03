package ceta.game;

import ceta.game.game.Assets;
import ceta.game.managers.ResultsManager;
import ceta.game.screens.*;
import ceta.game.transitions.ScreenTransition;
import ceta.game.transitions.ScreenTransitionFade;
import ceta.game.util.AudioManager;
import ceta.game.util.Constants;
import ceta.game.util.GamePreferences;
import ceta.game.managers.LevelsManager;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;

import org.opencv.core.Mat;


public class CetaGame extends DirectedGame {

	private boolean frameBlocked, hasNewFrame;
	private Mat lastFrame;//, previousFrame;
	private Object syncObject = new Object();
	@Override
	public void create () {
		Constants c = new Constants(); // to set the constants (tablet or cv)
		this.frameBlocked = false;
		//TODO:smarichal should hasNewFrame be initialized in false?
		//this.hasNewFrame = false;
		// Set Libgdx log level
		Gdx.app.setLogLevel(Application.LOG_NONE);
		// Load assets
		Assets.instance.init(new AssetManager());
		// load preferences
		GamePreferences.instance.load();
		levelsManager = new LevelsManager(this);
		resultsManager = new ResultsManager();


		// load music
		AudioManager.instance.play(Assets.instance.music.song01);
		ScreenTransition transition = ScreenTransitionFade.init(1);
		//setScreen(new MenuScreen(this),transition);
		//setScreen(new Level3VerticalScreen(this,1),transition);
		//setScreen(new TutorialScreen(this),transition);
		setScreen(new IntroBrunoScreen(this),transition);
		//setScreen(new IntroScreen(this),transition);
		//setScreen(new Level1VerticalMovingScreen(this, 2), transition);
		//setScreen(new Level3HorizontalCvScreen(this, 1, Assets.instance.staticBackground.city1), transition);
		//setScreen(new Level2VerticalScreen(this, 13, Assets.instance.staticBackground.clouds3), transition);
		//setScreen(new IntroScreen(this));

		//setScreen(new Level1VerticalScreen(this, 17, Assets.instance.staticBackground.city1), transition);
		//setScreen(new TutorialScreen(this));
		//setScreen(new TreeScreen(this,true));
		//setScreen(new Level1VerticalCvScreen(this, 2, Assets.instance.staticBackground.city1), transition);


	}

	public void setLastFrame(Mat frame){
	//	Gdx.app.log(TAG,"Setting last frame setLastFrame!");
		synchronized (syncObject) {
			if(!this.frameBlocked) {
			//	Gdx.app.log(TAG,"Setting new frame!");
				this.lastFrame = frame.clone();
				this.hasNewFrame = true;
			}else{
		//		Gdx.app.log(TAG,"blocked frame!");
			}
		}
	}

	public Mat getAndBlockLastFrame(){
		synchronized (syncObject) {
	//		Gdx.app.log(TAG,"blocking frame!");
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

	
}

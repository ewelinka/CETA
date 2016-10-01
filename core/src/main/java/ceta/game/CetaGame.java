package ceta.game;

import ceta.game.game.Assets;
import ceta.game.screens.DirectedGame;
import ceta.game.screens.Level1Screen;
import ceta.game.transitions.ScreenTransition;
import ceta.game.transitions.ScreenTransitionFade;
import ceta.game.util.AudioManager;
import ceta.game.util.osc.OSCReceiver;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.illposed.osc.OSCListener;



public class CetaGame extends DirectedGame {
	SpriteBatch batch;
	Texture img;
	private OSCReceiver receiver;
	private String localIp;
	
	private Level1Screen level1Screen;
	
	
	public CetaGame(){
		this.localIp="";
	}
	
	public CetaGame(String localIp){
		this.localIp=localIp;
	}
	@Override
	public void create () {
		// Set Libgdx log level
		Gdx.app.setLogLevel(Application.LOG_DEBUG);
		// Load assets
		Assets.instance.init(new AssetManager());
		// load music
		AudioManager.instance.play(Assets.instance.music.song01);
		ScreenTransition transition = ScreenTransitionFade.init(1);
		//setScreen(new MenuScreen(this),transition);
		//setScreen(new TestScreen(this),transition);
		level1Screen = new Level1Screen(this);
		setScreen(level1Screen,transition);
		//setScreen(new FinalScreen(this),transition);
	}
	
	
	public void initReceiver(OSCListener listener){
		this.receiver =  new OSCReceiver("/wizardOfOz", 12345, listener);
		//TODO aqui hay que pasarle el level1Screen como OSCListener
	}
	
	public void startOSCReceiver(){
		this.receiver.start();
	}
	
	public void stopOSCReceiver(){
		this.receiver.stop();
	}
	public String getLocalIp() {
		return localIp;
	}
	public void setLocalIp(String localIp) {
		this.localIp = localIp;
	}
	

	
}

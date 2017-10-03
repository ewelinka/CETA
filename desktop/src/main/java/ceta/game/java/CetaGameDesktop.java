package ceta.game.java;

import ceta.game.CetaGame;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class CetaGameDesktop {
	public static void main (String[] args) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.height = 768;
		config.width = 450;
		
		//System.load("/home/seba/NVPACK/OpenCV-2.4.8.2-Tegra-sdk/sdk/java/bin/opencv_java248.jar");--> esto es pa android, burro!
		System.loadLibrary("opencv_java2413");
		new LwjglApplication(new CetaGame(), config);
	}
}
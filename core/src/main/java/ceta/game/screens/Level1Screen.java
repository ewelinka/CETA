package ceta.game.screens;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ceta.game.game.WorldRenderer;
import ceta.game.game.controllers.Level1Controller;
import ceta.game.util.Constants;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.illposed.osc.OSCListener;
import com.illposed.osc.OSCMessage;

/**
 * Created by ewe on 8/23/16.
 */
public class Level1Screen extends AbstractGameScreen implements OSCListener{
	
    private static final String TAG = Level1Screen.class.getName();
    private Level1Controller worldController;
    private WorldRenderer worldRenderer;
    private OrthographicCamera camera;
    private Stage stage;

    private boolean paused;

    public Level1Screen(DirectedGame game) {

        super(game);
    }


    @Override
    public void render(float deltaTime) {

        // Do not update game world when paused.
        if (!paused) {
            worldController.update(deltaTime);

        }
        // Render game world to screen
        worldRenderer.render();

    }


    @Override
    public void resize(int width, int height) {
        worldRenderer.resize(width, height);

    }

    @Override
    public void show() {
        // TODO load preferences
        stage = new Stage(new FitViewport(Constants.VIEWPORT_WIDTH , Constants.VIEWPORT_HEIGHT));
        worldController = new Level1Controller(game, stage);
        // Todo here we should make camera stuff and fitviewport
        worldRenderer = new WorldRenderer(worldController,stage);
        // android back key
        Gdx.input.setCatchBackKey(true);
    }

    @Override
    public void hide() {
        worldController.dispose();
        worldRenderer.dispose();
        stage.dispose();
        Gdx.input.setCatchBackKey(false);
    }

    @Override
    public void pause() {
        paused =true;
    }

    @Override
    public void resume () {
        super.resume();
        // Only called on Android!
        paused = false;
    }

    @Override
    public void dispose(){
        worldController.dispose();
        worldRenderer.dispose();
        stage.dispose();

    }


    @Override
    public InputProcessor getInputProcessor() {
        InputMultiplexer multiplexer = new InputMultiplexer();
        multiplexer.addProcessor(stage);
        multiplexer.addProcessor(worldController);
        return multiplexer;
    }


	@Override
	public void acceptMessage(Date time, OSCMessage message) {
		Gdx.app.log(TAG, "message received!!!");
		for(int i =0;i< message.getArguments().size();i++){
			 Gdx.app.log(TAG,"arg("+i+")="+message.getArguments().get(i));
		}
		Gdx.app.log(TAG, "----------- end of message ------------");
		
		List<Object> arguments = message.getArguments();
		if(arguments.get(0).equals("addBlock")){
			
			/*Message Format
			 * arg0 - addBlock | removeBlock : String
			 *    if arg0 == addBlock 
			 *    		arg1 - blockValue : int
			 *    		arg2 - originX    : int
			 *    		arg3 - originY    : int
			 *    		arg4 - rotation   : int
			 *    		arg5 - vertex 1   : int
			 *    		arg6 -
			 *    		arg7 -
			 *    		arg8 -
			 *    arg5 -
			 * */
		
			/*ArrayList<Object> collectionToSend = new ArrayList<Object>();
	    	collectionToSend.add("addBlock");
	    	collectionToSend.add((int)block.getBlockValue());
	    	collectionToSend.add(polygon2.getOriginX());
	    	collectionToSend.add(polygon2.getOriginY());
	    	collectionToSend.add(polygon2.getRotation());
	    	
	    	for(int i=0;i< polygon2.getVertices().length;i++){
	        	collectionToSend.add(polygon2.getVertices()[i]);
	    	}*/
	        	
	        	
		}else if(arguments.get(0).equals("removeBlock")){
			
		}
	}
	
}

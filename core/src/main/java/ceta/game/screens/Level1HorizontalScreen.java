package ceta.game.screens;

import java.util.Date;
import java.util.List;

import ceta.game.game.controllers.AbstractWorldController;
import ceta.game.game.renderers.WorldRendererLevel1;
import ceta.game.game.controllers.Level1HorizontalController;
import ceta.game.util.Constants;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.illposed.osc.OSCListener;
import com.illposed.osc.OSCMessage;

/**
 * Created by ewe on 8/23/16.
 */
public class Level1HorizontalScreen extends AbstractGameScreen implements OSCListener{
    private static final String TAG = Level1HorizontalScreen.class.getName();
    private boolean paused;

    public Level1HorizontalScreen(DirectedGame game) {

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
        Gdx.app.log(TAG," we start the RESIZE of the screen ! "+Gdx.graphics.getWidth());
        worldRenderer.resize(width, height);

    }

    @Override
    public void show() {
        Gdx.app.log(TAG," we start the SHOW! "+Gdx.graphics.getWidth());
        // TODO load preferences
        stage = new Stage(new FitViewport(Constants.VIEWPORT_WIDTH , Constants.VIEWPORT_HEIGHT));
        worldController = new Level1HorizontalController(game, stage);
        // Todo here we should make camera stuff and fitviewport
        worldRenderer = new WorldRendererLevel1(worldController,stage);
        // android back key
        Gdx.input.setCatchBackKey(true);
    }

    @Override
    public void hide() {
        Gdx.app.log(TAG," we start the HIDE of the screen ! " +Gdx.graphics.getWidth());

        worldController.dispose();
        worldRenderer.dispose();
        stage.dispose();
        Gdx.input.setCatchBackKey(false);
    }

    @Override
    public void pause() {
        Gdx.app.log(TAG," we start the PAUSE of the screen ! " +Gdx.graphics.getWidth());
        paused =true;
    }

    @Override
    public void resume () {
        Gdx.app.log(TAG," we start the RESUME of the screen ! " +Gdx.graphics.getWidth());
        super.resume();
        // Only called on Android!
        paused = false;
    }

    @Override
    public void dispose(){
        Gdx.app.log(TAG," we start the DISPOSE of the screen ! " +Gdx.graphics.getWidth());
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

    public AbstractWorldController getLevel1Controller(){
        return worldController;
    }


    @Override
    public void acceptMessage(Date date, OSCMessage message) {
        Gdx.app.log(TAG, "message received!!!");
        for(int i =0;i< message.getArguments().size();i++){
            Gdx.app.log(TAG,"arg("+i+")="+message.getArguments().get(i));
        }
        Gdx.app.log(TAG, "----------- end of message ------------");

        List<Object> arguments = message.getArguments();
        if(arguments.get(0).equals("addBlock")) {
            Gdx.app.log(TAG, "add osc block "+ arguments.get(1).toString());
            /*
             * TODO Ewe: Formato nuevo del mensaje
             * "addBlock"
    		 * blockValue
    	     * blockId
             */
            ((Level1HorizontalController)worldController).getVirtualBlocksManagerOSC().oscAdd(
                    Float.valueOf(arguments.get(1).toString()), // value
                    Integer.valueOf(arguments.get(2).toString()), // id
                    Float.valueOf(arguments.get(3).toString()), // pos x
                    Float.valueOf(arguments.get(4).toString()), // pos y
                    Float.valueOf(arguments.get(5).toString()) // rotation
            );
        }else if(arguments.get(0).equals("removeBlock")){
            ((Level1HorizontalController)worldController).getVirtualBlocksManagerOSC().oscRemove(
                    Integer.valueOf(arguments.get(1).toString()) // id to remove
            );
            /*
             * TODO Ewe: Formato nuevo del mensaje
             * "removeBlock"
    	     * blockId
             */
        }else if(arguments.get(0).equals("updateBlock")){
        	/*
             * TODO Ewe: Formato nuevo del mensaje
             * "updateBlock"
    		 * blockValue
    	     * blockId
             */
            ((Level1HorizontalController)worldController).getVirtualBlocksManagerOSC().oscUpdateBlock(
                    Integer.valueOf(arguments.get(2).toString()), // id
                    Float.valueOf(arguments.get(3).toString()), // pos x
                    Float.valueOf(arguments.get(4).toString()), // pos y
                    Float.valueOf(arguments.get(5).toString()) // rotation
            );
        } else if(arguments.get(0).equals("startCountdown")){
            worldController.setCountdownOn(true);


        } else if(arguments.get(0).equals("cancelCountdown")){
            worldController.setCountdownOn(false);

        }


    }
}

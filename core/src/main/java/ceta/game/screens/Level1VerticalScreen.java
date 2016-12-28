package ceta.game.screens;

import ceta.game.game.controllers.Level1VerticalController;
import ceta.game.game.renderers.WorldRenderer;
import ceta.game.game.renderers.WorldRenderer1Vertical;
import ceta.game.util.Constants;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;


/**
 * Created by ewe on 10/19/16.
 */
public class Level1VerticalScreen extends AbstractGameScreen {
    private static final String TAG = Level1VerticalScreen.class.getName();
    private boolean paused;

    public Level1VerticalScreen(DirectedGame game, int levelNr) {

        super(game,levelNr);


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
        Gdx.app.log(TAG," we start the SHOW! "+Gdx.graphics.getWidth());
        // TODO load preferences
        stage = new Stage(new FitViewport(Constants.VIEWPORT_WIDTH , Constants.VIEWPORT_HEIGHT));
        worldController = new Level1VerticalController(game, stage,levelNr);
        // Todo here we should make camera stuff and fitviewport
        worldRenderer = new WorldRenderer1Vertical(worldController,stage, false); //false to indicate isVertical
        // android back key
        Gdx.input.setCatchBackKey(true);
    }

    @Override
    public void hide() {
        Gdx.app.log(TAG," we start the HIDE of the screen ! " +Gdx.graphics.getWidth()+" h "+Gdx.graphics.getHeight());

//        worldController.dispose();
//        worldRenderer.dispose();
//        stage.dispose();
        Gdx.input.setCatchBackKey(false);
        dispose();
    }

    @Override
    public void pause() {
        Gdx.app.log(TAG," we start the PAUSE!");
        paused =true;
    }

    @Override
    public void resume () {
        Gdx.app.log(TAG," we start the RESUME!");
        super.resume();
        // Only called on Android!
        paused = false;
    }

    @Override
    public void dispose(){
        Gdx.app.log(TAG," we start the DISPOSE!");
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

}


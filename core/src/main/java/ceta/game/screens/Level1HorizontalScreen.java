package ceta.game.screens;

import ceta.game.game.Assets;
import ceta.game.game.controllers.Level1HorizontalController;
import ceta.game.game.renderers.WorldRenderer;
import ceta.game.util.Constants;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;

/**
 * Created by ewe on 8/23/16.
 */
public class Level1HorizontalScreen extends AbstractGameScreen{
    private static final String TAG = Level1HorizontalScreen.class.getName();
    private boolean paused;

//    public Level1HorizontalScreen(DirectedGame game, int levelNr) {
//        super(game, levelNr);
//    }

    public Level1HorizontalScreen(DirectedGame game, int levelNr, TextureAtlas.AtlasRegion regtex) {
        super(game, levelNr, regtex);
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
        Gdx.app.log(TAG," we start the RESIZE of the screen ! "+Gdx.graphics.getWidth()+" h "+Gdx.graphics.getHeight());
        worldRenderer.resize(width, height);

    }

    @Override
    public void show() {
        Gdx.app.log(TAG," we start the SHOW! "+Gdx.graphics.getWidth());
        stage = new Stage(new FitViewport(Constants.VIEWPORT_WIDTH , Constants.VIEWPORT_HEIGHT));
        worldController = new Level1HorizontalController(game,stage,levelJson);
        worldRenderer = new WorldRenderer(worldController,stage,regTex); // default set number line to horizontal
        // android back key
        Gdx.input.setCatchBackKey(true);
    }

    @Override
    public void hide() {
        Gdx.app.log(TAG," we start the HIDE of the screen ! " +Gdx.graphics.getWidth()+" h "+Gdx.graphics.getHeight());

     //   Gdx.input.setCatchBackKey(false);
        dispose();
    }

    @Override
    public void pause() {
        Gdx.app.log(TAG," we start the PAUSE of the screen ! " +Gdx.graphics.getWidth()+" h "+Gdx.graphics.getHeight());
        paused =true;
    }

    @Override
    public void resume () {
        Gdx.app.log(TAG," we start the RESUME of the screen ! " +Gdx.graphics.getWidth()+" h "+Gdx.graphics.getHeight());
        super.resume();
        // Only called on Android!
        paused = false;
    }

    @Override
    public void dispose(){
        Gdx.app.log(TAG," we start the DISPOSE of the screen ! " +Gdx.graphics.getWidth()+" h "+Gdx.graphics.getHeight());
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

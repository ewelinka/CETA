package ceta.game.screens;

//import ceta.game.game.WorldController;
//import ceta.game.game.WorldRenderer;
//import ceta.game.game.levels.Level;

import ceta.game.game.WorldController;
import ceta.game.game.WorldRenderer;
import ceta.game.game.objects.Bruno;
import ceta.game.game.objects.Coin;
import ceta.game.game.objects.Latter;
import ceta.game.util.Constants;
import ceta.game.util.LatterManager;
import ceta.game.util.VirtualBlocksManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.ColorAction;
import com.badlogic.gdx.utils.viewport.FitViewport;

/**
 * Created by ewe on 7/25/16.
 */
public class LevelOneScreen  extends AbstractGameScreen {
    private static final String TAG = LevelOneScreen.class.getName();
    private WorldController worldController;
    private WorldRenderer worldRenderer;
    private OrthographicCamera camera;
    private Stage stage;

    // Rectangles for collision detection
    private Rectangle r1 = new Rectangle();
    private Rectangle r2 = new Rectangle();

    private boolean paused;

    public LevelOneScreen (DirectedGame game) {

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
        worldController = new WorldController(game, stage);
        // Todo here we should make camera stuff and fitviewport
        worldRenderer = new WorldRenderer(worldController,stage);
    }

    @Override
    public void hide() {
        worldController.dispose();
        worldRenderer.dispose();
        stage.dispose();
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
        //Gdx.input.setInputProcessor(multiplexer);


        return multiplexer;
    }
}

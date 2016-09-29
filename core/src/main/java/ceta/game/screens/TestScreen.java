package ceta.game.screens;



import ceta.game.game.WorldRenderer;
import ceta.game.game.controllers.Level1Controller;
import ceta.game.game.controllers.WorldController;
import ceta.game.util.Constants;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;

/**
 * Created by ewe on 7/25/16.
 */
public class TestScreen extends AbstractGameScreen {
    private static final String TAG = TestScreen.class.getName();
    private WorldController worldController;
    private WorldRenderer worldRenderer;
    private OrthographicCamera camera;
    private Stage stage;

    private boolean paused;

    public TestScreen(DirectedGame game) {

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
        //Gdx.input.setInputProcessor(multiplexer);


        return multiplexer;
    }
}

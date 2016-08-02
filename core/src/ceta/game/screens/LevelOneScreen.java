package ceta.game.screens;

import ceta.game.game.WorldController;
import ceta.game.game.WorldRenderer;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;

/**
 * Created by ewe on 7/25/16.
 */
public class LevelOneScreen  extends AbstractGameScreen {
    private static final String TAG = LevelOneScreen.class.getName();
    private Stage stage;
    private WorldController worldController;
    private WorldRenderer worldRenderer;
    private boolean paused;

    public LevelOneScreen (DirectedGame game) {
        super(game);
    }

    @Override
    public void buildStage() {

    }

    @Override
    public void render(float deltaTime) {
        // Do not update game world when paused.
        if (!paused) {
            // Update game world by the time that has passed
            // since last rendered frame.
            worldController.update(deltaTime);
        }
        // Sets the clear screen color to: Cornflower Blue
        Gdx.gl.glClearColor(0x64 / 255.0f, 0x95 / 255.0f,0xed / 255.0f, 0xff / 255.0f);
        // Clears the screen
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        // Render game world to screen
        worldRenderer.render();

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void show() {
        worldController = new WorldController(game);
        worldRenderer = new WorldRenderer(worldController);
        // android back key
        Gdx.input.setCatchBackKey(true);
    }

    @Override
    public void hide() {

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
    public InputProcessor getInputProcessor() {
        return worldController;
    }
}

package ceta.game.screens;

import ceta.game.game.controllers.Level2VerticalController;
import ceta.game.game.controllers.TestController;
import ceta.game.game.renderers.WorldRenderer;
import ceta.game.util.Constants;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;

/**
 * Created by ewe on 11/22/16.
 */
public class TestScreen  extends Level1VerticalScreen {
    private static final String TAG = TestScreen.class.getName();


    public TestScreen(DirectedGame game, int levelNr) {
        super(game,levelNr);
    }

    @Override
    public void show() {
        Gdx.app.log(TAG," we start the SHOW! "+Gdx.graphics.getWidth());
        // TODO load preferences
        stage = new Stage(new FitViewport(Constants.VIEWPORT_WIDTH , Constants.VIEWPORT_HEIGHT));
        worldController = new TestController(game,stage,levelNr);
        // Todo here we should make camera stuff and fitviewport
        worldRenderer = new WorldRenderer(worldController,stage, false); //false because is vertical!!
        // android back key
        Gdx.input.setCatchBackKey(true);
    }
}

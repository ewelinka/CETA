package ceta.game.screens;

import ceta.game.game.controllers.Level3HorizontalController;
import ceta.game.game.renderers.WorldRenderer;
import ceta.game.util.Constants;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;

/**
 * Created by ewe on 11/4/16.
 */
public class Level3HorizontalScreen extends Level1HorizontalScreen{
    private static final String TAG = Level3HorizontalScreen.class.getName();

    public Level3HorizontalScreen(DirectedGame game, int levelNr) {
        super(game,levelNr);
    }

    @Override
    public void show() {
        Gdx.app.log(TAG," we start the SHOW! "+Gdx.graphics.getWidth());
        // TODO load preferences
        stage = new Stage(new FitViewport(Constants.VIEWPORT_WIDTH , Constants.VIEWPORT_HEIGHT));
        worldController = new Level3HorizontalController(game,stage,levelNr);
        //worldController = new Level1HorizontalController(game, stage);
        // Todo here we should make camera stuff and fitviewport
        worldRenderer = new WorldRenderer(worldController,stage);
        // android back key
        Gdx.input.setCatchBackKey(true);
    }
}
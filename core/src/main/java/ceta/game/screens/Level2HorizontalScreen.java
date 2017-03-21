package ceta.game.screens;

import ceta.game.game.controllers.Level2HorizontalController;
import ceta.game.game.renderers.WorldRenderer;
import ceta.game.util.Constants;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;

/**
 * Created by ewe on 11/4/16.
 */
public class Level2HorizontalScreen extends Level1HorizontalScreen {

    private static final String TAG = Level2HorizontalScreen.class.getName();


    public Level2HorizontalScreen(DirectedGame game, int levelNr,TextureAtlas.AtlasRegion  regtex) {
        super(game,levelNr,regtex);
    }

    @Override
    public void show() {
        Gdx.app.log(TAG," we start the SHOW! "+Gdx.graphics.getWidth());
        stage = new Stage(new FitViewport(Constants.VIEWPORT_WIDTH , Constants.VIEWPORT_HEIGHT));
        worldController = new Level2HorizontalController(game,stage,levelJson);
        //worldController = new Level1HorizontalController(game, stage);
        worldRenderer = new WorldRenderer(worldController,stage,regTex);
        // android back key
        Gdx.input.setCatchBackKey(true);
    }


}

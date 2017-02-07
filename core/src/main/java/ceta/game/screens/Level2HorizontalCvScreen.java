package ceta.game.screens;

import ceta.game.game.controllers.Level2HorizontalController;
import ceta.game.game.controllers.Level2HorizontalCvController;
import ceta.game.game.renderers.WorldRenderer;
import ceta.game.game.renderers.WorldRendererCV;
import ceta.game.util.Constants;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;

/**
 * Created by ewe on 12/5/16.
 */
public class Level2HorizontalCvScreen extends Level1HorizontalCvScreen{
    private static final String TAG = Level2HorizontalCvScreen.class.getName();
    public Level2HorizontalCvScreen(DirectedGame game, int levelNr, TextureAtlas.AtlasRegion  regtex) {
        super(game, levelNr,regtex);
    }

    @Override
    public void show() {
        Gdx.app.log(TAG," we start the SHOW! "+Gdx.graphics.getWidth());
        // TODO load preferences
        stage = new Stage(new FitViewport(Constants.VIEWPORT_WIDTH , Constants.VIEWPORT_HEIGHT));
        worldController = new Level2HorizontalCvController(game,stage,levelJson);
        //worldController = new Level1HorizontalController(game, stage);
        // Todo here we should make camera stuff and fitviewport
        worldRenderer = new WorldRendererCV(worldController,stage,regTex);
        // android back key
        Gdx.input.setCatchBackKey(true);
    }
}

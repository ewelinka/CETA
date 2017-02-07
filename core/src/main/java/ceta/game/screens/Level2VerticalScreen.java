package ceta.game.screens;

import ceta.game.game.controllers.Level2HorizontalController;
import ceta.game.game.controllers.Level2VerticalController;
import ceta.game.game.renderers.WorldRenderer;
import ceta.game.util.Constants;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;

/**
 * Created by ewe on 11/9/16.
 */
public class Level2VerticalScreen extends Level1VerticalScreen {
    private static final String TAG = Level2VerticalScreen.class.getName();


    public Level2VerticalScreen(DirectedGame game, int levelNr,TextureAtlas.AtlasRegion  regtex) {
        super(game,levelNr,regtex);
    }

    @Override
    public void show() {
        Gdx.app.log(TAG," we start the SHOW! "+Gdx.graphics.getWidth());
        // TODO load preferences
        stage = new Stage(new FitViewport(Constants.VIEWPORT_WIDTH , Constants.VIEWPORT_HEIGHT));
        worldController = new Level2VerticalController(game,stage,levelJson);
        // Todo here we should make camera stuff and fitviewport
        worldRenderer = new WorldRenderer(worldController,stage, false,regTex); //false because is vertical!!
        // android back key
        Gdx.input.setCatchBackKey(true);
    }
}

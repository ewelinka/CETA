package ceta.game.screens;

import ceta.game.game.controllers.Level3HorizontalController;
import ceta.game.game.controllers.Level3VerticalController;
import ceta.game.game.renderers.WorldRenderer;
import ceta.game.util.Constants;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;

/**
 * Created by ewe on 11/9/16.
 */
public class Level3VerticalScreen extends Level1VerticalScreen {
    private static final String TAG = Level3VerticalScreen.class.getName();
    public Level3VerticalScreen(DirectedGame game, int levelNr,TextureAtlas.AtlasRegion  regtex) {
        super(game,levelNr,regtex);
    }

    @Override
    public void show() {
        Gdx.app.log(TAG," we start the SHOW! "+Gdx.graphics.getWidth());
        stage = new Stage(new FitViewport(Constants.VIEWPORT_WIDTH , Constants.VIEWPORT_HEIGHT));
        worldController = new Level3VerticalController(game,stage,levelJson);
        worldRenderer = new WorldRenderer(worldController,stage,false,regTex);
        // android back key
        Gdx.input.setCatchBackKey(true);
    }
}

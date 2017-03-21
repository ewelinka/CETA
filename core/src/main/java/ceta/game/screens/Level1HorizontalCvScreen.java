package ceta.game.screens;

import ceta.game.game.Assets;
import ceta.game.game.controllers.Level1HorizontalController;
import ceta.game.game.controllers.Level1HorizontalCvController;
import ceta.game.game.renderers.WorldRenderer;
import ceta.game.game.renderers.WorldRendererCV;
import ceta.game.util.Constants;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;

/**
 * Created by ewe on 12/5/16.
 */
public class Level1HorizontalCvScreen extends Level1HorizontalScreen {
    private static final String TAG = Level1HorizontalCvScreen.class.getName();
    private boolean paused;

//    public Level1HorizontalCvScreen(DirectedGame game, int levelNr) {
//        super(game, levelNr);
//    }
    public Level1HorizontalCvScreen(DirectedGame game, int levelNr, TextureAtlas.AtlasRegion  regtex) {
        super(game, levelNr,regtex);
    }


    @Override
    public void show() {
        Gdx.app.log(TAG," we start the SHOW! "+Gdx.graphics.getWidth());
        stage = new Stage(new FitViewport(Constants.VIEWPORT_WIDTH , Constants.VIEWPORT_HEIGHT));
        worldController = new Level1HorizontalCvController(game,stage,levelJson);
        worldRenderer = new WorldRendererCV(worldController,stage, regTex); // default set number line to horizontal
        // android back key
        Gdx.input.setCatchBackKey(true);
    }

}

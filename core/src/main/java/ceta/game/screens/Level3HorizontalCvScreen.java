package ceta.game.screens;

import ceta.game.game.controllers.Level1VerticalCvController;
import ceta.game.game.controllers.Level3HorizontalCvController;
import ceta.game.game.renderers.WorldRendererCV;
import ceta.game.util.Constants;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;

/**
 * Created by ewe on 12/2/16.
 */
public class Level3HorizontalCvScreen extends Level3HorizontalScreen {
    private static final String TAG = Level3HorizontalCvScreen.class.getName();

    public Level3HorizontalCvScreen(DirectedGame game, int levelNr,TextureAtlas.AtlasRegion  regtex) {
        super(game, levelNr,regtex);
    }


    @Override
    public void show() {
        Gdx.app.log(TAG," we start the SHOW! "+Gdx.graphics.getWidth());
        // TODO load preferences
        stage = new Stage(new FitViewport(Constants.VIEWPORT_WIDTH , Constants.VIEWPORT_HEIGHT));
        worldController = new Level3HorizontalCvController(game,stage,levelJson);
        // Todo here we should make camera stuff and fitviewport
        worldRenderer = new WorldRendererCV(worldController,stage,regTex); //false because is vertical!!
        // android back key
        Gdx.input.setCatchBackKey(true);
    }
}

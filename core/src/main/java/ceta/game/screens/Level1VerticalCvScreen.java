package ceta.game.screens;

import ceta.game.game.controllers.Level1VerticalCvController;
import ceta.game.game.renderers.WorldRenderer1VerticalCV;
import ceta.game.game.renderers.WorldRendererCV;
import ceta.game.util.Constants;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;

/**
 * Created by ewe on 11/22/16.
 */
public class Level1VerticalCvScreen extends Level1VerticalScreen {
    private static final String TAG = Level1VerticalCvScreen.class.getName();


    public Level1VerticalCvScreen(DirectedGame game, int levelNr,TextureAtlas.AtlasRegion  regtex) {
        super(game,levelNr,regtex);
    }

    @Override
    public void show() {
        Gdx.app.log(TAG," we start the SHOW! "+Gdx.graphics.getWidth());
        // TODO load preferences
        stage = new Stage(new FitViewport(Constants.VIEWPORT_WIDTH , Constants.VIEWPORT_HEIGHT));
        worldController = new Level1VerticalCvController(game,stage,levelJson);
        // Todo here we should make camera stuff and fitviewport
        worldRenderer = new WorldRenderer1VerticalCV(worldController,stage, false,regTex); //false because is vertical!!
        // android back key
        Gdx.input.setCatchBackKey(true);
    }
}

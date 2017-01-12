package ceta.game.screens;

import ceta.game.game.controllers.AbstractWorldController;
import ceta.game.game.levels.LevelParams;
import ceta.game.game.renderers.AbstractWorldRenderer;
import ceta.game.util.Constants;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import ceta.game.game.Assets;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Json;
import com.sun.org.apache.bcel.internal.generic.TABLESWITCH;

/**
 * Created by ewe on 7/25/16.
 */
public abstract class AbstractGameScreen  implements Screen {
    public static final String TAG = AbstractGameScreen.class.getName();
    protected DirectedGame game;
    protected Stage stage;
    protected AbstractWorldRenderer worldRenderer;
    protected AbstractWorldController worldController;
    protected int levelJson;

    public AbstractGameScreen (DirectedGame game){
        this(game,0);
    }
    public AbstractGameScreen (DirectedGame game, int levelJson) {
        this.game = game;
        this.levelJson = levelJson;
    }

    public abstract void render (float deltaTime);
    public abstract void resize (int width, int height);
    public abstract void show ();
    public abstract void hide ();
    public abstract void pause ();

    public void resume () {
        Gdx.app.log(TAG,"== resume assets instance");
        //Assets.instance.init(new AssetManager());
    }
    public void dispose () {
        Gdx.app.log(TAG,"== dispose assets instance");
        Assets.instance.dispose();
    }
    public abstract InputProcessor getInputProcessor ();

    public AbstractWorldRenderer getWorldRenderer(){
        return worldRenderer;
    }


}


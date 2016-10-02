package ceta.game.screens;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import ceta.game.game.Assets;
import com.badlogic.gdx.scenes.scene2d.Stage;

/**
 * Created by ewe on 7/25/16.
 */
public abstract class AbstractGameScreen  implements Screen {
    protected DirectedGame game;
    protected Stage stage;

    public AbstractGameScreen (DirectedGame game) {
        this.game = game;
    }

    // Subclasses must load actors in this method
    //public abstract void buildStage();

    public abstract void render (float deltaTime);
    public abstract void resize (int width, int height);
    public abstract void show ();
    public abstract void hide ();
    public abstract void pause ();

    public void resume () {
        Assets.instance.init(new AssetManager());
    }
    public void dispose () {
        Assets.instance.dispose();
    }
    public abstract InputProcessor getInputProcessor ();
}


package ceta.game.game.renderers;

import ceta.game.game.Assets;
import ceta.game.game.controllers.AbstractWorldController;
import ceta.game.util.Constants;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Disposable;

/**
 * Created by ewe on 10/19/16.
 */
public abstract class AbstractWorldRenderer implements Disposable {
    public static final String TAG = AbstractWorldRenderer.class.getName();
    protected OrthographicCamera camera;
    protected SpriteBatch spriteBatch;
    protected ShapeRenderer shapeRenderer;
    protected BitmapFont font;
    protected BitmapFont bigFont;
    protected BitmapFont counterFont;
    protected AbstractWorldController worldController;
    protected Stage stage;
    protected Image imgBackground;

    protected int levelMinimumNumber;
    protected boolean numberLineIsHorizontal;


    public abstract void init();
    public abstract void render();
    protected abstract void renderGuiScore(SpriteBatch batch);

    public void resize (int width, int height) {
        camera.viewportWidth = (Constants.VIEWPORT_HEIGHT / height) * width;
        camera.update();
        stage.getViewport().update(width, height, true);
        camera.position.set(0,0,0);
    }

    protected void renderGui (SpriteBatch batch) {
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        renderGuiScore(batch);
        batch.end();
    }

    protected void clearWhite(){
        // white
        Gdx.gl.glClearColor(1, 1, 1, 1);
        // Clears the screen
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

    protected void clearBlue(){
        // Sets the clear screen color to: Cornflower Blue
        Gdx.gl.glClearColor(0x64 / 255.0f, 0x95 / 255.0f,0xed / 255.0f, 0xff / 255.0f);
        // Clears the screen
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

    protected void renderDetectionZone(ShapeRenderer shRenderer){
        // detection zone in gray
        shRenderer.setProjectionMatrix(camera.combined);
        shRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shRenderer.setColor(Color.LIGHT_GRAY);
        shRenderer.rect(-Constants.VIEWPORT_WIDTH/2, Constants.DETECTION_LIMIT,Constants.VIEWPORT_WIDTH, -Constants.DETECTION_LIMIT);
        shRenderer.end();
    }

    protected void renderBackgroundImg(SpriteBatch batch){
        TextureAtlas.AtlasRegion b = Assets.instance.background.back2;
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        batch.draw(b.getTexture(),-Constants.VIEWPORT_WIDTH/2, 0,
                b.getRegionWidth()/2, b.getRegionHeight()/2,
                b.getRegionWidth(), b.getRegionHeight(),
                1, 1,
                0,
                b.getRegionX(), b.getRegionY(),
                b.getRegionWidth(), b.getRegionHeight(), false,false);
        batch.end();
    }


    @Override
    public void dispose() {
        spriteBatch.dispose();
        shapeRenderer.dispose();
//        font.dispose();
//        counterFont.dispose();
    }
}

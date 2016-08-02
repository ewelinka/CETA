package ceta.game.game;

import ceta.game.util.Constants;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Disposable;

/**
 * Created by ewe on 7/25/16.
 */
public class WorldRenderer implements Disposable {
    private OrthographicCamera camera;
    private OrthographicCamera cameraGUI;
    private SpriteBatch batch;
    private ShapeRenderer shapeRenderer;
    private WorldController worldController;

    public WorldRenderer (WorldController worldController) {
        this.worldController = worldController;
        init();
    }
    private void init () {
        batch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();
        camera = new OrthographicCamera(Constants.VIEWPORT_WIDTH,
                Constants.VIEWPORT_HEIGHT);
        camera.position.set(0, 0, 0);
        camera.update();

        cameraGUI = new OrthographicCamera(Constants.VIEWPORT_GUI_WIDTH, Constants.VIEWPORT_GUI_HEIGHT);
        cameraGUI.position.set(0, 0, 0);
        cameraGUI.setToOrtho(true); // flip y-axis
        cameraGUI.update();
    }

    public void render () {

        renderWorld(batch);
        renderGui(batch);
        renderHelperLines();
    }

    private void renderWorld (SpriteBatch batch) {
        worldController.cameraHelper.applyTo(camera);
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        worldController.level.render(batch);
       // renderHelperNumbers();
        batch.end();
    }
    private void renderGui (SpriteBatch batch) {
        batch.setProjectionMatrix(cameraGUI.combined);
        batch.begin();
        // draw collected gold coins icon + text
        // (anchored to top left edge)
        renderGuiScore(batch);
        batch.end();
    }

    private void renderHelperNumbers(){
        BitmapFont font = new BitmapFont();
        font.getData().setScale(0.2f);
        font.draw(batch, "0", 0, 0);
        font.draw(batch, "-30", -30, 0);
        font.draw(batch, "30", 30, 0);
    }

    public void renderHelperLines(){
        Gdx.gl.glLineWidth(1);
        shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(1, 1, 1, 1);
        for(int i = -5; i<=50;i+=5){
            //shapeRenderer.line(i, 20,i,0);
            shapeRenderer.line(-Constants.VIEWPORT_WIDTH/2 + Constants.OFFSET_X, i,
                    Constants.VIEWPORT_WIDTH/2 - Constants.OFFSET_X,i);
        }
        shapeRenderer.setColor(1, 0, 0, 1);
        shapeRenderer.line(-Constants.VIEWPORT_WIDTH/2 + Constants.OFFSET_X, 0,
                Constants.VIEWPORT_WIDTH/2 - Constants.OFFSET_X,0);
        shapeRenderer.end();

    }

    //// TODO: 7/25/16
    private void renderGuiScore (SpriteBatch batch) {};

    public void resize (int width, int height) {
        camera.viewportWidth = (Constants.VIEWPORT_HEIGHT / height) * width;
        camera.update();
        cameraGUI.viewportHeight = Constants.VIEWPORT_GUI_HEIGHT;
        cameraGUI.viewportWidth = (Constants.VIEWPORT_GUI_HEIGHT / (float)height) * (float)width;
        cameraGUI.position.set(cameraGUI.viewportWidth / 2, cameraGUI.viewportHeight / 2, 0);
        cameraGUI.update();
    }

    @Override public void dispose () {
        batch.dispose();
    }
}

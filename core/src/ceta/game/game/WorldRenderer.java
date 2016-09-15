package ceta.game.game;

import ceta.game.game.controllers.AbstractWorldController;
import ceta.game.game.controllers.Level1Controller;
import ceta.game.game.controllers.WorldController;
import ceta.game.util.Constants;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Disposable;

/**
 * Created by ewe on 7/25/16.
 */
public class WorldRenderer implements Disposable {
    private OrthographicCamera camera;
    private OrthographicCamera cameraGUI;
    private SpriteBatch batch;
    private ShapeRenderer shapeRenderer;
    private AbstractWorldController worldController;
    private Stage stage;

    private short linesRange;

    public WorldRenderer (AbstractWorldController worldController, Stage stage) {
        this.stage = stage;
        this.worldController = worldController;
        init();
    }
    private void init () {
        batch = new SpriteBatch();

        shapeRenderer = new ShapeRenderer();

        linesRange = (short)(Constants.VIEWPORT_HEIGHT/Constants.BASE/2)*Constants.BASE;


        //stage = new Stage(new FitViewport(Constants.VIEWPORT_WIDTH , Constants.VIEWPORT_HEIGHT));
        camera = new OrthographicCamera(Constants.VIEWPORT_WIDTH, Constants.VIEWPORT_HEIGHT);
        camera.setToOrtho(false,Constants.VIEWPORT_WIDTH,Constants.VIEWPORT_HEIGHT);
        camera.position.set(0,0,0);
        camera.update();
        stage.getViewport().setCamera(camera);

//        cameraGUI = new OrthographicCamera(Constants.VIEWPORT_GUI_WIDTH, Constants.VIEWPORT_GUI_HEIGHT);
//        cameraGUI.position.set(0, 0, 0);
//        cameraGUI.setToOrtho(true); // flip y-axis
//        cameraGUI.update();
    }

    public void render () {

        renderWorld(batch);
        //renderGui(batch);
        renderHelperLines();
    }



    private void renderWorld (SpriteBatch batch) {
        worldController.cameraHelper.applyTo(camera);
        //batch.setProjectionMatrix(camera.combined);
       // batch.begin();
       // stage.getBatch().draw(regTexBack.getTexture(), -Constants.VIEWPORT_WIDTH/2, 0, 600,512);
       // renderHelperNumbers();


        batch.begin();
        worldController.level.render(batch);
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



    public void renderHelperLines(){
        Gdx.gl.glLineWidth(1);
        shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(1, 1, 1, 1);



        for(int i = -linesRange; i<=linesRange;i+=Constants.BASE){
            //shapeRenderer.line(i, 20,i,0);
            shapeRenderer.line(-Constants.VIEWPORT_WIDTH/2 , i, Constants.VIEWPORT_WIDTH/2,i);
            shapeRenderer.line(i , -Constants.VIEWPORT_HEIGHT/2, i,Constants.VIEWPORT_HEIGHT/2);
        }
        shapeRenderer.setColor(1, 0, 0, 1);
        shapeRenderer.line(-Constants.VIEWPORT_WIDTH/2, 0, Constants.VIEWPORT_WIDTH/2,0);
        shapeRenderer.line(0 , -Constants.VIEWPORT_HEIGHT/2, 0,Constants.VIEWPORT_HEIGHT/2);
        // and detection limit
        shapeRenderer.setColor(0, 0, 1, 1);
        shapeRenderer.line(-Constants.VIEWPORT_WIDTH/2, Constants.DETECTION_LIMIT, Constants.VIEWPORT_WIDTH/2,Constants.DETECTION_LIMIT);

        shapeRenderer.end();
    }

    //// TODO: 7/25/16
    private void renderGuiScore (SpriteBatch batch) {};

    public void resize (int width, int height) {
//        camera.viewportWidth = (Constants.VIEWPORT_HEIGHT / height) * width;
//        camera.update();
//        cameraGUI.viewportHeight = Constants.VIEWPORT_GUI_HEIGHT;
//        cameraGUI.viewportWidth = (Constants.VIEWPORT_GUI_HEIGHT / (float)height) * (float)width;
//        cameraGUI.position.set(cameraGUI.viewportWidth / 2, cameraGUI.viewportHeight / 2, 0);
//        cameraGUI.update();

        // called from TestScreen on resize
        stage.getViewport().update(width, height, true);
        camera.position.set(0,0,0);
    }

    @Override public void dispose () {
        batch.dispose();
    }
}

package ceta.game.screens;

//import ceta.game.game.WorldController;
//import ceta.game.game.WorldRenderer;
//import ceta.game.game.levels.Level;
import ceta.game.game.LevelOneController;
import ceta.game.game.objects.Bruno;
import ceta.game.game.objects.Coin;
import ceta.game.game.objects.Latter;
import ceta.game.util.Constants;
import ceta.game.util.LatterManager;
import ceta.game.util.VirtualBlocksManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.ColorAction;
import com.badlogic.gdx.utils.viewport.FitViewport;

/**
 * Created by ewe on 7/25/16.
 */
public class LevelOneScreen  extends AbstractGameScreen {
    private static final String TAG = LevelOneScreen.class.getName();
    public Bruno bruno;
    private Coin coin;

    private OrthographicCamera camera;
    private Stage stage;
    private ShapeRenderer shapeRenderer;
    private LevelOneController levelOneController;

    // Rectangles for collision detection
    private Rectangle r1 = new Rectangle();
    private Rectangle r2 = new Rectangle();

    private boolean paused;

    public LevelOneScreen (DirectedGame game) {
        super(game);
    }

    @Override
    public void buildStage() {
//        for(Actor actor : stage.getActors()) {
//            //actor.remove();
//            actor.addAction(Actions.removeActor());
//        }
        shapeRenderer = new ShapeRenderer();

        bruno = new Bruno();
        bruno.setPosition(-Constants.VIEWPORT_WIDTH/2 + Constants.OFFSET_X ,-Constants.BASE);
        stage.addActor(bruno);

        coin = new Coin();
        coin.setPosition(300,300);
        stage.addActor(coin);

        levelOneController = new LevelOneController(stage);

    }

    @Override
    public void render(float deltaTime) {

        // Do not update game world when paused.
        if (!paused) {
            levelOneController.update();
            coin.update(deltaTime);
            stage.act(deltaTime);
        }
        // check collisions
        testCollisions();

        // Sets the clear screen color to: Cornflower Blue
        Gdx.gl.glClearColor(0x64 / 255.0f, 0x95 / 255.0f,0xed / 255.0f, 0xff / 255.0f);
        // Clears the screen
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        // Render game world to screen
        stage.draw();

        renderHelperLines();
    }

    public void renderHelperLines(){
        Gdx.gl.glLineWidth(1);
        shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(1, 1, 1, 1);
        for(int i = -500; i<=500;i+=50){
            //shapeRenderer.line(i, 20,i,0);
            shapeRenderer.line(-Constants.VIEWPORT_WIDTH/2 + Constants.OFFSET_X, i,
                    Constants.VIEWPORT_WIDTH/2 - Constants.OFFSET_X,i);
            shapeRenderer.line(i , -Constants.VIEWPORT_HEIGHT/2+ Constants.OFFSET_X,
                    i,Constants.VIEWPORT_HEIGHT/2- Constants.OFFSET_X);
        }
        shapeRenderer.setColor(1, 0, 0, 1);
        shapeRenderer.line(-Constants.VIEWPORT_WIDTH/2 + Constants.OFFSET_X, 0,
                Constants.VIEWPORT_WIDTH/2 - Constants.OFFSET_X,0);
        shapeRenderer.line(0 , -Constants.VIEWPORT_HEIGHT/2+ Constants.OFFSET_X,
                0,Constants.VIEWPORT_HEIGHT/2- Constants.OFFSET_X);
        shapeRenderer.end();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
        camera.position.set(0,0,0);
       // buildStage();// problems because from fade we called resize
    }

    @Override
    public void show() {
        stage = new Stage(new FitViewport(Constants.VIEWPORT_WIDTH , Constants.VIEWPORT_HEIGHT));

        camera = new OrthographicCamera(Constants.VIEWPORT_WIDTH, Constants.VIEWPORT_HEIGHT);
        camera.setToOrtho(false,Constants.VIEWPORT_WIDTH,Constants.VIEWPORT_HEIGHT);
        camera.position.set(0,0,0);
        camera.update();
        stage.getViewport().setCamera(camera);

        buildStage();
    }

    @Override
    public void hide() {
        stage.dispose();
    }

    @Override
    public void pause() {
        paused =true;
    }

    @Override
    public void resume () {
        super.resume();
        // Only called on Android!
        paused = false;
    }

    @Override
    public void dispose(){

        stage.dispose();
        shapeRenderer.dispose();

    }

    private void testCollisions () {
        // TODO check for top Y and x range
        // now if the coin passes by the middle if also works (make sense! but not for us!)
        Latter l = levelOneController.getLastLatter();
        if(l != null){
            r1.set(l.getX(), l.getY()+l.getHeight() - 4, l.getWidth(), 8);
            //Gdx.app.debug(TAG, "latter! ");
            r2.set(coin.getX(), coin.getY(), coin.bounds.width, coin.bounds.height);

            if (r1.overlaps(r2))
                Gdx.app.debug(TAG, "collision coin - latter! ");
        }

    }



    @Override
    public InputProcessor getInputProcessor() {
        return stage;
    }
}

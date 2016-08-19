//package ceta.game.game;
//
//import ceta.game.game.levels.Level;
//import ceta.game.game.levels.LevelOne;
//import ceta.game.game.objects.AbstractLatter;
//import ceta.game.game.objects.Bruno;
//import ceta.game.screens.DirectedGame;
//import ceta.game.util.CameraHelper;
//import ceta.game.util.Constants;
//import com.badlogic.gdx.InputAdapter;
//import com.badlogic.gdx.math.Rectangle;
//import com.badlogic.gdx.utils.Disposable;
//
//
///**
// * Created by ewe on 7/25/16.
// */
//public class WorldController extends InputAdapter implements Disposable {
//    private static final String TAG = WorldController.class.getName();
//    private DirectedGame game;
//    public CameraHelper cameraHelper;
//    public LevelOne level;
//    //public Level level;
//    // Rectangles for collision detection
//    private Rectangle r1 = new Rectangle();
//    private Rectangle r2 = new Rectangle();
//
//    public WorldController (DirectedGame game) {
//        this.game = game;
//        init();
//    }
//
//    private void init () {
//        cameraHelper = new CameraHelper();
//        initLevel();
//    }
//
//    private void initLevel(){
//        level = new LevelOne();
//
//    }
//
//    public void update (float deltaTime) {
//        level.update(deltaTime);
//
//    }
//
//    @Override
//    public void dispose() {
//
//    }
//
//
//}

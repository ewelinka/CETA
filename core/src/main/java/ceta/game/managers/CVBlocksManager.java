package ceta.game.managers;

import java.util.Set;

import org.opencv.core.Core;
import org.opencv.core.Mat;

import ceta.game.CetaGame;
import ceta.game.screens.DirectedGame;

import com.badlogic.gdx.Gdx;

import edu.ceta.vision.android.topcode.TopCodeDetectorAndroid;
import edu.ceta.vision.core.blocks.Block;

/**
 * Created by ewe on 11/22/16.
 */
public class CVBlocksManager extends AbstractBlocksManager {
    public static final String TAG = CVBlocksManager.class.getName();
    private TopCodeDetectorAndroid topCodeDetector;
    private DirectedGame game;

    public CVBlocksManager(DirectedGame game){
        this.game = game;
    }
    @Override
    public void init() {
        Gdx.app.log(TAG,"test");
        topCodeDetector = new TopCodeDetectorAndroid(50,true,70,5,true,false, false, true) ;
    }

    @Override
    public void updateDetected() {
    	Mat frame = ((CetaGame)game).getAndBlockLastFrame();
    	Core.flip(frame, frame, 0);
        Set<Block> blocks = topCodeDetector.detectBlocks(frame);
        ((CetaGame)game).releaseFrame();
        Gdx.app.log(TAG," blocks detected "+blocks.size());
        for(Block i : blocks){
            Gdx.app.log(TAG, " orientation (red) "+ i.getOrientation()+" center "+i.getCenter()+" type "+i.getType());
        }


    }
}


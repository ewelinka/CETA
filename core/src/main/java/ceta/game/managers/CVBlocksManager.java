package ceta.game.managers;

import ceta.game.CetaGame;
import ceta.game.screens.DirectedGame;
import com.badlogic.gdx.Gdx;
import edu.ceta.vision.android.topcode.TopCodeDetectorAndroid;
import edu.ceta.vision.core.blocks.Block;
import edu.ceta.vision.core.topcode.TopCodeDetector;

import java.util.ArrayList;
import java.util.Set;

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
        topCodeDetector = new TopCodeDetectorAndroid(50,true,70,5,true,false, false) ;
    }

    @Override
    public void updateDetected() {
        Set<Block> blocks = topCodeDetector.detectBlocks(((CetaGame)game).getAndBlockLastFrame());
        ((CetaGame)game).releaseFrame();
        Gdx.app.log(TAG," blocks detected "+blocks.size());
        for(Block i : blocks){
            Gdx.app.log(TAG, " orientation (red) "+ i.getOrientation()+" center "+i.getCenter()+" type "+i.getType());
        }


    }
}


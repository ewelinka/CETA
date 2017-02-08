package ceta.game.managers;

import ceta.game.game.Assets;
import ceta.game.game.objects.VirtualBlock;
import ceta.game.util.Constants;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.TimeUtils;

import java.util.ArrayList;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.delay;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.run;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;

/**
 * Created by ewe on 2/8/17.
 */
public class VirtualBlocksManagerTutorial extends VirtualBlocksManager {
    private Image hand;
    public VirtualBlocksManagerTutorial(Stage stage) {
        super(stage);
    }


    @Override
    public void init(){
        //TODO ojo que este hardcoded no es lindo
        linesRange = 6 * Constants.BASE;
        margin = 20;
        nowId = 0;
        polygon = new Polygon();
        virtualBlocksOnStage = new ArrayList<VirtualBlock>();
        resetDetectedAndRemoved();
        // polygon will be set for checks
        polygon = new Polygon();
        xSpace = 60;
        ySpace = 45;
        initBlocks();
        setWaitForFirstMove(true);
        noChangesSince = TimeUtils.millis();
        nowDetectedVals = new ArrayList<Integer>();
        nowDetectedBlocks = new ArrayList<VirtualBlock>();

        //move hand and block
        addHand();

    }

    private void addHand(){
        hand = new Image(Assets.instance.feedback.justHand);
        hand.setSize(496,454);
        hand.setPosition(-Constants.VIEWPORT_WIDTH/2,-Constants.VIEWPORT_HEIGHT/2 - hand.getHeight());
        stage.addActor(hand);

        final VirtualBlock firstToMove = getBlockByValue(1);

        hand.addAction(sequence(
                delay(2.0f),
                Actions.moveTo(firstToMove.getX()-hand.getWidth()+50,firstToMove.getY()-hand.getHeight()+50,2.0f),
                delay(1.0f),
                Actions.moveBy(200,140,1.0f),
                delay(0.3f),
                Actions.moveTo(-Constants.VIEWPORT_WIDTH/2,-Constants.VIEWPORT_HEIGHT/2 - hand.getHeight(),1.5f)

        ));
        firstToMove.addAction(sequence(
                delay(5.0f),
                Actions.moveBy(200,140,1.0f),
                run(new Runnable() {
                    @Override
                    public void run() {
                        firstToMove.setWasMoved(true);
                    }
                })

        ));


    }


}

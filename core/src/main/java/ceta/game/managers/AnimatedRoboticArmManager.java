package ceta.game.managers;

import ceta.game.game.objects.ArmPieceAnimated;
import ceta.game.util.Constants;
import ceta.game.util.Pair;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by ewe on 10/27/16.
 */

public class AnimatedRoboticArmManager extends RoboticArmManager {
    public static final String TAG = AnimatedRoboticArmManager.class.getName();
    private ArrayList<ArmPieceAnimated> armPiecesAnim;
    protected ArrayList<Short> inExpansionIds = new ArrayList<Short>();
    private float terminalDelay;
    private float currentDelayPassed;
    private final float animationSpeed = 0.3f;


    public AnimatedRoboticArmManager(Stage stage) {
        super(stage);
    }

    @Override
    public void init(){
        lastX = initialX;
        armPiecesAnim = new ArrayList<ArmPieceAnimated>();
        terminalDelay = 0;
        currentDelayPassed = 0;

    }


    public void updateAnimated(ArrayList toAdd, ArrayList<Short> toRemoveValues){
        short toAddNr = 0;
        short toRemoveNr = 0;

        if(toRemoveValues.size() > 0){
            Gdx.app.log(TAG, "-- BEFORE removeAnimatedArms "+Arrays.toString(toRemoveValues.toArray()));
            //check how many we should add
            removeAnimatedArms(toRemoveValues); // removed arms notify the manager and we update the positions
        }

        if(toAdd.size() > 0){
            // check how many we should remove
            addArms(toAdd);

        }

        // at the end we see the difference
        
    }


    protected void addArms(ArrayList<Pair> toAdd){
        terminalDelay = 0; // TODO we check if we should start in 0 or there is somenthing in movement
        for(short i=0; i< toAdd.size();i++) {
            for (short j = 0; j < toAdd.get(i).getValue(); j++) {
                Gdx.app.log(TAG, " add "+ toAdd.get(i).getKey()+" part "+j + " lastx "+lastX+" delay "+ terminalDelay);
                ArmPieceAnimated armToAdd = new ArmPieceAnimated((short) 1, this);
                stage.addActor(armToAdd);

                armToAdd.setId((short)(toAdd.get(i).getKey()*10+j));
                armToAdd.setPosition(lastX,initialY);
                lastX+=armToAdd.getWidth();

                armToAdd.expandMe(terminalDelay, animationSpeed); // will grow in 1 sec
                terminalDelay += animationSpeed;

                armPiecesAnim.add(armToAdd);
            }
        }
    }


    protected void removeFromArrayByIdAndUpdatePositions(short id){
        for(int i=armPiecesAnim.size()-1;i>=0;i--){
            if(armPiecesAnim.get(i).getId() == id){
                armPiecesAnim.remove(i); //remove from armPieces array
                break;
            }
        }
    }

    @Override
    protected void removeActorByIndex(short which){
        Gdx.app.log(TAG, "removeArmPieceByIndex "+ which + " with id "+armPiecesAnim.get(which).getId());
        armPiecesAnim.get(which).collapseMe(terminalDelay, animationSpeed); // remove Actor
    }

    public ArmPieceAnimated getLastAnimatedArmPiece(){
        if(armPiecesAnim.size()>0)
            return armPiecesAnim.get(armPiecesAnim.size()-1);
        return null;
    }

    protected void removeAnimatedArms(ArrayList<Short> toRemoveValues){
        short removeMaxIndex = (short)(armPiecesAnim.size()-1);
        short toRemoveSum = 0;

        for(short i = 0;i<toRemoveValues.size();i++) {
            // we get the value [get(i)] to remove => value 2, we remove 2 pieces
            toRemoveSum+=toRemoveValues.get(i);

        }
        terminalDelay = 0; //TODO change?
        Gdx.app.log(TAG, " toRemoveSum "+toRemoveSum);
        for(short i = removeMaxIndex;i>=0;i--) {
            // start to look!
            if(inMovementIds.contains(armPiecesAnim.get(i).getId())){
                Gdx.app.log(TAG, "we have to look for another piece, this one is busy "+armPiecesAnim.get(i).getId());
            }else{
                removeActorByIndex(i);
                lastX-=Constants.BASE; // we know that each arm part is = Constants.BASE
                toRemoveSum-=1;
                terminalDelay+=animationSpeed;
                if(toRemoveSum<=0)
                    break;
            }


        }
    }

    public void addToInExpansionIds(short id){
        inExpansionIds.add(id);

    }

    public void notificationArmExpanded(short id){
        Gdx.app.log(TAG, "BEFORE expansion "+Arrays.toString(inExpansionIds.toArray())+" with id "+id);
        inExpansionIds.remove((Object)id);
        Gdx.app.log(TAG, "AFTER expansion"+Arrays.toString(inExpansionIds.toArray())+" with id "+id);

    }

    public boolean isExpanding(){
        return inExpansionIds.size() > 0;
    }


}

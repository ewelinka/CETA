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


    public void updateAnimated(ArrayList<Pair> toAdd, ArrayList<Short> toRemoveValues){
        short toAddNr = 0;
        short toRemoveNr = 0;

        for(short i=0; i< toRemoveValues.size();i++) {
            toRemoveNr+= toRemoveValues.get(i);
        }
        for(short i=0; i< toAdd.size();i++) {
            toAddNr+=toAdd.get(i).getValue();

        }

        //Gdx.app.log(TAG,"final score: "+toRemoveNr+ " to remove and "+toAddNr+" to add!");

        // at the end we see the difference
        if((toAddNr - toRemoveNr) >= 0){
            addArms(toAdd, (short)(toAddNr - toRemoveNr)); // here we pass a

        }else{
            removeAnimatedArms((short)(Math.abs(toAddNr - toRemoveNr))); // we have to remove a positive number of pieces
        }
    }


    protected void addArms(ArrayList<Pair> toAdd, short howMany){
        terminalDelay = 0; // TODO we check if we should start in 0 or there is somenthing in movement
        short currentKey = 0; // we need it if we have to check for ids to generate new false ids

        for(short i=0; i< howMany;i++) {
            if(i>9)
                currentKey+=1;

            Gdx.app.log(TAG, " add "+ toAdd.get(currentKey).getKey()+" part "+i + " lastx "+lastX+" delay "+ terminalDelay);
            ArmPieceAnimated armToAdd = new ArmPieceAnimated((short) 1, this);
            stage.addActor(armToAdd);

            armToAdd.setId((short)(toAdd.get(currentKey).getKey()*10+i)); //we have to invent id because one virtual piece is mapped into up to 5 arm pieces
            armToAdd.setPosition(lastX,initialY);
            lastX+=armToAdd.getWidth();

            armToAdd.expandMe(terminalDelay, animationSpeed); // will grow in 1 sec
            terminalDelay += animationSpeed;

            armPiecesAnim.add(armToAdd);

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

    protected void removeAnimatedArms(short toRemoveSum){
        short removeMaxIndex = (short)(armPiecesAnim.size()-1);

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



}

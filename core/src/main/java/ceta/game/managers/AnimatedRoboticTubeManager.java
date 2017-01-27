package ceta.game.managers;

import ceta.game.game.objects.TubePieceAnimated;
import ceta.game.util.Constants;
import ceta.game.util.Pair;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;

import java.util.ArrayList;

import static com.badlogic.gdx.math.MathUtils.ceil;

/**
 * Created by ewe on 10/27/16.
 */

public class AnimatedRoboticTubeManager extends RoboticArmManager {
    public static final String TAG = AnimatedRoboticTubeManager.class.getName();
    private ArrayList<TubePieceAnimated> armPiecesAnim;
    private float terminalDelay;
    private final float animationSpeed = 0.3f;


    public AnimatedRoboticTubeManager(Stage stage) {
        super(stage);
    }

    @Override
    public void init(){
        lastX = initialX;
        armPiecesAnim = new ArrayList<TubePieceAnimated>();
        terminalDelay = 0;

    }


    public void updateAnimated(ArrayList<Pair> toAdd, ArrayList<Integer> toRemoveValues){
        int toAddNr = 0;
        int toRemoveNr = 0;

        for(int i=0; i< toRemoveValues.size();i++) {
            toRemoveNr+= toRemoveValues.get(i);
        }
        for(int i=0; i< toAdd.size();i++) {
            toAddNr+=toAdd.get(i).getValue();

        }

        //Gdx.app.log(TAG,"final score: "+toRemoveNr+ " to remove and "+toAddNr+" to add!");

        // at the end we see the difference
        if((toAddNr - toRemoveNr) >= 0){
            addArms(toAdd, toAddNr - toRemoveNr); // here we pass a

        }else{
            removeAnimatedArms(Math.abs(toAddNr - toRemoveNr)); // we have to remove a positive number of pieces
        }
    }


    protected void addArms(ArrayList<Pair> toAdd, int howMany){
        terminalDelay = 0; // TODO we check if we should start in 0 or there is somenthing in movement; change if NO action submit
        int currentKey; // we need it if we have to check for ids to generate new false ids

        for(int i=0; i< howMany;i++) {

            currentKey=ceil(i/10);
            Gdx.app.log(TAG,"current key "+currentKey+" part "+i + " lastx "+lastX+" delay "+ terminalDelay);
            Gdx.app.log(TAG, " add key: "+ toAdd.get(currentKey).getKey());
            TubePieceAnimated armToAdd = new TubePieceAnimated(1, this);
            stage.addActor(armToAdd);

            armToAdd.setId(toAdd.get(currentKey).getKey()*10+i); //we have to invent id because one virtual piece is mapped into up to 5 arm pieces
            armToAdd.setPosition(lastX,initialY);
            lastX+=armToAdd.getWidth();

            armToAdd.expandMe(terminalDelay, animationSpeed); // will grow in 1 sec
            terminalDelay += animationSpeed;

            armPiecesAnim.add(armToAdd);

        }
    }


    protected void removeFromArrayByIdAndUpdatePositions(int id){
        for(int i=armPiecesAnim.size()-1;i>=0;i--){
            if(armPiecesAnim.get(i).getId() == id){
                armPiecesAnim.remove(i); //remove from armPieces array
                break;
            }
        }
    }

    @Override
    protected void removeActorByIndex(int which){
        Gdx.app.log(TAG, "removeArmPieceByIndex "+ which + " with id "+armPiecesAnim.get(which).getId());
        armPiecesAnim.get(which).collapseMe(terminalDelay, animationSpeed); // remove Actor
    }

    public TubePieceAnimated getLastAnimatedArmPiece(){
        if(armPiecesAnim.size()>0)
            return armPiecesAnim.get(armPiecesAnim.size()-1);
        return null;
    }

    protected void removeAnimatedArms(int toRemoveSum){
        int removeMaxIndex = armPiecesAnim.size()-1;

        terminalDelay = 0; //TODO change if NO action submit
        Gdx.app.log(TAG, " toRemoveSum "+toRemoveSum);
        for(int i = removeMaxIndex;i>=0;i--) {
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

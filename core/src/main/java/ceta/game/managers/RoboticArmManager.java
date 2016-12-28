package ceta.game.managers;

import ceta.game.game.objects.ArmPiece;
import ceta.game.util.Constants;
import ceta.game.util.Pair;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.parallel;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;

/**
 * Created by ewe on 8/23/16.
 */
public class RoboticArmManager {
    public static final String TAG = RoboticArmManager.class.getName();
    protected Stage stage;

    private ArrayList<ArmPiece> armPieces;
    private ArrayList<ArmPiece> armsToAdd;
    protected ArrayList<Integer> inMovementIds = new ArrayList<Integer>();

    protected int initialX;
    protected int initialY;
    protected int lastX;

    public RoboticArmManager(Stage stage){
        this.stage = stage;
        // hardcoded
        initialX = -200;
        initialY = Constants.PRICE_Y_HORIZONTAL;

    }

    public void init(){
        lastX = initialX;
        armPieces = new ArrayList<ArmPiece>();
        armsToAdd = new ArrayList<ArmPiece>();
    }


    public void update(ArrayList<Pair> toAdd, ArrayList<Integer> toRemoveIds ){
        if(toRemoveIds.size() > 0){
            Gdx.app.debug(TAG,"to remove! so many: "+toRemoveIds.size());
            removeArms(toRemoveIds); // removed arms notify the manager and we update the positions
        }

        if(toAdd.size() > 0){
            int negativeX = addArmsFromLeftToRight(toAdd);
            updatePositionOnAdded(negativeX);
        }

    }


    protected void updatePositionsIfRemoved(){
        // we update the positions of the arm pieces that are still there
        lastX = initialX;
        for(int i=0;i<armPieces.size();i++){
            if(armPieces.get(i).getX() != lastX){
                armPieces.get(i).moveMeToAndSetTerminalX(lastX,initialY);
            }
            lastX += armPieces.get(i).getWidth();
        }
    }

    protected void updatePositionOnAdded(int negativeInitX){
        // we update the positions of the arm pieces that are still there
        float moveRight = Math.abs(negativeInitX-initialX);
        for(int i=0;i<armPieces.size();i++){
            armPieces.get(i).moveMeToAndSetTerminalX(armPieces.get(i).getTerminalX()+moveRight,armPieces.get(i).getY());
        }
        // we update the positions of new arm pieces
        int moveTo = initialX;
        for(int i=0;i<armsToAdd.size();i++) {
            armsToAdd.get(i).setPosition(negativeInitX,initialY );
            armsToAdd.get(i).moveMeToAndSetTerminalX(moveTo,initialY);

            moveTo+= armsToAdd.get(i).getWidth();
            negativeInitX+=armsToAdd.get(i).getWidth();
        }
        armsToAdd.addAll(armPieces);
        armPieces = new ArrayList(armsToAdd);
        armsToAdd.clear();
    }


    protected int addArmsFromLeftToRight(ArrayList<Pair> toAdd){
        int negativeInitX = initialX; // we reset the negativeInitX to bruno's position
        for(int i=0; i< toAdd.size();i++){
            ArmPiece armToAdd = new ArmPiece(toAdd.get(i).getValue(),this);
            armToAdd.setId(toAdd.get(i).getKey());
            negativeInitX -= armToAdd.getWidth();
            stage.addActor(armToAdd);
            armsToAdd.add(armToAdd);

        }
        return negativeInitX;
    }

    protected void removeArms(ArrayList<Integer> shouldRemoveIds){
        // we start at the end and check if the arm piece should be removed
        for(int i=armPieces.size()-1;i>=0;i--){
            if(shouldRemoveIds.contains(armPieces.get(i).getId())){
                removeActorByIndex(i);
            }
        }
    }

    private void removeOneByIndex(int which){
        armPieces.get(which).disappearAndRemove(); // remove Actor
        armPieces.remove(which); //remove from armPieces
    }

    protected void removeActorByIndex(int which){
        Gdx.app.log(TAG, "removeArmPieceByIndex "+ which + " with id "+armPieces.get(which).getId());
        armPieces.get(which).disappearAndRemove(); // remove Actor
    }

    private void removeOneById(int id){
        for(int i=armPieces.size()-1;i>=0;i--){
            if(armPieces.get(i).getId() == id){
                armPieces.get(i).disappearAndRemove(); // remove Actor
                armPieces.remove(i); //remove from armPieces array
            }
        }
    }

    protected void removeFromArrayByIdAndUpdatePositions(int id){

        for(int i=armPieces.size()-1;i>=0;i--){
            if(armPieces.get(i).getId() == id){
                armPieces.remove(i); //remove from armPieces array
                break;
            }
        }
        updatePositionsIfRemoved();
    }


    public ArmPiece getLastArmPiece(){
        if(armPieces.size()>0)
            return armPieces.get(armPieces.size()-1);
        return null;
    }

    private void removeFromInMovementIds(int id){
        Gdx.app.log(TAG, "BEFORE "+Arrays.toString(inMovementIds.toArray())+" with id "+id);
        inMovementIds.remove((Object)id);
        Gdx.app.log(TAG, "AFTER "+Arrays.toString(inMovementIds.toArray())+" with id "+id);

    }

    private void removeAllIdsFromInMovementIds(int id){
        Gdx.app.log(TAG, "ALL BEFORE "+Arrays.toString(inMovementIds.toArray())+" with id "+id);
        inMovementIds.removeAll(Collections.singleton((Object)id));
        Gdx.app.log(TAG, "ALL AFTER "+Arrays.toString(inMovementIds.toArray())+" with id "+id);

    }

    public void addToInMovementIds(int id){
        inMovementIds.add(id);

    }

    public void notificationArmGone(int armId){

        removeAllIdsFromInMovementIds(armId);
        removeFromArrayByIdAndUpdatePositions(armId);
    }

    public void notificationArmMoved(int armId){
        removeFromInMovementIds(armId);
    }

    public boolean isUpdatingArmPiecesPositions(){
        return inMovementIds.size() > 0;
    }

    public void printInMovementIds(){
        for(int i=inMovementIds.size()-1;i>=0;i--){
            Gdx.app.log(TAG, inMovementIds.get(i)+" is in movement ids");
        }
    }

    public void printArmPieces(){
        for(int i=armPieces.size()-1;i>=0;i--){
            Gdx.app.log(TAG," ARM PIECE"+i+" id "+ armPieces.get(i).getId() );
        }
    }


}

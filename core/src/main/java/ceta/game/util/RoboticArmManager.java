package ceta.game.util;

import ceta.game.game.objects.ArmPiece;
import ceta.game.game.objects.Latter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Arrays;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.parallel;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.run;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;

/**
 * Created by ewe on 8/23/16.
 */
public class RoboticArmManager {
    public static final String TAG = RoboticArmManager.class.getName();
    private Stage stage;

    private ArrayList<ArmPiece> armPieces;
    private ArrayList<ArmPiece> armsToAdd;
    private ArrayList<Short> inMovementIds = new ArrayList<Short>();

    private short initialX;
    private short initialY;
    private short lastX;

    public RoboticArmManager(Stage stage){
        this.stage = stage;
        // hardcoded
        initialX = -200;
        initialY = Constants.BASE;

    }

    public void init(){
        lastX = initialX;
        armPieces = new ArrayList<ArmPiece>();
        armsToAdd = new ArrayList<ArmPiece>();
    }


    public void update(ArrayList toAdd, ArrayList toRemove ){
        if(toRemove.size() > 0){
            Gdx.app.debug(TAG,"to remove! so many: "+toRemove.size());
            removeArms(toRemove); // removed arms notify the manager and we update the positions
        }

        if(toAdd.size() > 0){
            int negativeX = addArmsFromLeftToRight(toAdd);
            updatePositionOnAdded(negativeX);
        }

    }


    private void updatePositionsIfRemoved(){
        // we update the positions of the arm pieces that are still there
        lastX = initialX;
        for(short i=0;i<armPieces.size();i++){
            if(armPieces.get(i).getX() != lastX){
                armPieces.get(i).moveMeToAndSetTerminalX(lastX,initialY);
            }
            lastX += armPieces.get(i).getWidth();
        }
    }

    private void updatePositionOnAdded(int negativeInitX){
        // we update the positions of the arm pieces that are still there
        float moveRight = Math.abs(negativeInitX-initialX);
        for(short i=0;i<armPieces.size();i++){
            armPieces.get(i).moveMeToAndSetTerminalX(armPieces.get(i).getTerminalX()+moveRight,armPieces.get(i).getY());
        }
        // we update the positions of new arm pieces
        int moveTo = initialX;
        for(short i=0;i<armsToAdd.size();i++) {
            armsToAdd.get(i).setPosition(negativeInitX,initialY );
            armsToAdd.get(i).moveMeToAndSetTerminalX(moveTo,initialY);

            moveTo+= armsToAdd.get(i).getWidth();
            negativeInitX+=armsToAdd.get(i).getWidth();
        }
        armsToAdd.addAll(armPieces);
        armPieces = new ArrayList(armsToAdd);
        armsToAdd.clear();
    }


    private int addArmsFromLeftToRight(ArrayList<Pair<Short,Short>> toAdd){
        int negativeInitX = initialX; // we reset the negativeInitX to bruno's position
        for(short i=0; i< toAdd.size();i++){
            ArmPiece armToAdd = new ArmPiece(toAdd.get(i).getValue(),this);
            armToAdd.setId(toAdd.get(i).getKey());
            negativeInitX -= armToAdd.getWidth();
            stage.addActor(armToAdd);
            armsToAdd.add(armToAdd);

        }
        return negativeInitX;
    }

    private void removeArms(ArrayList shouldRemove){
        // we start at the end and check if the arm piece should be removed
        for(int i=armPieces.size()-1;i>=0;i--){
            if(shouldRemove.contains(armPieces.get(i).getId())){
                removeActorByIndex((short)i);
            }
        }
    }

    private void removeOneByIndex(short which){
        armPieces.get(which).disappearAndRemove(); // remove Actor
        armPieces.remove(which); //remove from armPieces
    }

    private void removeActorByIndex(short which){
        Gdx.app.log(TAG, "removeArmPieceByIndex "+ which + " with id "+armPieces.get(which).getId());
        armPieces.get(which).disappearAndRemove(); // remove Actor
    }

    private void removeOneById(short id){
        for(int i=armPieces.size()-1;i>=0;i--){
            if(armPieces.get(i).getId() == id){
                armPieces.get(i).disappearAndRemove(); // remove Actor
                armPieces.remove(i); //remove from armPieces array
            }
        }
    }

    private void removeFromArrayByIdAndUpdatePositions(short id){
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

    private void removeFromInMovementIds(short id){
        //Gdx.app.log(TAG, "BEFORE "+Arrays.toString(inMovementIds.toArray())+" with id "+id);
        inMovementIds.remove((Object)id);
        //Gdx.app.log(TAG, "AFTER "+Arrays.toString(inMovementIds.toArray())+" with id "+id);

    }

    public void addToInMovementIds(short id){
        inMovementIds.add(id);

    }

    public void notificationArmGone(short armId, boolean isMoving){
        if(isMoving){
            removeFromInMovementIds(armId); // we will interrupt a movemnt that won't notify to manager
        }
        removeFromArrayByIdAndUpdatePositions(armId);
    }

    public void notificationArmMoved(short armId){
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

}

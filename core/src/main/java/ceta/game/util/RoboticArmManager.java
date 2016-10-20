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
    //private short negativeInitX;

    private MoveToAction moveToAction;

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
            //updatePositionsIfRemoved();
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
            // TODO see how to reuse the actions
            // http://www.gamefromscratch.com/post/2014/10/28/Re-using-actions-in-LibGDX.aspx
            if(armPieces.get(i).getX() != lastX){
                inMovementIds.add(armPieces.get(i).getId()); // we monitor this piece
                armPieces.get(i).moveMeToAndSetTerminalX(lastX,initialY);
                //Gdx.app.debug(TAG," we added action to arm piece number "+i+" and moved it to "+lastY);
            }
            lastX += armPieces.get(i).getWidth();
        }
    }

    private void updatePositionOnAdded(int negativeInitX){
        // we update the positions of the arm pieces that are still there
        float moveRight = Math.abs(negativeInitX-initialX);
        for(short i=0;i<armPieces.size();i++){
            // TODO see how to reuse the actions
            // http://www.gamefromscratch.com/post/2014/10/28/Re-using-actions-in-LibGDX.aspx
            inMovementIds.add(armPieces.get(i).getId());
            armPieces.get(i).moveMeToAndSetTerminalX(armPieces.get(i).getTerminalX()+moveRight,armPieces.get(i).getY());
        }

        // we update the positions of new arm pieces
        int moveTo = initialX;
        for(short i=0;i<armsToAdd.size();i++) {
            inMovementIds.add(armsToAdd.get(i).getId());
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
            //armToAdd.setPosition(negativeInitX - armToAdd.getWidth(),initialY );
            negativeInitX -= armToAdd.getWidth();
            stage.addActor(armToAdd);
            armsToAdd.add(armToAdd);

        }
        return negativeInitX;
    }

    private void removeArms(ArrayList shouldRemove){
        // we start at the beginning and check if the arm piece should be removed
        for(int i=armPieces.size()-1;i>=0;i--){
            if(shouldRemove.contains(armPieces.get(i).getId())){
                inMovementIds.add(armPieces.get(i).getId()); // we have to now if something is going on
                //removeOneByIndex((short)i);
                removeActorByIndex((short)i);
            }
        }
    }

    private void removeOneByIndex(short which){
        // remove Actor
        armPieces.get(which).goBackAndRemove();
        //remove from armPieces
        armPieces.remove(which);

    }

    private void removeActorByIndex(short which){
        armPieces.get(which).goBackAndRemove();
    }

    private void removeOneById(short id){
        for(int i=armPieces.size()-1;i>=0;i--){
            if(armPieces.get(i).getId() == id){
                armPieces.get(i).goBackAndRemove(); // remove Actor
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
        inMovementIds.remove((Object)id);
    }

    public void notificationArmGone(short armId){
        removeFromInMovementIds(armId);
        removeFromArrayByIdAndUpdatePositions(armId);
    }

    public void notificationArmMoved(short armId){
        removeFromInMovementIds(armId);
    }

    public boolean isUpdatingArmPiecesPositions(){
        return inMovementIds.size() > 0;
    }

}

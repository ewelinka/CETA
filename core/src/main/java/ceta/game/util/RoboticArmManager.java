package ceta.game.util;

import ceta.game.game.objects.ArmPiece;
import ceta.game.game.objects.Latter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by ewe on 8/23/16.
 */
public class RoboticArmManager {
    public static final String TAG = RoboticArmManager.class.getName();
    private Stage stage;

    private ArrayList<ArmPiece> armPieces;
    private ArrayList<ArmPiece> armsToAdd;

    private short initialX;
    private short initialY;
    private short lastX;
    //private short negativeInitX;

    private MoveToAction moveToAction;

    public RoboticArmManager(Stage stage){
        this.stage = stage;
        armPieces = new ArrayList<ArmPiece>();
        armsToAdd = new ArrayList<ArmPiece>();
        // hardcoded
        initialX = -200;

        lastX = initialX;
        initialY = Constants.BASE;

    }

    public void init(){


    }


    public void update(ArrayList toAdd, ArrayList toRemove ){
        if(toRemove.size() > 0){
            Gdx.app.debug(TAG,"to remove! so many: "+toRemove.size());
            removeArms(toRemove);
            updatePositionsIfRemoved();
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
                moveToAction = new MoveToAction();
                moveToAction.setPosition(lastX,initialY);
                moveToAction.setDuration(1f);
                armPieces.get(i).addAction(moveToAction);
                armPieces.get(i).setTerminalX(lastX);
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
            moveToAction = new MoveToAction();
            moveToAction.setPosition(armPieces.get(i).getTerminalX()+moveRight,armPieces.get(i).getY());
            moveToAction.setDuration(1f);
            armPieces.get(i).addAction(moveToAction);
            armPieces.get(i).setTerminalX(armPieces.get(i).getTerminalX()+moveRight);
            Gdx.app.debug(TAG," we added action to arm piece id "+armPieces.get(i).getId()+" and moved it TO "+armPieces.get(i).getTerminalX());

        }

        // we update the positions of new arm pieces
        int moveTo = initialX;
        for(short i=0;i<armsToAdd.size();i++) {
            armsToAdd.get(i).setPosition(negativeInitX,initialY );
            moveToAction = new MoveToAction();
            moveToAction.setPosition(moveTo,initialY);
            moveToAction.setDuration(1f);
            armsToAdd.get(i).addAction(moveToAction);
            armsToAdd.get(i).setTerminalX(moveTo);

            Gdx.app.debug(TAG," we added action to arm piece id "+armsToAdd.get(i).getId()+" and moved it TO "+moveTo);

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
            Gdx.app.debug(TAG,"we add new arm piece id "+toAdd.get(i).getKey()+ " and value "+toAdd.get(i).getValue());
            ArmPiece armToAdd = new ArmPiece(toAdd.get(i).getValue());
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
                removeOneByIndex((short)i);
            }
        }
    }

    private void removeOneByIndex(short which){
        // remove Actor
        //armPieces.get(which).remove();
        armPieces.get(which).goBackAndRemove();
        //remove from armPieces
        armPieces.remove(which);

    }

    public ArmPiece getLastArmPiece(){
        if(armPieces.size()>0)
            return armPieces.get(armPieces.size()-1);
        return null;
    }
}

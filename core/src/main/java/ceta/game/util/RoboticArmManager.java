package ceta.game.util;

import ceta.game.game.objects.ArmPiece;
import ceta.game.game.objects.Latter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.MoveByAction;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;

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
    private  short [] remove;
    private  short [] add;
    private  short toRemove;
    private  short toAdd;
    private short initialX;
    private short initialY;
    private short lastX;
    private short negativeInitX;

    private MoveToAction moveToAction;
    private MoveByAction moveByAction;

    public RoboticArmManager(Stage stage){
        this.stage = stage;
        armPieces = new ArrayList<ArmPiece>();
        armsToAdd = new ArrayList<ArmPiece>();
        // hardcoded
        initialX = -200;
        negativeInitX = initialX;
        lastX = initialX;
        initialY = Constants.BASE;

    }

    public void init(){

    }


    public void update(short toRemove, short toAdd, short[] remove, short [] add){
        this.toRemove = toRemove;
        this.toAdd = toAdd;
        this.remove = remove;
        this.add = add;

        if(toRemove > 0)
            updatePositionsIfRemoved(toRemove);
        if(toAdd > 0)
            updatePositionOnAdded();
    }


    private void updatePositionsIfRemoved(short shouldRemove){
        // we have to remove something. perhaps more than one thing.

        Gdx.app.debug(TAG,"to remove! "+shouldRemove);
        removeArms(shouldRemove);

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
                //Gdx.app.debug(TAG," we added action to arm piece number "+i+" and moved it to "+lastY);
            }
            lastX += armPieces.get(i).getWidth();
        }
    }

    private void updatePositionOnAdded(){
        Gdx.app.debug(TAG,"to add! "+toAdd + Arrays.toString(add));
        negativeInitX = initialX; // we reset the negativeInitX to bruno's position

        for(short i = 0;i<add.length;i++){
            if(add[i]>0){

                // TODO here we should check if we can re-use an arm piece
                // but now we just create a new ones
                addArmsFromLeftToRight((short)(i+1),add[i]);
            }
        }
        Gdx.app.log(TAG, "negativeInitX "+negativeInitX);
        // we update the positions of the arm pieces that are still there
        for(short i=0;i<armPieces.size();i++){
            // TODO see how to reuse the actions
            // http://www.gamefromscratch.com/post/2014/10/28/Re-using-actions-in-LibGDX.aspx

            moveByAction = new MoveByAction();
            moveByAction.setAmountX(Math.abs(negativeInitX-initialX));
            moveByAction.setDuration(1f);
            armPieces.get(i).addAction(moveByAction);
            //Gdx.app.debug(TAG," we added action to arm piece number "+i+" and moved it to "+lastY);

        }

        // we update the positions of new arm pieces
        int moveTo = initialX;
        for(short i=0;i<armsToAdd.size();i++) {
            armsToAdd.get(i).setPosition(negativeInitX,initialY );
            moveToAction = new MoveToAction();
            moveToAction.setPosition(moveTo,initialY);
            moveToAction.setDuration(1f);
            armsToAdd.get(i).addAction(moveToAction);

            moveTo+= armsToAdd.get(i).getWidth();
            negativeInitX+=armsToAdd.get(i).getWidth();


        }

        armsToAdd.addAll(armPieces);
        armPieces = armsToAdd;
        armsToAdd = new ArrayList<ArmPiece>();
    }

    private void addArms(short val, short howMany){
        for(short i=0; i< howMany;i++){
            Gdx.app.debug(TAG,"we add new arm piece number "+i+ " of value "+val);
            ArmPiece armToAdd = new ArmPiece((short)(val));
            armToAdd.setPosition(lastX - armToAdd.getWidth(),initialY );
            moveToAction = new MoveToAction();
            moveToAction.setPosition(lastX,initialY);
            moveToAction.setDuration(1f);
            armToAdd.addAction(moveToAction);

            lastX += armToAdd.getWidth();
            stage.addActor(armToAdd);
            armPieces.add(armToAdd);

        }
    }

    private void addArmsFromLeftToRight(short val, short howMany){
        for(short i=0; i< howMany;i++){
            Gdx.app.debug(TAG,"we add new arm piece number "+i+ " of value "+val);
            ArmPiece armToAdd = new ArmPiece((short)(val));
            //armToAdd.setPosition(negativeInitX - armToAdd.getWidth(),initialY );
            negativeInitX -= armToAdd.getWidth();
            stage.addActor(armToAdd);
            armsToAdd.add(armToAdd);

        }
    }

    private void removeArms(short shouldRemove){
        short removed = 0;
        // we start at the end
        // if the latter that we are checking should be removed
        for(short i=(short)(armPieces.size()-1);i>=0;i--){
            // we have to adjust the value of the latter to array range
            // latter of value 1 should be in index 0
            short currentArmPieceValueIndex = (short)(armPieces.get(i).getArmValue() - 1);
            // TODO !!!! considerar aca cuantos hay que borrar

            if(remove[currentArmPieceValueIndex] > 0){
                Gdx.app.debug(TAG, "we found armPiece to remove!! index: "+currentArmPieceValueIndex+" we should remove "+remove[currentArmPieceValueIndex]);
                removeOne(i,currentArmPieceValueIndex);
                // we update removed
                removed+=1;
                // check if we should end looping
                //if(removed >= shouldRemove) Gdx.app.log(TAG, "we removed all "+shouldRemove+" armPieces");
            }
        }


    }

    private void removeOne(short which, short valueIndex){
        // remove Actor
        //armPieces.get(which).remove();
        armPieces.get(which).goBackAndRemove();
        //remove from armPieces
        armPieces.remove(which);
        // we update remove[]
        remove[valueIndex]-=1;
    }

    public ArmPiece getLastArmPiece(){
        if(armPieces.size()>0)
            return armPieces.get(armPieces.size()-1);
        return null;
    }
}

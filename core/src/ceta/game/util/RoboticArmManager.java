package ceta.game.util;

import ceta.game.game.objects.ArmPiece;
import ceta.game.game.objects.Latter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
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
    private  short [] remove;
    private  short [] add;
    private  short toRemove;
    private  short toAdd;
    private short initialX;
    private short initialY;
    private short lastX;

    private MoveToAction moveToAction;

    public RoboticArmManager(Stage stage){
        this.stage = stage;
        armPieces = new ArrayList<ArmPiece>();
        // starts where BigBruno ended
        initialX = -(short)Constants.VIEWPORT_WIDTH/2 + Constants.OFFSET_X + Constants.BASE*2;
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

        for(short i = 0;i<add.length;i++){
            if(add[i]>0){
                // TODO we should check how many should we add, perhaps there are 2 to add..
                // TODO here we should check if we can re-use an arm piece
                // but now we just create a new one
                ArmPiece armToAdd = new ArmPiece((short)(i+1));

                // TODO we should add it left and move to right all the arm pieces
                if(armPieces.size()>0){
                    Gdx.app.debug(TAG,"we add one latter at the end!");
                    armToAdd.setPosition(lastX, initialY);
                }else{
                    Gdx.app.debug(TAG,"we add the first latter ever!!");
                    armToAdd.setPosition(initialX,initialY );
                    lastX = initialX;

                }
                lastX += armToAdd.getWidth();
                stage.addActor(armToAdd);
                armPieces.add(armToAdd);
            }
        }
    }

    private void removeArms(short shouldRemove){
        short removed = 0;
        // we start at the end and check
        // if the latter that we are checking should be removed
        for(short i=(short)(armPieces.size()-1);i>=0;i--){
            // we have to adjust the value of the latter to array range
            // latter of value 1 should be in index 0
            short currentArmPieceValueIndex = (short)(armPieces.get(i).getArmValue() - 1);

            if(remove[currentArmPieceValueIndex] > 0){
                Gdx.app.debug(TAG, "we found armPiece to remove!! index: "+currentArmPieceValueIndex+" value: "+(currentArmPieceValueIndex+1));
                removeOne(i,currentArmPieceValueIndex);
                // we update removed
                removed+=1;
                // check if we should end looping
                // TODO end looping if we already deleted all latters that we should remove
                if(removed >= shouldRemove) Gdx.app.log(TAG, "we removed all "+shouldRemove+" armPieces");
            }
        }


    }

    private void removeOne(short which, short valueIndex){
        // remove Actor
        armPieces.get(which).remove();
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

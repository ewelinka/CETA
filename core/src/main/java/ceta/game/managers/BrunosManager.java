package ceta.game.managers;

import ceta.game.game.objects.BrunoVertical;
import ceta.game.util.Constants;
import ceta.game.util.Pair;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/**
 * Created by ewe on 11/7/16.
 */
public class BrunosManager {
    public static final String TAG = BrunosManager.class.getName();
    protected Stage stage;

    private ArrayList<BrunoVertical> brunos;
    private ArrayList<BrunoVertical> brunosToAdd;
    protected ArrayList<Short> inMovementIds = new ArrayList<Short>();

    protected short initialY;
    protected short lastY;

    public BrunosManager(Stage stage){
        this.stage = stage;
        // hardcoded
        initialY = 0;

    }

    public void init(){
        lastY = initialY;
        brunos = new ArrayList<BrunoVertical>();
        brunosToAdd = new ArrayList<BrunoVertical>();
    }

    public void update(ArrayList toAdd, ArrayList<Short> toRemoveIds ){
        if(toRemoveIds.size() > 0){
            Gdx.app.debug(TAG,"to remove! so many: "+toRemoveIds.size());
            removeBrunos(toRemoveIds); // removed bruno notify the manager and we update the positions
        }

        if(toAdd.size() > 0){
            int negativeY = addBrunosFromBottomToTop(toAdd);
            updatePositionOnAdded(negativeY);
        }

    }

    protected void removeBrunos(ArrayList<Short> shouldRemoveIds){
        // we start at the end and check if bruno should be removed
        for(int i=brunos.size()-1;i>=0;i--){
            if(shouldRemoveIds.contains(brunos.get(i).getId())){
                removeActorByIndex((short)i);
            }
        }
    }

    protected void removeActorByIndex(short which){
        Gdx.app.log(TAG, "removeActorByIndex "+ which + " with id "+brunos.get(which).getId());
        brunos.get(which).disappearAndRemove(); // remove Actor
    }

    protected void updatePositionOnAdded(int negativeInitY){
        // we update the positions of the brunoss that are still there
        float moveTop = Math.abs(negativeInitY-initialY);
        for(short i=0;i<brunos.size();i++){
            brunos.get(i).moveMeToAndSetTerminalY(brunos.get(i).getX(),brunos.get(i).getTerminalY()+moveTop);
        }
        // we update the positions of new brunos
        int moveTo = initialY;
        for(short i=0;i<brunosToAdd.size();i++) {
            brunosToAdd.get(i).setPosition(Constants.VERTICAL_MIDDLE_X-brunosToAdd.get(i).getWidth()/2, negativeInitY );
            brunosToAdd.get(i).moveMeToAndSetTerminalY(Constants.VERTICAL_MIDDLE_X-brunosToAdd.get(i).getWidth()/2,moveTo);

            moveTo+= brunosToAdd.get(i).getHeight();
            negativeInitY+=brunosToAdd.get(i).getHeight();
        }
        brunosToAdd.addAll(brunos);
        brunos = new ArrayList(brunosToAdd);
        brunosToAdd.clear();
    }

    protected int addBrunosFromBottomToTop(ArrayList<Pair> toAdd){
        int negativeInitY = initialY; // we reset the negativeInitY to bruno's position
        for(short i=0; i< toAdd.size();i++){
            BrunoVertical brunoToAdd = new BrunoVertical(toAdd.get(i).getValue(),this);
            brunoToAdd.setId(toAdd.get(i).getKey());
            negativeInitY -= brunoToAdd.getHeight();
            stage.addActor(brunoToAdd);
            brunosToAdd.add(brunoToAdd);

        }
        return negativeInitY;
    }

    public void addToInMovementIds(short brunoId){
        inMovementIds.add(brunoId);

    }

    public void notificationBrunoGone(short brunoId){

        removeAllIdsFromInMovementIds(brunoId);
        removeFromArrayByIdAndUpdatePositions(brunoId);
    }

    public void notificationBrunoMoved(short brunoId){
        removeFromInMovementIds(brunoId);
    }

    private void removeAllIdsFromInMovementIds(short brunoId){
        Gdx.app.log(TAG, "ALL BEFORE "+ Arrays.toString(inMovementIds.toArray())+" with id "+brunoId);
        inMovementIds.removeAll(Collections.singleton((Object)brunoId));
        Gdx.app.log(TAG, "ALL AFTER "+Arrays.toString(inMovementIds.toArray())+" with id "+brunoId);

    }

    protected void removeFromArrayByIdAndUpdatePositions(short brunoId){

        for(int i=brunos.size()-1;i>=0;i--){
            if(brunos.get(i).getId() == brunoId){
                brunos.remove(i); //remove from brunos array
                break;
            }
        }
        updatePositionsIfRemoved();
    }

    protected void updatePositionsIfRemoved(){
        // we update the positions of the brunos that are still there
        lastY = initialY;
        for(short i=0;i<brunos.size();i++){
            if(brunos.get(i).getY() != lastY){
                brunos.get(i).moveMeToAndSetTerminalY(Constants.VERTICAL_MIDDLE_X - brunos.get(i).getWidth()/2,lastY);
            }
            lastY += brunos.get(i).getHeight();
        }
    }

    private void removeFromInMovementIds(short brunoId){
        Gdx.app.log(TAG, "BEFORE "+Arrays.toString(inMovementIds.toArray())+" with id "+brunoId);
        inMovementIds.remove((Object)brunoId);
        Gdx.app.log(TAG, "AFTER "+Arrays.toString(inMovementIds.toArray())+" with id "+brunoId);

    }

    public BrunoVertical getLastBruno(){
        if(brunos.size()>0)
            return brunos.get(brunos.size()-1);
        return null;
    }

    public boolean isUpdatingBrunosPositions(){
        return inMovementIds.size() > 0;
    }

}

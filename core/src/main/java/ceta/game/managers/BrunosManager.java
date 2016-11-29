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
    protected ArrayList<Integer> inMovementIds = new ArrayList<Integer>();

    protected int initialY;
    protected int lastY;

    public BrunosManager(Stage stage){
        this.stage = stage;
        // hardcoded
        //initialY = 0;
        initialY = Constants.DETECTION_ZONE_END;

    }

    public void init(){
        lastY = initialY;
        brunos = new ArrayList<BrunoVertical>();
        brunosToAdd = new ArrayList<BrunoVertical>();
    }

    public void update(ArrayList<Pair> toAdd, ArrayList<Integer> toRemoveIds ){
        if(toRemoveIds.size() > 0){
            Gdx.app.log(TAG,"to remove! so many: "+toRemoveIds.size());
            removeBrunos(toRemoveIds); // removed bruno notify the manager and we update the positions
        }

        if(toAdd.size() > 0){
            Gdx.app.log(TAG,"to add! so many: "+toAdd.size());

            int negativeY = addBrunosFromBottomToTop(toAdd);
            updatePositionOnAdded(negativeY);
        }

    }

    protected void removeBrunos(ArrayList<Integer> shouldRemoveIds){
        // we start at the end and check if bruno should be removed
        Gdx.app.log(TAG, "we should remove "+shouldRemoveIds+" and in movement now "+Arrays.toString(inMovementIds.toArray()));
        for(int i=brunos.size()-1;i>=0;i--){
            if(shouldRemoveIds.contains(brunos.get(i).getId())){
                removeActorByIndex(i);
            }
        }
    }

    protected void removeActorByIndex(int which){
        Gdx.app.log(TAG, "removeActorByIndex "+ which + " with id "+brunos.get(which).getId());
        brunos.get(which).disappearAndRemove(); // remove Actor
    }

    protected void updatePositionOnAdded(int negativeInitY){
        // we update the positions of the brunoss that are still there
        float moveTop = Math.abs(negativeInitY-initialY);
        for(int i=0;i<brunos.size();i++){
            brunos.get(i).moveMeToAndSetTerminalY(brunos.get(i).getX(),brunos.get(i).getTerminalY()+moveTop);
        }
        // we update the positions of new brunos
        int moveTo = initialY;
        for(int i=0;i<brunosToAdd.size();i++) {
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
        for(int i=0; i< toAdd.size();i++){
            BrunoVertical brunoToAdd = new BrunoVertical(toAdd.get(i).getValue(),this);
            brunoToAdd.setId(toAdd.get(i).getKey());
            Gdx.app.log(TAG,"we will add bruno with id "+brunoToAdd.getId()+" and in movement currently "+Arrays.toString(inMovementIds.toArray()));
            negativeInitY -= brunoToAdd.getHeight();
            stage.addActor(brunoToAdd);
            brunosToAdd.add(brunoToAdd);

        }
        return negativeInitY;
    }

    public void addToInMovementIds(int brunoId){
        inMovementIds.add(brunoId);

    }

    public void notificationBrunoGone(int brunoId){

        removeAllIdsFromInMovementIds(brunoId);
        removeFromArrayByIdAndUpdatePositions(brunoId);
    }

    public void notificationBrunoMoved(int brunoId){
        removeFromInMovementIds(brunoId);
    }

    private void removeAllIdsFromInMovementIds(int brunoId){
        Gdx.app.log(TAG, "ALL BEFORE "+ Arrays.toString(inMovementIds.toArray())+" with id "+brunoId);
        inMovementIds.removeAll(Collections.singleton((Object)brunoId));
        Gdx.app.log(TAG, "ALL AFTER "+Arrays.toString(inMovementIds.toArray())+" with id "+brunoId);

    }

    protected void removeFromArrayByIdAndUpdatePositions(int brunoId){

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
        for(int i=0;i<brunos.size();i++){
            if(brunos.get(i).getY() != lastY){
                brunos.get(i).moveMeToAndSetTerminalY(Constants.VERTICAL_MIDDLE_X - brunos.get(i).getWidth()/2,lastY);
            }
            lastY += brunos.get(i).getHeight();
        }
    }

    private void removeFromInMovementIds(int brunoId){
        Gdx.app.log(TAG, "BEFORE "+Arrays.toString(inMovementIds.toArray())+" with id "+brunoId);
        inMovementIds.remove((Object)brunoId);
        Gdx.app.log(TAG, "AFTER "+Arrays.toString(inMovementIds.toArray())+" with id "+brunoId);

    }

    public BrunoVertical getLastBruno(){
        if(brunos.size()>0)
            return brunos.get(brunos.size()-1);
        return null;
    }

    public boolean inMovementHasId(int id){
        return inMovementIds.contains(id);
    }

    public boolean isUpdatingBrunosPositions(){
        return inMovementIds.size() > 0;
    }

}

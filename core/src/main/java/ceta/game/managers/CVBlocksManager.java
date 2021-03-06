package ceta.game.managers;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.parallel;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.run;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import ceta.game.util.GamePreferences;
import com.badlogic.gdx.Application;
import edu.ceta.vision.core.topcode.TopCodeDetector;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Rect;

import ceta.game.CetaGame;
import ceta.game.game.objects.VirtualBlock;
import ceta.game.screens.DirectedGame;
import ceta.game.util.Constants;
import ceta.game.util.Pair;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.ArrayMap;
import com.badlogic.gdx.utils.TimeUtils;

import edu.ceta.vision.android.topcode.ScannerAndroid;
import edu.ceta.vision.android.topcode.TopCodeDetectorAndroid;
import edu.ceta.vision.core.blocks.Block;
import edu.ceta.vision.core.utils.BlocksMarkersMap;

/**
 * Created by ewe on 11/22/16.
 */
public class CVBlocksManager extends AbstractBlocksManager {
    public static final String TAG = CVBlocksManager.class.getName();
    private TopCodeDetector topCodeDetector;
    private DirectedGame game;

    // private Set<Block> lastDetectedBlocks;
    private float actionsSpeed;
    private Stage stage;
    private ArrayList<Block> newDetectedCVBlocks;
    private ArrayList<Integer> toRemoveCVIds;
    private ArrayList<Integer> toRemoveCVValues;
    private ArrayList<Pair> newDetectedCVPairs;
    private ArrayList<Integer> oldIds;
    private ArrayList<Integer> newIds,stableIds;
    public ArrayList<Set> results = new ArrayList<Set>();
    private boolean detectionReady;
    //private long noChangesSince;
    private int noMovementDist;
    private int noMovementRot;
    private final double rotAdjust = -1;
    private ArrayMap<Integer,Integer> strikes;
    private int maxStrikes;
    private ArrayMap<Integer,Integer> idToValue;

    private Set<Block> currentBlocks;
    //public boolean waitForFirstMove;


    public CVBlocksManager(DirectedGame game, Stage stage){

        this.game = game;
        this.stage = stage;
    }
    @Override
    public void init() {
        Gdx.app.log(TAG,"in init!!");
		//FIXME Ewe --> aca va el rectangulo con la zona de deteccion
        Rect detectionZone = new Rect(Constants.CV_MIN_Y,0,480,480);
        if((Gdx.app.getType() == Application.ApplicationType.Android)) {
            topCodeDetector = new TopCodeDetectorAndroid(50, true, 70, 5, true, false, false, true, detectionZone);
        }
        // lastDetectedBlocks = new HashSet<Block>();
        actionsSpeed = 0.5f;
        noChangesSince = TimeUtils.millis();

        newDetectedCVBlocks = new ArrayList<Block>();
        newDetectedCVPairs = new ArrayList<Pair>();
        toRemoveCVIds = new ArrayList<Integer>();
        toRemoveCVValues = new ArrayList<Integer>();
        newIds = new ArrayList<Integer>();
        oldIds = stableIds = new ArrayList<Integer>();
        nowDetectedVals = new ArrayList<Integer>();
        nowDetectedBlocks = new ArrayList<VirtualBlock>();

        virtualBlocksOnStage = new ArrayList<VirtualBlock>();
        detectionReady = false;

        noMovementDist = Constants.NO_MOVEMENT_DIST;
        noMovementRot = 20;
        setWaitForFirstMove(true);

        maxStrikes = 3; // after x frames without marker, we pronounce it deleted
        strikes = new ArrayMap<Integer,Integer>();
        idToValue = new ArrayMap<Integer,Integer>();
        initStrikesAndBlocksValues();

        currentBlocks = new HashSet<Block>();

    }

    
	@Override
    public void updateDetected() {
        if((Gdx.app.getType() == Application.ApplicationType.Android)) {
            new Thread(new Runnable() {
                public void run() {
                    Mat frame = ((CetaGame) game).getAndBlockLastFrame();
                    Core.flip(frame, frame, 0);

                    final Set<Block> finalSet = ((TopCodeDetectorAndroid) topCodeDetector).detectBlocks(frame, GamePreferences.instance.getFvalue());
                    //final Set<Block> finalSet = topCodeDetector.detectBlocks(((CetaGame) game).getAndBlockLastFrame());
                    // Gdx.app.log(TAG, "ready with the detection!! framerateee"+Gdx.graphics.getFramesPerSecond());
                    detectionReady = true;
                    ((CetaGame) game).releaseFrame();

                    Gdx.app.postRunnable(new Runnable() {
                        @Override
                        public void run() {
                            // process the result, e.g. add it to an Array<Result> field of the ApplicationListener.
                            results.clear();
                            results.add(finalSet);
                        }
                    });
                }
            }).start();
        }
        else{
            //PC case
        }
    }

    public void analyseDetected(){
        //Set<Block> currentBlocks = topCodeDetector.detectBlocks(((CetaGame)game).getAndBlockLastFrame());
        if(detectionReady) {
            if(results.size() > 0) {
                currentBlocks = results.get(0);
            }
            else {
                Gdx.app.error(TAG," very very wrong -> empty result!");
                currentBlocks =  new HashSet<Block>();
            }

            oldIds = new ArrayList(newIds);
            stableIds = new ArrayList(newIds);
            newIds.clear();
            newDetectedCVBlocks.clear();
            nowDetectedVals.clear();
            nowDetectedBlocks.clear();


            for (Block i : currentBlocks) {
               // Gdx.app.log(TAG, " orientation (radians) " + i.getOrientation() + " center " + i.getCenter() + " type " + i.getType() + " id " + i.getId());
                newIds.add(i.getId());
                newDetectedCVBlocks.add(i);
                nowDetectedVals.add(i.getValue());
                nowDetectedBlocks.add(getBlockById(i.getId()));
            }
            Gdx.app.log(TAG, "blocks detected " + currentBlocks.size() + " new ids " + Arrays.toString(newIds.toArray()) + " old: " + Arrays.toString(oldIds.toArray()));

            compareIds(newIds, oldIds);
            detectionReady = false;
        }
    }


    private void compareIds(ArrayList<Integer> newBlocksIds, ArrayList<Integer> oldBlocksIds){
        for(int i = newBlocksIds.size()-1;i>=0;i--){ // we start from the end to avoid ids problems
            Block nBlock = newDetectedCVBlocks.get(i);
            int newId = newBlocksIds.get(i);
            if(oldBlocksIds.contains(newId)){
                resetStrikes(newId);
                boolean shouldBeUpdated = false;
                if(getDistance(newId,newDetectedCVBlocks.get(i)) > noMovementDist){
                    //Gdx.app.log(TAG,"distance > "+noMovementDist);
                    shouldBeUpdated = true;
                }

                //Vector2(shouldRotateTo,actualDiff)
                Vector2 rotateInfo = calculateRotation(newDetectedCVBlocks.get(i).getOrientation(),getBlockById(newId).getRotation());
                if(rotateInfo.y > noMovementRot){
                  //  Gdx.app.log(TAG,"rotation > "+noMovementRot+" now:  "+radianToStage(newDetectedCVBlocks.get(i).getOrientation())+" before: "+getBlockById(newId).getRotation());
                    shouldBeUpdated =true;
                }

                if(shouldBeUpdated) {
                    updateBlockAndLieCV(nBlock.getId(),
                            xToStage(nBlock.getCenter().y),
                            yToStage(nBlock.getCenter().x),
                            radianToStage(nBlock.getOrientation()),
                            rotateInfo.x
                    );
                }
            }else {
                addBlockCV(nBlock.getValue(),
                        nBlock.getId(),
                        xToStage(nBlock.getCenter().y), //important!! x and y flipped!!
                        yToStage(nBlock.getCenter().x),
                        radianToStage(nBlock.getOrientation()));
                resetStrikes(nBlock.getId());

            }
        }
        // we won't use more oldBlocksIds so we use it to get unique ids that should be removed
        oldBlocksIds.removeAll(newBlocksIds);
        for(int i=0;i<oldBlocksIds.size();i++){
            checkStrikesAndDecideIfRemove(oldBlocksIds.get(i));
        }
    }

    private Vector2 calculateRotation(float nowRadianRotation, float previousRotation){
        float nowRotation = radianToStage(nowRadianRotation);
        float diff = nowRotation - previousRotation;
        float shouldRotateTo = nowRotation;
        float actualDiff = Math.abs(diff);

        if(Math.abs(diff)>180){
            if(diff < 0){
                shouldRotateTo = 360 + nowRotation;
            }
            else{
                shouldRotateTo = -360 + nowRotation;
            }
            actualDiff = 360 - Math.abs(diff);
        }

       // Gdx.app.log(TAG,"calculateRotation: nowRot "+nowRotation+" before "+previousRotation+ " shouldRotateTo "+shouldRotateTo+" actualDiff "+actualDiff);
        return new Vector2(shouldRotateTo,actualDiff);

    }

    private void checkStrikesAndDecideIfRemove(int id){
        if(strikes.get(id) > maxStrikes ) {
            Gdx.app.log(TAG, " remove block with id: " + id + "because its gone and has max strikes!");
            removeBlockCV(id, idToValue.get(id));
        }
        else{
            Gdx.app.log(TAG," STRIKE for "+id);
            //update strikes
            updateStrikes(id);
            // add to new
            newIds.add(id);
        }
    }

    private float map(float x, float in_min, float in_max, float out_min, float out_max)
    {
        return (x - in_min) * (out_max - out_min) / (in_max - in_min) + out_min;
    }

    private float xToStage(double x){
        //Gdx.app.log(TAG, " x "+x+" converted to "+map((float)x,0,480,-Constants.CV_DETECTION_EDGE_TABLET/2, Constants.CV_DETECTION_EDGE_TABLET));
        return map((float)x,0,480,-Constants.CV_DETECTION_EDGE_TABLET/2, Constants.CV_DETECTION_EDGE_TABLET/2);
        // return (float)(x-240);


    }

    private float yToStage(double y){
        return  map((float)y,0,640-Constants.CV_MIN_Y,-Constants.VIEWPORT_HEIGHT/2,-Constants.VIEWPORT_HEIGHT/2+Constants.CV_DETECTION_EDGE_TABLET);
    }

    private float radianToStage(double r){
       // Gdx.app.log(TAG," radians to stage "+r);
        float d = (float) Math.toDegrees(r*rotAdjust - Math.PI/2);
        if(d < 0)
            d = 360 + d;
        return d;
    }


    private VirtualBlock getBlockById(int id){
        for(int i=0;i<virtualBlocksOnStage.size();i++){
            if(virtualBlocksOnStage.get(i).getBlockId() == id)
                return virtualBlocksOnStage.get(i);
        }
        return null;
    }


    private void updateBlockCV(int id, float px, float py, float rot){
        Gdx.app.log(TAG,"we should update id: "+id+" rot "+rot);
        VirtualBlock vBlock = getBlockById(id);
        vBlock.clearActions();
        vBlock.addAction(parallel(
                Actions.moveTo(px-vBlock.getWidth()/2,//px. is a center!! adjust!!
                        py-vBlock.getHeight()/2,actionsSpeed), // p.y is a center!! adjust!!
                Actions.rotateTo(rot,actionsSpeed)
        ));
        noChangesSince = TimeUtils.millis();
    }

    private void updateBlockAndLieCV(int id, float px, float py, final float rotDegreesNow, float shouldRotateTo){
        final VirtualBlock vBlock = getBlockById(id);
        Gdx.app.log(TAG,"updateBlockAndLieCV: rotDegreesNow "+rotDegreesNow+" shouldRotateTo "+shouldRotateTo);

        if(!vBlock.hasActions()) {
            vBlock.addAction(sequence(
                    parallel(
                            Actions.moveTo(px - vBlock.getWidth() / 2,//px. is a center!! adjust!!
                                    py - vBlock.getHeight() / 2, actionsSpeed), // p.y is a center!! adjust!!
                            Actions.rotateTo(shouldRotateTo, actionsSpeed)
                    ),
                    run(new Runnable() {
                        @Override
                        public void run() {
                            vBlock.setRotation(rotDegreesNow);
                        }
                    })
            ));
        }
        noChangesSince = TimeUtils.millis();
    }


    private void removeBlockCV(int id, int val){
        //TODO check if its in "to add" blocks
        boolean inDetected = false;
        // check if its not waiting to be added, add+remove = 0!
        for(int i =0; i<newDetectedCVPairs.size();i++){
            Gdx.app.log(TAG,"removeBlockCV id to remove "+id + " key in Pairs: "+newDetectedCVPairs.get(i).getKey());
            if(newDetectedCVPairs.get(i).getKey() == id){
                newDetectedCVPairs.remove(i); // remove from "new detected" and we do nota add to toRemove!!
                inDetected = true;
                break;
            }
        }
        if(!inDetected) {
            Gdx.app.log(TAG,"removeBlockCV id to not in new detected so we will remove!!");
            blockRemovedWithIdAndValue(id, val);

        }
        // if nothing to add and nothing to remove -> no AS
        if(needAS()) {
            setWaitForFirstMove(false);

        }else{
            setWaitForFirstMove(true);
        }
        noChangesSince = TimeUtils.millis();
        removeFromStageById(id); // we remove it from detection zone/stage
    }

    protected void removeFromStageById(int whichId){
        // remove Actor
        Gdx.app.log(TAG,"we should remove id: "+whichId+" from stage");
        for(int i=0;i<virtualBlocksOnStage.size();i++){
            // Gdx.app.log(TAG,"we are checking block "+i+" id: "+virtualBlocksOnStage.get(i).getBlockId());
            if(virtualBlocksOnStage.get(i).getBlockId() == whichId){
                //remove actor
                virtualBlocksOnStage.get(i).disappearAndRemove();
                // remove from array
                virtualBlocksOnStage.remove(i);
                //return;
            }
        }

    }

    @Override
    public void addBlockWithId(int val, int id){
        Gdx.app.log(TAG, "!!! addBlockWithId id:" + id);
        //TODO if is also to remove, what should i do? ===> fixme!!!
        newDetectedCVPairs.add(new Pair(id,val));
    }

    public void addBlockCV(int blockToAddVal, int id, float px, float py, float rot) {

        // TODO check if its not waiting to be added, add+remove = 0!
        if(toRemoveCVIds.contains(id)){
            Gdx.app.log(TAG, "addBlockCV but we had it in to remove id:" + id);
            toRemoveCVIds.remove((Object)id);
            toRemoveCVValues.remove((Object)blockToAddVal);
        }else {
            Gdx.app.log(TAG, "addBlockCV, setting block id to " + id + " and rotation: " + rot);
            addBlockWithId(blockToAddVal, id); // add to manager! will be checked by brunos manager
        }

        VirtualBlock vBlock = new VirtualBlock(blockToAddVal, 30, true); // warning 30px!
        stage.addActor(vBlock);

        vBlock.setWasDetected(true);
        vBlock.setBlockId(id);
        vBlock.setPosition(px - vBlock.getWidth() / 2, py - vBlock.getHeight() / 2); // transform center to left bottom corner
        vBlock.setRotation(rot);
        vBlock.setMyAlpha(0);

        vBlock.addAction(Actions.alpha(1, actionsSpeed));// we should appear!!
        virtualBlocksOnStage.add(vBlock);

        if(needAS()) {
            setWaitForFirstMove(false);

        }else{
            setWaitForFirstMove(true);
        }
        noChangesSince = TimeUtils.millis(); //new change!
        //setWaitForFirstMove(false);
    }

    protected void removeFromStageByIndex(int index){
        // remove Actor
        Gdx.app.log(TAG,"we should remove index: "+index);

        virtualBlocksOnStage.get(index).disappearAndRemove();


    }

    @Override
    public ArrayList<Pair> getNewDetected(){
        newDetectedPairs = new ArrayList(newDetectedCVPairs);
        newDetectedCVPairs.clear();

        return new ArrayList(newDetectedPairs);
    }

    @Override
    public ArrayList getToRemove(){
        toRemoveFromDetectedIds = new ArrayList(toRemoveCVIds);
        toRemoveCVIds.clear();
        return toRemoveFromDetectedIds;
    }

    @Override
    public ArrayList<Integer> getToRemoveValues(){
        toRemoveFromDetectedValues = new ArrayList(toRemoveCVValues);
        toRemoveCVValues.clear();
        toRemoveCVIds.clear();
        return toRemoveFromDetectedValues;
    }


    @Override
    public void blockRemovedWithIdAndValue(int id, int value){
            toRemoveCVIds.add(id);
            toRemoveCVValues.add(value);
    }

    public boolean isDetectionReady(){
        return detectionReady;
    }



    private float getDistance(int newId, Block block){
        try {
            Vector2 v1 = getBlockById(newId).getCenterVector(); // get block position on stage
            Vector2 v2 =  new Vector2(
                    xToStage(block.getCenter().y),
                    yToStage(block.getCenter().x));
            //Gdx.app.log(TAG," distance "+v1.dst(v2));
            return v1.dst(v2);
        }catch(java.lang.NullPointerException e){
            for (int i = 0 ; i<virtualBlocksOnStage.size();i++)
                Gdx.app.error(TAG, " virtualBlocksOnStage i "+i+" id "+ virtualBlocksOnStage.get(i).getBlockId());

            return 0.0f;
        }


    }


    private void initStrikesAndBlocksValues(){
        int [][] allMarkers = {BlocksMarkersMap.block1,BlocksMarkersMap.block2,BlocksMarkersMap.block3,BlocksMarkersMap.block4,BlocksMarkersMap.block5};

        for(int arrIdx = 0;arrIdx < allMarkers.length; arrIdx++){
            for(int i =0; i< allMarkers[arrIdx].length;i++){
                //Gdx.app.log(TAG, " putting "+allMarkers[arrIdx][i]);
                strikes.put(allMarkers[arrIdx][i],0);
                idToValue.put(allMarkers[arrIdx][i],arrIdx+1);
            }
        }

        Gdx.app.log(TAG," idToVal "+idToValue);

    }

    public ArrayList<Integer> getStableIds(){
        return stableIds;
    }

    private void resetStrikes(int id){
        strikes.put(id,0); // reset strikes!
    }

    private void updateStrikes(int id){
        strikes.put(id,strikes.get(id)+1); // add one!
    }

    private boolean needAS(){
        Gdx.app.log(TAG,"needAS??? "+toRemoveCVIds.size()+" "+newDetectedCVPairs.size());
        return (toRemoveCVIds.size()>0 || newDetectedCVPairs.size()>0);
    }

    public TopCodeDetector getTopCodeDetector(){
        return topCodeDetector;
    }

}


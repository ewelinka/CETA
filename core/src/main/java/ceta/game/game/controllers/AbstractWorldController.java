package ceta.game.game.controllers;

import ceta.game.game.Assets;
import ceta.game.game.levels.AbstractLevel;
import ceta.game.game.levels.LevelParams;
import ceta.game.game.objects.*;
import ceta.game.screens.DirectedGame;
import ceta.game.screens.CongratulationsScreen;
import ceta.game.screens.MenuScreen;
import ceta.game.transitions.ScreenTransition;
import ceta.game.transitions.ScreenTransitionFade;
import ceta.game.util.*;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.Json;

import java.util.ArrayList;


/**
 * Created by ewe on 8/23/16.
 */
public abstract class  AbstractWorldController extends InputAdapter implements Disposable {
    private static final String TAG = AbstractWorldController.class.getName();
    protected Rectangle r1 = new Rectangle();
    protected Rectangle r2 = new Rectangle();
    protected Stage stage;
    public CameraHelper cameraHelper;
    public AbstractLevel level;
    public int score;
    public DirectedGame game;
    protected boolean countdownOn;
    protected float countdownCurrentTime;
    private ScreenTransition oneSegFadeIn;
    protected LevelParams levelParams;
    protected boolean playerInactive;
    protected float timeLeftScreenFinishedDelay;
    protected boolean screenFinished;
    protected boolean moveMade;
    private int localCountdownMax;
    protected int currentErrors;
    protected boolean isTooMuch;
    protected int timeToWait;
    protected float timeToWaitForReading;
    protected boolean tableCleaned;




    public AbstractWorldController(DirectedGame game, Stage stage, int levelJson) {
        this.game = game;
        this.stage = stage;

        levelParams = getLevelParams(levelJson);
        AudioManager.instance.setStage(stage);

        if(GamePreferences.instance.actionSubmit) {
            timeToWait = Constants.ACTION_SUBMIT_WAIT;
            Gdx.app.log(TAG, " time to wait!!! "+timeToWait);
        }
        else {
            timeToWait = 0;
            Gdx.app.log(TAG, "no time to wait!!!");
        }
        timeToWaitForReading = 0;

        init();
        localInit();
        newPriceRegister();
    }


    public abstract void update(float delta);
    protected abstract void testCollisionsInController(boolean isDynamic);
    protected abstract void readDetectedSaveIntentAndLastSolution();


    protected abstract void localInit();
    protected abstract void updateDigitalRepresentations();
    protected abstract void countdownMove();
    public abstract boolean isPlayerInactive();


    public void init () {

        cameraHelper = new CameraHelper();
        cameraHelper.setTarget(null);
        score = 0;
        oneSegFadeIn = ScreenTransitionFade.init(1);
        playerInactive = false;
        timeLeftScreenFinishedDelay = 0;
        moveMade = false;
        //localCountdownMax = GamePreferences.instance.countdownMax;
        localCountdownMax = Constants.COUNTDOWN_MAX;
        currentErrors = 0;
        isTooMuch = false;
        tableCleaned = true;

        actionSubmitInit();
        adjustCamera();
    }

    protected void testCollisions(){
       // Gdx.app.log(TAG," testCollisions with price dynamic: "+level.price.isDynamic());
        if(level.price.isDynamic()) {
            testCollisionsInController(true);
        }
        else {
            if(moveMade)
                testCollisionsInController(false);
        }
    }

    private void adjustCamera(){
        if (Gdx.app.getType() == Application.ApplicationType.Android){
            // moveCamera(0, -512/2);
            // cameraHelper.addZoom(-0.3f);
        }
    }

    private void actionSubmitInit(){
        setCountdownOn(false);
        countdownCurrentTime = localCountdownMax;
    }


    @Override
    public boolean keyUp (int keycode) {
        // Reset game world
        if (keycode == Input.Keys.R) {
            setCountdownOn(true);
            Gdx.app.debug(TAG, "Action submit");
        }
        else if(keycode == Input.Keys.N){
           // GamePreferences.instance.addOneToLastLevelAndSave();
            game.getLevelsManager().goToNextLevelWorkaround();
        }

        else if(keycode == Input.Keys.B){
//            GamePreferences.instance.subtractOneToLastLevelAndSave();
//            game.getLevelsManager().goToNextLevel();
            game.getLevelsManager().goToPreviousLevelWorkaround();
        }
        else if(keycode == Input.Keys.C){
            goToCongratulationsScreen();
        }
        // Toggle camera follow
        else if (keycode == Input.Keys.ENTER) {
            cameraHelper.setTarget(cameraHelper.hasTarget() ? null: level.bruno);
            Gdx.app.debug(TAG, "Camera follow enabled: " + cameraHelper.hasTarget());
        }
        // Back to Menu
        else if (keycode == Input.Keys.ESCAPE || keycode == Input.Keys.BACK) {
            Gdx.app.log(TAG,"back key = go to menu!");
            backToMenu();
        }
        return false;
    }

    public void backToMenu () {
        // switch to menu screen
       // ScreenTransition transition = ScreenTransitionFade.init(1);
        game.setScreen(new MenuScreen(game), oneSegFadeIn);
    }

    public void goToCongratulationsScreen () {
        game.getLevelsManager().onLevelCompleted();
        game.setScreen(new CongratulationsScreen(game), oneSegFadeIn);

    }

    public void handleDebugInput (float deltaTime) {
        if (Gdx.app.getType() != Application.ApplicationType.Desktop) return;
        if (!cameraHelper.hasTarget(level.bruno)) {
            // Camera Controls (move)
            float camMoveSpeed = 100 * deltaTime;

            if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) moveCamera(-camMoveSpeed, 0);
            if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) moveCamera(camMoveSpeed, 0);
            if (Gdx.input.isKeyPressed(Input.Keys.UP)) moveCamera(0, camMoveSpeed);
            if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) moveCamera(0, -camMoveSpeed);
            // default
            if (Gdx.input.isKeyPressed(Input.Keys.BACKSPACE)) cameraHelper.setPosition(0, 0);
        }
        // Camera Controls (zoom)
        float camZoomSpeed = 1 * deltaTime;
        // mas lejos estamos
        if (Gdx.input.isKeyPressed(Input.Keys.COMMA)) cameraHelper.addZoom(camZoomSpeed);
        // mas cerca
        if (Gdx.input.isKeyPressed(Input.Keys.PERIOD)) cameraHelper.addZoom(-camZoomSpeed);
        // default zoom
        if (Gdx.input.isKeyPressed(Input.Keys.SLASH)) cameraHelper.setZoom(1);
    }


    protected void testCollisionsHorizontalStatic(AbstractGameObject objectToCheck){
        if(objectToCheck != null ) {
            r1.set(objectToCheck.getX() + objectToCheck.getWidth() - 2,
                    objectToCheck.getY(), // two pixels below the middle
                    4, objectToCheck.getHeight());
            r2.set(level.price.getX(),
                    level.price.getY(),
                    level.price.getWidth()/2, level.price.getHeight()/2);

            if (r1.overlaps(r2)) {
                onCollisionBrunoWithPrice(level.price);
                moveMade = false;
            } else {
                if (moveMade) {
                    AudioManager.instance.play(Assets.instance.sounds.liveLost);
                    moveMade = false;
                }

            }
        }else{
            if (moveMade) {
                AudioManager.instance.play(Assets.instance.sounds.liveLost);
                moveMade = false;
            }
        }
    }

    protected void testCollisionsHorizontalDynamic(AbstractGameObject objectToCheck){
        //TODO how we know about error or win??? the price is moving!!!
        //Gdx.app.log(TAG, " testCollisionsHorizontalDynamic ");
        if(objectToCheck != null ) {
            r1.set(objectToCheck.getX() + objectToCheck.getWidth() - 2,
                    objectToCheck.getY(), // two pixels below the middle
                    4, 4);
            r2.set(level.price.getX(),
                    level.price.getY(),
                    level.price.getWidth() / 2, level.price.getHeight() / 2);

            if (r1.overlaps(r2)) {
                onCollisionBrunoWithPrice(level.price);
                moveMade = false;
            }
            else{
                //TODO check if the price number and number line position ==
                // if == -> its a good answer
                // if not -> error
            }
        }

    }

    protected void testCollisionsVerticalStatic(BrunoVertical objectToCheck){
        if(objectToCheck != null ) {
            r1.set(objectToCheck.getX() ,
                    objectToCheck.getY()+ objectToCheck.getHeight() - 4, // two pixels below the middle
                    objectToCheck.getWidth()+Constants.PRICE_X_OFFSET, 4);
            r2.set(level.price.getX(),
                    level.price.getY(),
                    level.price.getWidth()/2, level.price.getHeight()/2);

            if (r1.overlaps(r2)) {
                onCollisionBrunoWithPriceVertical(level.price, objectToCheck);
                moveMade = false;
            } else {
                if (moveMade) {
                    AudioManager.instance.playWithoutInterruption(Assets.instance.sounds.liveLost);
                    moveMade = false;
                }

            }
        }else{
            if (moveMade) {
                AudioManager.instance.playWithoutInterruption(Assets.instance.sounds.liveLost);
                moveMade = false;
            }
        }
    }

    protected void testCollisionsVerticalDynamic(BrunoVertical objectToCheck){
        if(objectToCheck != null ) {
            r1.set(objectToCheck.getX() ,
                    objectToCheck.getY()+ objectToCheck.getHeight()/2 - 4, // two pixels below the middle
                    objectToCheck.getWidth(), 4);
            r2.set(level.price.getX(),
                    level.price.getY(),
                    level.price.getWidth()/2, level.price.getHeight()/2);

            if (r1.overlaps(r2)) {
                onCollisionBrunoWithPriceVertical(level.price, objectToCheck);
                moveMade = false;
            }
        }
    }


    private void moveCamera (float x, float y) {
        x += cameraHelper.getPosition().x;
        y += cameraHelper.getPosition().y;
        cameraHelper.setPosition(x, y);
    }

    protected void onCollisionBrunoWithPrice(Price price) {
        //Gdx.app.log(TAG, "NO updates in progress and collision!");
        if (price.getActions().size == 0) { // we act just one time!
            AudioManager.instance.playWithoutInterruption(Assets.instance.sounds.pickupPrice);
            score += 1;
            //TODO some nice yupi animation
            if (score < levelParams.operationsNumberToPass) {
                price.wasCollected();
                newPriceRegister();

            } else {
                screenFinished = true;
                price.lastCollected();
                timeLeftScreenFinishedDelay = Constants.TIME_DELAY_SCREEN_FINISHED;
            }
            game.resultsManager.setLastToFinal();
        }
    }

    protected void  onCollisionBrunoWithPriceVertical(Price price, Bruno bruno){
       // Gdx.app.log(TAG, "NO updates in progress and collision!");
        if (price.getActions().size == 0) { // we act just one time!
            bruno.moveHead();
            AudioManager.instance.playWithoutInterruption(Assets.instance.sounds.pickupPrice);
            score += 1;
            //TODO some nice yupi animation
            if (score < levelParams.operationsNumberToPass) {
                Gdx.app.log(TAG,"=== to eat bruno x"+bruno.getX()+" bruno y "+bruno.getEatPointY()+" price x "+price.getX()+" y "+price.getY());
                price.wasEaten(bruno.getX(), bruno.getEatPointY());
                newPriceRegister();

            } else {
                screenFinished = true;
                price.lastEaten(bruno.getX(), bruno.getEatPointY());
                timeLeftScreenFinishedDelay = Constants.TIME_DELAY_SCREEN_FINISHED;
            }
            game.resultsManager.setLastToFinal();
        }
    }

    protected void onCollisionBrunoWithPriceHorizontal3(Price price, Bruno bruno){
        // Gdx.app.log(TAG, "NO updates in progress and collision!");
        if (price.getActions().size == 0) { // we act just one time!
            bruno.moveHead();
            AudioManager.instance.playWithoutInterruption(Assets.instance.sounds.pickupPrice);
            score += 1;
            //TODO some nice yupi animation
            if (score < levelParams.operationsNumberToPass) {
                Gdx.app.log(TAG,"=== to eat "+bruno.getX()+" eat y "+bruno.getEatPointY());
                price.wasEatenHorizontal(bruno.getX(), bruno.getEatPointY());
                newPriceRegister();
            } else {
                screenFinished = true;
                price.lastEaten(bruno.getX(), bruno.getEatPointY());
                timeLeftScreenFinishedDelay = Constants.TIME_DELAY_SCREEN_FINISHED;
            }
            game.resultsManager.setLastToFinal();
        }
    }



    public boolean getCountdownOn(){
        return countdownOn;
    }

    public float getCountdownCurrentTime(){
        return countdownCurrentTime;
    }

    public void setCountdownOn(boolean isOn){
        //Gdx.app.log(TAG, " setCountdownOn "+isOn);
        if(isOn) {
            AudioManager.instance.play(Assets.instance.sounds.buzz);
        }
        else {
            AudioManager.instance.stopSound();
        }
        countdownOn = isOn;
        countdownCurrentTime = localCountdownMax;
    }



    public void resetScore(){
        score = 0;
    }


    protected LevelParams getLevelParams(int levelNr){
        Json json = new Json();
        return json.fromJson(LevelParams.class, Gdx.files.internal(Constants.LEVELS_FOLDER+"/"+levelNr+".json"));
    }

    public int getOperationsNumberToPass(){
        return levelParams.operationsNumberToPass;
    }


    public int getMinimumNumber(){ return levelParams.numberMin;}

    public boolean isNumberLineVisible(){ return levelParams.visibleNumberLine;}



    public void setPlayerInactive(boolean isInactive){
        playerInactive = isInactive;
    }



    @Override public boolean touchDown (int screenX, int screenY, int pointer, int button) {
        // ignore if its not left mouse button or first touch pointer
        if(screenY < 150) {
            game.getLevelsManager().goToNextLevelWorkaround();
        }

        return true;
    }

    public void globalUpdate(float deltaTime){
        if(screenFinished) {
            //Gdx.app.log(TAG, "SCREEN FINISHED! "+timeLeftScreenFinishedDelay);
            timeLeftScreenFinishedDelay -= deltaTime;
            if (timeLeftScreenFinishedDelay < 0)
                goToCongratulationsScreen();
        }
        else{
            testCollisions(); // winning condition checked
        }

        handleDebugInput(deltaTime);
        level.update(deltaTime); //stage.act()
    }

    public boolean isTimeForReadOver(float deltaTime){
        timeToWaitForReading-=deltaTime;
        if(timeToWaitForReading<0)
            return true;
        else
            return false;

    }

    public boolean isReadOver(){
        return timeToWaitForReading<0;
    }



    protected void addIntentToResults(int kidResponse, int priceValue){
        boolean wasSuccessful = (kidResponse == priceValue);
        isTooMuch = (kidResponse > priceValue);
        game.resultsManager.addIntent(wasSuccessful, kidResponse,priceValue);
        errorCheck(wasSuccessful);
    }

    private void errorCheck(boolean wasSuccessful){
        if(wasSuccessful)
            currentErrors = 0;
        else
            currentErrors +=1;

        Gdx.app.log(TAG, " new intent with result: "+wasSuccessful+ " we have now "+currentErrors+" errors acumulated, is too much:  "+isTooMuch);
    }

    protected void newPriceRegister(){
        game.resultsManager.newPriceAppeared(score+1,game.getLevelsManager().getLastLevelCompleted());
    }

    protected void readDetectedAndSaveIntentGeneric(ArrayList<Integer> toReadVals){
        AudioManager.instance.readTheSum(toReadVals);
        timeToWaitForReading = toReadVals.size(); // seconds

        int sum = 0;
        for( Integer i : toReadVals ) {
            sum += i;
        }
        addIntentToResults(sum,level.price.getDisplayNumber());
    }

    protected void saveLastSolution(ArrayList<VirtualBlock> detectedBlocks){
        game.resultsManager.saveLastSolution(detectedBlocks);
    }



    protected void resetIntentStart(){
        game.resultsManager.resetIntentStart();
    }




    public int getCurrentPriceType(){
        return level.price.getPriceTypeNr();
    }

    public boolean isTooMuch(){ return isTooMuch;}

    protected void checkIfTableCleaned(){
        ArrayList<VirtualBlock> nowSolution = game.resultsManager.getLastSolution();
        ArrayList<Solution> lastFinalSolution = game.resultsManager.getLastFinalSolution();
        int correctAnswer = level.price.getDisplayNumber();
        int nowSolutionNr = game.resultsManager.getLastSolutionNr();
        int lastFinalSolutionNr = game.resultsManager.getLastFinalSolutionNr();
        Gdx.app.log(TAG,"lastFinalSolutionNr "+lastFinalSolutionNr);
        if(lastFinalSolutionNr!=0) { // we start with lastFinalSolution = 0;

            if ((correctAnswer + lastFinalSolutionNr) == nowSolutionNr) {
                for (int i = 0; i < lastFinalSolution.size(); i++) {
                    Solution lastSolutionBlock = lastFinalSolution.get(i);
                   /// Gdx.app.log(TAG, " for i " + i + " id " + lastSolutionBlock.getId());
                    boolean lastBlockPresent = false;
                    for (int j = 0; j < nowSolution.size(); j++) {
                        VirtualBlock nowSolutionBlock = nowSolution.get(j);
                        //Gdx.app.log(TAG, " for j " + j + " id " + nowSolutionBlock.getBlockId());
                        if (lastSolutionBlock.getId() == nowSolutionBlock.getBlockId()) {
                         //   Gdx.app.log(TAG, "i id == j id");
                            lastBlockPresent = true;
                            if (nowSolutionBlock.getCenterVector().dst(lastSolutionBlock.getPosition()) > Constants.NO_MOVEMENT_DIST) {
                                Gdx.app.log(TAG, " same id but big distance!!");
                                lastBlockPresent = false;
                            }
                        }
                    }
                    if (!lastBlockPresent) {
                        Gdx.app.log(TAG, "BREAK! TABLE CLEANED: TRUE!");
                        setTableCleaned(true);
                        return;
                    }
                }
                setTableCleaned(false); // if we checked and everything in -> the table wasn't clean
                return;
            } else {
                setTableCleaned(true);
            }
        }
    }

    public boolean wasTableCleaned(){
        return tableCleaned;
    }

    private void setTableCleaned(boolean wasCleaned){
        tableCleaned = wasCleaned;
    }

    public ArrayList<Solution> getLastFinalSolution(){
        return game.resultsManager.getLastFinalSolution();
    }





}

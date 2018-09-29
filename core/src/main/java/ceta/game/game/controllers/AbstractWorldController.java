package ceta.game.game.controllers;

import ceta.game.game.Assets;
import ceta.game.game.levels.AbstractLevel;
import ceta.game.game.levels.Level3Horizontal;
import ceta.game.game.levels.LevelParams;
import ceta.game.game.objects.*;
import ceta.game.managers.AbstractBlocksManager;
import ceta.game.screens.*;
import ceta.game.transitions.ScreenTransition;
import ceta.game.transitions.ScreenTransitionFade;
import ceta.game.util.*;

import com.badlogic.gdx.*;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Disposable;

import java.util.ArrayList;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.delay;


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
    public int levelNr;

    public DirectedGame game;
    protected boolean countdownOn;
    protected float countdownCurrentTime;
    private ScreenTransition oneSegFadeIn;
    protected LevelParams levelParams;
    protected boolean playerInactive;
    protected float timeLeftScreenFinishedDelay;
    protected boolean screenFinished, inChangeProcess;
    protected boolean moveMade;
    private int localCountdownMax;
    protected int currentErrors;
    protected boolean isTooMuch;
    protected int timeToWait;
    protected float timeToWaitForReading;
    protected boolean tableCleaned;
    private boolean happyEnd;
    private ArrayList<Integer> toReadVals;

    protected int operationsNumberToPassToNext;



    public AbstractWorldController(DirectedGame game, Stage stage, int levelNr) {
        this.game = game;
        this.stage = stage;
        this.levelNr = levelNr;
        oneSegFadeIn = ScreenTransitionFade.init(1);

        //levelParams = getLevelParams(levelNr);
        loadLevelParamsFromCsv(levelNr);
        checkLevelParamsAndSetOperationsToPassToNext();
        AudioManager.instance.setStage(stage);

        // check if works
        switch(levelParams.islandNr){
            case 1:
                AudioManager.instance.play(Assets.instance.music.musicCity);
                break;
            case 2:
                AudioManager.instance.play(Assets.instance.music.musicClouds);
                break;
            case 3:
                AudioManager.instance.play(Assets.instance.music.musicTubes);
                break;
            case 4:
                if(levelNr == Constants.PLUS_10_LEVEL)
                    AudioManager.instance.playSoundWithDelay(Assets.instance.sounds.plus10);
                AudioManager.instance.play(Assets.instance.music.musicUnderwater);
                break;
            case 5:
                AudioManager.instance.play(Assets.instance.music.musicFactory);
                break;
            case 6:
                if(levelNr == Constants.LAST_LEVEL_NR){
                    AudioManager.instance.playSoundWithDelay(Assets.instance.sounds.finalBattleIntro);
                   // AudioManager.instance.playWithoutInterruption(Assets.instance.sounds.finalBattleIntro);
                    AudioManager.instance.play(Assets.instance.music.musicBattle);
                }

                else
                    AudioManager.instance.play(Assets.instance.music.musicCity);
                break;

        }

        timeToWait = Constants.ACTION_SUBMIT_WAIT;

        init();
        localInit();
    }


    public abstract void update(float delta);
    protected abstract void testCollisionsInController(boolean isDynamic);
    protected abstract void readDetectedSaveIntentAndLastSolution();


    protected abstract void localInit();
    protected abstract void updateDigitalRepresentations();
    protected abstract void countdownMove();
    public abstract boolean isPlayerInactive();

    public abstract int getNowDetectedSum();
    public abstract boolean isWaitForFirstMove();


    public void init () {
        timeToWaitForReading = 0;

        happyEnd = false;
        inChangeProcess = screenFinished = false;
        cameraHelper = new CameraHelper();
        cameraHelper.setTarget(null);
        score = 0;

        playerInactive = false;
        timeLeftScreenFinishedDelay = 0;
        moveMade = false;
        //localCountdownMax = GamePreferences.instance.countdownMax;
        localCountdownMax = Constants.COUNTDOWN_MAX;
        currentErrors = 0;
        isTooMuch = false;
        tableCleaned = true;
        toReadVals = new ArrayList<Integer>();

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
        else if (keycode == Input.Keys.S){
            forceSuccess();
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
        ScreenTransition transition = ScreenTransitionFade.init(0.75f);
        game.setScreen(new SimpleMenuScreen(game), transition);
    }

    public void goToCongratulationsScreen () {

        game.setScreen(new CongratulationsScreen(game,score), oneSegFadeIn);

    }

    public void goToRepeatLevelScreen(){
        game.setScreen(new RepeatLevelScreen(game),oneSegFadeIn);
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
                onCollisionBrunoWithPrice(level.price, objectToCheck);
                moveMade = false;
            } else {
                if (moveMade) {
                    playErrorSound();
                    moveMade = false;
                }

            }
        }else{
            if (moveMade) {
                playErrorSound();
                moveMade = false;
            }
        }
    }

    protected void testCollisionsHorizontalDynamic(AbstractGameObject objectToCheck){
        // Gdx.app.log(TAG, " testCollisionsHorizontalDynamic W "+ Constants.VIEWPORT_HEIGHT/2);
        if(objectToCheck != null ) {

            if (level.price.getY() < Constants.GROUND_LEVEL + Constants.VIEWPORT_HEIGHT / 2) { // price in detection zone
                r1.set(objectToCheck.getX() + objectToCheck.getWidth() - 2, objectToCheck.getY(), // two pixels below the middle
                        4, 4);

                r2.set(level.price.getX(),
                        Constants.GROUND_LEVEL,
                        level.price.getWidth() / 2, Constants.VIEWPORT_HEIGHT / 2);


                if (r1.overlaps(r2)) {
                    onCollisionBrunoWithPrice(level.price, objectToCheck);
                    moveMade = false;
                }else {
                    if (moveMade) {
                        playErrorSound();
                        moveMade = false;
                    }

                }
            }
        }else{
            if (moveMade) {
                playErrorSound();
                moveMade = false;
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
                onCollisionBrunoWithPriceOpenMouth(level.price, objectToCheck);
                moveMade = false;
            } else {
                if (moveMade) {
                    playErrorSound();
                    moveMade = false;
                }

            }
        }else{
            if (moveMade) {
                playErrorSound();
                moveMade = false;
            }
        }
    }

    protected void testCollisionsDynamicL3H(float xZero){
        if (!(level.bruno.getActions().size > 0)) { // we have to be sure that the move finished
            ((Level3Horizontal)level).gear.setRotationSpeed(0);
            // we set 4px x 4px box at the middle end (X), in the top (Y)
            if(level.bruno.getTerminalX() != xZero ) {
                if ((level.price.getY() < Constants.GROUND_LEVEL + Constants.VIEWPORT_HEIGHT / 2) //above
                        && level.price.getY() > level.bruno.getEatPointY()) { //not below mouth
                    r1.set(level.bruno.getX() + level.bruno.getWidth() / 2 - 2,
                            level.bruno.getY() + level.bruno.getHeight(),
                            4, Constants.BASE);
                    r2.set(level.price.getX(),
                            Constants.GROUND_LEVEL,
                            level.price.getWidth(), Constants.VIEWPORT_HEIGHT/2);

                    if (r1.overlaps(r2)) {
                        //onCollisionBrunoWithPrice(level.price);
                        onCollisionBrunoWithPriceOpenMouth(level.price, level.bruno);
                        moveMade = false;
                    }
                    else {
                        if (moveMade) {
                            playErrorSound();
                            moveMade = false;
                        }

                    }
                }
            }
            else{
                if (moveMade) {
                    playErrorSound();
                    moveMade = false;
                }
            }
        }
    }

    protected void testCollisionsVerticalDynamic(BrunoVertical objectToCheck){
        if(objectToCheck != null ) {
            if ((level.price.getX() < 190) //not very right
                    && level.price.getX() > objectToCheck.getX()) { //not behind bruno
                r1.set(objectToCheck.getX(),
                        objectToCheck.getY() + objectToCheck.getHeight() / 2 - 4, // two pixels below the middle
                        objectToCheck.getWidth(), 4);
                r2.set(-Constants.VIEWPORT_WIDTH/2,
                        level.price.getY(),
                        Constants.VIEWPORT_WIDTH, level.price.getHeight() / 2);

                if (r1.overlaps(r2)) {
                    onCollisionBrunoWithPriceOpenMouth(level.price, objectToCheck);
                    moveMade = false;
                }
                else {
                    if (moveMade) {
                        playErrorSound();
                        moveMade = false;
                    }

                }
            }
        }
        else {
            if (moveMade) {
                playErrorSound();
                moveMade = false;
            }

        }
    }

    protected void testCollisionsDynamicL3V (float yZero) {
        BrunoVertical brunoV = (BrunoVertical) level.bruno;
        if (!(brunoV.getActions().size > 0)) { // if bruno is not moving
            if(brunoV.getTerminalY() != yZero ) {
                if ((level.price.getX() < 190) //not very right
                        && level.price.getX() > brunoV.getX()) { //not behind bruno
                    r1.set(brunoV.getX(),
                            brunoV.getY(),
                            brunoV.getWidth(), brunoV.getHeight() / 2);
                    r2.set(-Constants.VIEWPORT_WIDTH/2,
                            level.price.getY(),
                            Constants.VIEWPORT_WIDTH, level.price.getHeight() / 2);

                    if (r1.overlaps(r2)) {
                        onCollisionBrunoWithPriceOpenMouth(level.price, brunoV);
                        moveMade = false;
                    }
                    else {
                        if (moveMade) {
                            playErrorSound();
                            moveMade = false;
                        }

                    }
                }
            }
            else {
                if (moveMade) {
                    playErrorSound();
                    moveMade = false;
                }

            }
        }
    }

    private void moveCamera (float x, float y) {
        x += cameraHelper.getPosition().x;
        y += cameraHelper.getPosition().y;
        cameraHelper.setPosition(x, y);
    }

    private void genericOnCollision(){
        if(notYetPassTheLevel()){
            playPositiveSound(AudioManager.instance.getPositiveFeedbackForIsland(levelParams.islandNr));
            currentErrors = 0;
            game.resultsManager.setLastToFinal();
            game.resultsManager.onCollisionRecord(level.price.getCorrectAnswerToPut(), level.price.getDisplayNumber(), score);
            score += 1;
        }else{
            currentErrors = 0;
            game.resultsManager.setLastToFinal();
            game.resultsManager.onCollisionRecord(level.price.getCorrectAnswerToPut(), level.price.getDisplayNumber(), score);
            score += 1;
            if(score >= getOperationsToPassToNextLevel())
                playPositiveSound(Assets.instance.sounds.positive);
            else
                playErrorSound();
        }
    }


    protected void onCollisionBrunoWithPrice(Price price, AbstractGameObject collisionObject) {
        if (price.getActions().size == 0) { // we act just one time!
            genericOnCollision();
            if (notYetPassTheLevel()) {
                price.onCollision(collisionObject.getX()+collisionObject.getWidth(),collisionObject.getY());
            } else {
                price.onCollisionLast(collisionObject.getX()+collisionObject.getWidth(),collisionObject.getY());
                onLevelFinished();
            }

        }
    }

    protected void onCollisionBrunoWithPriceOpenMouth(Price price, Bruno bruno){
        // Gdx.app.log(TAG, "NO updates in progress and collision!");
        if (price.getActions().size == 0) { // we act just one time!
            bruno.moveHead();
            genericOnCollision();
            if (notYetPassTheLevel()) {
                //Gdx.app.log(TAG,"=== to eat bruno x"+bruno.getX()+" bruno y "+bruno.getEatPointY()+" price x "+price.getX()+" y "+price.getY());
                //price.wasEaten(bruno.getX(), bruno.getEatPointY());
                price.onCollision(bruno.getX()+bruno.getWidth()/2, bruno.getEatPointY());

            } else {
//                price.lastEaten(bruno.getX(), bruno.getEatPointY());
                price.onCollisionLast(bruno.getX()+bruno.getWidth()/2, bruno.getEatPointY());
                onLevelFinished();
            }

        }
    }


    public void finishTheScreen(){
        screenFinished = true;
        timeLeftScreenFinishedDelay = Constants.TIME_DELAY_SCREEN_FINISHED;
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
            AudioManager.instance.playWithoutInterruption(Assets.instance.sounds.buzz);
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


    protected void loadLevelParamsFromCsv(int levelNr){
        levelParams = LevelsCsv.instance.getParams(levelNr);
    }

    private void checkLevelParamsAndSetOperationsToPassToNext(){
        if(levelParams.operations.length > 0) { // NOT RANDOM CASE
            if(levelParams.operations.length < levelParams.operationsToFinishLevel ) {
                Gdx.app.log(TAG, "we adjust operations to finish!  was: " + levelParams.operationsToFinishLevel + " now " + levelParams.operations.length);
                levelParams.operationsToFinishLevel = levelParams.operations.length;
            }

//            // in this no-random case if there are more operations to finish the level than all the operations, it is wrong!!
//            if (levelParams.operationsNumberToPassToNext  levelParams.operationsToFinishLevel){
//                Gdx.app.log(TAG,"strange! finish with no posibility to collect! "+levelParams.operationsNumberToPassToNext +" "+levelParams.operationsToFinishLevel);
//                levelParams.operationsToFinishLevel = levelParams.operationsNumberToPassToNext;
//            }
        }
        operationsNumberToPassToNext = MathUtils.round(levelParams.operationsToFinishLevel*0.8f);
        Gdx.app.log(TAG,"to finish level "+levelParams.operationsToFinishLevel+" to pass: "+operationsNumberToPassToNext);

    }

    public int getOperationsNumberToFinishLevel(){
        return levelParams.operationsToFinishLevel;

    }


    public int getMinimumNumber(){ return levelParams.numberMin;}
    public int getMaximumNumber(){ return levelParams.numberMax;}

    public boolean isNumberLineVisible(){ return levelParams.visibleNumberLine;}



    public void setPlayerInactive(boolean isInactive){
        playerInactive = isInactive;
    }



    @Override
    public boolean touchDown (int screenX, int screenY, int pointer, int button) {
        Gdx.app.log(TAG," TOUCHED "+screenX+ " "+screenY);
        if (Gdx.app.getType() == Application.ApplicationType.Android) {
            if (screenX > 570 && screenY < 40) {
                game.setScreen(new EmergencyScreen(game), oneSegFadeIn);
            }
            else if(screenX < 40 && screenY < 40) {
                //game.getLevelsManager().goToNextLevelWorkaround();
                forceSuccess();
            }

        }else {

            if (screenX > 440 && screenY < 10) {
                game.setScreen(new EmergencyScreen(game), oneSegFadeIn);
            }
            else if(screenX < 10 && screenY < 10) {
                forceSuccess();
                //game.getLevelsManager().goToNextLevelWorkaround();
            }

        }



        return true;
    }

    public void globalUpdate(float deltaTime){
        if(screenFinished) {
            if(!inChangeProcess) {
                timeLeftScreenFinishedDelay -= deltaTime;
                if (timeLeftScreenFinishedDelay < 0) {
                    inChangeProcess = true;
                    if (happyEnd) {
                        goToCongratulationsScreen();
                    }
                    else {
                        goToRepeatLevelScreen();
                    }
                }
            }

        }
        else{
            //testScreenOver();
            testCollisions(); // winning condition checked
        }

        handleDebugInput(deltaTime);
        level.update(deltaTime); //stage.act()
    }

//    public boolean isTimeForReadOver(float deltaTime){
////        timeToWaitForReading-=deltaTime;
////        if(timeToWaitForReading<0)
////            return true;
////        else
////            return false;
//        return true;
//
//    }
//
//    public boolean isReadOver(){
//
//       // return timeToWaitForReading<0;
//        return true;
//    }



    protected void addIntentToResults(int kidResponse, int priceValue, int priceDisplayNumber, ArrayList<Integer> toReadVals){
        boolean wasSuccessful = (kidResponse == priceValue);
        isTooMuch = (kidResponse > priceValue);
        game.resultsManager.addIntent(wasSuccessful, kidResponse,priceValue,priceDisplayNumber, toReadVals, score);
        errorCheck(wasSuccessful);
    }

    private void errorCheck(boolean wasSuccessful){
        if(wasSuccessful)
            currentErrors = 0;
        else
            currentErrors +=1;

        Gdx.app.log(TAG, " new intent with result: "+wasSuccessful+ " we have now "+currentErrors+" errors acumulated, is too much:  "+isTooMuch);
    }



    protected void readDetectedAndSaveIntentGeneric(ArrayList<Integer> toReadIds){
        //AudioManager.instance.readTheSum(toReadVals);
        toReadVals.clear();
        timeToWaitForReading = toReadIds.size(); // seconds

        int sum = 0;
        for( Integer id : toReadIds ) {
            sum += AbstractBlocksManager.getValueById(id);
            toReadVals.add(AbstractBlocksManager.getValueById(id));
        }

        AudioManager.instance.readNumber(levelParams.numberMin+sum, (levelParams.numberMin+ sum) == level.price.getDisplayNumber());
        addIntentToResults(sum,level.price.getCorrectAnswerToPut(), level.price.getDisplayNumber(), toReadVals);

    }

    protected void saveLastSolution(ArrayList<VirtualBlock> detectedBlocks){
        game.resultsManager.saveLastSolution(detectedBlocks);
    }


    private boolean notYetPassTheLevel(){
        Gdx.app.log(TAG," we have to collect "+ getOperationsNumberToFinishLevel()+" now have: "+ getOperationsNumber() );
        return getOperationsNumber() < getOperationsNumberToFinishLevel();
    }

//    private boolean operationsNumberCompleted(){
//     //   Gdx.app.log(TAG," operations : "+getOperationsNumber() + " to finish "+getOperationsNumberToFinishLevel());
//        return getOperationsNumber() > getOperationsNumberToFinishLevel();
//    }

    public int getCurrentPriceType(){
        return level.price.getPriceTypeNr();
    }

    public boolean isTooMuch(){ return isTooMuch;}

    protected void checkIfTableCleaned(){
        ArrayList<VirtualBlock> nowSolution = game.resultsManager.getLastSolution();
        ArrayList<Solution> lastFinalSolution = game.resultsManager.getLastFinalSolution();
        int correctAnswer = level.price.getCorrectAnswerToPut();
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

    private int getOperationsNumber(){
        return level.price.getCurrentOperationNr();
    }

    private int getOperationsToPassToNextLevel(){
        return operationsNumberToPassToNext;
    }

//    private void testScreenOver(){
//
//        if(operationsNumberCompleted()){
//
//        }
//
//    }


    public void onLevelFinished(){
        Gdx.app.log(TAG," score "+score+", to pass "+getOperationsToPassToNextLevel());

        if(score >= getOperationsToPassToNextLevel()){
            Gdx.app.log(TAG," yesss happEND!!!");
            happyEnd = true;

        }else{
            // we re-do the level (0 points)
            Gdx.app.log(TAG, "to less! we should re-do!!!"); // wait here!!!
            happyEnd = false;
        }
        finishTheScreen();


    }

    public int getLevelNr(){ return levelNr;}

    public void onNewPricePosition(int currentOperationNr){
        currentErrors  = 0;
        game.resultsManager.newPriceAppeared(currentOperationNr,game.getLevelsManager().getCurrentLevel()); // we register now level not last level completed!

    }

    public void forceSuccess(){
        happyEnd = true;
        finishTheScreen();
    }


    public boolean isMoveMade(){
        return moveMade;
    }



    public int getNowDetectedSumWithStartNumberLineValue(){
        return (getNowDetectedSum()+levelParams.numberMin) ;
    }


    protected void playErrorSound(){
        AudioManager.instance.playErrorSound();
    }

    protected void playPositiveSound(final Sound toPlay){
        AudioManager.instance.playPositiveSound(toPlay);
    }

}

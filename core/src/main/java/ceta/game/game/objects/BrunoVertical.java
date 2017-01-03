package ceta.game.game.objects;

import ceta.game.game.Assets;
import ceta.game.managers.BrunosManager;
import ceta.game.util.Constants;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;


import static com.badlogic.gdx.scenes.scene2d.actions.Actions.run;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;

/**
 * Created by ewe on 11/7/16.
 */
public class BrunoVertical extends Bruno {
    public static final String TAG = BrunoVertical.class.getName();
    protected int brunoValue;
    protected int id;
    protected BrunosManager brunosManager;
    private boolean goingToDie;

    protected TextureRegion brunoHeadReg;
    protected TextureRegion brunoBodyReg;

    private int headXoffset,bodyOffsetX,headYoffset;
    protected float actionVelocity;



    public BrunoVertical (int value, BrunosManager brunosManagerNow) {
        super(value,brunosManagerNow);
       // abstractObjectInit();
        Gdx.app.log(TAG," in vertical bruno constructor!! ----");
        brunosManager = brunosManagerNow;
        brunoValue = value;
        goingToDie = false;
        setColorAndTexture(brunoValue);
        init();

    }

    @Override
    public void init () {
        Gdx.app.log(TAG," in vertical bruno init ----");
        headState = HEAD_STATE.FIXED;
        this.setSize(Constants.BASE,Constants.BASE*brunoValue); // now we can set the values that depend on size
        this.setPosition(Constants.VERTICAL_MIDDLE_X - getWidth()/2 ,Constants.DETECTION_ZONE_END-Constants.BASE/2);
        this.setTerminalX(Constants.VERTICAL_MIDDLE_X - getWidth()/2);
        this.setTerminalY(Constants.DETECTION_ZONE_END-Constants.BASE/2);
        actionVelocity = 0.3f;

        super.abstractObjectInit();


    }

    protected void setColorAndTexture(int bValue){ //TODO all bodies and all heads
        Gdx.app.log(TAG," in vertical bruno setColorAndTexture ----");
        headXoffset = 0;
        headYoffset = 0;
        bodyOffsetX = 0;
        switch (bValue){
            case 1:
                setColor(Color.YELLOW);
                regTex = Assets.instance.bruno.body01;
                brunoHeadReg = Assets.instance.bruno.body01head;
                brunoBodyReg = Assets.instance.bruno.body01body;
                headYoffset = -3;
                break;
            case 2:
                setColor(Color.GREEN);
                regTex = Assets.instance.bruno.body02;
                brunoHeadReg = Assets.instance.bruno.body02head;
                brunoBodyReg = Assets.instance.bruno.body02body;
                headXoffset =  -3;
                headYoffset = -5;
                break;
            case 3:
                setColor(Color.ORANGE);
                regTex = Assets.instance.bruno.body03;
                brunoHeadReg = Assets.instance.bruno.body03head;
                brunoBodyReg = Assets.instance.bruno.body03body;
                headXoffset = 5;
                headYoffset = -5;
                bodyOffsetX = -3;
                break;
            case 4:
                setColor(Color.CYAN);
                regTex = Assets.instance.bruno.body04;
                brunoHeadReg = Assets.instance.bruno.body04head;
                brunoBodyReg = Assets.instance.bruno.body04body;
                headYoffset = -4;
                bodyOffsetX = -5;
                break;
            case 5:
                setColor(Color.PINK);
                regTex = Assets.instance.bruno.body05;
                brunoHeadReg = Assets.instance.bruno.body05head;
                brunoBodyReg = Assets.instance.bruno.body05body;
                headXoffset = -14;
                headYoffset = -2;
                break;
        }



    }



    public int getId() {
        return id;
    }

    public void setId(int idVal){
        Gdx.app.log(TAG, "we set the id "+idVal);
        id = idVal;
    }

    public void moveMeToAndSetTerminalY(float x, float y){
        setTerminalY(y);
        brunosManager.addToInMovementIds(id);
        addAction(sequence(Actions.moveTo(x,y,actionVelocity),
                run(new Runnable() {
                    public void run() {
                        brunosManager.notificationBrunoMoved(id);
                    }
                })
        ));
    }

    public void moveMeToAndSetTerminalYWithBounce(float x, float y){
        setTerminalY(y);
        brunosManager.addToInMovementIds(id);
        addAction(sequence(Actions.moveTo(x,y,actionVelocity*(Math.abs(getY()-y)/40), Interpolation.pow2),
                run(new Runnable() {
                    public void run() {
                        brunosManager.notificationBrunoMoved(id);
                    }
                })
        ));
    }

    public void disappearAndRemove(){
        //clearActions();
        if(!goingToDie) {
            goingToDie = true;
            brunosManager.addToInMovementIds(id);

            addAction(sequence(Actions.alpha(0, actionVelocity), run(new Runnable() {
                public void run() {
                    brunosManager.notificationBrunoGone(id);

                    remove();
                }
            })));
        }else
            Gdx.app.log(TAG,"i don't need more actions, im dying!");
    }


    public void moveHead(){
        headState = HEAD_STATE.UP;
        //Gdx.app.log(TAG, "moveHead!!! "+headState);
    }




    @Override
    public void act(float delta) {
        super.act(delta);
       // Gdx.app.log(TAG, "headState "+headState);
        switch(headState) {
            case UP:
               // Gdx.app.log(TAG, "UP now rotation "+getRotation()+ " next rotation "+getRotation() + delta * 100);
                setRotation(getRotation() + delta * 100);
               // Gdx.app.log(TAG, "UP now rotation "+getRotation());
                if(getRotation() > 35) {
                   // Gdx.app.log(TAG, "state change to GOING_BACK");
                    headState = HEAD_STATE.GOING_BACK;
                    //Gdx.app.log(TAG, "headState GB "+headState);
                }
                break;
            case FIXED:
                setRotation(0);
                break;
            case GOING_BACK:
               // Gdx.app.log(TAG, "GOING_BACK now rotation "+getRotation()+ " next rotation "+getRotation() + delta * 100);
                setRotation(getRotation() - delta * 100);
              //  Gdx.app.log(TAG, "GOING_BACK now rotation "+getRotation());
                if(getRotation() < 0) {
                 //   Gdx.app.log(TAG, "state change to FIXED");
                    headState = HEAD_STATE.FIXED;
                 //   Gdx.app.log(TAG, "headState F"+headState);
                }

        }


    }

    @Override
    public void draw(Batch batch, float parentAlpha) {

        //batch.setProjectionMatrix(camera.combined);
        // batch.draw(regTex,this.getX(),this.getY());
        if(getY()>Constants.DETECTION_ZONE_END-getHeight()) {
           // Gdx.app.log(TAG, "draw vertical bruno "+brunoValue);
            batch.setColor(this.getColor());
            batch.draw(brunoBodyReg.getTexture(),
                    this.getX() + offsetX + bodyOffsetX, this.getY() + offsetY,
                    this.getOriginX(), this.getOriginY(),
                    brunoBodyReg.getRegionWidth() + 4, brunoBodyReg.getRegionHeight(),
                    this.getScaleX(), this.getScaleY(),
                    0,
                    brunoBodyReg.getRegionX(), brunoBodyReg.getRegionY(),
                    brunoBodyReg.getRegionWidth(), brunoBodyReg.getRegionHeight(), lookingLeft, false);

            batch.draw(brunoHeadReg.getTexture(),
                    this.getX() + headXoffset + offsetX+ bodyOffsetX, this.getY() + brunoBodyReg.getRegionHeight() + headYoffset + offsetY,
                    0, 0,
                    brunoHeadReg.getRegionWidth(), brunoHeadReg.getRegionHeight(),
                    this.getScaleX(), this.getScaleY(),
                    this.getRotation(),
                    brunoHeadReg.getRegionX(), brunoHeadReg.getRegionY(),
                    brunoHeadReg.getRegionWidth(), brunoHeadReg.getRegionHeight(), lookingLeft, false);
            batch.setColor(1, 1, 1, 1);
        }

        offsetX = 0;
        offsetY = 0;


    }

    @Override
    public float getEatPointY(){
        return getY()+ brunoBodyReg.getRegionHeight()+headYoffset - 4;
    }


}

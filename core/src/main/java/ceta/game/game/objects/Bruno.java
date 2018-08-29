package ceta.game.game.objects;

import ceta.game.game.Assets;
import ceta.game.managers.BrunosManager;
import ceta.game.util.Constants;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.run;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;

/**
 * Created by ewe on 7/26/16.
 */
public class Bruno extends AbstractGameObject {
    public static final String TAG = Bruno.class.getName();

    protected boolean lookingLeft;
    protected TextureRegion tube;
    protected TextureRegion mask;
    public enum HEAD_STATE {UP,FIXED, GOING_BACK}
    public HEAD_STATE headState;



    public Bruno () {
        Gdx.app.log(TAG," in bruno constructor@@@ ----");
        init();
    }

    public Bruno (int value, BrunosManager brunosManagerNow) {
        Gdx.app.log(TAG," in bruno constructor@@@ ---- trucho &&&&&");

    }


    @Override
    public void init () {
        Gdx.app.log(TAG, "in init --- brunoooo");
        regTex = Assets.instance.bruno.underWater;
        mask = Assets.instance.roboticParts.mask;
        tube = Assets.instance.roboticParts.finalTube;
        this.setSize(regTex.getRegionWidth(),regTex.getRegionHeight());
        abstractObjectInit();
        lookingLeft = false;

    }

    protected void abstractObjectInit(){
        Gdx.app.log(TAG, "in abstractObjectInit init --- brunoooo");
        super.init();

    }


    @Override
    public void draw(Batch batch, float parentAlpha) {
        //batch.setProjectionMatrix(camera.combined);
        // batch.draw(regTex,this.getX(),this.getY());
        batch.setColor(this.getColor());
        batch.draw(regTex.getTexture(),
                this.getX()+offsetX, this.getY()+offsetY,
                this.getOriginX(), this.getOriginY(),
                this.getWidth() ,this.getHeight(),
                this.getScaleX(), this.getScaleY(),
                this.getRotation(),
                regTex.getRegionX(), regTex.getRegionY(),
                regTex.getRegionWidth(), regTex.getRegionHeight(), false,false);
        batch.setColor(1,1,1,1);

        batch.draw(tube.getTexture(),
                Constants.HORIZONTAL_ZERO_X-tube.getRegionWidth() , this.getY()+offsetY + 230, // adjust
                this.getOriginX(), this.getOriginY(),
                tube.getRegionWidth(),tube.getRegionHeight(),
                this.getScaleX(), this.getScaleY(),
                this.getRotation(),
                tube.getRegionX(), tube.getRegionY(),
                tube.getRegionWidth(), tube.getRegionHeight(), false,false);
        batch.setColor(1,1,1,1);

        batch.draw(mask.getTexture(),
                this.getX()+offsetX +95, this.getY()+offsetY + 227,
                this.getOriginX(), this.getOriginY(),
                mask.getRegionWidth(), mask.getRegionHeight(),
                this.getScaleX(), this.getScaleY(),
                this.getRotation(),
                mask.getRegionX(), mask.getRegionY(),
                mask.getRegionWidth(), mask.getRegionHeight(), false,false);
        batch.setColor(1,1,1,1);

        offsetX = 0;
        offsetY = 0;
    }


    public void moveHead(){
        headState = BrunoVertical.HEAD_STATE.UP;
        //Gdx.app.log(TAG, "moveHead!!! "+headState);
    }


    public void moveMeToAndSetTerminalX(float x, float y){
        //clearActions();
        MoveToAction moveToAction = new MoveToAction();
        moveToAction.setPosition(x,y);
        moveToAction.setDuration(1f);
        setTerminalX(x);

        addAction(moveToAction);
    }


    public void setLookingLeft(boolean isLeft){
        lookingLeft = isLeft;
    }

    public float getEatPointY(){
        return getY()+ getHeight() - 15;
    }



}

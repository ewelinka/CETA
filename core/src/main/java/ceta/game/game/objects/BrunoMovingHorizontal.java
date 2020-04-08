package ceta.game.game.objects;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import ceta.game.game.Assets;
import ceta.game.util.Constants;

/**
 * Created by ewe on 12/2/16.
 */
public class BrunoMovingHorizontal extends Bruno {
    private TextureRegion head;

    @Override
    public void init () {
        headState = HEAD_STATE.FIXED;
        animation = Assets.instance.bruno.walk;
        head = Assets.instance.bruno.walkHead;
        this.setSize(53,85); //Hardcoded!!
        abstractObjectInit();
        lookingLeft = false;
        float xZero = Constants.HORIZONTAL_ZERO_X-getWidth()/2;
        setPosition(xZero,Constants.GROUND_LEVEL);
        setTerminalX(xZero);
        setAnimation(animation);

    }





    @Override
    public void act(float delta) {
        super.act(delta);
        // Gdx.app.log(TAG, "headState "+headState);
        switch(headState) {
            case UP:
                // Gdx.app.log(TAG, "UP now rotation "+getRotation()+ " next rotation "+getRotation() + delta * 100);
                setRotation(getRotation() + delta * 200);
                // Gdx.app.log(TAG, "UP now rotation "+getRotation());
                if(getRotation() > 90) {
                    // Gdx.app.log(TAG, "state change to GOING_BACK");
                    headState = BrunoVertical.HEAD_STATE.GOING_BACK;
                    //Gdx.app.log(TAG, "headState GB "+headState);
                }
                break;
            case FIXED:
                setRotation(0);
                break;
            case GOING_BACK:
                // Gdx.app.log(TAG, "GOING_BACK now rotation "+getRotation()+ " next rotation "+getRotation() + delta * 100);
                setRotation(getRotation() - delta * 200);
                //  Gdx.app.log(TAG, "GOING_BACK now rotation "+getRotation());
                if(getRotation() < 0) {
                    //   Gdx.app.log(TAG, "state change to FIXED");
                    headState = BrunoVertical.HEAD_STATE.FIXED;
                    //   Gdx.app.log(TAG, "headState F"+headState);
                }

        }


    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        //batch.setProjectionMatrix(camera.combined);
        // batch.draw(regTex,this.getX(),this.getY());
        if(!hasActions()){
            //regTex = Assets.instance.bruno.still;
            lookingLeft = false;
            stateTime = 0;
        }
        regTex = animation.getKeyFrame(stateTime,true);

        batch.setColor(this.getColor());
        batch.draw(regTex.getTexture(),
                this.getX()+offsetX, this.getY()+offsetY,
                this.getOriginX(), this.getOriginY(),
                this.getWidth() ,this.getHeight(),
                this.getScaleX(), this.getScaleY(),
                0,
                regTex.getRegionX(), regTex.getRegionY(),
                regTex.getRegionWidth(), regTex.getRegionHeight(), lookingLeft,false);


        batch.draw(head.getTexture(),
                this.getX()+offsetX, this.getY()+offsetY + 50 ,
                0, 0,
                this.getWidth() ,32,
                this.getScaleX(), this.getScaleY(),
                this.getRotation(),
                head.getRegionX(), head.getRegionY(),
                head.getRegionWidth(), head.getRegionHeight(), lookingLeft,false);

        batch.setColor(1,1,1,1);

        offsetX = 0;
        offsetY = 0;
    }

    @Override
    public float getEatPointY(){
        return getY() +offsetY ;
    }

}

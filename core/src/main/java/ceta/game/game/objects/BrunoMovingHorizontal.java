package ceta.game.game.objects;

import ceta.game.game.Assets;
import ceta.game.util.Constants;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created by ewe on 12/2/16.
 */
public class BrunoMovingHorizontal extends Bruno {
    private TextureRegion head;

    @Override
    public void init () {

        animation = Assets.instance.bruno.walk;
        head = Assets.instance.bruno.walkHead;
        this.setSize(53,85); //Hardcoded!!
        superinit();
        lookingLeft = false;
        float xZero = Constants.HORIZONTAL_ZERO_X-getWidth()/2;
        setPosition(xZero,Constants.DETECTION_ZONE_END);
        setTerminalX(xZero);
        setAnimation(animation);

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
}

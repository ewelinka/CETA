package ceta.game.game.objects;

import ceta.game.game.Assets;
import ceta.game.util.Constants;
import com.badlogic.gdx.graphics.g2d.Batch;

/**
 * Created by ewe on 12/27/16.
 */
public class MegaBrunoWithArm extends Bruno {

    @Override
    public void init () {
        regTex = Assets.instance.bruno.mega2;
       // mask = Assets.instance.roboticParts.maskArm;
        //tube = Assets.instance.roboticParts.finalTube;
        this.setSize(regTex.getRegionWidth(),regTex.getRegionHeight());
        abstractObjectInit();
        lookingLeft = false;

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

        offsetX = 0;
        offsetY = 0;
    }
}

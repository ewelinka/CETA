package ceta.game.game.objects;

import ceta.game.game.Assets;
import ceta.game.util.Constants;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

/**
 * Created by ewe on 2/18/17.
 */
public class BlackShadow extends AbstractGameObject {


    public BlackShadow(){
        regTex = Assets.instance.tree.tree;
    }


    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.setColor(this.getColor());
        batch.draw(regTex.getTexture(),
                this.getX(), this.getY(),
                this.getOriginX(), this.getOriginY(),
                this.getWidth() ,this.getHeight(),
                this.getScaleX(), this.getScaleY(),
                this.getRotation(),
                regTex.getRegionX()+5, regTex.getRegionY()+regTex.getRegionHeight()-15,
                10, 10, false,false);



    }
}

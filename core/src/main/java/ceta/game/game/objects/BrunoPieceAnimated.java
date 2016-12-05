package ceta.game.game.objects;

import ceta.game.game.Assets;
import ceta.game.managers.AnimatedBrunoManager;
import ceta.game.managers.BrunosManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.run;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;

/**
 * Created by ewe on 11/9/16.
 */
public class BrunoPieceAnimated extends BrunoVertical {
    public static final String TAG = BrunoPieceAnimated.class.getName();

    public BrunoPieceAnimated(int value, AnimatedBrunoManager brunosManager) {
        super(value, brunosManager);
        setOrigin(this.getWidth()/2, 0);
        setScale(1,0);


    }


    @Override
    protected void setColorAndTexture(int val){
        setColor(Color.YELLOW);
        regTex = Assets.instance.bruno.energy;

    }


    public void expandMe(float delay, float speed){
        brunosManager.addToInMovementIds(id);
        addAction(sequence(
                Actions.delay(delay),
                Actions.scaleTo(1.0f,1.0f,speed),
                run(new Runnable() {
                    public void run() {
                        brunosManager.notificationBrunoMoved(id);
                    }
                })
        ));
    }

    public void collapseMe(float delay, float speed){
        brunosManager.addToInMovementIds(id);

        addAction(sequence(
                Actions.delay(delay),
                Actions.scaleTo(1f,0f,speed),
                run(new Runnable() {
                    public void run() {
                        brunosManager.notificationBrunoGone(id);
                        remove();
                    }
                })
        ));


    }
//

//
//    @Override
//    public void draw(Batch batch, float parentAlpha) {
//        //batch.setProjectionMatrix(camera.combined);
//        // batch.draw(regTex,this.getX(),this.getY());
//
//        //batch.setColor(this.getColor());
//
//
//
//        batch.setColor(255, 255, 0, alphaColor * parentAlpha);
//        batch.draw(regTex.getTexture(),
//                this.getX()+offsetX, this.getY()+offsetY,
//                this.getOriginX(), this.getOriginY(),
//                this.getWidth() ,this.getHeight(),
//                this.getScaleX(), this.getScaleY(),
//                this.getRotation(),
//                regTex.getRegionX(), regTex.getRegionY(),
//                regTex.getRegionWidth(), regTex.getRegionHeight(), false,false);
//        batch.setColor(1,1,1,1);
//
//
//        offsetX = 0;
//        offsetY = 0;
//
//    }

}

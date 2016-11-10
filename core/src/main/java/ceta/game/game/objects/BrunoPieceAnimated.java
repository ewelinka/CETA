package ceta.game.game.objects;

import ceta.game.game.Assets;
import ceta.game.managers.AnimatedBrunoManager;
import ceta.game.managers.BrunosManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.run;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;

/**
 * Created by ewe on 11/9/16.
 */
public class BrunoPieceAnimated extends BrunoVertical {
    public BrunoPieceAnimated(short value, AnimatedBrunoManager brunosManager) {
        super(value, brunosManager);
        setOrigin(this.getWidth()/2, 0);
        setScale(1,0);

    }


    @Override
    protected void setColorAndTexture(short val){
        setColor(Color.YELLOW);
        regTex = Assets.instance.roboticParts.copperFitting1;

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
}

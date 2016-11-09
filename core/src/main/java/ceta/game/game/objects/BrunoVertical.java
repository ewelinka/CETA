package ceta.game.game.objects;

import ceta.game.game.Assets;
import ceta.game.managers.BrunosManager;
import ceta.game.util.Constants;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.run;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;

/**
 * Created by ewe on 11/7/16.
 */
public class BrunoVertical extends Bruno {
    private short brunoValue;
    private short id;
    private BrunosManager brunosManager;

    public BrunoVertical (short value, BrunosManager brunosManager) {
        this.brunosManager = brunosManager;
        brunoValue = value;
        init();
        setColorAndTexture(brunoValue);
    }


    public void init () {
        regTex = Assets.instance.bruno.body;
        this.setSize(Constants.BASE,Constants.BASE*brunoValue); // now we can set the values that depend on size
        super.superinit();
    }

    protected void setColorAndTexture(short bValue){
        switch (bValue){
            case 1:
                setColor(Color.YELLOW);
                regTex = Assets.instance.bruno.body;
                break;
            case 2:
                setColor(Color.CYAN);
                regTex = Assets.instance.bruno.body;
                break;
            case 3:
                setColor(Color.ORANGE);
                regTex = Assets.instance.bruno.body;
                break;
            case 4:
                setColor(Color.VIOLET);
                regTex = Assets.instance.bruno.body;
                break;
            case 5:
                setColor(Color.GREEN);
                regTex = Assets.instance.bruno.body;
                break;
        }
    }

    public short getId() {
        return id;
    }

    public void setId(short idVal){
        Gdx.app.log(TAG, "we set the id "+idVal);
        id = idVal;
    }

    public void moveMeToAndSetTerminalY(float x, float y){
        MoveToAction moveToAction = new MoveToAction();
        moveToAction.setPosition(x,y);
        moveToAction.setDuration(1f);
        setTerminalY(y);

        brunosManager.addToInMovementIds(id);

        addAction(sequence(moveToAction,
                run(new Runnable() {
                    public void run() {
                        brunosManager.notificationBrunoMoved(id);
                    }
                })
        ));
    }

    public void disappearAndRemove(){
        brunosManager.addToInMovementIds(id);

        addAction(sequence(Actions.alpha(0,1f),run(new Runnable() {
            public void run() {
                brunosManager.notificationBrunoGone(id);

                remove();
            }
        })));
    }
}

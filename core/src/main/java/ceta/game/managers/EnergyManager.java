package ceta.game.managers;

import ceta.game.game.Assets;
import ceta.game.game.objects.BrunoVertical;
import ceta.game.util.Constants;
import ceta.game.util.Pair;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

import java.util.ArrayList;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;

/**
 * Created by ewe on 11/9/16.
 */
public class EnergyManager extends BrunosManager {
    public static final String TAG = EnergyManager.class.getName();

    private float terminalDelay;
    private final float animationSpeed = 0.2f;
    private float alphaColor;
    private boolean fadeIn;
    private Image base;
    private int units;

    private BrunoVertical brunoVertical;
    private Image energyUnit;

    public EnergyManager(Stage stage) {
        super(stage);
    }

    @Override
    public void init(){
        lastY = initialY;
        units = 0;

        terminalDelay = 0;
        alphaColor = 0.5f;
        fadeIn = true;

        brunoVertical = new BrunoVertical(1,this);
        brunoVertical.setPosition(Constants.VERTICAL_MIDDLE_X - brunoVertical.getWidth()/2,
                Constants.GROUND_LEVEL-brunoVertical.getHeight()/2);

        energyUnit = new Image(Assets.instance.bruno.energy);
        energyUnit.setSize(Constants.BASE-8,Constants.BASE);
        energyUnit.setScale(1,0);
        energyUnit.setPosition(Constants.VERTICAL_MIDDLE_X - energyUnit.getWidth()/2+2, brunoVertical.getY()+10);
        energyUnit.setColor(Color.YELLOW);

//        head = new Image(Assets.instance.bruno.headEnergy);
//        head.setSize(Constants.BASE,30);
//        head.setPosition(Constants.VERTICAL_MIDDLE_X - head.getWidth()/2, Constants.DETECTION_ZONE_END); // in 10 ends the base
//
//        thorax = new Image(Assets.instance.bruno.thoraxEnergy);
//        thorax.setSize(Constants.BASE,10);
//        thorax.setPosition(Constants.VERTICAL_MIDDLE_X - thorax.getWidth()/2,
//                Constants.DETECTION_ZONE_END-thorax.getHeight()+2);

        base = new Image(Assets.instance.bruno.baseEnergy);
        base.setSize(Constants.BASE-4,10);
        base.setPosition(Constants.VERTICAL_MIDDLE_X - base.getWidth()/2+2,
                brunoVertical.getY());

//
        stage.addActor(base);
        stage.addActor(energyUnit);
//        stage.addActor(thorax);
//        stage.addActor(head);
        stage.addActor(brunoVertical);


    }

    public void updateAnimated(ArrayList<Pair> toAdd, ArrayList<Integer> toRemoveValues){
        int toAddNr = 0;
        int toRemoveNr = 0;

        for(int i=0; i< toRemoveValues.size();i++) {
            toRemoveNr+= toRemoveValues.get(i);
        }
        for(int i=0; i< toAdd.size();i++) {
            toAddNr+=toAdd.get(i).getValue();
        }
        // at the end we see the difference
        if((toAddNr - toRemoveNr) > 0){
            addPieces(toAdd, toAddNr - toRemoveNr); // here we pass a
        }
        if((toAddNr - toRemoveNr) < 0){
            removeAnimatedPieces(Math.abs(toAddNr - toRemoveNr)); // we have to remove a positive number of pieces
        }
    }


    protected void addPieces(ArrayList<Pair> toAdd, int howMany){
        terminalDelay = animationSpeed*howMany;
        units+=howMany;
        lastY+=(Constants.BASE*howMany);

        energyUnit.addAction(Actions.scaleTo(1,units,terminalDelay));
        // calculate delay
        // calculate size



//        head.addAction(Actions.moveTo(head.getX(),lastY,terminalDelay));
//        thorax.addAction(Actions.moveTo(head.getX(),lastY-thorax.getHeight()+2,terminalDelay));
        brunoVertical.addAction(Actions.moveTo(brunoVertical.getX(),lastY-brunoVertical.getHeight()/2,terminalDelay));
        //thorax.toFront();
        //brunoVertical.toFront();
    }


    protected void removeAnimatedPieces(int toRemoveSum){

        terminalDelay = animationSpeed*toRemoveSum;
        lastY-=(Constants.BASE*toRemoveSum);
        units-=toRemoveSum;
        Gdx.app.log(TAG, " toRemoveSum "+toRemoveSum);


//        head.addAction(Actions.moveTo(head.getX(),lastY,terminalDelay));
//        thorax.addAction(Actions.moveTo(head.getX(),lastY-thorax.getHeight()+2,terminalDelay));
//        thorax.toFront();
        energyUnit.addAction(Actions.scaleTo(1,units,terminalDelay));
        brunoVertical.addAction(Actions.moveTo(brunoVertical.getX(),lastY-brunoVertical.getHeight()/2,terminalDelay));

        brunoVertical.toFront();

    }


    public void updateAlpha(float delta){
        //Gdx.app.log(TAG," update my alpha"+alphaColor);
        if(fadeIn){
            alphaColor+=(delta/2);
        }
        else
            alphaColor-=(delta/2);

        if(alphaColor > 0.9){
            fadeIn=!fadeIn;
        }
        if(alphaColor < 0.5)
            fadeIn=!fadeIn;

        energyUnit.setColor(255,255,0,alphaColor);



    }

    public BrunoVertical getBrunoVertical(){
        return brunoVertical;
    }

    @Override
    public boolean isUpdatingBrunosPositions(){
        return energyUnit.hasActions();
    }



}

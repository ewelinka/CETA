package ceta.game.screens;

import ceta.game.game.Assets;
import ceta.game.game.objects.TreeArrow;
import ceta.game.game.objects.TreeGear;
import ceta.game.util.AudioManager;
import ceta.game.util.Constants;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.alpha;

/**
 * Created by ewe on 4/24/17.
 */
public class TreeHalfScreen extends TreeScreen {
    private static final String TAG = TreeHalfScreen.class.getName();

    public TreeHalfScreen(DirectedGame game) {
        this(game, false);
    }

    public TreeHalfScreen(DirectedGame game, boolean gameInit) {
        super(game, gameInit);
    }

    @Override
    protected void addGears(){
        level1gear = new TreeGear(Assets.instance.tree.gear3,Assets.instance.tree.gear3inactive);
        level2gear = new TreeGear(Assets.instance.tree.gear2,Assets.instance.tree.gear2inactive);
        level3gear = new TreeGear(Assets.instance.tree.gear4,Assets.instance.tree.gear4inactive);
        level4gear = new TreeGear(Assets.instance.tree.gear6,Assets.instance.tree.gear6inactive);
        level5gear = new TreeGear(Assets.instance.tree.gear1,Assets.instance.tree.gear1inactive);
        level6gear = new TreeGear(Assets.instance.tree.gear5,Assets.instance.tree.gear5inactive);


        level1gear.setPosition(gearsPositions[0][0], gearsPositions[0][1]);
        stage.addActor(level1gear);

        level2gear.setPosition(gearsPositions[1][0], gearsPositions[1][1]);
        stage.addActor(level2gear);

        level3gear.setPosition(gearsPositions[2][0], gearsPositions[2][1]);
        stage.addActor(level3gear);

        level4gear.setPosition(gearsPositions[3][0], gearsPositions[3][1]);
        stage.addActor(level4gear);

        level5gear.setPosition(gearsPositions[4][0], gearsPositions[4][1]);
        stage.addActor(level5gear);

        level6gear.setPosition(gearsPositions[5][0], gearsPositions[5][1]);
        stage.addActor(level6gear);

        arrow = new TreeArrow(Assets.instance.tree.arrow);
        stage.addActor(arrow); // now add to place the arrow most to front
    }

    protected void enableGears(){
        int nowLevel = game.getLevelsManager().getLastLevelCompleted();
        int newActivated = 6;
        Gdx.app.log(TAG, " current level "+nowLevel);


        if(nowLevel < Constants.L6_HALF_COMPLETED_NR){
            level6gear.setActive(false);
            part6.setColor(1,1,1,0);
             Gdx.app.log(TAG, "6 gray");
            newActivated = 5;
        }
        if(nowLevel < Constants.L5_HALF_COMPLETED_NR){
            level5gear.setActive(false);
            part5.setColor(1,1,1,0);
             Gdx.app.log(TAG, "5 gray");
            newActivated = 4;
        }
        if(nowLevel < Constants.L4_HALF_COMPLETED_NR){
            level4gear.setActive(false);
            part4.setColor(1,1,1,0);
             Gdx.app.log(TAG, " 4 gray");
            newActivated = 3;
        }
        if(nowLevel < Constants.L3_HALF_COMPLETED_NR){
            level3gear.setActive(false);
            part3.setColor(1,1,1,0);
             Gdx.app.log(TAG, "3 gray");
            newActivated = 2;
        }
        if(nowLevel < Constants.L2_HALF_COMPLETED_NR){
            level2gear.setActive(false);
            part2.setColor(1,1,1,0);
             Gdx.app.log(TAG, "2 gray");
            newActivated = 1;
        }
        if(nowLevel < Constants.L1_HALF_COMPLETED_NR){
            level1gear.setActive(false);
            part1.setColor(1,1,1,0);
            newActivated = 0;
             Gdx.app.log(TAG, "1 gray");
        }

        if(!gameInit)
            enableGearsWithAnimation(newActivated);
        else{
            switch(newActivated){
                case 0:
                    part1.setColor(1,1,1,0);
                    arrow.setPosition(arrowPositions[0][0],arrowPositions[0][1]); // pos 1

                    break;
                case 1:
                    part1.setColor(1,1,1,0);
                    level1gear.setActiveTexture(Assets.instance.tree.gear3half);
                    level1gear.setActive(true);
                    //level1gear.setIsMoving(true,0.2f);

                    break;
                case 2:
                    part2.setColor(1,1,1,0);

                    arrow.setPosition(arrowPositions[1][0],arrowPositions[1][1]); // pos 2
                    level2gear.setActiveTexture(Assets.instance.tree.gear2half);
                    level2gear.setActive(true);
                    //level2gear.setIsMoving(true,0.2f);

                    break;
                case 3:
                    part3.setColor(1,1,1,0);
                    arrow.setPosition(arrowPositions[2][0],arrowPositions[2][1]); // pos 3
                    level3gear.setActiveTexture(Assets.instance.tree.gear4half);
                    level3gear.setActive(true);
                    //level3gear.setIsMoving(true,0.2f);

                    break;
                case 4:
                    part4.setColor(1,1,1,0);
                    arrow.setPosition(arrowPositions[3][0],arrowPositions[3][1]); // pos 4

                    level4gear.setActiveTexture(Assets.instance.tree.gear6half);
                    level4gear.setActive(true);
                    //level4gear.setIsMoving(true,0.2f);

                    break;
                case 5:
                    part5.setColor(1,1,1,0);
                    arrow.setPosition(arrowPositions[4][0],arrowPositions[4][1]); // pos 5
                    level5gear.setActiveTexture(Assets.instance.tree.gear1half);
                    level5gear.setActive(true);
                    break;
                case 6:
                    part6.setColor(1,1,1,0);
                    arrow.setPosition(arrowPositions[5][0],arrowPositions[5][1]); // pos 6
                    level6gear.setActiveTexture(Assets.instance.tree.gear5half);
                    level6gear.setActive(true);
                    //level6gear.setIsMoving(true,0.2f);
            }
        }
    }

    @Override
    protected void enableGearsWithAnimation(int newActivated){
        Gdx.app.log(TAG," === enableGearsWithAnimation "+newActivated);
//        if(newActivated!=0)
//            AudioManager.instance.playWithoutInterruptionLoud(Assets.instance.sounds.levelPassed);
        switch(newActivated){
            case 0:
                part1.setColor(1,1,1,0);
                arrow.setPosition(arrowPositions[0][0],arrowPositions[0][1]); // pos 1
                level1gear.setActiveTexture(Assets.instance.tree.gear3half);
                level1gear.setIsMoving(true,0.2f);
                break;
            case 1:
                part1.setColor(1,1,1,0);
                arrow.setPosition(arrowPositions[0][0],arrowPositions[0][1]); // pos 1
                level1gear.setActiveTexture(Assets.instance.tree.gear3half);
                level1gear.setActive(false);
                level1gear.activateGear();
                break;
            case 2:
                part2.setColor(1,1,1,0);
                arrow.setPosition(arrowPositions[1][0],arrowPositions[1][1]); // pos 2
                level2gear.setActiveTexture(Assets.instance.tree.gear2half);
                level2gear.setActive(false);
                level2gear.activateGear();
                break;
            case 3:
                part3.setColor(1,1,1,0);
                arrow.setPosition(arrowPositions[2][0],arrowPositions[2][1]); // pos 3
                level3gear.setActiveTexture(Assets.instance.tree.gear4half);
                level3gear.setActive(false);
                level3gear.activateGear();
                break;
            case 4:
                part4.setColor(1,1,1,0);
                arrow.setPosition(arrowPositions[3][0],arrowPositions[3][1]); // pos 4
                level4gear.setActiveTexture(Assets.instance.tree.gear6half);
                level4gear.setActive(false);
                level4gear.activateGear();
                break;
            case 5:
                part5.setColor(1,1,1,0);
                arrow.setPosition(arrowPositions[4][0],arrowPositions[4][1]); // pos 5
                level5gear.setActiveTexture(Assets.instance.tree.gear1half);
                level5gear.setActive(false);
                level5gear.activateGear();
                break;
            case 6:
                part6.setColor(1,1,1,0);
                arrow.setPosition(arrowPositions[5][0],arrowPositions[5][1]); // pos 6
                level6gear.setActiveTexture(Assets.instance.tree.gear5half);
                level6gear.setActive(false);
                level6gear.activateGear();
                // TODO thats all falks!
                break;

        }

    }
}

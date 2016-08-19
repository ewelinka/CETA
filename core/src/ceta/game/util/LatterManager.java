package ceta.game.util;

import ceta.game.game.objects.Latter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by ewe on 8/1/16.
 */
public class LatterManager {
    public static final String TAG = LatterManager.class.getName();
    private Stage stage;

    private ArrayList<Latter> latters;
    private  short [] remove;
    private  short [] add;
    private  short toRemove;
    private  short toAdd;
    private short initialX;
    private short lastY;

    private  MoveToAction moveToAction;

    public LatterManager(Stage stage){
        this.stage = stage;
        latters = new ArrayList<Latter>();
        initialX = 0;

    }

    public void init(){

        //initLatters();
    }

    private void initLatters(){

        Latter latter;
        // ojo!!! de 1 a 5!! es el value
        for(short i = 1;i<6;i++){
            latter = new Latter(i);
            //we "hide" the latter below the ground level
            latter.setPosition(initialX, -latter.getHeight() - Constants.BASE );
            stage.addActor(latter);
            latters.add(latter);
        }
//        Latter latter = new Latter((short)1);
//        latter.setPosition(-Constants.VIEWPORT_WIDTH/2 + Constants.OFFSET_X + Constants.BASE*2, -Constants.BASE );
//        stage.addActor(latter);
//        latters.add(latter);
//
//
//        Latter latterDouble = new Latter((short)2);
//        latterDouble.setPosition(-Constants.VIEWPORT_WIDTH/2 + Constants.OFFSET_X+ Constants.BASE*4 , -Constants.BASE);
//        stage.addActor(latterDouble);
//        latters.add(latterDouble);
    }


    public void update(short toRemove, short toAdd, short[] remove, short [] add){
        this.toRemove = toRemove;
        this.toAdd = toAdd;
        this.remove = remove;
        this.add = add;

        updatePositions();
    }



    private void updatePositions(){
        // we have to remove something. perhaps more than one thing.
        if(toRemove > 0){
            Gdx.app.debug(TAG,"to remove! "+toRemove);
            short removed = 0;
            // we start at the end
            for(short i=(short)(latters.size()-1);i>=0;i--){
                // we have to adjust the value of the latter to array range
                // latter of value 1 should be in index 0
                short currentLatterValueIndex = (short)(latters.get(i).getLatterValue() - 1);

                if(remove[currentLatterValueIndex] > 0){
                    Gdx.app.debug(TAG, "we found latter to remove!! index: "+currentLatterValueIndex+" value: "+(currentLatterValueIndex+1));
                    // we delete this latter
                    latters.get(i).setColor(1,0,0,1);
                    // remove Actor
                    latters.get(i).remove();
                    //remove from latters
                    latters.remove(i);
                    // we update remove[]
                    remove[currentLatterValueIndex]-=1;
                    // we update removed
                    removed+=1;
                }
            }

            // we update the positions of the latters that are still there
            lastY = -Constants.BASE;
            for(short i=0;i<latters.size();i++){
                // TODO see how to reuse the actions
                // http://www.gamefromscratch.com/post/2014/10/28/Re-using-actions-in-LibGDX.aspx
                if(latters.get(i).getY() != lastY){
                    moveToAction = new MoveToAction();
                    moveToAction.setPosition(initialX,lastY);
                    moveToAction.setDuration(1f);
                    latters.get(i).addAction(moveToAction);
                    Gdx.app.debug(TAG," we added action to latter number "+i+" and moved it to "+lastY);
                }

                lastY += latters.get(i).getHeight();
            }
        }
        if(toAdd>0){
            Gdx.app.debug(TAG,"to add! "+toAdd + Arrays.toString(add));
            for(short i = 0;i<add.length;i++){
                if(add[i]>0){
                    // TODO we should check how many should we add, perhaps there are 2 to add..
                    // TODO here we should check if we can re-use a latter
                    // but now we just create a new one
                    Latter latterToAdd = new Latter((short)(i+1));

                    // TODO we should add it below and move upwards all the latters above
                    // we put if at the top of all already existing latters
                    // so we have to check the position of last latter added
                    if(latters.size()>0){
                        Gdx.app.debug(TAG,"we add one latter at the end!");
                        latterToAdd.setPosition(initialX, lastY);
                    }else{
                        Gdx.app.debug(TAG,"we add the first latter ever!!");
                        latterToAdd.setPosition(initialX,-Constants.BASE );
                        lastY = -Constants.BASE;

                    }
                    lastY += latterToAdd.getHeight();
                    stage.addActor(latterToAdd);
                    latters.add(latterToAdd);
                }
            }
        }
    }

    public Latter getLastLatter(){
        if(latters.size()>0)
            return latters.get(latters.size()-1);
        return null;
    }
}

package ceta.game.managers;

import ceta.game.game.objects.ArmPieceAnimated;
import ceta.game.game.objects.BrunoPieceAnimated;
import ceta.game.util.Constants;
import ceta.game.util.Pair;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;

import java.util.ArrayList;

/**
 * Created by ewe on 11/9/16.
 */
public class AnimatedBrunoManager extends BrunosManager {
    public static final String TAG = AnimatedBrunoManager.class.getName();
    private ArrayList<BrunoPieceAnimated> brunoPiecesAnim;
    private float terminalDelay;
    private float currentDelayPassed; //TODO use if no action submit
    private final float animationSpeed = 0.3f;

    public AnimatedBrunoManager(Stage stage) {
        super(stage);
    }

    @Override
    public void init(){
        lastY = initialY;
        brunoPiecesAnim = new ArrayList<BrunoPieceAnimated>();
        terminalDelay = 0;
        currentDelayPassed = 0;

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
        if((toAddNr - toRemoveNr) >= 0){
            addPieces(toAdd, toAddNr - toRemoveNr); // here we pass a
        }else{
            removeAnimatedPieces(Math.abs(toAddNr - toRemoveNr)); // we have to remove a positive number of pieces
        }
    }


    protected void addPieces(ArrayList<Pair> toAdd, int howMany){
        terminalDelay = 0; // TODO we check if we should start in 0 or there is somenthing in movement; change if NO action submit
        int currentKey = 0; // we need it if we have to check for ids to generate new false ids

        for(int i=0; i< howMany;i++) {
            if(i>9)
                currentKey+=1;

            Gdx.app.log(TAG, " add "+ toAdd.get(currentKey).getKey()+" part "+i + " lasty "+lastY+" delay "+ terminalDelay);
            BrunoPieceAnimated pieceToAdd = new BrunoPieceAnimated(1, this);
            stage.addActor(pieceToAdd);

            pieceToAdd.setId(toAdd.get(currentKey).getKey()*10+i); //we have to invent id because one virtual piece is mapped into up to 5 arm pieces
            pieceToAdd.setPosition(Constants.VERTICAL_MIDDLE_X-pieceToAdd.getWidth()/2,lastY);
            lastY+=pieceToAdd.getHeight();

            pieceToAdd.expandMe(terminalDelay, animationSpeed); // will grow in 1 sec
            terminalDelay += animationSpeed;

            brunoPiecesAnim.add(pieceToAdd);

        }
    }


    protected void removeFromArrayByIdAndUpdatePositions(int id){
        for(int i=brunoPiecesAnim.size()-1;i>=0;i--){
            if(brunoPiecesAnim.get(i).getId() == id){
                brunoPiecesAnim.remove(i); //remove from armPieces array
                break;
            }
        }
    }

    @Override
    protected void removeActorByIndex(int which){
        Gdx.app.log(TAG, "removeActorByIndex "+ which + " with id "+brunoPiecesAnim.get(which).getId());
        brunoPiecesAnim.get(which).collapseMe(terminalDelay, animationSpeed); // remove Actor
    }

    public BrunoPieceAnimated getLastAnimatedBrunoPiece(){
        if(brunoPiecesAnim.size()>0)
            return brunoPiecesAnim.get(brunoPiecesAnim.size()-1);
        return null;
    }

    protected void removeAnimatedPieces(int toRemoveSum){
        int removeMaxIndex = brunoPiecesAnim.size()-1;

        terminalDelay = 0; //TODO change if NO action submit
        Gdx.app.log(TAG, " toRemoveSum "+toRemoveSum);
        for(int i = removeMaxIndex;i>=0;i--) {
            // start to look!
            if(inMovementIds.contains(brunoPiecesAnim.get(i).getId())){
                Gdx.app.log(TAG, "we have to look for another piece, this one is busy "+brunoPiecesAnim.get(i).getId());
            }else{
                removeActorByIndex(i);
                lastY-= Constants.BASE; // we know that each arm part is = Constants.BASE
                toRemoveSum-=1;
                terminalDelay+=animationSpeed;
                if(toRemoveSum<=0)
                    break;
            }
        }
    }

}

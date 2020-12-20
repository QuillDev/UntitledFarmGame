package tech.quilldev.Engine.Actions.WorldActions;

import tech.quilldev.Engine.Actions.Action;
import tech.quilldev.Engine.GameManager;
import tech.quilldev.MathConstants;

public class WorldTickBasedAction extends Action {

    private final float rate;

    /**
     * Constructor for new actions
     * @param gameManager main game manager
     */
    public WorldTickBasedAction(GameManager gameManager, float rate) {
        super(gameManager);
        this.rate = rate;
    }

    public boolean execute(){

        //roll to see if we grow
        var roll = Math.floor(Math.random() * MathConstants.TICKS_PER_SECOND * rate);
        return roll == 0f;
    }


}

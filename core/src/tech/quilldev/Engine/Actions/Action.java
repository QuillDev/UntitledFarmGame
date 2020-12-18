package tech.quilldev.Engine.Actions;

import tech.quilldev.Engine.GameManager;

public abstract class Action {

    //every action needs the game manager
    protected final GameManager gameManager;

    /**
     * Constructor for new actions
     * @param gameManager main game manager
     */
    public Action(GameManager gameManager){
        this.gameManager = gameManager;
    }

    //execute the action
    protected boolean execute() {
        return false;
    }
}

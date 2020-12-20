package tech.quilldev.Engine.Actions.PlayerActions;

import tech.quilldev.Engine.Actions.Action;
import tech.quilldev.Engine.GameManager;

public class PickupAction extends Action {
    /**
     * Constructor for new actions
     * @param gameManager main game manager
     */
    public PickupAction(GameManager gameManager) {
        super(gameManager);
    }

    @Override
    public boolean execute() {

        //the entity manager
        var entityManager = gameManager.entityManager;

        //get the player and item manager
        var player = entityManager.getPlayer();
        var itemManager = entityManager.getItemManager();

        //check if there is a collision
        var collision = itemManager.getFirstCollision(player);

        //if there was no collision return false
        if(collision == null){
            return false;
        }

        //if the player isn't holding an item then pickup the item and hold it
        player.pickup(collision);

        //if there was have the player pick it up
        itemManager.removeItems(collision);

        return true;
    }
}

package tech.quilldev.Engine.Actions.InventoryActions;

import tech.quilldev.Engine.Actions.Action;
import tech.quilldev.Engine.GameManager;

public class InventorySwapAction extends Action {
    /**
     * Constructor for new actions
     * @param gameManager main game manager
     */
    public InventorySwapAction(GameManager gameManager) {
        super(gameManager);
    }

    public boolean execute(int slot) {

        //get the player and inventory we need
        var player = gameManager.entityManager.getPlayer();

        //the player to draw
        player.setHeldItem(slot);

        return true;
    }
}

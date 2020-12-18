package tech.quilldev.Engine.Actions.InventoryActions;

import tech.quilldev.Engine.Actions.Action;
import tech.quilldev.Engine.GameManager;

public class ToggleInventoryAction extends Action {
    /**
     * Constructor for new actions
     * @param gameManager main game manager
     */
    public ToggleInventoryAction(GameManager gameManager) {
        super(gameManager);
    }

    @Override
    public boolean execute() {

        //toggle whether the inventory is enabled
        var inventory = gameManager.entityManager.getPlayer().getInventory();
        inventory.toggleEnabled();

        return true;
    }
}

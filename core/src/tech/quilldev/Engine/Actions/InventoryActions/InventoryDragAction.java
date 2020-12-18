package tech.quilldev.Engine.Actions.InventoryActions;

import com.badlogic.gdx.math.Vector3;
import tech.quilldev.Engine.Actions.Action;
import tech.quilldev.Engine.GameManager;

public class InventoryDragAction extends Action {
    /**
     * Constructor for new actions
     * @param gameManager main game manager
     */
    public InventoryDragAction(GameManager gameManager) {
        super(gameManager);
    }

    public void execute(Vector3 touchDown, Vector3 touchRelease){
        var inventory = gameManager.entityManager.getPlayer().getInventory();

        //get indexes
        var indexFrom = inventory.getIndexFromVector(touchDown);
        var indexTo = inventory.getIndexFromVector(touchRelease);

        //if either of the indexes are null then return false
        if(indexFrom == null || indexTo == null){
            return;
        }
        //swap inventory slots
        inventory.swapSlots(indexFrom, indexTo);
    }
}

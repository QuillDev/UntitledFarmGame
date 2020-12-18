package tech.quilldev.Engine.Actions.PlayerActions;

import tech.quilldev.Engine.Actions.Action;
import tech.quilldev.Engine.Entities.StaticEntities.Items.Item;
import tech.quilldev.Engine.GameManager;

public class DropAction extends Action {
    /**
     * Constructor for new actions
     * @param gameManager main game manager
     */
    public DropAction(GameManager gameManager) {
        super(gameManager);
    }

    @Override
    public boolean execute() {


        var entityManager = gameManager.entityManager;
        var player = entityManager.getPlayer();
        var itemManager = entityManager.getItemManager();

        //if we're holding an item, null it out
        if(player.holdingItem()){
            itemManager.registerItems(player.getHeldItem());
            player.dropItem();
            return true;
        }
        return false;
    }
}

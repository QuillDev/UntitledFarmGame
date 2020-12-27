package tech.quilldev.Engine.Actions.FarmActions;

import tech.quilldev.Engine.Entities.StaticEntities.Items.ItemType;
import tech.quilldev.Engine.GameManager;
import tech.quilldev.Engine.Map.Tiles.TileType;


public class FarmPlantAction extends FarmPrepareAction {
    public FarmPlantAction(GameManager gameManager, ItemType itemHeld, TileType standingOn, TileType changeTo) {
        super(gameManager, itemHeld, standingOn, changeTo);
    }

    @Override
    public boolean execute() {

        //if the first action failed, fail this one too
        if(!super.execute()) {
            return false;
        }

        //get the player
        var player = gameManager.entityManager.getPlayer();

        //get the current item from the inventory
        var item = player.getInventory().getItem(this.requiredItem);

        //subtract 1 from the stack count
        item.stackCount -= 1;

        //if the stack count hit zero, remove it
        if(item.getStackCount() == 0){
            player.getInventory().dropItem(item);
        }


        return true;
    }
}

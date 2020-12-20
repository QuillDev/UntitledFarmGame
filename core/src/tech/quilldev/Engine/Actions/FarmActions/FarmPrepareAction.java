package tech.quilldev.Engine.Actions.FarmActions;

import tech.quilldev.Engine.Actions.Action;
import tech.quilldev.Engine.Entities.StaticEntities.Items.ItemType;
import tech.quilldev.Engine.GameManager;
import tech.quilldev.Engine.Map.Tiles.TileType;


public class FarmPrepareAction extends Action {

    protected final ItemType requiredItem;
    protected final TileType standingOn;
    protected final TileType changeTo;

    public FarmPrepareAction(GameManager gameManager, ItemType itemType, TileType standingOn, TileType changeTo) {
        super(gameManager);
        this.requiredItem = itemType;
        this.standingOn = standingOn;
        this.changeTo = changeTo;
    }

    public boolean execute(){

        //get the player and the held item
        var player = this.gameManager.entityManager.getPlayer();

        //if the player isn't holding an item, return
        if(!player.holdingItem()){
            return false;
        }

        //if the held item is not the given type, return false;
        if(!(player.getHeldItem().isType(requiredItem))){
            return false;
        }

        //get the map manager
        var mapManager = gameManager.mapManager;

        //if the player isn't standing on soil, return
        if(!mapManager.tileAtPositionIsType(player.getPosition(), standingOn)) {
            return false;
        }

        return mapManager.changeTileToTileType(player.getPosition(), changeTo);
    }
}

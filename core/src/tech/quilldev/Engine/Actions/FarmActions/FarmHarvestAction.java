package tech.quilldev.Engine.Actions.FarmActions;

import tech.quilldev.Engine.Actions.Action;

import tech.quilldev.Engine.Entities.StaticEntities.Items.CloneItem;
import tech.quilldev.Engine.Entities.StaticEntities.Items.Item;
import tech.quilldev.Engine.Entities.StaticEntities.Items.ItemType;
import tech.quilldev.Engine.GameManager;
import tech.quilldev.Engine.Map.Tiles.TileType;
import tech.quilldev.Engine.Utilities.Position;
import tech.quilldev.MathConstants;

public class FarmHarvestAction extends Action {


    private final ItemType itemType;
    private final TileType standingOn;
    private final TileType changeTo;
    private final Item[] drops;

    public FarmHarvestAction(GameManager gameManager, ItemType itemType, TileType standingOn, TileType changeTo, Item... drops) {
        super(gameManager);

        this.itemType = itemType;
        this.standingOn = standingOn;
        this.changeTo = changeTo;
        this.drops = drops;
    }

    public boolean execute(){
        var player = this.gameManager.entityManager.getPlayer();

        //if the player isn't holding the item, return false
        if(!player.holdingItemOfType(itemType)){
            return false;
        }

        //get the map manager
        var mapManger = gameManager.mapManager;
        var position = player.getPosition();

        //if we're not standing on the tile type we need to return false
        if(!mapManger.tileAtPositionIsType(position, standingOn)){
            return false;
        }

        //get the tile at the players position
        var cell = mapManger.getCellAtPosition(position);

        //if the tile is null return false
        if(cell == null){
            return false;
        }

        //change the tile
        var changed = mapManger.changeTileToTileType(cell, changeTo);

        //if the tile didnt change return false;
        if(!changed){
            return false;
        }

        //get the base drop position for new drops
        var baseDropPosition = cell.getPosition();

        //drop the drops
        for(var drop : drops){

            //calculate a "random" drop position
            var posToSet = new Position(baseDropPosition);
            posToSet.jiggle();

            //Register
            gameManager.entityManager
                    .getItemManager()
                    .registerItems(new CloneItem(drop, posToSet));
        }
        return false;
    }
}

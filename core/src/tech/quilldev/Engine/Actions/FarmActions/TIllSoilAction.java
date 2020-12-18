package tech.quilldev.Engine.Actions.FarmActions;

import tech.quilldev.Engine.Actions.Action;
import tech.quilldev.Engine.Entities.StaticEntities.Items.ItemType;
import tech.quilldev.Engine.GameManager;
import tech.quilldev.Engine.Map.Tiles.TileType;

public class TIllSoilAction extends Action {
    /**
     * Constructor for new actions
     * @param gameManager main game manager
     */
    public TIllSoilAction(GameManager gameManager) {
        super(gameManager);
    }

    @Override
    public boolean execute(){

        //get the player and the held item
        var player = this.gameManager.entityManager.getPlayer();

        //if the held item is not a hoe, return false;
        if(!player.holdingItemOfType(ItemType.HOE)){
            return false;
        }

        //get the map manager
        var mapManager = gameManager.mapManager;

        //if the player isn't standing on soil, return
        if(!mapManager.tileAtPositionIsType(player.getPosition(), TileType.SOIL)) {
            return false;
        }

        return mapManager.changeTileToTileType(player.getPosition(), TileType.DIRT);
    }
}

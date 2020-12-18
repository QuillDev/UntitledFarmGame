package tech.quilldev.Engine.Actions.WorldActions;

import tech.quilldev.Engine.Actions.Action;
import tech.quilldev.Engine.GameManager;
import tech.quilldev.Engine.Map.Tiles.TileType;
import tech.quilldev.MathConstants;

public class GrassGrowAction extends Action {
    /**
     * Constructor for new actions
     * @param gameManager main game manager
     */
    public GrassGrowAction(GameManager gameManager) {
        super(gameManager);
    }

    public boolean execute(){

        //get the map manager
        var mapManager = gameManager.mapManager;

        //average time before a tile grows (Last number is seconds)
        var timePerTile = MathConstants.UPDATES_PER_SECOND * 15f;

        //roll to see if we grow
        var roll = Math.floor(Math.random() * timePerTile);

        //if we're not growing any tiles, return
        if(roll != 0){
            return false;
        }

        //get all tiles of the given type
        var tiles = mapManager.getTilesOfType(0, TileType.GRASS);

        //if there are no tiles of that type return false;
        if(tiles.size() == 0){
            return false;
        }

        //Select a random tile of that type
        var selectedTile = tiles.get((int) Math.floor(Math.random() * tiles.size()));

        //change that tile to the given tile type
        mapManager.changeTileToTileType(selectedTile.getIndex(), 1, TileType.FOLIAGE);
        return true;
    }
}

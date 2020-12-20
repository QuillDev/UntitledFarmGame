package tech.quilldev.Engine.Actions.WorldActions;

import tech.quilldev.Engine.Actions.Action;
import tech.quilldev.Engine.GameManager;
import tech.quilldev.Engine.Map.Tiles.TileType;
import tech.quilldev.MathConstants;

public class WorldGrowTileAction extends WorldTickBasedAction {


    private final TileType check;
    private final TileType change;

    /**
     * Constructor for new actions
     * @param gameManager main game manager
     */
    public WorldGrowTileAction(GameManager gameManager, TileType check, TileType change, float rate) {
        super(gameManager, rate);

        //set the requirements for this call
        this.check = check;
        this.change = change;
    }

    public boolean execute(){

        if(super.execute()){
            //get the map manager
            var mapManager = gameManager.mapManager;

            //get all tiles of the given type
            var tiles = mapManager.getTilesOfType(0, check);

            //if there are no tiles of that type return false;
            if(tiles.size() == 0){
                return false;
            }

            //Select a random tile of that type
            var selectedTile = tiles.get((int) Math.floor(Math.random() * tiles.size()));

            return mapManager.changeTileToTileType(selectedTile.getCell(), change);
        }

        return false;

    }
}

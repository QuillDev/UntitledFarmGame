package tech.quilldev.Engine.Actions.WorldActions;

import tech.quilldev.Engine.Actions.Action;
import tech.quilldev.Engine.Entities.StaticEntities.Objects.CloneObject;
import tech.quilldev.Engine.Entities.StaticEntities.Objects.GameObject;
import tech.quilldev.Engine.GameManager;
import tech.quilldev.Engine.Map.Tiles.TileType;
import tech.quilldev.MathConstants;

public class WorldSpawnObjectAction extends Action {


    private final TileType check;
    private final GameObject object;
    private final float rate; //in seconds

    /**
     * Constructor for new actions
     * @param gameManager main game manager
     */
    public WorldSpawnObjectAction(GameManager gameManager, TileType check, GameObject object, float rate) {
        super(gameManager);

        //set the requirements for this call
        this.check = check;
        this.object = object;
        this.rate = rate;
    }

    public boolean execute(){

        //get the map manager
        var mapManager = gameManager.mapManager;

        //average time before a tile grows (Last number is seconds)
        var timePerTile = MathConstants.UPDATES_PER_SECOND * rate;

        //roll to see if we grow
        var roll = Math.floor(Math.random() * timePerTile);

        //if we're not growing any tiles, return
        if(roll != 0){
            return false;
        }

        //get all tiles of the given type
        var tiles = mapManager.getTilesOfType(0, check);

        //if there are no tiles of that type return false;
        if(tiles.size() == 0){
            return false;
        }

        //Select a random tile of that type
        var selectedTile = tiles.get((int) Math.floor(Math.random() * tiles.size()));

        //generate the object position
        var objPos = mapManager.cellIndexToPosition(selectedTile.getIndex());

        //create a new game object cloning the properties of the current object
        var objToAdd = new CloneObject(this.object, objPos);

        //get the object manager
        var objManager = gameManager.entityManager.getObjectManager();

        //if there's no other object where we're trying to spawn out object spawn it
        if(objManager.getFirstCollision(objToAdd) == null){
            objManager.registerObjects(objToAdd);
            return true;
        }

        return false;

    }
}

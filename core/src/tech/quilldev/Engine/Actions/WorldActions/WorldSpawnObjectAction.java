package tech.quilldev.Engine.Actions.WorldActions;

import tech.quilldev.Engine.Entities.StaticEntities.Objects.CloneObject;
import tech.quilldev.Engine.Entities.StaticEntities.Objects.GameObject;
import tech.quilldev.Engine.GameManager;
import tech.quilldev.Engine.Map.Tiles.TileType;

public class WorldSpawnObjectAction extends WorldTickBasedAction {


    private final TileType check;
    private final GameObject object;

    /**
     * Constructor for new actions
     * @param gameManager main game manager
     */
    public WorldSpawnObjectAction(GameManager gameManager, TileType check, GameObject object, float rate) {
        super(gameManager, rate);

        //set the requirements for this call
        this.check = check;
        this.object = object;
    }

    public boolean execute(){

        if(super.execute()){
            //get the map manager
            var mapManager = gameManager.mapManager;

            //get all tiles of the given type
            var tiles = mapManager.getTilesOfType(check);

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

            //if it's an impassable object make sure the player isn't on it.. that would be bad
            if(!objToAdd.isPassable()){

                //if the object to add would be colliding with the player then don't spawn it
                if(objToAdd.collidingWith(gameManager.entityManager.getPlayer())){
                    return false;
                }
            }

            //get the object manager
            var objManager = gameManager.entityManager.getObjectManager();

            //if there's no other object where we're trying to spawn out object spawn it
            if(objManager.getFirstCollision(objToAdd) == null){
                objManager.registerObjects(objToAdd);
                return true;
            }
        }

        return false;
    }
}

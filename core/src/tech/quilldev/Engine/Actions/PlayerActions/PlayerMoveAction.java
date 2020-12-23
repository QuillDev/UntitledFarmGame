package tech.quilldev.Engine.Actions.PlayerActions;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import tech.quilldev.Engine.Actions.Action;
import tech.quilldev.Engine.Console.GameConsole;
import tech.quilldev.Engine.Entities.DynamicEntities.Dummy;
import tech.quilldev.Engine.Entities.DynamicEntities.DynamicEntity;
import tech.quilldev.Engine.Entities.Entity;
import tech.quilldev.Engine.GameManager;
import tech.quilldev.Engine.Map.Map;
import tech.quilldev.Engine.Utilities.Position;
import tech.quilldev.MathConstants;

public class PlayerMoveAction extends Action {
    /**
     * Constructor for new actions
     *
     * @param gameManager main game manager
     */
    public PlayerMoveAction(GameManager gameManager) {
        super(gameManager);
    }

    @Override
    public boolean execute(){

        //get the player
        var player = gameManager.entityManager.getPlayer();

        //if the inventory is open, return
        if(player.getInventory().isEnabled()){
            return false;
        }

        //calculate the speed depending on the framerate
        var speed = 3.5f * MathConstants.WORLD_UNIT * Gdx.graphics.getDeltaTime();

        //create a new position
        var position = new Position();

        //Determine what values to add to the players position
        if(Gdx.input.isKeyPressed(Input.Keys.W)){
            position.addY(speed);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.A)){
            position.addX(-speed);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.S)){
            position.addY(-speed);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.D)) {
            position.addX(speed);
        }

        //get the map manager
        var mapManager = gameManager.mapManager;

        //if the move was legal add those changes to the player
        if(!legalMove(player, position, mapManager.getCurrentMap())){
            return false;
        }


        //if we have a held item
        if(player.holdingItem()){
            //get the held item and update it
            var item = player.getHeldItem();
            item.setPosition(player.getPosition());
        }

        return true;
    }

    /**
     * Check if the move is legal for that entity
     * @param entity whether the move is legal for that entity
     * @param posToAdd the position to add to the current one for checking
     * @return whether that move was legal
     */
    private boolean legalMove(DynamicEntity entity, Position posToAdd, Map map){

        //if the game console is visible return false
        if(GameConsole.isVisible()){
            return false;
        }
        //create "dummy" entity based on the player
        var dummy = new Dummy(entity);
        dummy.addPosition(posToAdd);

        //get the dummy's collider
        var dCollider = dummy.getCollider();

        //if the collider isn't colliding with the map return true.
        if(!dCollider.collidingWith(map.getCollider())) {


            //check if we're colliding with any impassable objects, return false if we are
            for(var object : gameManager.entityManager.getAllObjectCollisions(dummy)){

                //if the object is impassable return false
                if(!object.isPassable()){
                    return false;
                }
            }

            entity.addPosition(posToAdd);
            return true;
        }

        return false;
    }
}

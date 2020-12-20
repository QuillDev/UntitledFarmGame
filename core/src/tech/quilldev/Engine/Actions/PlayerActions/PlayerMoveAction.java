package tech.quilldev.Engine.Actions.PlayerActions;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import tech.quilldev.Engine.Actions.Action;
import tech.quilldev.Engine.Entities.DynamicEntities.DynamicEntity;
import tech.quilldev.Engine.Entities.Entity;
import tech.quilldev.Engine.GameManager;
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
        var speed = 4f * MathConstants.WORLD_UNIT * Gdx.graphics.getDeltaTime();

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
        if(!mapManager.legalMove(player, position)){
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
}

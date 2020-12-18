package tech.quilldev.Engine.Rendering;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import tech.quilldev.Engine.Entities.DynamicEntities.Player;

public class Camera2D extends OrthographicCamera {

    /**
     * Create the camera
     */
    public Camera2D(){

        //set the camera to be orthographic
        this.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        this.zoom = .25f;
    }

    /**
     * set the camera to the players position
     * @param player the player to lock to
     */
    public void setToPlayerPosition(Player player){
        this.position.x = player.getPosition().getX();
        this.position.y = player.getPosition().getY();
    }

    /**
     * set the camera to be locked to the player and update the screen
     * @param player the player to lock to
     */
    public void update(Player player){
        this.setToPlayerPosition(player);
        this.update();
    }


}
package tech.quilldev.Engine.Entities;

import com.badlogic.gdx.graphics.g2d.Batch;
import tech.quilldev.Engine.Entities.DynamicEntities.Player;
import tech.quilldev.Engine.Entities.StaticEntities.Items.ItemManager;

public class EntityManager {

    private final Player player;
    private final ItemManager itemManager;

    public EntityManager(){
        this.player = new Player();
        this.itemManager = new ItemManager();
    }

    /**
     * Update the states of all entities
     */
    public void update(){
    }

    /**
     * Render all of the entities in our entityList
     * @param batch the batch to render to
     */
    public void render(Batch batch){

        //render all items in the item manager
        this.itemManager.render(batch);

        //render the player last so they're above any items
        this.player.render(batch);
    }

    /**
     * Get the player from the entityManager
     * @return the player
     */
    public Player getPlayer(){
        return this.player;
    }

    /**
     * Get the item manager
     * @return the item manager
     */
    public ItemManager getItemManager() {
        return itemManager;
    }

}

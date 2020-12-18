package tech.quilldev.Engine.Entities.StaticEntities.Items;

import com.badlogic.gdx.graphics.g2d.Batch;
import tech.quilldev.Engine.Entities.Entity;

import java.util.ArrayList;
import java.util.Arrays;

public class ItemManager {

    //the item list
    ArrayList<Item> items;

    //Creates the item manager
    public ItemManager(){
        this.items = new ArrayList<>();
    }

    /**
     * Get the first entity in the item list we're colliding with
     * @param entity the entity to check collisions with
     * @return the entity we're colliding with
     */
    public Item getFirstCollision(Entity entity){
        for(Item item : items){
            if(item.colliding(entity)){
                System.out.println("COLLIDING WITH " + item);
                return item;
            }
        }

        return null;
    }
    /**
     * Render all of the items to the sprite batch
     * @param batch the batch to render to
     */
    public void render(Batch batch){
        for(Item item : items){
            item.render(batch);
            item.getCollider().render(batch);
        }
    }

    /**
     * Register items to the item manager
     * @param items to remove
     */
    public void registerItems(Item... items){
        //add all items to the item list
        this.items.addAll(Arrays.asList(items));
    }

    /**
     * Remove all of the items given
     * @param items to remove
     */
    public void removeItems(Item... items){
        this.items.removeAll(Arrays.asList(items));
    }
}

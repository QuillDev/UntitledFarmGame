package tech.quilldev.Engine.Entities.StaticEntities.Items;

import com.badlogic.gdx.graphics.g2d.Batch;
import tech.quilldev.Engine.Console.GameConsole;
import tech.quilldev.Engine.Entities.Entity;

import java.util.ArrayList;
import java.util.Arrays;

public class ItemManager {

    //the item list
    private final ArrayList<Item> items;

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
            if(item.collidingWith(entity)){
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
        }
    }

    /**
     * Register items to the item manager
     * @param items to remove
     */
    public void registerItems(Item... items){
        for(var item : items){
            GameConsole.log(String.format("Created item of Type %s @ %s", item.getItemType(), item.getPosition()));
        }
        //add all items to the item list
        this.items.addAll(Arrays.asList(items));
    }

    /**
     * Register items to the item manager
     * @param items to remove
     */
    public void registerItems(ArrayList<Item> items){
        for(var item : items){
            GameConsole.log(String.format("Created item of Type %s @ %s", item.getItemType(), item.getPosition()));
        }
        //add all items to the item list
        this.items.addAll(items);
    }

    /**
     * Get the item array list
     * @return the items list
     */
    public ArrayList<Item> getItems() {
        return items;
    }

    /**
     * Remove all of the items given
     * @param items to remove
     */
    public void removeItems(Item... items){
        this.items.removeAll(Arrays.asList(items));
    }
}

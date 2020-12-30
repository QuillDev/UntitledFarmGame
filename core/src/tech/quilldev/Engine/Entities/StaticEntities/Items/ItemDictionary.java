package tech.quilldev.Engine.Entities.StaticEntities.Items;

import tech.quilldev.Engine.Entities.StaticEntities.Objects.GameObject;
import tech.quilldev.Engine.Utilities.Position;

import java.util.HashMap;

public class ItemDictionary {

    private static HashMap<ItemType, Item> itemMap;

    /**
     * Create the object dictionary
     */
    public ItemDictionary(){
        itemMap = new HashMap<>();

        //Register all objects
        itemMap.put(ItemType.ROCK, new Rock());
        itemMap.put(ItemType.HOE, new Hoe());
        itemMap.put(ItemType.CARROT, new Carrot());
        itemMap.put(ItemType.PICKAXE, new Pickaxe());
        itemMap.put(ItemType.SCYTHE, new Scythe());
        itemMap.put(ItemType.SEEDS, new Seeds());
    }

    /**
     * Get the object from the given type
     * @param type of the object to get
     * @return the game object
     */
    public static Item getItem(ItemType type){
        return itemMap.get(type);
    }

    /**
     * Clone the object and set the position to the given one
     * @param type of object to clone
     * @param position to clone it at
     * @return the new object at the position
     */
    public static Item cloneItemOfType(ItemType type, Position position){

        //get the object from get item
        var object = getItem(type);

        //if the object is null, return null
        if(object == null){
            return null;
        }

        return new CloneItem(object, position);
    }

    /**
     * Clone the item and give it a default position
     * @param type of the item to clone
     * @return the object
     */
    public static Item cloneItemOfType(ItemType type){
        return cloneItemOfType(type, new Position());
    }
}

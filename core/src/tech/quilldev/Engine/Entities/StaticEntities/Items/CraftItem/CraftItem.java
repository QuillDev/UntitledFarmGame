package tech.quilldev.Engine.Entities.StaticEntities.Items.CraftItem;

import tech.quilldev.Engine.Entities.StaticEntities.Items.CloneItem;
import tech.quilldev.Engine.Entities.StaticEntities.Items.Item;
import tech.quilldev.Engine.GUI.Inventory;
import tech.quilldev.Engine.Utilities.Position;

import java.util.ArrayList;

public abstract class CraftItem extends Item {

    protected ArrayList<Item> materials;

    /**
     * Create a craft item based on the given item
     * @param item to base the item on
     */
    public CraftItem(Item item){
        super(item.getTexture(), new Position(item.getPosition()), item.getItemType());
        this.materials = new ArrayList<>();
        this.registerRecipe();
    }

    /**
     * Craft the given item
     * @param inventory to craft with
     * @return the item we crafted
     */
    public Item craft(Inventory inventory){

        //Check if we can craft the item
        for(var material : materials){
            if(!inventory.containsItem(material)){
                return null;
            }

            //get that item
            var invItem = inventory.getItemOfType(material.getItemType());

            //if the item stack count is insufficient, return
            if(invItem.stackCount < material.stackCount){
                return null;
            }
        }

        //do the craft if we have the materials
        for(var material : materials){

            //get the item of type material
            var invItem = inventory.getItemOfType(material.getItemType());

            //subtract the materials from the inventory that we need
            invItem.stackCount -= material.stackCount;

            //if the stack count hits zero, remove that item from the inventory
            if(invItem.stackCount == 0){
                inventory.removeItem(invItem);
            }
        }

        return new CloneItem(this);
    }

    //OVERRIDE IN OTHER METHODS
    public abstract void registerRecipe();
}

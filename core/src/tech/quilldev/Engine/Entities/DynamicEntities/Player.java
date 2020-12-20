package tech.quilldev.Engine.Entities.DynamicEntities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import tech.quilldev.Engine.Entities.StaticEntities.Items.Item;
import tech.quilldev.Engine.Entities.StaticEntities.Items.ItemType;
import tech.quilldev.Engine.GUI.Inventory;
import tech.quilldev.Engine.Utilities.Position;

public class Player extends DynamicEntity {

    private final Inventory inventory;
    private Item heldItem;
    private int heldItemIndex;

    //Create a new player
    public Player() {
        super(new Texture("entities/character.png"), new Position());
        this.inventory = new Inventory(this);
        this.heldItem = null;
        this.heldItemIndex = 0;
        this.getCollider().setDimensions(15, 15);
    }

    /**
     * Render us and our held item
     */
    public void render(Batch batch){
        super.render(batch);

        var item = this.inventory.getItemAtIndex(heldItemIndex);

        //if the item isn't null then render it
        if(item != null){
            item.render(batch);
        }

        //render the collider

        //draw the inventory if it's enabled
        this.inventory.drawInventory(batch);
    }

    /**
     * Check if the player is holding an item of the given type
     * @param itemType the item type to check
     * @return whether they're holding the given item
     */
    public boolean holdingItemOfType(ItemType itemType){

        //get the held item
        var item = getHeldItem();

        //if the item is null, return false.
        if(item == null){
            return false;
        }

        return getHeldItem().isType(itemType);
    }

    /**
     * Get whether we're holding an item
     * @return whether we're holding an item
     */
    public boolean holdingItem(){
        return getHeldItem() != null;
    }

    /**
     * Get the player's held item
     * @return the players item by using the held item index
     */
    public Item getHeldItem() {
        return this.inventory.getItemAtIndex(heldItemIndex);
    }

    /**
     * Set the held item to the passed item
     * @param index to set the held item to
     **/
    public void setHeldItem(int index) {
        this.heldItem = this.inventory.getItemAtIndex(index);
        this.heldItemIndex = index;
    }

    /**
     * Drop the item by nulling out the held item
     */
    public void dropItem(){
        this.inventory.dropItem(inventory.getItemAtIndex(heldItemIndex));
        this.heldItem = null;
    }

    /**
     * Change the held item to the item we picked up
     * @param item the item to pick up
     */
    public void pickup(Item item){

        //if we're not holding the item then set it as the held item
        if(!holdingItem()){
            this.heldItem = item;
        }

        //add the item to the inventory
        this.inventory.addItem(item);
    }

    /**
     * Get the inventory
     * @return the inventory of the player
     */
    public Inventory getInventory() {
        return inventory;
    }

    /**
     * Get the inventory index of the held item
     * @return the index of the held item
     */
    public int getHeldItemIndex() {
        return heldItemIndex;
    }
}

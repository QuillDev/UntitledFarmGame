package tech.quilldev.Engine.Entities.DynamicEntities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import tech.quilldev.Engine.Entities.EntityCollider;
import tech.quilldev.Engine.Entities.StaticEntities.Items.Item;
import tech.quilldev.Engine.Entities.StaticEntities.Items.ItemType;
import tech.quilldev.Engine.GUI.Inventory;
import tech.quilldev.Engine.Utilities.Position;
import tech.quilldev.MathConstants;

public class Player extends DynamicEntity {

    //Item variables
    private final Inventory inventory;
    private int heldItemIndex;

    //collision variables
    private final EntityCollider useCollider;
    protected final float colliderRatio;
    protected final float reach;


    //Create a new player
    public Player() {
        super(new Texture("textures/character.png"), new Position());

        //inventory configuration
        this.inventory = new Inventory(this);
        this.heldItemIndex = 0;

        //collider configuration
        this.colliderRatio = .9f; // % of the texture size that the main collider is
        this.reach = (MathConstants.WORLD_UNIT) / 5f; //players reach distance in pixels
        this.useCollider = new EntityCollider(this);

        //set the collider dimensions
        this.useCollider.setDimensions(useCollider.width + reach, useCollider.height + reach);
        this.getCollider().setDimensions(this.getTextureWidth() * colliderRatio, this.getTextureHeight() * colliderRatio);
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

    @Override
    public void update() {

        //Math for centering use collider
        var usePosition = new Position(this.getPosition());
        usePosition.x += (getTextureWidth() /2f) - ( this.useCollider.width / 2f );
        usePosition.y += (getTextureHeight() /2f)- ( this.useCollider.height / 2f );
        this.useCollider.updatePosition(usePosition);

        //math for centering main collider
        var playerColliderPos = new Position(this.getPosition());
        usePosition.x += ( getTextureWidth() / 2f) - ( this.collider.width / 2f );
        usePosition.y += ( getTextureHeight() /2f) - ( this.collider.height / 2f );
        this.collider.updatePosition(playerColliderPos);
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
            return true;
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
        this.inventory.getItemAtIndex(index);
        this.heldItemIndex = index;
    }

    /**
     * Drop the item by nulling out the held item
     */
    public void dropItem(){
        this.inventory.dropItem(inventory.getItemAtIndex(heldItemIndex));
    }

    /**
     * Change the held item to the item we picked up
     * @param item the item to pick up
     */
    public void pickup(Item item){

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

    /**
     * Get the player's "Use" collider
     * @return the players use collider
     */
    public EntityCollider getUseCollider() {
        return useCollider;
    }
}

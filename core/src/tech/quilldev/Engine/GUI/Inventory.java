package tech.quilldev.Engine.GUI;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;

import com.badlogic.gdx.graphics.g2d.BitmapFont;

import com.badlogic.gdx.math.Vector3;
import tech.quilldev.Engine.Entities.DynamicEntities.Player;
import tech.quilldev.Engine.Entities.StaticEntities.Items.Item;
import tech.quilldev.Engine.Entities.StaticEntities.Items.ItemType;

import static tech.quilldev.MathConstants.WORLD_UNIT;

public class Inventory {

    //Rendering vars
    private final Texture texture;
    private final Texture hotbarTexture;
    private final Texture selectedTexture;
    private final Player player;
    private final BitmapFont font;
    private boolean enabled;


    //The inventory x and y position
    private float x = 0;
    private float y = 0;

    //Save a vector3d of the screens dimensions
    private Vector3 screen;

    //max capacity of the array
    private final int maxCapacity;

    //the item list
    private final Item[] itemList;

    public Inventory(Player player){

        //rendering setup
        this.texture = new Texture("textures/inventory.png");
        this.hotbarTexture = new Texture("textures/hotbar.png");
        this.selectedTexture = new Texture("textures/selected.png");
        this.font = new BitmapFont();

        //get the player
        this.player = player;
        this.enabled = false;

        this.screen = new Vector3();

        //logical vars
        this.maxCapacity = 15;
        this.itemList = new Item[maxCapacity];

        this.font.getData().setScale(.35f);
    }

    //INVENTORY CONTROL LOGIC

    /**
     * Add an item into the inventory
     * @param item the item to add to the inventory
     */
    public void addItem(Item item){

        if(item == null){
            return;
        }

        //if we already have the item then merge it
        if(containsItem(item)){
            var existingItem = getItemOfType(item.getItemType());
            existingItem.setStackCount(existingItem.getStackCount() + item.getStackCount());
            return;
        }

        //get the first empty slot in the inventory
        var emptySlot = getFirstEmptySlot();

        //if there's no empty slot return
        if(emptySlot == null){
            return;
        }

        //add that item to the item list
        this.itemList[emptySlot] = item;
    }

    /**
     * Remove the given item from the inventory
     * @param item to remove
     */
    public void removeItem(Item item){
        var index = getItemIndex(item);
        itemList[index] = null;
    }

    /**
     * Get the item at the index
     * @param index the index to get
     * @return the item at that index
     */
    public Item getItemAtIndex(int index){

        if(index < 0 || index > itemList.length){
            return null;
        }

        return itemList[index];
    }

    /**
     * Get an item by its item tpye
     * @return the corresponding item if it exists
     */
    public Item getItemOfType(ItemType itemType){

        //iterate through the items
        for(Item item : itemList){

            //if the item is null, continue
            if(item == null){
                continue;
            }

            //if the itemType is equal to the other itemType return the item
            if(item.isType(itemType)){
                return item;
            }
        }
        return null;
    }

    /**
     * Check if we have the item we're trying to add to our inventory
     * @param otherItem the other item to compare it to
     * @return whether the item already exists in our inventory
     */
    public boolean containsItem(Item otherItem){

        //iterate through the items
        for(Item item : itemList){
            if(item != null){
                //if we contain the other item
                if(item.isType(otherItem.getItemType())){
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Get whether the inventory is enabled
     * @return whether it's enabled or not
     */
    public boolean isEnabled(){
        return this.enabled;
    }

    /**
     * Method to drop an item from hte inventory
     * @param item the item to drop
     */
    public void dropItem(Item item){

        //get the item to drop
        var itemIndex = getItemIndex(item);

        //if the item doesn't exist return
        if(itemIndex == null){
            return;
        }

        //null out the index of the item we dropped
        itemList[itemIndex] = null;
    }

    /**
     * Get the index of the inputted item
     * @param query the item to query for
     * @return the items index
     */
    public Integer getItemIndex(Item query){

        for(int index = 0; index < itemList.length; index++){

            //get the item at that index
            var item = itemList[index];

            //if the item is null skip it
            if( item == null){
                continue;
            }

            //if the items are the same type return the index
            if(item.isType(query.getItemType())){
                return index;
            }
        }

        return null;
    }

    /**
     * Get the index of the first empty inventory slot
     * @return the index of the first null slot
     */
    public Integer getFirstEmptySlot(){

        //iterate through the item list
        for(int index = 0; index < itemList.length; index++){

            //if we hit a null slot
            if(itemList[index] == null){

                //return that index
                return  index;
            }
        }

        return null;
    }


    // INVENTORY RENDERING LOGIC
    /**
     * Method to draw the inventory on the screen
     * @param batch the batch to draw to
     */
    public void drawInventory(Batch batch){

        //calculate this.x and this.y
        this.x = player.getPosition().getX() - texture.getWidth() /2f;

        //if the inventory is enabled draw it
        if (enabled) {
            //calculate y value
            this.y = player.getPosition().getY() - texture.getHeight() /2f;
            //draw the inventory at the coordinates given
            batch.draw(this.texture, this.x, this.y);

            //dynamically render the items onto the inventory
            for(int index = 0; index < itemList.length; index++){

                //get the item from the index
                var item = itemList[index];

                //if the item is null, continue
                if(item == null){
                    continue;
                }

                //get the items texture;
                var texture = item.getTexture();

                //calculate the row and column for the item
                var row = (float) Math.floor(index / 5f);
                var column = index % 5f;

                //calculate x and y positions of the item
                var itemX = column * WORLD_UNIT + this.x;
                var itemY = row * WORLD_UNIT + this.y;

                //calculate font position coordinates
                var fontX = itemX + texture.getWidth()*.7f;
                var fontY = itemY + font.getLineHeight();

                //draw the item onto the window
                batch.draw(texture, itemX, itemY);
                font.draw(batch, String.format("%s", item.getStackCount()), fontX, fontY);
            }
        }
        else {
            var y = screen.y;
            batch.draw(this.hotbarTexture, this.x, y);

            for(int index =0 ; index < 5; index++){
                var item = getItemAtIndex(index);

                //the column of the item
                var column = index % 5;

                //calculate the x position
                var xPos = column * WORLD_UNIT + this.x;

                //if the item is not null, draw the items texture
                if(item != null){
                    batch.draw(item.getTexture(), xPos, y);
                }
            }

            var xPos = (player.getHeldItemIndex() % 5) * WORLD_UNIT + this.x;
            batch.draw(this.selectedTexture, xPos, y);
        }

    }

    /**
     * Swap items in two slots
     * @param from index item is from
     * @param to index item is going to
     */
    public void swapSlots(int from, int to){
        var fromItem= getItemAtIndex(from);
        var toItem = getItemAtIndex(to);

        if(from > maxCapacity || to > maxCapacity){
            return;
        }
        itemList[from] = toItem;
        itemList[to] = fromItem;

    }
    /**
     * Get the row and column of the item
     * @param vector  a 3d vector that contains the coordinates we desire
     * @return the item index from those coordinates
     */
    public Integer getIndexFromVector(Vector3 vector){

        //calculate the item X
        var itemX = vector.x - this.x;

        var itemY = enabled ? ( vector.y - this.y ) : ( vector.y - screen.y );

        //Get column and row
        var column = (int) Math.floor(itemX / WORLD_UNIT);
        var row = (int) Math.floor(itemY / WORLD_UNIT);

        //if the slot is invalid return null
        if( enabled ? ( row > 2 || column > 4 || row < 0 || column < 0 ) : ( row != 0 ||  column > 4 || column < 0 )){
            return null;
        }

        return row * 5 + column;
    }
    /**
     * Whether the inventory should be enabled
     */
    public void toggleEnabled(){
        this.enabled = !this.enabled;
    }

    //resize the inventory
    public void updateScreen(Vector3 screenVector){
        this.screen = screenVector;
    }
}

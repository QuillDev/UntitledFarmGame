package tech.quilldev.Engine.GUI.Inventory;

import com.badlogic.gdx.graphics.g2d.Batch;
import tech.quilldev.Engine.Entities.StaticEntities.Items.CloneItem;
import tech.quilldev.Engine.Entities.StaticEntities.Items.Item;
import tech.quilldev.Engine.Entities.StaticEntities.Items.ItemType;
import tech.quilldev.Engine.Utilities.Position;
import tech.quilldev.MathConstants;

import java.util.ArrayList;

public class Inventoryv2 {

    //manage slots of the inventory
    private final ArrayList<InventorySlot> slots;

    //properties of the inventory based on the size
    private final int rows;
    private final int columns;
    private final float width;
    private final float height;
    private boolean enabled;

    public Inventoryv2(int columns, int rows){
        this.slots = createSlots(columns, rows);
        this.rows = rows;
        this.columns = columns;
        this.width = this.columns * MathConstants.WORLD_UNIT;
        this.height = this.rows * MathConstants.WORLD_UNIT;
        this.enabled = false;
    }

    /**
     * Return whether the inventory is enabled
     * @return whether the inventory is enabled
     */
    public boolean isEnabled(){
        return enabled;
    }

    /**
     * Toggle the state of the inventory being enabled
     */
    public void toggleEnabled(){
        this.enabled = !this.enabled;
    }

    /**
     * Set the item to null
     * @param item to drop
     */
    public void dropItem(Item item){
        var invItem = getItem(item);

        invItem = null;
    }

    /**
     * Add an item to the inventory
     * @param item to add to the inventory
     * @return the item to the inventory
     */
    public void addItem(Item item){
        var firstMergeable = getFirstMergeable(item);

        //if the first mergeable item exists, then merge stacks
        if(firstMergeable != null){
            firstMergeable.mergeStacks(item);
            return;
        }
        var emptySlot = getEmptySlot();

        if(emptySlot == null){
            return;
        }

        emptySlot.setItem(item);


    }

    /**
     * Get the first item that is capable of merging
     * @param item to merge
     * @return the first item that can merge
     */
    public Item getFirstMergeable(Item item){
        for(var slot : getPopulatedSlots()){
            if(slot.isItem(item)){
                var slotItem = slot.getItem();
                var futureVal = slotItem.stackCount + item.stackCount;
                if(slotItem.stackCount < slotItem.maxStackSize){
                    if(futureVal > item.maxStackSize){
                        var dif = futureVal - item.maxStackSize;
                        slotItem.setStackCount(slotItem.maxStackSize);
                        item.stackCount = dif;
                        continue;
                    }
                    return slot.getItem();
                }
            }
        }

        return null;
    }
    /**
     * Get the item at the given index
     * @param index to get item at
     * @return the item at that index
     */
    public Item getItemAtIndex(int index){
        return slots.get(index).getItem();
    }

    /**
     * Get the item of the given type
     * @param item to check for
     * @return the item we got
     */
    public Item getItem(Item item){
        return getItem(item.getItemType());
    }

    /**
     * Get all items matching the current item
     * @param item to match
     * @return the items matching that item
     */
    public ArrayList<Item> getItems(Item item){

        //matches
        var matches = new ArrayList<Item>();

        //add the item to the matches
        for(var slot : getPopulatedSlots()){
            if(slot.isItem(item)){
                matches.add(item);
            }
        }

        return matches.size() == 0 ? null : matches;
    }

    /**
     * Get the given item from the item type
     * @param itemType to get
     * @return the item
     */
    public Item getItem(ItemType itemType){
        //iterate through populated slots
        for(var slot : getPopulatedSlots()){

            //get the given item if we match
            if(slot.getItem().isType(itemType)){
                return slot.getItem();
            }
        }

        return null;
    }

    /**
     * check whether the inventory has the given item type in it
     * @param itemType the item type to check for
     * @return whether we already have it
     */
    public boolean hasType(ItemType itemType){
        return getHeldTypes().contains(itemType);
    }

    /**
     * check whether the inventory has the given item type in it
     * @param item to check for
     * @return whether we already have it
     */
    public boolean hasItem(Item item){
        return hasType(item.getItemType());
    }

    /**
     * Get all held item tpes
     * @return all of the held item types as an array
     */
    public ArrayList<ItemType> getHeldTypes(){
        final var types = new ArrayList<ItemType>();

        for(var slot : getPopulatedSlots()){
            types.add(slot.getItemType());
        }

        return types;
    }

    /**
     * Get slots in the inventory that have items
     * @return whether the list contains the given item
     */
    public ArrayList<InventorySlot> getPopulatedSlots(){

        var populated = new ArrayList<InventorySlot>();
        for(var slot : slots){

            //if the slot has an item, add it
            if(slot.hasItem()){
                populated.add(slot);
            }
        }

        return populated;
    }
    /**
     * Get the first available empty slot in the inventory
     * @return the empty slot
     */
    public InventorySlot getEmptySlot(){
        var emptySlots = this.getEmptySlots();

        //if there are no empty slots, return null
        if(emptySlots.size() == 0){
            return null;
        }

        //return the first empty slot
        return emptySlots.get(0);
    }

    /**
     * Get the inventories empty slots
     * @return the inventories empty slots
     */
    public ArrayList<InventorySlot> getEmptySlots(){

        final ArrayList<InventorySlot> emptySlots = new ArrayList<>();
        for(var slot : slots){
            if(!slot.hasItem()){
                emptySlots.add(slot);
            }
        }

        return emptySlots;
    }

    /**
     * Render the inventory relative to the given position
     * @param batch to render to
     * @param position to render to (centered on this position)
     */
    public void render(Batch batch, Position position){

        //if the inventory isn't enabled, return
        if(!enabled){
            return;
        }

        //If they're off, remove - sign
        float startX = position.x - ( width / 2f) - ( MathConstants.WORLD_UNIT / 2f ) ;
        float startY = position.y - ( height / 2f) - ( MathConstants.WORLD_UNIT / 2f );


        for(int index = 0; index < slots.size(); index++){

            //get the slot at this index
            var slot = slots.get(index);

            //position y to draw the slots
            int row = index / columns;
            int column = index % columns;

            //position x to draw the slots
            var posX = startX + column * MathConstants.WORLD_UNIT;
            var posY = startY + row * MathConstants.WORLD_UNIT;

            slot.setPosition(posX, posY);
            slot.render(batch);
        }


    }
    /**
     * Create the inventory slots given the desired columns and rows
     * @param columns to be in the inventory
     * @param rows to be in the inventory
     * @return the slots array with the slot positions
     */
    private ArrayList<InventorySlot> createSlots(int columns, int rows){
        //get the size of the array
        int size = columns * rows;

        ArrayList<InventorySlot> slots = new ArrayList<>();

        //add a new slot to the inventory
        for(int index = 0; index < size; index++){
            //add a slot with the given position
            slots.add(new InventorySlot());
        }

        return slots;
    }
}

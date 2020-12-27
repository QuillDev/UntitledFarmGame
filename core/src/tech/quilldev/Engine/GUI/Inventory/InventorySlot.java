package tech.quilldev.Engine.GUI.Inventory;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import tech.quilldev.Engine.Entities.StaticEntities.Items.Item;
import tech.quilldev.Engine.Entities.StaticEntities.Items.ItemType;
import tech.quilldev.Engine.Utilities.Position;
import tech.quilldev.MathConstants;

public class InventorySlot {

    //TODO Research plox, ty!
    private static final ThreadLocal<Texture> texture = ThreadLocal.withInitial(() -> new Texture("textures/inventory_slot.png"));
    private final BitmapFont font;

    private Item item;
    private final Position position;

    //constructor for an empty inventory slot
    public InventorySlot(){
        this.item = null;
        this.position = new Position(0, 0);
        this.font = genFont();

    }

    //constructor for an empty inventory slot
    public InventorySlot(Position position){
        this.item = null;
        this.position = new Position(position);
        this.font = genFont();
    }

    //TODO causes memory leak prob
    private BitmapFont genFont(){
        var generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/FFFFORWA.TTF"));
        var params = new FreeTypeFontGenerator.FreeTypeFontParameter();
        params.size = MathConstants.WORLD_UNIT / 4;
        params.spaceX = MathConstants.WORLD_UNIT / 8;

        return generator.generateFont(params);
    }
    /**
     * Check if the held item is the given item type
     * @param item to check for
     * @return whether our held item is the same type
     */
    public boolean isItem(Item item){

        //if we don't have an item, return false
        if(!hasItem()){
            return false;
        }

        return item.isType(this.item.getItemType());
    }

    /**
     * Get this slots item
     * @return this slots item
     */
    public Item getItem(){
        return this.item;
    }

    /**
     * set the item to the given item
     * @param item to set to
     */
    public void setItem(Item item){
        this.item = item;
    }

    /**
     * Get the item type of the held item
     * @return the item type
     */
    public ItemType getItemType(){

        //if the item is null, return null
        if(this.item == null){
            return null;
        }

        return this.item.getItemType();
    }

    /**
     * Check if the slot has an item
     * @return slot has item = true
     */
    public boolean hasItem(){
        return item != null;
    }

    /**
     * Get the x pos of the slot
     * @return the x pos of the slots
     */
    public float getX(){
        return this.position.x;
    }

    /**
     * Get the y pos of the slot
     * @return the y pos of the slot
     */
    public float getY(){
        return this.position.getY();
    }

    /**
     * Set the position of the slot to the given position
     * @param x position to set
     * @param y position to set
     */
    public void setPosition(float x, float y){
        this.position.x = x;
        this.position.y = y;
    }

    /**
     * Render this inventory slot
     * @param batch to render to
     */
    public void render(Batch batch){

        //draw the slot at the given position
        batch.draw(texture.get(), getX(), getY());

        //if the item is null, return
        if(item == null){
            return;
        }

        //draw the item
        batch.draw(this.item.getTexture(), getX(), getY());

        //if the stack count of our item is 0, return
        if(this.item.stackCount == 0){
            return;
        }

        //render stack count text
        String text = String.format("%s", this.item.getStackCount());
        var fontX = getX() + MathConstants.WORLD_UNIT - (text.length() * ( MathConstants.WORLD_UNIT / 5f + MathConstants.WORLD_UNIT / 8f));
        var fontY = getY() + font.getLineHeight() + MathConstants.WORLD_UNIT * .1f;

        font.draw(batch, text, fontX, fontY);
    }
}

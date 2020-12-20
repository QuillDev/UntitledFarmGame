package tech.quilldev.Engine.Entities.StaticEntities.Items;

import com.badlogic.gdx.graphics.Texture;
import tech.quilldev.Engine.Utilities.Position;

public class Carrot extends Item{
    /**
     * Constructor for a new item
     * @param position of the item
     */
    public Carrot(Position position) {
        super(new Texture("textures/item_carrot.png"), position, ItemType.CARROT);
    }

    public Carrot() {
        super(new Texture("textures/item_carrot.png"), new Position(), ItemType.CARROT);
    }
}

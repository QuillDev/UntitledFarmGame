package tech.quilldev.Engine.Entities.StaticEntities.Items;

import com.badlogic.gdx.graphics.Texture;
import tech.quilldev.Engine.Utilities.Position;

public class Hoe extends Item{
    public Hoe(Position position) {
        super(new Texture("textures/item_hoe.png"), position, ItemType.HOE);
    }
}

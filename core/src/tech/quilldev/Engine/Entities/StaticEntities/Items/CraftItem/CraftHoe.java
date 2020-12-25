package tech.quilldev.Engine.Entities.StaticEntities.Items.CraftItem;

import tech.quilldev.Engine.Entities.StaticEntities.Items.CloneItem;
import tech.quilldev.Engine.Entities.StaticEntities.Items.Hoe;
import tech.quilldev.Engine.Entities.StaticEntities.Items.Rock;

import java.util.Arrays;

public class CraftHoe extends CraftItem {

    /**
     * Constructor for a new item
     */
    public CraftHoe() {
        super(new Hoe());
    }

    @Override
    public void registerRecipe(){
        this.materials.addAll(
                Arrays.asList(
                        new CloneItem(new Rock() ,3)
                )
        );
    }
}

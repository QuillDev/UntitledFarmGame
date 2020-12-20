package tech.quilldev.Engine.Entities.StaticEntities.Objects;

import tech.quilldev.Engine.Entities.StaticEntities.Items.CloneItem;
import tech.quilldev.Engine.Utilities.Position;

public class CloneObject extends GameObject{

    public CloneObject(GameObject object, Position position){
        super(object, new Position(position));
        this.passable = object.passable;
        cloneDrops(object);
    }

    //empty since we use the clone objects method
    @Override
    public void registerDrops() {
    }

    /**
     * Clone the objects drops and add them to it
     * @param object to clone items from
     */
    public void cloneDrops(GameObject object){
        for(var item : object.getDrops()){
            this.addDrops(new CloneItem(item, this.getPosition()));
        }
    }
}

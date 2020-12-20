package tech.quilldev.Engine.Actions.BreakActions;

import tech.quilldev.Engine.Actions.Action;
import tech.quilldev.Engine.Entities.StaticEntities.Items.ItemType;
import tech.quilldev.Engine.Entities.StaticEntities.Objects.ObjectType;
import tech.quilldev.Engine.GameManager;

public class BreakObjectAction extends Action {

    private final ItemType usableItems;
    private final ObjectType objectToBreak;

    /**
     * Constructor for new actions
     * @param gameManager main game manager
     */
    public BreakObjectAction(GameManager gameManager, ItemType usableItems, ObjectType objectToBreak) {
        super(gameManager);
        this.usableItems = usableItems;
        this.objectToBreak = objectToBreak;
    }

    public boolean execute(){
        var objManager = gameManager.entityManager.getObjectManager();
        var player = gameManager.entityManager.getPlayer();

        //if we're not holding the correct item, return false
        if(!player.holdingItemOfType(usableItems)){
            return false;
        }

        //get the closest object
        var closestObject = objManager.getFirstCollision(player.getUseCollider());

        //if there's no object return false
        if(closestObject == null){
            return false;
        }

        //if the object isn't of the type to break
        if(closestObject.objectType != objectToBreak){
            return false;
        }

        //get the entity manager
        var entityManager = gameManager.entityManager;

        //register the drop items & remove the object
        entityManager.getItemManager().registerItems(closestObject.getDrops());
        entityManager.getObjectManager().removeObjects(closestObject);

        return true;
    }
}

package tech.quilldev.Engine.Actions.CraftActions;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import tech.quilldev.Engine.Actions.Action;
import tech.quilldev.Engine.Entities.StaticEntities.Items.CloneItem;
import tech.quilldev.Engine.Entities.StaticEntities.Items.CraftItem.CraftItem;
import tech.quilldev.Engine.GameManager;

public class CraftAction extends Action {

    private final CraftItem craftItem;

    public CraftAction(GameManager gameManager, CraftItem craftItem){
        super(gameManager);
        this.craftItem = craftItem;
    }

    public boolean execute(){

        if(Gdx.input.isKeyJustPressed(Input.Keys.C)){
            var player = gameManager.entityManager.getPlayer();
            var inventory = player.getInventory();
            var item = craftItem.craft(inventory);

            //if the item is null, return false
            if(item == null) {
                return false;
            }

            //register the item to the item manager
            gameManager.entityManager.getItemManager().registerItems(new CloneItem(item, player.getPosition()));

            return true;
        }

        return false;
    }
}

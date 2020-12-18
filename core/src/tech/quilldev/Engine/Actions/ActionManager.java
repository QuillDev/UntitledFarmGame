package tech.quilldev.Engine.Actions;

import com.badlogic.gdx.math.Vector3;
import tech.quilldev.Engine.Actions.FarmActions.TIllSoilAction;
import tech.quilldev.Engine.Actions.InventoryActions.InventoryDragAction;
import tech.quilldev.Engine.Actions.InventoryActions.InventorySwapAction;
import tech.quilldev.Engine.Actions.InventoryActions.ToggleInventoryAction;
import tech.quilldev.Engine.Actions.PlayerActions.DropAction;
import tech.quilldev.Engine.Actions.PlayerActions.PickupAction;
import tech.quilldev.Engine.Actions.PlayerActions.PlayerMoveAction;
import tech.quilldev.Engine.Actions.WorldActions.GrassGrowAction;
import tech.quilldev.Engine.GameManager;

import java.util.ArrayList;

public class ActionManager {

    //All use actions
    private final ArrayList<Action> useActions;
    private final ArrayList<Action> updateActions;
    private final ArrayList<Action> runActions;

    //Random actions
    private final DropAction dropAction;
    private final ToggleInventoryAction toggleInventoryAction;
    private final InventorySwapAction inventorySwapAction;
    private final InventoryDragAction inventoryDragAction;

    public ActionManager(GameManager gameManager){

        //Create actions arrays
        this.useActions = new ArrayList<>();
        this.updateActions = new ArrayList<>();
        this.runActions = new ArrayList<>();
        registerActions(gameManager);

        //register random actions
        this.dropAction = new DropAction(gameManager);
        this.toggleInventoryAction = new ToggleInventoryAction(gameManager);
        this.inventorySwapAction = new InventorySwapAction(gameManager);
        this.inventoryDragAction = new InventoryDragAction(gameManager);
    }

    /**
     * Updates any actions that need to be updated constantly
     */
    public void update(){
        //execute all actions in the update actions list
        for(Action action : runActions){
            action.execute();
        }
    }

    /**
     * Run frame logic based actions
     */
    public void logicUpdate(){
        for (Action action : updateActions){
            action.execute();
        }
    }

    //Register actions to the action lists
    private void registerActions(GameManager gameManager){
        //Use actions
        useActions.add(new PickupAction(gameManager));
        useActions.add(new TIllSoilAction(gameManager));

        //Run Actions
        runActions.add(new PlayerMoveAction(gameManager));
        runActions.add(new DebugAction(gameManager));

        //Update actions
        updateActions.add(new GrassGrowAction(gameManager));
    }

    /**
     * Execute an inventory swap action using the given slot
     * @param slot the slot to check
     */
    public void inventorySwap(int slot){
        inventorySwapAction.execute(slot);
    }

    /**
     * Execute the inventory drag action using the touch down location and touch release location
     * @param touchDown  the location they last touched down
     * @param touchRelease the location they last released
     */
    public void inventoryDrag(Vector3 touchDown, Vector3 touchRelease){
        this.inventoryDragAction.execute(touchDown, touchRelease);
    }

    //Execute actions based on usage
    public void useEvent(){

        //iterate through actions
        for(Action action : useActions){

            //if an action finishes successfully break the action manager
            if(action.execute()){
                break;
            }
        }
    }

    //execute the drop action
    public void dropEvent(){
        this.dropAction.execute();
    }

    //toggle the inventory
    public void toggleInventory(){
        this.toggleInventoryAction.execute();
    }
}

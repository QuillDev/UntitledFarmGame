package tech.quilldev.Engine.Actions;

import com.badlogic.gdx.math.Vector3;
import tech.quilldev.Engine.Actions.BreakActions.BreakObjectAction;
import tech.quilldev.Engine.Actions.CraftActions.CraftAction;
import tech.quilldev.Engine.Actions.FarmActions.FarmHarvestAction;
import tech.quilldev.Engine.Actions.FarmActions.FarmPlantAction;
import tech.quilldev.Engine.Actions.FarmActions.FarmPrepareAction;
import tech.quilldev.Engine.Actions.InventoryActions.InventoryDragAction;
import tech.quilldev.Engine.Actions.InventoryActions.InventorySwapAction;
import tech.quilldev.Engine.Actions.InventoryActions.ToggleInventoryAction;
import tech.quilldev.Engine.Actions.Networking.PacketHandler;
import tech.quilldev.Engine.Actions.PlayerActions.DropAction;
import tech.quilldev.Engine.Actions.PlayerActions.PickupAction;
import tech.quilldev.Engine.Actions.PlayerActions.PlayerMoveAction;
import tech.quilldev.Engine.Actions.WorldActions.WorldGrowTileAction;
import tech.quilldev.Engine.Actions.WorldActions.WorldSpawnObjectAction;
import tech.quilldev.Engine.Entities.StaticEntities.Items.Carrot;
import tech.quilldev.Engine.Entities.StaticEntities.Items.CraftItem.CraftHoe;
import tech.quilldev.Engine.Entities.StaticEntities.Items.ItemType;
import tech.quilldev.Engine.Entities.StaticEntities.Objects.ObjectType;
import tech.quilldev.Engine.Entities.StaticEntities.Objects.RockObject;
import tech.quilldev.Engine.Entities.StaticEntities.Objects.TallGrassObject;
import tech.quilldev.Engine.GameManager;
import tech.quilldev.Engine.Map.Tiles.TileType;
import tech.quilldev.Engine.Networking.NetworkUtils.Packet;

import java.util.ArrayList;
import java.util.Arrays;

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

    //create the packet handler
    private final PacketHandler packetHandler;

    //keep the game manager around
    private final GameManager gameManager;

    public ActionManager(GameManager gameManager){

        //get the game manager
        this.gameManager = gameManager;

        //get the packet handler
        this.packetHandler = new PacketHandler(gameManager);

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

        //set the player position to the map position
        gameManager.entityManager.getPlayer().setPosition(gameManager.mapManager.getSpawnPosition());
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

    /**
     * Route the packets to the packet handler
     * @param packets to handle
     */
    public void handlePackets(ArrayList<Packet> packets){
        this.packetHandler.execute(packets);
    }
    //Register actions to the action lists
    private void registerActions(GameManager gameManager){

        //add use actions to the action list
        useActions.addAll(Arrays.asList(
                //picking up always goes first
                new PickupAction(gameManager),

                //Harvest action
                new FarmHarvestAction(gameManager, ItemType.SCYTHE, TileType.CARROT, TileType.DIRT,
                        new Carrot()),

                //Break actions go before farm actions
                new BreakObjectAction(gameManager, ItemType.SCYTHE, ObjectType.TALL_GRASS),
                new BreakObjectAction(gameManager, ItemType.PICKAXE, ObjectType.ROCK),

                //farm actions go last
                new FarmPrepareAction(gameManager, ItemType.HOE, TileType.SOIL, TileType.DIRT),
                new FarmPlantAction(gameManager, ItemType.SEEDS, TileType.DIRT, TileType.PLANTED_SOIL)
        ));

        //Add actions that run on a frame by frame basis
        runActions.addAll(Arrays.asList(
                new PlayerMoveAction(gameManager),
                new DebugAction(gameManager)
        ));

        //Add world actions that depend on the logic cycle
        updateActions.addAll(Arrays.asList(
                new WorldSpawnObjectAction(gameManager, TileType.GRASS, new TallGrassObject(), 7f),
                new WorldSpawnObjectAction(gameManager, TileType.ROCK, new RockObject(), 15f),
                new WorldGrowTileAction(gameManager, TileType.PLANTED_SOIL, TileType.GROWING, 35f),
                new WorldGrowTileAction(gameManager, TileType.GROWING, TileType.CARROT, 35f),
                new WorldGrowTileAction(gameManager, TileType.DIRT, TileType.GRASS, 50f),
                new CraftAction(gameManager, new CraftHoe())
        ));
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

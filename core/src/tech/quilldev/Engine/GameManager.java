package tech.quilldev.Engine;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector3;
import tech.quilldev.Engine.Actions.ActionManager;
import tech.quilldev.Engine.Console.GameConsole;
import tech.quilldev.Engine.Entities.EntityManager;
import tech.quilldev.Engine.Entities.StaticEntities.Items.Hoe;
import tech.quilldev.Engine.Entities.StaticEntities.Items.Scythe;
import tech.quilldev.Engine.GUI.GUI;
import tech.quilldev.Engine.Input.InputHandler;
import tech.quilldev.Engine.Map.MapManager;
import tech.quilldev.Engine.Rendering.GameRenderer;
import tech.quilldev.Engine.Utilities.Position;
import tech.quilldev.MathConstants;


public class GameManager {

    //Create rendering variables
    public final GameRenderer gameRenderer;
    public final EntityManager entityManager;
    public final MapManager mapManager;

    private final GUI gui;
    private final ActionManager actionManager;
    private final GameConsole gameConsole;

    // Game manager constructor
    public GameManager(){

        //Entities
        this.entityManager = new EntityManager();

        //manage the map
        this.mapManager = new MapManager();

        //Rendering
        this.gameRenderer = new GameRenderer(this.entityManager, this.mapManager);
        this.gui = new GUI();

        //get the action manager
        this.actionManager = new ActionManager(this);

        //Input Processing
        InputHandler inputHandler = new InputHandler(this.actionManager, this.gameRenderer.getCamera());
        Gdx.input.setInputProcessor(inputHandler);

        //create the console
        this.gameConsole = new GameConsole();
        GameConsole.log("TEST STRING");
    }

    //TODO Used for debugging
    public void create(){
        var itemManager = this.entityManager.getItemManager();
        itemManager.registerItems(new Hoe(new Position(30, 30)), new Scythe(new Position(50, 50)));
    }

    public void update(){

        //increment the accumulator
        MathConstants.ACCUMULATOR += Math.min(Gdx.graphics.getDeltaTime(), 0.15f);

        //check if the accumulator is at an acceptable level
        while (MathConstants.ACCUMULATOR >= MathConstants.TICK_RATE){
            this.actionManager.logicUpdate();
            MathConstants.ACCUMULATOR -= MathConstants.TICK_RATE;
        }

        this.entityManager.update();
        this.actionManager.update();
    }

    // Renders any data that we need rendered to the game
    public void render(){
        //call for game logic updates
        this.update();

        //render all entities & general game stuff
        this.gameRenderer.render();

        //render the console
        this.gameConsole.render();

        this.gui.updateUi(entityManager.getPlayer());

        //render the gui
        this.gui.render();

        var cam = gameRenderer.getCamera();
        this.entityManager.getPlayer().getInventory().updateScreen(cam.unproject(new Vector3(cam.viewportWidth, cam.viewportHeight, 0)));
    }

    /**
     * Update anything that needs to be resized in the game engine
     * @param width the new width
     * @param height the new height
     */
    public void resize(int width, int height){
        this.gameRenderer.resize(width, height);
        this.gui.resize(width, height);
    }

    // For disposing of textures when the game closes.
    public void dispose(){
        this.gameRenderer.dispose();
        this.gui.dispose();
    }
}

package tech.quilldev.Engine.GUI;

import com.badlogic.gdx.Gdx;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.kotcrab.vis.ui.VisUI;
import com.kotcrab.vis.ui.widget.VisLabel;
import com.kotcrab.vis.ui.widget.VisTable;
import tech.quilldev.Engine.Entities.DynamicEntities.Player;

public class GUI {

    //components for the ui
    private final Stage stage;
    private final VisTable table;


    //specific ui labels
    private VisLabel chunkLabel;
    private VisLabel fpsLabel;

    public GUI(){

        //setup stage
        this.stage = new Stage(new ScreenViewport());

        //setup table
        this.table = new VisTable();

        configureUi();
    }

    /**
     * Render the stage
     */
    public void render(){
        this.stage.act(Gdx.graphics.getDeltaTime());
        this.stage.getViewport().update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), false);
        this.stage.draw();
    }

    /**
     * Resizes the stage the ui is in
     * @param width width to resize to
     * @param height to resize to;
     */
    public void resize(int width, int height){
        this.stage.getViewport().update(width, height, true);
    }

    /**
     * Creates the default UI to be drawn to the window
     */
    private void createDefaultUi(){

        //create labels
        this.chunkLabel = new VisLabel();
        this.fpsLabel = new VisLabel();

        //add the chunk label to the table
        this.table.add(chunkLabel, fpsLabel);

        this.table.act(Gdx.graphics.getDeltaTime());
    }

    /**
     * Configure the ui and set it's screen position
     */
    private void configureUi(){

        //set the stage as an input processor
        Gdx.input.setInputProcessor(this.stage);

        //fill the parent
        this.table.setFillParent(true);

        //add the table to the stage
        this.stage.addActor(table);

        //create the default UI elements
        createDefaultUi();
    }

    /**
     * Update the ui with new values based on the current state of the game and the character
     */
    public void updateUi(Player player){

        //set the labels
        this.fpsLabel.setText(String.format("FPS: %s", Gdx.graphics.getFramesPerSecond()));
        this.chunkLabel.setText(String.format("Chunk: [X: %s, Y: %s]", player.getPosition().getXWorldUnitsTruncated(), player.getPosition().getYWorldUnitsTruncated()));

        this.chunkLabel.setPosition(this.fpsLabel.getX(), this.fpsLabel.getY() - this.chunkLabel.getHeight());
        this.stage.getViewport().getCamera().position.x = Gdx.graphics.getWidth() + this.chunkLabel.getWidth() * .2f;
        this.stage.getViewport().getCamera().position.y = 0 + this.fpsLabel.getHeight() / 3;
    }
    /**
     * Dispose of the stage
     */
    public void dispose(){
        VisUI.dispose();
        stage.dispose();
    }
}
package tech.quilldev.Engine.Rendering;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import tech.quilldev.DebugModes;
import tech.quilldev.Engine.Console.GameConsole;
import tech.quilldev.Engine.Entities.Entity;
import tech.quilldev.Engine.Entities.EntityManager;
import tech.quilldev.Engine.Map.MapManager;

import java.awt.*;


public class GameRenderer {
    private final Batch batch;
    private final EntityManager entityManager;
    private final MapManager mapManager;
    private final Camera2D camera;
    private final Viewport viewport;
    private final ShapeRenderer shapeRenderer;

    public GameRenderer(EntityManager entityManager, MapManager mapManager){

        //create rendering components
        this.camera = new Camera2D();
        this.batch = new SpriteBatch();
        this.viewport = new ScreenViewport(this.camera);

        //setup the shape renderer
        this.shapeRenderer = new ShapeRenderer();
        this.shapeRenderer.setAutoShapeType(true);
        this.shapeRenderer.setColor(255f, 0f, 0f, 1f);
        this.shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());

        //get the manager classes
        this.mapManager = mapManager;
        this.entityManager = entityManager;

        //setup the map manager
        this.mapManager.setup(this.camera);
    }


    public void render(){

        //Clear the screen
        Gdx.gl.glClearColor(0.57f, 0.77f, 0.85f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //begin the sprite batch
        this.batch.begin();

        //setup the projection matrixes
        this.batch.setProjectionMatrix(this.camera.combined);
        this.shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());

        //render the map from the map manager
        this.mapManager.render();

        //render the entity manager
        this.entityManager.render(batch);

        //update the camera after everything renders
        this.camera.update(entityManager.getPlayer());

        //render any debug stuff
        this.renderDebug();

        //end the sprite batch
        this.batch.end();
    }

    /**
     * Render any debug objects if debug rendering is enabled
     */
    public void renderDebug(){
        //if the colliders aren't
        if(!DebugModes.COLLIDERS){
            return;
        }

        //end the batch
        this.batch.end();

        //begin the shape renderer
        this.shapeRenderer.begin();

        this.shapeRenderer.setColor(255, 0,0 ,1);
        //Draw the colliders
        for(var collider : mapManager.getEntityColliders()) {
            this.shapeRenderer.rect(collider.x, collider.y, collider.width, collider.height);
        }

        //for each entity in the entity manager
        for(Entity entity : entityManager.getAllEntities()){
            var collider = entity.getCollider();
            this.shapeRenderer.rect(collider.x, collider.y, collider.width, collider.height);
        }

        this.shapeRenderer.setColor(0, 255, 0, 1);

        var useCollider = this.entityManager.getPlayer().getUseCollider();
        this.shapeRenderer.rect(useCollider.x ,useCollider.y, useCollider.width, useCollider.height);
        this.shapeRenderer.end();

        //re begin the batch
        this.batch.begin();
    }
    /**
     * Get the camera from the renderer
     * @return the camera;
     */
    public Camera2D getCamera() {
        return camera;
    }

    /**
     * Updat the viewport with the new width and height
     * @param width the width to update with
     * @param height the height to update with
     */
    public void resize(int width, int height){
        this.viewport.update(width, height);
        GameConsole.refresh();
    }

    //Dispose of any trash when we're done with it
    public void dispose(){
        this.batch.dispose();
    }
}

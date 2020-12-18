package tech.quilldev.Engine.Rendering;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import tech.quilldev.Engine.Entities.EntityManager;
import tech.quilldev.Engine.Map.MapManager;


public class GameRenderer {
    private final Batch batch;
    private final EntityManager entityManager;
    private final MapManager mapManager;
    private final Camera2D camera;
    private final Viewport viewport;

    public GameRenderer(EntityManager entityManager, MapManager mapManager){

        //create rendering components
        this.batch = new SpriteBatch();
        this.camera = new Camera2D();
        this.viewport = new ScreenViewport(this.camera);

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

        //set the batch projection to the camera
        this.batch.setProjectionMatrix(this.camera.combined);

        this.mapManager.render(batch);

        //render the entity manager
        this.entityManager.render(batch);

        //update the camera after everything renders
        this.camera.update(entityManager.getPlayer());

        //end the sprite batch
        this.batch.end();
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
    }

    //Dispose of any trash when we're done with it
    public void dispose(){
        this.batch.dispose();
    }
}

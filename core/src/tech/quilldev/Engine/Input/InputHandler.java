package tech.quilldev.Engine.Input;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector3;
import tech.quilldev.Engine.Actions.ActionManager;
import tech.quilldev.Engine.Rendering.Camera2D;

import java.util.ArrayList;

public class InputHandler implements InputProcessor {

    private final ArrayList<Integer> keysDown;
    private final ActionManager actionManager;
    private final Camera2D camera;

    //vector positions of last touch and release of the mouse
    private Vector3 lastTouchDown;
    private Vector3 lastTouchRelease;

    //Constructor for the input handler
    public InputHandler(ActionManager actionManager, Camera2D camera){
        this.keysDown = new ArrayList<>();
        this.actionManager = actionManager;
        this.camera = camera;

        //vectors for touch and release
        this.lastTouchDown = new Vector3();
        this.lastTouchRelease = new Vector3();
    }


    @Override
    public boolean keyDown(int keycode) {
        //add the keycode to the list
        this.keysDown.add(keycode);

        //if the e key is pressed execute the use events in the action manager

        //Switch case to run actions
        switch (keycode){

            //Key use events
            case Input.Keys.E: actionManager.useEvent(); break;
            case Input.Keys.G: actionManager.dropEvent(); break;
            case Input.Keys.I: actionManager.toggleInventory(); break;

            //Swap to inventory slot
            case Input.Keys.NUM_1: actionManager.inventorySwap(0); break;
            case Input.Keys.NUM_2: actionManager.inventorySwap(1); break;
            case Input.Keys.NUM_3: actionManager.inventorySwap(2); break;
            case Input.Keys.NUM_4: actionManager.inventorySwap(3); break;
            case Input.Keys.NUM_5: actionManager.inventorySwap(4); break;
        }



        return false;
    }

    @Override
    public boolean keyUp(int keycode) {

        //remove the key from the list
        this.keysDown.remove(Integer.valueOf(keycode));

        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {

        //set the last touch down location
        this.lastTouchDown = screenToGameUnits(screenX, screenY);
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        this.lastTouchRelease = screenToGameUnits(screenX, screenY);
        this.actionManager.inventoryDrag(lastTouchDown, lastTouchRelease);
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }

    /**
     * Method that converts X and Y mouse pos to game units
     * @param x value to convert
     * @param y value to convert
     * @return A 3d vector converted to game units
     */
    private Vector3 screenToGameUnits(int x, int y){
        //create a 3D Vector for the mouse
        Vector3 mousePos = new Vector3(x, y, 0);

        //Do a matrix transformation using the camera
        this.camera.unproject(mousePos);

        return mousePos;
    }
}

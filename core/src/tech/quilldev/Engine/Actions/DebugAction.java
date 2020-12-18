package tech.quilldev.Engine.Actions;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import tech.quilldev.Engine.GameManager;

public class DebugAction extends Action{

    public DebugAction(GameManager gameManager) {
        super(gameManager);
    }

    @Override
    public boolean execute() {

        if(Gdx.input.isKeyPressed(Input.Keys.EQUALS)){
            this.gameManager.mapManager.setMap(1);
        }
        return true;
    }
}

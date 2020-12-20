package tech.quilldev.Engine.Actions;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import tech.quilldev.DebugModes;
import tech.quilldev.Engine.GameManager;

public class DebugAction extends Action{

    private int curMap = 0;
    public DebugAction(GameManager gameManager) {
        super(gameManager);
    }

    @Override
    public boolean execute() {

        //Toggle through maps
        if(Gdx.input.isKeyJustPressed(Input.Keys.EQUALS)){
            var maps = gameManager.mapManager.getMaps();

            if(curMap < maps.size() -1){
                curMap++;
            }
            else {
                curMap = 0;
            }
            gameManager.mapManager.setMap(curMap);

        }

        //Toggle debug drawing
        if(Gdx.input.isKeyJustPressed(Input.Keys.F9)){
            DebugModes.COLLIDERS = !DebugModes.COLLIDERS;
        }

        return true;
    }
}

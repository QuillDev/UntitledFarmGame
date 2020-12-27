package tech.quilldev.Engine.Console;

import com.strongjoshua.console.CommandExecutor;
import tech.quilldev.DebugModes;
import tech.quilldev.Engine.GameManager;
import tech.quilldev.MathConstants;

public class GameConsoleExecutor extends CommandExecutor {

    private final GameManager gameManager;

    public GameConsoleExecutor(GameManager gameManager){
        this.gameManager = gameManager;
    }

    public void cls(){
        this.console.clear();
    }

    /**
     * Connect to the multiplayer server
     * @param host address to connect to
     * @param port to connected to on host
     */
    public void connect(String host, int port){
        this.gameManager.networkManager.connect(host, port);
    }

    /**
     * Disconnect from the multiplayer server
     */
    public void disconnect(){
        var networkManager = this.gameManager.networkManager;

        //if we're hosting, stop hosting
        if(networkManager.isHosting()){
            networkManager.stopHosting();
        }

        //disconnect the game manager
        this.gameManager.networkManager.disconnect();
    }

    /**
     * Try to host
     */
    public void host(){
        GameConsole.log("Trying to host..");
        this.gameManager.networkManager.host();
    }

    /**
     * Set the games TPS to the specified number of ticks per second
     * @param ticks per second to set to
     */
    public void tps(int ticks){
        MathConstants.TICK_RATE = 1f / MathConstants.TICKS_PER_SECOND / (ticks / MathConstants.TICKS_PER_SECOND);
        GameConsole.log("Set TPS to " + 1f / MathConstants.TICK_RATE);
    }
}

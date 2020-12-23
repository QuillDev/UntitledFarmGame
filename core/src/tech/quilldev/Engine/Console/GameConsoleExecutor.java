package tech.quilldev.Engine.Console;

import com.strongjoshua.console.CommandExecutor;
import tech.quilldev.MathConstants;

public class GameConsoleExecutor extends CommandExecutor {
    public GameConsoleExecutor(){
    }

    public void cls(){
        this.console.clear();
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

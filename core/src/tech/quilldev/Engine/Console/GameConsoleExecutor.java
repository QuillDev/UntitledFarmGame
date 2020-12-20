package tech.quilldev.Engine.Console;

import com.badlogic.gdx.Input;
import com.strongjoshua.console.CommandExecutor;

public class GameConsoleExecutor extends CommandExecutor {

    public GameConsoleExecutor(){

    }

    public void easteregg(){
        this.console.log("easter egg");
    }

    public void cls(){
        this.console.clear();
    }
}

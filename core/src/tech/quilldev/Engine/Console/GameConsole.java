package tech.quilldev.Engine.Console;

import com.badlogic.gdx.Input;
import com.strongjoshua.console.Console;
import com.strongjoshua.console.GUIConsole;

public class GameConsole {

    private static final Console console = new GUIConsole();

    public GameConsole(){
        this.configure();
    }

    /**
     * Setup the console
     */
    private void configure(){
        console.setCommandExecutor(new GameConsoleExecutor());
        console.setDisplayKeyID(Input.Keys.GRAVE);
    }

    /**
     * Render the console
     */
    public static void render(){
        console.draw();
    }

    /**
     * Check if the console is visible
     * @return whether the console is visible
     */
    public static boolean isVisible(){
        return console.isVisible();
    }

    /**
     * Refresh the console
     */
    public static void refresh(){
        console.refresh();
    }

    /**
     * Log to the game console
     * @param string to log to the console
     */
    public static void log(String string){
        console.log(string);
    }
}

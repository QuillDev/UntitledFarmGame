package tech.quilldev.Engine.Console;

import com.badlogic.gdx.Input;
import com.strongjoshua.console.Console;
import com.strongjoshua.console.GUIConsole;
import tech.quilldev.Engine.GameManager;

public class GameConsole {

    private static final Console console = new GUIConsole();

    public GameConsole(GameManager gameManager){
        this.configure(gameManager);
    }

    /**
     * Setup the console
     * @param gameManager
     */
    private void configure(GameManager gameManager){
        console.setCommandExecutor(new GameConsoleExecutor(gameManager));
        console.setDisplayKeyID(Input.Keys.GRAVE);
    }

    /**
     * Render the console
     */
    public static void render(){
        try {
            console.draw();
        } catch (Exception ignored){
            console.refresh();
        };

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

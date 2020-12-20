package tech.quilldev.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import tech.quilldev.UntitledFarmGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.foregroundFPS = 0;
		config.backgroundFPS = 5;
		config.vSyncEnabled = false;
		config.useGL30 = true;
		new LwjglApplication(new UntitledFarmGame(), config);
	}
}

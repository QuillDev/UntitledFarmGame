package tech.quilldev;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.kotcrab.vis.ui.VisUI;
import tech.quilldev.Engine.Console.GameConsole;
import tech.quilldev.Engine.GameManager;
import tech.quilldev.Engine.Singletons.TextureManager;

public class UntitledFarmGame extends ApplicationAdapter {

	//create the game manager
	private GameManager gameManager;

	@Override
	public void create () {
		VisUI.load();
		this.gameManager = new GameManager();
		this.gameManager.create();

		//create our static classes
		new GameConsole();
		new TextureManager();

	}

	@Override
	public void render () {
		this.gameManager.render();
	}
	
	@Override
	public void dispose () {
		this.gameManager.dispose();
	}

	@Override
	public void resize(int width, int height){
		this.gameManager.resize(width, height);
	}
}

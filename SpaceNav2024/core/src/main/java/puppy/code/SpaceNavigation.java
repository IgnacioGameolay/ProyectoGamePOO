package puppy.code;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;




public class SpaceNavigation extends Game {
	private String nombreJuego = "Te Echaste el Ramo";
	private SpriteBatch batch;
	private BitmapFont font;
	private Texture background;

			
	public void create() {
		batch = new SpriteBatch();
		font = new BitmapFont();
		font.getData().setScale(2f);
		background = new Texture(Gdx.files.internal("template.png"));

        
		Screen ss = new PantallaMenu(this);
		this.setScreen(ss);
	}

	public void render() {
		super.render(); // important!
		
		batch.begin();
        batch.draw(background, 0, 0, 600, 900); // Ajusta el tamaño y posición según sea necesario
        batch.end();
	}

	public void dispose() {
		batch.dispose();
		font.dispose();
		background.dispose();
	}

	public SpriteBatch getBatch() {
		return batch;
	}

	public BitmapFont getFont() {
		return font;
	}
}
package puppy.code;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;

public class NaveBase extends Nave{
	
	
	public NaveBase(PantallaJuego juego) {
		super(Gdx.graphics.getWidth() / 2 - 50, 
				30, 
				new Texture(Gdx.files.internal("spaceship1.png")), 
				Gdx.audio.newSound(Gdx.files.internal("naveHurt.mp3")), 
				new Texture(Gdx.files.internal("bullet.png")), 
				Gdx.audio.newSound(Gdx.files.internal("shoot_theme.mp3")), 
				juego, 
				3, 
				new Texture(Gdx.files.internal("spaceship1_1.png")), 
				new Texture[]{
			        new Texture(Gdx.files.internal("spaceship2.png")),
			        new Texture(Gdx.files.internal("spaceship3.png")),
			        new Texture(Gdx.files.internal("spaceship4.png")),
			        new Texture(Gdx.files.internal("spaceship5.png"))
			    });
    }
	
	
	@Override
    public void mover() {
        // Movimiento con teclado
        if (Gdx.input.isKeyJustPressed(Input.Keys.LEFT)) velocidadX--;
        if (Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)) velocidadX++;

        // Mantener dentro de los límites de la pantalla
        if (x + velocidadX < 0 || x + velocidadX + spr.getWidth() > Gdx.graphics.getWidth()) velocidadX *= -1;
        spr.setPosition(x + velocidadX,y);
    }

    @Override
    public void disparar() {
        if (juego != null) {
            Bullet bala = new Bullet(spr.getX() + spr.getWidth() / 2 - 5, spr.getY() + spr.getHeight() - 5, 0, 3, txBala);
            juego.agregarBalaJugador(bala);
            soundBala.play();
        }
    }
}

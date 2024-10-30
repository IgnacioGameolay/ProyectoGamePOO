package puppy.code.fabricasConcretas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;

import puppy.code.Enemigo;
import puppy.code.PantallaJuego;
import puppy.code.enemigos.EnemigoPolilla;
import puppy.code.fabricasAbstractas.FabricaAbstractaEnemigo;

public class FabricaEnemigoPolilla implements FabricaAbstractaEnemigo{
	private static final Texture texture = new Texture(Gdx.files.internal("ene2.png"));
	
	@Override	
	public Enemigo crearAbeja(int x, int y, PantallaJuego juego) {
		return null;//new EnemigoPolilla(x, y, 25, 1, 3, texture, juego, MathUtils.random(0.1f, 0.8f), 1);
	}

	public static void dispose() {
        texture.dispose();
    }
	
	@Override
	public Enemigo crearPolilla(int x, int y, PantallaJuego juego) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Enemigo crearMiniBoss(int x, int y, PantallaJuego juego) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Enemigo crearFinalBoss(int x, int y, PantallaJuego juego) {
		// TODO Auto-generated method stub
		return null;
	}
}


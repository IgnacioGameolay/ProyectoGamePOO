package puppy.code.fabricasConcretas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;

import puppy.code.Enemigo;
import puppy.code.PantallaJuego;
import puppy.code.enemigos.EnemigoAbeja;
import puppy.code.fabricasAbstractas.FabricaAbstractaEnemigo;

public class FabricaEnemigoAbeja implements FabricaAbstractaEnemigo{
	
    
	@Override
	public Enemigo crearAbeja(int x, int y, PantallaJuego juego) {
		//EnemigoSimple(int x, int y, int size, int xSpeed, int ySpeed, Texture texture, PantallaJuego juego, float speedAttack, int vida)
        //return new EnemigoAbeja(x, y, 20, 2, 2, (Texture) "wa", juego, MathUtils.random(0.1f, 0.5f), 1);
		return null;
    }
	
	public static void dispose() {
        //texture.dispose();
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


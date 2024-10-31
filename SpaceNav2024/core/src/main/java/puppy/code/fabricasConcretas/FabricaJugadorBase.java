package puppy.code.fabricasConcretas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

import puppy.code.Nave;
import puppy.code.NaveBase;
import puppy.code.PantallaJuego;
import puppy.code.fabricasAbstractas.FabricaAbstractaJugador;

public class FabricaJugadorBase implements FabricaAbstractaJugador{
	@Override
    public Nave crearNave(PantallaJuego juego) {
        return new NaveBase(juego);  // Constructor de EnemigoPolilla
    }

}

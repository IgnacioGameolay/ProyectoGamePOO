package puppy.code.fabricasConcretas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

import puppy.code.Nave;
import puppy.code.NaveBase;
import puppy.code.PantallaJuego;
import puppy.code.fabricasAbstractas.FabricaAbstractaJugador;

/**
 * Implementación de la fábrica abstracta para crear un jugador base.
 * Esta clase es responsable de crear instancias de la nave base en el juego.
 */
public class FabricaJugadorBase implements FabricaAbstractaJugador {

    /**
     * Crea una nueva instancia de la nave base.
     *
     * @param juego La pantalla del juego donde se creará la nave.
     * @return Una nueva instancia de NaveBase.
     */
    @Override
    public Nave crearNave(PantallaJuego juego) {
        return new NaveBase(juego); // Crea y devuelve una nueva nave base
    }
}
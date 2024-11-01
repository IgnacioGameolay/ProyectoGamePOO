package puppy.code.fabricasAbstractas;

import puppy.code.Nave;
import puppy.code.PantallaJuego;

/**
 * Interfaz para la fábrica abstracta de jugadores.
 * Esta interfaz define un contrato para crear instancias de nave en el juego.
 */
public interface FabricaAbstractaJugador {
    
    /**
     * Crea una nueva nave en la pantalla del juego.
     *
     * @param juego La pantalla del juego donde se creará la nave.
     * @return Una instancia de Nave que representa la nave del jugador.
     */
    Nave crearNave(PantallaJuego juego);
}
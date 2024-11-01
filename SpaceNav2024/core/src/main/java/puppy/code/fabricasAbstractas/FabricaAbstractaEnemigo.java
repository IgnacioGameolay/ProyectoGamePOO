package puppy.code.fabricasAbstractas;

import puppy.code.Enemigo;
import puppy.code.PantallaJuego;


/**
 * Interfaz para la fábrica abstracta de enemigos.
 * Esta interfaz define un contrato para crear instancias de diferentes tipos de enemigos en el juego.
 */
public interface FabricaAbstractaEnemigo {
	/**
     * Crea una nueva Polilla en la posición especificada.
     *
     * @param x La coordenada X donde se creará la Polilla.
     * @param y La coordenada Y donde se creará la Polilla.
     * @param juego La pantalla del juego donde se creará el enemigo.
     * @return Una instancia de Enemigo que representa una Polilla.
     */
	Enemigo crearPolilla(int x, int y, PantallaJuego juego);
	
	 /**
     * Crea una nueva Abeja en la posición especificada.
     *
     * @param x La coordenada X donde se creará la Abeja.
     * @param y La coordenada Y donde se creará la Abeja.
     * @param juego La pantalla del juego donde se creará el enemigo.
     * @return Una instancia de Enemigo que representa una Abeja.
     */
    Enemigo crearAbeja(int x, int y, PantallaJuego juego);
    
    /**
     * Crea un nuevo MiniBoss en la posición especificada.
     *
     * @param x La coordenada X donde se creará el MiniBoss.
     * @param y La coordenada Y donde se creará el MiniBoss.
     * @param juego La pantalla del juego donde se creará el enemigo.
     * @return Una instancia de Enemigo que representa un MiniBoss.
     */
    Enemigo crearMiniBoss(int x, int y, PantallaJuego juego);
    
    /**
     * Crea un nuevo Final Boss en la posición especificada.
     *
     * @param x La coordenada X donde se creará el Final Boss.
     * @param y La coordenada Y donde se creará el Final Boss.
     * @param juego La pantalla del juego donde se creará el enemigo.
     * @return Una instancia de Enemigo que representa un Final Boss.
     */
    Enemigo crearFinalBoss(int x, int y, PantallaJuego juego);
}

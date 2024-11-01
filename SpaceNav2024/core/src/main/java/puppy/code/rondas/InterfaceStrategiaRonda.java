package puppy.code.rondas;

import java.util.List;
import puppy.code.Enemigo;
import puppy.code.PantallaJuego;

/**
 * Interfaz que define la estrategia para configurar una ronda de enemigos en el juego.
 */
public interface InterfaceStrategiaRonda {

    /**
     * Configura la ronda generando enemigos y agregándolos a la lista proporcionada.
     *
     * @param juego La pantalla del juego donde se mostrará la ronda.
     * @param enemigosLista La lista donde se agregarán los enemigos generados.
     */
    void configurarRonda(PantallaJuego juego, List<Enemigo> enemigosLista);
}
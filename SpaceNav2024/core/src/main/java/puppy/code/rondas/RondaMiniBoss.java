package puppy.code.rondas;

import java.util.List;

import puppy.code.Enemigo;
import puppy.code.GameManager;
import puppy.code.PantallaJuego;
import puppy.code.fabricasAbstractas.FabricaAbstractaEnemigo;
import puppy.code.fabricasConcretas.FabricaEnemigosPorRonda;

/**
 * Clase que representa la estrategia para configurar la ronda de mini jefes.
 * Implementa la interfaz InterfaceStrategiaRonda.
 */
public class RondaMiniBoss implements InterfaceStrategiaRonda {

    /**
     * Configura la ronda generando mini jefes y agregándolos a la lista.
     *
     * @param juego La pantalla del juego donde se mostrará la ronda.
     * @param enemigosLista La lista donde se agregarán los enemigos generados.
     */
    @Override
    public void configurarRonda(PantallaJuego juego, List<Enemigo> enemigosLista) {
        enemigosLista.clear(); // Limpiar lista antes de generar nuevos enemigos

        FabricaAbstractaEnemigo fabricaAbstracta = new FabricaEnemigosPorRonda();

        // Configuración de filas
        int filas = 3;               // Número de filas de enemigos
        int enemigosPorFila = 4;     // Número de enemigos en cada fila
        int ronda = GameManager.getInstance().getRonda(); // Nivel del enemigo
        int espacioHorizontal = 200; // Espacio entre enemigos en la misma fila
        int espacioVertical = 100;   // Espacio entre filas

        // Coordenadas iniciales
        int yInicial = 400;          // Altura inicial de la primera fila
        int xInicial = 200;          // Posición inicial en X

        // Generación de filas de enemigos
        int enemyX = xInicial;
        int enemyY = yInicial + espacioVertical; // Incremento en Y por cada fila
        
        // Generar mini jefes en las filas
        for (int i = 0; i < enemigosPorFila; i++) {
            enemigosLista.add(fabricaAbstracta.crearMiniBoss(enemyX, enemyY, GameManager.getInstance().getJuego()));
            
            // Incrementar posición X para el siguiente enemigo en la fila
            enemyX += espacioHorizontal;
        }
    }
}

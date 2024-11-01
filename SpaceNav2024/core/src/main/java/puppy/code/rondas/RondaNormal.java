package puppy.code.rondas;

import java.util.List;

import puppy.code.Enemigo;
import puppy.code.GameManager;
import puppy.code.PantallaJuego;
import puppy.code.fabricasAbstractas.FabricaAbstractaEnemigo;
import puppy.code.fabricasConcretas.FabricaEnemigosPorRonda;

/**
 * Clase que representa la estrategia para configurar una ronda normal de enemigos.
 * Implementa la interfaz InterfaceStrategiaRonda.
 */
public class RondaNormal implements InterfaceStrategiaRonda {

    /**
     * Configura la ronda generando enemigos en filas y agregándolos a la lista.
     *
     * @param juego La pantalla del juego donde se mostrará la ronda.
     * @param enemigosLista La lista donde se agregarán los enemigos generados.
     */
    @Override
    public void configurarRonda(PantallaJuego juego, List<Enemigo> enemigosLista) {
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
        for (int fila = 0; fila < filas; fila++) {
            int enemyX = xInicial;
            int enemyY = yInicial + (fila * espacioVertical); // Incremento en Y por cada fila
            
            for (int i = 0; i < enemigosPorFila; i++) {
                // Determinar la cantidad de abejas que se pueden crear según el nivel de la ronda
                int cantAbejas = Math.max(0, 4 - ronda + 1);
                
                // Crear enemigos según la fila
                if (i < cantAbejas) {
                    // Crear una abeja si todavía hay abejas disponibles
                    enemigosLista.add(fabricaAbstracta.crearAbeja(enemyX, enemyY, juego));
                } else {
                    // Crear una polilla
                    enemigosLista.add(fabricaAbstracta.crearPolilla(enemyX, enemyY, juego));
                }
                
                // Incrementar posición X para el siguiente enemigo en la fila
                enemyX += espacioHorizontal;
            }
        }
    }
}

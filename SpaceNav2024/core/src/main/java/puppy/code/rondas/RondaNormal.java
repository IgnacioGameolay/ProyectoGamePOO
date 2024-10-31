package puppy.code.rondas;

import java.util.List;

import puppy.code.Enemigo;
import puppy.code.GameManager;
import puppy.code.PantallaJuego;
import puppy.code.fabricasAbstractas.FabricaAbstractaEnemigo;
import puppy.code.fabricasConcretas.FabricaEnemigosPorRonda;

public class RondaNormal implements InterfaceStrategiaRonda {
	
	@Override
	public void configurarRonda(PantallaJuego juego, List<Enemigo> enemigosLista){
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
                // Elegir tipo de enemigo según la fila
            	int cantAbejas = Math.max(0, 4 - ronda + 1);
                //int cantPolillas = Math.min(4, ronda-1);
                
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

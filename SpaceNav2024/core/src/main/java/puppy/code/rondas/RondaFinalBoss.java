package puppy.code.rondas;

import java.util.List;

import puppy.code.Enemigo;
import puppy.code.GameManager;
import puppy.code.PantallaJuego;
import puppy.code.fabricasAbstractas.FabricaAbstractaEnemigo;
import puppy.code.fabricasConcretas.FabricaEnemigosPorRonda;

public class RondaFinalBoss implements InterfaceStrategiaRonda {
	
	@Override
	public void configurarRonda(PantallaJuego juego, List<Enemigo> enemigosLista){
		enemigosLista.clear(); // Limpiar lista antes de generar nuevos enemigos

		FabricaAbstractaEnemigo fabricaAbstracta = new FabricaEnemigosPorRonda();
	
        // Configuración de filas
        int filas = 3;               // Número de filas de enemigos
        int enemigosPorFila = 4;     // Número de enemigos en cada fila
        int ronda = GameManager.getInstance().getRonda() + 1; // Nivel del enemigo
        int espacioHorizontal = 200; // Espacio entre enemigos en la misma fila
        int espacioVertical = 100;   // Espacio entre filas

        // Coordenadas iniciales
        int yInicial = 400;          // Altura inicial de la primera fila
        int xInicial = 200;          // Posición inicial en X

     // Generación de filas de enemigos
        int enemyX = xInicial;
        int enemyY = yInicial + espacioVertical; // Incremento en Y por cada fila
        
        for (int i = 0; i < enemigosPorFila; i++) {
   
            enemigosLista.add(fabricaAbstracta.crearMiniBoss(enemyX, enemyY, GameManager.getInstance().getJuego()));
            
            // Incrementar posición X para el siguiente enemigo en la fila
            enemyX += espacioHorizontal;
        }
        
        enemigosLista.add(fabricaAbstracta.crearFinalBoss(enemyX, enemyY, GameManager.getInstance().getJuego()));
	}
}

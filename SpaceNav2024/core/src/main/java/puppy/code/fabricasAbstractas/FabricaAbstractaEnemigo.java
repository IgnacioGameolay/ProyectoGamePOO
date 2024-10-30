package puppy.code.fabricasAbstractas;

import puppy.code.Enemigo;
import puppy.code.PantallaJuego;

public interface FabricaAbstractaEnemigo {
	Enemigo crearPolilla(int x, int y, PantallaJuego juego);
    Enemigo crearAbeja(int x, int y, PantallaJuego juego);
    Enemigo crearMiniBoss(int x, int y, PantallaJuego juego);
    Enemigo crearFinalBoss(int x, int y, PantallaJuego juego);
}

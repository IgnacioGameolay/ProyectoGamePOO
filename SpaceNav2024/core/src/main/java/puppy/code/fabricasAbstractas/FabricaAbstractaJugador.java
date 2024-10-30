package puppy.code.fabricasAbstractas;

import puppy.code.Nave4;
import puppy.code.PantallaJuego;

public interface FabricaAbstractaJugador {
	Nave4 crearNave(PantallaJuego juego);
}

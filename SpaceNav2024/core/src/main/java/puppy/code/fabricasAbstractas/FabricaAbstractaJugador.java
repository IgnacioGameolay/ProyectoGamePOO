package puppy.code.fabricasAbstractas;

import puppy.code.Nave;
import puppy.code.PantallaJuego;

public interface FabricaAbstractaJugador {
	Nave crearNave(PantallaJuego juego);
}

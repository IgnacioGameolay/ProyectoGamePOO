package puppy.code.rondas;

import java.util.List;
import puppy.code.Enemigo;
import puppy.code.PantallaJuego;

public interface InterfaceStrategiaRonda {
	void configurarRonda(PantallaJuego juego, List<Enemigo> enemigosLista);
}

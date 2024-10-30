package puppy.code.fabricasConcretas;
import puppy.code.Enemigo;
import puppy.code.PantallaJuego;
import puppy.code.enemigos.EnemigoAbeja;
import puppy.code.enemigos.EnemigoFinalBoss;
import puppy.code.enemigos.EnemigoMiniBoss;
import puppy.code.enemigos.EnemigoPolilla;
import puppy.code.fabricasAbstractas.FabricaAbstractaEnemigo;

public class FabricaEnemigosPorRonda implements FabricaAbstractaEnemigo {

    @Override
    public Enemigo crearPolilla(int x, int y, PantallaJuego juego) {
        return new EnemigoPolilla(x, y, juego);  // Constructor de EnemigoPolilla
    }

    @Override
    public Enemigo crearAbeja(int x, int y, PantallaJuego juego) {
        return new EnemigoAbeja(x, y, juego);  // Constructor de EnemigoAbeja
    }

    @Override
    public Enemigo crearMiniBoss(int x, int y, PantallaJuego juego) {
        return new EnemigoMiniBoss(x, y, juego);  // Constructor de MiniBoss
    }

    @Override
    public Enemigo crearFinalBoss(int x, int y, PantallaJuego juego) {
        return new EnemigoFinalBoss(x, y, juego);  // Constructor de FinalBoss
    }
}

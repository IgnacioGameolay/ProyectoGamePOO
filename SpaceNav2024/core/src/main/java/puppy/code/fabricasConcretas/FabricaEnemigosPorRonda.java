package puppy.code.fabricasConcretas;
import puppy.code.Enemigo;
import puppy.code.PantallaJuego;
import puppy.code.enemigos.EnemigoAbeja;
import puppy.code.enemigos.EnemigoFinalBoss;
import puppy.code.enemigos.EnemigoMiniBoss;
import puppy.code.enemigos.EnemigoPolilla;
import puppy.code.fabricasAbstractas.FabricaAbstractaEnemigo;

/**
 * Implementación de la fábrica abstracta para crear enemigos según la ronda.
 * Esta clase es responsable de instanciar diferentes tipos de enemigos en el juego.
 */
public class FabricaEnemigosPorRonda implements FabricaAbstractaEnemigo {

    /**
     * Crea una nueva instancia de EnemigoPolilla en la posición especificada.
     *
     * @param x La coordenada X donde se creará el enemigo.
     * @param y La coordenada Y donde se creará el enemigo.
     * @param juego La pantalla del juego donde se creará el enemigo.
     * @return Una nueva instancia de EnemigoPolilla.
     */
    @Override
    public Enemigo crearPolilla(int x, int y, PantallaJuego juego) {
        return new EnemigoPolilla(x, y, juego); // Crea y devuelve una nueva polilla
    }

    /**
     * Crea una nueva instancia de EnemigoAbeja en la posición especificada.
     *
     * @param x La coordenada X donde se creará el enemigo.
     * @param y La coordenada Y donde se creará el enemigo.
     * @param juego La pantalla del juego donde se creará el enemigo.
     * @return Una nueva instancia de EnemigoAbeja.
     */
    @Override
    public Enemigo crearAbeja(int x, int y, PantallaJuego juego) {
        return new EnemigoAbeja(x, y, juego); // Crea y devuelve una nueva abeja
    }

    /**
     * Crea una nueva instancia de EnemigoMiniBoss en la posición especificada.
     *
     * @param x La coordenada X donde se creará el mini jefe.
     * @param y La coordenada Y donde se creará el mini jefe.
     * @param juego La pantalla del juego donde se creará el enemigo.
     * @return Una nueva instancia de EnemigoMiniBoss.
     */
    @Override
    public Enemigo crearMiniBoss(int x, int y, PantallaJuego juego) {
        return new EnemigoMiniBoss(x, y, juego); // Crea y devuelve un nuevo mini jefe
    }

    /**
     * Crea una nueva instancia de EnemigoFinalBoss en la posición especificada.
     *
     * @param x La coordenada X donde se creará el jefe final.
     * @param y La coordenada Y donde se creará el jefe final.
     * @param juego La pantalla del juego donde se creará el enemigo.
     * @return Una nueva instancia de EnemigoFinalBoss.
     */
    @Override
    public Enemigo crearFinalBoss(int x, int y, PantallaJuego juego) {
        return new EnemigoFinalBoss(x, y, juego); // Crea y devuelve un nuevo jefe final
    }
}
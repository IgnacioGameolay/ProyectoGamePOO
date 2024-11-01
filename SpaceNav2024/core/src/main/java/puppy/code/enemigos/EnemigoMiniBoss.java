package puppy.code.enemigos;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;

import puppy.code.Bullet;
import puppy.code.Enemigo;
import puppy.code.PantallaJuego;



/**
 * Clase que representa un enemigo de tipo MiniBoss en el juego.
 * Extiende la clase base Enemigo y define su comportamiento específico.
 */
public class EnemigoMiniBoss extends Enemigo {

	/**
     * Constructor para crear una instancia de EnemigoMiniBoss.
     *
     * @param x La coordenada X inicial del enemigo.
     * @param y La coordenada Y inicial del enemigo.
     * @param juego La pantalla del juego donde se encuentra este enemigo.
     */
    public EnemigoMiniBoss(int x, int y, PantallaJuego juego) {
        super(x, y, 30, 4, 4, new Texture(Gdx.files.internal("ene3.png")), juego,  MathUtils.random(0.5f, 1f), 10);
        this.aumentarTamano(1.5f); // Aumenta el tamaño del MiniBoss
    }

    /**
     * Método que define el movimiento del enemigo MiniBoss.
     * Se mueve de lado a lado y desciende una vez que alcanza el borde de la pantalla.
     */
    @Override
    public void mover() {
    	// Implementación de movimiento específica para EnemigoMiniBoss
        if (movingRight) {
            x += velocidadX; // Mover a la derecha
        } else {
            x -= velocidadX; // Mover a la izquierda
        }

     // Verificar límites de pantalla y cambiar dirección
        if (x < 0 || x + spr.getWidth() > Gdx.graphics.getWidth()) {
            movingDown = true; // Cambiar a movimiento vertical
            movingRight = !movingRight; // Cambiar dirección horizontal
        }

        // Mover hacia abajo
        if (movingDown) {
            y -= downMovementDistance; // Desciende
            movingDown = false; // Resetea el movimiento hacia abajo
        }

        spr.setPosition(x, y);// Actualizar la posición del sprite
    }

    /**
     * Método que permite al enemigo MiniBoss disparar una bala.
     * Crea una nueva instancia de Bullet y la agrega al juego.
     */
    @Override
    public void disparar() {
        if (juego != null) {
            Bullet bala = new Bullet(spr.getX() + spr.getWidth() / 2 - 5, spr.getY() + spr.getHeight() - 5, 0f, -0.5f, txBala);
            juego.agregarBalaEnemigo(bala); // Agrega la bala al juego
        }
    }
}

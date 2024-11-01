package puppy.code.enemigos;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;

import puppy.code.Bullet;
import puppy.code.Enemigo;
import puppy.code.PantallaJuego;

/**
 * Clase que representa un enemigo de tipo Abeja en el juego.
 * Extiende la clase base Enemigo y define su comportamiento específico.
 */
public class EnemigoAbeja extends Enemigo {

    /**
     * Constructor para crear una instancia de EnemigoAbeja.
     *
     * @param x La coordenada X inicial del enemigo.
     * @param y La coordenada Y inicial del enemigo.
     * @param juego La pantalla del juego donde se encuentra este enemigo.
     */
    public EnemigoAbeja(int x, int y, PantallaJuego juego) {
        // Inicializa las propiedades del enemigo
        // x, y, size, xSpeed, ySpeed, nueva textura, juego, velocidad de ataque, vida
        super(x, y, 20, 2, 2, new Texture(Gdx.files.internal("ene1.png")), juego, MathUtils.random(0.1f, 0.5f), 1);
    }

    /**
     * Método que define el movimiento del enemigo Abeja.
     * Se mueve de lado a lado y desciende una vez que alcanza el borde de la pantalla.
     */
    @Override
    public void mover() {
        // Implementación de movimiento específica para EnemigoAbeja
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

        spr.setPosition(x, y); // Actualizar la posición del sprite
    }

    /**
     * Método que permite al enemigo Abeja disparar una bala.
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
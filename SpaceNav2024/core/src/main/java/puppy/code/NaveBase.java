package puppy.code;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;

/**
 * Clase que representa la nave base del jugador.
 * Extiende la clase Nave y proporciona implementación para el movimiento y disparo.
 */
public class NaveBase extends Nave {

    /**
     * Constructor de la clase NaveBase.
     * 
     * @param juego Referencia a la pantalla de juego
     */
    public NaveBase(PantallaJuego juego) {
        super(Gdx.graphics.getWidth() / 2 - 50, // Posición inicial X en el centro de la pantalla
              30, // Posición inicial Y
              new Texture(Gdx.files.internal("spaceship1.png")), // Textura de la nave
              Gdx.audio.newSound(Gdx.files.internal("naveHurt.mp3")), // Sonido al recibir daño
              new Texture(Gdx.files.internal("bullet.png")), // Textura de la bala
              Gdx.audio.newSound(Gdx.files.internal("shoot_theme.mp3")), // Sonido al disparar
              juego, // Pantalla de juego
              3, // Vida inicial de la nave
              new Texture(Gdx.files.internal("spaceship1_1.png")), // Textura de la nave con luz encendida
              new Texture[]{ // Sprites de los propulsores
                  new Texture(Gdx.files.internal("spaceship2.png")),
                  new Texture(Gdx.files.internal("spaceship3.png")),
                  new Texture(Gdx.files.internal("spaceship4.png")),
                  new Texture(Gdx.files.internal("spaceship5.png"))
              });
    }

    /**
     * Método para manejar el movimiento de la nave basado en la entrada del teclado.
     * Incrementa o decrementa la velocidad en función de las teclas presionadas.
     */
    @Override
    public void mover() {
        // Movimiento con teclado
        if (Gdx.input.isKeyJustPressed(Input.Keys.LEFT)) velocidadX--; // Mueve a la izquierda
        if (Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)) velocidadX++; // Mueve a la derecha

        // Mantener dentro de los límites de la pantalla
        if (x + velocidadX < 0 || x + velocidadX + spr.getWidth() > Gdx.graphics.getWidth()) {
            velocidadX *= -1; // Invertir velocidad si se sale de los límites
        }
        spr.setPosition(x + velocidadX, y); // Actualiza la posición de la nave
    }

    /**
     * Método para disparar una bala desde la nave.
     * Crea una nueva instancia de Bullet y la agrega a la pantalla de juego.
     */
    @Override
    public void disparar() {
        if (juego != null) {
            Bullet bala = new Bullet(spr.getX() + spr.getWidth() / 2 - 5, // Posición X de la bala
                                      spr.getY() + spr.getHeight() - 5, // Posición Y de la bala
                                      0, // Velocidad en X
                                      3, // Velocidad en Y
                                      txBala); // Textura de la bala
            juego.agregarBalaJugador(bala); // Agrega la bala al juego
            soundBala.play(); // Reproduce el sonido de disparo
        }
    }
}

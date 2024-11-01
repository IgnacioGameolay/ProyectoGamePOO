package puppy.code;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

/**
 * Clase que representa una bala en el juego.
 */
public class Bullet {

    private float xSpeed;   // Velocidad en el eje X de la bala
    private float ySpeed;   // Velocidad en el eje Y de la bala
    private boolean destroyed = false; // Estado de destrucción de la bala
    private Sprite spr;     // Sprite que representa visualmente la bala

    /**
     * Constructor de la clase Bullet.
     * 
     * @param x Coordenada X inicial de la bala
     * @param y Coordenada Y inicial de la bala
     * @param xSpeed Velocidad en el eje X de la bala
     * @param ySpeed Velocidad en el eje Y de la bala
     * @param tx Textura utilizada para el sprite de la bala
     */
    public Bullet(float x, float y, float xSpeed, float ySpeed, Texture tx) {
        spr = new Sprite(tx);
        spr.setPosition(x, y);
        this.xSpeed = xSpeed;
        this.ySpeed = ySpeed;
    }

    /**
     * Actualiza la posición de la bala en base a su velocidad.
     * Se verifica si la bala ha salido de los límites de la pantalla.
     */
    public void update() {
        spr.setPosition(spr.getX() + xSpeed, spr.getY() + ySpeed);
        // Verifica si la bala se ha salido de los límites de la pantalla
        if (spr.getX() < 0 || spr.getX() + spr.getWidth() > Gdx.graphics.getWidth()) {
            destroyed = true;
        }
        if (spr.getY() < 0 || spr.getY() + spr.getHeight() > Gdx.graphics.getHeight()) {
            destroyed = true;
        }
    }

    /**
     * Dibuja la bala en la pantalla.
     * 
     * @param batch El SpriteBatch utilizado para el dibujo
     */
    public void draw(SpriteBatch batch) {
        spr.draw(batch);
    }

    /**
     * Verifica si la bala colisiona con un enemigo.
     * 
     * @param e El enemigo con el que se verifica la colisión
     * @return true si hay colisión, false en caso contrario
     */
    public boolean checkCollision(Enemigo e) {
        if (spr.getBoundingRectangle().overlaps(e.getArea())) {
            // Se destruyen ambos
            e.checkCollision(this);
            this.destroyed = true;
            return true;
        }
        return false;
    }

    /**
     * Verifica si la bala colisiona con la nave del jugador.
     * 
     * @param n La nave con la que se verifica la colisión
     * @return true si hay colisión, false en caso contrario
     */
    public boolean checkCollision(Nave n) {
        if (spr.getBoundingRectangle().overlaps(n.getArea())) {
            this.destroyed = true;
            return true;
        }
        return false;
    }

    /**
     * Verifica si la bala ha sido destruida.
     * 
     * @return true si la bala está destruida, false en caso contrario
     */
    public boolean isDestroyed() {
        return destroyed;
    }

    /**
     * Establece el estado de destrucción de la bala.
     * 
     * @param state El nuevo estado de destrucción de la bala
     */
    public void setDestroyed(boolean state) {
        this.destroyed = state;
    }

    /**
     * Obtiene el área de colisión de la bala.
     * 
     * @return Un rectángulo que representa el área de colisión de la bala
     */
    public Rectangle getArea() {
        return spr.getBoundingRectangle();
    }
}

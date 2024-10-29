package puppy.code;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class Ball2 extends Entidad {
    private Sprite spr;
    private boolean movingRight = true; // Indica la dirección de movimiento horizontal
    private boolean movingDown = false; // Indica si debe bajar después de chocar
    private float downMovementDistance = 10; // Distancia a bajar tras chocar

    public Ball2(int x, int y, int size, int xSpeed, int ySpeed, Texture texture) {
        super(x, y, size, xSpeed, ySpeed, texture, xSpeed, ySpeed);
        this.spr = new Sprite(texture);
        
        // Asegurar que Ball2 no salga fuera de los límites de la pantalla
        if (x - size < 0) this.x = size;
        if (x + size > Gdx.graphics.getWidth()) this.x = Gdx.graphics.getWidth() - size;
        if (y - size < 0) this.y = size;
        if (y + size > Gdx.graphics.getHeight()) this.y = Gdx.graphics.getHeight() - size;

        spr.setPosition(this.x, this.y);
    }

    @Override
    public void mover() {
        // Moverse hacia la derecha o izquierda según la dirección
        if (movingRight) {
            x += velocidadX; // Mover hacia la derecha
        } else {
            x -= velocidadX; // Mover hacia la izquierda
        }

        // Verificar colisión con los bordes
        if (x < 0) {
            x = 0; // Asegurarse de no salir por la izquierda
            movingDown = true; // Cambiar a movimiento hacia abajo
        } else if (x + spr.getWidth() > Gdx.graphics.getWidth()) {
            x = (int) (Gdx.graphics.getWidth() - spr.getWidth()); // Asegurarse de no salir por la derecha
            movingDown = true; // Cambiar a movimiento hacia abajo
        }

        // Mover hacia abajo si es necesario
        if (movingDown) {
            y -= downMovementDistance; // Mover hacia abajo
            movingDown = false; // Resetear el movimiento hacia abajo
            movingRight = !movingRight; // Cambiar la dirección horizontal
        }

        // Limitar el movimiento vertical dentro de la pantalla
        if (y < 0) {
            y = 0; // Asegurarse de no salir por la parte inferior
        } else if (y + spr.getHeight() > Gdx.graphics.getHeight()) {
            y = (int) (Gdx.graphics.getHeight() - spr.getHeight()); // Asegurarse de no salir por la parte superior
        }

        spr.setPosition(x, y); // Actualizar la posición del sprite
    }

    @Override
    public void disparar() {
        // No implementado para Ball2
    }

    public void checkCollision(Ball2 other) {
        if (spr.getBoundingRectangle().overlaps(other.spr.getBoundingRectangle())) {
            /* Invertir las velocidades para simular el rebote
            this.velocidadX *= -1;
            this.velocidadY *= -1;
            other.velocidadX *= -1;
            other.velocidadY *= -1;*/
        	this.velocidadX *= 1;
        }
    }

    public Rectangle getArea() {
        return spr.getBoundingRectangle();
    }

    public void draw(SpriteBatch batch) {
        spr.draw(batch);
    }
}

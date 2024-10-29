package puppy.code;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class Ball2 extends Entidad {
    private Sprite spr;

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
        // Actualizar la posición y verificar colisiones con los bordes
        x += velocidadX;
        y += velocidadY;

        if (x < 0 || x + spr.getWidth() > Gdx.graphics.getWidth()) {
        	velocidadX *= -1;
        }
        if (y < 0 || y + spr.getHeight() > Gdx.graphics.getHeight()) {
        	velocidadY *= -1;
        }

        spr.setPosition(x, y);
    }

    @Override
    public void disparar() {
        // No implementado para Ball2
    }

    public void checkCollision(Ball2 other) {
        if (spr.getBoundingRectangle().overlaps(other.spr.getBoundingRectangle())) {
            // Invertir las velocidades para simular el rebote
            this.velocidadX *= -1;
            this.velocidadY *= -1;
            other.velocidadX *= -1;
            other.velocidadY *= -1;
        }
    }

    public Rectangle getArea() {
        return spr.getBoundingRectangle();
    }

    public void draw(SpriteBatch batch) {
        spr.draw(batch);
    }
}

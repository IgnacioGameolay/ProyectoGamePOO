package puppy.code.enemigos;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;

import puppy.code.Bullet;
import puppy.code.Enemigo;
import puppy.code.PantallaJuego;

public class EnemigoFinalBoss extends Enemigo {

    public EnemigoFinalBoss(int x, int y, PantallaJuego juego) {
        super(x, y, 30, 1, 5, new Texture(Gdx.files.internal("ene4.png")), juego,  MathUtils.random(0.8f, 1f), 20);
        this.aumentarTamano(2.0f);
    }

    @Override
    public void mover() {
        // Implementación de movimiento específica para EnemigoSimple
        if (movingRight) {
            x += velocidadX;
        } else {
            x -= velocidadX;
        }

        if (x < 0 || x + spr.getWidth() > Gdx.graphics.getWidth()) {
            movingDown = true;
            movingRight = !movingRight;
        }

        if (movingDown) {
            y -= downMovementDistance;
            movingDown = false;
        }

        spr.setPosition(x, y);
    }

    @Override
    public void disparar() {
        if (juego != null) {
            Bullet bala = new Bullet(spr.getX() + spr.getWidth() / 2 - 5, spr.getY() + spr.getHeight() - 5, 0f, -1f, txBala);
            juego.agregarBalaEnemigo(bala);
        }
    }
}

package puppy.code.enemigos;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;

import puppy.code.Bullet;
import puppy.code.Enemigo;
import puppy.code.PantallaJuego;

public class EnemigoPolilla extends Enemigo {
    public EnemigoPolilla(int x, int y, PantallaJuego juego) {
        super(x, y, 20, 2, 2, new Texture(Gdx.files.internal("ene2.png")), juego,  MathUtils.random(0.1f, 0.5f), 3);
    }

    @Override
    public void mover() {
        // Implementación de movimiento específica para EnemigoSimple
        if (movingRight) {
            x += velocidadX+10;
        } else {
            x -= velocidadX+10;
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
        if ((this.juego != null)) {
            Bullet bala = new Bullet(spr.getX() + spr.getWidth() / 2 - 5, spr.getY() + spr.getHeight() - 5, 0f, -0.5f, txBala);
            juego.agregarBalaEnemigo(bala);
        }
    }
}

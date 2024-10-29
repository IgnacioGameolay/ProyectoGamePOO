package puppy.code;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;

public class Nave4 extends Entidad {
    private PantallaJuego juego;
    private boolean destruida = false;
    private Sprite spr;
    private Sound sonidoHerido;
    private Sound soundBala;
    private Texture txBala;
    private boolean herido = false;
    private int tiempoHeridoMax = 10;
    private int tiempoHerido;

    public Nave4(int x, int y, Texture tx, Sound soundChoque, 
    		Texture txBala, Sound soundBala, PantallaJuego juego, int vida) {
        super(x, y, 45, 0, 0, tx, 0, 0, vida);  // Inicializa posición y tamaño en la clase Entidad
        this.juego = juego;
        this.sonidoHerido = soundChoque;
        this.soundBala = soundBala;
        this.txBala = txBala;
        this.spr = new Sprite(tx);
        this.spr.setPosition(x, y);
        this.spr.setBounds(x, y, 150, 150);
    }

    public void draw(SpriteBatch batch) {
        float x = spr.getX();
        float y = spr.getY();

        if (!herido) {
        	// Movimiento con teclado
            if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            	velocidadX = -5;
            } else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            	velocidadX = 5;
            } else {
            	velocidadX = 0;
            }
            
         // Mantener dentro de los límites de la pantalla
            if (x + velocidadX < 0) {
                velocidadX = 0;
                spr.setX(0);
            } else if (x + velocidadX + spr.getWidth() > Gdx.graphics.getWidth()) {
                velocidadX = 0;
                spr.setX(Gdx.graphics.getWidth() - spr.getWidth());
            }
            spr.setPosition(x + velocidadX, y + velocidadY);
            spr.draw(batch);
        } else {
            // Efecto de sacudida cuando está herido
            spr.setX(spr.getX() + MathUtils.random(-2, 2));
            spr.draw(batch);
            spr.setX(x);  // Restaurar posición original después de la sacudida
            tiempoHerido--;

            if (tiempoHerido <= 0) herido = false;
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            disparar();
        }
    }

    @Override
    public void mover() {
        // Movimiento con teclado
        if (Gdx.input.isKeyJustPressed(Input.Keys.LEFT)) velocidadX--;
        if (Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)) velocidadX++;

        // Mantener dentro de los límites de la pantalla
        if (x + velocidadX < 0 || x + velocidadX + spr.getWidth() > Gdx.graphics.getWidth()) velocidadX *= -1;
        spr.setPosition(x + velocidadX,y);
    }

    @Override
    public void disparar() {
        if (juego != null) {
            Bullet bala = new Bullet(spr.getX() + spr.getWidth() / 2 - 5, spr.getY() + spr.getHeight() - 5, 0, 3, txBala);
            juego.agregarBalaJugador(bala);
            soundBala.play();
        }
    }

    public boolean checkCollision(Enemigo e) {
        if (!herido && e.getArea().overlaps(spr.getBoundingRectangle())) {
            vida--;
            herido = true;
            tiempoHerido = tiempoHeridoMax;
            sonidoHerido.play();
            if (vida <= 0) destruida = true;
            return true;
        }
        return false;
    }
    
    public boolean checkCollision(Bullet b) {
        if (!herido && b.getArea().overlaps(spr.getBoundingRectangle())) {
        	b.setDestroyed(true);
            vida--;
            herido = true;
            tiempoHerido = tiempoHeridoMax;
            sonidoHerido.play();
            if (vida <= 0) destruida = true;
            return true;
        }
        return false;
    }

    public boolean estaDestruido() {
        return !herido && destruida;
    }

    public boolean estaHerido() {
        return herido;
    }
    
    public int getVidas() {
        return vida;
    }

    public int getX() {
        return (int) spr.getX();
    }

    public int getY() {
        return (int) spr.getY();
    }

    public void setVidas(int vidas) {
        this.vida = vidas;
    }
    
    public Rectangle getArea() {
        return spr.getBoundingRectangle();
    }
    
}

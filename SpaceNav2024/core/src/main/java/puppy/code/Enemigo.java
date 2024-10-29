package puppy.code;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;


public class Enemigo extends Entidad {
    private Sprite spr;
    private float lastShotTime;  // Tiempo del último disparo
    private float speedAttack;  // Intervalo de disparo en milisegundos
    private PantallaJuego juego;
    private boolean movingRight = true; // Indica la dirección de movimiento horizontal
    private boolean movingDown = false; // Indica si debe bajar después de chocar
    private float downMovementDistance = 10; // Distancia a bajar tras chocar
    private Texture txBala;
    private boolean herido = false;
    private boolean muerto = false;

    public Enemigo(int x, int y, int size, int xSpeed, int ySpeed, 
    		Texture texture, PantallaJuego juego, float speedAttack, int vida) {
    	
        super(x, y, size, xSpeed, ySpeed, texture, xSpeed, ySpeed, vida);
        this.spr = new Sprite(texture);
        this.txBala = new Texture(Gdx.files.internal("bullet_enemigo.png"));
        this.juego = juego;
        this.lastShotTime = 1;
        this.speedAttack = speedAttack; 
        
        // Asegurar que Enemy no salga fuera de los límites de la pantalla
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
        if (juego != null) {
            Bullet bala = new Bullet(spr.getX() + spr.getWidth() / 2 - 5, spr.getY() + spr.getHeight() - 5, 0f, -0.5f, txBala);
            juego.agregarBalaEnemigo(bala);
 
        }
    }
    
    public boolean puedeDisparar(float delta) {
    	this.lastShotTime += delta;
        if (this.lastShotTime >= (1/this.speedAttack)) {
        	this.lastShotTime = 0; // Reinicia el temporizador
            return true;
        }
        return false;
    }
    
    public boolean checkCollision(Bullet b) {
        if (!this.herido && b.getArea().overlaps(this.spr.getBoundingRectangle())) {
        	b.setDestroyed(true);
            this.vida--;
            if (this.vida <= 0) this.muerto = true;
            return true;
        }
        return false;
    }

    public boolean estaDestruido() {
        return this.muerto;
    }

    public Rectangle getArea() {
        return this.spr.getBoundingRectangle();
    }
    
    public Texture getTxBullet() {
    	return this.txBala;
    }

    public void draw(SpriteBatch batch) {
        this.spr.draw(batch);
    }
}

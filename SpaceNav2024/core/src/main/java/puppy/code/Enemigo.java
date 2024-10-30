package puppy.code;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public abstract class Enemigo extends Entidad {
    protected Sprite spr;
    protected float lastShotTime;  // Tiempo del último disparo
    protected float speedAttack;  // Intervalo de disparo en milisegundos
    protected PantallaJuego juego;
    protected boolean movingRight = true; // Indica la dirección de movimiento horizontal
    protected boolean movingDown = false; // Indica si debe bajar después de chocar
    protected float downMovementDistance = 10; // Distancia a bajar tras chocar
    protected Texture txBala;
    protected boolean herido = false;
    protected boolean muerto = false;

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

    // Deja mover() como un método abstracto para que cada tipo de enemigo
    // implemente su propio comportamiento de movimiento
    @Override
    public abstract void mover();

    // Deja disparar() como un método abstracto para que cada tipo de enemigo
    // implemente su propio comportamiento de disparo
    @Override
    public abstract void disparar();
    
    public boolean puedeDisparar(float delta) {
    	
        this.lastShotTime += delta;
        if (this.lastShotTime >= (1 / this.speedAttack)) {
        	
            this.lastShotTime = 0; // Reinicia el temporizador
            System.out.println("deverria choter?" + lastShotTime);
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

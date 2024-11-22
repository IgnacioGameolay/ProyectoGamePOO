package puppy.code;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

/**
 * Clase abstracta que representa un enemigo en el juego.
 */
public abstract class Enemigo extends Entidad {
    protected Sprite spr; // Sprite que representa visualmente al enemigo
    protected float lastShotTime; // Tiempo del último disparo
    protected float speedAttack; // Intervalo de disparo en milisegundos
    protected PantallaJuego juego; // Referencia a la pantalla del juego
    protected boolean movingRight = true; // Indica la dirección de movimiento horizontal
    protected boolean movingDown = false; // Indica si debe bajar después de chocar
    protected float downMovementDistance = 10; // Distancia a bajar tras chocar
    protected Texture txBala; // Textura de la bala del enemigo
    protected boolean herido = false; // Indica si el enemigo ha sido herido
    protected boolean muerto = false; // Indica si el enemigo está muerto

    /**
     * Constructor de la clase Enemigo.
     * 
     * @param x Coordenada X inicial del enemigo
     * @param y Coordenada Y inicial del enemigo
     * @param size Tamaño del enemigo
     * @param xSpeed Velocidad en el eje X del enemigo
     * @param ySpeed Velocidad en el eje Y del enemigo
     * @param texture Textura utilizada para el sprite del enemigo
     * @param juego Referencia a la pantalla del juego
     * @param speedAttack Intervalo de disparo del enemigo
     * @param vida Vida inicial del enemigo
     */
    public Enemigo(int x, int y, int size, int xSpeed, int ySpeed, 
                   Texture texture, PantallaJuego juego, float speedAttack, int vida) {
        super(x, y, size, xSpeed, ySpeed, texture, xSpeed, ySpeed, vida);
        this.spr = new Sprite(texture);
        this.txBala = new Texture(Gdx.files.internal("bullet_enemigo.png"));
        this.juego = juego;
        this.lastShotTime = 1;
        this.speedAttack = speedAttack; 
        
        // Asegura que el enemigo no salga fuera de los límites de la pantalla
        if (x - size < 0) this.x = size;
        if (x + size > Gdx.graphics.getWidth()) this.x = Gdx.graphics.getWidth() - size;
        if (y - size < 0) this.y = size;
        if (y + size > Gdx.graphics.getHeight()) this.y = Gdx.graphics.getHeight() - size;

        spr.setPosition(this.x, this.y);
    }
    
    /**
     * Método Template que define el flujo de actualización del enemigo.
     */
    public final void actualizar(float delta) {
        verificarEstado(); // Paso 1: Verificar el estado del enemigo
        if (!estaDestruido()) { // Si no está destruido, continúa con las operaciones
            mover(); // Paso 2: Movimiento específico (implementado por la subclase)
            if (puedeDisparar(delta)) { 
                disparar(); // Paso 3: Disparo específico (implementado por la subclase)
            }
        }
    }
    
    /**
     * Verifica el estado del enemigo, como su vida y si está herido.
     */
    protected void verificarEstado() {
        if (this.vida <= 0) {
            this.muerto = true;
        }
    }
    
    /**
     * Aumenta el tamaño del sprite y de la hitbox del enemigo.
     * 
     * @param factor Factor por el cual se aumenta el tamaño del sprite
     */
    public void aumentarTamano(float factor) {
        // Obtener el tamaño actual
        float nuevoAncho = spr.getWidth() * factor;
        float nuevoAlto = spr.getHeight() * factor;
        
        // Ajustar el tamaño del sprite
        spr.setSize(nuevoAncho, nuevoAlto);
        
        // Actualizar la posición de la hitbox
        spr.setPosition(this.x, this.y);
    }

    /**
     * Método abstracto para mover al enemigo.
     * Debe ser implementado por las clases que heredan de Enemigo.
     */
    @Override
    public abstract void mover();

    /**
     * Método abstracto para disparar desde el enemigo.
     * Debe ser implementado por las clases que heredan de Enemigo.
     */
    @Override
    public abstract void disparar();
    
    /**
     * Verifica si el enemigo puede disparar basado en el tiempo transcurrido.
     * 
     * @param delta Tiempo transcurrido desde el último frame
     * @return true si el enemigo puede disparar, false en caso contrario
     */
    public boolean puedeDisparar(float delta) {
        this.lastShotTime += delta;
        if (this.lastShotTime >= (1 / this.speedAttack)) {
            this.lastShotTime = 0; // Reinicia el temporizador
            return true;
        }
        return false;
    }
    
    /**
     * Verifica si el enemigo colisiona con una bala.
     * 
     * @param b La bala con la que se verifica la colisión
     * @return true si hay colisión, false en caso contrario
     */
    public boolean checkCollision(Bullet b) {
        if (!this.herido && b.getArea().overlaps(this.spr.getBoundingRectangle())) {
            b.setDestroyed(true);
            this.vida--;
            if (this.vida <= 0) this.muerto = true;
            return true;
        }
        return false;
    }

    /**
     * Verifica si el enemigo ha sido destruido.
     * 
     * @return true si el enemigo está muerto, false en caso contrario
     */
    public boolean estaDestruido() {
        return this.muerto;
    }

    /**
     * Obtiene el área de colisión del enemigo.
     * 
     * @return Un rectángulo que representa el área de colisión del enemigo
     */
    public Rectangle getArea() {
        return this.spr.getBoundingRectangle();
    }
    
    /**
     * Obtiene la textura de la bala del enemigo.
     * 
     * @return La textura de la bala del enemigo
     */
    public Texture getTxBullet() {
        return this.txBala;
    }

    /**
     * Dibuja el sprite del enemigo en la pantalla.
     * 
     * @param batch El SpriteBatch utilizado para el dibujo
     */
    public void draw(SpriteBatch batch) {
        this.spr.draw(batch);
    }
}

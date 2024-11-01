package puppy.code;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;

/**
 * Clase abstracta que representa una nave en el juego.
 * Extiende la clase Entidad y proporciona métodos para movimiento, disparo y gestión de colisiones.
 */
public abstract class Nave extends Entidad {
	protected PantallaJuego juego; // Referencia a la pantalla de juego
    protected boolean destruida = false; // Indica si la nave está destruida
    protected Sprite spr; // Sprite que representa la nave
    protected Sound sonidoHerido; // Sonido que se reproduce al recibir daño
    protected Sound soundBala; // Sonido del disparo
    protected Texture txBala; // Textura de la bala
    protected boolean herido = false; // Indica si la nave ha sido dañada
    protected int tiempoHeridoMax = 10; // Tiempo máximo en que la nave estará herida
    protected int tiempoHerido; // Contador para el tiempo herido
    protected Texture naveLuzOn; // Textura de la nave con luz encendida
    protected Texture naveLuzOff; // Textura de la nave con luz apagada
    protected Texture propulsor; // Textura del propulsor
    protected boolean luzEncendida; // Estado de la luz de la nave
    protected Texture[] propulsoresSprites; // Array para almacenar los sprites de los propulsores
    protected int indicePropulsor; // Índice del sprite actual de los propulsores
    protected float tiempoAnimacion; // Tiempo acumulado para la animación de los propulsores
    protected float intervaloAnimacion = 100f; // Intervalo entre cada sprite en milisegundos
    protected long tiempoCambioLuz; // Tiempo para cambiar el estado de la luz
    protected int intervaloLuz = 500; // Intervalo de cambio de luz en milisegundos


    /**
     * Constructor de la clase Nave.
     *
     * @param x Coordenada X inicial de la nave
     * @param y Coordenada Y inicial de la nave
     * @param tx Textura de la nave
     * @param soundChoque Sonido al recibir daño
     * @param txBala Textura de la bala
     * @param soundBala Sonido del disparo
     * @param juego Referencia a la pantalla de juego
     * @param vida Vida inicial de la nave
     * @param naveLuzOn Textura de la nave con luz encendida
     * @param propulsoresSprites Sprites de los propulsores
     */
    public Nave(int x, int y, Texture tx, Sound soundChoque, 
    		Texture txBala, Sound soundBala, PantallaJuego juego, int vida, Texture naveLuzOn, Texture[] propulsoresSprites) {
    	super(x, y, 45, 0, 0, tx, 0, 0, vida);
        this.juego = juego;
        this.sonidoHerido = soundChoque;
        this.soundBala = soundBala;
        this.txBala = txBala;
        this.naveLuzOff = tx;
        this.naveLuzOn = naveLuzOn;
        this.propulsoresSprites = propulsoresSprites;
        this.luzEncendida = true;
        this.tiempoCambioLuz = System.currentTimeMillis();
        this.indicePropulsor = 0;
        this.spr = new Sprite(naveLuzOff);
        this.spr.setPosition(x, y);
        this.spr.setBounds(x, y, 150, 150);
    }
    
    /**
     * Dibuja la nave y gestiona su movimiento, animaciones y colisiones.
     *
     * @param batch El SpriteBatch utilizado para dibujar en la pantalla
     */
	public void draw(SpriteBatch batch) {
	    float x = spr.getX();
	    float y = spr.getY();
	    
	    // Alterna la luz en intervalos regulares
	    long tiempoActual = System.currentTimeMillis();
	    if (tiempoActual - tiempoCambioLuz >= intervaloLuz) {
	        luzEncendida = !luzEncendida;
	        tiempoCambioLuz = tiempoActual;
	    }
	    spr.setTexture(luzEncendida ? naveLuzOn : naveLuzOff); // Cambia la textura de la nave
	    // Actualizar la animación de propulsores
	    tiempoAnimacion += Gdx.graphics.getDeltaTime() * 1000; // Tiempo en milisegundos
	    if (tiempoAnimacion >= intervaloAnimacion) {
	        indicePropulsor = (indicePropulsor + 1) % propulsoresSprites.length; // Avanza al siguiente sprite
	        tiempoAnimacion = 0; // Reiniciar el temporizador
	    }
	    
	    // Dibuja el sprite actual del propulsor
	    batch.draw(propulsoresSprites[indicePropulsor], spr.getX() + 1, spr.getY() + 120 - propulsoresSprites[indicePropulsor].getHeight());
	
	    if (!herido) {
	    	// Movimiento de la nave
            if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
                velocidadX = -5; // Mueve hacia la izquierda
            } else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
                velocidadX = 5; // Mueve hacia la derecha
            } else {
                velocidadX = 0; // Detiene el movimiento
            }

	        
            // Mantener dentro de los límites de la pantalla
            if (x + velocidadX < 0) {
                velocidadX = 0;
                spr.setX(0); // Coloca la nave en el borde izquierdo
            } else if (x + velocidadX + spr.getWidth() > Gdx.graphics.getWidth()) {
                velocidadX = 0;
                spr.setX(Gdx.graphics.getWidth() - spr.getWidth()); // Coloca la nave en el borde derecho
            }

            spr.setPosition(x + velocidadX, y + velocidadY); // Actualiza la posición
            spr.draw(batch); // Dibuja la nave
        } else {
            // Efecto de sacudida cuando está herido
            spr.setX(spr.getX() + MathUtils.random(-2, 2)); // Sacudida aleatoria
            spr.draw(batch); // Dibuja la nave
            spr.setX(x); // Restaurar posición original después de la sacudida
            tiempoHerido--; // Disminuir el tiempo herido

            if (tiempoHerido <= 0) herido = false; // Termina el estado herido
        }

    // Disparo
    if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
        disparar();
    }
}

	/**
     * Método abstracto para mover la nave, implementado por las subclases.
     */
    @Override
    public abstract void mover();

    /**
     * Método abstracto para disparar, implementado por las subclases.
     */
    @Override
    public abstract void disparar();

    /**
     * Verifica colisión con un enemigo.
     * 
     * @param e El enemigo con el que se verifica la colisión
     * @return true si hay colisión, false de lo contrario
     */
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
    
    /**
     * Verifica colisión con una bala.
     * 
     * @param b La bala con la que se verifica la colisión
     * @return true si hay colisión, false de lo contrario
     */
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

    /**
     * Verifica si la nave está destruida.
     * 
     * @return true si la nave está destruida, false de lo contrario
     */
    public boolean estaDestruido() {
        return !herido && destruida;
    }
    
    /**
     * Verifica si la nave está herida.
     * 
     * @return true si la nave está herida, false de lo contrario
     */
    public boolean estaHerido() {
        return herido;
    }
    
    /**
     * Retorna la posición X actual del sprite de la nave
     * 
     * @return posición X de la nave y su sprite.
     */
    @Override
    public int getX() {
        return (int) spr.getX();
    }

    /**
     * Retorna la posición Y actual del sprite de la nave
     * 
     * @return posición Y de la nave y su sprite.
     */
    @Override
    public int getY() {
        return (int) spr.getY();
    }

    /**
     * Retorna el rectángulo de colisión del sprite de la nave
     * 
     * @return rectángulo de colisión del sprite de la nave
     */
    public Rectangle getArea() {
        return spr.getBoundingRectangle();
    }
    
}

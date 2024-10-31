package puppy.code;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;

public abstract class Nave extends Entidad {
    protected PantallaJuego juego;
    protected boolean destruida = false;
    protected Sprite spr;
    protected Sound sonidoHerido;
    protected Sound soundBala;
    protected Texture txBala;
    protected boolean herido = false;
    protected int tiempoHeridoMax = 10;
    protected int tiempoHerido;
    protected Texture naveLuzOn;
    protected Texture naveLuzOff;
    protected Texture propulsor;
    protected boolean luzEncendida;
    protected Texture[] propulsoresSprites; // Array para almacenar los sprites de los propulsores
    protected int indicePropulsor;           // Índice del sprite actual
    protected float tiempoAnimacion;          // Tiempo para la animación
    protected float intervaloAnimacion = 100f; // Intervalo entre cada sprite en milisegundos

    protected long tiempoCambioLuz;     // Tiempo para cambiar la luz
    protected int intervaloLuz = 500;   // Intervalo de cambio de luz en ms
    

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
        this.indicePropulsor = 0;  // Inicia con el primer sprite de propulsor
        this.spr = new Sprite(naveLuzOff);
        this.spr.setPosition(x, y);
        this.spr.setBounds(x, y, 150, 150);
    }

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

    // Disparo
    if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
        disparar();
    }
}

    @Override
    public abstract void mover();

    @Override
    public abstract void disparar();

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
    

    public int getX() {
        return (int) spr.getX();
    }

    public int getY() {
        return (int) spr.getY();
    }

    
    public Rectangle getArea() {
        return spr.getBoundingRectangle();
    }
    
}

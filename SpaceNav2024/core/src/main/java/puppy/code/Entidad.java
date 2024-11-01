package puppy.code;

import com.badlogic.gdx.graphics.Texture;

/**
 * Clase abstracta que representa una entidad en el juego.
 */
public abstract class Entidad {
    protected int x; // Posición en el eje X
    protected int y; // Posición en el eje Y
    protected float velocidadX; // Velocidad de la entidad en el eje X
    protected float velocidadY; // Velocidad de la entidad en el eje Y
    protected int vida; // Puntos de vida de la entidad

    /**
     * Constructor de la clase Entidad.
     * 
     * @param x Posición inicial en el eje X
     * @param y Posición inicial en el eje Y
     * @param size Tamaño de la entidad (no se utiliza directamente en esta clase)
     * @param xSpeed Velocidad en el eje X
     * @param ySpeed Velocidad en el eje Y
     * @param texture Textura utilizada para el sprite (no se utiliza directamente en esta clase)
     * @param velocidadX Velocidad en el eje X de la entidad
     * @param velocidadY Velocidad en el eje Y de la entidad
     * @param vida Puntos de vida de la entidad
     */
    public Entidad(int x, int y, int size, int xSpeed, int ySpeed, 
                   Texture texture, float velocidadX, float velocidadY, int vida) {
        this.x = x;
        this.y = y;
        this.velocidadX = velocidadX;
        this.velocidadY = velocidadY;
        this.vida = vida;
    }
    
    /**
     * Método abstracto para mover la entidad.
     * Debe ser implementado por las clases que heredan de Entidad.
     */
    public abstract void mover(); 

    /**
     * Método abstracto para disparar desde la entidad.
     * Debe ser implementado por las clases que heredan de Entidad.
     */
    public abstract void disparar();
    
    // Getters y Setters

    /**
     * Obtiene la posición en el eje X de la entidad.
     * 
     * @return La posición en el eje X
     */
    public int getX() {
        return x;
    }

    /**
     * Establece la posición en el eje X de la entidad.
     * 
     * @param x Nueva posición en el eje X
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * Obtiene la posición en el eje Y de la entidad.
     * 
     * @return La posición en el eje Y
     */
    public int getY() {
        return y;
    }

    /**
     * Establece la posición en el eje Y de la entidad.
     * 
     * @param y Nueva posición en el eje Y
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * Obtiene la velocidad en el eje X de la entidad.
     * 
     * @return La velocidad en el eje X
     */
    public float getVelocidadX() {
        return velocidadX;
    }
    
    /**
     * Obtiene la velocidad en el eje Y de la entidad.
     * 
     * @return La velocidad en el eje Y
     */
    public float getVelocidadY() {
        return velocidadY;
    }

    /**
     * Establece la velocidad en el eje X de la entidad.
     * 
     * @param velocidadX Nueva velocidad en el eje X
     */
    public void setVelocidadX(int velocidadX) {
        this.velocidadX = velocidadX;
    }
    
    /**
     * Establece la velocidad en el eje Y de la entidad.
     * 
     * @param velocidadY Nueva velocidad en el eje Y
     */
    public void setVelocidadY(int velocidadY) {
        this.velocidadY = velocidadY;
    }

    /**
     * Obtiene los puntos de vida de la entidad.
     * 
     * @return Los puntos de vida
     */
    public int getVida() {
        return vida;
    }

    /**
     * Establece los puntos de vida de la entidad.
     * 
     * @param vida Nuevos puntos de vida
     */
    public void setVida(int vida) {
        this.vida = vida;
    }
}

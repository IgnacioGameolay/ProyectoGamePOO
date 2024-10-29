package puppy.code;

import com.badlogic.gdx.graphics.Texture;

public abstract class Entidad implements Vivible {
	protected int x;  // Posición en el eje X
    protected int y;  // Posición en el eje Y
    protected float velocidadX;  // Velocidad de la entidad
    protected float velocidadY;  // Velocidad de la entidad
    protected int vida;  // Puntos de vida de la entidad

    // Constructor
    public Entidad(int x, int y, int size, int xSpeed, int ySpeed, 
    		Texture texture, float velocidadX, float velocidadY) {
        this.x = x;
        this.y = y;
        this.velocidadX = velocidadX;
        this.velocidadY = velocidadY;
    }
    
    public abstract void mover(); 
	@Override
	public abstract void disparar();

	@Override
    public void vivir() {
        System.out.println("La entidad está viva.");
    }
	
	public void morir() {
        System.out.println("La entidad ha muerto.");
    }

    
 // Otros métodos comunes
    public boolean isAlive() {
        return vida > 0;
    }

    // Getters y Setters
    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public float getVelocidadX() {
        return velocidadX;
    }
    
    public float getVelocidadY() {
        return velocidadY;
    }

    public void setVelocidadX(int velocidadX) {
        this.velocidadX = velocidadX;
    }
    
    public void setVelocidadY(int velocidadY) {
        this.velocidadY = velocidadY;
    }
    public int getVida() {
        return vida;
    }

    public void setVida(int vida) {
        this.vida = vida;
    }

}

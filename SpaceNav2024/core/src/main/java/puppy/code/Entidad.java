package puppy.code;

public abstract class Entidad implements Vivible {
	protected int x;  // Posición en el eje X
    protected int y;  // Posición en el eje Y
    protected int velocidad;  // Velocidad de la entidad
    protected int vida;  // Puntos de vida de la entidad

    // Constructor
    public Entidad(int x, int y, int velocidad, int vida) {
        this.x = x;
        this.y = y;
        this.velocidad = velocidad;
        this.vida = vida;
    }
    public abstract void mover(); 
	@Override
	public abstract void disparar();

	public void morir() {
        System.out.println("La entidad ha muerto.");
    }

    @Override
    public void vivir() {
        System.out.println("La entidad está viva.");
    }
 // Otros métodos comunes
    public boolean estaVivo() {
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

    public int getVelocidad() {
        return velocidad;
    }

    public void setVelocidad(int velocidad) {
        this.velocidad = velocidad;
    }

    public int getVida() {
        return vida;
    }

    public void setVida(int vida) {
        this.vida = vida;
    }

}

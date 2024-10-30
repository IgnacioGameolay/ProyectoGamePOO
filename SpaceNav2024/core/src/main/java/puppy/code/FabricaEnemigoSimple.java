package puppy.code;

public class FabricaEnemigoSimple implements FabricaEnemigo{
	@Override
	public InterfaceEnemigo crearEnemigo() {
		return new EnemigoSimple();
	}
}

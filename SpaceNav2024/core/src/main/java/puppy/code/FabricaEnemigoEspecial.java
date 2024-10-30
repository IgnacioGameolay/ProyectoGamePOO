package puppy.code;

public class FabricaEnemigoEspecial implements FabricaEnemigo{
	@Override
	public InterfaceEnemigo crearEnemigo() {
		return new EnemigoEspecial();
	}
}

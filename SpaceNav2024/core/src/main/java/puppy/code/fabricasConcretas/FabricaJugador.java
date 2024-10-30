package puppy.code.fabricasConcretas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

import puppy.code.Nave4;
import puppy.code.PantallaJuego;
import puppy.code.fabricasAbstractas.FabricaAbstractaJugador;

public class FabricaJugador implements FabricaAbstractaJugador{
	@Override
    public Nave4 crearNave(PantallaJuego juego) {
        return new Nave4(Gdx.graphics.getWidth() / 2 - 50, 30, 
                new Texture(Gdx.files.internal("spaceship1.png")),
                Gdx.audio.newSound(Gdx.files.internal("naveHurt.mp3")),
                new Texture(Gdx.files.internal("bullet.png")),
                Gdx.audio.newSound(Gdx.files.internal("shoot_theme.mp3")),
                juego, 3);  // Constructor de EnemigoPolilla
    }

}

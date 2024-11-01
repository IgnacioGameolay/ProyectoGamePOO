package puppy.code;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.ScreenUtils;

/**
 * Clase que representa la pantalla de "Game Over".
 * Implementa la interfaz Screen de LibGDX.
 */
public class PantallaGameOver implements Screen {

    private SpaceNavigation game; // Referencia al juego principal
    private OrthographicCamera camera; // Cámara para la proyección

    /**
     * Constructor de la clase PantallaGameOver.
     * 
     * @param game Referencia al juego principal
     */
    public PantallaGameOver(SpaceNavigation game) {
        this.game = game;
        
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 1200, 800); // Configuración de la cámara
    }

    /**
     * Método que se llama para renderizar la pantalla.
     * 
     * @param delta Tiempo transcurrido entre frames
     */
    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0.2f, 1); // Limpia la pantalla con un color de fondo

        camera.update(); // Actualiza la cámara
        game.getBatch().setProjectionMatrix(camera.combined); // Establece la matriz de proyección

        game.getBatch().begin(); // Comienza a dibujar
        game.getFont().draw(game.getBatch(), "Game Over !!! ", 120, 400, 400, 1, true); // Mensaje de Game Over
        game.getFont().draw(game.getBatch(), "Pincha en cualquier lado para reiniciar ...", 100, 300); // Instrucción para reiniciar
        game.getBatch().end(); // Finaliza el dibujo

        // Detecta si se ha tocado la pantalla o se ha presionado una tecla
        if (Gdx.input.isTouched() || Gdx.input.isKeyJustPressed(Input.Keys.ANY_KEY)) {
            Screen ss = new PantallaJuego(game, 1280, 720); // Crea una nueva instancia de PantallaJuego
            ss.resize(1200, 800); // Ajusta el tamaño de la nueva pantalla
            game.setScreen(ss); // Cambia a la nueva pantalla
            dispose(); // Libera recursos de la pantalla actual
        }
    }

    @Override
    public void show() {
    	// TODO Auto-generated method stub
    }

    @Override
    public void resize(int width, int height) {
    	// TODO Auto-generated method stub
    }

    @Override
    public void pause() {
    	// TODO Auto-generated method stub
    }

    @Override
    public void resume() {
    	// TODO Auto-generated method stub
    }

    @Override
    public void hide() {
    	// TODO Auto-generated method stub
    }

    @Override
    public void dispose() {
    	// TODO Auto-generated method stub
    }
}

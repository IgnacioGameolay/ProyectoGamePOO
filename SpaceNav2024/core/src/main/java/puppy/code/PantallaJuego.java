package puppy.code;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import puppy.code.enemigos.*;
import puppy.code.fabricasAbstractas.FabricaAbstractaEnemigo;
import puppy.code.fabricasAbstractas.FabricaAbstractaJugador;
import puppy.code.fabricasConcretas.FabricaEnemigosPorRonda;
import puppy.code.fabricasConcretas.FabricaJugadorBase;


/**
 * Clase que representa la pantalla principal del juego
 */
public class PantallaJuego implements Screen {
	private SpaceNavigation game; // Referencia al objeto principal del juego
    private OrthographicCamera camera; // Cámara para la vista del juego
    private SpriteBatch batch; // Herramienta para dibujar gráficos
    private Sound explosionSound; // Sonido de explosión
    private Music gameMusic; // Música de fondo del juego
    private Texture bgTexture; // Textura de fondo
    private Texture heartTexture; // Textura de corazones que representan vidas
    private int currentScore = 0; // Puntuación actual del jugador
    private Nave nave; // Referencia a la nave del jugador
  
    // Listas para almacenar enemigos y balas
    private List<Enemigo> enemigosLista = new ArrayList<>();
    private List<Bullet> balasJugador = new ArrayList<>();
    private List<Bullet> balasEnemigo = new ArrayList<>();
    
    
 // Constructor de la clase PantallaJuego
    public PantallaJuego(SpaceNavigation game, int alto, int ancho) {
        GameManager.getInstance().setJuego(this); // Configura la instancia del juego
        this.game = game; 
        batch = game.getBatch(); // Obtiene el SpriteBatch del juego
        camera = new OrthographicCamera(); // Inicializa la cámara
        camera.setToOrtho(false, alto, ancho); // Configura la cámara con el tamaño de la pantalla
        bgTexture = new Texture(Gdx.files.internal("wallpaper.jpeg")); // Carga la textura de fondo
        heartTexture = new Texture(Gdx.files.internal("cora.png")); // Carga la textura de corazones
        
        // Inicializar música y sonidos
        explosionSound = Gdx.audio.newSound(Gdx.files.internal("enemy_1_dead.mp3")); // Carga el sonido de explosión
        explosionSound.setVolume(1, 0.25f); // Establece el volumen del sonido de explosión
        gameMusic = Gdx.audio.newMusic(Gdx.files.internal("music_theme.mp3")); // Carga la música de fondo
        gameMusic.setLooping(true); // Hace que la música se repita
        gameMusic.setVolume(0.25f); // Establece el volumen de la música de fondo
        gameMusic.play(); // Reproduce la música
        
        // Cargar la nave del jugador
        FabricaAbstractaJugador fabricaJugador = new FabricaJugadorBase(); // Crea una fábrica para la nave
        nave = fabricaJugador.crearNave(GameManager.getInstance().getJuego()); // Crea la nave utilizando la fábrica

        crearRonda(); // Crea la primera ronda de enemigos
    }
    

    
    /**
     * Método para crear una nueva ronda de enemigos.
     * Este método llama al método crearRonda en el GameManager para inicializar la lista de enemigos.
     */
    public void crearRonda() {
        GameManager.getInstance().crearRonda(enemigosLista);
    }

    /**
     * Método para dibujar la interfaz de usuario (HUD).
     * Este método muestra la cantidad de vidas del jugador, el número de ronda actual,
     * la puntuación y el récord de puntuación en la pantalla.
     */
    public void dibujarHUD() {
        CharSequence str = "Vidas: "; // Texto que muestra el encabezado de vidas
        int vidas = nave.getVida(); // Obtiene la cantidad de vidas de la nave

        // Dibuja hasta 3 corazones según las vidas restantes
        for (int i = 0; i < 3; i++) {
            if (i < vidas) {
                // Dibuja un corazón lleno si la nave tiene vida
                batch.draw(heartTexture, 100 + i * 60, 10); // Dibuja el corazón en la posición correspondiente
            }
        }

        // Dibuja la ronda actual
        CharSequence rondaStr = "Ronda: " + GameManager.getInstance().getRonda(); // Obtiene el número de ronda actual
        game.getFont().getData().setScale(2f); // Establece el tamaño del texto
        game.getFont().draw(batch, rondaStr, 300, 30); // Dibuja el texto de la ronda en la pantalla

        // Dibuja el encabezado y la puntuación actual
        game.getFont().getData().setScale(2f); // Establece nuevamente el tamaño del texto
        game.getFont().draw(batch, str, 10, 30); // Dibuja el texto de vidas
        game.getFont().draw(batch, "Score:" + GameManager.getInstance().getScore(), Gdx.graphics.getWidth() - 150, 30); // Dibuja la puntuación
        game.getFont().draw(batch, "HighScore:" + GameManager.getInstance().getHighScore(), Gdx.graphics.getWidth() / 2 - 80, 30); // Dibuja el récord de puntuación
    }

    
   


    /**
     * Método que renderiza cada cuadro del juego.
     * Este método se encarga de limpiar la pantalla, dibujar el fondo, actualizar
     * y dibujar todos los elementos del juego, y gestionar las colisiones.
     *
     * @param delta El tiempo en segundos desde el último cuadro renderizado.
     */
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1); // Establece el color de limpieza de la pantalla a negro
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); // Limpia el buffer de color
        
        batch.begin(); // Comienza el proceso de dibujo

        // Cambiar el color solo para el fondo
        batch.setColor(1, 0, 0, 1); // Cambiar a rojo
        batch.draw(bgTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight()); // Dibuja el fondo
        
        // Restablecer el color a blanco
        batch.setColor(1, 1, 1, 1); // Restablece el color a blanco

        if (!nave.estaHerido()) { // Verifica si la nave no está herida
            // Colisiones entre balasJugador y enemigos
            for (int i = 0; i < balasJugador.size(); i++) {
                Bullet balaJugador = balasJugador.get(i);
                balaJugador.update(); // Actualiza la posición de la bala
                
                for (int j = 0; j < enemigosLista.size(); j++) {
                    Enemigo enemigo = enemigosLista.get(j);
                    
                    if (enemigo.checkCollision(balaJugador)) { // Verifica colisión entre la bala y el enemigo
                        explosionSound.play(); // Reproduce el sonido de explosión
                        
                        if (enemigo.estaDestruido()) { // Verifica si el enemigo ha sido destruido
                            explosionSound.play(); // Reproduce el sonido de explosión
                            enemigosLista.remove(j); // Elimina el enemigo de la lista
                            j--; // Decrementa el índice para evitar errores
                            currentScore += 10; // Aumenta la puntuación
                            GameManager.getInstance().updateScore(currentScore); // Actualiza la puntuación en el GameManager
                        }
                    }
                }
                if (balaJugador.isDestroyed()) { // Verifica si la bala ha sido destruida
                    balasJugador.remove(i); // Elimina la bala de la lista
                    i--; // Decrementa el índice para evitar errores
                }
            }
        }
        
        // Actualizar enemigos
        for (Enemigo enemigo : enemigosLista) {
            enemigo.actualizar(delta);

            // Actualizar y dibujar balas de enemigos
            for (int i = 0; i < balasEnemigo.size(); i++) {
                Bullet balaEnemigo = balasEnemigo.get(i);
                balaEnemigo.update(); // Actualiza la posición de la bala
                nave.checkCollision(balaEnemigo); // Verifica colisión con la nave
                
                if (balaEnemigo.isDestroyed()) { // Verifica si la bala ha sido destruida
                    balasEnemigo.remove(i); // Elimina la bala de la lista
                    i--; // Decrementa el índice para evitar errores
                }
                balaEnemigo.draw(batch); // Dibuja la bala
            }
        }

        // Dibujar balasJugador
        for (Bullet b : balasJugador) {
            b.draw(batch); // Dibuja cada bala del jugador
        }

        // Dibujar nave
        nave.draw(batch); // Dibuja la nave del jugador

        // Dibujar enemigos y verificar colisión con la nave
        for (int i = 0; i < enemigosLista.size(); i++) {
            Enemigo enemigo = enemigosLista.get(i);
            
            if (nave.checkCollision(enemigo) || enemigo.estaDestruido()) { // Verifica colisión con la nave
                enemigosLista.remove(i); // Elimina el enemigo de la lista
                i--; // Decrementa el índice para evitar errores
            } else {
                enemigo.draw(batch); // Dibuja el enemigo
            }
        }
        
        // Verificar si la nave fue destruida
        if (nave.estaDestruido()) {
            dispose(); // Libera recursos si la nave está destruida
        }
        
        dibujarHUD(); // Dibuja la interfaz de usuario (HUD)
        batch.end(); // Finaliza el proceso de dibujo

        // Verificar si el nivel está completo
        if (enemigosLista.isEmpty()) {
            GameManager.getInstance().updateRonda(); // Actualiza la ronda en el GameManager
            crearRonda(); // Crea una nueva ronda de enemigos
        }
    }


    
    
    /**
     * Agrega una bala del jugador a la lista de balas del jugador.
     *
     * @param bb La bala del jugador a agregar.
     * @return true si la bala fue agregada exitosamente; false en caso contrario.
     */
    public boolean agregarBalaJugador(Bullet bb) {
        return balasJugador.add(bb); // Agrega la bala a la lista de balas del jugador
    }

    /**
     * Agrega una bala de enemigo a la lista de balas de enemigos.
     *
     * @param bb La bala del enemigo a agregar.
     * @return true si la bala fue agregada exitosamente; false en caso contrario.
     */
    public boolean agregarBalaEnemigo(Bullet bb) {
        return balasEnemigo.add(bb); // Agrega la bala a la lista de balas de enemigos
    }

    /**
     * Obtiene el objeto SpriteBatch utilizado para el dibujo.
     *
     * @return El objeto SpriteBatch.
     */
    public SpriteBatch getBatch() {
        return batch; // Devuelve el SpriteBatch para dibujar
    }

    /**
     * Se llama cuando esta pantalla es mostrada.
     * Este método inicia la reproducción de la música del juego.
     */
    @Override
    public void show() {
        gameMusic.play(); // Inicia la música del juego
    }

    /**
     * Libera los recursos utilizados por la pantalla.
     * Este método se llama cuando la pantalla ya no es necesaria.
     */
    @Override
    public void dispose() {
        explosionSound.dispose(); // Libera el recurso de sonido de explosión
        gameMusic.dispose(); // Libera el recurso de música del juego
        bgTexture.dispose(); // Libera el recurso de textura de fondo
        
        // Actualiza el puntaje más alto si el puntaje actual es mayor
        if (GameManager.getInstance().getScore() > GameManager.getInstance().getHighScore()) {
            GameManager.getInstance().setHighScore(GameManager.getInstance().getScore());
        }
        
        GameManager.getInstance().setRonda(1); // Reinicia la ronda a 1
        GameManager.getInstance().setScore(0); // Reinicia el puntaje a 0
        game.setScreen(new PantallaGameOver(game)); // Cambia a la pantalla de Game Over
    }


    @Override
    public void resize(int width, int height) {}

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}
   
}
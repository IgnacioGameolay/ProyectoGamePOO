package puppy.code;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;

import puppy.code.enemigos.*;
import puppy.code.fabricasAbstractas.FabricaAbstractaEnemigo;
import puppy.code.fabricasAbstractas.FabricaAbstractaJugador;
import puppy.code.fabricasConcretas.FabricaEnemigosPorRonda;
import puppy.code.fabricasConcretas.FabricaJugadorBase;


public class PantallaJuego implements Screen {
    private SpaceNavigation game;
    private OrthographicCamera camera;
    private SpriteBatch batch;
    private Sound explosionSound;
    private Music gameMusic;
    private Texture bgTexture;
    private Texture heartTexture;
    private int currentScore = 0;
    private Nave nave;
  
    private List<Enemigo> enemigosLista = new ArrayList<>();
    private List<Bullet> balasJugador = new ArrayList<>();
    private List<Bullet> balasEnemigo = new ArrayList<>();
    
    
    public PantallaJuego(SpaceNavigation game, int alto, int ancho) {
    	GameManager.getInstance().setJuego(this);
        this.game = game; 
        batch = game.getBatch();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, alto, ancho);
        bgTexture = new Texture(Gdx.files.internal("wallpaper.jpeg"));
        heartTexture = new Texture(Gdx.files.internal("cora.png"));
        
        // Inicializar música y sonidos
        explosionSound = Gdx.audio.newSound(Gdx.files.internal("enemy_1_dead.mp3"));
        explosionSound.setVolume(1, 0.25f);
        gameMusic = Gdx.audio.newMusic(Gdx.files.internal("music_theme.mp3"));
        gameMusic.setLooping(true);
        gameMusic.setVolume(0.25f);
        gameMusic.play();
        
        // Cargar nave
        FabricaAbstractaJugador fabricaJugador = new FabricaJugadorBase();
        
        nave = fabricaJugador.crearNave(GameManager.getInstance().getJuego());
        nave.setVida(10);

        crearRonda();
      
    }
    

    
    public void crearRonda() {
    	GameManager.getInstance().crearRonda(enemigosLista);
    }
    
    
    public void dibujarHUD() {
        CharSequence str = "Vidas: ";
        int vidas = nave.getVida();
        for (int i = 0; i < 3; i++) {
            if (i < vidas) {
                // Dibuja un corazón lleno si la nave tiene vida
                batch.draw(heartTexture, 100 + i * 60, 10); 
            }
        }
     // Dibuja la ronda
        CharSequence rondaStr = "Ronda: " + GameManager.getInstance().getRonda();
        game.getFont().getData().setScale(2f);
        game.getFont().draw(batch, rondaStr, 300, 30); 
        
        
        game.getFont().getData().setScale(2f);
        game.getFont().draw(batch, str, 10, 30);
        game.getFont().draw(batch, "Score:" + GameManager.getInstance().getScore(), Gdx.graphics.getWidth() - 150, 30);
        game.getFont().draw(batch, "HighScore:" + GameManager.getInstance().getHighScore(), Gdx.graphics.getWidth() / 2 - 80, 30);
    }
    
   


    @Override
    public void render(float delta) {
    	Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        batch.begin();
        
        // Cambiar el color solo para el fondo
        batch.setColor(1, 0, 0, 1); // Cambiar a rojo
        batch.draw(bgTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        
        // Restablecer el color a blanco
        batch.setColor(1, 1, 1, 1); // Restablecer el color a blanco
        
        

        if (!nave.estaHerido()) {
            // Colisiones entre balasJugador y enemigos
            for (int i = 0; i < balasJugador.size(); i++) {
                Bullet balaJugador = balasJugador.get(i);
                balaJugador.update();
                
                for (int j = 0; j < enemigosLista.size(); j++) {
                    Enemigo enemigo = enemigosLista.get(j);
                    
                    if (enemigo.checkCollision(balaJugador)) {
                    	explosionSound.play();
                    	if (enemigo.estaDestruido()) {
                    		explosionSound.play();
                            enemigosLista.remove(j);
                            j--;
                            currentScore += 10;
                            GameManager.getInstance().updateScore(currentScore);
                    	}
                    }
                }
                if (balaJugador.isDestroyed()) {
                    balasJugador.remove(i);
                    i--;
                }
            }

            
        }
        
     // Actualizar enemigos
        for (Enemigo enemigo : enemigosLista) {
            enemigo.mover();
            if (enemigo.puedeDisparar(delta)) {
            	//System.out.println("estoy shoorint?");
                enemigo.disparar();
            }
            

            for (int i = 0; i < balasEnemigo.size(); i++) {
                Bullet balaEnemigo = balasEnemigo.get(i);
                balaEnemigo.update();
                nave.checkCollision(balaEnemigo);
                if (balaEnemigo.isDestroyed()) {
                	balasEnemigo.remove(i);
                    i--;
                }
                balaEnemigo.draw(batch);
            }
        }

        // Dibujar balasJugador
        for (Bullet b : balasJugador) {
            b.draw(batch);
        }

        // Dibujar nave
        nave.draw(batch);

        // Dibujar enemigos y verificar colisión con la nave
        for (int i = 0; i < enemigosLista.size(); i++) {
        	Enemigo enemigo = enemigosLista.get(i);
        	if (nave.checkCollision(enemigo) || enemigo.estaDestruido()) {
                enemigosLista.remove(i);
                i--;
            } else {
            	enemigo.draw(batch);
            }
            
           
            
        }
        
        // Verificar si la nave fue destruida
        if (nave.estaDestruido()) {
            
            dispose();
        }
        
        dibujarHUD();
        batch.end();

        // Verificar si el nivel está completo
        if (enemigosLista.isEmpty()) {
            
            GameManager.getInstance().updateRonda();
            crearRonda();
        }
    }

    
    
    public boolean agregarBalaJugador(Bullet bb) {
        return balasJugador.add(bb);
    }
    public boolean agregarBalaEnemigo(Bullet bb) {
        return balasEnemigo.add(bb);
    }
    
    public SpriteBatch getBatch() {
    	return batch;
    }

    @Override
    public void show() {
        gameMusic.play();
    }

    @Override
    public void resize(int width, int height) {}

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {
    	
        explosionSound.dispose();
        gameMusic.dispose();
        bgTexture.dispose();
        if (GameManager.getInstance().getScore() > GameManager.getInstance().getHighScore()) GameManager.getInstance().setHighScore(GameManager.getInstance().getScore());
        GameManager.getInstance().setRonda(1);
        GameManager.getInstance().setScore(0);
        game.setScreen(new PantallaGameOver(game));
    }
   
}
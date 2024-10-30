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



public class PantallaJuego implements Screen {
    private SpaceNavigation game;
    private OrthographicCamera camera;
    private int alto;
    private int ancho;
    private SpriteBatch batch;
    private Sound explosionSound;
    private Music gameMusic;
    private Texture bgTexture;
    private Texture heartTexture;
    private int currentScore = 0;
    private int ronda;
    private int velXEnemigos;
    private int velYEnemigos;
    private Nave4 nave;
  
    private List<Enemigo> enemigosLista = new ArrayList<>();
    private List<Bullet> balasJugador = new ArrayList<>();
    private List<Bullet> balasEnemigo = new ArrayList<>();

    
    public PantallaJuego(SpaceNavigation game, int alto, int ancho) {
        this.game = game;
        this.ronda = GameManager.getInstance().getLevel();
        
        batch = game.getBatch();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, alto, ancho);
        bgTexture = new Texture(Gdx.files.internal("wallpaper.jpeg"));
        heartTexture = new Texture(Gdx.files.internal("cora.png"));
        
        // Inicializar música y sonidos
        explosionSound = Gdx.audio.newSound(Gdx.files.internal("enemy_1_dead.mp3"));
        explosionSound.setVolume(1, 0.5f);
        gameMusic = Gdx.audio.newMusic(Gdx.files.internal("music_theme.mp3"));
        gameMusic.setLooping(true);
        gameMusic.setVolume(0.5f);
        gameMusic.play();

        // Cargar nave
        nave = new Nave4(Gdx.graphics.getWidth() / 2 - 50, 30, 
                new Texture(Gdx.files.internal("spaceship1.png")),
                Gdx.audio.newSound(Gdx.files.internal("naveHurt.mp3")),
                new Texture(Gdx.files.internal("bullet.png")),
                Gdx.audio.newSound(Gdx.files.internal("shoot_theme.mp3")),
                this, 3); // Pasa PantallaJuego
  

        dibujarEnemigos();

        
    }
    

    
    public void dibujarEnemigos() {
    	//Fabricar enemigo simple
    	
    	FabricaEnemigo enemySimpleFactory = new FabricaEnemigoSimple();
    	EnemigoSimple enemySimple = (EnemigoSimple) enemySimpleFactory.crearEnemigo();
    			
    	enemySimple.helloEnemy();
    	
        enemigosLista.clear(); // Limpiar lista antes de generar nuevos enemigos

        // Configuración de filas
        int filas = 3;               // Número de filas de enemigos
        int enemigosPorFila = 4;     // Número de enemigos en cada fila
        int espacioHorizontal = 200; // Espacio entre enemigos en la misma fila
        int espacioVertical = 100;   // Espacio entre filas

        // Coordenadas iniciales
        int yInicial = 400;          // Altura inicial de la primera fila
        int xInicial = ancho;          // Posición inicial en X

        // Generación de filas de enemigos
        for (int fila = 0; fila < filas; fila++) {
            int enemyX = xInicial;
            int enemyY = yInicial + (fila * espacioVertical); // Incremento en Y por cada fila
            
            for (int i = 0; i < enemigosPorFila; i++) {
                // Elegir tipo de enemigo según la fila
                int tipo = (fila == 0) ? 1 : 0; // Tipo de enemigo (1 para fila 0, 0 para las demás)
                int nivel = (fila == 0) ? 2 : 1; // Nivel del enemigo
                
                if (tipo != 1) {
                	// Crear enemigo y agregarlo a la lista
                    Enemigo enemigo = new Enemigo(enemyX, enemyY, 20, 
                            velXEnemigos, 
                            velYEnemigos, 
                            new Texture(Gdx.files.internal("ene1.png")),this, 
                            			MathUtils.random(0.1f, 0.5f), nivel);
                    enemigosLista.add(enemigo);
                } else {
                	// Crear enemigo y agregarlo a la lista
                    Enemigo enemigo = new Enemigo(enemyX, enemyY, 20, 
                            velXEnemigos, 
                            velYEnemigos, 
                            new Texture(Gdx.files.internal("ene2.png")),this, 
                            			MathUtils.random(0.1f, 0.6f), nivel);
                    enemigosLista.add(enemigo);
                }
                
                
                
                // Incrementar posición X para el siguiente enemigo en la fila
                enemyX += espacioHorizontal;
            }
        }
    }
    
    
    public void dibujarHUD() {
        CharSequence str = "Vidas: ";
        int vidas = nave.getVidas();
        for (int i = 0; i < 3; i++) {
            if (i < vidas) {
                // Dibuja un corazón lleno si la nave tiene vida
                batch.draw(heartTexture, 100 + i * 60, 10); 
            }
        }
     // Dibuja la ronda
        CharSequence rondaStr = "Ronda: " + ronda;
        game.getFont().getData().setScale(2f);
        game.getFont().draw(batch, rondaStr, 300, 30); 
        
        
        game.getFont().getData().setScale(2f);
        game.getFont().draw(batch, str, 10, 30);
        batch.setColor(1, 0, 0, 1); // RGB para el bg
        game.getFont().draw(batch, "Score:" + GameManager.getInstance().getScore(), Gdx.graphics.getWidth() - 150, 30);
        game.getFont().draw(batch, "HighScore:" + GameManager.getInstance().getHighScore(), Gdx.graphics.getWidth() / 2 - 80, 30);
    }
    
   


    @Override
    public void render(float delta) {
    	Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        batch.begin();
        batch.draw(bgTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        
        dibujarHUD();

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
            if (GameManager.getInstance().getScore() > GameManager.getInstance().getHighScore()) GameManager.getInstance().setHighScore(currentScore);
            game.setScreen(new PantallaGameOver(game));
            GameManager.getInstance().setScore(0);
            dispose();
        }

        batch.end();

        // Verificar si el nivel está completo
        if (enemigosLista.isEmpty()) {
            game.setScreen(new PantallaJuego(game, 1280,720));
            dispose();
        }
    }

    
    
    public boolean agregarBalaJugador(Bullet bb) {
        return balasJugador.add(bb);
    }
    public boolean agregarBalaEnemigo(Bullet bb) {
        return balasEnemigo.add(bb);
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
    }
   
}
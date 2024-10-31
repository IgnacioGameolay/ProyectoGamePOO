package puppy.code;

import java.util.List;
import puppy.code.rondas.*;

public class GameManager {
    // La única instancia del GameManager
    private static GameManager instance;
    private int score;
    private int highScore;
    private int ronda;
    private PantallaJuego juego;
    private InterfaceStrategiaRonda rondaStrategy;
    
    // Constructor privado para evitar instanciación externa
    private GameManager() {
    	score = 0;
    	highScore = 0;
    	ronda = 1;
    }

    // Método para obtener la instancia del Singleton
    public static GameManager getInstance() {
        if (instance == null) {
            instance = new GameManager();
        }
        return instance;
    }
    
    public void crearRonda(List<Enemigo> enemigosLista) {
		enemigosLista.clear(); // Limpiar lista antes de generar nuevos enemigos

    	if (ronda <= 3) {
    		rondaStrategy = new RondaNormal();
    	} else if (ronda == 4) {
    		rondaStrategy = new RondaMiniBoss();
    	} else {
    		rondaStrategy = new RondaFinalBoss();
    	}
    	
    	rondaStrategy.configurarRonda(juego, enemigosLista);
    }
    

    public void updateScore(int points) {
    	score += points;
    }
    
    public void updateRonda() {
    	ronda++;
    }
    
    public int getScore() {
    	return score;
    }
    
    public int getHighScore() {
    	return highScore;
    }
    
    public int getRonda() {
    	return ronda;
    }
    
    public void setScore(int s) {
    	score = s;
    }
    
    public void setHighScore(int hs) {
    	highScore = hs;
    }
    
    public void setRonda(int l) {
    	ronda = l;
    }
    
    public PantallaJuego getJuego() {
    	return juego;
    }
    
    public void setJuego(PantallaJuego j) {
    	juego = j;
    }
}


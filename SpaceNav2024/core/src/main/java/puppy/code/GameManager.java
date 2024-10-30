package puppy.code;

public class GameManager {
    // La única instancia del GameManager
    private static GameManager instance;
    private int score;
    private int highScore;
    private int level;
    private PantallaJuego juego;
    // Constructor privado para evitar instanciación externa
    private GameManager() {
    	score = 0;
    	highScore = 0;
    	level = 1;
    }

    // Método para obtener la instancia del Singleton
    public static GameManager getInstance() {
        if (instance == null) {
            instance = new GameManager();
        }
        return instance;
    }
    

    public void updateScore(int points) {
    	score += points;
    }
    
    public void updateLevel() {
    	level++;
    }
    
    public int getScore() {
    	return score;
    }
    
    public int getHighScore() {
    	return highScore;
    }
    
    public int getLevel() {
    	return level;
    }
    
    public void setScore(int s) {
    	score = s;
    }
    
    public void setHighScore(int hs) {
    	highScore = hs;
    }
    
    public void setLevel(int l) {
    	level = l;
    }
    
    public PantallaJuego getJuego() {
    	return juego;
    }
    
    public void setJuego(PantallaJuego j) {
    	juego = j;
    }
}


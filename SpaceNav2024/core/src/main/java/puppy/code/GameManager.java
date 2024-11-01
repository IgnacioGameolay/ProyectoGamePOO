package puppy.code;

import java.util.List;
import puppy.code.rondas.*;

/**
 * Clase que gestiona el estado del juego y las rondas.
 * Implementa el patrón Singleton para asegurar que solo haya una instancia de GameManager.
 */
public class GameManager {
    // La única instancia del GameManager
    private static GameManager instance;
    private int score; // Puntuación actual del jugador
    private int highScore; // Puntuación más alta alcanzada
    private int ronda; // Número de la ronda actual
    private PantallaJuego juego; // Referencia a la pantalla de juego
    private InterfaceStrategiaRonda rondaStrategy; // Estrategia de ronda actual

    // Constructor privado para evitar instanciación externa
    private GameManager() {
        score = 0;
        highScore = 0;
        ronda = 1;
    }

    /**
     * Método para obtener la instancia del Singleton.
     * 
     * @return La instancia única de GameManager
     */
    public static GameManager getInstance() {
        if (instance == null) {
            instance = new GameManager();
        }
        return instance;
    }

    /**
     * Crea una nueva ronda y configura los enemigos de acuerdo con la ronda actual.
     * 
     * @param enemigosLista Lista donde se almacenan los enemigos de la ronda
     */
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

    /**
     * Actualiza la puntuación del jugador.
     * 
     * @param points Puntos a añadir a la puntuación actual
     */
    public void updateScore(int points) {
        score += points;
    }

    /**
     * Avanza a la siguiente ronda.
     */
    public void updateRonda() {
        ronda++;
    }

    /**
     * Obtiene la puntuación actual.
     * 
     * @return La puntuación actual del jugador
     */
    public int getScore() {
        return score;
    }

    /**
     * Obtiene la puntuación más alta.
     * 
     * @return La puntuación más alta alcanzada
     */
    public int getHighScore() {
        return highScore;
    }

    /**
     * Obtiene el número de la ronda actual.
     * 
     * @return El número de la ronda actual
     */
    public int getRonda() {
        return ronda;
    }

    /**
     * Establece la puntuación del jugador.
     * 
     * @param s Nueva puntuación
     */
    public void setScore(int s) {
        score = s;
    }

    /**
     * Establece la puntuación más alta alcanzada.
     * 
     * @param hs Nueva puntuación más alta
     */
    public void setHighScore(int hs) {
        highScore = hs;
    }

    /**
     * Establece el número de la ronda actual.
     * 
     * @param l Nuevo número de ronda
     */
    public void setRonda(int l) {
        ronda = l;
    }

    /**
     * Obtiene la referencia a la pantalla de juego.
     * 
     * @return La pantalla de juego actual
     */
    public PantallaJuego getJuego() {
        return juego;
    }

    /**
     * Establece la referencia a la pantalla de juego.
     * 
     * @param j Nueva pantalla de juego
     */
    public void setJuego(PantallaJuego j) {
        juego = j;
    }
}

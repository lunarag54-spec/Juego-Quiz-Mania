package config;

/**
 * Clase que contiene las constantes utilizadas en toda la aplicación.
 * Centraliza las rutas de archivos y los límites de valores.
 *
 *  @author Sergio García Rodríguez
 */
public class Constantes {

    public static final String CIUDADES_PATH = "src/datos/ficheros/ciudades.csv";
    public static final String RANKING_PATH = "src/datos/ficheros/ranking.txt";
    public static final String HISTORICO_PATH = "src/datos/ficheros/historico.txt";
    public static final String JUGADORES_PATH = "src/datos/ficheros/jugadores.txt";
    public static final String LOG_PATH = "src/datos/ficheros/logs/salida.log";
    public static final String PROPERTIES_PATH = "src/config/configuracion.properties";
    public static final int TAM_MIN_NOMBRE_JUGADOR = 0;
    public static final int TAM_MAX_NOMBRE_JUGADOR = 9;
    public static final int NUM_MIN_JUGADORES = 2;
    public static final int NUM_MAX_JUGADORES = 4;
    public static final int NUM_MIN_RONDAS = 1;
    public static final int NUM_MAX_RONDAS = 10;
    public static final int TAM_MAX_RESPUESTA = 100;
    public static final int PUNTOS_x_RESPUESTA = 1;
    public static final int TOTAL_ITERACIONES_MASTERMIND = 3;
    public static final int MIN_SEGUNDOS_CRONOMETRO = 1;
    public static final int MAX_SEGUNDOS_CRONOMETRO = 5;
}
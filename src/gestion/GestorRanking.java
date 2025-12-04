package gestion;

import java.io.File;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.FileNotFoundException;

import config.LectorProperties;
import datos.RankingDiccionario;

/**
 * Clase que gestiona la lectura y escritura del archivo de ranking de jugadores.
 * Permite leer el ranking, actualizarlo y mostrarlo por consola.
 *
 *  @author Sergio García Rodríguez
 */
public class GestorRanking {

    /**
     * Lee el contenido del archivo de ranking.
     * @return El contenido del ranking en forma de cadena de texto.
     */
    private static String leer() {
        String txt = "";
        try {
            BufferedReader lector = new BufferedReader(new FileReader(config.Constantes.RANKING_PATH));
            try {
                String linea;
                while ((linea = lector.readLine()) != null) {
                    txt = txt.concat(linea).concat("\n");
                }
                lector.close();
            } catch (IOException e) {
                GestorLog.escribirMensaje(e.getMessage());
            }
        } catch (FileNotFoundException e) {
            // Si el fichero no existe, se crea uno nuevo
            GestorLog.escribirMensaje("No se encuentra el fichero " + config.Constantes.RANKING_PATH);
            File f = new File(config.Constantes.RANKING_PATH);
            try {
                f.createNewFile();
            } catch (IOException ex) {
                GestorLog.escribirMensaje(ex.getMessage());
            }
        }
        return txt;
    }

    /**
     * Devuelve el contenido actual del ranking.
     * @return El contenido del ranking en forma de cadena de texto.
     */
    public static String leerFicheroRanking() {
        String rank = "El ranking de los " + LectorProperties.getTamRanking() + " jugadores con más puntos es:\n";
        return rank + leer();
    }

    /**
     * Escribe un nuevo ranking en el archivo de ranking.
     * @param rkOrdenado Texto a procesar y guardar en el ranking.
     */
    private static void escribirFicheroRanking(String rkOrdenado) {
        try {
            BufferedWriter escritor = new BufferedWriter(new FileWriter(config.Constantes.RANKING_PATH));
            escritor.write(rkOrdenado);
            escritor.close();
        } catch (IOException e) {
            GestorLog.escribirError(e.getMessage());
        }
    }

    /**
     * Se recalcula el ranking teniendo en cuenta el resultado de la partida actual y el ranking anterior a la partida).
     */
    public static void calcularRanking(){
        // Se crea un RankingDiccionario con el listado de jugadores actaules (y sus puntuaciones), y el contenido del
        // fichero de ranking.
        RankingDiccionario rd = new RankingDiccionario(GestorPartidas.getGestorJugadores().getListaJugadores(),
                GestorRanking.leer());
        escribirFicheroRanking(rd.getRankingOrdenado());
    }

    /**
     * Elimina un jugador del ranking (porque ese jugador se ha eliminado de la partida).
     * @param nombre Nombre del jugador a ser eliminado del ranking.
     */
    public static void eliminarJugadorRanking(String nombre){
        // Se crea un RankingDiccionario con el contenido actual del fichero de ranking.
        RankingDiccionario rd = new RankingDiccionario(GestorRanking.leer());
        rd.eliminarJugador(nombre);
        // Se actualiza el fichero de ranking.
        escribirFicheroRanking(rd.getRankingOrdenado());
    }
}
package gestion;

import java.io.*;

import config.Constantes;

public class GestorJugadoresFichero {
    /**
     * Lee el contenido del archivo de Jugadores.
     * @return El contenido del fichero en forma de cadena de texto.
     */
    private static String leerFichero() {
        String txt = "";
        try {
            BufferedReader lector = new BufferedReader(new FileReader(Constantes.JUGADORES_PATH));
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
            GestorLog.escribirMensaje("No se encuentra el fichero " + config.Constantes.JUGADORES_PATH);
            File f = new File(config.Constantes.JUGADORES_PATH);
            try {
                f.createNewFile();
            } catch (IOException ex) {
                GestorLog.escribirMensaje(ex.getMessage());
            }
        }
        return txt;
    }

    /**
     * Escribe la lista de jugadores en el archivo de jugadores.
     * @param listaJugadores Texto a guardar en el fichero.
     */
    public static void escribirFicheroJugadores(String listaJugadores) {
        try {
            BufferedWriter escritor = new BufferedWriter(new FileWriter(Constantes.JUGADORES_PATH));
            escritor.write(listaJugadores);
            escritor.close();
        } catch (IOException e) {
            GestorLog.escribirError(e.getMessage());
        }
    }

    /**
     * Inicializa la lista de jugadores en GestorPartidas.GestorJugadores, leyéndolos desde el fichero de jugadores.
     * @throws JuegoException Error al crear un jugador
     */
    public static void cargarListaJugadores() throws JuegoException {
        String jugs = leerFichero();
        String[] trozos = jugs.split("\\s+");
        if (trozos.length > 1) {
            GestorJugadores gJ = GestorPartidas.crearGestorJugadores(trozos.length / 2);
            for (int i = 0; i < trozos.length; i+=2) {
                gJ.crearJugador(trozos[i], Integer.parseInt(trozos[i + 1]));
            }
        }
    }
}

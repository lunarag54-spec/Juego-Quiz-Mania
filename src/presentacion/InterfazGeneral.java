package presentacion;

import java.util.Scanner;

import config.LectorProperties;
import gestion.*;

/**
 * Clase que gestiona la interfaz de usuario para interactuar con el juego.
 *
 *  @author Sergio García Rodríguez
 */
public class InterfazGeneral {

    private static final Scanner sc = new Scanner(System.in);

    /**
     * Muestra el menú principal.
     */
    public static void iniciarMenuGeneral() {
        int opcion;
        GestorLog.escribirMensaje("Arranco la aplicación");
        LectorProperties.cargarProperties();
        try {
            GestorJugadoresFichero.cargarListaJugadores();
        } catch (JuegoException e) {
            // En caso de error con el fichero de jugadores o su contenido devolvemos la Exception a consola
            throw new RuntimeException(e);
        }
        do {
            pintarMenu();
            opcion = Utilidades.preguntarInt("", 1, 5);
            switch (opcion) {
                case 1 -> opcionJugar();
                case 2 -> opcionRanking();
                case 3 -> opcionHistorico();
                case 4 -> opcionJugadores();
                case 5 -> opcionSalir();
            }
        } while (opcion != 5);
    }

    /**
     * Muestra el menú general.
     */
    private static void pintarMenu() {
        Utilidades.escribirMensaje("\n========= MENÚ DEL JUEGO =========");
        Utilidades.escribirMensaje("1. Jugar                         |");
        Utilidades.escribirMensaje("2. Ranking                       |");
        Utilidades.escribirMensaje("3. Histórico                     |");
        Utilidades.escribirMensaje("4. Jugadores                     |");
        Utilidades.escribirMensaje("5. Salir                         |");
        Utilidades.escribirMensaje("==================================");
    }

    /**
     * Lógica de la opción de iniciar partida.
     */
    public static void opcionJugar() {
        GestorLog.escribirMensaje("Comienzo partida");
        InterfazPartida.iniciarPartida();
    }

    /**
     * Lógica de la opción de mostrar ranking.
     */
    public static void opcionRanking() {
        GestorLog.escribirMensaje("Muestra Ranking");
        Utilidades.escribirMensaje(GestorRanking.leerFicheroRanking());
    }

    /**
     * Lógica de la opción de mostrar el histórico.
     */
    public static void opcionHistorico() {
        GestorLog.escribirMensaje("Muestra histórico");
        Utilidades.escribirMensaje(GestorHistorico.leer());
    }

    /**
     * Lógica de la opción de gestionar jugadores.
     */
    public static void opcionJugadores() {
        GestorLog.escribirMensaje("Abro menú de jugadores");
        new InterfazJugadores().iniciarMenuJugadores();
    }

    /**
     * Lógica de la opción salir del sistema.
     */
    public static void opcionSalir() {
        if(GestorPartidas.getGestorJugadores() == null || GestorPartidas.getGestorJugadores().getTotalJugadores() == 0){
            GestorJugadoresFichero.escribirFicheroJugadores("");
        }else {
            GestorJugadoresFichero.escribirFicheroJugadores(GestorPartidas.getGestorJugadores().getListaJugadores());
        }
        GestorLog.escribirMensaje("Grabo lista de Jugadores");
        Utilidades.escribirMensaje("Has salido del sistema (>‿◠)✌\n");
        GestorLog.escribirMensaje("Paro la aplicación");
        GestorLog.cerrar();
    }

    /**
     * Devuelve Scanner para leer desde consola.
     * @return Scanner.
     */
    public static Scanner getSc() {
        return sc;
    }
}

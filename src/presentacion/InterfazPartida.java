package presentacion;

import config.Constantes;
import gestion.GestorPartidas;
import gestion.GestorLog;

/**
 * Clase que representa la interfaz de interacción para iniciar una partida.
 * Se encarga de validar la existencia de jugadores, preguntar por el número de rondas
 * y gestionar el flujo de inicio y finalización de la partida.
 *
 * @author Sergio García Rodríguez
 */
public class InterfazPartida {

    /**
     * Verifica si existen jugadores dados de alta, solicita el número de rondas
     * y gestiona la ejecución de la partida mostrando el resultado al final.
     */
    public static void iniciarPartida() {
        // Comprueba si hay jugadores registrados antes de comenzar
        if (GestorPartidas.getGestorJugadores() == null) {
            Utilidades.escribirMensaje("¡Debes dar de alta los jugadores en la opción 4!");
            return;
        }
        // Comprueba si hay el número mínimo de jugadores registrados antes de comenzar
        if (GestorPartidas.getGestorJugadores().getTotalJugadores() < Constantes.NUM_MIN_JUGADORES) {
            Utilidades.escribirMensaje("¡Necesitas un mínimo de " + Constantes.NUM_MIN_JUGADORES + " jugadores!");
            return;
        }

        // Registro de información sobre los jugadores
        GestorLog.escribirMensaje("Comienza la partida con " + GestorPartidas.getGestorJugadores().getTotalJugadores()
                + " jugadores: "
                + GestorPartidas.getGestorJugadores().getTotalJugadoresHumanos() + " jugadores humanos, "
                + GestorPartidas.getGestorJugadores().getTotalJugadoresCPU() + " jugadores de CPU");
        Utilidades.escribirMensaje("Somos " + GestorPartidas.getGestorJugadores().getTotalJugadores() + " jugadores.");
        int rondas = Utilidades.preguntarInt("¿Cuántas rondas quieres jugar?",
                Constantes.NUM_MIN_RONDAS,Constantes.NUM_MAX_RONDAS
        );

        GestorLog.escribirMensaje("Jugamos a " + rondas + " rondas");
        Utilidades.escribirMensaje("\n========> Comienza la partida");

        // Inicia la partida
        GestorPartidas.iniciarPartida(rondas);

        // Muestra el ganador o empate
        if (GestorPartidas.isEmpate()) {
            Utilidades.escribirMensaje("========> Fin de partida con " + GestorPartidas.getGestorJugadores().getTotalJugadores()
                    + " jugadores. Ha habido empate: " + GestorPartidas.getNombreGanador()
                    + " Consulte el histórico para más información");
        } else {
            Utilidades.escribirMensaje("========> Fin de partida con " + GestorPartidas.getGestorJugadores().getTotalJugadores()
                    + " jugadores. Ganador ha sido " + GestorPartidas.getNombreGanador());
        }
        // Muestro las puntuaciones acumuladas teniendo en cuenta el registro de jugadores en fichero
        Utilidades.escribirMensaje("\n** Resultado acumulado del Juego **");
        Utilidades.escribirMensaje(GestorPartidas.getGestorJugadores().getListaJugadores());
    }
}

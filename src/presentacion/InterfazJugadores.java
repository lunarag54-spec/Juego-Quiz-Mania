package presentacion;

import config.Constantes;
import datos.jugadores.Jugador;
import gestion.GestorLog;
import gestion.GestorPartidas;
import gestion.JuegoException;

/**
 * Clase que gestiona la interfaz de los jugadores del juego.
 *
 * @author Sergio García Rodríguez
 */
public class InterfazJugadores {
    /**
     * Muestra por consola el menú de opciones para los jugadores.
     */
    private void pintarMenu() {
        Utilidades.escribirMensaje("\n========= MENÚ DE JUGADORES =========");
        Utilidades.escribirMensaje("1.- Lista de jugadores registrados  |");
        Utilidades.escribirMensaje("2.- Crear lista jugadores           |");
        Utilidades.escribirMensaje("3.- Añadir jugador                  |");
        Utilidades.escribirMensaje("4.- Eliminar jugador                |");
        Utilidades.escribirMensaje("5.- Salir                           |");
        Utilidades.escribirMensaje("=====================================");
    }

    /**
     * Inicia el menú interactivo para gestionar los jugadores.
     */
    public void iniciarMenuJugadores() {
        int opcion;
        do {
            pintarMenu();
            opcion = Utilidades.preguntarInt("", 1, 5);
            switch (opcion) {
                case 1 -> opcionListaJugadores();
                case 2 -> opcionCrearListaJugadores();
                case 3 -> opcionAddJugador();
                case 4 -> opcionEliminarJugador();
                case 5 -> opcionSalirMenuJugadores();
            }
        } while (opcion != 5);
    }

    /**
     * Muestra la lista actual de jugadores registrados.
     */
    private void opcionListaJugadores() {
        GestorLog.escribirMensaje("Muestro lista de jugadores");
        Utilidades.escribirMensaje("Lista de jugadores registrados:");
        if (GestorPartidas.getGestorJugadores() != null){
            Utilidades.escribirMensaje(GestorPartidas.getGestorJugadores().getListaJugadores());
        } else {
            Utilidades.escribirMensaje("Lista vacía!!!!!");
        }
    }

    /**
     * Permite crear varios jugadores según el número especificado por el usuario.
     * Borra la lista de jugadores anterior.
     */
    private void opcionCrearListaJugadores() {
        GestorLog.escribirMensaje("Menu crear lista jugadores");
        int tamJugadores = Utilidades.preguntarInt(
                "¿Cuántos jugadores sois?", Constantes.NUM_MIN_JUGADORES, Constantes.NUM_MAX_JUGADORES
        );
        GestorPartidas.crearGestorJugadores(tamJugadores);

        for (int i = 0; i < tamJugadores; i++) {
            // Bucle hasta que se cree cada jugador de manera OK
            while (!crearJugador(i));
        }
    }

    /**
     * Permite añadir un jugador nuevo.
     */
    private void opcionAddJugador() {
        GestorLog.escribirMensaje("Menu añadir jugador");
        if (GestorPartidas.getGestorJugadores() == null){
            GestorPartidas.crearGestorJugadores(1);
        }else if(GestorPartidas.getGestorJugadores().getTotalJugadores() == Constantes.NUM_MAX_JUGADORES) {
            Utilidades.escribirMensaje("No se puede superar el número de " + Constantes.NUM_MAX_JUGADORES + " jugadores.");
            return;
        }else{
            GestorPartidas.getGestorJugadores().ampliarListaJugadores();
        }
        // Bucle hasta que se cree cada jugador de manera OK
        while (!crearJugador(GestorPartidas.getGestorJugadores().getTotalJugadores()-1));
    }

    /**
     * Crea un jugador en la posición dada.
     * @param pos Posición del jugador en la lista
     * @return true si el jugador fue creado con éxito, false si hubo error
     */
    private boolean crearJugador(int pos) {
        String nombre = "";
        int tipoJugador = Utilidades.preguntarInt(
                "¿Qué tipo es el jugador " + (pos + 1) + "? (1: Humano o 2: CPU)", 1,2
        );
        if (tipoJugador == 1) {
            nombre = Utilidades.preguntarNombre(
                    "¿Nombre del jugador " + (pos + 1) + "?", Constantes.TAM_MIN_NOMBRE_JUGADOR,
                    Constantes.TAM_MAX_NOMBRE_JUGADOR
            );
        }
        try {
            Jugador j = GestorPartidas.getGestorJugadores().crearJugador(nombre, 0, tipoJugador);
            Utilidades.escribirMensaje(j);
            return true;
        } catch (JuegoException e) {
            // Error al crear jugador, nombre repetido
            GestorLog.escribirError(e.getMessage());
            Utilidades.escribirMensaje(e.getMessage());
            return false;
        }
    }

    /**
     * Permite eliminar un jugador introduciendo su nombre.
     */
    private void opcionEliminarJugador() {
        GestorLog.escribirMensaje("Menu borrar jugador");
        if (GestorPartidas.getGestorJugadores() != null) {
            boolean res = GestorPartidas.getGestorJugadores().eliminarJugador(
                    Utilidades.preguntarNombre(
                            "Introduce el nombre del jugador a eliminar", Constantes.NUM_MIN_JUGADORES,
                            Constantes.TAM_MAX_NOMBRE_JUGADOR
                    )
            );
            if (res)
                Utilidades.escribirMensaje("Jugador eliminado ;)");
            else
                Utilidades.escribirMensaje("Jugador NO encontrado");
        } else {
            Utilidades.escribirMensaje("La lista de jugadores está vacía!!!!!");
        }
    }

    /**
     * Sale del menú de jugadores y registra el evento.
     */
    private void opcionSalirMenuJugadores() {
        GestorLog.escribirMensaje("Salgo del menú jugadores");
        Utilidades.escribirMensaje("De vuelta al menu principal :)");
    }
}
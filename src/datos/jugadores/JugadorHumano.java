package datos.jugadores;

/**
 * Clase jugador humano.
 * Hereda de la clase Jugador.
 * Si el nombre proporcionado está vacío, se le asigna un nombre por defecto basado en su posición.
 *
 *  @author Sergio García Rodríguez
 */
public class JugadorHumano extends Jugador {

    /**
     * Crea un nuevo JugadorHumano con un nombre, posición y puntos iniciales.
     * @param nombre Nombre del jugador.
     * @param puntos Puntos iniciales del jugador.
     * @param posJugador Posición del jugador.
     */
    public JugadorHumano(String nombre, int puntos, int posJugador) {
        super(nombre, puntos, true);
        if (nombre.isEmpty()) {
            super.setNombre("Jugador" + posJugador);
        }
    }
}

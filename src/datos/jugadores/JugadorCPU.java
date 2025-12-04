package datos.jugadores;

/**
 * Clase jugador de tipo CPU.
 * El nombre se genera automáticamente como "CPU" seguido de su posición.
 * Hereda de la clase Jugador.
 *
 *  @author Sergio García Rodríguez
 */
public class JugadorCPU extends Jugador {

    /**
     * Constructor que crea un JugadorCPU con un número de posición específico y puntos iniciales.
     * @param puntos Puntos iniciales del jugador.
     * @param posJugador Número de identificación de la CPU.
     */
    public JugadorCPU(int puntos, int posJugador) {
        super("CPU" + posJugador, puntos, false);
    }

    /**
     * Constructor que crea un JugadorCPU con un número de posición específico y puntos iniciales.
     * @param nombre Nombre del Jugador
     * @param puntos Puntos iniciales del jugador.
     */
    public JugadorCPU(String nombre, int puntos) {
        super(nombre, puntos, false);
    }
}

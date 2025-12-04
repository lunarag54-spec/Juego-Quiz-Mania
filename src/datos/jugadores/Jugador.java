package datos.jugadores;

import config.Constantes;
import presentacion.Utilidades;

/**
 * Clase abstracta (porque NO puede existir un Jugador, tienen que ser JugadorHumano o JugadorCPU).
 *
 * @author Sergio García Rodríguez
 */
public abstract class Jugador {

    private String nombre;
    private int puntos;
    private final boolean humano;
    private int puntosRonda;
    private int puntosPartida;

    /**
     * Constructor de la clase Jugador.
     * @param nombre Nombre del jugador.
     * @param puntos Puntos iniciales del jugador.
     * @param humano true si el jugador es humano, false si es CPU.
     */
    public Jugador(String nombre, int puntos, boolean humano ) {
        this.setNombre(nombre);
        this.puntos = puntos;
        this.humano = humano;
    }

    /**
     * Devuelve una representación textual del jugador, incluyendo
     * su nombre (rellenado con espacios para alineación) y sus puntos.
     * @return Cadena con nombre y puntos del jugador.
     */
    @Override
    public String toString() {
        return Utilidades.rellenarConBlancos(getNombre(), Constantes.TAM_MAX_NOMBRE_JUGADOR) + "\t" + getPuntos();
    }

    /**
     * Devuelve el nombre del jugador.
     * @return Nombre del jugador.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Graba el nombre del jugador.
     * @param nombre Nombre del jugador.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Devuelve los puntos del jugador.
     * @return Puntos del jugador.
     */
    public int getPuntos() {
        return puntos;
    }

    /**
     * Añade puntos al jugador.
     * @param puntos Puntos a añadir al jugador.
     */
    public void addPuntos(int puntos) {
        this.puntos += puntos;
    }

    /**
     * Indica si el jugador es humano.
     * @return true si es humano, false si es CPU.
     */
    public boolean isHumano() {
        return humano;
    }

    /**
     * Devuelve los puntos acumulados en la ronda actual.
     * @return los puntos de la ronda actual.
     */
    public int getPuntosRonda() {
        return puntosRonda;
    }

    /**
     * Devuelve los puntos acumulados en toda la partida.
     * @return los puntos totales de la partida.
     */
    public int getPuntosPartida() {
        return puntosPartida;
    }

    /**
     * Añade puntos a la ronda actual y a la partida.
     * @param puntos los puntos a añadir.
     */
    public void addPuntosRonda(int puntos) {
        this.puntosRonda += puntos;
        this.puntosPartida += puntos;
    }

    /**
     * Reinicia los puntos de la ronda actual a cero.
     */
    public void resetPuntosRonda() {
        this.puntosRonda = 0;
    }

    /**
     * Reinicia los puntos acumulados de toda la partida a cero.
     */
    public void resetPuntosPartida() {
        this.puntosPartida = 0;
    }

}

package gestion;

import datos.jugadores.Jugador;
import datos.jugadores.JugadorCPU;
import datos.jugadores.JugadorHumano;
import presentacion.Utilidades;

/**
 * Clase que gestiona la creación y asignación de los jugadores del juego.
 * Permite añadir jugadores humanos y jugadores controlados por la CPU, en posiciones aleatorias dentro
 * del array de jugadores.
 *
 *  @author Sergio García Rodríguez
 */
public class GestorJugadores {
    private Jugador[] jugadores;
    private int totalJugadoresHumanos = 0;
    private int totalJugadoresCPU = 0;

    /**
     * Constructor de la clase GestorJugadores.
     * @param tamJugadores Número total de jugadores que participarán en la partida.
     */
    public GestorJugadores(int tamJugadores) {
        jugadores = new Jugador[tamJugadores];
    }

    /**
     * Crea un nuevo jugador y lo añade al array si hay posición disponible.
     * @param nombre Nombre del jugador (solo se usa si es humano).
     * @param tipoJ Tipo de jugador: 1 = humano, 2 = CPU.
     * @return El objeto jugador creado.
     * @throws JuegoException Si hay errores de validación (nombre repetido).
     */
    public Jugador crearJugador(String nombre, int puntos, int tipoJ) throws JuegoException {
        if (existeJugador(nombre))
            throw new JuegoException("El nombre " + nombre + " ya está registrado");
        if (tipoJ == 1) {
            return addJugadorHumano(nombre, puntos);
        } else {
            return addJugadorCPU(nombre, puntos);
        }
    }

    /**
     * Crea un nuevo jugador. Esta función se utiliza cuando se carga la lista de jugadores desde el registro en fichero
     * @param nombre Nombre del jugador.
     * @return El objeto jugador creado.
     * @throws JuegoException Si hay errores de validación (nombre repetido).
     */
    public Jugador crearJugador(String nombre, int puntos) throws JuegoException {
        if (nombre.contains("CPU")){
            return crearJugador(nombre, puntos, 2);
        }else{
            return crearJugador(nombre, puntos, 1);
        }
    }

    /**
     * Añade un jugador humano en una posición aleatoria libre.
     * @param nombreJ Nombre del jugador humano.
     * @param puntos Puntos iniciales del jugador.
     * @return Objeto JugadorHumano creado.
     * @throws JuegoException Si el nombre empieza por "CPU".
     */
    private JugadorHumano addJugadorHumano(String nombreJ, int puntos) throws JuegoException {
        if (nombreJ.toUpperCase().startsWith("CPU")) {
            throw new JuegoException("El nombre no puede comenzar con 'CPU'");
        }

        int pos = buscarPosicionLibre();
        JugadorHumano jugador = new JugadorHumano(nombreJ, puntos, pos + 1);
        getJugadores()[pos] = jugador;
        totalJugadoresHumanos = getTotalJugadoresHumanos() + 1;
        GestorLog.escribirMensaje("Jugador humano creado: " + getJugadores()[pos]);
        return jugador;
    }

    /**
     * Añade un jugador CPU en una posición aleatoria libre.
     * @param nombreCPU Nombre del jugador CPU.
     * @param puntos Puntos iniciales del jugador.
     * @return Objeto JugadorCPU creado.
     */
    private JugadorCPU addJugadorCPU(String nombreCPU, int puntos) {
        int pos = buscarPosicionLibre();
        JugadorCPU jugador;
        if (nombreCPU.isEmpty()){
            // Al crear un jugador CPU se le da nombre automático, pero podría existir en el registro de
            // jugadores en fichero. Se reasigna un nombre nuevo y se vuelve a comprobar.
            int i = pos;
            while (existeJugador("CPU" + i)){
                i++;
            }
            nombreCPU = "CPU" + i;
        }
        jugador = new JugadorCPU(nombreCPU, puntos);
        getJugadores()[pos] = jugador;
        totalJugadoresCPU = getTotalJugadoresCPU() + 1;
        GestorLog.escribirMensaje("Jugador CPU creado: " + getJugadores()[pos]);
        return jugador;
    }

    /**
     * Añade una casilla adicional al vector de Jugadores.
     */
    public void ampliarListaJugadores(){
        Jugador[] jAux = new Jugador[getJugadores().length + 1];
        for (int i = 0, j = 0; i < getJugadores().length; i++) {
                jAux[j++] = getJugadores()[i];
        }
        jugadores = jAux;
    }
    /**
     * Elimina un jugador del array si existe, y ajusta los contadores.
     * @param nombre Nombre del jugador a eliminar.
     * @return true si el jugador fue eliminado, false si no se encontró.
     */
    public boolean eliminarJugador(String nombre) {
        try {
            if (getJugadores().length == 0) {
                return false;
            }

            Jugador[] jAux = new Jugador[getJugadores().length - 1];
            for (int i = 0, j = 0; i < getJugadores().length; i++) {
                if (!getJugadores()[i].getNombre().equalsIgnoreCase(nombre)) {
                    // Si no es el jugador a eliminar, se añade a un vector auxiliar (más corto que el original).
                    jAux[j++] = getJugadores()[i];
                } else {
                    if (getJugadores()[i].isHumano()) {
                        totalJugadoresHumanos--;
                    } else {
                        totalJugadoresCPU--;
                    }
                }
            }

            // El vector auxiliar pasa a ser el vector de Jugadores general.
            jugadores = jAux;
            GestorRanking.eliminarJugadorRanking(nombre);
            GestorLog.escribirMensaje("Jugador " + nombre + " eliminado");
        } catch (ArrayIndexOutOfBoundsException e) {
            // Puede ocurrir si no se encuentra el jugador al eliminar, porque se intentaría añadir un jugador más
            // de los que caben en el vector Auxiliar, que es una celda más corto que el original).
            GestorLog.escribirMensaje("Jugador " + nombre + " NO eliminado (por no ser encontrado)");
            return false;
        }
        return true;
    }

    /**
     * Devuelve la lista de jugadores actuales en formato de texto.
     * @return Texto con los jugadores o mensaje si está vacía.
     */
    public String getListaJugadores() {
        StringBuilder lista = new StringBuilder();
        for (Jugador j : getJugadores()) {
            if (j != null) {
                lista.append(j).append("\n");
            }
        }
        return (lista.isEmpty()) ? "  --> Lista vacía!!" : lista.toString();
    }

    /**
     * Busca aleatoriamente una posición libre en el array de jugadores.
     * Así los jugadores se organizan de forma aleatoria
     * @return Índice de una posición vacía.
     */
    private int buscarPosicionLibre() {
        int pos;
        do {
            pos = Utilidades.getRandom(getTotalJugadores() - 1);
            if (getJugadores()[pos] == null) {
                return pos;
            }
        } while (true);
    }

    /**
     * Comprueba si ya existe un jugador con el nombre dado (ignorando mayúsculas/minúsculas).
     * @param nombre Nombre a comprobar.
     * @return true si ya existe, false en caso contrario.
     */
    private boolean existeJugador(String nombre) {
        for (Jugador jugador : getJugadores()) {
            if (jugador != null && jugador.getNombre().equalsIgnoreCase(nombre)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Devuelve el número total de jugadores (capacidad máxima).
     * @return Tamaño del array de jugadores.
     */
    public int getTotalJugadores() {
        return getJugadores().length;
    }

    /**
     * Devuelve el número total de jugadores humanos.
     * @return Total de Jugadores humanos.
     */
    public int getTotalJugadoresHumanos() {
        return totalJugadoresHumanos;
    }

    /**
     * Devuelve el número total de jugadores CPU.
     * @return Total de Jugadores CPU.
     */
    public int getTotalJugadoresCPU() {
        return totalJugadoresCPU;
    }

    /**
     * Obtiene el nombre del jugador ganador según la mayor cantidad de puntos.
     * En caso de empate, concatena los nombres de los jugadores que han empatado.
     * @return Nombre del ganador o nombres de jugadores que han empatado.
     */
    public String getGanador() {
        int max = -1;
        StringBuilder ganadorPartida = new StringBuilder();

        for (Jugador jugador : getJugadores()) {
            if (jugador.getPuntosPartida() > max) {
                max = jugador.getPuntosPartida();
                ganadorPartida = new StringBuilder(jugador.getNombre());
            } else if (jugador.getPuntosPartida() == max) {
                ganadorPartida.append(" - ").append(jugador.getNombre());
            }
        }
        return ganadorPartida.toString();
    }

    /**
     * Array que contiene los jugadores de la partida
     */
    public Jugador[] getJugadores() {
        return jugadores;
    }
}

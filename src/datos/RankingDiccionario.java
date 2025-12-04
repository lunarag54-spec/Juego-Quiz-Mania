package datos;

import java.util.*;
import java.util.function.Predicate;

import config.Constantes;
import config.LectorProperties;
import presentacion.Utilidades;

/**
 * Clase que gestiona un ranking de jugadores, combinando información de partida y ranking global.
 * Ofrece ordenación por puntuación y eliminación de jugadores.
 *
 *  @author Sergio García Rodríguez
 */
public class RankingDiccionario {

    private Map<String, Integer> mapRanking;
    private List<Map.Entry<String, Integer>> listaOrdenada;

    /**
     * Constructor que se inicializa el ranking con datos de la partida actual y el ranking global.
     * @param rankingPartida Cadena con jugadores y puntuaciones de la partida actual.
     * @param rankingGlobal  Cadena con jugadores y puntuaciones del ranking global.
     */
    public RankingDiccionario(String rankingPartida, String rankingGlobal) {
        mapRanking = new HashMap<>();
        llenarMapRanking(rankingPartida, rankingGlobal);
        listaOrdenada = ordenarPorPuntuacion();
    }

    /**
     * Constructor que se inicializa solo con el ranking global.
     * @param rankingGlobal Cadena con el ranking global.
     */
    public RankingDiccionario(String rankingGlobal) {
        this("", rankingGlobal);
    }

    /**
     * Llena el mapa de ranking combinando datos del ranking de partida y del ranking global.
     * @param rankingPartida Cadena con puntuaciones de la partida.
     * @param rankingGlobal  Cadena con puntuaciones globales.
     */
    private void llenarMapRanking(String rankingPartida, String rankingGlobal) {
        String[] trozos = rankingGlobal.concat(" ").concat(rankingPartida).split("\\s+");
        for (int i = 0; i < trozos.length; i += 2) {
            mapRanking.put(trozos[i], Integer.valueOf(trozos[i + 1]));
        }
    }

    /**
     * Función auxiliar privado sobrecargado.
     * @param rankingGlobal Ranking global en formato texto.
     */
    private void llenarMapRanking(String rankingGlobal) {
        llenarMapRanking("", rankingGlobal);
    }

    /**
     * Ordena el mapa por puntuación en orden descendente.
     * @return Lista ordenada de entradas (nombre, puntuación).
     */
    private List<Map.Entry<String, Integer>> ordenarPorPuntuacion() {
        List<Map.Entry<String, Integer>> rankingOrdenado = new ArrayList<>(mapRanking.entrySet());
        rankingOrdenado.sort((a, b) -> b.getValue().compareTo(a.getValue())); // mayor a menor
        return rankingOrdenado;
    }

    /**
     * Devuelve una cadena con el ranking ordenado de jugadores.
     * Solo se incluyen los primeros N jugadores, según la constante configurada.
     * @return Texto con el ranking formateado.
     */
    public String getRankingOrdenado() {
        String ranking = "";
        for (int i = 0; i < listaOrdenada.size() && i < LectorProperties.getTamRanking(); i++) {
            String nombre = listaOrdenada.get(i).getKey();

            // Si el nombre es demasiado largo, se recorta
            if (nombre.length() > Constantes.TAM_MAX_NOMBRE_JUGADOR) {
                nombre = nombre.substring(0, Constantes.TAM_MAX_NOMBRE_JUGADOR);
            }

            // Se rellena para mantener el formato tabulado
            nombre = Utilidades.rellenarConBlancos(nombre, Constantes.TAM_MAX_NOMBRE_JUGADOR);

            ranking = ranking.concat(nombre + "\t" + listaOrdenada.get(i).getValue() + "\n");
        }
        return ranking;
    }

    /**
     * Elimina un jugador del ranking por su nombre (ignorando mayúsculas/minúsculas).
     * @param nombre Nombre del jugador a eliminar.
     */
    public void eliminarJugador(String nombre) {
        listaOrdenada.removeIf(new Predicate<Map.Entry<String, Integer>>() {
            public boolean test(Map.Entry<String, Integer> stringIntegerEntry) {
                return stringIntegerEntry.getKey().equalsIgnoreCase(nombre);
            }
        });
    }
}

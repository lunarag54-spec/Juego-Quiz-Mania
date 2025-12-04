package datos.preguntas;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

import config.Constantes;
import presentacion.Utilidades;

/**
 * Clase que representa una pregunta de tipo geografía.
 * Accede a un fichero de tipo csv, selecciona cinco ciudades y calcula la distancia entre
 * una de ellas y las cuatro restantes. Prepara una pregunta de tipo test con estas ciudades para responder
 * la más cercana de ellas a una ciudad dada en la pregunta.
 *
 * @author Sergio García Rodríguez
 */
public class PreguntaGeografia implements Pregunta{

    private char solucion;
    private String txt1;
    private String txt2 = "";
    private String txtSolucion;
    private List<PreguntaGeografia.Ciudad> ciudades = new ArrayList<>();

    /**
     * Constructor.
     */
    public PreguntaGeografia() {
        iniciar();
    }

    /**
     * Genera una nueva pregunta de geografía y su respuesta automáticamente.
     */
    private void iniciar(){
        try {
            ciudades = cargarCiudades();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Ciudad ciudadPreg = ciudades.get(Utilidades.getRandom(0, ciudades.size()-1));
        txt1 = "Selecciona la ciudad más cercana a " + ciudadPreg.nombre;

        List<Ciudad> opciones = new ArrayList<>();
        // Selecciona 4 opciones distintas a la ciudad ciudadPreg
        while (opciones.size() < 4) {
            Ciudad opcion = ciudades.get(Utilidades.getRandom(0, ciudades.size()-1));
            if (!opcion.nombre.equals(ciudadPreg.nombre) && !opciones.contains(opcion)) {
                opciones.add(opcion);
            }
        }

        // Preparar lista de opciones
        for (int i = 0; i < opciones.size(); i++) {
            txt2 += "\n" + (char)((int)('A') + i) + ". " + opciones.get(i).nombre;
        }

        // Calcular distancias desde la ciudad ciudadPreg a cada opción
        txtSolucion = "\nDistancias desde " + ciudadPreg.nombre + ":";
        Map<Ciudad, Double> distancias = new HashMap<>();
        for (Ciudad opcion : opciones) {
            double distancia = haversine(ciudadPreg.latitud, ciudadPreg.longitud, opcion.latitud, opcion.longitud);
            distancias.put(opcion, distancia);
            txtSolucion += "\n - " + opcion.nombre + ": " + ((int)(distancia * 100)) / 100 + " km";
        }

        // Determina que ciudad está más cerca
        Ciudad masCercana = opciones.stream().min(Comparator.comparing(distancias::get)).get();
        solucion = (char)((int)('A') + opciones.indexOf(masCercana));
    }

    /**
     * Devuelve la pregunta matemática generada.
     * @param mostrarSolucion Envío de pregunta con solución.
     * @return Pregunta en formato String.
     */
    public String getTextoPregunta(boolean mostrarSolucion) {
        if (mostrarSolucion){
            return txt1 + " ==> ("+  solucion + ")" + txt2;
        }else {
            return txt1 + txt2;
        }
    }

    /**
     * Devuelve la respuesta correcta a la pregunta.
     * @return Respuesta en formato char.
     */
    public Object getSolucion(){
        return solucion;
    }

    /**
     * Comprueba si la respuesta es correcta según la pregunta.
     * @param respuesta Respuesta del usuario a comparar
     * @return Resultado ¿Mensaje a mostrar al usuario y si es correcta la respuesta?
     */
    public Resultado comprobarRespuesta(Object respuesta){
        boolean acierto = (char)respuesta == solucion;
        if (acierto) {
            return new Resultado("Has acertado!!!!!" + txtSolucion, acierto);
        }else {
            return new Resultado("Has fallado. El resultado es: " + solucion + txtSolucion, acierto);
        }
    }

    /**
     * Devuelve una respuesta aleatoria entre A, B, C, D.
     * @return Una letra aleatoria: 'A', 'B', 'C' o 'D'.
     */
    public char getRespuestaCPU() {
        char[] opciones = {'A', 'B', 'C', 'D'};
        int indice = Utilidades.getRandom(0, opciones.length-1);
        return opciones[indice];
    }

    /**
     * Carga una lista de ciudades desde un fichero CSV.
     * @return Lista de objetos Ciudad.
     * @throws IOException Si ocurre un error al leer el fichero.
     */
    private List<PreguntaGeografia.Ciudad> cargarCiudades() throws IOException {

        BufferedReader br = new BufferedReader(new FileReader(Constantes.CIUDADES_PATH));
        String linea;
        br.readLine(); // Omitir cabecera
        while ((linea = br.readLine()) != null) {
            String[] partes = linea.split(",");
            ciudades.add(new PreguntaGeografia.Ciudad(partes[0], Double.parseDouble(partes[1]),
                    Double.parseDouble(partes[2])));
        }
        br.close();
        return ciudades;
    }

    /**
     * Calcula la distancia entre dos puntos geográficos usando la fórmula de Haversine.
     *
     * @param lat1 Latitud del primer punto.
     * @param lon1 Longitud del primer punto.
     * @param lat2 Latitud del segundo punto.
     * @param lon2 Longitud del segundo punto.
     * @return Distancia en kilómetros.
     */
    static double haversine(double lat1, double lon1, double lat2, double lon2) {
        final int RADIO_TIERRA = 6371; // Radio de la Tierra en kilómetros
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(dLon / 2) * Math.sin(dLon / 2);
        return 2 * RADIO_TIERRA * Math.asin(Math.sqrt(a));
    }

    /**
     * Representa una ciudad con nombre, latitud y longitud.
     * Esta clase es interna a PreguntaGeografia porque no se va a usar fuera de la clase superior.
     */
    private static class Ciudad {
        String nombre;
        double latitud;
        double longitud;

        Ciudad(String nombre, double latitud, double longitud) {
            this.nombre = nombre;
            this.latitud = latitud;
            this.longitud = longitud;
        }

        /**
         * Para uso en: Map<Ciudad, Double> distancias
         * @return int.
         */
        @Override
        public int hashCode() {
            return Objects.hash(nombre);
        }
    }

}

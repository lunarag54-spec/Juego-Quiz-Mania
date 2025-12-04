package datos.preguntas;

import java.time.Duration;
import java.time.Instant;

/**
 * Clase que representa una pregunta de tipo cronómetro.
 * Calcula el tiempo entre dos eventos y comprueba que es igual +-0.5 segs que el tiempo de la prueba.
 *
 * @author Sergio García Rodríguez
 */
public class PreguntaCronometro implements Pregunta{

    private final int totalSegs;
    Instant inicio, fin;

    /**
     * Constructor. Genera una nueva pregunta matemática y su respuesta automáticamente.
     */
    public PreguntaCronometro(int totalSegs){
        this.totalSegs = totalSegs;
    }

    /**
     * Se usa para tomar el tiempo de arranque del test.
     * @param mostrarSolucion No se usa, pero es necesario porque es una función de la interface Pregunta.
     * @return Una cadena con la pregunta.
     */
    public String getTextoPregunta(boolean mostrarSolucion) {
        inicio = Instant.now();
        return "Tic... Tac...";
    }

    /**
     * NO se usa pero se implementa para cumplir con la interface Pregunta.
     * @return Una cadena vacía.
     */
    public String getSolucion(){
        return "";
    }

    /**
     * Se usa para tomar el tiempo de finalización del test.
     * @param respuesta No se usa, pero es necesario porque es una función de la interface Pregunta.
     * @return Un objeto Resultado con el texto final de la prueba y si se utilizó el tiempo indicado
     * en la misma (+-0.5 segs).
     */
    public Resultado comprobarRespuesta(Object respuesta) {
        fin = Instant.now();
        // Calcula la duración entre los dos instantes
        Duration duracion = Duration.between(inicio, fin);
        long segundos = duracion.getSeconds() - totalSegs;

        // Hay que calcular el valor absoluto porque se puede haber tardado más o menos tiempo del marcado en la prueba
        boolean acierto = Math.abs(segundos) <= 0.5;
        if (acierto) {
            return new Resultado("Has acertado!!!!!", acierto);
        }else {
            return new Resultado("Has fallado. La diferencia con el tiempo de la prueba es: " + segundos + " segundos.", acierto);
        }
    }
}

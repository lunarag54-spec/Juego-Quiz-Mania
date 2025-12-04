package datos.preguntas;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

import config.LectorProperties;
import presentacion.Utilidades;

/**
 * Clase que representa una pregunta de tipo matemático.
 * Genera una expresión aleatoria con operaciones básicas (+, -, *) y calcula su resultado.
 *
 * @author Sergio García Rodríguez
 */
public class PreguntaMates implements Pregunta {

    private final String pregunta;
    private final int solucion;

    /**
     * Constructor. Genera una nueva pregunta matemática y su respuesta automáticamente.
     */
    public PreguntaMates() {
        pregunta = generarPregunta();
        solucion = generarSolucion();
    }

    /**
     * Genera una operación matemática aleatoria con sumas, restas y multiplicaciones.
     * @return Una cadena con la operación matemática.
     */
    private String generarPregunta() {
        int nums = Utilidades.getRandom(LectorProperties.getMinNumEnterosPregMates(),
                LectorProperties.getMaxNumEnterosPregMates());

        StringBuilder operacion = new StringBuilder();
        for (int i = 0; i < nums; i++) {
            int cifra = Utilidades.getRandom(LectorProperties.getMinCifraPregMates(),
                    LectorProperties.getMaxCifraPregMates());
            operacion.append(cifra);

            if (i < nums - 1) { // Agregar operador solo entre números, no al final
                switch (Utilidades.getRandom(1, 4)) {
                    case 1 -> operacion.append(" + ");
                    case 2 -> operacion.append(" - ");
                    case 3 -> operacion.append(" * ");
                }
            }
        }
        return operacion.toString();
    }

    /**
     * Evalúa la expresión matemática y devuelve el resultado como String.
     * @return Resultado de la operación como cadena.
     */
    private int generarSolucion() {
        Expression exp = new ExpressionBuilder(pregunta).build();
        return (int) exp.evaluate(); // Truncamos a entero
    }

    /**
     * Devuelve la pregunta matemática generada.
     * @param mostrarSolucion Envío de solución con pregunta.
     * @return Pregunta en formato String.
     */
    public String getTextoPregunta(boolean mostrarSolucion) {
        String txt = "Realiza la siguiente operación: \n" + pregunta;
        if (mostrarSolucion){
          return txt + " ==> ("+  solucion + ")";
        }else {
            return txt;
        }
    }

    /**
     * Devuelve la respuesta correcta a la pregunta.
     * @return Respuesta en formato int.
     */
    public Object getSolucion() {
        return solucion;
    }

    /**
     * Comprueba si la respuesta es correcta según la pregunta.
     * @param respuesta Respuesta del usuario a comparar
     * @return Resultado ¿Mensaje a mostrar al usuario y si es correcta la respuesta?
     */
    public Resultado comprobarRespuesta(Object respuesta){
        boolean acierto = (int)respuesta == solucion;
        if (acierto) {
            return new Resultado("Has acertado!!!!!", acierto);
        }else {
            return new Resultado("Has fallado. El resultado es: " + solucion, acierto);
        }
    }
}
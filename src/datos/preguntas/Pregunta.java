package datos.preguntas;

/**
 * Interfaz que define el contrato para cualquier tipo de pregunta dentro del sistema.
 * Toda clase que implemente esta interfaz debe proporcionar una forma de:
 * - Obtener el enunciado de la pregunta.
 * - Obtener la respuesta correspondiente.
 *
 * @author Sergio García Rodríguez
 */
public interface Pregunta {

    /**
     * Devuelve el enunciado o texto que describe la pregunta.
     * @param mostrarSolucion Envío de solución con pregunta.
     * @return Una cadena con la pregunta.
     */
    String getTextoPregunta(boolean mostrarSolucion);

    /**
     * Devuelve la respuesta a la pregunta.
     * @return Una cadena/int/char/... con la respuesta correcta.
     */
    Object getSolucion();

    /**
     * Comprueba si la respuesta es correcta según la pregunta.
     * @param respuesta Respuesta del usuario a comparar
     * @return Resultado ¿Mensaje a mostrar al usuario y si es correcta la respuesta?
     */
    Resultado comprobarRespuesta(Object respuesta);

}

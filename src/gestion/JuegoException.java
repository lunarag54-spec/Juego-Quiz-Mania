package gestion;

/**
 * Clase que representa una excepción personalizada para el juego.
 * Se utiliza para manejar errores específicos en el flujo del juego.
 *
 *  @author Sergio García Rodríguez
 */
public class JuegoException extends Exception {

    /**
     * Constructor que recibe un mensaje de error personalizado.
     * @param msg El mensaje de error que describe el problema.
     */
    public JuegoException(String msg) {
        super(msg); // Llamada al constructor de la clase padre.
    }
}

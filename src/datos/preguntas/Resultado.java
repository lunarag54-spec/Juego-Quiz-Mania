package datos.preguntas;

/**
 * La clase incluye un mensaje explicativo y un booleano sobre si fue acertada una pregunta.
 *
 * @author Sergio García Rodríguez
 */
public class Resultado {
    /** Mensaje asociado al resultado, explicando el motivo o contenido del mismo. */
    private final String mensaje;
    /** Indica si el resultado fue acertado (true) o no (false). */
    private final boolean acertado;

    /**
     * Constructor.
     * @param mensaje el mensaje explicativo del resultado
     * @param acertado valor booleano que indica si el resultado fue acertado
     */
    public Resultado(String mensaje, boolean acertado){
        this.mensaje = mensaje;
        this.acertado = acertado;
    }

    /**
     * Obtiene el mensaje explicativo del resultado.
     * @return el mensaje del resultado
     */
    public String getMensaje() {
        return mensaje;
    }

    /**
     * Indica si el resultado fue acertado.
     * @return {@code true} si el resultado fue acertado; {@code false} en caso contrario
     */
    public boolean isAcertado() {
        return acertado;
    }
}

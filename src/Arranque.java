import presentacion.InterfazGeneral;
import tests.InterfazTest;

/**
 * Clase principal que inicia la ejecución de la aplicación o un testeo del juego.
 *
 * @author Sergio García Rodríguez
 */
public class Arranque {

    /**
     * Función principal que se ejecuta al iniciar la aplicación.
     * Lanza el juego en modo prueba o normal según la constante.
     */
    public static void main(String[] args) {
        final boolean MODO_PRUEBA = false;   // Si es true se ejecutará un testeo de la aplicación.
        if (!MODO_PRUEBA) {
            InterfazGeneral.iniciarMenuGeneral(); // Lanza la aplicación en modo normal con menú.
        }else{
            InterfazTest.iniciarMenuTest();
        }
    }
}

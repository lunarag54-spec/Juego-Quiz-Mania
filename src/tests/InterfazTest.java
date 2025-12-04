package tests;

import config.LectorProperties;
import gestion.JuegoException;
import presentacion.Utilidades;

/**
 * Clase de selección de los testeos de la aplicación.
 *
 * @author Sergio García Rodríguez
 */

public class InterfazTest {
    /**
     * Muestra el menú test.
     */
    public static void iniciarMenuTest() {
        int opcion;
        LectorProperties.cargarProperties();
        do {
            pintarMenu();
            opcion = Utilidades.preguntarInt("", 1, 5);
            switch (opcion) {
                case 1 -> opcionTestRondas();
                case 2 -> opcionTestMastermind();
                case 3 -> opcionTestGeografia();
                case 4 -> opcionTestCronometro();
                case 5 -> opcionSalir();
            }
        } while (opcion != 5);
    }

    /**
     * Muestra el menú test.
     */
    private static void pintarMenu() {
        Utilidades.escribirMensaje("========== MENÚ DE TEST ==========");
        Utilidades.escribirMensaje("1. Test Rondas                   |");
        Utilidades.escribirMensaje("2. Test Mastermind               |");
        Utilidades.escribirMensaje("3. Test Geografía                |");
        Utilidades.escribirMensaje("4. Test Cronómetro               |");
        Utilidades.escribirMensaje("5. Salir                         |");
        Utilidades.escribirMensaje("==================================");
    }

    /**
     * Lógica de la opción de Test Rondas.
     */
    public static void opcionTestRondas() {
        try {
            Tester.testRondaJuego();
        } catch (JuegoException e) {
            // Si ocurre una excepción de lógica de juego, la relanzo a la consola.
            throw new RuntimeException(e);
        }
    }

    /**
     * Lógica de la opción de Test Mastermind.
     */
    public static void opcionTestMastermind() {
        Tester.testPreguntaMasterMind();
    }

    /**
     * Lógica de la opción de Test Cronómetro
     */
    public static void opcionTestGeografia() {
        Tester.testGeografia();
    }

    /**
     * Lógica de la opción de Test Cronómetro.
     */
    public static void opcionTestCronometro() {
        Tester.testCronometro();
    }

    /**
     * Lógica de la opción salir del sistema.
     */
    public static void opcionSalir() {
        Utilidades.escribirMensaje("Has salido del testeo (>‿◠)✌\n");
    }
}

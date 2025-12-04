package tests;

import config.Constantes;
import config.LectorProperties;
import datos.preguntas.PreguntaCronometro;
import datos.preguntas.PreguntaGeografia;
import datos.preguntas.PreguntaMasterMind;
import datos.preguntas.Resultado;
import gestion.*;
import presentacion.Utilidades;

/**
 * Clase principal de testeo de la aplicación.
 *
 * @author Sergio García Rodríguez
 */
public class Tester {

    /**
     * Simula una partida rápida para pruebas automáticas.
     * Carga lista de jugadores o crea dos jugadores, lanza una partida de 2 rondas y muestra los resultados.
     * @throws JuegoException Si ocurre un error al crear jugadores.
     */
    public static void testRondaJuego() throws JuegoException {
        LectorProperties.cargarProperties(); // Carga configuración del juego.
        try {
            GestorJugadoresFichero.cargarListaJugadores();
        } catch (JuegoException e) {
            // En caso de error con el fichero de jugadores o su contenido devolvemos la Exception a consola
            throw new RuntimeException(e);
        }

        // Para probar con dos jugadores creados en este momento
//        GestorJugadores gJ = GestorPartidas.crearGestorJugadores(2);
//
//        // Se crean dos jugadores (uno humano y uno CPU).
//        Jugador j1 = gJ.crearJugador("", 1); // Humano
//        Jugador j2 = gJ.crearJugador("", 2); // CPU

        Utilidades.escribirMensaje(GestorPartidas.getGestorJugadores().getListaJugadores());

        GestorPartidas.iniciarPartida(2); // Lanza una partida de 2 rondas

        // Muestra resultados al final de la partida.
        Utilidades.escribirMensaje("\n/*/*/*/*/*/*/*/*/*/*/*/");
        Utilidades.escribirMensaje("GANADOR: " + GestorPartidas.getNombreGanador());
        Utilidades.escribirMensaje(GestorPartidas.getGestorJugadores().getListaJugadores());
        GestorJugadoresFichero.escribirFicheroJugadores(GestorPartidas.getGestorJugadores().getListaJugadores());
    }

    /**
     * Lanza una prueba del algoritmo de juego Mastermind.
     */
    public static void testPreguntaMasterMind() {
        PreguntaMasterMind pregM = new PreguntaMasterMind();
        Utilidades.escribirMensaje("El número secreto es: " + pregM.getSolucion());

        Resultado res;
        for (int i = 0; i < Constantes.TOTAL_ITERACIONES_MASTERMIND; i++) {
            res = pregM.comprobarRespuesta(
                    Utilidades.preguntarNombre(pregM.getTextoPregunta(LectorProperties.isDebug()),
                            LectorProperties.getTotalCifrasPregMasterMind(),
                            LectorProperties.getTotalCifrasPregMasterMind())
            );
            Utilidades.escribirMensaje(res.getMensaje());
            if (res.isAcertado()) {
                // Fuerzo a que salga del bucle.
                i = Constantes.TOTAL_ITERACIONES_MASTERMIND;
            }
        }
        Utilidades.escribirMensaje("Solución: " + pregM.getSolucion());
        Utilidades.escribirMensaje("Fin de la prueba Mastermind");
    }

    /**
     * Lanza una prueba del juego Historia.
     */
    public static void testGeografia() {
        PreguntaGeografia preg = new PreguntaGeografia();
        GestorLog.escribirMensaje("Pregunta de Geografía: " + preg.getTextoPregunta(true));
        Resultado res = preg.comprobarRespuesta(
                    Utilidades.preguntarChar(preg.getTextoPregunta(LectorProperties.isDebug())));
        Utilidades.escribirMensaje(res.getMensaje());
    }

    /**
     * Lanza una prueba del juego Cronómetro.
     */
    public static void testCronometro() {
        int totalSegs = Utilidades.getRandom(Constantes.MIN_SEGUNDOS_CRONOMETRO, Constantes.MAX_SEGUNDOS_CRONOMETRO);

        PreguntaCronometro preg = new PreguntaCronometro(totalSegs);
        Utilidades.escribirMensaje("Pregunta de Cronómetro. Una vez pulses enter, tienes que esperar "
                + totalSegs + " segundos y pulsar enter de nuevo. Yo contaré el tiempo...");
        Utilidades.recogerIntro();
        Utilidades.escribirMensaje(preg.getTextoPregunta(false));
        Utilidades.recogerIntro();
        Resultado res = preg.comprobarRespuesta("");
        Utilidades.escribirMensaje(res.getMensaje());
    }
}

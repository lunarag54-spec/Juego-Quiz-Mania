package gestion;

import config.Constantes;
import config.LectorProperties;
import datos.jugadores.Jugador;
import datos.preguntas.*;
import presentacion.Utilidades;

/**
 * Clase encargada de gestionar el desarrollo de una partida: jugadores, rondas, preguntas y ganador.
 *
 *  @author Sergio García Rodríguez
 */
public class GestorPartidas {

    private static GestorJugadores gJ;
    //private static Pregunta preg = null;

    /**
     * Crea un nuevo gestor de jugadores para la partida.
     * @param tamJugadores Número de jugadores a gestionar.
     * @return Instancia de GestorJugadores creada.
     */
    public static GestorJugadores crearGestorJugadores(int tamJugadores) {
        gJ = new GestorJugadores(tamJugadores);
        return getGestorJugadores();
    }

    /**
     * Devuelve el gestor de jugadores actual.
     * @return GestorJugadores activo.
     */
    public static GestorJugadores getGestorJugadores() {
        return gJ;
    }

    /**
     * Inicia una partida completa con un número determinado de rondas.
     * Al finalizar, muestra el resultado y registra en el histórico.
     * @param rondas Número de rondas a jugar.
     */
    public static void iniciarPartida(int rondas) {
        GestorLog.escribirMensaje("Inicio partida con " + gJ.getTotalJugadoresHumanos() +
                " jugadores humanos, " + gJ.getTotalJugadoresCPU() + " jugadores de CPU");

        // Inicializo los puntos de partida de los jugadores
        for (int i = 0; i < gJ.getTotalJugadores(); i++) {
            gJ.getJugadores()[i].resetPuntosPartida();
        }

        for (int i = 1; i <= rondas; i++) {
            Utilidades.escribirMensaje("\n** Ronda número " + i + " **");
            GestorPartidas.jugarRonda();
            // Muestro las puntuaciones de la tanda
            Utilidades.escribirMensaje("\n** Resultado de la Ronda número " + i + " **");
            for (int j = 0; j < gJ.getTotalJugadores(); j++) {
                Jugador jugador = gJ.getJugadores()[j];
                Utilidades.escribirMensaje(jugador.getNombre() + ": " + jugador.getPuntosRonda() + " puntos!!");
            }
        }

        // Muestro las puntuaciones de la partida
        Utilidades.escribirMensaje("\n** Resultado de la Partida **");
        for (int i = 0; i < gJ.getTotalJugadores(); i++) {
            Jugador jugador = gJ.getJugadores()[i];
            Utilidades.escribirMensaje(jugador.getNombre() + ": " + jugador.getPuntosPartida() + " puntos!!");
        }

        if (isEmpate()) {
            GestorLog.escribirMensaje("Fin de partida con " + gJ.getTotalJugadores() +
                    " jugadores. Ha habido empate: " + getNombreGanador() +
                    ". Consulte el histórico para más información");
        } else {
            GestorLog.escribirMensaje("Fin de partida con " + gJ.getTotalJugadores() +
                    " jugadores. Ganador ha sido " + getNombreGanador());
        }

        // Actualizar ranking y registrar histórico
        GestorRanking.calcularRanking();
        GestorHistorico.escribir(getGestorJugadores().getListaJugadores());
    }

    /**
     * Ejecuta una ronda para cada uno de los jugadores. A cada uno se le plantea una pregunta aleatoria.
     */
    public static void jugarRonda() {
        int tipo;
        // Inicializo los puntos de ronda de los jugadores
        for (int i = 0; i < gJ.getTotalJugadores(); i++) {
            gJ.getJugadores()[i].resetPuntosRonda();
        }
        // Selección aleatoria del tipo de pregunta (1 a 4)
        for (int i = 0; i < gJ.getTotalJugadores(); i++) {
            tipo = Utilidades.getRandom(1, 4);
            GestorLog.escribirMensaje("***** > Le toca a " + gJ.getJugadores()[i].getNombre());
            Utilidades.escribirMensaje("\n***** > Le toca a " + gJ.getJugadores()[i].getNombre());
            preguntar(tipo, gJ.getJugadores()[i]);
        }
    }

    /**
     * Crea una pregunta de un tipo aleatorio al jugador actual y muestra el resultado.
     * @param tipo Tipo de pregunta (1: Mates, 2: MasterMind, 3: Geografía, 4: Cronómetro).
     * @param jug Instancia de Jugador al que se le va a preguntar.
     */
    private static void preguntar(int tipo, Jugador jug) {
        Resultado res = null;
        switch (tipo) {
            case 1 -> {
                res = lanzarMates(jug.isHumano());
                Utilidades.escribirMensaje(res.getMensaje());
            }
            case 2 -> {
                res = lanzarMastermind(jug.isHumano());
            }
            case 3 -> {
                res = lanzarGeografia(jug.isHumano());
                Utilidades.escribirMensaje(res.getMensaje());
            }
            case 4 -> {
                res = lanzarCronometro(jug.isHumano());
                Utilidades.escribirMensaje(res.getMensaje());
            }
        }

        if (res.isAcertado()) {
            jug.addPuntos(Constantes.PUNTOS_x_RESPUESTA);
            jug.addPuntosRonda(Constantes.PUNTOS_x_RESPUESTA);
        }

    }
    /**
     * Lanza una pregunta de tipo Matemáticas y obtiene la respuesta del jugador.
     * @param isHumano boolean.
     * @return Resultado que contiene el mensaje final y si acertó o no.
     */
    private static Resultado lanzarMates(boolean isHumano) {
        PreguntaMates preg = new PreguntaMates();
        GestorLog.escribirMensaje("Pregunta de Matemáticas: " + preg.getTextoPregunta(true));

        if (isHumano) {
            return preg.comprobarRespuesta(
                    Utilidades.preguntarInt(preg.getTextoPregunta(LectorProperties.isDebug())));
       }else {
            // La CPU siempre acierta!!!
            Utilidades.escribirMensaje(preg.getTextoPregunta(false).concat("\n" +
                    preg.getSolucion()));
            return preg.comprobarRespuesta(preg.getSolucion());
       }
    }

    /**
     * Lanza una pregunta de tipo MasterMind y obtiene la respuesta del jugador.
     * El jugador tiene un número limitado de intentos para adivinar el número secreto.
     * Se evalúan sus respuestas dando retroalimentación según el sistema MasterMind.
     * @param isHumano boolean.
     * @return Resultado que contiene el mensaje final y si acertó o no.
     */
    private static Resultado lanzarMastermind(boolean isHumano) {
        PreguntaMasterMind preg = new PreguntaMasterMind();
        GestorLog.escribirMensaje("Pregunta de Mastermind");

        Resultado res = null;
        for (int i = 0; i < Constantes.TOTAL_ITERACIONES_MASTERMIND; i++) {
            if (isHumano) {
                res = preg.comprobarRespuesta(
                        Utilidades.preguntarNombre(preg.getTextoPregunta(LectorProperties.isDebug()),
                                LectorProperties.getTotalCifrasPregMasterMind(),
                                LectorProperties.getTotalCifrasPregMasterMind())
                );
            } else {
                //La CPU genera un número aleatorio!!!
                String resCPU = preg.getRespuestaCPU();
                Utilidades.escribirMensaje(preg.getTextoPregunta(false).concat("\n" + resCPU));
                res = preg.comprobarRespuesta(resCPU);
            }

            Utilidades.escribirMensaje(res.getMensaje());
            if (res.isAcertado()) {
                return res;
            }
        }
        Utilidades.escribirMensaje("Solución: " + preg.getSolucion());
        return res;
    }

    /**
     * Lanza una pregunta de tipo Geografía y obtiene la respuesta del jugador.
     * @param isHumano boolean.
     * @return Resultado que contiene el mensaje final y si acertó o no.
     */
    private static Resultado lanzarGeografia(boolean isHumano) {
        PreguntaGeografia preg = new PreguntaGeografia();
        GestorLog.escribirMensaje("Pregunta de Geografía: " + preg.getTextoPregunta(true));

        if (isHumano) {
            return preg.comprobarRespuesta(
                    Utilidades.preguntarChar(preg.getTextoPregunta(LectorProperties.isDebug())));
        }else {
            char resCPU = preg.getRespuestaCPU();
            //La CPU genera un número aleatorio!!!
            Utilidades.escribirMensaje(preg.getTextoPregunta(false).concat("\n" + resCPU));
            return preg.comprobarRespuesta(resCPU);
        }
    }

    /**
     * Lanza una pregunta de tipo Cronómetro (TicTac).
     * @param isHumano boolean.
     * @return Resultado que contiene el mensaje final y si acertó o no.
     */
    private static Resultado lanzarCronometro(boolean isHumano) {
        int totalSegs = Utilidades.getRandom(Constantes.MIN_SEGUNDOS_CRONOMETRO, Constantes.MAX_SEGUNDOS_CRONOMETRO);

        if (isHumano) {
            PreguntaCronometro preg = new PreguntaCronometro(totalSegs);
            Utilidades.escribirMensaje("Pregunta de Cronómetro. Una vez pulses enter, tienes que esperar "
                    + totalSegs + " segundos y pulsar enter de nuevo. Yo contaré el tiempo...");
            Utilidades.recogerIntro();
            Utilidades.escribirMensaje(preg.getTextoPregunta(false));
            Utilidades.recogerIntro();
            return preg.comprobarRespuesta("");
        } else {
            Utilidades.escribirMensaje("Pregunta de Cronómetro. Una vez pulses enter, tienes que esperar "
                    + totalSegs + " segundos y pulsar enter de nuevo. Yo contaré el tiempo...");
            // PAUSA para simular el tiempo de reacción del jugador CPU
            try {
                Thread.sleep(3000); // En milisegundos.
            } catch (InterruptedException e) {
                // Ignoro la Exception
            }
            // Los jugadores CPU siempre fallan la prueba de cronómetro.
            return new Resultado("Has fallado CPU!!. Has tardado muy poco tiempo...", false);
        }
    }

    /**
     * Obtiene el nombre del ganador de la partida.
     * Si hay empate, se devuelven múltiples nombres concatenados con " - ".
     * @return Nombre o nombres del ganador(es).
     */
    public static String getNombreGanador() {
        return gJ.getGanador();
    }

    /**
     * Determina si ha habido un empate en la partida.
     * @return true si hay empate, false si hay un único ganador.
     */
    public static boolean isEmpate() {
        // Si ha habido empate se mostrarán dos o más nombres de jugador separados por " - "
        return gJ.getGanador().contains(" - ");
    }
}
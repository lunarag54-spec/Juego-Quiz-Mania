package datos.preguntas;

import java.util.Arrays;
import java.util.Objects;

import config.LectorProperties;
import presentacion.Utilidades;

/**
 * Clase que representa una pregunta del tipo MasterMind.
 * El juego consiste en adivinar un número secreto con una cierta cantidad de cifras.
 * Cada intento del usuario será evaluado con pistas:
 * - 1: cifra correcta en la posición correcta
 * - 2: cifra correcta en la posición incorrecta
 * - 3: cifra incorrecta
 *
 * @author Sergio García Rodríguez
 */
public class PreguntaMasterMind implements Pregunta {

    // Número secreto generado por el sistema
    private final int[] numSecreto = new int[LectorProperties.getTotalCifrasPregMasterMind()];
    private String posiciones = "";

    /**
     * Constructor que inicializa el número secreto al crear la instancia.
     */
    public PreguntaMasterMind() {
        generarNumero();
    }

    /**
     * Genera aleatoriamente el número secreto utilizando cifras del 0 al 9.
     */
    private void generarNumero() {
        boolean cifraDuplicada;
        for (int i = 0; i < LectorProperties.getTotalCifrasPregMasterMind(); i++) {
            do{
                cifraDuplicada = false;
                numSecreto[i] = Utilidades.getRandom(LectorProperties.getMaxCifraPregMastermind());
                for (int j = i - 1; !cifraDuplicada && j >= 0; j--) {
                    cifraDuplicada = (numSecreto[i] == numSecreto[j]);
                }
            }while(cifraDuplicada);
        }
    }

    /**
     * Genera una respuesta aleatoria simulando la del CPU.
     * @return un arreglo con cifras aleatorias.
     */
    private int[] generarRespuestaCPU() {
        int[] respuestaCPU = new int[LectorProperties.getTotalCifrasPregMasterMind()];
        for (int i = 0; i < LectorProperties.getTotalCifrasPregMasterMind(); i++) {
            respuestaCPU[i] = Utilidades.getRandom(LectorProperties.getMaxCifraPregMastermind());
        }
        return respuestaCPU;
    }

    /**
     * Compara la respuesta del usuario con el número secreto y devuelve una pista codificada:
     * 1: cifra correcta en la posición correcta.
     * 2: cifra correcta en una posición incorrecta.
     * 3: cifra no presente en el número secreto.
     * @param respUsuario Cadena que representa la respuesta del usuario.
     * @return Cadena con los valores 1, 2 o 3 indicando la validez de cada cifra.
     */
    private boolean comprobarRespuesta(String respUsuario) {
        int[] aux = Arrays.copyOf(numSecreto, LectorProperties.getTotalCifrasPregMasterMind());
        int[] vRespuesta = new int[LectorProperties.getTotalCifrasPregMasterMind()];

        // Primera pasada: detecta cifras en la posición correcta
        for (int i = 0; i < LectorProperties.getTotalCifrasPregMasterMind(); i++) {
            vRespuesta[i] = 3; // Valor por defecto: incorrecto
            int x;
            try {
                x = Integer.parseInt("" + respUsuario.charAt(i));
            }catch(NumberFormatException ex){
                x = -1;
            }
            int y = numSecreto[i];
            if (x == y) {
                vRespuesta[i] = 1; // Posición y cifra correcta
                aux[i] = -1; // Se marca como usada
            }
        }

        // Segunda pasada: detecta cifras correctas en posición incorrecta
        for (int i = 0; i < LectorProperties.getTotalCifrasPregMasterMind(); i++) {
            if (vRespuesta[i] != 1) {
                for (int j = 0; j < LectorProperties.getTotalCifrasPregMasterMind(); j++) {
                    int x;
                    try {
                        x = Integer.parseInt("" + respUsuario.charAt(i));
                    }catch(NumberFormatException ex){
                        x = -1;
                    }
                    int y = aux[j];
                    // Si aún no se ha detectado y la cifra coincide
                    if (vRespuesta[i] == 3 && x == y) {
                        vRespuesta[i] = 2; // Cifra correcta pero mal ubicada
                        aux[j] = -1; // Se marca como usada
                        break; // Evita usar la misma cifra varias veces
                    }
                }
            }
        }
        posiciones = vectorToString(vRespuesta);
        return (Objects.equals(posiciones, PreguntaMasterMind.generarCadenaDeUnos(
                LectorProperties.getTotalCifrasPregMasterMind())));
    }

    /**
     * Devuelve una respuesta aleatoria del CPU como cadena.
     * @return Cadena de cifras aleatorias.
     */
    public String getRespuestaCPU() {
        return vectorToString(generarRespuestaCPU());
    }

    /**
     * Convierte un vector de enteros en una cadena.
     * @param vectorInt Vector de enteros.
     * @return Representación en texto del vector.
     */
    private String vectorToString(int[] vectorInt) {
        StringBuilder res = new StringBuilder();
        for (int i : vectorInt) {
            res.append(i);
        }
        return res.toString();
    }

    /**
     * Devuelve el enunciado o texto que describe la pregunta.
     * @param mostrarSolucion Envío de pregunta y solución.
     * @return Una cadena con la pregunta.
     */
    public String getTextoPregunta(boolean mostrarSolucion) {
        String txt = "Mastermind. Escribe un número de " +
                LectorProperties.getTotalCifrasPregMasterMind() + " cifras: ";
        if (mostrarSolucion){
            return txt + " ==> ("+  vectorToString(numSecreto) + ")";
        }else {
            return txt;
        }
    }

    /**
     * Devuelve el número secreto generado por el sistema.
     * @return Cadena con el número secreto.
     */
    public String getSolucion() {
        return vectorToString(numSecreto);
    }

    /**
     * Comprueba si la respuesta es correcta según la pregunta.
     * @param respuesta Respuesta del usuario a comparar
     * @return Resultado ¿Mensaje a mostrar al usuario y si es correcta la respuesta?
     */
    public Resultado comprobarRespuesta(Object respuesta){
        boolean acierto = comprobarRespuesta((String)respuesta);
        if (acierto) {
            return new Resultado("Has acertado!!!!!", acierto);
        }else {
            return new Resultado("Has fallado. Tus posiciones son: " + posiciones, acierto);
        }

    }

    /**
     * Genera una cadena de '1'.
     * @param cantidad Número de veces que se debe repetir el carácter '1'.
     * @return Una cadena formada por 'cantidad' repeticiones del carácter '1'.
     */
    public static String generarCadenaDeUnos(int cantidad) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < cantidad; i++) {
            sb.append('1');
        }
        return sb.toString();
    }

}

package config;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import gestion.GestorLog;

/**
 * Clase encargada de leer las propiedades de configuración desde un archivo
 * especificado en {@link Constantes#PROPERTIES_PATH}.
 * Las propiedades leídas son el modo depuración y el tamaño del ranking.
 * Si hay errores al leer el archivo o convertir los valores, se usarán
 * valores por defecto.
 */
public class LectorProperties {

    /**
     * Indica si el sistema está en modo depuración (debug).
     */
    private static boolean debug = false;

    /**
     * Tamaño máximo del ranking. Valor por defecto: 10.
     */
    private static int tamRanking = 10;

    /**
     * Cantidad mínima de enteros en pregunta matemáticas. Valor por defecto: 4.
     */
    private static int minNumEnterosPregMates = 4;

    /**
     * Cantidad mínima de enteros en pregunta matemáticas. Valor por defecto: 8.
     */
    private static int maxNumEnterosPregMates = 8;

    /**
     * Valor mínimo de enteros en pregunta matemáticas. Valor por defecto: 2.
     */
    private static int minCifraPregMates = 2;

    /**
     * Valor maximo de enteros en pregunta matemáticas. Valor por defecto: 12.
     */
    private static int maxCifraPregMates = 12;

    /**
     * Total de cifras mastermind. Valor por defecto: 3.
     */
    private static int totalCifrasPregMasterMind = 3;

    /**
     * Valor maximo de las cifras en pregunta Mastermind. Valor por defecto: 9.
     */
    private static int maxCifraPregMastermind = 9;

    /**
     * Carga las propiedades desde el archivo definido en {@link Constantes#PROPERTIES_PATH}.
     * Establece los valores de depuración y tamaño del ranking.
     */
    public static void cargarProperties() {
        Properties propiedades = new Properties();

        // Leer el archivo de propiedades
        try (FileInputStream fis = new FileInputStream(Constantes.PROPERTIES_PATH)) {
            propiedades.load(fis);
            LectorProperties.debug = Boolean.parseBoolean(propiedades.getProperty("depuracion"));
            GestorLog.escribirMensaje("Estamos en modo depuración: " + isDebug());
            try {
                LectorProperties.tamRanking = Integer.parseInt(propiedades.getProperty("tamRanking"));
                GestorLog.escribirMensaje("Tamaño de ranking: " + getTamRanking());
            } catch (NumberFormatException ex) {
                GestorLog.escribirError("Tamaño de ranking no es un número. "
                        + "Utilizo valor por defecto: " + tamRanking);
            }
            try {
                LectorProperties.minNumEnterosPregMates = Integer.parseInt(propiedades.getProperty("minNumEnterosPregMates"));
                GestorLog.escribirMensaje("minNumEnterosPregMates: " + getMinNumEnterosPregMates());
            } catch (NumberFormatException ex) {
                GestorLog.escribirError("MinNumEnterosPregMates no es un número. "
                        + "Utilizo valor por defecto: " + minNumEnterosPregMates);
            }
            try {
                LectorProperties.maxNumEnterosPregMates = Integer.parseInt(propiedades.getProperty("maxNumEnterosPregMates"));
                GestorLog.escribirMensaje("maxNumEnterosPregMates: " + getMaxNumEnterosPregMates());
            } catch (NumberFormatException ex) {
                GestorLog.escribirError("MaxNumEnterosPregMates no es un número. "
                        + "Utilizo valor por defecto: " + maxNumEnterosPregMates);
            }
            try {
                LectorProperties.minCifraPregMates = Integer.parseInt(propiedades.getProperty("minCifraPregMates"));
                GestorLog.escribirMensaje("minCifraPregMates: " + getMinCifraPregMates());
            } catch (NumberFormatException ex) {
                GestorLog.escribirError("MinCifraPregMates no es un número. "
                        + "Utilizo valor por defecto: " + minNumEnterosPregMates);
            }
            try {
                LectorProperties.maxCifraPregMates = Integer.parseInt(propiedades.getProperty("maxCifraPregMates"));
                GestorLog.escribirMensaje("maxCifraPregMates: " + getMaxCifraPregMates());
            } catch (NumberFormatException ex) {
                GestorLog.escribirError("MaxNumEnterosPregMates no es un número. "
                        + "Utilizo valor por defecto: " + maxNumEnterosPregMates);
            }
            try {
                LectorProperties.totalCifrasPregMasterMind = Integer.parseInt(propiedades.getProperty("totalCifrasPregMasterMind"));
                GestorLog.escribirMensaje("totalCifrasPregMasterMind: " + getTotalCifrasPregMasterMind());
            } catch (NumberFormatException ex) {
                GestorLog.escribirError("TotalCifrasPregMasterMind no es un número. "
                        + "Utilizo valor por defecto: " + getTotalCifrasPregMasterMind());
            }
            try {
                LectorProperties.maxCifraPregMastermind = Integer.parseInt(propiedades.getProperty("maxCifraPregMastermind"));
                GestorLog.escribirMensaje("maxCifraPregMastermind: " + getMaxCifraPregMastermind());
            } catch (NumberFormatException ex) {
                GestorLog.escribirError("MaxCifraPregMastermind no es un número. "
                        + "Utilizo valor por defecto: " + getMaxCifraPregMastermind());
            }
        } catch (IOException ex) {
            GestorLog.escribirError("Error al leer archivo de propiedades: " + ex.getMessage());
        }
    }

    /**
     * Devuelve si el sistema está en modo depuración.
     * @return true si está en modo debug, false en caso contrario.
     */
    public static boolean isDebug() {
        return debug;
    }

    /**
     * Devuelve el tamaño configurado del ranking.
     * @return Tamaño del ranking como entero.
     */
    public static int getTamRanking() {
        return tamRanking;
    }

    /**
     * Tamaño mínimo de números en la pregunta matemáticas. Valor por defecto: 4.
     * @return Tamaño mínimo de números.
     */
    public static int getMinNumEnterosPregMates() {
        return minNumEnterosPregMates;
    }

    /**
     * Tamaño maximo de números en la pregunta matemáticas. Valor por defecto: 8.
     * @return Tamaño máximo de números.
     */
    public static int getMaxNumEnterosPregMates() {
        return maxNumEnterosPregMates;
    }

    /**
     * Valor mínimo de enteros en pregunta matemáticas. Valor por defecto: 2.
     *  @return Valor mínimo de números.
     */
    public static int getMinCifraPregMates() {
        return minCifraPregMates;
    }

    /**
     * Valor mínimo de enteros en pregunta matemáticas. Valor por defecto: 12.
     *  @return Valor máximo de números.
     */
    public static int getMaxCifraPregMates() {
        return maxCifraPregMates;
    }

    /**
     * Total de cifras mastermind. Valor por defecto: 3.
     */
    public static int getTotalCifrasPregMasterMind() {
        return totalCifrasPregMasterMind;
    }

    /**
     * Valor maximo de las cifras en pregunta Mastermind. Valor por defecto: 9.
     */
    public static int getMaxCifraPregMastermind() {
        return maxCifraPregMastermind;
    }
}
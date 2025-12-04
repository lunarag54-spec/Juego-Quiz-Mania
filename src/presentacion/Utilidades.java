package presentacion;

import java.util.InputMismatchException;
import java.util.Random;

import gestion.GestorLog;

/**
 * Clase de utilidades para entrada de datos y formateo.
 * Proporciona funciones estáticas para leer datos desde consola controlando los errores.
 *
 * @author Sergio García Rodríguez
 */
public class Utilidades {
    private static final Random rnd = new Random();
    /**
     * Pregunta un número entero dentro de un rango válido.
     * @param msg Mensaje a mostrar al usuario.
     * @return Número entero introducido.
     */
    public static int preguntarInt(String msg) {
        int respuesta = -1;
        boolean valido;
        do {
            Utilidades.escribirMensaje(msg);
            try {
                respuesta = InterfazGeneral.getSc().nextInt();
                InterfazGeneral.getSc().nextLine(); // limpiar buffer
                valido = true;
            } catch (InputMismatchException e) {
                // Manejo de error por entrada no numérica
                Utilidades.escribirMensaje("ERROR: Debes introducir un número válido.");
                InterfazGeneral.getSc().nextLine(); // limpiar buffer
                valido = false;
            }
        } while (!valido);
        return respuesta;
    }

    /**
     * Pregunta al usuario una letra válida entre A, B, C o D.
     *
     * @param msg Mensaje a mostrar al usuario.
     * @return Letra introducida en mayúscula (A, B, C o D).
     */
    public static char preguntarChar(String msg) {
        char respuesta = ' ';
        boolean valido;
        do {
            Utilidades.escribirMensaje(msg);
            String entrada = InterfazGeneral.getSc().nextLine().trim().toUpperCase();
            if (entrada.length() == 1 && "ABCD".indexOf(entrada.charAt(0)) >= 0) {
                respuesta = entrada.charAt(0);
                valido = true;
            } else {
                Utilidades.escribirMensaje("ERROR: Debes introducir una letra A, B, C ó D.");
                valido = false;
            }
        } while (!valido);
        return respuesta;
    }

    /**
     * Pregunta un número entero dentro de un rango válido.
     * @param msg      Mensaje a mostrar al usuario.
     * @param valorInf Límite inferior permitido.
     * @param valorSup Límite superior permitido.
     * @return Número entero introducido dentro del rango.
     */
    public static int preguntarInt(String msg, int valorInf, int valorSup) {
        int respuesta = -1;
        boolean valido;
        do {
            Utilidades.escribirMensaje(msg);
            try {
                respuesta = InterfazGeneral.getSc().nextInt();
                InterfazGeneral.getSc().nextLine(); // limpiar buffer
                valido = respuesta >= valorInf && respuesta <= valorSup;
                if (!valido) {
                    Utilidades.escribirMensaje("Número fuera de rango.");
                }
            } catch (InputMismatchException e) {
                // Manejo de error por entrada no numérica
                Utilidades.escribirMensaje("ERROR: Debes introducir un número válido.");
                InterfazGeneral.getSc().nextLine(); // limpiar buffer
                valido = false;
            }
        } while (!valido);
        return respuesta;
    }

    /**
     * Pregunta un nombre de jugador respetando límites de tamaño y sin espacios.
     * @param msg           Mensaje a mostrar.
     * @param tamMin        Tamaño mínimo del nombre.
     * @param tamMax        Tamaño máximo del nombre.
     * @return Nombre válido introducido por el usuario.
     */
    public static String preguntarNombre(String msg, int tamMin, int tamMax) {
        return preguntarString(msg, tamMin, tamMax, false);
    }

    /**
     * Lee una línea. Es para recoger la pulsación de intro.
     */
    public static void recogerIntro() {
        InterfazGeneral.getSc().nextLine();
    }

    /**
     * Rellena un nombre con espacios en blanco hasta un tamaño determinado.
     * @param nombre Texto a rellenar.
     * @param tam    Tamaño objetivo.
     * @return Cadena con espacios añadidos al final.
     */
    public static String rellenarConBlancos(String nombre, int tam) {
        StringBuilder nombreSB = new StringBuilder(nombre);
        for (int j = nombreSB.length(); j < tam + 1; j++)
            nombreSB.append(" ");
        nombre = nombreSB.toString();
        return nombre;
    }

    /**
     * Función privada auxiliar para preguntar una cadena con validación de tamaño y espacios.
     * @param msg           Mensaje a mostrar.
     * @param tamMin        Longitud mínima.
     * @param tamMax        Longitud máxima.
     * @param admiteBlancos Indica si se permiten espacios en blanco.
     * @return Cadena válida según las restricciones.
     */
    private static String preguntarString(String msg, int tamMin, int tamMax, boolean admiteBlancos) {
        String respuesta;
        do {
            Utilidades.escribirMensaje(msg);
            respuesta = InterfazGeneral.getSc().nextLine();

            if (!admiteBlancos && respuesta.indexOf(' ') != -1) {
                GestorLog.escribirError("No se admiten espacios en blanco en los nombres de jugadores");
                Utilidades.escribirMensaje("No se admiten espacios en blanco en los nombres de jugadores");
            } else if (respuesta.length() > tamMax) {
                // Si excede el tamaño máximo, se recorta directamente
                respuesta = respuesta.substring(0, tamMax);
                return respuesta;
            } else if (respuesta.length() >= tamMin) {
                return respuesta;
            } else {
                Utilidades.escribirMensaje("El tamaño mínimo debe ser " + tamMin + " caracteres.");
            }
        } while (true);
    }

    /**
     * Función para escribir por la interfaz (consola).
     * @param msg           Mensaje a mostrar.
     */
    public static void escribirMensaje(String msg) {
        System.out.println(msg);
    }

    /**
     * Función para escribir por la interfaz (consola).
     * @param obj           Objeto a mostrar su descripción.
     */
    public static void escribirMensaje(Object obj) {
        System.out.println(obj.toString());
    }

    /**
     * Devuelve un número aleatorio entre 0 y el valor máximo especificado (inclusive).
     *
     * @param max Valor máximo que puede tomar el número aleatorio.
     * @return Número aleatorio entre 0 y max (inclusive).
     */
    public static int getRandom(int max) {
        return getRandom(0, max);
    }

    /**
     * Devuelve un número aleatorio entre el valor mínimo y máximo especificados (inclusive).
     *
     * @param min Valor mínimo que puede tomar el número aleatorio.
     * @param max Valor máximo que puede tomar el número aleatorio.
     * @return Número aleatorio entre min y max (inclusive).
     */
    public static int getRandom(int min, int max) {
        return rnd.nextInt(min, max + 1);
    }
}
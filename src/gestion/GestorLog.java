package gestion;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import config.Constantes;
import presentacion.Utilidades;

/**
 * Clase que gestiona la creación y escritura de mensajes en un archivo de log.
 * Permite abrir, escribir y cerrar el archivo de forma controlada, asegurando que cada mensaje
 * queda registrado junto con la fecha y la hora.
 *
 *  @author Sergio García Rodríguez
 */
public class GestorLog {

    private static BufferedWriter escritorLog;

    /**
     * Inicializa el gestor de log, abriendo el archivo en modo append para añadir líneas en lugar
     * de borrar su contenido.
     */
    public static void iniciar() {
        try {
            BufferedReader fis = new BufferedReader(new FileReader(Constantes.LOG_PATH));
            String linea;
            String fechaHoy = getFecha();
            if ((linea = fis.readLine()) != null ){
                if (!linea.startsWith(fechaHoy)){
                    fis.close();
                    File fich = new File(Constantes.LOG_PATH);
                    File fichNuevo = new File(Constantes.LOG_PATH + "." + getFechaFormatoOrdenado(linea));
                    if (fichNuevo.exists())
                        fichNuevo.delete();
                    fich.renameTo(fichNuevo);
                }
            }
            // Abrir el archivo en modo append (añadir contenido al final)
            escritorLog = new BufferedWriter(new FileWriter(Constantes.LOG_PATH, true));
        }catch (IOException e) {
            Utilidades.escribirMensaje("ERROR al abrir el fichero de log en " + Constantes.LOG_PATH + ": " + e.getMessage());
        }
    }

    /**
     * Cierra el archivo de log.
     */
    public static void cerrar() {
        if (escritorLog != null) {
            try {
                escritorLog.close();
            } catch (IOException e) {
                Utilidades.escribirMensaje("ERROR al cerrar el fichero de log: " + e.getMessage());
            }
        }
    }

    /**
     * Escribe un mensaje en el archivo de log.
     * Si el archivo no está abierto, se inicializa antes de escribir.
     * @param mensaje Mensaje que se desea escribir en el log.
     */
    public static void escribirMensaje(String mensaje) {
        if (escritorLog == null) {
            iniciar();
        }
        try {
            // Escribir la fecha, hora y el mensaje
            escritorLog.write(getFechaHora() + ": " + mensaje);
            escritorLog.newLine();
            escritorLog.flush(); // Asegurar que se guarde inmediatamente
        } catch (IOException e) {
            Utilidades.escribirMensaje("ERROR al escribir en el fichero de log: " + e.getMessage());
        }
    }

    public static void escribirError(String mensaje) {
        escribirMensaje("ERROR: " + mensaje);
    }

    /**
     * Obtiene la fecha y hora actual en formato de texto.
     * @return Fecha y hora actuales como cadena de texto.
     */
    private static String getFechaHora() {
        Date ahora = new Date();
        // Crear formato de fecha
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        return formato.format(ahora);
    }

    /**
     * Obtiene la fecha actual en formato de texto.
     * @return Fecha actuales como cadena de texto.
     */
    private static String getFecha() {
        Date ahora = new Date();
        // Crear formato de fecha
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        return formato.format(ahora);
    }

    /**
     * Modifica el formato de una fecha para que sirva para ser ordenable (yyyMMdd).
     * @param fecha String que contiene una fecha en formato dd/MM/yyyy.
     * @return Fecha en formato yyyyMMdd.
     */
    private static String getFechaFormatoOrdenado(String fecha) {
        try {
            if (fecha.length() > 10)
                fecha = fecha.substring(0, 10);

            // Usar formato explícito para entrada: dd/MM/yyyy
            SimpleDateFormat formatoEntrada = new SimpleDateFormat("dd/MM/yyyy");
            Date ahora = formatoEntrada.parse(fecha);

            // Formato de salida: yyyyMMdd
            SimpleDateFormat formatoSalida = new SimpleDateFormat("yyyyMMdd");
            return formatoSalida.format(ahora);
        } catch (ParseException ex) {
            return "NO_FECHA";
        }
    }
}
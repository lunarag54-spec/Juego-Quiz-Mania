package gestion;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.Date;

import config.Constantes;

 /**
 * Clase que gestiona la lectura y escritura en el archivo histórico de la aplicación.
 *
 * @author Sergio García Rodríguez
 */
public class GestorHistorico {

    /**
     * Lee el contenido completo del archivo histórico.
     * @return Texto leído del archivo como una cadena, incluyendo saltos de línea.
     */
    public static String leer() {
        String txt = "";
        try {
            // Abrir el archivo para lectura
            BufferedReader lector = new BufferedReader(new FileReader(Constantes.HISTORICO_PATH));
            try {
                String linea;
                // Leer cada línea y añadirla al texto
                while ((linea = lector.readLine()) != null) {
                    txt = txt.concat(linea) + "\n";
                }
                lector.close();
            } catch (IOException e) {
                GestorLog.escribirMensaje(e.getMessage());
            }
        } catch (FileNotFoundException e) {
            GestorLog.escribirMensaje("NO encuentro el fichero " + Constantes.HISTORICO_PATH);
        }
        return txt;
    }

    /**
     * Escribe una nueva entrada en el archivo histórico.
     * @param txt Mensaje que se desea registrar en el histórico.
     */
    public static void escribir(String txt) {
        try {
            // Abrir el archivo en modo append (añadir al final sin sobrescribir)
            BufferedWriter escritorHistorico = new BufferedWriter(new FileWriter(Constantes.HISTORICO_PATH, true));
            // Escribir la fecha actual y el texto
            escritorHistorico.write("Registro de la partida " + new Date() + ": ");
            escritorHistorico.newLine();
            escritorHistorico.write(txt);
            escritorHistorico.newLine();
            escritorHistorico.close();
        } catch (IOException e) {
            GestorLog.escribirError("Al escribir en el fichero Histórico --> " + e.getMessage());
        }
    }
}

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Clase que elimina acentos de un archivo de texto.
 */
public class QuitarAcentos {

    /**
     * Metodo principal que ejecuta el programa para eliminar acentos de un archivo.
     *
     * @param args Los argumentos de línea de comando.
     */
    public static void main(String[] args) {
        String rutaEntrada = "src/listado-general.txt"; // Ruta del archivo original
        String rutaSalida = "src/listado-general-sin-acentos.txt"; // Ruta del archivo de salida

        try (BufferedReader reader = new BufferedReader(new FileReader(rutaEntrada));
             BufferedWriter writer = new BufferedWriter(new FileWriter(rutaSalida))) {

            String linea;
            while ((linea = reader.readLine()) != null) {
                // Reemplazar letras acentuadas
                linea = linea.replace("á", "a")
                        .replace("é", "e")
                        .replace("í", "i")
                        .replace("ó", "o")
                        .replace("ú", "u")
                        .replace("Á", "A")
                        .replace("É", "E")
                        .replace("Í", "I")
                        .replace("Ó", "O")
                        .replace("Ú", "U");
                // Escribir la línea sin acentos en el archivo de salida
                writer.write(linea);
                writer.newLine();
            }

            System.out.println("Los acentos han sido eliminados y el nuevo archivo ha sido creado: " + rutaSalida);
        } catch (IOException e) {
            System.out.println("Error al procesar el archivo: " + e.getMessage());
        }
    }
}

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;

/**
 * Esta clase gestiona el diccionario de palabras para el juego de Scrabble.
 */
public class Diccionario {
    private HashSet<String> palabras;

    /**
     * Constructor que carga el diccionario desde un archivo.
     *
     * @param rutaArchivo la ruta del archivo de texto que contiene las palabras del diccionario.
     * @throws IOException si ocurre un error al leer el archivo.
     */
    public Diccionario(String rutaArchivo) throws IOException {
        palabras = new HashSet<>();
        cargarDiccionario(rutaArchivo);
    }

    /**
     * Carga las palabras del diccionario desde un archivo.
     *
     * @param rutaArchivo la ruta del archivo de texto que contiene las palabras del diccionario.
     * @throws IOException si ocurre un error al leer el archivo.
     */
    private void cargarDiccionario(String rutaArchivo) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(rutaArchivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                palabras.add(linea.trim().toLowerCase());
            }
        }
    }

    /**
     * Verifica si una palabra existe en el diccionario.
     *
     * @param palabra la palabra a verificar.
     * @return true si la palabra existe en el diccionario, false en caso contrario.
     */
    public boolean existePalabra(String palabra) {
        return palabras.contains(palabra.toLowerCase());
    }
}

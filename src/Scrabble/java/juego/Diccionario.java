package juego;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;

/**
 * Clase que gestiona un diccionario de palabras, proporcionando métodos
 * para cargar un archivo de palabras y verificar si una palabra existe en el diccionario.
 */
public class Diccionario {

    private HashSet<String> palabras;

    /**
     * Constructor que inicializa el diccionario cargando las palabras desde un archivo.
     *
     * @throws RuntimeException Si ocurre un error al cargar el archivo del diccionario.
     */
    public Diccionario(){
        try{
            palabras = new HashSet<>();
            cargarDiccionario("src/Scrabble/java/juego/listado-general.txt");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Carga las palabras del archivo de texto especificado y las agrega al conjunto de palabras del diccionario.
     * Cada palabra se convierte a minúsculas antes de ser agregada.
     *
     * @param rutaArchivo La ruta del archivo de texto que contiene el listado de palabras.
     * @throws IOException Si ocurre un error al leer el archivo.
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
     * @param palabra La palabra a verificar.
     * @return true si la palabra está en el diccionario, false en caso contrario.
     */
    public boolean existePalabra(String palabra) {
        return palabras.contains(palabra.toLowerCase());
    }
}

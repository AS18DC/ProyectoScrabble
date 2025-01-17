package juego;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.List;

/**
 * Esta clase proporciona un método para buscar archivos dentro de un directorio que
 * tengan una extensión específica.
 */
public class FileDirectory {

    /**
     * Obtiene una lista de nombres de archivos con una extensión específica en un directorio dado.
     *
     * @param directory El directorio donde se buscarán los archivos.
     * @param extension La extensión de los archivos que se desean buscar (incluyendo el punto, como ".txt").
     * @return Una lista de nombres de archivos que tienen la extensión especificada.
     *         Si el directorio no es válido o no contiene archivos con la extensión, se devuelve una lista vacía.
     */
    public static List<String> getFileNamesWithExtension(File directory, String extension) {
        List<String> fileNames = new ArrayList<>();

        if (directory.isDirectory()) {
            File[] files = directory.listFiles(new FilenameFilter() {
                @Override
                public boolean accept(File dir, String name) {
                    return name.toLowerCase().endsWith(extension.toLowerCase());
                }
            });
            for (File file : files) {
                fileNames.add(file.getName());
            }
        } else {
            System.out.println("The provided path is not a directory.");
        }
        return fileNames;
    }
}

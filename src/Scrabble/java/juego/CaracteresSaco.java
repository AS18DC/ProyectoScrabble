package juego;

import java.util.ArrayList;

/**
 * Interfaz que define el comportamiento de una bolsa de caracteres (símbolos),
 * proporcionando métodos para obtener, reponer y restablecer los caracteres en la bolsa.
 */
public interface CaracteresSaco {

    /**
     * Obtiene una cantidad específica de caracteres de la bolsa.
     *
     * @param n El número de caracteres a obtener de la bolsa.
     * @return Una lista de caracteres obtenidos de la bolsa.
     */
    public ArrayList<Character> get(int n);

    /**
     * Restablece la bolsa, limpiando su contenido actual.
     * Este método puede ser útil cuando se quiere reiniciar el estado de la bolsa.
     */
    public void reset();

    /**
     * Obtiene la cantidad de caracteres restantes en la bolsa.
     *
     * @return El número de caracteres que quedan en la bolsa.
     */
    public int remaning();

    /**
     * Reemplaza el contenido actual de la bolsa con una lista de caracteres proporcionada.
     *
     * @param characterList La lista de caracteres que se utilizará para reponer la bolsa.
     */
    public void reponer(ArrayList<Character> characterList);
}

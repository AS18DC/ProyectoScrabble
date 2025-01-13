/**
 * Clase que representa una letra en el juego de Scrabble.
 */
class Letra {
    String letra;
    int puntaje;
    int cantidad;

    /**
     * Constructor de la clase Letra.
     *
     * @param letra La letra.
     * @param puntaje El puntaje de la letra.
     * @param cantidad La cantidad de veces que esta letra está disponible en el saco.
     */
    public Letra(String letra, int puntaje, int cantidad) {
        this.letra = letra;
        this.puntaje = puntaje;
        this.cantidad = cantidad;
    }
}

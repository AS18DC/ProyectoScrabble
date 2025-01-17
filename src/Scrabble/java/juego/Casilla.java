package juego;

/**
 * Representa una casilla en un tablero de juego que puede contener una ficha
 * y tener una bonificación asociada para modificar el puntaje de una jugada.
 */
public class Casilla {

    private Character ficha;
    private String bonificacion;

    /**
     * Crea una casilla vacía sin bonificación.
     */
    public Casilla() {
        this.ficha = null;
        this.bonificacion = null;
    }

    /**
     * Crea una casilla sin ficha, pero con una bonificación específica.
     *
     * @param bonificacion La bonificación que tendrá la casilla (ej. "2L", "3P", etc.).
     */
    public Casilla(String bonificacion) {
        this.ficha = null;
        this.bonificacion = bonificacion;
    }

    /**
     * Coloca una ficha en la casilla.
     *
     * @param ficha El caracter que representa la ficha que se coloca en la casilla.
     */
    public void colocarFicha(Character ficha) {
        this.ficha = ficha;
    }

    /**
     * Obtiene la ficha actual de la casilla.
     *
     * @return El caracter de la ficha colocada en la casilla, o null si la casilla está vacía.
     */
    public Character getFicha() {
        return ficha;
    }

    /**
     * Verifica si la casilla está vacía (sin ficha).
     *
     * @return true si la casilla está vacía, false si tiene una ficha.
     */
    public boolean estaVacia() {
        return ficha == null;
    }

    /**
     * Establece la bonificación de la casilla.
     *
     * @param bonificacion La bonificación que se asignará a la casilla (ej. "2L", "3P").
     */
    public void setBonificacion(String bonificacion) {
        this.bonificacion = bonificacion;
    }

    /**
     * Obtiene la bonificación de la casilla.
     *
     * @return La bonificación de la casilla, o null si no tiene bonificación.
     */
    public String getBonificacion() {
        return bonificacion;
    }

    /**
     * Aplica el multiplicador de bonificación al puntaje base.
     * Si la casilla tiene una bonificación, el puntaje base será multiplicado
     * por el valor correspondiente (2L, 3L, 2P, 3P).
     *
     * @param puntosBase El puntaje base a modificar.
     * @return El puntaje modificado según la bonificación de la casilla.
     */
    public int aplicarMultiplicador(int puntosBase) {
        if (bonificacion == null) {
            return puntosBase;
        }
        switch (bonificacion) {
            case "2L": return puntosBase * 2;
            case "3L": return puntosBase * 3;
            case "2P": return puntosBase * 2;
            case "3P": return puntosBase * 3;
            default: return puntosBase;
        }
    }

    /**
     * Devuelve una representación en forma de cadena de la casilla.
     * Si la casilla tiene una ficha, se devuelve el símbolo de la ficha.
     * Si la casilla tiene una bonificación, se devuelve la bonificación.
     * Si está vacía y sin bonificación, se devuelve un espacio en blanco.
     *
     * @return Una cadena que representa el contenido de la casilla.
     */
    @Override
    public String toString() {
        if (ficha != null) {
            return ficha.toString();
        }
        return (bonificacion != null) ? bonificacion : " ";
    }
}

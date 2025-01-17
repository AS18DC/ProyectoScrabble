package juego;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Representa un carácter (símbolo) con un valor de puntos asociado, utilizado en el contexto de un juego.
 * Esta clase está configurada para ignorar propiedades desconocidas durante la deserialización de JSON.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Character {
    private String symbol;
    private int points;

    /**
     * Constructor por defecto que crea un objeto juego.Character sin inicializar sus propiedades.
     */
    public Character() {
    }

    /**
     * Constructor que crea un objeto juego.Character con un símbolo y puntos específicos.
     *
     * @param symbol El símbolo que representa el carácter (por ejemplo, una letra).
     * @param points Los puntos asociados al símbolo en el contexto del juego.
     */
    public Character(String symbol, int points) {
        this.symbol = symbol;
        this.points = points;
    }

    /**
     * Obtiene el símbolo del carácter.
     *
     * @return El símbolo del carácter (por ejemplo, una letra).
     */
    public String getSymbol() {
        return symbol;
    }

    /**
     * Establece el símbolo del carácter.
     *
     * @param symbol El nuevo símbolo del carácter.
     */
    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    /**
     * Obtiene los puntos asociados al carácter.
     *
     * @return Los puntos asociados al carácter.
     */
    public int getPoints() {
        return points;
    }

    /**
     * Establece los puntos asociados al carácter.
     *
     * @param points Los nuevos puntos a asignar al carácter.
     */
    public void setPoints(int points) {
        this.points = points;
    }

    /**
     * Devuelve una representación en cadena del carácter, que es simplemente su símbolo.
     *
     * @return El símbolo del carácter como cadena.
     */
    @Override
    public String toString() {
        return getSymbol();
    }
}

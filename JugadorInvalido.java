/**
 * Clase que representa una excepción personalizada para indicar que los datos de un jugador son inválidos.
 */
public class JugadorInvalido extends Exception {
    /**
     * Constructor de la clase JugadorInvalido.
     *
     * @param message El mensaje de error que describe la causa de la excepción.
     */
    public JugadorInvalido(String message) {
        super(message);
    }
}

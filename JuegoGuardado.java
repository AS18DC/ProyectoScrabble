import java.util.List;

/**
 * Clase que representa un juego guardado de Scrabble.
 */
public class JuegoGuardado {
    private Tablero tablero;
    private Partida partida;
    private List<String> letrasJugador1;
    private List<String> letrasJugador2;
    private int puntajeJugador1;
    private int puntajeJugador2;
    private int contadorMovimientos;
    private long tiempoTotal;
    private int palabrasColocadas;


    /**
     * Constructor para crear un nuevo juego guardado.
     *
     * @param tablero           El estado del tablero.
     * @param partida           Los datos de la partida.
     * @param letrasJugador1    Las letras del jugador 1.
     * @param letrasJugador2    Las letras del jugador 2.
     * @param puntajeJugador1   El puntaje del jugador 1.
     * @param puntajeJugador2   El puntaje del jugador 2.
     * @param contadorMovimientos El contador de movimientos.
     */
    public JuegoGuardado(Tablero tablero, Partida partida, List<String> letrasJugador1, List<String> letrasJugador2, int puntajeJugador1, int puntajeJugador2, int contadorMovimientos, long tiempoTotal, int palabrasColocadas) {
        this.tablero = tablero;
        this.partida = partida;
        this.letrasJugador1 = letrasJugador1;
        this.letrasJugador2 = letrasJugador2;
        this.puntajeJugador1 = puntajeJugador1;
        this.puntajeJugador2 = puntajeJugador2;
        this.contadorMovimientos = contadorMovimientos;
        this.tiempoTotal = tiempoTotal;
        this.palabrasColocadas = palabrasColocadas;
    }

    /**
     * Obtiene el estado del tablero.
     *
     * @return El tablero.
     */
    public Tablero getTablero() {
        return tablero;
    }

    /**
     * Obtiene los datos de la partida.
     *
     * @return La partida.
     */
    public Partida getPartida() {
        return partida;
    }

    /**
     * Obtiene las letras del jugador 1.
     *
     * @return Las letras del jugador 1.
     */
    public List<String> getLetrasJugador1() {
        return letrasJugador1;
    }

    /**
     * Obtiene las letras del jugador 2.
     *
     * @return Las letras del jugador 2.
     */
    public List<String> getLetrasJugador2() {
        return letrasJugador2;
    }

    /**
     * Obtiene el puntaje del jugador 1.
     *
     * @return El puntaje del jugador 1.
     */
    public int getPuntajeJugador1() {
        return puntajeJugador1;
    }

    /**
     * Obtiene el puntaje del jugador 2.
     *
     * @return El puntaje del jugador 2.
     */
    public int getPuntajeJugador2() {
        return puntajeJugador2;
    }

    /**
     * Obtiene el contador de movimientos.
     *
     * @return El contador de movimientos.
     */
    public int getContadorMovimientos() {
        return contadorMovimientos;
    }

    /**
     * Establece el contador de movimientos.
     *
     * @param contadorMovimientos El nuevo valor del contador de movimientos.
     */
    public void setContadorMovimientos(int contadorMovimientos) {
        this.contadorMovimientos = contadorMovimientos;
    }

    public long getTiempoTotal() {
        return tiempoTotal;
    }

    public int getPalabrasColocadas() {
        return palabrasColocadas;
    }
}

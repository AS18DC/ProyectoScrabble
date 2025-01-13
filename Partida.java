/**
 * Clase que representa una partida de Scrabble.
 */
public class Partida {
    private String alias;
    private Jugador jugador1; // Agregar jugador1
    private Jugador jugador2;
    private Saco saco; // Agregar jugador2
    private boolean gano;
    private int puntos;
    private long tiempoTotal;
    private int palabrasColocadas;

    /**
     * Constructor para crear una nueva partida.
     *
     * @param alias            El alias de la partida.
     * @param jugador1         El primer jugador.
     * @param jugador2         El segundo jugador.
     * @param saco             El saco de letras.
     * @param puntos           Los puntos de la partida.
     * @param gano             Indicador de si la partida fue ganada.
     * @param tiempoTotal      El tiempo total de la partida en milisegundos.
     * @param palabrasColocadas La cantidad de palabras colocadas durante la partida.
     */
    public Partida(String alias, Jugador jugador1, Jugador jugador2, Saco saco, int puntos, boolean gano, long tiempoTotal, int palabrasColocadas) {
        this.alias = alias;
        this.jugador1 = jugador1;
        this.jugador2 = jugador2;
        this.saco = saco;
        this.puntos = puntos;
        this.gano = gano;
        this.tiempoTotal = tiempoTotal;
        this.palabrasColocadas = palabrasColocadas;
    }

    /**
     * Obtiene el primer jugador.
     *
     * @return El primer jugador.
     */
    public Jugador getJugador1() {
        return jugador1;
    }

    /**
     * Establece el primer jugador.
     *
     * @param jugador1 El primer jugador.
     */
    public void setJugador1(Jugador jugador1) {
        this.jugador1 = jugador1;
    }

    /**
     * Obtiene el segundo jugador.
     *
     * @return El segundo jugador.
     */
    public Jugador getJugador2() {
        return jugador2;
    }

    /**
     * Establece el segundo jugador.
     *
     * @param jugador2 El segundo jugador.
     */
    public void setJugador2(Jugador jugador2) {
        this.jugador2 = jugador2;
    }

    /**
     * Obtiene el saco.
     *
     * @return El saco.
     */
    public Saco getSaco() {
        return saco;
    }

    /**
     * Establece el saco.
     *
     * @param saco El nuevo saco.
     */
    public void setSaco(Saco saco) {
        this.saco = saco;
    }

    /**
     * Obtiene el alias del jugador.
     *
     * @return El alias del jugador.
     */
    public String getAlias() {
        return this.alias;
    }

    /**
     * Establece el alias del jugador.
     *
     * @param alias El nuevo alias del jugador.
     */
    public void setAlias(String alias) {
        this.alias = alias;
    }

    /**
     * Verifica si el jugador ganó la partida.
     *
     * @return true si el jugador ganó la partida, false en caso contrario.
     */
    public boolean isGano() {
        return this.gano;
    }

    /**
     * Establece si el jugador ganó la partida.
     *
     * @param gano true si el jugador ganó la partida, false en caso contrario.
     */
    public void setGano(boolean gano) {
        this.gano = gano;
    }

    /**
     * Obtiene los puntos obtenidos en la partida.
     *
     * @return Los puntos obtenidos en la partida.
     */
    public int getPuntos() {
        return this.puntos;
    }

    /**
     * Establece los puntos obtenidos en la partida.
     *
     * @param puntos Los puntos obtenidos en la partida.
     */
    public void setPuntos(int puntos) {
        this.puntos = puntos;
    }

    /**
     * Obtiene el tiempo total de la partida en segundos.
     *
     * @return El tiempo total de la partida en segundos.
     */
    public long getTiempoTotal() {
        return this.tiempoTotal;
    }

    /**
     * Establece el tiempo total de la partida en segundos.
     *
     * @param tiempoTotal El tiempo total de la partida en segundos.
     */
    public void setTiempoTotal(long tiempoTotal) {
        this.tiempoTotal = tiempoTotal;
    }

    /**
     * Obtiene el número de palabras colocadas durante la partida.
     *
     * @return El número de palabras colocadas durante la partida.
     */
    public int getPalabrasColocadas() {
        return this.palabrasColocadas;
    }

    /**
     * Establece el número de palabras colocadas durante la partida.
     *
     * @param palabrasColocadas El número de palabras colocadas durante la partida.
     */
    public void setPalabrasColocadas(int palabrasColocadas) {
        this.palabrasColocadas = palabrasColocadas;
    }
}

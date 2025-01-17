package juego;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.*;

/**
 * Clase que representa a un jugador en el juego.
 * Un jugador tiene un alias, un correo electrónico, un puntaje total, una cantidad de palabras jugadas,
 * las fichas que posee, y el tiempo total jugado en segundos.
 * Esta clase incluye métodos para gestionar las fichas del jugador, su puntaje, y el progreso durante el juego.
 */
@JsonIgnoreProperties(ignoreUnknown = true)

public class Jugador {
    private String alias;            // Alias del jugador
    private String email;            // Correo electrónico del jugador
    private int score;               // Puntaje total del jugador
    private int palabrasJugadas;     // Cantidad de palabras jugadas por el jugador
    private Fichas playerCharacters; // Fichas que posee el jugador
    private long tiempoTotalJugado;
    private int partidasJugadas;
    // Tiempo total jugado en segundos

    /**
     * Constructor vacío por defecto.
     */
    public Jugador(){

    }

    /**
     * Constructor para crear un jugador con un alias y un correo electrónico.
     * Inicializa el puntaje, palabras jugadas, fichas y tiempo total jugado en valores predeterminados.
     *
     * @param alias Alias del jugador.
     * @param email Correo electrónico del jugador.
     */
    public Jugador(String alias, String email) {
        this.alias = alias;
        this.email = email;
        this.score = 0;
        this.palabrasJugadas = 0;
        this.playerCharacters = new Fichas();
        this.tiempoTotalJugado = 0;
        this.partidasJugadas=0;
    }

    /**
     * Establece las fichas del jugador.
     *
     * @param playerCharacters Las nuevas fichas del jugador.
     */
    public void setPlayerCharacters(Fichas playerCharacters) {
        this.playerCharacters = playerCharacters;
    }


    public void setAlias(String alias) {
        this.alias = alias;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Obtiene el alias del jugador.
     *
     * @return El alias del jugador.
     */
    public String getAlias() {
        return alias;
    }

    /**
     * Obtiene el correo electrónico del jugador.
     *
     * @return El correo electrónico del jugador.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Obtiene el puntaje total del jugador.
     *
     * @return El puntaje total del jugador.
     */
    public int getScore() {
        return score;
    }

    /**
     * Obtiene la cantidad de palabras jugadas por el jugador.
     *
     * @return La cantidad de palabras jugadas.
     */
    public int getPalabrasJugadas() {
        return palabrasJugadas;
    }

    /**
     * Obtiene el tiempo total jugado por el jugador en segundos.
     *
     * @return El tiempo total jugado en segundos.
     */
    public long getTiempoTotalJugado() {
        return tiempoTotalJugado;
    }

    /**
     * Limpia las fichas del jugador, reiniciándolas a un estado vacío.
     */
    public void limpiarFichas(){
        this.playerCharacters = new Fichas();
    }

    /**
     * Obtiene las fichas del jugador.
     *
     * @return Las fichas del jugador.
     */
    public Fichas getPlayerCharacters() {
        return playerCharacters;
    }

    /**
     * Obtiene la cantidad de fichas que tiene el jugador.
     *
     * @return El número de fichas del jugador.
     */
    public int numberOfCharacters() {
        return playerCharacters.cantidadDeFichas();
    }

    /**
     * Imprime las fichas del jugador en la consola.
     */
    public void printCharacters(){
        System.out.println("Fichas del jugador: " + this.alias + "\n" + this.playerCharacters);
    }

    /**
     * Agrega nuevas fichas al jugador.
     *
     * @param newCharacters Lista de nuevas fichas a agregar.
     */
    public void addCharacters(ArrayList<Character> newCharacters){
        playerCharacters.reponer(newCharacters);
    }

    /**
     * Agrega puntos al puntaje del jugador.
     *
     * @param puntos Los puntos a agregar.
     */
    public void addPoints(int puntos) {
        this.score += puntos;
    }

    /**
     * Aumenta la cantidad de palabras jugadas por el jugador.
     */
    public void addWordPlayed() {
        this.palabrasJugadas +=1 ;
    }

    /**
     * Aumenta el tiempo total jugado por el jugador en segundos.
     *
     * @param seconds El tiempo en segundos a agregar.
     */
    public void addTimePlated(long seconds) {
        this.tiempoTotalJugado += seconds;
    }

    public int getPartidasJugadas() {
        return partidasJugadas;
    }

    public void setPartidasJugadas(int partidasJugadas) {
        this.partidasJugadas = partidasJugadas;
    }

    /**
     * Representa al jugador como una cadena de texto.
     *
     * @return Una cadena con los detalles del jugador.
     */
    public String toString() {
        return "juego.Jugador{" +
                "alias='" + alias + '\'' +
                ", email='" + email + '\'' +
                ", score=" + score +
                ", palabrasJugadas=" + palabrasJugadas +
                ", tiempoTotalJugado=" + tiempoTotalJugado + " segundos" +
                '}';
    }

}

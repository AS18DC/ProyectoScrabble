package juego;

import java.util.ArrayList;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Clase que representa una partida de Scrabble entre dos jugadores.
 * Gestiona el flujo del juego, las acciones de los jugadores y el manejo de tiempo.
 */
public class Partida {
    UUID uuid = UUID.randomUUID();
    private final ManejadorDeArchivos manejadorDeArchivos = new ManejadorDeArchivos();
    private String id;
    private Jugador jugador1;
    private Jugador jugador2;
    private int score1;
    private int score2;
    private Saco bag;
    private Tablero tablero;
    private int actualTurn;
    private long initialTime;
    private int time;
    private int winner;

    /**
     * Constructor por defecto de la clase juego.Partida.
     */
    public Partida() {
    }

    /**
     * Constructor de la clase juego.Partida, inicializa los jugadores y otros atributos.
     *
     * @param jugador1 El primer jugador de la partida.
     * @param jugador2 El segundo jugador de la partida.
     */
    public Partida(Jugador jugador1, Jugador jugador2) {
        this.id = uuid.toString();
        this.jugador1 = jugador1;
        this.jugador2 = jugador2;
        this.bag = new Saco();
        this.tablero = new Tablero();
        this.score1 = 0;
        this.score2 = 0;
        this.actualTurn = 1;
        this.winner = 0;
        startGame();
    }

    public String getId() {
        return id;
    }

    public juego.Jugador getJugador1() {
        return jugador1;
    }

    public int getActualTurn() {
        return actualTurn;
    }

    public juego.Jugador getJugador2() {
        return jugador2;
    }


    public int getScore1() {
        return score1;
    }


    public int getScore2() {
        return score2;
    }


    public Saco getBag() {
        return bag;
    }


    public Tablero getTablero() {
        return tablero;
    }

    public void setTablero(Tablero tablero) {
        this.tablero = tablero;
    }


    public int getTime() {
        return time;
    }

    public int getWinner() {
        return winner;
    }

    public void setWinner(int winner) {
        this.winner = winner;
    }

    public void ubicarPalabra(String word, int fila, int columna, boolean horizontal, Jugador jugador){
        int puntos = jugador.getScore();
        this.tablero.ubicarPalabra(word.toUpperCase(), fila, columna, horizontal, jugador);
        if (actualTurn ==1){
            this.score1 = jugador.getScore() - puntos;
        }else {
            this.score2 = jugador.getScore() - puntos;
        }
        reponerFichas(jugador);
        manejadorDeArchivos.salvarPartida(this);
    }


    /**
     * Alterna el turno entre los dos jugadores.
     */
    public void alternarTurno() {
        if (actualTurn == 1) {

            actualTurn = 2;
        } else {
            actualTurn = 1;
        }
    }

    /**
     * Inicia el juego, reparte las fichas y registra el tiempo inicial.
     */
    private void startGame() {
        jugador1.addCharacters(bag.get(7));
        jugador2.addCharacters(bag.get(7));
    }

    /**
     * Método principal del juego que gestiona el ciclo de la partida.
     * Alterna los turnos de los jugadores, muestra el tablero y guarda los avances en archivos.
     */
    public void game() {
        startGame();
        boolean finPartida = false;
        while (!chooseWinner() && !finPartida) {
            initialTime = System.currentTimeMillis();
            this.tablero.mostrarTablero();
            if (actualTurn == 1) {
                finPartida = menuDeJugador(jugador1);
            } else {
                finPartida = menuDeJugador(jugador2);
            }
            chooseWinner();
            manejadorDeArchivos.salvarPartida(this);
            manejadorDeArchivos.salvarJugador(getJugador1());
            manejadorDeArchivos.salvarJugador(getJugador2());
            finishGame();
        }
    }

    /**
     * Continúa una partida guardada, manteniendo el turno de los jugadores y el estado actual del juego.
     */
    public void continuarPartida() {
        boolean finPartida = false;
        while (!chooseWinner() && !finPartida) {
            this.initialTime = System.currentTimeMillis();
            this.tablero.mostrarTablero();
            if (actualTurn == 1) {
                finPartida = menuDeJugador(jugador1);
            } else {
                finPartida = menuDeJugador(jugador2);
            }
            chooseWinner();
            manejadorDeArchivos.salvarPartida(this);
            finishGame();
        }
    }
    private boolean LetraValida(String letra){
        Pattern pattern = Pattern.compile("[A-Z]");
        Matcher matcher = pattern.matcher(letra);
        if (matcher.matches()){
            return true;
        }
        else return (letra.equals("RR") | letra.equals("CH") | letra.equals("LL"));
    }

    /**
     * Muestra el menú de opciones para el jugador y maneja su selección.
     *
     * @param jugador El jugador que está realizando una acción en su turno.
     * @return true si la partida ha terminado, false en caso contrario.
     */
    public boolean menuDeJugador(Jugador jugador) {
        System.out.println("Es turno del jugador " + jugador.getAlias());
        System.out.println("Tiempo de partida: " + this.time);
        jugador.printCharacters();
        Fichas comodin = jugador.getPlayerCharacters();
        while(comodin.existeComodin()){
            System.out.println("Te has conseguido un comodín, cámbialo por una letra!!");
            String letra = Main.read.next();
            letra = letra.toUpperCase();
            while (!LetraValida(letra)){
                System.out.println("Necesitas elegir una letra entre la A-Z o CH,LL,RR, intenta de nuevo");
                letra = Main.read.next();
                letra = letra.toUpperCase();
            }
            jugador.getPlayerCharacters().setComodin(letra);
            comodin = jugador.getPlayerCharacters();
            jugador.printCharacters();
        }

        boolean finalizar = false;
        do{
            System.out.println("=========================");
            System.out.println("        MENÚ JUEGO      ");
            System.out.println("=========================");
            System.out.println("1. Colocar Palabra");
            System.out.println("2. Cambiar Fichas");
            System.out.println("3. Pasar Turno");
            System.out.println("4. Salir de la juego.Partida");
            System.out.println("=========================");
            System.out.print("Por favor, selecciona una opcion (1-4): ");
            int opcion = Main.leerNumero();
            switch (opcion) {
                case 1:
                    if (colocarPalabra(jugador)) {
                        alternarTurno();
                        finalizar = true;
                    }
                    break;
                case 2:
                    cambiarFichasDeJugador(jugador);
                    alternarTurno();
                    finalizar = true;
                    break;
                case 3:
                    alternarTurno();
                    finalizar = true;
                    break;
                case 4:
                    salirDePartida();
                    return true;
                default:
                    System.out.println("Opción inválida. Por favor, selecciona una opción válida.");
            }
        }while (!finalizar);
        return false;
    }

    /**
     * Permite al jugador cambiar sus fichas por otras del saco.
     *
     * @param jugador El jugador que está cambiando fichas.
     * @return true si el cambio fue exitoso, false en caso contrario.
     */
    public boolean cambiarFichasDeJugador(Jugador jugador) {
        String fichasACambiar = "";

        for (Character fichas : jugador.getPlayerCharacters().getFichas()){
            fichasACambiar = fichasACambiar + "," + fichas;
        }

        Fichas fichas = jugador.getPlayerCharacters();
        return fichas.reemplazarFichas(fichasACambiar, bag);
    }

    /**
     * Permite al jugador salir de la partida y guardar el progreso.
     */
    public void salirDePartida() {
        manejadorDeArchivos.salvarPartida(this);
    }

    /**
     * Permite al jugador colocar una palabra en el tablero.
     *
     * @param jugador El jugador que coloca la palabra.
     * @return true si la palabra fue colocada exitosamente, false en caso contrario.
     */
    public boolean colocarPalabra(Jugador jugador) {
        boolean result = false;
        while (jugador.getPlayerCharacters().existeComodin()) {
            System.out.println("Te has conseguido un comodín, cámbialo por una letra!!");
            String letra = Main.read.next();
            letra = letra.toUpperCase();
            jugador.getPlayerCharacters().setComodin(letra);
        }
        Diccionario diccionario = new Diccionario();
        System.out.println("Escribe la palabra que quieres poner");
        String word = Main.read.next();
        int cont = 0;
        while (!diccionario.existePalabra(word)) {
            System.out.println("Esa palabra no existe en el diccionario, por favor ingrese otra");
            word = Main.read.next();
            cont++;
            if (cont == 2) {
                System.out.println("Perdiste el turno");
                return true;
            }
        }
        System.out.println("¿En qué posición la deseas colocar? Número de fila: ");
        int fila = Main.read.nextInt();
        System.out.println("Número de columna: ");
        int columna = Main.read.nextInt();
        System.out.println("Escribe la dirección de la palabra:\n1. Horizontal\n2. Vertical");
        int direccion = Main.read.nextInt();
        if (direccion == 1) {
            result = this.tablero.ubicarPalabra(word.toUpperCase(), fila, columna, true, jugador);
        } else if (direccion == 2) {
            result = this.tablero.ubicarPalabra(word.toUpperCase(), fila, columna, false, jugador);
        }
        reponerFichas(jugador);
        return result;
    }

    /**
     * Reemplaza las fichas del jugador si es necesario.
     *
     * @param jugador El jugador que está reponiendo sus fichas.
     */
    public void reponerFichas(Jugador jugador) {
        int fichasNecesarias = 7 - jugador.numberOfCharacters();
        if (fichasNecesarias > 0) {
            ArrayList<Character> nuevasFichas = bag.get(fichasNecesarias);
            jugador.addCharacters(nuevasFichas);
        }
    }

    /**
     * Finaliza el juego y calcula el tiempo total de la partida.
     */
    public void finishGame() {
        long finishtime = System.currentTimeMillis();
        this.time += (int) ((finishtime - this.initialTime)/10000);
    }

    /**
     * Verifica si hay un ganador basado en las condiciones del juego.
     *
     * @return true si hay un ganador, false en caso contrario.
     */
    public boolean chooseWinner() {
        if (this.bag.remaning() == 0) {
            if (this.jugador1.numberOfCharacters() == 1) {
                System.out.println("el ganador es: " + jugador1.getAlias());
                this.winner = 1;
                return true;
            } else if (this.jugador2.numberOfCharacters() == 1) {
                System.out.println("el ganador es: " + jugador2.getAlias());
                this.winner = 2;
                return true;
            }
        }
        return false;
    }
}

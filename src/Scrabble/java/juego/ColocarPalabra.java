package juego;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Clase que gestiona la colocación de palabras en un tablero de juego,
 * verifica si una palabra se puede colocar y actualiza el tablero con las palabras jugadas.
 */
public class ColocarPalabra {
    private int puntosGanados = 0;
    private boolean enlazado = false;

    /**
     * Coloca una palabra en el tablero si es válido y actualiza el puntaje del jugador.
     *
     * @param palabra La palabra que se va a colocar en el tablero.
     * @param fila La fila donde se iniciará la colocación de la palabra.
     * @param columna La columna donde se iniciará la colocación de la palabra.
     * @param horizontal Indica si la palabra se coloca horizontalmente (true) o verticalmente (false).
     * @param jugador El jugador que está colocando la palabra.
     * @param tablero El tablero donde se va a colocar la palabra.
     * @param tableroVacio Indica si el tablero está vacío (para un primer movimiento).
     * @return true si la palabra se puede colocar correctamente, false en caso contrario.
     */
    public boolean colocarPalabra(String palabra, int fila, int columna, boolean horizontal, Jugador jugador, Character[][] tablero, boolean tableroVacio) {
        ArrayList<Character> fichasUsadas = new ArrayList<>();
        boolean canPlace = canPlaceWord(palabra, fila, columna, jugador.getPlayerCharacters(), horizontal, tablero, tableroVacio);
        if (canPlace) {
            actualizarTablero(palabra, fila, columna, horizontal, tablero, jugador.getPlayerCharacters(), fichasUsadas);
            jugador.getPlayerCharacters().getFichas().removeAll(fichasUsadas);
            jugador.addPoints(puntosGanados);
            jugador.addWordPlayed();
        } else {
            System.out.println("La palabra \"" + palabra + "\" no puede ser colocada en el tablero.");
        }
        return canPlace;
    }

    /**
     * Verifica si una palabra puede ser colocada en el tablero en la posición especificada.
     *
     * @param word La palabra que se va a colocar.
     * @param row La fila donde se quiere colocar la palabra.
     * @param col La columna donde se quiere colocar la palabra.
     * @param fichas Las fichas que el jugador tiene disponibles para usar.
     * @param horizontal Indica si la palabra se colocará horizontalmente o verticalmente.
     * @param board El tablero donde se intenta colocar la palabra.
     * @param tableroVacio Indica si el tablero está vacío (para un primer movimiento).
     * @return true si la palabra se puede colocar en el tablero, false en caso contrario.
     */
    public boolean canPlaceWord(String word, int row, int col, Fichas fichas, boolean horizontal, Character[][] board, boolean tableroVacio) {
        int crow, ccol;
        boolean enlazado = false;
        Map<String, Integer> tileCount = new HashMap<>();
        for (Character tile : fichas.getFichas()) {
            tileCount.put(tile.getSymbol(), tileCount.getOrDefault(tile.getSymbol(), 0) + 1);
        }
        int requiredTilesCount = 0;
        for (int i = 0; i < word.length(); ) {
            String tile;
            if (i < word.length() - 1 && (word.charAt(i) == 'L' && word.charAt(i + 1) == 'L')) {
                tile = "LL";
                requiredTilesCount++;
                i += 2;
            } else if (i < word.length() - 1 && (word.charAt(i) == 'R' && word.charAt(i + 1) == 'R')) {
                tile = "RR";
                requiredTilesCount++;
                i += 2;
            } else if (i < word.length() - 1 && (word.charAt(i) == 'C' && word.charAt(i + 1) == 'H')) {
                tile = "CH";
                requiredTilesCount++;
                i += 2;
            } else {
                tile = String.valueOf(word.charAt(i));
                requiredTilesCount++;
                i++;
            }
            if (horizontal) {
                ccol = col + requiredTilesCount - 1;
                crow = row;
            } else {
                crow = row + requiredTilesCount - 1;
                ccol = col;
            }
            if (board[crow][ccol] != null && !board[crow][ccol].getSymbol().equals(tile)) {
                return false;
            }
            if (board[crow][ccol] == null && (tileCount.getOrDefault(tile, 0) <= 0)) {
                return false;
            }
            if (board[crow][ccol] == null) {
                tileCount.put(tile, tileCount.get(tile) - 1);
            } else {
                enlazado = true;
            }
        }
        return tableroVacio || enlazado;
    }

    /**
     * Actualiza el tablero con la palabra colocada por el jugador.
     *
     * @param word La palabra que se va a colocar en el tablero.
     * @param fila La fila donde se colocará la palabra.
     * @param columna La columna donde se colocará la palabra.
     * @param horizontal Indica si la palabra se coloca horizontalmente.
     * @param tablero El tablero que se actualizará con la palabra.
     * @param fichas Las fichas del jugador que se utilizan para la colocación.
     * @param fichasUsadas La lista donde se agregarán las fichas que han sido usadas.
     */
    public void actualizarTablero(String word, int fila, int columna, boolean horizontal, Character[][] tablero, Fichas fichas, ArrayList<Character> fichasUsadas) {
        if (horizontal) {
            for (int i = 0; i < word.length(); ) {
                if (tablero[fila][columna] == null) {
                    String tile;
                    if (i < word.length() - 1 && (word.charAt(i) == 'L' && word.charAt(i + 1) == 'L')) {
                        tile = "LL";
                        Character ficha = fichas.buscar(tile);
                        tablero[fila][columna] = ficha;
                        fichasUsadas.add(ficha);
                        puntosGanados = puntosGanados + ficha.getPoints();
                        columna++;
                        i += 2;
                    } else if (i < word.length() - 1 && (word.charAt(i) == 'R' && word.charAt(i + 1) == 'R')) {
                        tile = "RR";
                        Character ficha = fichas.buscar(tile);
                        tablero[fila][columna] = ficha;
                        fichasUsadas.add(ficha);
                        puntosGanados = puntosGanados + ficha.getPoints();
                        columna++;
                        i += 2;
                    } else if (i < word.length() - 1 && (word.charAt(i) == 'C' && word.charAt(i + 1) == 'H')) {
                        tile = "CH";
                        Character ficha = fichas.buscar(tile);
                        tablero[fila][columna] = ficha;
                        fichasUsadas.add(ficha);
                        puntosGanados = puntosGanados + ficha.getPoints();
                        columna++;
                        i += 2;
                    } else {
                        tile = String.valueOf(word.charAt(i));
                        Character ficha = fichas.buscar(tile);
                        tablero[fila][columna] = ficha;
                        fichasUsadas.add(ficha);
                        puntosGanados = puntosGanados + ficha.getPoints();
                        columna++;
                        i++;
                    }
                } else {
                    String simbolo = tablero[fila][columna].getSymbol();
                    if (simbolo.equals("CH") || simbolo.equals("LL") || simbolo.equals("RR")) {
                        i += 2;
                    } else {
                        i++;
                    }
                    columna++;
                }
            }
        } else {
            for (int i = 0; i < word.length(); ) {
                if (tablero[fila][columna] == null) {
                    String tile;
                    if (i < word.length() - 1 && (word.charAt(i) == 'L' && word.charAt(i + 1) == 'L')) {
                        tile = "LL";
                        Character ficha = fichas.buscar(tile);
                        tablero[fila][columna] = ficha;
                        fichasUsadas.add(ficha);
                        puntosGanados = puntosGanados + ficha.getPoints();
                        fila++;
                        i += 2;
                    } else if (i < word.length() - 1 && (word.charAt(i) == 'R' && word.charAt(i + 1) == 'R')) {
                        tile = "RR";
                        Character ficha = fichas.buscar(tile);
                        tablero[fila][columna] = ficha;
                        fichasUsadas.add(ficha);
                        puntosGanados = puntosGanados + ficha.getPoints();
                        fila++;
                        i += 2;
                    } else if (i < word.length() - 1 && (word.charAt(i) == 'C' && word.charAt(i + 1) == 'H')) {
                        tile = "CH";
                        Character ficha = fichas.buscar(tile);
                        tablero[fila][columna] = ficha;
                        fichasUsadas.add(ficha);
                        puntosGanados = puntosGanados + ficha.getPoints();
                        fila++;
                        i += 2;
                    } else {
                        tile = String.valueOf(word.charAt(i));
                        Character ficha = fichas.buscar(tile);
                        tablero[fila][columna] = ficha;
                        fichasUsadas.add(ficha);
                        puntosGanados = puntosGanados + ficha.getPoints();
                        fila++;
                        i++;
                    }
                } else {
                    String simbolo = tablero[fila][columna].getSymbol();
                    if (simbolo.equals("CH") || simbolo.equals("LL") || simbolo.equals("RR")) {
                        i += 2;
                    } else {
                        i++;
                    }
                    fila++;
                }
            }
        }
    }
}

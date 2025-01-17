package juego;

/**
 * La clase `juego.Tablero` representa el tablero de juego en un juego tipo Scrabble.
 * El tablero tiene un tamaño de 15x15 casillas y permite colocar palabras
 * siguiendo reglas específicas, como el manejo de palabras especiales y
 * la validación de colocación de letras adyacentes.
 */
public class Tablero {
    private static final int TAMANO = 15;
    private static final int MAX_FICHAS = 7;
    private Character[][] tablero;
    private boolean estaVacio;

    /**
     * Constructor de la clase `juego.Tablero`. Inicializa el tablero y lo pone vacío.
     */
    public Tablero() {
        tablero = new Character[TAMANO][TAMANO];
        estaVacio = true;
        inicializarTablero();
    }

    /**
     * Verifica si hay letras en el centro del tablero (casilla [7][7]).
     *
     * @return `true` si hay una letra en el centro del tablero, `false` en caso contrario.
     */
    public boolean hayLetrasEnElCentro() {
        return tablero[7][7] != null;
    }

    /**
     * Obtiene la representación interna del tablero.
     *
     * @return Una matriz de caracteres que representa el tablero.
     */
    public Character[][] getTablero() {
        return tablero;
    }

    /**
     * Inicializa todas las casillas del tablero con `null` (vacías).
     */
    private void inicializarTablero() {
        for (int i = 0; i < TAMANO; i++) {
            for (int j = 0; j < TAMANO; j++) {
                tablero[i][j] = null;
            }
        }
    }

    /**
     * Establece una nueva representación del tablero.
     *
     * @param tablero Una matriz de caracteres que representa el nuevo tablero.
     */
    public void setTablero(Character[][] tablero) {
        this.tablero = tablero;
    }

    /**
     * Obtiene el estado de si el tablero está vacío.
     *
     * @return `true` si el tablero está vacío, `false` si ya se han colocado palabras.
     */
    public boolean isEstaVacio() {
        return estaVacio;
    }

    /**
     * Establece el estado de si el tablero está vacío o no.
     *
     * @param estaVacio `true` si el tablero debe marcarse como vacío, `false` en caso contrario.
     */
    public void setEstaVacio(boolean estaVacio) {
        this.estaVacio = estaVacio;
    }

    /**
     * Muestra el estado actual del tablero en la consola.
     * Imprime una representación visual del tablero con las fichas colocadas.
     */
    public void mostrarTablero() {
        String disp = "";
        System.out.print("   ");
        for (int i = 0; i < TAMANO; i++) {
            System.out.print("%3d ");
        }
        System.out.println();
        for (int i = 0; i < TAMANO; i++) {
            System.out.print("%2d ");
            for (int j = 0; j < TAMANO; j++) {
                if (tablero[i][j] == null) {
                    disp = "   ";
                } else {
                    disp = " " + tablero[i][j].getSymbol() + " ";
                }
                System.out.print("|" + disp);
            }
            System.out.println("|");
        }
    }

    /**
     * Intenta ubicar una palabra en el tablero en la posición especificada.
     *
     * @param palabra La palabra a colocar.
     * @param fila La fila en la que comenzar a colocar la palabra.
     * @param columna La columna en la que comenzar a colocar la palabra.
     * @param horizontal Si la palabra debe colocarse de manera horizontal (`true`) o vertical (`false`).
     * @param jugador El jugador que está intentando colocar la palabra.
     * @return `true` si la palabra se colocó correctamente, `false` si no se pudo colocar.
     */
    public boolean ubicarPalabra(String palabra, int fila, int columna, boolean horizontal, Jugador jugador) {
        boolean result;
        ColocarPalabra colocarPalabra = new ColocarPalabra();
        result = colocarPalabra.colocarPalabra(palabra, fila, columna, horizontal, jugador, this.getTablero(), estaVacio);
        estaVacio = false;
        return result;
    }

    /**
     * Valida si una casilla es válida para colocar una letra del símbolo proporcionado.
     *
     * @param simbolo El símbolo de la letra a colocar.
     * @param fila La fila de la casilla a verificar.
     * @param columna La columna de la casilla a verificar.
     * @param horizontal Si la colocación es horizontal.
     * @param offset El desplazamiento dentro de la palabra.
     * @return `true` si la casilla es válida, `false` si no lo es.
     */
    private boolean validarCasilla(String simbolo, int fila, int columna, boolean horizontal, int offset) {
        int posFila = horizontal ? fila : fila + offset;
        int posColumna = horizontal ? columna + offset : columna;
        if (tablero[posFila][posColumna] == null) return true;
        return tablero[posFila][posColumna].getSymbol().equalsIgnoreCase(simbolo);
    }

    /**
     * Verifica si hay alguna letra adyacente en las casillas vecinas.
     *
     * @param fila La fila de la casilla a verificar.
     * @param columna La columna de la casilla a verificar.
     * @param horizontal Si la colocación es horizontal.
     * @param offset El desplazamiento dentro de la palabra.
     * @return `true` si hay una letra adyacente, `false` si no.
     */
    private boolean hayLetraAdyacente(int fila, int columna, boolean horizontal, int offset) {
        int posFila = horizontal ? fila : fila + offset;
        int posColumna = horizontal ? columna + offset : columna;
        int[][] direcciones = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
        for (int[] dir : direcciones) {
            int adyFila = posFila + dir[0];
            int adyColumna = posColumna + dir[1];

            if (adyFila >= 0 && adyFila < TAMANO && adyColumna >= 0 && adyColumna < TAMANO) {
                if (tablero[adyFila][adyColumna] != null) return true;
            }
        }
        return false;
    }

    /**
     * Verifica si el tablero está vacío (sin fichas colocadas).
     *
     * @return `true` si el tablero está vacío, `false` si tiene fichas colocadas.
     */
    private boolean tableroVacio() {
        for (int i = 0; i < TAMANO; i++) {
            for (int j = 0; j < TAMANO; j++) {
                if (tablero[i][j] != null) return false;
            }
        }
        return true;
    }
}

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * La clase Tablero representa el tablero de juego para un juego de palabras.
 * Contiene una matriz de Strings que representa las posiciones en el tablero y métodos para manipularlo.
 */
class Tablero {
    private String[][] tablero;
    private Saco saco;
    private static final String fondoRojo = "\u001B[48;5;124m"; // Doble palabra
    private static final String textoNegro = "\u001B[30m"; // Texto negro
    private static final String fondoAzull = "\u001B[48;5;33m"; // Triple palabra
    private static final String fondoGris = "\u001B[48;5;250m"; // Fondo gris claro
    private static final String fondoVerde = "\u001B[48;5;22m"; // Doble letra
    private static final String fondoAmarillo = "\u001B[48;5;142m"; // Triple letra
    private static final String reset = "\u001B[0m"; // Restablecer color

    /**
     * Constructor que inicializa el tablero con un saco de letras.
     *
     * @param saco el saco de letras utilizado en el juego.
     */
    public Tablero(Saco saco) {
        this.saco = saco;
        tablero = new String[15][15]; // Tablero de 15x15
        for (int i = 0; i < 15; i++) {
            Arrays.fill(tablero[i], " "); // Inicializa con un espacio en blanco como String
        }
    }

    /**
     * Coloca los multiplicadores de puntos en el tablero.
     */
    public void colocarMultiplicadores() {
        // Doble palabra
        tablero[0][0] = fondoRojo + "2XP" + reset;
        tablero[0][7] = fondoRojo + "2XP" + reset;
        tablero[0][14] = fondoRojo + "2XP" + reset;
        tablero[7][0] = fondoRojo + "2XP" + reset;
        tablero[7][14] = fondoRojo + "2XP" + reset;
        tablero[14][0] = fondoRojo + "2XP" + reset;
        tablero[14][7] = fondoRojo + "2XP" + reset;
        tablero[14][14] = fondoRojo + "2XP" + reset;

        // Triple palabra
        tablero[0][3] = fondoAzull + textoNegro + "3XP" + reset;
        tablero[0][11] = fondoAzull + textoNegro + "3XP" + reset;
        tablero[3][0] = fondoAzull + textoNegro + "3XP" + reset;
        tablero[3][14] = fondoAzull + textoNegro + "3XP" + reset;
        tablero[11][0] = fondoAzull + textoNegro + "3XP" + reset;
        tablero[11][14] = fondoAzull + textoNegro + "3XP" + reset;
        tablero[14][3] = fondoAzull + textoNegro + "3XP" + reset;
        tablero[14][11] = fondoAzull + textoNegro + "3XP" + reset;
        tablero[7][7] = fondoAzull + textoNegro + "3XP" + reset; // Centro

        // Doble letra
        tablero[1][5] = fondoVerde + "2XL" + reset;
        tablero[1][9] = fondoVerde + "2XL" + reset;
        tablero[5][1] = fondoVerde + "2XL" + reset;
        tablero[5][5] = fondoVerde + "2XL" + reset;
        tablero[5][9] = fondoVerde + "2XL" + reset;
        tablero[5][13] = fondoVerde + "2XL" + reset;
        tablero[9][1] = fondoVerde + "2XL" + reset;
        tablero[9][5] = fondoVerde + "2XL" + reset;
        tablero[9][9] = fondoVerde + "2XL" + reset;
        tablero[9][13] = fondoVerde + "2XL" + reset;
        tablero[13][5] = fondoVerde + "2XL" + reset;
        tablero[13][9] = fondoVerde + "2XL" + reset;

        // Triple letra
        tablero[1][1] = fondoAmarillo + textoNegro + "3XL" + reset;
        tablero[1][13] = fondoAmarillo + textoNegro + "3XL" + reset;
        tablero[13][1] = fondoAmarillo + textoNegro + "3XL" + reset;
        tablero[13][13] = fondoAmarillo + textoNegro + "3XL" + reset;
        tablero[5][5] = fondoAmarillo + textoNegro + "3XL" + reset;
        tablero[5][9] = fondoAmarillo + textoNegro + "3XL" + reset;
        tablero[9][5] = fondoAmarillo + textoNegro + "3XL" + reset;
        tablero[9][9] = fondoAmarillo + textoNegro + "3XL" + reset;
    }


    /**
     * Coloca una palabra en el tablero y calcula el puntaje obtenido.
     *
     * @param palabra    la palabra a colocar en el tablero.
     * @param fila       la fila inicial donde se colocará la palabra.
     * @param col        la columna inicial donde se colocará la palabra.
     * @param horizontal indica si la palabra se coloca horizontalmente (true) o verticalmente (false).
     * @param jugador    el jugador que coloca la palabra.
     * @param turno      el número de turno actual del juego.
     * @return el puntaje obtenido al colocar la palabra, o 0 si la colocación no es válida.
     */
    public int colocarPalabra(String palabra, int fila, int col, boolean horizontal, Jugador jugador, int turno) {
        int puntaje = 0;
        int multiplicadorPalabra = 1; // Multiplicador para el puntaje total de la palabra
        List<String> letrasUsadas = new ArrayList<>();

        // Validar límites del tablero
        if (fila < 0 || fila >= 15 || col < 0 || col >= 15) return 0;

        palabra = palabra.toUpperCase(); // Convertir palabra a mayúsculas para consistencia

        boolean cruzaConOtraPalabra = false;

        // Verificar si la palabra cruza con otra ya existente a partir del turno 2
        if (turno > 0) {
            if (horizontal) {
                for (int i = 0; i < palabra.length(); i++) {
                    int filaActual = fila;
                    int colActual = col + i;

                    if (colActual >= 15) return 0; // Verificar que no salga del tablero

                    String casilla = tablero[filaActual][colActual];
                    if (!casilla.trim().isEmpty() && !casilla.contains("XP") && !casilla.contains("XL")) {
                        cruzaConOtraPalabra = true; // Hay intersección con otra palabra
                    }

                    // Verificar celdas adyacentes
                    if ((filaActual > 0 && !tablero[filaActual - 1][colActual].trim().isEmpty() && !tablero[filaActual - 1][colActual].contains("XP") && !tablero[filaActual - 1][colActual].contains("XL")) ||
                            (filaActual < 14 && !tablero[filaActual + 1][colActual].trim().isEmpty() && !tablero[filaActual + 1][colActual].contains("XP") && !tablero[filaActual + 1][colActual].contains("XL"))) {
                        cruzaConOtraPalabra = true;
                    }
                }
            } else { // Vertical
                for (int i = 0; i < palabra.length(); i++) {
                    int filaActual = fila + i;
                    int colActual = col;

                    if (filaActual >= 15) return 0; // Verificar que no salga del tablero

                    String casilla = tablero[filaActual][colActual];
                    if (!casilla.trim().isEmpty() && !casilla.contains("XP") && !casilla.contains("XL")) {
                        cruzaConOtraPalabra = true; // Hay intersección con otra palabra
                    }

                    // Verificar celdas adyacentes
                    if ((colActual > 0 && !tablero[filaActual][colActual - 1].trim().isEmpty() && !tablero[filaActual][colActual - 1].contains("XP") && !tablero[filaActual][colActual - 1].contains("XL")) ||
                            (colActual < 14 && !tablero[filaActual][colActual + 1].trim().isEmpty() && !tablero[filaActual][colActual + 1].contains("XP") && !tablero[filaActual][colActual + 1].contains("XL"))) {
                        cruzaConOtraPalabra = true;
                    }
                }
            }

            if (!cruzaConOtraPalabra) return 0; // Si no cruza con ninguna palabra, la colocación es inválida
        }

        // Verificar límites y colisiones, luego colocar la palabra
        try {
            if (horizontal) {
                if (col + palabra.length() > 15) return 0;

                for (int i = 0; i < palabra.length(); ) {
                    String letraActual = String.valueOf(palabra.charAt(i));
                    String casillaActual = tablero[fila][col + i];

                    // Verificar si la letra actual es parte de una combinación doble
                    if (i < palabra.length() - 1 && esCombinacionDoble(palabra.substring(i, i + 2))) {
                        letraActual = palabra.substring(i, i + 2); // Tomar la combinación doble
                        i += 2; // Saltar la letra que sigue a la combinación
                    } else {
                        i++; // Incrementar el índice en 1
                    }

                    // Verificar colisiones
                    if (!casillaActual.trim().isEmpty() &&
                            !casillaActual.equals(letraActual) &&
                            !casillaActual.contains("XP") &&
                            !casillaActual.contains("XL")) {
                        return 0;
                    }

                    int puntajeLetra = saco.obtenerPuntajeDeLaLetra(letraActual);
                    if (casillaActual.contains("3XL")) puntajeLetra *= 3;
                    else if (casillaActual.contains("2XL")) puntajeLetra *= 2;
                    else if (casillaActual.contains("3XP")) multiplicadorPalabra *= 3;
                    else if (casillaActual.contains("2XP")) multiplicadorPalabra *= 2;

                    tablero[fila][col + (i - 1)] = letraActual; // Colocar la letra o combinación en el tablero
                    puntaje += puntajeLetra;
                    letrasUsadas.add(letraActual);
                }
            } else { // Vertical
                if (fila + palabra.length() > 15) return 0;

                for (int i = 0; i < palabra.length(); ) {
                    String letraActual = String.valueOf(palabra.charAt(i));
                    String casillaActual = tablero[fila + i][col];

                    // Verificar si la letra actual es parte de una combinación doble
                    if (i < palabra.length() - 1 && esCombinacionDoble(palabra.substring(i, i + 2))) {
                        letraActual = palabra.substring(i, i + 2); // Tomar la combinación doble
                        i += 2; // Saltar la letra que sigue a la combinación
                    } else {
                        i++; // Incrementar el índice en 1
                    }

                    // Verificar colisiones
                    if (!casillaActual.trim().isEmpty() &&
                            !casillaActual.equals(letraActual) &&
                            !casillaActual.contains("XP") &&
                            !casillaActual.contains("XL")) {
                        return 0;
                    }

                    int puntajeLetra = saco.obtenerPuntajeDeLaLetra(letraActual);
                    if (casillaActual.contains("3XL")) puntajeLetra *= 3;
                    else if (casillaActual.contains("2XL")) puntajeLetra *= 2;
                    else if (casillaActual.contains("3XP")) multiplicadorPalabra *= 3;
                    else if (casillaActual.contains("2XP")) multiplicadorPalabra *= 2;

                    tablero[fila + (i - 1)][col] = letraActual; // Colocar la letra o combinación en el tablero
                    puntaje += puntajeLetra;
                    letrasUsadas.add(letraActual);
                }
            }
        } catch (Exception e) {
            return 0;
        }

        puntaje *= multiplicadorPalabra; // Aplicar multiplicador de palabra
        return puntaje;
    }



    /**
     * Verifica si una combinación de letras es una combinación doble (CH, LL, RR).
     *
     * @param combinacion la combinación de letras a verificar.
     * @return true si la combinación es doble, false en caso contrario.
     */
    private boolean esCombinacionDoble(String combinacion) {
        return combinacion.equals("CH") || combinacion.equals("LL") || combinacion.equals("RR");
    }


    public String[][] getTablero() {
        return this.tablero;
    }

    /**
     * Muestra el tablero de juego con los colores y los multiplicadores correspondientes.
     */
    public void mostrarTableroConColores() {
        // Imprimir encabezado de columnas
        System.out.printf("%s%s  . %s", fondoGris, textoNegro, reset);
        for (int col = 0; col < tablero[0].length; col++) {
            System.out.printf("|%s%2d %s", fondoGris + textoNegro, col, reset); // Coordenadas en gris con texto negro
        }
        System.out.println("|");
        System.out.println("-----------------------------------------------------------------");

        // Imprimir filas del tablero
        for (int fila = 0; fila < tablero.length; fila++) {
            System.out.printf("%s %s%2d %s|", fondoGris, textoNegro, fila, reset); // Coordenadas en gris
            for (int col = 0; col < tablero[fila].length; col++) {
                String letra = tablero[fila][col];

                // No aplicar fondo a las letras del tablero
                System.out.printf("%s", centrarTexto(letra, 3)); // Solo texto sin fondo
                if (col < tablero[fila].length - 1) {
                    System.out.print("|"); // Separador entre columnas
                }
            }
            System.out.println("|");
            // Imprimir línea divisoria
            System.out.println("-----------------------------------------------------------------");
        }
        System.out.println("3X= x3 en letra(L) o palabra(P)      2X= x2 en letra(L) o palabra(P)");
    }

    /**
     * Centra el texto dentro de un ancho especificado.
     *
     * @param texto el texto a centrar.
     * @param ancho el ancho en el que se centrará el texto.
     * @return el texto centrado.
     */
    public String centrarTexto(String texto, int ancho) {
        if (texto.trim().isEmpty()) {
            return " ".repeat(ancho);
        }
        if (ancho < texto.length()) {
            return texto; // Si el ancho es menor que el texto, devolver el texto sin centrar
        }
        int espaciosIzquierda = (ancho - texto.length()) / 2;
        int espaciosDerecha = ancho - texto.length() - espaciosIzquierda;
        return " ".repeat(espaciosIzquierda) + texto + " ".repeat(espaciosDerecha);
    }

    /**
     * Limpia el tablero, llenándolo con espacios en blanco.
     */
    public void limpiarTablero() {
        for (int i = 0; i < 15; i++) {
            Arrays.fill(tablero[i], " ");
        }
    }
}

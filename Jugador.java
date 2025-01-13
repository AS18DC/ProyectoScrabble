import java.util.*;

/**
 * Clase que representa a un jugador en el juego de Scrabble.
 */
class Jugador {
    private String nombre;
    private String correoElectronico;
    private int puntaje;
    private int puntajePartida;
    private List<String> letras;

    /**
     * Constructor de la clase Jugador.
     *
     * @param correoElectronico El correo electrónico del jugador.
     * @param nombre            El nombre del jugador.
     */
    public Jugador(String correoElectronico, String nombre) {
        this.correoElectronico = correoElectronico;
        this.nombre = nombre;
        this.puntaje = 0;
        this.letras = new ArrayList<String>();
    }

    /**
     * Obtiene el nombre del jugador.
     *
     * @return El nombre del jugador.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Obtiene el puntaje de la partida actual del jugador.
     *
     * @return El puntaje de la partida actual.
     */
    public int getPuntajePartida() {
        return puntajePartida;
    }

    /**
     * Establece el puntaje de la partida actual del jugador.
     *
     * @param puntajePartida El nuevo puntaje de la partida.
     */
    public void setPuntajePartida(int puntajePartida) {
        this.puntajePartida = puntajePartida;
    }

    /**
     * Obtiene el puntaje total del jugador.
     *
     * @return El puntaje total.
     */
    public int getPuntaje() {
        return puntaje;
    }

    /**
     * Obtiene el correo electrónico del jugador.
     *
     * @return El correo electrónico del jugador.
     */
    public String getCorreoElectronico() {
        return correoElectronico;
    }

    /**
     * Establece el correo electrónico del jugador.
     *
     * @param correoElectronico El nuevo correo electrónico del jugador.
     */
    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    /**
     * Establece el nombre del jugador.
     *
     * @param nombre El nuevo nombre del jugador.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene las letras que el jugador tiene.
     *
     * @return Las letras del jugador.
     */
    public List<String> getLetras() {
        return letras;
    }

    /**
     * Establece las letras del jugador.
     *
     * @param letras Las nuevas letras del jugador.
     */
    public void setLetras(List<String> letras) {
        this.letras = letras;
    }

    /**
     * Agrega una letra al conjunto de letras del jugador y actualiza el puntaje.
     *
     * @param letra   La letra a agregar.
     * @param puntaje El puntaje de la letra a agregar.
     */
    public void agregarLetra(String letra, int puntaje) {
        if (letras.size() < 7) {
            letras.add(letra);
            this.puntaje += puntaje;
        }
    }

    /**
     * Quita una letra del conjunto de letras del jugador.
     *
     * @param letra La letra a quitar.
     * @return true si la letra fue quitada, false en caso contrario.
     */
    public boolean quitarLetra(String letra) {
        return letras.remove(letra);
    }

    /**
     * Verifica si el jugador puede formar una palabra con sus letras y las letras en el tablero.
     *
     * @param palabra La palabra a verificar.
     * @param tablero El tablero de Scrabble.
     * @return true si el jugador puede formar la palabra, false en caso contrario.
     */
    public boolean verificarLetrasParaPalabra(String palabra, Tablero tablero) {
        Map<String, Integer> letrasEnMano = new HashMap<>();
        for (String letra : letras) {
            letrasEnMano.put(letra, letrasEnMano.getOrDefault(letra, 0) + 1);
        }

        Map<String, Integer> letrasEnTablero = new HashMap<>();
        String[][] matrizTablero = tablero.getTablero();

        for (int fila = 0; fila < matrizTablero.length; fila++) {
            for (int col = 0; col < matrizTablero[fila].length; col++) {
                String letraEnTablero = matrizTablero[fila][col].trim();
                if (!letraEnTablero.isEmpty() && !letraEnTablero.contains("XP") && !letraEnTablero.contains("XL")) {
                    letrasEnTablero.put(letraEnTablero, letrasEnTablero.getOrDefault(letraEnTablero, 0) + 1);
                }
            }
        }

        boolean tieneLetraEnMano = false;
        for (int i = 0; i < palabra.length(); i++) {
            String letra = palabra.substring(i, Math.min(i + 2, palabra.length())).toUpperCase();
            if (letrasEnMano.getOrDefault(letra, 0) > 0) {
                tieneLetraEnMano = true;
                break;
            }
            letra = String.valueOf(palabra.charAt(i)).toUpperCase();
            if (letrasEnMano.getOrDefault(letra, 0) > 0) {
                tieneLetraEnMano = true;
                break;
            }
        }

        if (!tieneLetraEnMano) {
            return false;
        }

        for (int i = 0; i < palabra.length(); i++) {
            String letra = palabra.substring(i, Math.min(i + 2, palabra.length())).toUpperCase();
            if (letrasEnMano.getOrDefault(letra, 0) > 0) {
                letrasEnMano.put(letra, letrasEnMano.get(letra) - 1);
                i++;
            } else {
                letra = String.valueOf(palabra.charAt(i)).toUpperCase();
                if (letrasEnMano.getOrDefault(letra, 0) > 0) {
                    letrasEnMano.put(letra, letrasEnMano.get(letra) - 1);
                } else if (letrasEnTablero.getOrDefault(letra, 0) > 0) {
                    letrasEnTablero.put(letra, letrasEnTablero.get(letra) - 1);
                } else {
                    return false;
                }
            }
        }

        return true;
    }

    /**
     * Usa una letra del conjunto de letras del jugador.
     *
     * @param letra La letra a usar.
     * @return true si la letra fue usada correctamente, false en caso contrario.
     */
    public boolean usarLetra(String letra) {
        // Revisa si la letra existe en las letras del jugador
        if (letras.contains(letra)) {
            letras.remove(letra); // Elimina solo la primera coincidencia
            return true; // Indica que la letra fue usada correctamente
        }
        return false; // Indica que la letra no estaba disponible
    }

    /**
     * Usa el conjunto de letras del jugador.
     *
     * @param usadas Las letras a usar.
     */
    public void usarLetras(List<String> usadas) {
        for (String letra : usadas) {
            // Eliminar la letra del jugador
            if (letras.contains(letra)) {
                letras.remove(letra);
            }
        }
    }
}

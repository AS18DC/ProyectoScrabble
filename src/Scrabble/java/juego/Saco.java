package juego;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.ArrayList;
import java.util.Random;

/**
 * Clase que representa una bolsa de fichas con caracteres específicos para un juego de Scrabble
 * en español. Esta bolsa contiene una lista de caracteres con sus respectivos valores y se
 * encarga de proporcionar fichas a los jugadores de manera aleatoria.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Saco implements CaracteresSaco {

    /**
     * Lista de caracteres que representan las fichas disponibles en la bolsa.
     */
    private ArrayList<Character> characterList;

    /**
     * Constructor que inicializa la lista de caracteres y llena la bolsa con las fichas.
     */
    public Saco() {
        characterList = new ArrayList<>();
        addAll();
    }

    /**
     * Obtiene la lista de caracteres disponibles en la bolsa.
     *
     * @return La lista de caracteres.
     */
    public ArrayList<Character> getCharacterList() {
        return characterList;
    }

    /**
     * Obtiene el número de caracteres disponibles en la bolsa.
     *
     * @return El número de caracteres en la bolsa.
     */
    public int numberOfCharacters() {
        return characterList.size();
    }

    /**
     * Establece una nueva lista de caracteres para la bolsa.
     *
     * @param characterList La nueva lista de caracteres.
     */
    public void setCharacterList(ArrayList<Character> characterList) {
        this.characterList = characterList;
    }

    /**
     * Extrae un número específico de caracteres de la bolsa.
     * Si la bolsa contiene menos caracteres de los solicitados, se devuelven todos los caracteres disponibles.
     *
     * @param n El número de caracteres a extraer.
     * @return Una lista con los caracteres extraídos.
     */
    @Override
    public ArrayList<Character> get(int n) {
        ArrayList<Character> extractedCharacters = new ArrayList<>();
        if (characterList.size() >= n) {
            for (int i = 0; i != n; i++) {
                extractCharacters(extractedCharacters);
            }
        } else {
            while (!characterList.isEmpty()) {
                extractCharacters(extractedCharacters);
            }
        }
        return extractedCharacters;
    }

    /**
     * Reinicia la bolsa de caracteres, limpiando la lista y rellenándola con las fichas iniciales.
     */
    @Override
    public void reset() {
        characterList.clear();
        addAll();
    }

    /**
     * Extrae un carácter aleatorio de la bolsa y lo agrega a la lista proporcionada.
     *
     * @param list La lista a la que se añadirá el carácter extraído.
     */
    private void extractCharacters(ArrayList<Character> list) {
        Random random = new Random();
        int randomIndex = random.nextInt(characterList.size());
        Character randomItem = characterList.get(randomIndex);
        list.add(randomItem);
        characterList.remove(randomItem);
    }

    /**
     * Agrega un número específico de caracteres a la bolsa.
     *
     * @param symbol El símbolo del carácter.
     * @param point El valor del carácter.
     * @param times El número de veces que se debe agregar el carácter a la bolsa.
     */
    private void add(String symbol, int point, int times) {
        for (int i = 0; i < times; i++) {
            Character character = new Character(symbol, point);
            characterList.add(character);
        }
    }

    /**
     * Agrega todos los caracteres necesarios para la bolsa del juego en español.
     */
    private void addAll() {
        add("A", 1, 12);
        add("B", 4, 4);
        add("C", 3, 4);
        add("CH", 8, 1);
        add("D", 3, 5);
        add("E", 1, 12);
        add("F", 5, 1);
        add("G", 3, 2);
        add("H", 5, 2);
        add("I", 1, 6);
        add("J", 10, 1);
        add("L", 2, 4);
        add("LL", 8, 1);
        add("M", 3, 2);
        add("N", 2, 5);
        add("Ñ", 10, 1);
        add("O", 1, 9);
        add("P", 4, 2);
        add("Q", 8, 1);
        add("R", 2, 5);
        add("RR", 8, 1);
        add("S", 1, 6);
        add("T", 1, 4);
        add("U", 1, 5);
        add("V", 4, 1);
        add("X", 10, 1);
        add("Y", 5, 1);
        add("Z", 10, 1);
    }

    /**
     * Devuelve una representación en forma de cadena de la bolsa, incluyendo los caracteres disponibles.
     *
     * @return La cadena representando la bolsa de caracteres.
     */
    @Override
    public String toString() {
        return "juego.SpanishBag{"
                + "characterList=" + characterList
                + '}';
    }

    /**
     * Obtiene el número de caracteres restantes en la bolsa.
     *
     * @return El número de caracteres restantes.
     */
    @Override
    public int remaning() {
        return characterList.size();
    }

    /**
     * Restaura los caracteres a la bolsa, agregando una lista de caracteres proporcionada.
     *
     * @param characterList La lista de caracteres a agregar a la bolsa.
     */
    @Override
    public void reponer(ArrayList<Character> characterList) {
        this.characterList.addAll(characterList);
    }
}

package juego;

import java.util.ArrayList;

/**
 * Clase que gestiona las fichas de un jugador en el juego. Permite agregar, reemplazar, buscar,
 * y realizar operaciones relacionadas con las fichas del jugador, incluyendo la gestión de comodines.
 */
public class Fichas {
    private ArrayList<Character> fichas;

    /**
     * Constructor de la clase. Inicializa la lista de fichas del jugador.
     */
    public Fichas() {
        fichas = new ArrayList<>();
    }

    /**
     * Establece un comodín representado por el símbolo "+" en la lista de fichas del jugador,
     * reemplazándolo por el símbolo proporcionado.
     *
     * @param letra El símbolo que reemplazará al comodín "+".
     */
    public void setComodin(String letra){
        for ( Character ficha : fichas){
            String symbol = ficha.getSymbol();
            if (symbol.equalsIgnoreCase("+")){
                ficha.setSymbol(letra);
                break;
            }
        }
    }

    /**
     * Obtiene la lista de fichas del jugador.
     *
     * @return Lista de fichas del jugador.
     */
    public ArrayList<Character> getFichas() {
        return fichas;
    }

    /**
     * Establece una nueva lista de fichas para el jugador.
     *
     * @param fichas La nueva lista de fichas.
     */
    public void setFichas(ArrayList<Character> fichas) {
        this.fichas = fichas;
    }

    /**
     * Verifica si el jugador tiene un comodín (símbolo "+") en su conjunto de fichas.
     *
     * @return true si el jugador tiene un comodín, false en caso contrario.
     */
    public boolean existeComodin(){
        for ( Character ficha : fichas){
            String symbol = ficha.getSymbol();
            if (symbol.equalsIgnoreCase("+")) return true;
        }
        return false;
    }

    /**
     * Agrega una ficha al conjunto de fichas del jugador.
     *
     * @param ficha La ficha que se desea agregar.
     */
    public void agregarFicha(Character ficha) {
        fichas.add(ficha);
    }

    /**
     * Busca una ficha en el conjunto del jugador por su símbolo.
     *
     * @param simbolo El símbolo de la ficha a buscar.
     * @return La ficha encontrada, o null si no se encuentra.
     */
    public Character buscar(String simbolo) {
        Character result = null;
        for (Character c : fichas) {
            if (c.getSymbol().equalsIgnoreCase(simbolo)) {
                result = c;
                break;
            }
        }
        return result;
    }

    /**
     * Reemplaza un conjunto de fichas del jugador utilizando las fichas de la bolsa de fichas (juego.CharactersBag).
     * Si no hay suficientes fichas en la bolsa, no se realiza el reemplazo.
     *
     * @param fichasPorExtraer Cadena de texto con los símbolos de las fichas a reemplazar, separados por comas.
     * @param bag La bolsa de fichas del juego.
     * @return true si se realiza el reemplazo con éxito, false si no hay suficientes fichas en la bolsa.
     */
    public boolean reemplazarFichas(String fichasPorExtraer, CaracteresSaco bag) {
        String[] porExtraer = fichasPorExtraer.split(",");
        if (porExtraer.length > bag.remaning()) {
            return false;
        }
        ArrayList<Character> extraidas = extraerFichas(fichasPorExtraer);
        if (extraidas.isEmpty()) {
            return false;
        }
        fichas.addAll(bag.get(extraidas.size()));
        bag.reponer(extraidas);
        return true;
    }

    /**
     * Extrae un conjunto de fichas del jugador basado en los símbolos proporcionados.
     *
     * @param fichasPorExtraer Cadena de texto con los símbolos de las fichas a extraer, separados por comas.
     * @return Lista de las fichas extraídas.
     */
    public ArrayList<Character> extraerFichas(String fichasPorExtraer) {
        ArrayList<Character> extraidas = new ArrayList<>();
        String[] porExtraer = fichasPorExtraer.split(",");
        Character c;
        for (String ficha : porExtraer) {
            c = buscar(ficha);
            if (c != null) {
                fichas.remove(c);
                extraidas.add(c);
            }
            else {
                fichas.addAll(extraidas);
                extraidas.clear();
            }
        }
        return extraidas;
    }

    /**
     * Obtiene la cantidad total de fichas que tiene el jugador.
     *
     * @return El número de fichas del jugador.
     */
    public int cantidadDeFichas(){
        return fichas.size();
    }

    /**
     * Reemplaza las fichas del jugador con una lista proporcionada de fichas.
     *
     * @param characterList La lista de fichas a agregar al conjunto del jugador.
     */
    public void reponer(ArrayList<Character> characterList) {
        fichas.addAll(characterList);
    }

    /**
     * Verifica si el jugador tiene una ficha con el símbolo dado.
     *
     * @param simbolo El símbolo de la ficha a buscar.
     * @return true si el jugador tiene la ficha, false en caso contrario.
     */
    public boolean existeCaracterEnJugador(String simbolo) {
        boolean result = false;
        for (Character c : fichas) {
            if (c.getSymbol().equalsIgnoreCase(simbolo)) {
                result = true;
                break;
            }
        }
        return result;
    }

    /**
     * Devuelve una representación en cadena del conjunto de fichas del jugador, separadas por comas.
     *
     * @return La representación en cadena de las fichas del jugador.
     */
    @Override
    public String toString() {
        String result = "";
        String separador = "";
        for (Character c : fichas) {
            result = result + separador + c.getSymbol();
            separador = ",";
        }
        return result;
    }
}

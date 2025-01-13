import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

/**
 * La clase Saco representa un saco de letras utilizado en un juego.
 * Permite gestionar las letras disponibles y su distribución entre los jugadores.
 */
class Saco {
    private List<Letra> letras;

    /**
     * Constructor que inicializa el saco con las letras del juego y sus cantidades.
     */
    public Saco() {
        letras = new ArrayList<>();
        letras.add(new Letra("A", 1, 12));
        letras.add(new Letra("B", 4, 2));
        letras.add(new Letra("C", 3, 4));
        letras.add(new Letra("CH", 8, 1));
        letras.add(new Letra("D", 3, 5));
        letras.add(new Letra("E", 1, 12));
        letras.add(new Letra("F", 5, 1));
        letras.add(new Letra("G", 3, 2));
        letras.add(new Letra("H", 5, 2));
        letras.add(new Letra("I", 1, 6));
        letras.add(new Letra("J", 10, 1));
        letras.add(new Letra("L", 2, 4));
        letras.add(new Letra("LL", 8, 1));
        letras.add(new Letra("M", 3, 2));
        letras.add(new Letra("N", 2, 5));
        letras.add(new Letra("Ñ", 10, 1));
        letras.add(new Letra("O", 1, 9));
        letras.add(new Letra("P", 4, 2));
        letras.add(new Letra("Q", 8, 1));
        letras.add(new Letra("R", 2, 5));
        letras.add(new Letra("RR", 8, 1));
        letras.add(new Letra("S", 1, 6));
        letras.add(new Letra("T", 2, 4));
        letras.add(new Letra("U", 1, 5));
        letras.add(new Letra("V", 4, 1));
        letras.add(new Letra("X", 10, 1));
        letras.add(new Letra("Y", 5, 1));
        letras.add(new Letra("Z", 10, 1));
        letras.add(new Letra("-", 0, 2));

    }

    /**
     * Devuelve la lista de letras disponibles en el saco.
     *
     * @return la lista de letras disponibles.
     */
    public List<Letra> getLetras() {
        return letras;
    }

    /**
     * Elimina todas las letras del jugador.
     *
     * @param jugador el jugador del que se van a eliminar las letras.
     */
    public void quitarLetrasJugador(Jugador jugador) {
        jugador.getLetras().clear();
    }

    /**
     * Agrega una letra al saco incrementando su cantidad.
     *
     * @param letra la letra a agregar.
     */
    public void agregarLetra(String letra) {
        for (Letra l : letras) {
            if (l.letra.equals(letra)) {
                l.cantidad++; // Incrementar la cantidad de la letra
                return;
            }
        }
    }

    /**
     * Devuelve las letras del jugador al saco, incrementando sus cantidades correspondientes.
     *
     * @param jugador el jugador cuyas letras se van a devolver al saco.
     */
    public void devolverLetrasAlSaco(Jugador jugador) {
        List<String> letrasJugador = new ArrayList<>(jugador.getLetras());
        for (String letra : letrasJugador) {
            boolean letraEncontrada = false;
            for (Letra letraSaco : letras) {
                if (letraSaco.letra.equals(letra)) {
                    letraSaco.cantidad++; // Incrementa la cantidad independientemente de su valor actual
                    letraEncontrada = true;
                    break;
                }
            }

            // Si la letra no se encontró, significa que probablemente ya no estaba en el saco
            if (!letraEncontrada) {
                // Volver a agregar la letra al saco
                letras.add(new Letra(letra, obtenerPuntajeDeLaLetra(letra), 1));
            }
        }
        quitarLetrasJugador(jugador);
    }

    /**
     * Obtiene el puntaje de una letra específica.
     *
     * @param letra la letra cuyo puntaje se va a obtener.
     * @return el puntaje de la letra.
     */
    public int obtenerPuntajeDeLaLetra(String letra) {
        if (letra.length() == 2) {
            // Puntajes para combinaciones dobles
            if (letra.equals("CH")) return 8;
            else if (letra.equals("LL")) return 8;
            else if (letra.equals("RR")) return 8;
            else return 0; // Combinación no válida
        } else {
            // Puntajes para letras individuales
            switch (letra) {
                case "A":
                case "E":
                case "I":
                case "O":
                case "U":
                case "S":
                    return 1;

                case "L":
                case "N":
                case "R":
                case "T":
                    return 2;

                case "C":
                case "D":
                case "G":
                case "M":
                    return 3;

                case "V":
                case "B":
                case "P":
                    return 4;

                case "F":
                case "H":
                case "Y":
                    return 5;

                case "Q":
                    return 8;

                case "J":
                case "Ñ":
                case "X":
                case "Z":
                    return 10;

                case "-":
                    return 0;

                default:
                    return 0; // Letra no válida
            }
        }
    }

    /**
     * Reparte una cantidad específica de letras a un jugador.
     *
     * @param jugador el jugador que recibirá las letras.
     * @param cantidad la cantidad de letras a repartir.
     */
    public void repartirLetras(Jugador jugador, int cantidad) {
        Random rand = new Random();
        int letrasRepartidas = 0;

        // Calcular cuántas letras realmente necesita para llegar a 7
        int letrasFaltantes = 7 - jugador.getLetras().size();
        cantidad = Math.min(cantidad, letrasFaltantes);

        // Repartir letras hasta alcanzar la cantidad deseada o hasta que se agoten las letras
        while (letrasRepartidas < cantidad && !letras.isEmpty()) {
            int index = rand.nextInt(letras.size());
            Letra letra = letras.get(index);

            // Verificar si hay letras disponibles
            if (letra.cantidad > 0) {
                // Agregar la letra al jugador
                jugador.agregarLetra(letra.letra, letra.puntaje);
                letra.cantidad--;
                letrasRepartidas++;
            }

            // Si la letra se ha agotado, eliminarla de la lista
            if (letra.cantidad == 0) {
                letras.remove(index);
            }
        }

        // Mensaje si no se pudieron repartir todas las letras solicitadas
        if (letrasRepartidas < cantidad) {
            System.out.println("No hay suficientes letras disponibles en el saco para repartir " + cantidad + " letras.");
        }
    }


    /**
     * Compara las letras de dos listas y determina cuál está más cerca de la letra 'A'.
     *
     * @param letra1 la primera lista de letras.
     * @param letra2 la segunda lista de letras.
     * @return true si la primera letra está más cerca de 'A', false en caso contrario.
     */
    public boolean letraMasCercanaA(List<String> letra1, List<String> letra2) {
        String primer = letra1.get(0);
        String segundo = letra2.get(0);
        char primerCaracter1 = primer.charAt(0);
        char primerCaracter2 = segundo.charAt(0);
        int distancia1 = Math.abs(primerCaracter1 - 'A');
        int distancia2 = Math.abs(primerCaracter2 - 'A');

        char Ñ = 13;
        if (primerCaracter1 == Ñ) {
            distancia1 = Ñ;
        } else if (primerCaracter2 == Ñ) {
            distancia2 = Ñ;
        }

        if ((primerCaracter1 == '-')) {
            return true;
        }else if ((primerCaracter2 == '-')) {
            return false;
        }


        if (distancia2 < distancia1) {
            return false;
        }else if (distancia1 < distancia2) {
            return true;
        } else {
            System.out.println("Las dos letras son iguales, se va a volver a sortear: ");
            return true;
        }
    }


    /**
     * Cambia las fichas del jugador con nuevas fichas del saco.
     *
     * @param jugador el jugador que quiere cambiar sus fichas.
     * @return true si el cambio se realizó con éxito, false si no hay suficientes letras en el saco.
     */
    public boolean cambiarFichas(Jugador jugador) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Letras disponibles del jugador: " + jugador.getLetras());

        List<String> letrasACambiar = new ArrayList<>();
        String letraACambiar;

        while (true) {
            System.out.println("Ingrese la letra a cambiar (o 'salir' para terminar): ");
            letraACambiar = scanner.nextLine();

            if (letraACambiar.equalsIgnoreCase("salir")) {
                break;
            }

            if (jugador.getLetras().contains(letraACambiar)) {
                letrasACambiar.add(letraACambiar);
            } else {
                System.out.println("El jugador no tiene la letra: " + letraACambiar);
            }
        }

        if (letras.isEmpty()) {
            System.out.println("No hay letras disponibles en el saco para cambiar.");
            return false; // No se puede realizar el cambio
        }

        // Cambiar las letras seleccionadas
        for (String letra : letrasACambiar) {
            // Devolver la letra al saco
            boolean letraEncontrada = false;
            for (Letra letraSaco : letras) {
                if (letraSaco.letra.equals(letra)) {
                    letraSaco.cantidad++; // Incrementa la cantidad
                    letraEncontrada = true;
                    break;
                }
            }

            // Si la letra no se encontró en el saco, agregarla
            if (!letraEncontrada) {
                letras.add(new Letra(letra, obtenerPuntajeDeLaLetra(letra), 1));
            }

            // Quitar la letra del jugador
            jugador.quitarLetra(letra);

            // Obtener una letra aleatoria del saco
            Random random = new Random();
            Letra letraAleatoria = letras.get(random.nextInt(letras.size()));

            // Agregar la nueva letra al jugador
            jugador.agregarLetra(letraAleatoria.letra, letraAleatoria.puntaje);

            // Decrementar la cantidad de la letra aleatoria en el saco
            letraAleatoria.cantidad--;

            // Si la letra aleatoria se ha agotado, eliminarla del saco
            if (letraAleatoria.cantidad == 0) {
                letras.remove(letraAleatoria);
            }

            System.out.println("Cambio realizado: " + letra + " por " + letraAleatoria.letra);
        }

        return true;
    }

    /**
     * Cuenta el número total de letras disponibles en el saco.
     *
     * @return el número total de letras en el saco.
     */
    public int contarLetrasEnSaco() {
        int totalLetras = 0;

        for (Letra letra : letras) {
            totalLetras += letra.cantidad; // Sumar la cantidad de cada letra
        }

        return totalLetras; // Retornar el total
    }
}

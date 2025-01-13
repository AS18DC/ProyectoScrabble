import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

/**
 * Clase que representa el juego de Scrabble.
 */
class Juego {
    private Jugador jugador1;
    private Jugador jugador2;
    private Saco saco;
    private Tablero tablero;
    private long tiempoInicio;
    private Diccionario diccionario;
    private int contadorMovimientos;


    /**
     *
     * Constructor del juego de Scrabble con diccionario.
     *
     * @param diccionario El diccionario utilizado en el juego. */
    public Juego(Diccionario diccionario) {
        this.diccionario = diccionario;
    }
    /**
     * Constructor del juego de Scrabble.
     *
     * @param jugador1        El primer jugador.
     * @param jugador2        El segundo jugador.
     * @param rutaDiccionario La ruta del archivo del diccionario.
     * @throws IOException Si ocurre un error al cargar el diccionario.
     */
    public Juego(Jugador jugador1, Jugador jugador2, String rutaDiccionario) throws IOException {
        this.jugador1 = jugador1;
        this.jugador2 = jugador2;
        this.saco = new Saco();
        this.tablero = new Tablero(saco);
        this.tiempoInicio = System.currentTimeMillis();
        this.diccionario = new Diccionario(rutaDiccionario);
    }


    /**
     * Obtiene el contador de movimientos. *
     *
     * @return El contador de movimientos.
     */
    public int getContadorMovimientos() {
        return contadorMovimientos;
    }

    public void setContadorMovimientos(int contadorMovimientos) {
        this.contadorMovimientos = contadorMovimientos;
    }

    /**
     * Valida si un número está en el rango de 0 a 14.
     *
     * @return El número validado.
     */
    public int validarNumero() {
        Scanner scanner = new Scanner(System.in);
        int numero = 0;
        boolean entradaValida = false;
        System.out.print(" (0-14):");
        while (!entradaValida) {
            numero = scanner.nextInt();
            if (numero >= 0 && numero <= 14) {
                entradaValida = true;
            } else {
                System.out.println("Error: El número debe estar entre 0 y 14. Intente de nuevo.");
                scanner.nextLine();
            }
        }
        return numero;
    }

    /**
     *  Guarda la partida actual.
     *
     * @param alias El alias con el que se guardará la partida.
     */

    public void guardarPartida(String alias,long tiempoTotal, int palabrasColocadas) {
        int contadorMovimientos = this.contadorMovimientos;
        TableroManager.guardarJuego(alias, tablero, jugador1, jugador2, saco, contadorMovimientos, tiempoTotal, palabrasColocadas );
    }

    /**
     *
     *  Carga una partida guardada.
     *
     * @param alias El alias de la partida guardada.
     * @throws IOException Si ocurre un error al cargar la partida.
     */

    public void cargarPartida(String alias) throws IOException {
        JuegoGuardado juegoGuardado = TableroManager.cargarJuego(alias);

        System.out.println("Cargando partida con contador de movimientos: " + this.contadorMovimientos);


        // Cargar el tablero
        this.tablero = juegoGuardado.getTablero();

        // Cargar los jugadores
        Partida partida = juegoGuardado.getPartida();
        if (partida.getJugador1() != null) {
            this.jugador1 = partida.getJugador1();
        }
        if (partida.getJugador2() != null) {
            this.jugador2 = partida.getJugador2();
        }
        if (partida.getSaco() != null) {
            this.saco = partida.getSaco(); // Cargar el saco
        } else {
            // Inicializa el saco si es null
            this.saco = new Saco(); // o cualquier lógica para inicializar un nuevo saco
        }

        String rutaDiccionario = "src/listado-general-sin-acentos.txt"; // Ajusta la ruta si es necesario
        this.diccionario = new Diccionario(rutaDiccionario);

        // Cargar otros atributos necesarios
        this.jugador1.setPuntajePartida(juegoGuardado.getPuntajeJugador1());
        this.jugador2.setPuntajePartida(juegoGuardado.getPuntajeJugador2());

        // Cargar las letras de los jugadores
        List<String> letrasJugador1 = juegoGuardado.getLetrasJugador1();
        List<String> letrasJugador2 = juegoGuardado.getLetrasJugador2();

        if (letrasJugador1 != null) {
            this.jugador1.setLetras(letrasJugador1);
        }
        if (letrasJugador2 != null) {
            this.jugador2.setLetras(letrasJugador2);
        }

        this.contadorMovimientos = juegoGuardado.getContadorMovimientos();

        long tiempoTotal = juegoGuardado.getTiempoTotal();
        int palabrasColocadas = juegoGuardado.getPalabrasColocadas();
    }



    /**
     * Permite seleccionar un jugador existente.
     *
     * @return El jugador seleccionado o null si no se encuentra.
     */
    public Jugador seleccionJugador() {
        Scanner scanner = new Scanner(System.in);
        Gestion gestion = new Gestion();
        Jugador jugador = null; // Inicializamos jugador como null

        while (jugador == null) { // Bucle que se ejecuta mientras jugador sea null
            System.out.println("Coloque el nombre del jugador: ");
            String nombreExistente = scanner.nextLine();
            jugador = gestion.consultarJugador(nombreExistente); // Consultamos el jugador

            if (jugador != null) {
                System.out.println("Jugador encontrado.");
            } else {
                System.out.println("Jugador no encontrado. Desea volver al menu? si/no");// Mensaje si no se encuentra el jugador
                String decision = scanner.nextLine();
                if (decision.equalsIgnoreCase("Si")) {
                    return null;
                }
            }
        }

        return jugador; // Retornamos el jugador encontrado
    }

    /**
     * Pausa la ejecución del juego por un número de milisegundos.
     *
     * @param milisegundos El número de milisegundos a pausar.
     */
    public static void pausar(int milisegundos) {
        try {
            Thread.sleep(milisegundos);
        } catch (InterruptedException e) {
            // Manejar la excepción si es necesario
        }
    }

    /**
     * Valida si una palabra existe en el diccionario.
     *
     * @param palabra La palabra a validar.
     * @return true si la palabra es válida, false en caso contrario.
     */
    private boolean validarPalabra(String palabra) {
        if (!diccionario.existePalabra(palabra)) {
            System.out.println("La palabra '" + palabra + "' no es válida.");
            return false;
        }
        return true;
    }
///************************************************************************************************\\\
    /**
     * Inicia la partida de Scrabble.
     */
    public boolean iniciarPartida() {
        System.out.println("!!! El juego ha iniciado !!!");
        System.out.println(" ");
        System.out.println("Datos Jugador 1: ");
        jugador1 = seleccionJugador();
        if (jugador1 == null) {
            return false;
        }
        System.out.println(" ");
        System.out.println("Datos Jugador 2: ");
        jugador2 = seleccionJugador();
        if (jugador2 == null) {
            return false;
        } else if (Objects.equals(jugador2.getNombre(), jugador1.getNombre())) {
            System.out.println("Jugador 1 "+jugador1.getNombre()+" es igual a Jugador 2 "+jugador2);
            return false;
        }else {
            System.out.println("Ambos jugadores han sido seleccionados correctamente. ¡Listos para jugar!");
            System.out.println(" ");
        }

        saco.repartirLetras(jugador1, 1);
        saco.repartirLetras(jugador2, 1);
        System.out.println();
        System.out.println("El jugador " + jugador1.getNombre() + " saco: " + jugador1.getLetras());
        System.out.println("El jugador " + jugador2.getNombre() + " saco: " + jugador2.getLetras());
        jugador1.setPuntajePartida(0);
        jugador2.setPuntajePartida(0);

        boolean turnoJugador1 = true;

        if (saco.letraMasCercanaA(jugador1.getLetras(), jugador2.getLetras())) {
            System.out.println("El jugador que tiene el primer turno es: " + jugador1.getNombre());
        } else {
            System.out.println("El jugador que tiene el primer turno es: " + jugador2.getNombre());
            turnoJugador1 = !turnoJugador1;
        }
        saco.devolverLetrasAlSaco(jugador1);
        saco.devolverLetrasAlSaco(jugador2);

        saco.repartirLetras(jugador1, 7);
        saco.repartirLetras(jugador2, 7);

        pausar(3000);
        tablero.colocarMultiplicadores();

        int contadorMovimientos = 0;
        while (true) {
            Jugador jugadorActual = turnoJugador1 ? jugador1 : jugador2;
            System.out.println();
            System.out.println();
            System.out.println("**************************-SCRABBLE-*****************************");

            tablero.mostrarTableroConColores();
            System.out.println("Cantidad en el saco: " + saco.contarLetrasEnSaco());
            System.out.println();

            System.out.println("Turno de " + jugadorActual.getNombre());
            System.out.println("Puntaje es " + jugadorActual.getPuntajePartida());
            System.out.println("Letras disponibles: " + jugadorActual.getLetras());
            Scanner scanner = new Scanner(System.in);
            System.out.println("___________________________________");

            boolean turnoCompletado = false;

            String exit = " ";
            if (exit.equalsIgnoreCase("exit")){
                return false;
            }

            while (!turnoCompletado) {
                System.out.println("Escriba ''Exit'' para salir de la partida");
                System.out.println("Quiere cambiar alguna ficha? Si/No");
                String cambioFicha = scanner.nextLine();

                if (cambioFicha.equalsIgnoreCase("exit")){
                    exit = "exit";
                    System.out.println("Ingrese el alias de la partida para su guardado: ");
                    String aliasGuardar = scanner.nextLine();
                    long tiempoTotal = (System.currentTimeMillis() - tiempoInicio) / 1000; // Calcular tiempo total
                    int contadorPalabras = contadorMovimientos;
                    guardarPartida(aliasGuardar, tiempoTotal, contadorPalabras);
                    return false;
                }

                if (cambioFicha.equalsIgnoreCase("eliminar")){
                    System.out.println("Coloque ficha a Eliminar: ");
                    String ficha = scanner.nextLine();
                    jugadorActual.usarLetra(ficha);
                    saco.repartirLetras(jugadorActual, 1);
                }


                if (cambioFicha.equalsIgnoreCase("Si")) {
                    System.out.println("Quiere cambiar todas las fichas? Si/No");
                    String cambioTodasLasFichas = scanner.nextLine();

                    if (cambioTodasLasFichas.equalsIgnoreCase("Si")) {
                        saco.devolverLetrasAlSaco(jugadorActual);
                        saco.repartirLetras(jugadorActual, 7);
                        System.out.println("Nuevas Letras disponibles: " + jugadorActual.getLetras());
                    } else {
                        saco.cambiarFichas(jugadorActual);
                        System.out.println("Nueva Letra disponible: " + jugadorActual.getLetras());
                    }
                    turnoCompletado = true; // Cambio de fichas completa el turno
                } else {
                    System.out.println("Puede colocar palabra? Si/No");
                    String disponibilidadTurno = scanner.nextLine();

                    if (disponibilidadTurno.equalsIgnoreCase("Si")) {
                        System.out.print("Ingrese la palabra: ");
                        String palabra = scanner.nextLine();

                        if (!jugadorActual.verificarLetrasParaPalabra(palabra, tablero)) {
                            System.out.println("El jugador no puede jugar la palabra: " + palabra);
                            System.out.println();
                            continue; // Regresa al inicio del bucle para intentar de nuevo
                        }

                        if (palabra.contains("-")) {
                            // Contar cuántos comodines hay en la palabra
                            int cantidadComodines = palabra.length() - palabra.replace("-", "").length();

                            if (cantidadComodines == 1) {
                                // Si hay un solo comodín
                                System.out.println("Coloque la letra a asignar a comodín:");
                                String comodin = scanner.nextLine();
                                palabra = palabra.replace("-", comodin);
                                System.out.println("Palabra con comodín reemplazado: " + palabra);
                            } else if (cantidadComodines == 2) {
                                // Si hay dos comodines
                                System.out.println("Coloque la letra a asignar al primer comodín:");
                                String comodin1 = scanner.nextLine();
                                palabra = palabra.replaceFirst("-", comodin1); // Reemplaza el primer comodín

                                System.out.println("Coloque la letra a asignar al segundo comodín:");
                                String comodin2 = scanner.nextLine();
                                palabra = palabra.replaceFirst("-", comodin2); // Reemplaza el segundo comodín

                                System.out.println("Palabra con comodines reemplazados: " + palabra);
                            } else {
                                System.out.println("Se permiten hasta dos comodines.");
                            }
                        }

                        if (!validarPalabra(palabra)) {
                            System.out.println("La palabra no está en el diccionario.");
                            System.out.println();
                            continue; // Regresa al inicio del bucle para intentar de nuevo
                        }

                        if (contadorMovimientos == 0) {
                            System.out.print("¿Horizontal? (true/false): ");
                            String horizontal = scanner.nextLine();

                            boolean horizontal2 = false;
                            int fila = 0;
                            int col = 0;
                            if (horizontal.equalsIgnoreCase("true")) {
                                horizontal2 = true;
                                fila = 7;
                                col = 7 - (palabra.length() / 2);
                            } else {
                                fila = 7 - (palabra.length() / 2);
                                col = 7;
                            }
                            int puntosGanados = tablero.colocarPalabra(palabra, fila, col, horizontal2, jugadorActual, contadorMovimientos);

                            if (puntosGanados > 0) {
                                ArrayList<String> usadas = new ArrayList<>();
                                for (char letra : palabra.toCharArray()) {
                                    usadas.add(String.valueOf(letra));
                                }

                                // Actualizar el puntaje del jugador
                                System.out.println("Ganaste " + puntosGanados + " puntos");
                                jugadorActual.setPuntajePartida(jugadorActual.getPuntajePartida() + puntosGanados);
                                contadorMovimientos = contadorMovimientos+1;

                                usadas.replaceAll(String::toUpperCase);
                                jugadorActual.usarLetras(usadas);
                                saco.repartirLetras(jugadorActual, usadas.size());

                                turnoCompletado = true; // Marcar el turno como completado
                            } else {
                                System.out.println("No se pudo colocar la palabra. Intente de nuevo.");
                                System.out.println();
                                continue;
                            }
                        } else {
                            System.out.print("Ingrese fila: ");
                            int fila = validarNumero();

                            System.out.print("Ingrese columna: ");
                            int col = validarNumero();

                            System.out.print("¿Horizontal? (true/false): ");
                            String horizontal = scanner.nextLine();

                            boolean horizontal2 = false;
                            if (horizontal.equalsIgnoreCase("true")) {
                                horizontal2 = true;
                            }

                            int puntosGanados = tablero.colocarPalabra(palabra, fila, col, horizontal2, jugadorActual, contadorMovimientos);

                            if (puntosGanados > 0) {
                                ArrayList<String> usadas = new ArrayList<>();
                                for (char letra : palabra.toCharArray()) {
                                    usadas.add(String.valueOf(letra));
                                }

                                // Actualizar el puntaje del jugador
                                System.out.println("Ganaste " + puntosGanados + " puntos");
                                jugadorActual.setPuntajePartida(jugadorActual.getPuntajePartida() + puntosGanados);
                                contadorMovimientos = contadorMovimientos +1 ;

                                usadas.replaceAll(String::toUpperCase);
                                jugadorActual.usarLetras(usadas);
                                saco.repartirLetras(jugadorActual, usadas.size());
                                turnoCompletado = true; // Marcar el turno como completado
                            } else {
                                System.out.println("No se pudo colocar la palabra. Intente de nuevo.");
                                System.out.println();
                                continue;
                            }
                        }
                    } else {
                        turnoCompletado = true;
                    }
                }
            }
            if (jugador1.getLetras().isEmpty() || jugador2.getLetras().isEmpty()) {
                break; // Terminar el juego
            }
            turnoJugador1 = !turnoJugador1; // Cambiar turno
        }
        finalizarPartida();
        return true;
    }


    /**
     * Finaliza la partida de Scrabble, mostrando los puntajes y el ganador.
     */
    public void finalizarPartida() {
        long tiempoFin = System.currentTimeMillis();
        long tiempoTotal = (tiempoFin - tiempoInicio) / 1000;
        System.out.println("El juego ha terminado.");
        System.out.println("Ganador: " + (jugador1.getPuntaje() > jugador2.getPuntaje() ? jugador1.getNombre() : jugador2.getNombre()));
        System.out.println("Puntaje Jugador 1: " + jugador1.getPuntaje());
        System.out.println("Puntaje Jugador 2: " + jugador2.getPuntaje());
        System.out.println("Tiempo de la partida: " + tiempoTotal + " segundos.");

        Scanner scanner = new Scanner(System.in);
        System.out.println("Ingrese el alias para guardar la partida: ");
        String aliasGuardar = scanner.nextLine();
        guardarPartida(aliasGuardar, tiempoTotal, contadorMovimientos);
    }

    /**
     * Reanuda la partida escogida segun el alias de la partida que se haya cargado.
     */

    public boolean reanudarPartida() {
        System.out.println("!!! Reanudando la partida !!!");
        System.out.println(" ");

        // Asegurarse de que los jugadores y el tablero estén cargados correctamente
        System.out.println("Datos Jugador 1: " + jugador1.getNombre());
        System.out.println("Datos Jugador 2: " + jugador2.getNombre());
        System.out.println("Puntaje Jugador 1: " + jugador1.getPuntajePartida());
        System.out.println("Puntaje Jugador 2: " + jugador2.getPuntajePartida());
        System.out.println("Letras Jugador 1: " + jugador1.getLetras());
        System.out.println("Letras Jugador 2: " + jugador2.getLetras());
        //System.out.println("Contador de movimientos: " + contadorMovimientos);
        System.out.println(" ");

        if (saco == null) {
            System.err.println("Error: El saco no está inicializado. No se puede reanudar la partida.");
            return false; // Salir del metodo si el saco no está inicializado
        }


        boolean turnoJugador1 = jugador1.getPuntajePartida() >= jugador2.getPuntajePartida(); // Determina quién comienza según el puntaje


        int newContadorMovimientos = 1;

        while (true) {
            Jugador jugadorActual = turnoJugador1 ? jugador1 : jugador2;
            System.out.println();
            System.out.println();
            System.out.println("**************************-SCRABBLE-*****************************");

            tablero.mostrarTableroConColores();
            System.out.println("Cantidad en el saco: " + saco.contarLetrasEnSaco());
            System.out.println();

            System.out.println("Turno de " + jugadorActual.getNombre());
            System.out.println("Puntaje es " + jugadorActual.getPuntajePartida());
            System.out.println("Letras disponibles: " + jugadorActual.getLetras());
            Scanner scanner = new Scanner(System.in);
            System.out.println("___________________________________");

            boolean turnoCompletado = false;

            String exit = " ";
            if (exit.equalsIgnoreCase("exit")){
                return false;
            }

            while (!turnoCompletado) {
                System.out.println("Escriba ''Exit'' para salir de la partida");
                System.out.println("Quiere cambiar alguna ficha? Si/No");
                String cambioFicha = scanner.nextLine();

                if (cambioFicha.equalsIgnoreCase("exit")){
                    exit = "exit";
                    System.out.println("Ingrese el alias de la partida para su guardado: ");
                    String aliasGuardar = scanner.nextLine();
                    int palabrasColocadas = contadorMovimientos;
                    long tiempoTotal = (System.currentTimeMillis() - tiempoInicio) / 1000; // Calcular tiempo total
                    int contadorPalabras = contadorMovimientos;
                    guardarPartida(aliasGuardar, tiempoTotal, contadorPalabras);
                    return false;
                }

                if (cambioFicha.equalsIgnoreCase("eliminar")){
                    System.out.println("Coloque ficha a Eliminar: ");
                    String ficha = scanner.nextLine();
                    jugadorActual.usarLetra(ficha);
                    saco.repartirLetras(jugadorActual, 1);
                }


                if (cambioFicha.equalsIgnoreCase("Si")) {
                    System.out.println("Quiere cambiar todas las fichas? Si/No");
                    String cambioTodasLasFichas = scanner.nextLine();

                    if (cambioTodasLasFichas.equalsIgnoreCase("Si")) {
                        saco.devolverLetrasAlSaco(jugadorActual);
                        saco.repartirLetras(jugadorActual, 7);
                        System.out.println("Nuevas Letras disponibles: " + jugadorActual.getLetras());
                    } else {
                        saco.cambiarFichas(jugadorActual);
                        System.out.println("Nueva Letra disponible: " + jugadorActual.getLetras());
                    }
                    turnoCompletado = true; // Cambio de fichas completa el turno
                } else {
                    System.out.println("Puede colocar palabra? Si/No");
                    String disponibilidadTurno = scanner.nextLine();

                    if (disponibilidadTurno.equalsIgnoreCase("Si")) {
                        System.out.print("Ingrese la palabra: ");
                        String palabra = scanner.nextLine();

                        if (!jugadorActual.verificarLetrasParaPalabra(palabra, tablero)) {
                            System.out.println("El jugador no puede jugar la palabra: " + palabra);
                            System.out.println();
                            continue; // Regresa al inicio del bucle para intentar de nuevo
                        }

                        if (palabra.contains("-")) {
                            // Contar cuántos comodines hay en la palabra
                            int cantidadComodines = palabra.length() - palabra.replace("-", "").length();

                            if (cantidadComodines == 1) {
                                // Si hay un solo comodín
                                System.out.println("Coloque la letra a asignar a comodín:");
                                String comodin = scanner.nextLine();
                                palabra = palabra.replace("-", comodin);
                                System.out.println("Palabra con comodín reemplazado: " + palabra);
                            } else if (cantidadComodines == 2) {
                                // Si hay dos comodines
                                System.out.println("Coloque la letra a asignar al primer comodín:");
                                String comodin1 = scanner.nextLine();
                                palabra = palabra.replaceFirst("-", comodin1); // Reemplaza el primer comodín

                                System.out.println("Coloque la letra a asignar al segundo comodín:");
                                String comodin2 = scanner.nextLine();
                                palabra = palabra.replaceFirst("-", comodin2); // Reemplaza el segundo comodín

                                System.out.println("Palabra con comodines reemplazados: " + palabra);
                            } else {
                                System.out.println("Se permiten hasta dos comodines.");
                            }
                        }

                        if (!validarPalabra(palabra)) {
                            System.out.println("La palabra no está en el diccionario.");
                            System.out.println();
                            continue; // Regresa al inicio del bucle para intentar de nuevo
                        }

                        if (newContadorMovimientos == 0) {
                            System.out.print("¿Horizontal? (true/false): ");
                            String horizontal = scanner.nextLine();

                            boolean horizontal2 = false;
                            int fila = 0;
                            int col = 0;
                            if (horizontal.equalsIgnoreCase("true")) {
                                horizontal2 = true;
                                fila = 7;
                                col = 7 - (palabra.length() / 2);
                            } else {
                                fila = 7 - (palabra.length() / 2);
                                col = 7;
                            }
                            int puntosGanados = tablero.colocarPalabra(palabra, fila, col, horizontal2, jugadorActual, newContadorMovimientos);

                            if (puntosGanados > 0) {
                                ArrayList<String> usadas = new ArrayList<>();
                                for (char letra : palabra.toCharArray()) {
                                    usadas.add(String.valueOf(letra));
                                }

                                // Actualizar el puntaje del jugador
                                System.out.println("Ganaste " + puntosGanados + " puntos");
                                jugadorActual.setPuntajePartida(jugadorActual.getPuntajePartida() + puntosGanados);
                                newContadorMovimientos++;

                                usadas.replaceAll(String::toUpperCase);
                                jugadorActual.usarLetras(usadas);
                                saco.repartirLetras(jugadorActual, usadas.size());

                                turnoCompletado = true; // Marcar el turno como completado
                            } else {
                                System.out.println("No se pudo colocar la palabra. Intente de nuevo.");
                                System.out.println();
                                continue;
                            }
                        } else {
                            System.out.print("Ingrese fila: ");
                            int fila = validarNumero();

                            System.out.print("Ingrese columna: ");
                            int col = validarNumero();

                            System.out.print("¿Horizontal? (true/false): ");
                            String horizontal = scanner.nextLine();

                            boolean horizontal2 = false;
                            if (horizontal.equalsIgnoreCase("true")) {
                                horizontal2 = true;
                            }

                            int puntosGanados = tablero.colocarPalabra(palabra, fila, col, horizontal2, jugadorActual, newContadorMovimientos);

                            if (puntosGanados > 0) {
                                ArrayList<String> usadas = new ArrayList<>();
                                for (char letra : palabra.toCharArray()) {
                                    usadas.add(String.valueOf(letra));
                                }

                                // Actualizar el puntaje del jugador
                                System.out.println("Ganaste " + puntosGanados + " puntos");
                                jugadorActual.setPuntajePartida(jugadorActual.getPuntajePartida() + puntosGanados);
                                newContadorMovimientos++;

                                usadas.replaceAll(String::toUpperCase);
                                jugadorActual.usarLetras(usadas);
                                saco.repartirLetras(jugadorActual, usadas.size());
                                turnoCompletado = true; // Marcar el turno como completado
                            } else {
                                System.out.println("No se pudo colocar la palabra. Intente de nuevo.");
                                System.out.println();
                                continue;
                            }
                        }
                    } else {
                        turnoCompletado = true;
                    }
                }
            }
            if (jugador1.getLetras().isEmpty() || jugador2.getLetras().isEmpty()) {
                break; // Terminar el juego
            }
            turnoJugador1 = !turnoJugador1; // Cambiar turno
        }
        finalizarPartida();
        return true;
    }
}

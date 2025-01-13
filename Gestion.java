import java.io.File;
import java.util.LinkedList;
import java.util.Scanner;

/**
 * Clase que gestiona las operaciones de jugadores y partidas en el juego de Scrabble.
 */
public class Gestion {

    private LinkedList<Jugador> jugadores;
    private LinkedList<Partida> partidas;

    /**
     * Constructor que inicializa las listas de jugadores y partidas.
     * Lee los jugadores y partidas existentes de archivos JSON.
     */
    public Gestion() {
        this.jugadores = GestionListaJSON.leerJugadoresExistentes();
        if(jugadores == null){
            jugadores = new LinkedList<>();
        }
        this.partidas = GestionListaJSON.leerPartidasExistentes();
        if(partidas == null){
            partidas = new LinkedList<>();
        }
    }

    public void cargarPartidasGuardadas() {
        File currentDirectory = new File(".");
        File[] files = currentDirectory.listFiles((dir, name) -> name.startsWith("juego_") && name.endsWith(".json"));

        if (files != null && files.length > 0) {
            for (File file : files){
                String alias = file.getName().replace("juego_", "").replace(".json", "");
                JuegoGuardado juegoGuardado = TableroManager.cargarJuego(alias);

                if (juegoGuardado != null) {
                    partidas.add(juegoGuardado.getPartida());
                    System.out.println("Partida con alias \"" + alias + "\" cargada exitosamente.");
                } else {
                    System.err.println("Error al cargar la partida con alias \"" + alias + "\".");
                }
            }

        } else {
            System.out.println("No se encontraron partidas guardadas para cargar.");
        }
    }

        /**
         * Muestra las estadísticas de partidas de un jugador específico.
         * @param aliasJugador El alias del jugador.
         */
    public void mostrarEstadisticasDePartidas(String aliasJugador) {
        // Validar si el jugador existe
        Jugador jugador = consultarJugador(aliasJugador);
        if (jugador == null) {
            System.out.println("El jugador con alias \"" + aliasJugador + "\" no existe.");
            return;
        }

        // Variables para acumular estadísticas
        int partidasJugadas = 0;
        int partidasGanadas = 0;
        int totalPuntos = 0;
        long totalTiempo = 0;
        int totalPalabrasColocadas = 0;

        // Recorrer partidas para recolectar estadísticas
        for (Partida partida : partidas) {
            if (partida.getJugador1().getNombre().equalsIgnoreCase(aliasJugador) || partida.getJugador2().getNombre().equalsIgnoreCase(aliasJugador)) {
                partidasJugadas++;
                if (partida.isGano()) {
                    partidasGanadas++;
                }
                totalPuntos += partida.getPuntos();
                totalTiempo += partida.getTiempoTotal();
                totalPalabrasColocadas += partida.getPalabrasColocadas();
            }
        }

        // Mostrar estadísticas o mensaje de falta de partidas
        if (partidasJugadas > 0) {
            System.out.println("Estadísticas de las partidas para el jugador \"" + aliasJugador + "\":");
            System.out.println("Total de partidas jugadas: " + partidasJugadas);
            System.out.println("Total de partidas ganadas: " + partidasGanadas);
            System.out.println("Total de puntos: " + totalPuntos);
            System.out.println("Total de tiempo jugado (en segundos): " + totalTiempo);
            System.out.println("Total de palabras colocadas: " + totalPalabrasColocadas);
        } else {
            System.out.println("No se encontraron partidas registradas para el jugador \"" + aliasJugador + "\".");
        }
    }


    /**
     * Registra un nuevo jugador.
     * @param correo El correo del jugador.
     * @param alias El alias del jugador.
     */
    public void registrarJugador(String correo, String alias) {
        if (jugadores==null){
            jugadores = new LinkedList<>();
        }
        jugadores.add(new Jugador(correo, alias));
    }

    /**
     * Consulta los datos de un jugador por su alias.
     * @param alias El alias del jugador.
     * @return El objeto Jugador encontrado o null si no se encuentra.
     */
    public Jugador consultarJugador(String alias) {
        GestionListaJSON.leerJugadoresExistentes();
        for (Jugador jugador : jugadores) {
            if (jugador.getNombre().equalsIgnoreCase(alias)) {
                return jugador;
            }
        }
        return null;
    }

    /**
     * Edita el correo electrónico de un jugador.
     * @param alias El alias del jugador.
     * @param nuevoCorreo El nuevo correo electrónico del jugador.
     */
    public void editarCorreo(String alias, String nuevoCorreo) {
        for (Jugador jugador : jugadores) {
            if (jugador.getNombre().equalsIgnoreCase(alias)) {
                jugador.setCorreoElectronico(nuevoCorreo);
                System.out.println("El correo electrónico del jugador " + alias + " ha sido actualizado.");
                return;
            }
        }
        System.out.println("Jugador con alias \"" + alias + "\" no encontrado.");
    }

    /**
     * Edita el alias de un jugador.
     * @param alias El alias actual del jugador.
     * @param nuevoAlias El nuevo alias del jugador.
     */
    public void editarAlias(String alias, String nuevoAlias) {
        for (Jugador jugador : jugadores) {
            if (jugador.getNombre().equalsIgnoreCase(alias)) {
                jugador.setNombre(nuevoAlias);
                System.out.println("El alias del jugador ha sido actualizado a " + nuevoAlias + ".");
                return;
            }
        }
        System.out.println("Jugador con alias \"" + alias + "\" no encontrado.");
    }

    /**
     * Valida los datos de un jugador.
     * @param nombre El nombre del jugador.
     * @param correoElectronico El correo electrónico del jugador.
     * @throws JugadorInvalido Si los datos del jugador no son válidos.
     */
    public void validarJugador(String nombre, String correoElectronico) throws JugadorInvalido {
        if (nombre == null || nombre.trim().isEmpty() || nombre.length() < 3) {
            throw new JugadorInvalido("Nombre inválido: debe tener al menos 3 caracteres.");
        }
        if (correoElectronico == null || !correoElectronico.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,6}$")) {
            throw new JugadorInvalido("Email inválido: " + correoElectronico);
        }
    }

    /**
     * Muestra el menú de registro de jugadores y gestiona las opciones seleccionadas.
     * @param gestionJugador La instancia de Gestion que gestiona a los jugadores.
     */
    public void menuRegistro(Gestion gestionJugador) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n--- Menú de opciones ---");
            System.out.println("1. Registrar jugador");
            System.out.println("2. Consultar jugador");
            System.out.println("3. Editar correo de un jugador");
            System.out.println("4. Editar alias de un jugador");
            System.out.println("5. Mostrar estadísticas de partidas");
            System.out.println("6. Volver a menú principal");
            System.out.print("Seleccione una opción: ");

            int opcion = scanner.nextInt();
            scanner.nextLine();

            if (opcion == 1) {
                System.out.print("Introduce el alias del jugador: ");
                String alias = scanner.nextLine();
                System.out.print("Introduce el correo del jugador: ");
                String correo = scanner.nextLine();
                boolean validplayer= false;

                while (!validplayer){
                    try {
                        validarJugador(alias,correo);
                        validplayer = true;
                    } catch (JugadorInvalido e) {
                        System.out.println(e.getMessage());
                        System.out.print("Introduce nuevamente el alias del jugador: ");
                        alias = scanner.nextLine();
                        System.out.print("Introduce nuevamente el correo del jugador: ");
                        correo = scanner.nextLine();
                    }
                }
                registrarJugador(correo, alias);
                GestionListaJSON.leerJugadoresExistentes();
                GestionListaJSON.guardarJugadores(jugadores);

            } else if (opcion == 2) {
                System.out.print("Introduce el alias del jugador que deseas consultar: ");
                String aliasConsulta = scanner.nextLine();
                Jugador jugador = gestionJugador.consultarJugador(aliasConsulta);

                if (jugador != null) {
                    System.out.println("Jugador encontrado: " + jugador);
                } else {
                    System.out.println("Jugador con alias \"" + aliasConsulta + "\" no encontrado.");
                }

            } else if (opcion == 3) {
                System.out.print("Introduce el alias del jugador cuyo correo deseas editar: ");
                String aliasCorreo = scanner.nextLine();
                System.out.print("Introduce el nuevo correo electrónico: ");
                String nuevoCorreo = scanner.nextLine();
                gestionJugador.editarCorreo(aliasCorreo, nuevoCorreo);

            } else if (opcion == 4) {
                System.out.print("Introduce el alias del jugador cuyo alias deseas editar: ");
                String aliasEdicion = scanner.nextLine();
                System.out.print("Introduce el nuevo alias: ");
                String nuevoAlias = scanner.nextLine();
                gestionJugador.editarAlias(aliasEdicion, nuevoAlias);

            } else if (opcion == 5) {
                cargarPartidasGuardadas();
                System.out.print("Introduce el alias del jugador para mostrar sus estadísticas: ");
                String aliasEstadisticas = scanner.nextLine();
                gestionJugador.mostrarEstadisticasDePartidas(aliasEstadisticas);

            } else if (opcion == 6) {
                System.out.println("Volviendo a menú principal...");
                break;

            } else {
                System.out.println("Opción inválida. Por favor, intente de nuevo.");
            }
        }
    }

}

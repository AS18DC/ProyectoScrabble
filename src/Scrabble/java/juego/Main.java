package juego;

import java.util.Scanner;

/**
 * Clase principal que inicia y gestiona el flujo del juego, permitiendo a los jugadores
 * iniciar sesión, registrarse, continuar partidas anteriores y ver estadísticas.
 * También permite a los jugadores iniciar nuevas partidas o continuar partidas guardadas.
 */
public class Main {

    /**
     * Manejador de archivos para gestionar operaciones relacionadas con el almacenamiento y recuperación de datos.
     */
    public static ManejadorDeArchivos archivos = new ManejadorDeArchivos();

    /**
     * Scanner para leer entradas del usuario desde la consola.
     */
    public static Scanner read = new Scanner(System.in);

    /**
     * Bandera para validar la entrada de números por el usuario.
     */
    public static boolean validInput = false;

    /**
     * Inicia el proceso de inicio de sesión o registro de un jugador.
     * Permite al jugador iniciar sesión si ya tiene una cuenta, o registrarse si es un nuevo jugador.
     *
     * @return Un objeto {@link Jugador} con la información del jugador autenticado o registrado.
     */
    public static Jugador inicio() {
        String alias = "";
        String email = "";
        Autentificazion auth = new Autentificazion();
        System.out.println("\n Ingrese la opcion que quiere elegir: ");
        System.out.println(" 1. Iniciar sesión \n 2. Registrarse \n\n 0. Salir");
        int opc = read.nextInt();

        while (opc != 0) {
            if ((opc == 1) || (opc == 2)) {
                System.out.println("Ingresa el correo electrónico");
                email = read.next();

                // Validar el correo electrónico
                while (!auth.validateEmail(email)) {
                    System.out.println("Correo inválido, por favor ingrese otro");
                    email = read.next();
                }

                System.out.println("Ingresa el alias del jugador");
                alias = read.next();

                // Iniciar sesión o registrarse según la opción
                if (opc == 1) {
                    Jugador jugador = auth.login(alias, email);
                    while (jugador == null) {
                        System.out.print("Usuario no existe, ingrese un usuario válido");
                        jugador = inicio();  // Recursión para intentar nuevamente
                    }
                    jugador.limpiarFichas(); // Limpiar fichas del jugador al iniciar
                    return jugador;
                }
                return auth.register(alias, email);
            } else {
                System.out.println("Ingrese una opción válida");
                System.out.println("\n\n 1. Iniciar sesión \n 2. Registrarse \n\n 0. Salir");
                opc = read.nextInt();
            }
        }
        return null;
    }

    /**
     * Lee un número entero ingresado por el usuario, validando la entrada.
     *
     * @return El número leído.
     */
    public static int leerNumero() {
        int opc = -1;
        while (!validInput) {
            try {
                opc = read.nextInt();
                validInput = true;
            } catch (Exception e) {
                System.out.println("Entrada inválida. Por favor, introduce un número.");
                read.nextLine();
            }
        }
        validInput = false;
        return opc;
    }

    /**
     * Muestra el menú principal del juego con las opciones disponibles para los jugadores.
     */
    public static void menu() {
        System.out.println("\n¿Qué les gustaría hacer?");
        System.out.println( "1. Iniciar una partida");
        System.out.println( "2. Continuar partida anterior");
        System.out.println("3. Ver estadísticas de los jugadores");
        System.out.println("0. Salir");
    }

    /**
     * Método principal que gestiona el flujo del juego.
     * Permite a dos jugadores iniciar sesión, registrarse, ver estadísticas o continuar una partida.
     * También inicia una nueva partida o carga una guardada.
     *
     * @param args Argumentos de línea de comandos (no utilizados en este caso).
     */
    public static void main(String[] args) {
        System.out.println("****************************************");
        System.out.println("*********** Inicio del juego *************");
        System.out.println("*************** juego.Jugador 1 ****************");
        Jugador jugador1 = inicio();
        System.out.println("*************** juego.Jugador 2 ****************");
        Jugador jugador2 = inicio();

        menu();
        int opc = leerNumero();
        while (opc != 0) {
            if (opc == 1) {
                Partida partida = new Partida(jugador1, jugador2);
                partida.game();
                menu();
                opc = leerNumero();
            } else if (opc == 2) {
                if (jugador1 != null && jugador2 != null) {
                    Partida continuar = archivos.buscarPartida(jugador1.getAlias(), jugador2.getAlias());
                    if (continuar == null) {
                        System.out.println("No hay ninguna partida guardada para cargar.");
                    } else {
                        continuar.continuarPartida();
                    }
                }
                menu();
                opc = leerNumero();
            } else if (opc == 3) {
                Stats stats = new Stats();
                if ((jugador1 != null) && (jugador2 != null)) {
                    System.out.println("*************** juego.Jugador 1 ****************");
                    stats.estad(jugador1.getAlias());
                    System.out.println("*************** juego.Jugador 2 ****************");
                    stats.estad(jugador2.getAlias());
                }
                menu();
                opc = leerNumero();
            } else {
                System.out.println("Ingrese una opción válida");
                menu();
                opc = leerNumero();
            }
        }
    }
}

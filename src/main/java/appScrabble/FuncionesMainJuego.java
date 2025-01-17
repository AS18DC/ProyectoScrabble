package appScrabble;

import java.io.IOException;
import java.util.Scanner;

/**
 * Clase principal para ejecutar el juego de Scrabble.
 */
public class FuncionesMainJuego {
    private static Juego juego;
    private static boolean partidaIniciada = false;

    /**
     * Inicia un nuevo juego de Scrabble.
     *
     * @param scanner El escáner para leer la entrada del usuario.
     */
    private static void iniciarJuego(Scanner scanner) {
        try {
            Jugador jugador1 = new Jugador("", "");
            Jugador jugador2 = new Jugador("", "");
            String rutaDiccionario = "src/main/resources/listado.txt";
            juego = new Juego(jugador1, jugador2, rutaDiccionario);
            partidaIniciada = true;

            if (!juego.iniciarPartida()) {
                System.out.println("Regresando al menú...");
            } else {
                System.out.println("Partida iniciada con éxito.");
            }
        } catch (IOException e) {
            System.out.println("Error al cargar el diccionario: " + e.getMessage());
        }
    }
}
    /**
     * Continúa una partida guardada de Scrabble.
     *
     * @param scanner El escáner para leer la entrada del usuario.
     */
//    private static void continuarPartida(Scanner scanner) {
//        System.out.print("Ingrese el alias de la partida que desea reanudar: ");
//        String alias = scanner.next(); // Leer el alias de la partida
//
//        try {
//            // Inicializar juego y diccionario
//            String rutaDiccionario = "src/listado-general-sin-acentos.txt";
//            juego = new Juego(new Diccionario(rutaDiccionario));
//            juego.cargarPartida(alias);
//            partidaIniciada = true;
//            juego.reanudarPartida();
//            System.out.println("Partida reanudada con éxito.");
//        } catch (IOException e) {
//            System.out.println("Error al cargar la partida: " + e.getMessage());
//            System.out.println("No hay ninguna partida guardada con el alias '" + alias + "'.");
//        }
//    }
//}

package juego;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Esta clase se encarga de manejar las operaciones de autenticación y registro de jugadores,
 * incluyendo la validación de correos electrónicos.
 */
public class Autentificazion {

    /**
     * Permite a un jugador iniciar sesión verificando su alias y correo electrónico.
     *
     * @param alias El alias del jugador que desea iniciar sesión.
     * @param email El correo electrónico del jugador.
     * @return El objeto juego.Jugador si la autenticación es exitosa, o null si no se encuentra el jugador
     *         o el correo electrónico no coincide.
     */
    public Jugador login(String alias, String email){
        ManejadorDeArchivos archivo = new ManejadorDeArchivos();
        Jugador jugador = archivo.restaurarJugador(alias);
        if(jugador==null){
            System.out.println("juego.Jugador no encontrado debe registrar primero");
            return null;
        }
        String emailjugador = jugador.getEmail();
        if(emailjugador.equals(email)){
            return jugador;
        }else {
            return null;
        }
    }

    /**
     * Registra un nuevo jugador con su alias y correo electrónico.
     *
     * @param alias El alias del jugador que se desea registrar.
     * @param email El correo electrónico del jugador.
     * @return El objeto juego.Jugador que ha sido registrado.
     */
    public Jugador register(String alias, String email){
        ManejadorDeArchivos archivo = new ManejadorDeArchivos();
        Jugador jugador = new Jugador(alias, email);
        archivo.salvarJugador(jugador);
        return jugador;
    }

    /**
     * Valida si el formato del correo electrónico es válido según una expresión regular.
     *
     * @param email El correo electrónico que se desea validar.
     * @return true si el correo electrónico es válido, false en caso contrario.
     */
    public boolean validateEmail(String email){
        Pattern pattern = Pattern.compile("^([0-9a-zA-Z]+[-._+&])*[0-9a-zA-Z]+@([-0-9a-zA-Z]+[.])+[a-zA-Z]{2,6}$");
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}

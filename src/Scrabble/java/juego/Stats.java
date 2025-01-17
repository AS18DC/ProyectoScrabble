package juego;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase que gestiona y muestra estadísticas de un jugador basadas en las partidas jugadas.
 * Proporciona métodos para recuperar y mostrar estadísticas detalladas de un jugador específico.
 */
public class Stats {
    private String alias;
    private long scoreTotal;
    private long tiempoTotal;
    private long palabrasJugadasTotal;
    private long partidasJugadasTotal;

    public long getScoreTotal() {
        return scoreTotal;
    }

    public void setScoreTotal(long scoreTotal) {
        this.scoreTotal = scoreTotal;
    }

    public long getTiempoTotal() {
        return tiempoTotal;
    }

    public void setTiempoTotal(long tiempoTotal) {
        this.tiempoTotal = tiempoTotal;
    }

    public long getPalabrasJugadasTotal() {
        return palabrasJugadasTotal;
    }

    public void setPalabrasJugadasTotal(long palabrasJugadasTotal) {
        this.palabrasJugadasTotal = palabrasJugadasTotal;
    }

    public long getPartidasJugadasTotal() {
        return partidasJugadasTotal;
    }

    public void setPartidasJugadasTotal(long partidasJugadasTotal) {
        this.partidasJugadasTotal = partidasJugadasTotal;
    }

    /**
     * Método que calcula y muestra las estadísticas del jugador especificado por su alias.
     * Recupera las partidas del jugador y calcula el total de puntos, tiempo jugado,
     * palabras jugadas y partidas jugadas. También muestra una tabla con las estadísticas
     * de cada partida jugada.
     *
     * @param alias El alias del jugador cuyas estadísticas se desean mostrar.
     */
    public void estad(String alias){
        scoreTotal = 0;
        palabrasJugadasTotal = 0;
        partidasJugadasTotal = 0;
        tiempoTotal = 0;

        ManejadorDeArchivos manejadorDeArchivos = new ManejadorDeArchivos();
        File directory = new File(System.getProperty("user.dir"));
        String extension = ".par";  // Extensión de los archivos de partida
        List<String> fileNames = FileDirectory.getFileNamesWithExtension(directory, extension);

        ArrayList<Partida> partidas = new ArrayList<>();
        for (String fileName : fileNames) {
            Partida partida = manejadorDeArchivos.restaurarPartida(fileName);
            // Verifica si el jugador está en alguna de las partidas
            if (partida.getJugador1().getAlias().equals(alias) || partida.getJugador2().getAlias().equals(alias)){
                partidas.add(partida);
            }
        }

        // Muestra los encabezados de la tabla de estadísticas
        System.out.print("ID");
        System.out.printf("%43s ", "SCORE");
        System.out.printf("%8s ", "TIEMPO");
        System.out.printf("%13s ", "PALABRAS");
        System.out.printf("%15s ", "GANO/PERDIO");
        System.out.println("");

        long score = 0;
        long tiempo = 0;
        long palabras = 0;

        // Muestra las estadísticas de cada partida jugada
        for (Partida partida : partidas) {
            if (partida.getJugador1().getAlias().equals(alias)){
                score = partida.getJugador1().getScore();
                tiempo = partida.getTime();
                palabras = partida.getJugador1().getPalabrasJugadas();
            } else {
                score = partida.getJugador2().getScore();
                tiempo = partida.getTime();
                palabras = partida.getJugador2().getPalabrasJugadas();
            }

            // Muestra los detalles de cada partida
            System.out.print(partida.uuid);
            System.out.printf("%6d ", score);
            System.out.printf("%8d ", tiempo);
            System.out.printf("%13d ", palabras);
            System.out.println("");

            // Actualiza las estadísticas totales
            partidasJugadasTotal++;
            scoreTotal += score;
            tiempoTotal += tiempo;
            palabrasJugadasTotal += palabras;
        }

        // Muestra las estadísticas totales del jugador
        System.out.println("\nEstadísticas totales del jugador " + alias + " son:\n"
                + "Score Total=" + scoreTotal
                + "\nTiempo total jugado=" + tiempoTotal
                + "\nPalabras jugadas=" + palabrasJugadasTotal
                + "\nPartidas jugadas=" + partidasJugadasTotal
        );
    }

    public void estadisticasSimples(String alias){
        scoreTotal = 0;
        palabrasJugadasTotal = 0;
        partidasJugadasTotal = 0;
        tiempoTotal = 0;

        ManejadorDeArchivos manejadorDeArchivos = new ManejadorDeArchivos();
        File directory = new File(System.getProperty("user.dir"));
        String extension = ".par";  // Extensión de los archivos de partida
        List<String> fileNames = FileDirectory.getFileNamesWithExtension(directory, extension);

        ArrayList<Partida> partidas = new ArrayList<>();
        for (String fileName : fileNames) {
            Partida partida = manejadorDeArchivos.restaurarPartida(fileName);
            // Verifica si el jugador está en alguna de las partidas
            if (partida.getJugador1().getAlias().equals(alias) || partida.getJugador2().getAlias().equals(alias)){
                partidas.add(partida);
            }
        }

        long score = 0;
        long tiempo = 0;
        long palabras = 0;

        // Muestra las estadísticas de cada partida jugada
        for (Partida partida : partidas) {
            if (partida.getJugador1().getAlias().equals(alias)){
                score = partida.getJugador1().getScore();
                tiempo = partida.getTime();
                palabras = partida.getJugador1().getPalabrasJugadas();
            } else {
                score = partida.getJugador2().getScore();
                tiempo = partida.getTime();
                palabras = partida.getJugador2().getPalabrasJugadas();
            }


            // Actualiza las estadísticas totales
            partidasJugadasTotal++;
            scoreTotal += score;
            tiempoTotal += tiempo;
            palabrasJugadasTotal += palabras;
        }
    }
}

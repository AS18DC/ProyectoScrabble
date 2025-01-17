package appScrabble;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class EndGameController {

    @FXML
    private Label lblGanador;

    @FXML
    private Label lblPuntajeJugador1;

    @FXML
    private Label lblPuntajeJugador2;

    @FXML
    private Label lblTiempoPartida;

    public void setEndGameData(String ganador, int puntajeJugador1, int puntajeJugador2, long tiempoPartida) {
        lblGanador.setText("Ganador: " + ganador);
        lblPuntajeJugador1.setText("Puntaje Jugador 1: " + puntajeJugador1);
        lblPuntajeJugador2.setText("Puntaje Jugador 2: " + puntajeJugador2);
        lblTiempoPartida.setText("Tiempo de la partida: " + tiempoPartida + " segundos");
    }

    @FXML
    private void handleClose() {
        Stage stage = (Stage) lblGanador.getScene().getWindow();
        stage.close();
    }
}
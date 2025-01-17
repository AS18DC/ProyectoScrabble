package appScrabble;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.List;

public class PartidaController {

    @FXML
    private VBox jugador1Info;

    @FXML
    private VBox jugador2Info;

    @FXML
    private Label nombreJugador1;

    @FXML
    private Label scoreJugador1;

    @FXML
    private Label nombreJugador2;

    @FXML
    private Label scoreJugador2;

    @FXML
    private GridPane tablero;

    @FXML
    private HBox letrasJugador;

    @FXML
    private Button btnPasar;

    @FXML
    private Button btnCambiarFichas;

    @FXML
    private Button btnEnviar;

    @FXML
    private Button btnBolsaFichas;

    @FXML
    private Button btnDeshacer;

    @FXML
    private Button btnStats;

    private Juego juego;
    private String letraSeleccionada;
    private Saco saco;

    public void setJuego(Juego juego) {
        this.juego = juego;
        actualizarVistaJugadores();
        actualizarLetrasJugador();
    }

    public void initialize() {
        saco = new Saco();
        for (int row = 0; row < 15; row++) {
            for (int col = 0; col < 15; col++) {
                Label label = getLabelAt(row, col);
                if (label != null) {
                    label.setPickOnBounds(true);
                }
            }
        }
    }

    private Label getLabelAt(int row, int col) {
        for (javafx.scene.Node node : tablero.getChildren()) {
            if (GridPane.getRowIndex(node) == row && GridPane.getColumnIndex(node) == col) {
                if (node instanceof Label) {
                    return (Label) node;
                }
            }
        }
        return null;
    }

    void actualizarVistaJugadores() {
        nombreJugador1.setText(juego.getJugador1().getNombre());
        scoreJugador1.setText("Puntos: " + juego.getJugador1().getPuntajePartida());

        nombreJugador2.setText(juego.getJugador2().getNombre());
        scoreJugador2.setText("Puntos: " + juego.getJugador2().getPuntajePartida());
    }

    private void actualizarLetrasJugador() {
        if (letrasJugador != null) {
            letrasJugador.getChildren().clear();
            List<String> letras = juego.getJugadorEnTurno().getLetras();

            for (String letra : letras) {
                int puntaje = juego.saco.obtenerPuntajeDeLaLetra(letra);
                Button botonFicha = new Button(letra + " (" + puntaje + ")");
                botonFicha.setPrefSize(50, 50);
                botonFicha.setStyle("-fx-font-size: 14;");
                botonFicha.setOnAction(e -> seleccionarLetra(letra));
                letrasJugador.getChildren().add(botonFicha);
            }
        } else {
            System.err.println("letrasJugador is not initialized.");
        }

    }

    private void seleccionarLetra(String letra) {
        letraSeleccionada = letra;
    }

    @FXML
    private void handleCellClick(MouseEvent event) {
        if (event.getSource() instanceof Label) {
            Label clickedLabel = (Label) event.getSource();
            if (letraSeleccionada != null) {
                // Obtener la fila y columna de la celda
                Integer fila = GridPane.getRowIndex(clickedLabel);
                Integer col = GridPane.getColumnIndex(clickedLabel);

                if (fila != null && col != null) {
                    // Crear un nuevo texto con la letra seleccionada
                    int puntaje = juego.saco.obtenerPuntajeDeLaLetra(letraSeleccionada);
                    Text texto = new Text(letraSeleccionada + " (" + puntaje + ")");

                    texto.setStyle("-fx-font-weight: bold;");

                    // Crear un StackPane para contener el texto
                    StackPane celda = new StackPane();
                    celda.getChildren().add(texto );

                    // Reemplazar el Label con el StackPane en el GridPane
                    tablero.getChildren().remove(clickedLabel);
                    tablero.add(celda, col, fila);

                    // Actualizar las letras del jugador
                    juego.getJugadorEnTurno().getLetras().remove(letraSeleccionada);
                    letraSeleccionada = null;
                    actualizarLetrasJugador();
                }
            }
            System.out.println("Clicked cell: " + clickedLabel.getText());
        } else {
            System.err.println("Clicked source is not a Label");
        }

    }

    public void resaltarJugadorEnTurno(boolean esJugador1Turno) {
        jugador1Info.setStyle(esJugador1Turno ? "-fx-background-color: lightgreen;" : "");
        jugador2Info.setStyle(esJugador1Turno ? "" : "-fx-background-color: lightgreen;");
    }

    @FXML
    protected void onEnviarClick() {
        // Lógica para enviar palabra
    }

    @FXML
    protected void onDeshacerClick() {
        // Lógica para deshacer movimientos
    }

    @FXML
    protected void onBolsaFichasClick() {
        int totalLetras = saco.contarLetrasEnSaco();

        StringBuilder mensaje = new StringBuilder();
        mensaje.append("Letras restantes en el saco:\n");

        for (Letra letra : saco.getLetras()) {
            mensaje.append("Letra: ").append(letra.letra)
                    .append(", Cantidad: ").append(letra.cantidad)
                    .append("\n");
        }
        mensaje.append("\nTotal de letras restantes: ").append(totalLetras);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Letras en el Saco");
        alert.setHeaderText(null);
        alert.setContentText(mensaje.toString());
        alert.showAndWait();
    }

    @FXML
    protected void onPasarClick() {
        juego.pasarTurno();
        resaltarJugadorEnTurno(juego.esTurnoJugador1());
        actualizarLetrasJugador();
    }

    @FXML
    public void onCambiarTodasLasFichasClick() {
        Jugador jugadorActual = juego.getJugadorEnTurno();
        saco.devolverLetrasAlSaco(jugadorActual);
        saco.repartirLetras(jugadorActual, 7);
        juego.pasarTurno();
        actualizarLetrasJugador();
        resaltarJugadorEnTurno(juego.esTurnoJugador1());
    }

    @FXML
    protected void onEstadisticasClick() {
        // Mostrar estadísticas de los jugadores
    }
}
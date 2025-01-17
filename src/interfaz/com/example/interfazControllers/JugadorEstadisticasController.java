package com.example.interfazControllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import juego.Stats;

import java.io.IOException;

public class JugadorEstadisticasController {


    @FXML
    private TextField textAlias;

    @FXML
    private TextField textScoreTotal;
    @FXML
    private TextField textTiempoTotal;
    @FXML
    private TextField textPartidasJugadas;
    @FXML
    private TextField textPalabrasColocadas;


    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    protected void onButtonCargarEstadisticas() {
        Stats stats = new Stats();
        stats.estadisticasSimples(textAlias.getText());
        textPalabrasColocadas.setText(Long.toString(stats.getPalabrasJugadasTotal()));
        textScoreTotal.setText(Long.toString(stats.getScoreTotal()));
        textPartidasJugadas.setText(Long.toString(stats.getPartidasJugadasTotal()));
        textTiempoTotal.setText(Long.toString(stats.getTiempoTotal()));
    }


    @FXML
    protected void onButtonRegresar() {
        try {
            Stage stage = (Stage) textAlias.getScene().getWindow();
            stage.close();

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/resources/interfazVistas/VistaMenuUsuario.fxml"));
            Parent root = fxmlLoader.load();
            Stage newStage = new Stage();
            newStage.setTitle("Menú Principal");
            newStage.setScene(new Scene(root));
            newStage.show();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error al cargar la interfaz previa.");
        }
    }
}

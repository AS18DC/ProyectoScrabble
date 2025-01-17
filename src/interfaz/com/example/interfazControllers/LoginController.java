package com.example.interfazControllers;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import juego.Jugador;
import juego.ManejadorDeArchivos;

import java.io.File;
import java.io.IOException;

public class LoginController {

    @FXML
    private TextField textJugador1;

    @FXML
    private TextField textJugador2;

    @FXML
    protected void onIniciar() {
        ManejadorDeArchivos manejadorDeArchivos=new ManejadorDeArchivos();
        Jugador jugador1=manejadorDeArchivos.restaurarJugador(textJugador1.getText());
        Jugador jugador2=manejadorDeArchivos.restaurarJugador(textJugador2.getText());
        File f = new File(textJugador1.getText() + ".jug");
        if(!f.exists() && !f.isDirectory()) {
            showAlert("Error,","El jugador " + textJugador1 + " no existe");
            return;
        }
        File f2 = new File(textJugador2.getText() + ".jug");
        if(!f2.exists() && !f2.isDirectory()) {
            showAlert("Error,","El jugador " + textJugador2 + " no existe");
            return;
        }

        try {
            Stage stage = (Stage) textJugador1.getScene().getWindow();
            stage.close();

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/resources/interfazVistas/VistaMenuPartida.fxml"));
            Parent root = fxmlLoader.load();
            PartidasMenuController partidasMenuController = fxmlLoader.getController();
            partidasMenuController.setContext(jugador1,jugador2);
            Stage newStage = new Stage();
            newStage.setTitle("Menú Partida");
            newStage.setScene(new Scene(root));
            newStage.show();
            showAlert("Jugadores","Jugador 1:" + jugador1.getAlias() + " Jugador 2:" + jugador2.getAlias());
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error al cargar la interfaz previa.");
        }
    }

    @FXML
    protected void onSalir() {
        System.exit(0);
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}

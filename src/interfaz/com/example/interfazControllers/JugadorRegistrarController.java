package com.example.interfazControllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import juego.Autentificazion;

import java.io.File;
import java.io.IOException;

public class JugadorRegistrarController {
    @FXML
    private TextField textNombreDeUsuario;

    @FXML
    private TextField textCorreoDeUsuario;

    @FXML
    protected void onButtonGuardarUsuario(){
        String alias = textNombreDeUsuario.getText();
        File f = new File(alias + ".jug");
        if(f.exists() && !f.isDirectory()) {
            showAlert("Error,","El jugador " + alias + " ya ha sido registrado");
            return;
        }
        String email = textCorreoDeUsuario.getText();
        Autentificazion auth = new Autentificazion();
        if (!auth.validateEmail(email)) {
            showAlert("Error,","Formato de correo invalido");
            return;
        }
        auth.register(alias, email);
        showAlert("Exito","El usuario ha sido creado exitosamente");
    }

    @FXML
    protected void onButtonRegresar() {
        try {
            Stage stage = (Stage) textNombreDeUsuario.getScene().getWindow();
            stage.close();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/resources/interfazVistas/VistaMenuUsuario.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage newStage = new Stage();
            newStage.setScene(scene);
            newStage.setTitle("Menú Principal");
            newStage.show();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error al cargar la interfaz previa.");
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}

package com.example.interfazControllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class JugadorEliminarController {
    @FXML
    private TextField textNombreDeUsuarioAEliminar;

    @FXML
    protected void onButtonEliminar() {
        File archivo = new File(textNombreDeUsuarioAEliminar.getText() + ".jug");
        if (archivo.delete()) {
            showAlert("Exito", "El usuario ha sido eliminado");
            return;
        } else {
            showAlert("Error", "El usuario a eliminar no existe");
            return;
        }
    }

    @FXML
    protected void onButtonRegresar() {
        try {
            Stage stage = (Stage) textNombreDeUsuarioAEliminar.getScene().getWindow();
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


    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}

package com.example.interfazControllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import juego.JSONMapper;
import juego.Jugador;

import java.io.*;

public class JugadorModificarController {

    @FXML
    private TextField textAlias;

    @FXML
    private TextField textEmail;

    @FXML
    protected void onButtonModificar() {
        String nombreDeArchivo = textAlias.getText();
        Jugador jugador=new Jugador(textAlias.getText(),textEmail.getText());
        File archivo = new File(nombreDeArchivo + ".jug");
        try {
            FileWriter escrituraArchivo = new FileWriter(archivo);
            BufferedWriter bw = new BufferedWriter(escrituraArchivo);
            bw.write(JSONMapper.objectoToJson(jugador)); // Convierte al jugador a formato JSON y lo guarda
            bw.flush();
            bw.close();
        } catch (IOException e) {
            // Handle exceptions (comentado para fines de depuración)
        }
    }


    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
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

    @FXML
    protected void onButtonLeer(){
        String alias = textAlias.getText();
        File f = new File(alias + ".jug");
        if(!f.exists() && !f.isDirectory()) {
            showAlert("Error,","El jugador " + alias + " no existe");
            return;
        }
        try {
            FileReader fr = new FileReader(alias + ".jug");
            BufferedReader br = new BufferedReader(fr);
            String json = br.readLine();
            Jugador jugador=JSONMapper.jsonToObject(json, Jugador.class);
            textEmail.setText(jugador.getEmail());
        } catch (FileNotFoundException e) {
            showAlert("Error","El jugador " + alias + " no existe");
        } catch (IOException e) {
            showAlert("Error","Error al leer informacion");
        }
        return ;
    }
}


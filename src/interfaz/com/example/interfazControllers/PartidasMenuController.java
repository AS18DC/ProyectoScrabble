package com.example.interfazControllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import juego.Jugador;
import juego.Partida;

import java.io.IOException;

import static juego.Main.archivos;

public class PartidasMenuController {
    private Jugador jugador1;
    private Jugador jugador2;

    public void setContext(Jugador jugador1,Jugador jugador2){
        this.jugador1=jugador1;
        this.jugador2=jugador2;
    }


    @FXML
    protected void onButtonNuevoJuego(ActionEvent event) {
        try {
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.close();

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/resources/interfazVistas/VistaTablero.fxml"));
            Parent root = fxmlLoader.load();
            TableroController tableroController = fxmlLoader.getController();
            tableroController.setPartida(this.jugador1,this.jugador2);
            Stage stage = new Stage();
            stage.setTitle("Juego");
            stage.setScene(new Scene(root, 600, 800));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void onButtonContinuarJuego(ActionEvent event) {
        Partida continuar = archivos.buscarPartida(jugador1.getAlias(), jugador2.getAlias());
        if(continuar ==null ) return;

        try {
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.close();

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/resources/interfazVistas/VistaTablero.fxml"));
            Parent root = fxmlLoader.load();
            TableroController tableroController = fxmlLoader.getController();
            tableroController.continuarPartida(continuar);
            Stage stage = new Stage();
            stage.setTitle("Juego");
            stage.setScene(new Scene(root, 600, 800));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }    }

    @FXML
    protected void onButtonEstadisticas(ActionEvent event){
        try {

            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.close();

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/resources/interfazVistas/VistasMenuEstadisticasPartida.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("Menu De Registro");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    protected void onButtonRegresar(){
        System.exit(0);
    }

}

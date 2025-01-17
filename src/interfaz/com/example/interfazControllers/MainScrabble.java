package com.example.interfazControllers;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainScrabble extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/resources/interfazVistas/VistaLogin.fxml"));
        primaryStage.setTitle("Menu Jugador");
        primaryStage.setScene(new Scene(root, 600, 600));
        primaryStage.show();
        System.out.println("Hola Mundo");
    }

    public static void main(String[] args) {
        launch(args);
    }
}
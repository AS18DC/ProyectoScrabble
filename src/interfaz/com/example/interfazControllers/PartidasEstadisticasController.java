package com.example.interfazControllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import juego.Partida;
import juego.Jugador;

import java.io.IOException;

public class PartidasEstadisticasController {

    @FXML
    private TextField campoJugador1;

    @FXML
    private TextField campoPuntosJugador1;

    @FXML
    private TextField campoPalabrasJugador1;

    @FXML
    private TextField campoJugador2;

    @FXML
    private TextField campoPuntosJugador2;

    @FXML
    private TextField campoPalabrasJugador2;

    @FXML
    private TextField campoGanador;

    @FXML
    private TextField campoDuracion;

    @FXML
    private Button btnRegresar;

    private Partida partida;

    /*@FXML
    public void initialize() {
        // Configuración inicial del controlador si es necesaria.
        configurarCamposNoEditables();
        btnRegresar.setOnAction(event -> regresarMenuPrincipal());
    }*/

    public void setPartida(Partida partida) {
        this.partida = partida;
        cargarEstadisticas();
    }

    @FXML
    private void cargarEstadisticas() {
        if (partida != null) {
            Jugador jugador1 = partida.getJugador1();
            Jugador jugador2 = partida.getJugador2();

            if (jugador1 != null) {
                campoJugador1.setText(jugador1.getAlias());
                campoPuntosJugador1.setText(String.valueOf(jugador1.getScore()));
                campoPalabrasJugador1.setText(String.valueOf(jugador1.getPalabrasJugadas()));
            } else {
                campoJugador1.setText("N/A");
                campoPuntosJugador1.setText("0");
                campoPalabrasJugador1.setText("0");
            }

            if (jugador2 != null) {
                campoJugador2.setText(jugador2.getAlias());
                campoPuntosJugador2.setText(String.valueOf(jugador2.getScore()));
                campoPalabrasJugador2.setText(String.valueOf(jugador2.getPalabrasJugadas()));
            } else {
                campoJugador2.setText("N/A");
                campoPuntosJugador2.setText("0");
                campoPalabrasJugador2.setText("0");
            }

            // Determinar el ganador
            int ganador = partida.getWinner();

            // Establecer la duración
            campoDuracion.setText(partida.getId() != null ? String.valueOf(partida.getTime()) : "0:00");
        } else {
            System.out.println("No se ha configurado una partida.");
        }
    }

    private void configurarCamposNoEditables() {
        campoJugador1.setEditable(false);
        campoPuntosJugador1.setEditable(false);
        campoPalabrasJugador1.setEditable(false);

        campoJugador2.setEditable(false);
        campoPuntosJugador2.setEditable(false);
        campoPalabrasJugador2.setEditable(false);

        campoGanador.setEditable(false);
        campoDuracion.setEditable(false);
    }

    @FXML
    private void regresarMenuPrincipal() {
        try {
            Stage stage = (Stage) campoJugador1.getScene().getWindow();
            stage.close();

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/resources/interfazVistas/VistaMenuPartida.fxml"));
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


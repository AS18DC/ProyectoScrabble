package com.example.interfazControllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class GanadorController {

    @FXML
    private Label nombre;
    @FXML
    private Label puntos1;
    @FXML
    private Label puntos2;
    @FXML
    private Label palabras1;
    @FXML
    private Label palabras2;
    @FXML
    private Label tiempo;

    public void setContext(String nombre, int puntos1, int puntos2, int palabras1, int palabras2, int tiempo) {
        if (puntos1 == puntos2) {
            this.nombre.setText("Empate: ¡No hay ganador!");
        } else {
            this.nombre.setText("Ganador: " + nombre);
        }
        this.puntos1.setText("Puntos del Jugador 1: " + puntos1);
        this.puntos2.setText("Puntos del Jugador 2: " + puntos2);
        this.palabras1.setText("Palabras Jugadas por Jugador 1: " + palabras1);
        this.palabras2.setText("Palabras Jugadas por Jugador 2: " + palabras2);
        this.tiempo.setText("Tiempo de Partida: " + (tiempo / 1000000) + " segundos");
    }


    @FXML
    protected void onButtonSalir(ActionEvent event) {
        System.exit(0);
    }
}

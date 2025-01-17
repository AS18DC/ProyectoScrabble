package com.example.interfazControllers;

public abstract class MultiplicadorDecorator implements Puntaje {
    protected Puntaje puntajeDecorado;

    public MultiplicadorDecorator(Puntaje puntajeDecorado) {
        this.puntajeDecorado = puntajeDecorado;
    }

    @Override
    public int calcularPuntaje() {
        return puntajeDecorado.calcularPuntaje();
    }
}
package com.example.interfazControllers;

public class TriplePalabraDecorator extends MultiplicadorDecorator {
    public TriplePalabraDecorator(Puntaje puntajeDecorado) {
        super(puntajeDecorado);
    }

    @Override
    public int calcularPuntaje() {
        return super.calcularPuntaje() * 3;
    }
}
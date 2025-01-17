package com.example.interfazControllers;

public class DoblePalabraDecorator extends MultiplicadorDecorator {
    public DoblePalabraDecorator(Puntaje puntajeDecorado) {
        super(puntajeDecorado);
    }

    @Override
    public int calcularPuntaje() {
        return super.calcularPuntaje() * 2;
    }
}
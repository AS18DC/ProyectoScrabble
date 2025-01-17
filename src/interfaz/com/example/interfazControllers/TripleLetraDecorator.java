package com.example.interfazControllers;

public class TripleLetraDecorator extends MultiplicadorDecorator {
    public TripleLetraDecorator(Puntaje puntajeDecorado) {
        super(puntajeDecorado);
    }

    @Override
    public int calcularPuntaje() {
        return super.calcularPuntaje() * 3;
    }
}
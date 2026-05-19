package it.unicam.cs.mpgc.rpg123279.model.mappe;

public class Rotta {

    private Isola origine;
    private Isola destinazione;
    private int pericolo;
    private double probabilitaIncontro;

    public Rotta() {}
    public Rotta(Isola origine, Isola destinazione, int pericolo, double probabilitaIncontro) {
        this.origine = origine;
        this.destinazione = destinazione;
        this.pericolo = pericolo;
        this.probabilitaIncontro = probabilitaIncontro;
    }

    public Isola getOrigine() {
        return origine;
    }

    public Isola getDestinazione() {
        return destinazione;
    }

    public int getPericolo() {
        return pericolo;
    }

    public double getProbabilitaIncontro() {
        return probabilitaIncontro;
    }

    @Override
    public String toString() {
        return origine.getNome() + " -> " + destinazione.getNome();
    }
}
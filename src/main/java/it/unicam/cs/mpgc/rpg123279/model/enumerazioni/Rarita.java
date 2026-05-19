package it.unicam.cs.mpgc.rpg123279.model.enumerazioni;

public enum Rarita {

    COMUNE(1),
    RARO(2),
    EPICO(3),
    LEGGENDARIO(5);

    private final int moltiplicatore;

    Rarita(int m) {
        this.moltiplicatore = m;
    }

    public int getMoltiplicatore() {return moltiplicatore;}
}
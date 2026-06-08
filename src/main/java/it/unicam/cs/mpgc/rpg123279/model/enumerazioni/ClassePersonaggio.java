package it.unicam.cs.mpgc.rpg123279.model.enumerazioni;

public enum ClassePersonaggio {
    CORSARO(25, 12, 120),
    CAPITANO(18, 18, 160),
    CACCIATORE(30, 7, 85);

    private final int attaccoBase;
    private final int difesaBase;
    private final int maxHpBase;

    ClassePersonaggio(int attaccoBase, int difesaBase, int maxHpBase) {
        this.attaccoBase = attaccoBase;
        this.difesaBase = difesaBase;
        this.maxHpBase = maxHpBase;
    }

    public int getAttaccoBase() { return attaccoBase; }
    public int getDifesaBase() { return difesaBase; }
    public int getMaxHpBase() { return maxHpBase; }
}
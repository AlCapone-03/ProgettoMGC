package it.unicam.cs.mpgc.rpg123279.model.eventi;

import it.unicam.cs.mpgc.rpg123279.model.enumerazioni.TipoEvento;

public abstract class AbstractGameEvent {

    private final TipoEvento tipoEvento;
    private final String descrizione;

    protected AbstractGameEvent(TipoEvento tipoEvento, String desc) {
        this.tipoEvento  = tipoEvento;
        this.descrizione = desc;
    }

    public TipoEvento getTipoEvento() { return tipoEvento; }
    public String getDescrizione() { return descrizione; }

    @Override
    public String toString() {return tipoEvento + ": " + descrizione;}
}
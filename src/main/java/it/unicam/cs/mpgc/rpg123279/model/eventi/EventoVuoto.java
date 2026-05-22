package it.unicam.cs.mpgc.rpg123279.model.eventi;

import it.unicam.cs.mpgc.rpg123279.model.enumerazioni.TipoEvento;

public class EventoVuoto extends AbstractGameEvent {

    private static final String[] descrizioni = { "Il mare è calmo. Le onde cullano dolcemente la nave.",
            "Nessun segno di vita all'orizzonte. Solo cielo e oceano.",
            "Un vento favorevole spinge le vele. Il viaggio scorre tranquillo.",
            "Qualche nuvola all'orizzonte, ma niente di preoccupante."
    };

    public EventoVuoto() {
        super(TipoEvento.VUOTO, descrizioni[(int)(Math.random() * descrizioni.length)]);
    }
}
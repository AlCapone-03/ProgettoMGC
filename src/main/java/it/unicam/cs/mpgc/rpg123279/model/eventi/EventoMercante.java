package it.unicam.cs.mpgc.rpg123279.model.eventi;

import it.unicam.cs.mpgc.rpg123279.model.enumerazioni.TipoEvento;
import it.unicam.cs.mpgc.rpg123279.model.oggetti.AbstractOggetto;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

public class EventoMercante extends AbstractGameEvent {

    private final Map<AbstractOggetto, Integer> catalogo;

    public EventoMercante(Map<AbstractOggetto, Integer> catalogo) {
        super(TipoEvento.MERCANTE,"Incontri un mercante! Ha " + catalogo.size() + " oggetti in vendita.");
        this.catalogo = Collections.unmodifiableMap(new LinkedHashMap<>(catalogo));
    }

    public Map<AbstractOggetto, Integer> getCatalogo() { return catalogo; }
}
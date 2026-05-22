package it.unicam.cs.mpgc.rpg123279.model.eventi;

import it.unicam.cs.mpgc.rpg123279.model.enumerazioni.TipoEvento;
import it.unicam.cs.mpgc.rpg123279.model.personaggi.AbstractNemico;

public class EventoBattaglia extends AbstractGameEvent {

    private final AbstractNemico nemico;

    public EventoBattaglia(AbstractNemico nemico) {
        super(TipoEvento.BATTAGLIA, "Un nemico si para davanti a te: " + nemico.getNome() + " (Lv." + nemico.getLivello() + ")");
        this.nemico = nemico;
    }

    public AbstractNemico getNemico() { return nemico; }
}
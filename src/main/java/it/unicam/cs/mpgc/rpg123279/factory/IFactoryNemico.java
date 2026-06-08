package it.unicam.cs.mpgc.rpg123279.factory;

import it.unicam.cs.mpgc.rpg123279.model.personaggi.AbstractNemico;
import java.util.List;

public interface IFactoryNemico {

    AbstractNemico creaNemicoCasuale(int lvlGiocatore);
    AbstractNemico creaNemicoPerIsola(List<Class<? extends AbstractNemico>> nemiciPossibili, int lvlGiocatore);
    AbstractNemico creaKraken(int lvlGiocatore);
    AbstractNemico creaMarinaioMaledetto(int lvlGiocatore);
    AbstractNemico creaPirataScheletrico(int lvlGiocatore);
    AbstractNemico creaSerpenteMarino(int lvlGiocatore);
}
package it.unicam.cs.mpgc.rpg123279.factory;

import it.unicam.cs.mpgc.rpg123279.model.personaggi.AbstractNemico;

public interface IFactoryNemico {

    AbstractNemico creaNemicoCasuale(int lvlGiocatore);
    AbstractNemico creaKraken(int lvlGiocatore);
    AbstractNemico creaMarinaioMaledetto(int lvlGiocatore);
    AbstractNemico creaPirataScheletrico(int lvlGiocatore);
    AbstractNemico creaSerpenteMarino(int lvlGiocatore);
}
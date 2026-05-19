package it.unicam.cs.mpgc.rpg123279.factory;

import it.unicam.cs.mpgc.rpg123279.model.oggetti.AbstractOggetto;

public interface IFactoryOggetto {
    AbstractOggetto creaArma(int livelloGiocatore);
    AbstractOggetto creaArmatura(int livelloGiocatore);
    AbstractOggetto creaReliquia(int livelloGiocatore);
    AbstractOggetto creaPozioneSalute(int livelloGiocatore);
    AbstractOggetto creaRum(int livelloGiocatore);
    AbstractOggetto creaOggettoCasuale(int livelloGiocatore);
}
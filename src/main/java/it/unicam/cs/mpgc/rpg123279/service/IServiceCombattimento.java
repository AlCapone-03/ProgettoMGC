package it.unicam.cs.mpgc.rpg123279.service;

import it.unicam.cs.mpgc.rpg123279.model.battaglia.BattleState;
import it.unicam.cs.mpgc.rpg123279.model.battaglia.DatiTurno;
import it.unicam.cs.mpgc.rpg123279.model.battaglia.RisultatoBattaglia;
import it.unicam.cs.mpgc.rpg123279.model.enumerazioni.AzioneCombattimento;
import it.unicam.cs.mpgc.rpg123279.model.oggetti.AbstractOggetto;
import it.unicam.cs.mpgc.rpg123279.model.personaggi.AbstractNemico;
import it.unicam.cs.mpgc.rpg123279.model.personaggi.Giocatore;

public interface IServiceCombattimento {

    BattleState iniziaBattaglia(Giocatore giocatore, AbstractNemico nemico);
    DatiTurno eseguiTurno(BattleState stato, AzioneCombattimento azione, AbstractOggetto oggettoScelto);
    RisultatoBattaglia concludiBattaglia(BattleState stato);
}
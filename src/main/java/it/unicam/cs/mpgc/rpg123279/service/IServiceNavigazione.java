package it.unicam.cs.mpgc.rpg123279.service;

import it.unicam.cs.mpgc.rpg123279.model.eventi.AbstractGameEvent;
import it.unicam.cs.mpgc.rpg123279.model.mappe.Isola;
import it.unicam.cs.mpgc.rpg123279.model.mappe.Rotta;
import it.unicam.cs.mpgc.rpg123279.model.personaggi.Giocatore;
import java.util.List;

public interface IServiceNavigazione {
    Isola getIsolaCorrente();
    void setIsolaCorrente(Isola isola);
    List<Rotta> getRotteDisponibili();
    AbstractGameEvent naviga(Rotta rotta, Giocatore giocatore);
    AbstractGameEvent esplora(Giocatore giocatore);
}
package it.unicam.cs.mpgc.rpg123279.service;

import it.unicam.cs.mpgc.rpg123279.model.personaggi.Giocatore;

public interface IServiceLivello {
    void aggiungiEsperienza(Giocatore giocatore, int xp);
    int getXpLivelloSuccessivo(int livello);
    boolean saleDiLivello(Giocatore giocatore);
}
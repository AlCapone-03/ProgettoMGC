package it.unicam.cs.mpgc.rpg123279.service;

import it.unicam.cs.mpgc.rpg123279.model.oggetti.AbstractOggetto;
import it.unicam.cs.mpgc.rpg123279.model.personaggi.Giocatore;

public interface IServiceInventario {
    boolean aggiungiOggetto(Giocatore giocatore, AbstractOggetto oggetto);
    boolean rimuoviOggetto(Giocatore giocatore, AbstractOggetto oggetto);
    void usaOggetto(Giocatore giocatore, AbstractOggetto oggetto);
    boolean acquista(Giocatore giocatore, AbstractOggetto oggetto);
}
package it.unicam.cs.mpgc.rpg123279.service;

import it.unicam.cs.mpgc.rpg123279.model.PartitaRipristinata;
import it.unicam.cs.mpgc.rpg123279.model.Salvataggio;
import it.unicam.cs.mpgc.rpg123279.model.mappe.MappaGioco;
import it.unicam.cs.mpgc.rpg123279.model.personaggi.Giocatore;
import java.util.List;

public interface IServiceSalvataggio {
    Salvataggio salva(Giocatore giocatore, String nomeSlot, String nomeIsolaCorrente);
    PartitaRipristinata ripristinaPartita(String nomeSlot, MappaGioco mappa);
    void elimina(String nomeSlot);
    List<Salvataggio> getSalvataggiByDate();
    boolean esisteSlot(String nomeSlot);
}
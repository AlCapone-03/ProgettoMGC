package it.unicam.cs.mpgc.rpg123279.service;

import it.unicam.cs.mpgc.rpg123279.model.eventi.AbstractGameEvent;
import it.unicam.cs.mpgc.rpg123279.model.mappe.Isola;
import it.unicam.cs.mpgc.rpg123279.model.mappe.Rotta;

public interface IServiceEvento {
    AbstractGameEvent generaEventoNavigazione(Rotta rotta, int livelloGiocatore, Isola isola);
    AbstractGameEvent generaEventoEsplorazione(int pericolo, int livelloGiocatore, Isola isola);
}
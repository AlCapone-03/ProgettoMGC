package it.unicam.cs.mpgc.rpg123279.service;

import it.unicam.cs.mpgc.rpg123279.model.eventi.AbstractGameEvent;
import it.unicam.cs.mpgc.rpg123279.model.mappe.Rotta;

public interface IServiceEvento {
    AbstractGameEvent generaEvento(Rotta rotta, int livelloGiocatore);
}
package it.unicam.cs.mpgc.rpg123279.model.oggetti.equipaggiamenti;

import it.unicam.cs.mpgc.rpg123279.model.enumerazioni.Rarita;
import it.unicam.cs.mpgc.rpg123279.model.enumerazioni.TipoOggetto;
import it.unicam.cs.mpgc.rpg123279.model.oggetti.AbstractEquipaggiamento;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("ARMA")
public class Arma extends AbstractEquipaggiamento {

    public Arma() {}
    public Arma(String nome, String descrizione, int valore, int livelloRichiesto, Rarita rarita, int bonus) {
        super(nome, descrizione, valore, rarita, TipoOggetto.ARMA,livelloRichiesto, bonus);
    }
}
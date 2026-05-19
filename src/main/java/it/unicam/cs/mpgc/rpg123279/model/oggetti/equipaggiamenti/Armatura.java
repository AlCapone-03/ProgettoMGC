package it.unicam.cs.mpgc.rpg123279.model.oggetti.equipaggiamenti;

import it.unicam.cs.mpgc.rpg123279.model.enumerazioni.Rarita;
import it.unicam.cs.mpgc.rpg123279.model.enumerazioni.TipoOggetto;
import it.unicam.cs.mpgc.rpg123279.model.oggetti.AbstractEquipaggiamento;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("ARMATURA")
public class Armatura extends AbstractEquipaggiamento {

    public Armatura(String nome, String descrizione, int valore, Rarita rarita, int livelloRichiesto,  int bonus) {
        super(nome, descrizione, valore, rarita, TipoOggetto.ARMATURA, livelloRichiesto,  bonus);
    }
}
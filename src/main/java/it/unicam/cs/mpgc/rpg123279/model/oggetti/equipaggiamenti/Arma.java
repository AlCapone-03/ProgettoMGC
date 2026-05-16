package it.unicam.cs.mpgc.rpg123279.model.oggetti.equipaggiamenti;

import it.unicam.cs.mpgc.rpg123279.model.enumerazioni.Rarita;
import it.unicam.cs.mpgc.rpg123279.model.enumerazioni.TipoOggetto;
import it.unicam.cs.mpgc.rpg123279.model.oggetti.AbstractEquipaggiamento;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("ARMA")
public class Arma extends AbstractEquipaggiamento {
    public Arma() {
        super("Spada Arrugginita", "Una vecchia spada da pirata.",
                25, Rarita.COMUNE, TipoOggetto.ARMA, 5);
    }
}
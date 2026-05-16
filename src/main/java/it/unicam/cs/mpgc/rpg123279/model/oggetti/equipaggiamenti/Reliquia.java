package it.unicam.cs.mpgc.rpg123279.model.oggetti.equipaggiamenti;

import it.unicam.cs.mpgc.rpg123279.model.enumerazioni.Rarita;
import it.unicam.cs.mpgc.rpg123279.model.enumerazioni.TipoOggetto;
import it.unicam.cs.mpgc.rpg123279.model.oggetti.AbstractEquipaggiamento;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("RELIQUIA")
public class Reliquia extends AbstractEquipaggiamento {

    public Reliquia() {
        super("Amuleto Maledetto", "Un antico amuleto legato al mare.",
                100, Rarita.EPICO, TipoOggetto.RELIQUIA, 10);
    }
}
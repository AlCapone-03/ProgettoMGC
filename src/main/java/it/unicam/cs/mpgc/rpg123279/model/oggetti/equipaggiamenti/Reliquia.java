package it.unicam.cs.mpgc.rpg123279.model.oggetti.equipaggiamenti;

import it.unicam.cs.mpgc.rpg123279.model.enumerazioni.Rarita;
import it.unicam.cs.mpgc.rpg123279.model.enumerazioni.TipoOggetto;
import it.unicam.cs.mpgc.rpg123279.model.oggetti.AbstractEquipaggiamento;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("RELIQUIA")
public class Reliquia extends AbstractEquipaggiamento {

    @Column(nullable = true)
    private String effettoSpeciale;

    public Reliquia() {}
    public Reliquia(String nome, String descrizione, int valore, int livelloRichiesto,
                    Rarita rarita, int bonus, String effettoSpeciale) {
        super(nome, descrizione, valore, rarita, TipoOggetto.RELIQUIA, livelloRichiesto, bonus);
        this.effettoSpeciale = effettoSpeciale;
    }
}
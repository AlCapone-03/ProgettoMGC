package it.unicam.cs.mpgc.rpg123279.model.oggetti.consumabili;

import it.unicam.cs.mpgc.rpg123279.model.enumerazioni.Rarita;
import it.unicam.cs.mpgc.rpg123279.model.enumerazioni.TipoOggetto;
import it.unicam.cs.mpgc.rpg123279.model.oggetti.AbstractConsumabile;
import it.unicam.cs.mpgc.rpg123279.model.personaggi.Giocatore;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("RUM")
public class Rum extends AbstractConsumabile {

    @Column(nullable = false)
    private int cura;

    public Rum() {}
    public Rum(String nome, String descrizione, int valore, Rarita rarita, int livelloRichiesto, int cura) {
        super(nome, descrizione, valore, rarita, TipoOggetto.CONSUMABILE, livelloRichiesto);
        this.cura = cura;
    }

    @Override
    public void usa(Giocatore giocatore) {
        giocatore.recuperaSalute(cura);
    }
}
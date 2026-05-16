package it.unicam.cs.mpgc.rpg123279.model.oggetti.consumabili;

import it.unicam.cs.mpgc.rpg123279.model.enumerazioni.Rarita;
import it.unicam.cs.mpgc.rpg123279.model.enumerazioni.TipoOggetto;
import it.unicam.cs.mpgc.rpg123279.model.oggetti.AbstractConsumabile;
import it.unicam.cs.mpgc.rpg123279.model.personaggi.Giocatore;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("POZIONE")
public class PozioneSalute extends AbstractConsumabile {

    private static final int CURA = 50;

    public PozioneSalute() {
        super("Pozione della Marea", "Una pozione curativa rara.",
                50, Rarita.RARO, TipoOggetto.CONSUMABILE);
    }

    @Override
    public void usa(Giocatore giocatore) {
        giocatore.recuperaSalute(CURA);
    }
}
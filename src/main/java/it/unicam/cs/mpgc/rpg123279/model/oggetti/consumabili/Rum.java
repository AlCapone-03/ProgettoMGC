package it.unicam.cs.mpgc.rpg123279.model.oggetti.consumabili;

import it.unicam.cs.mpgc.rpg123279.model.enumerazioni.Rarita;
import it.unicam.cs.mpgc.rpg123279.model.enumerazioni.TipoOggetto;
import it.unicam.cs.mpgc.rpg123279.model.oggetti.AbstractConsumabile;
import it.unicam.cs.mpgc.rpg123279.model.personaggi.Giocatore;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("RUM")
public class Rum extends AbstractConsumabile {

    private static final int CURA = 20;

    public Rum() {
        super("Rum del Corsaro", "Recupera salute durante il viaggio.",
                15, Rarita.COMUNE, TipoOggetto.CONSUMABILE);
    }

    @Override
    public void usa(Giocatore giocatore) {
        giocatore.recuperaSalute(CURA);
    }
}
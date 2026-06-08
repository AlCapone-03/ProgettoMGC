package it.unicam.cs.mpgc.rpg123279.model.personaggi.nemici;

import it.unicam.cs.mpgc.rpg123279.model.enumerazioni.TipoNemico;
import it.unicam.cs.mpgc.rpg123279.model.personaggi.AbstractMostroMarino;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "kraken_boss")
public class KrakenBoss extends AbstractMostroMarino {

    protected KrakenBoss() {}
    public KrakenBoss(int livello) {
        super("Kraken", 150+(livello*12), 25+(livello*3), 6+(livello*4),
                livello, 100+(livello*15), 60+(livello*10), TipoNemico.KRAKEN_BOSS);
    }

}
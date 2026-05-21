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
        super("Kraken", 300+(livello*20), 120+(livello*3), 80+(livello*2),
                livello, 250*(livello*3), 200*(livello*3), TipoNemico.KRAKEN_BOSS);
    }

    @Override
    public void subisciDanno(int damage) {
        int riduzione = getDifesa() / 2;
        int dannoEffettivo = Math.max(1, damage - getDifesa());
        setHp(Math.max(0, getHp() - dannoEffettivo));
    }
}
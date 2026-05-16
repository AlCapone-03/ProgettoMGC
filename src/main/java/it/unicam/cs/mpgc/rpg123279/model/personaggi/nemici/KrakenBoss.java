package it.unicam.cs.mpgc.rpg123279.model.personaggi.nemici;

import it.unicam.cs.mpgc.rpg123279.model.enumerazioni.TipoNemico;
import it.unicam.cs.mpgc.rpg123279.model.personaggi.AbstractMostroMarino;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "kraken_boss")
public class KrakenBoss extends AbstractMostroMarino {

    public KrakenBoss() {
        super("Kraken", 300, 45, 20, 10, 250,
                200, TipoNemico.KRAKEN_BOSS);
    }

    @Override
    public void subisciDanno(int damage) {

    }

    @Override
    public boolean isVivo() {
        return false;
    }

    @Override
    public void recuperaSalute(int amount) {

    }

    @Override
    public int getAttacco() {
        return 0;
    }

    @Override
    public int getDifesa() {
        return 0;
    }

    @Override
    public int getHp() {
        return 0;
    }

    @Override
    public int getMaxHp() {
        return 0;
    }

    @Override
    public int getLivello() {
        return 0;
    }

    @Override
    public boolean isAlive() {
        return false;
    }
}
